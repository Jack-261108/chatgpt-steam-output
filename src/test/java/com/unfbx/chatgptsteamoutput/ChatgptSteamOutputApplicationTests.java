package com.unfbx.chatgptsteamoutput;

import com.unfbx.chatgptsteamoutput.config.ChatGptConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = ChatgptSteamOutputApplication.class)
class ChatgptSteamOutputApplicationTests {
    @Resource
    private ChatGptConfig chatGptConfig;

    @Test
    void contextLoads() {
        System.out.println(chatGptConfig.getTokens());
    }

}
