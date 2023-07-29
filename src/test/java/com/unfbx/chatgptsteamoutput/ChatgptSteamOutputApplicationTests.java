package com.unfbx.chatgptsteamoutput;

import com.unfbx.chatgptsteamoutput.websocket.WebSocketServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = ChatgptSteamOutputApplication.class)
class ChatgptSteamOutputApplicationTests {
    @Resource
    private WebSocketServer webSocketServer;

    @Test
    void contextLoads() {
        System.out.println(webSocketServer.getTokens());
    }

}
