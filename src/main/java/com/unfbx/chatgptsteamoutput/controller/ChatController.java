package com.unfbx.chatgptsteamoutput.controller;

import cn.hutool.core.util.StrUtil;
import com.unfbx.chatgpt.exception.BaseException;
import com.unfbx.chatgpt.exception.CommonError;
import com.unfbx.chatgptsteamoutput.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 * @date 2023-03-01
 */
@Controller
@Slf4j
public class ChatController {

    private final SseService sseService;

    public ChatController(SseService sseService) {
        this.sseService = sseService;
    }

    /**
     * 创建sse连接
     *
     * @param headers
     * @return
     */
    @CrossOrigin
    @GetMapping("/createSse")
    public SseEmitter createConnect(@RequestHeader Map<String, String> headers) {
        String uid = getUid(headers);
        return sseService.createSse(uid);
    }
    /**
     * 关闭连接
     *
     * @param headers
     */
    @CrossOrigin
    @GetMapping("/closeSse")
    public void closeConnect(@RequestHeader Map<String, String> headers) {
        String uid = getUid(headers);
        sseService.closeSse(uid);
    }
    /**
     * 获取uid
     *
     * @param headers
     * @return
     */
    private String getUid(Map<String, String> headers) {
        String uid = headers.get("uid");
        if (StrUtil.isBlank(uid)) {
            throw new BaseException(CommonError.SYS_ERROR);
        }
        return uid;
    }


}
