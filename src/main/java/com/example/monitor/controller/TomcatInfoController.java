package com.example.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tomcat")
public class TomcatInfoController {

    @Autowired
    private ServletWebServerApplicationContext webServerApplicationContext;

    @GetMapping("/info")
    public Map<String, Object> getTomcatInfo() {
        Map<String, Object> result = new HashMap<>();
        
        TomcatWebServer tomcatWebServer = (TomcatWebServer) webServerApplicationContext.getWebServer();
        org.apache.catalina.startup.Tomcat tomcat = tomcatWebServer.getTomcat();
        
        // 获取基本信息
        result.put("port", webServerApplicationContext.getWebServer().getPort());
        result.put("running", true);  // 如果服务能响应，说明正在运行
        
        // 获取连接器信息
        org.apache.catalina.connector.Connector connector = tomcat.getConnector();
        result.put("maxConnections", connector.getProperty("maxConnections"));
        result.put("maxThreads", connector.getProperty("maxThreads"));
        result.put("connectionTimeout", connector.getProperty("connectionTimeout"));
        result.put("acceptCount", connector.getProperty("acceptCount"));
        result.put("protocol", connector.getProtocol());
        
        // 获取线程池信息
        result.put("currentThreadCount", tomcat.getService().getContainer().getBackgroundProcessorDelay());
        
        return result;
    }
} 