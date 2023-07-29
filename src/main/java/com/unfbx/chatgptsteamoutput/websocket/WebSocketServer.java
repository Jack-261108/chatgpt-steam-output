package com.unfbx.chatgptsteamoutput.websocket;

import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.utils.TikTokensUtil;
import com.unfbx.chatgptsteamoutput.config.ChatGptConfig;
import com.unfbx.chatgptsteamoutput.context.MessageContext;
import com.unfbx.chatgptsteamoutput.listener.OpenAIWebSocketEventSourceListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：websocket 服务端
 *
 * @author https:www.unfbx.com
 * @date 2023-03-23
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{uid}")
public class WebSocketServer {
    public static final Map<String, MessageContext> contextHashMap = new ConcurrentHashMap<>();
    private static OpenAiStreamClient openAiStreamClient;


    @Resource
    private ChatGptConfig chatGptConfig;
    //在线总数
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //当前会话
    private Session session;
    //用户id -目前是按浏览器随机生成
    private String uid;

    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 用来存放每个客户端对应的WebSocketServer对象
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap();

    /**
     * 为了保存在线用户信息，在方法中新建一个list存储一下【实际项目依据复杂度，可以存储到数据库或者缓存】
     */
    private final static List<Session> SESSIONS = Collections.synchronizedList(new ArrayList<>());

    @Resource
    public void setOrderService(OpenAiStreamClient openAiStreamClient) {
        WebSocketServer.openAiStreamClient = openAiStreamClient;
    }

    /**
     * 建立连接
     *
     * @param session session
     * @param uid     用户id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) {
        this.session = session;
        this.uid = uid;
//        加入上下文环境
        log.debug("current context count:{}", chatGptConfig.getTokens());
        contextHashMap.put(uid, new MessageContext(chatGptConfig.getTokens()));
        webSocketSet.add(this);
        SESSIONS.add(session);
        if (webSocketMap.containsKey(uid)) {
            webSocketMap.remove(uid);
            webSocketMap.put(uid, this);
        } else {
            webSocketMap.put(uid, this);
            addOnlineCount();
        }
        log.info("[连接ID:{}] 建立连接, 当前连接数:{}", this.uid, getOnlineCount());
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose() {
        contextHashMap.remove(uid);
        webSocketSet.remove(this);
        if (webSocketMap.containsKey(uid)) {
            webSocketMap.remove(uid);
            subOnlineCount();
        }
        log.info("[连接ID:{}] 断开连接, 当前连接数:{}", uid, getOnlineCount());
    }

    /**
     * 发送错误
     *
     * @param session session
     * @param error   error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("[连接ID:{}] 错误原因:{}", this.uid, error.getMessage());
        error.printStackTrace();
    }

    /**
     * 接收到客户端消息
     *
     * @param msg 接受的消息
     */
    @OnMessage
    public void onMessage(String msg) {
        //       获取上下文环境
        MessageContext context = getContext();
        //接受参数
        OpenAIWebSocketEventSourceListener eventSourceListener = new OpenAIWebSocketEventSourceListener(this.session, context);
//        构造当前消息
        Message currentMessage = Message.builder().content(msg).role(Message.Role.USER).build();
//        把当前消息加入上下文环境
        context.push(currentMessage);
        ChatCompletion chatCompletion = buildChatCompletion(context);
        openAiStreamClient.streamChatCompletion(chatCompletion, eventSourceListener);
    }

    private ChatCompletion buildChatCompletion(MessageContext context) {
        int usedTokens = TikTokensUtil.tokens(ChatCompletion.Model.GPT_3_5_TURBO.getName(), context.get());
        return ChatCompletion.builder().messages(context.get()).stream(true).maxTokens(4096 - usedTokens).build();
    }

    public MessageContext getContext() {
        return contextHashMap.getOrDefault(uid, new MessageContext(chatGptConfig.getTokens()));
    }

    /**
     * 获取当前连接数
     *
     * @return 当前连接数
     */
    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 当前连接数加一
     */
    public static synchronized void addOnlineCount() {
        onlineCount.getAndAdd(1);
    }

    /**
     * 当前连接数减一
     */
    public static synchronized void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

}

