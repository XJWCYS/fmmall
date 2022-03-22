package com.cys.fmmall.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//添加websocket服务节点配置（Java配置⽅式）
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter getServerEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
