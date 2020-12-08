package com.cx.demo.controller;


import com.cx.demo.pojo.LolHero;
import com.cx.demo.service.LolHeroService;
import com.cx.demo.util.ResultUtil;
import com.cx.demo.vo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody
@RequestMapping("lolhero")
@CrossOrigin
public class LolHeroController {


//    private static Logger logger = LoggerFactory.getLogger(LolHeroController.class);
    // com.cx.controller.LolHeroController
    private static Logger logger = LoggerFactory.getLogger(LolHeroController.class.getSimpleName());


    @Autowired
    LolHeroService lolHeroService;

    @Autowired
    ResultUtil resultUtil;
    @Transactional
    @GetMapping("heroall")
    public ResponseResult selectAll(){

        logger.error("error--------------------------");
        logger.debug("debug--------------------------");

        List<LolHero> lolHeroList = lolHeroService.selectAll();
        return resultUtil.getResponseResult(1, lolHeroList);
    }
    @Transactional
    @GetMapping("heroallpage")
    public ResponseResult selectAllPage(){
        logger.error("error--------------------------");
        return resultUtil.getResponseResult(1, lolHeroService.selectAllPage());
    }
    @GetMapping("hero")
    public ResponseResult select(){
//        System.out.println(responseResult);
        return null;
    }

    @GetMapping("hero/{id}")
    public LolHero selectById(@PathVariable Integer id){
//        System.out.println(responseResult);
        return lolHeroService.findById(id);
    }

    @PostMapping("insert")
    public LolHero insertHero(LolHero lolHero){
//        LolHero lolHero = new LolHero();
//        lolHero.setNickname("kk");
//        lolHero.setHandHard(6);
//        lolHero.setAttackPower(7);
//        lolHero.setUkName("uk");
//        lolHero.setTinyImg("img");
        lolHeroService.insertHero(lolHero);
        return lolHero;
    }

    @PutMapping("update")
    public LolHero updateHero(){
        LolHero lolHero = new LolHero();
        lolHero.setId(179);
        lolHero.setNickname("kk");
        lolHero.setHandHard(6);
        lolHero.setAttackPower(7);
        lolHero.setUkName("uk");
        lolHero.setTinyImg("img");
        lolHeroService.updateHero(lolHero);
        return lolHero;
    }
    @DeleteMapping("del/{id}")
    public String delete(@PathVariable("id") Integer id){
        lolHeroService.deleteById(id);
        return "1";
    }
    @DeleteMapping("delall")
    public String clearCache(){
        lolHeroService.deleteAll();
        return "1";
    }
}
