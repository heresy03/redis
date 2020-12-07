package com.cx.demo.service;



import com.cx.demo.pojo.LolHero;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface LolHeroService {

    List<LolHero> selectAll();
    PageInfo<LolHero> selectAllPage();
    LolHero findById(Integer id);
    LolHero insertHero(LolHero lolHero);
    LolHero updateHero(LolHero lolHero);
    void deleteById(Integer id);
    void deleteAll();

}
