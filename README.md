# 简介

Open AI ChatGPT流式输出。Open AI Stream output. ChatGPT Stream output、 支持Tokens计算。

**此项目只是对[chatgpt-java](https://github.com/Grt1228/chatgpt-java) SDK的一个简单示例项目，实现流式输出，仅做参考仅做参考仅做参考。大家最好还是自己基于SDK动手实现**
对[chatgpt-java-stream](https://github.com/Grt1228/chatgpt-steam-output) 做了一点简单优化.

---

流式输出实现方式 | 小程序 | 安卓 | ios | H5
---|---|---|---|---
SSE参考：[OpenAISSEEventSourceListener](https://github.com/Grt1228/chatgpt-steam-output/blob/main/src/main/java/com/unfbx/chatgptsteamoutput/listener/OpenAISSEEventSourceListener.java) | 不支持| 支持| 支持 | 支持
WebSocket参考：[OpenAIWebSocketEventSourceListener](https://github.com/Grt1228/chatgpt-steam-output/blob/main/src/main/java/com/unfbx/chatgptsteamoutput/listener/OpenAIWebSocketEventSourceListener.java) | 支持| 支持| 支持| 支持

---
**最新版SDK参考：https://github.com/Grt1228/chatgpt-java**

## 目前仅支持websocket

* 快速开始
* 1 克隆本项目
* 2 配置自己的apikey和上下文Token数(默认是20)
* 运行本项目的前端 [Gpt](https://github.com/Jack-261108/Gpt)
* 


