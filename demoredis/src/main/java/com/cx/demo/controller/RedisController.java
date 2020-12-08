package com.cx.demo.controller;

import com.cx.demo.pojo.LolHero;
import com.cx.demo.redis.RedisOpsUtil;
import com.cx.demo.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    RedisOpsUtil redisOpsUtil;

    @GetMapping("get")
    public String putObj(){
        LolHero lolHero = new LolHero();
        lolHero.setId(179);
        lolHero.setNickname("kk");
        lolHero.setHandHard(6);
        lolHero.setAttackPower(7);
        lolHero.setUkName("uk");
        lolHero.setTinyImg("img");
        redisOpsUtil.set("hero",lolHero);

        return "1";
    }


}
