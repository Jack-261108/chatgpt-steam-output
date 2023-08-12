# 简介
Open AI ChatGPT流式输出。Open AI Stream output. ChatGPT Stream output、
支持Tokens计算。

**此项目只是对[chatgpt-java](https://github.com/Grt1228/chatgpt-java) SDK的一个简单示例项目，实现流式输出，仅做参考仅做参考仅做参考。大家最好还是自己基于SDK动手实现**
---

流式输出实现方式 | 小程序 | 安卓 | ios | H5 
---|---|---|---|---
SSE参考：[OpenAISSEEventSourceListener](https://github.com/Grt1228/chatgpt-steam-output/blob/main/src/main/java/com/unfbx/chatgptsteamoutput/listener/OpenAISSEEventSourceListener.java) | 不支持| 支持| 支持 | 支持
WebSocket参考：[OpenAIWebSocketEventSourceListener](https://github.com/Grt1228/chatgpt-steam-output/blob/main/src/main/java/com/unfbx/chatgptsteamoutput/listener/OpenAIWebSocketEventSourceListener.java) | 支持| 支持| 支持| 支持
---
**最新版SDK参考：https://github.com/Grt1228/chatgpt-java** 


### 有bug欢迎朋友们指出，互相学习，有疑问加群**免费**解答。
一起探讨chatgpt-java，SDK问题咨询<br/>项目产品开发交流 | 群失效关注公众号恢复：chatgpt-java | 个人微信
---|---|---
<img src="https://user-images.githubusercontent.com/27008803/225246389-7b452214-f3fe-4a70-bd3e-832a0ed34288.jpg" width="210" height="300" alt="二维码" />  | <img src="https://g-photo.oss-cn-shanghai.aliyuncs.com/hd15.jpg" width="210" height="210" alt="二维码" /> | <img src="https://user-images.githubusercontent.com/27008803/225246581-15e90f78-5438-4637-8e7d-14c68ca13b59.jpg" width="210" height="300" alt="二维码" />
---

# SSE
主要是基于[SSE](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#event_stream_format) 实现的（可以百度下这个技术）。也是最近在了解到SSE。OpenAI官网在接受Completions接口的时候，有提到过这个技术。
Completion对象本身有一个stream属性，当stream为true时候Api的Response返回就会变成Http长链接。
具体可以看下文档：https://platform.openai.com/docs/api-reference/completions/create
![实例2](https://user-images.githubusercontent.com/27008803/224496355-76e94a21-a346-4260-93bf-9088bcb31a18.gif)


