package cn.qb.scaffolding.config;

import cn.qb.scaffolding.common.OperateLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.UUID;

/**
 * 日志切面
 **/
@Component
@Configuration
@Aspect
@Slf4j
public class RestApiLogConfiguration {

    /**
     * GSON
     */
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 记录接口操作内容
     */
    private ThreadLocal<OperateLog> operateLogThreadLocal = new ThreadLocal<>();

    /**
     * controller包下的所有类中的所有方法作为切点
     */
    @Pointcut("execution(public * cn.qb.scaffolding.api.*.*(..))")
    public void apis(){
        log.info("接口切面");
    }

    /**
     * 接口请求时打印日志
     *
     * @param joinPoint 切点
     */
    @Before("apis()")
    public void before(JoinPoint joinPoint){

        try{
            //获取本次请求的HttpServletRequest
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            //定义traceID，要么从上游系统的请求头中拿，要么就自己生成
            String traceId = request.getHeader("traceId");
            if (StringUtils.isBlank(traceId)) {
                traceId = UUID.randomUUID().toString().replace("-", "");
            }
            MDC.put("traceId", traceId);

            //得到请求的时间，为了算本次请求的耗时
            long reqTime = System.currentTimeMillis();
            OperateLog operateLog = new OperateLog();
            operateLog.setRequestTime(reqTime);
            //将请求时间塞进
            operateLogThreadLocal.set(operateLog);

            //打印请求体相关日志，param为get请求的参数，request为post请求的requestBody
            log.info("开始请求{}@|uri={}@|headers:{}@|param:{}@|request:{}",
                    request.getMethod(), request.getRequestURI(),
                    getHeaders(request), request.getQueryString(),getBody(request));
        }catch(Exception e){
            log.error("打印请求日志失败：", e);
        }
    }

    /**
     * 接口返回时日志打印
     *
     * @param obj 返回对象
     */
    @AfterReturning(returning = "obj",pointcut = "apis()")
    public void afterReturning(Object obj){
        try{
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            //从线程存储类中获得请求时间
            OperateLog operateLog = operateLogThreadLocal.get();
            log.info("结束请求{}@|uri={}@|cost:{}ms@|response={}",request.getMethod(),
                    request.getRequestURI(),System.currentTimeMillis()- operateLog.getRequestTime(),
                    getResponse(obj));
            //清空线程对象
            operateLogThreadLocal.remove();
            MDC.clear();
        }catch (Exception e){
            log.error("打印返回日志失败：",e);
        }
    }

    @AfterThrowing(throwing = "ex",pointcut = "apis()")
    public void afterThrowing(Exception ex){

        try{
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            OperateLog operateLog = operateLogThreadLocal.get();
            log.info("结束请求{}@|uri={}@|cost:{}ms@|type=throw@|response={}",request.getMethod(),
                    request.getRequestURI(),System.currentTimeMillis()- operateLog.getRequestTime(),
                    ex.getMessage());
            //清空线程对象
            operateLogThreadLocal.remove();
            MDC.clear();
        }catch(Exception e){
            log.error("打印异常日志失败：{}",e);
        }
    }



    /**
     * 获取请求头
     *
     * @param request 请求体
     * @return Header值
     */
    private String getHeaders(HttpServletRequest request){
        Enumeration er = request.getHeaderNames();
        StringBuffer sb = new StringBuffer("");
        while(er.hasMoreElements()){
            String name = (String)er.nextElement();
            sb.append(name);
            sb.append("=");
            sb.append(request.getHeader(name));
            sb.append(";");
        }
        return sb.toString();
    }

    /**
     * 请求体json格式化输出，主要针对POST请求
     *
     * @param request 请求参数
     * @return 格式化数据
     */
    private String getBody(HttpServletRequest request){

        String body = null;
        String cutBody = null;
        InputStream inputStream = null;
        try{
            inputStream = request.getInputStream();
            body = IOUtils.toString(inputStream, "utf-8");
            cutBody = body.length() > 8888 ? body.substring(0,8888) : body;
        }catch (IOException e){
            return null;
        }finally{
            IOUtils.closeQuietly(inputStream);
        }
        return cutBody;
    }

    /**
     * 截取返回的response
     *
     * @param obj 返回参数
     * @return 截取后的返回
     */
    private String getResponse(Object obj){
        String cutResponse = gson.toJson(obj);
        try{
            String response = gson.toJson(obj);
            cutResponse = response.length() > 1000 ? response.substring(0,1000) : response;
        }catch (Exception e){
            return null;
        }
        return cutResponse;
    }

}
