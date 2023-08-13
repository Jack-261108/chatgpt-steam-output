package com.unfbx.chatgptsteamoutput;

import com.unfbx.chatgptsteamoutput.context.HandlerContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = ChatgptSteamOutputApplication.class)
class ChatgptSteamOutputApplicationTests {
    @Resource
    private HandlerContext handlerContext;

    @Test
    void contextLoads() {
       handlerContext.dohandle();
    }

}
