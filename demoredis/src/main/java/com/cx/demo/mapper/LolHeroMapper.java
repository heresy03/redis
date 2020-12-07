package com.cx.demo.mapper;

import com.cx.demo.pojo.LolHero;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LolHeroMapper {

    List<LolHero> selectAll();
    LolHero findById(Integer id);
    int insertHero(LolHero lolHero);
    int updateHero(LolHero lolHero);
    int deleteById(Integer id);
    void deleteAll();
}
