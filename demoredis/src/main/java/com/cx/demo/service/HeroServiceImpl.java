package com.cx.demo.service;

import com.cx.demo.mapper.LolHeroMapper;
import com.cx.demo.pojo.LolHero;
import com.cx.demo.redis.RedisOpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeroServiceImpl implements HeroService{

    @Autowired
    LolHeroMapper lolHeroMapper;
    @Autowired
    RedisOpsUtil redisOpsUtil;

    @Override
    public LolHero findHero(Integer id) {
        LolHero lolHero = null;
        if(redisOpsUtil.exists(id + "")){
            lolHero = redisOpsUtil.get(id+"", LolHero.class);
        } else  {
            lolHero  = lolHeroMapper.findById(id);
            redisOpsUtil.set(id+"", lolHero, 120);
        }

        return lolHero;
    }
}
