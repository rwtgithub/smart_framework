package com.rwt.smartframework;

import com.rwt.smartframework.bean.Data;
import com.rwt.smartframework.bean.Handler;
import com.rwt.smartframework.bean.Param;
import com.rwt.smartframework.bean.View;
import com.rwt.smartframework.helper.BeanHelper;
import com.rwt.smartframework.helper.ControllerHelper;
import com.rwt.smartframework.util.ConfigHelper;
import com.rwt.smartframework.util.RefelectionUtil;
import com.sun.xml.internal.ws.util.StreamUtils;
import com.sun.xml.internal.ws.util.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关的Helper类
        HelperLoader.init();
        //获取ServeletContext对象，用于注册Servlet
        ServletContext servletContext=servletConfig.getServletContext();
        //注册处理JSP的Servlet
        ServletRegistration jspServlet=servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet=servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求方法与请求路径
        String requestMethod=req.getMethod();
        String requestPath=req.getPathInfo();
        //获取Action处理器
        Handler handler=ControllerHelper.gerHandler(requestMethod,requestPath);
        if(handler!=null){
            //获取Controller类及其Bean实例
            Class<?> controllerClass=handler.getControllerClass();
            Object controllerBean=BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String,Object> paraMap=new HashMap<String, Object>();
            Enumeration<String> paramNames=req.getParameterNames();
            while(paramNames.hasMoreElements()){
                String paramName=paramNames.nextElement();
                String paramValue=req.getParameter(paramName);
                paraMap.put(paramName,paramValue);

            }
            //String body=CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            String body="";
            if(true){ //StringUtil.isNotEmpty()
                String [] params=body.split("&");
                if(ArrayUtils.isNotEmpty(params)){
                    for (String param:params){
                        String [] array=param.split("=");
                        if(ArrayUtils.isNotEmpty(array)&&array.length==2){
                            String paramName=array[0];
                            String paramValue=array[1];
                            paraMap.put(paramName,paramValue);
                        }
                    }
                }

            }
            Param param=new Param(paraMap);
            //调用Action方法
            Method actionMethod=handler.getActionMethod();
            Object result=RefelectionUtil.invokeMethod(controllerBean,actionMethod,param);
            //处理Action方法返回值
            if(result instanceof View){
                View view=(View) result;
                String path=view.getPath();
                if(!path.isEmpty()){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath()+path);
                    }else{
                        Map<String,Object> model=view.getModelMap();
                        for (Map.Entry<String,Object> entry:model.entrySet()){
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req,resp);
                    }
                }

            }else if(result instanceof Data){
                Data data=(Data) result;
                Object model=data.getModel();
                if(model!=null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer=resp.getWriter();
                    //String json=JsonUtil.toJson(model);
                    String json="";
                    writer.write(json);
                    writer.flush();
                    writer.close();



                }
            }

        }
    }
}
