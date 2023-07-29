package com.unfbx.chatgptsteamoutput.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jack
 * @Desc
 * @Date 2023/7/29/19:18:48
 **/
@Setter
@Getter
@ConfigurationProperties("chatgpt")
public class ChatGptConfig {
    private int tokens = 20;
    private String apiHost = "127.0.0.1";
    private List<String> apiKey;
}
