package com.jw.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RequestResponseInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer pathInfo = request.getRequestURL();
        logger.info("===================== START - [{}] =====================", pathInfo);

        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());

        StringBuilder builder = new StringBuilder();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for(Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String[] values = entry.getValue();
            builder.append("| ").append(entry.getKey()).append(" = ");
            for(String value : values) {
                builder.append(value).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(" ");
        }
        if(builder.length() > 0) {
            builder.append("|");
            logger.info(builder.toString());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String pathInfo = request.getRequestURL().toString();
        pathInfo = pathInfo.replace("/", "-");

        logger.info("===================== End - [{}] =====================", pathInfo);
        super.afterCompletion(request, response, handler, ex);
    }
}
