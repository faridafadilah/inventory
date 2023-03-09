package com.inventory.backend.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.inventory.backend.server.model.Logging;
import com.inventory.backend.server.repository.LoggRepository;

import java.util.Date;

@Component
public class LoggerInterceptor implements HandlerInterceptor {
  
  @Autowired
  private LoggRepository logrepo;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String method = request.getMethod();
    String endpoint = request.getRequestURI();
    String logSource = handler.toString();
    String logMessage = String.format("%s %s", method, endpoint);
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Date createdAt = new Date();

    request.setAttribute("startTime", System.currentTimeMillis());
    request.setAttribute("method", method);
    request.setAttribute("endpoint", endpoint);
    request.setAttribute("username", username);
    request.setAttribute("createdAt", createdAt);
    request.setAttribute("logSource", logSource);
    request.setAttribute("logMessage", logMessage);

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    String method = (String) request.getAttribute("method");
    String endpoint = (String) request.getAttribute("endpoint");
    Integer status = response.getStatus();
    String username = (String) request.getAttribute("username");
    Date createdAt = (Date) request.getAttribute("createdAt");
    String logMessage = (String) request.getAttribute("logMessage");

    Logging log = new Logging();
    log.setStatusCode(status);
    log.setError(logMessage);
    log.setMethod(method);
    log.setUrl(endpoint);
    log.setDate(createdAt.toString());
    log.setUsername(username);
    logrepo.save(log);
  }
}

