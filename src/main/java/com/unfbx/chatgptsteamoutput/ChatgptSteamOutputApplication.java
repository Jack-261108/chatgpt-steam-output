package com.unfbx.chatgptsteamoutput;

import com.unfbx.chatgptsteamoutput.config.ChatGptConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * 描述：ChatgptSteamOutputApplication
 *
 * @author https:www.unfbx.com
 * @date 2023-02-28
 */
@EnableConfigurationProperties(ChatGptConfig.class)
@EnableWebSocket
@SpringBootApplication
public class ChatgptSteamOutputApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatgptSteamOutputApplication.class, args);
    }

}
