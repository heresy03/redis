package com.cx.demo.controller;

import com.cx.demo.pojo.LolHero;
import com.cx.demo.redis.RedisOpsUtil;
import com.cx.demo.redis.RedisUtil;
import com.cx.demo.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    HeroService heroService;

    // 自己写的时候返回值改成responseresult
    @RequestMapping("demo01")
    public String demo01(){
        return "";
    }




}
