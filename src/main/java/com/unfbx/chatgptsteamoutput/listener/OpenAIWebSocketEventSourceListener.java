package com.unfbx.chatgptsteamoutput.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgptsteamoutput.context.MessageContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import org.jetbrains.annotations.NotNull;

import javax.websocket.Session;

/**
 * 描述：OpenAI流式输出Socket接收
 *
 * @author https:www.unfbx.com
 * @date 2023-03-23
 */
@Slf4j
public class OpenAIWebSocketEventSourceListener extends AbstractOpenAiEventSourceListener {

    private final Session session;
    private StringBuilder builder;
    private final MessageContext messageContext;

    public OpenAIWebSocketEventSourceListener(@NotNull Session session, @NotNull MessageContext context) {
        this.messageContext = context;
        builder = new StringBuilder();
        this.session = session;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        log.info("OpenAI建立sse连接...");
    }

    /**
     * {@inheritDoc}
     */
    @SneakyThrows
    @Override
    public void onEvent(@NotNull EventSource eventSource, String id, String type, @NotNull String data) {
        if (data.equals("[DONE]")) {
            Message build = Message.builder().content(builder.toString()).role(Message.Role.ASSISTANT).build();
//            把消息加入上下文环境中
            messageContext.push(build);
            log.info("OpenAI返回数据结束了");
            builder = new StringBuilder();
            session.getBasicRemote().sendText("[DONE]");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        ChatCompletionResponse completionResponse = mapper.readValue(data, ChatCompletionResponse.class); // 读取Json
        String delta = mapper.writeValueAsString(completionResponse.getChoices().get(0).getDelta());
        String msg = completionResponse.getChoices().get(0).getDelta().getContent();
        builder.append(msg == null ? "" : msg);
        session.getBasicRemote().sendText(delta);
    }


    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        log.info("OpenAI关闭sse连接...");
    }
}
