package com.unfbx.chatgptsteamoutput.config;

import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.function.KeyRandomStrategy;
import com.unfbx.chatgpt.function.KeyStrategyFunction;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author Jack
 * @Desc
 * @Date 2023/8/13/22:08:45
 **/
@Configuration
public class OpenAiConfig {


    @ConditionalOnMissingBean(KeyStrategyFunction.class)
    @Bean
    public KeyStrategyFunction<?, ?> keyStrategyFunction() {
        return new KeyRandomStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(OkHttpClient.class)
    public OkHttpClient okHttpClient(Proxy proxy) {
        return new OkHttpClient.Builder()
                .proxy(proxy)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(Proxy.class)
    public Proxy proxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
    }

    @Bean
    @ConditionalOnMissingBean(value = OpenAiStreamClient.class)
    public OpenAiStreamClient openAiStreamClient(OkHttpClient okHttpClient, ChatGptConfig chatGptConfig, KeyStrategyFunction<?, ?> keyStrategy) {
        return OpenAiStreamClient.builder()
                .apiKey(chatGptConfig.getApiKey())
                //自定义key使用策略 默认随机策略
                .keyStrategy(keyStrategy).okHttpClient(okHttpClient).build();
    }
}
