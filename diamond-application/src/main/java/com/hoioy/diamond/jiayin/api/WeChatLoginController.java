package com.hoioy.diamond.jiayin.api;

import cn.hutool.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wechat")
public class WeChatLoginController {
    final  String  AppID="wxccbae6dc90e98a2f";
    final String AppSecret ="6e74498b801c4d4bcc7d6de18541cf1f";
    final String url = "https://api.weixin.qq.com/sns/jscode2session?";

    @RequestMapping("/login")
    public void Login(@RequestParam("code") String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("appid", AppID);
        map.put("secret", AppSecret);
        map.put("grant_type", "authorization_code");
        map.put("js_code", code);
        String body = HttpRequest.get(url).form(map).timeout(20000).execute().body();
    }
}
