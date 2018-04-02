package net.adwiser.demo.multicall;

import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class PortProvider implements ApplicationListener<ServletWebServerInitializedEvent> {

    private int port;

    public int getPort() {
        return port;
    }

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        port = event.getWebServer().getPort();
    }
}