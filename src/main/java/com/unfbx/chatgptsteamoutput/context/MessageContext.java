package com.unfbx.chatgptsteamoutput.context;

import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.utils.TikTokensUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Jack
 * @Desc
 * @Date 2023/7/28/16:57:11
 **/
@Slf4j
public class MessageContext {
    private final CopyOnWriteArrayList<Message> context;
    private final int contextSize;

    public MessageContext(int contextSize) {
        this.contextSize = contextSize;
        this.context = new CopyOnWriteArrayList<>();
    }

    public void push(Message message) {
        if (context.size() > contextSize) {
            remove();
        }
        context.add(message);
//        计算token，避免超过最大值
        int tokens = TikTokensUtil.tokens(ChatCompletion.Model.GPT_3_5_TURBO.getName(), context);
        while (tokens > 4096) {
            remove();
            tokens = TikTokensUtil.tokens(ChatCompletion.Model.GPT_3_5_TURBO.getName(), context);
        }
    }

    public List<Message> get() {
        return context;
    }

    public void clear() {
        context.clear();
    }

    public void remove() {
        context.remove(0);
    }
}
