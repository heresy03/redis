package com.cx.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RedisDemoController {
    // redis数据库的操作工具类
    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("test")
    public String test(){
        System.out.println(redisTemplate);
        return "1";
    }


}
