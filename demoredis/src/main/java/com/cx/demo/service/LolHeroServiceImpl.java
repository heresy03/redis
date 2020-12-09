package com.cx.demo.service;


import com.cx.demo.mapper.LolHeroMapper;
import com.cx.demo.pojo.LolHero;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 缓存与数据库同步
 *
 */
@Service
// 本类内的方法指定使用缓存时，默认的名称
@CacheConfig(cacheNames = "herocache")
public class LolHeroServiceImpl implements LolHeroService {
    @Autowired
    LolHeroMapper lolHeroMapper;

    @Override
    public List<LolHero> selectAll() {

        return lolHeroMapper.selectAll();
    }


    @Override
    public PageInfo<LolHero> selectAllPage() {
        // 设置分页参数
        PageHelper.startPage(1, 3);
        List<LolHero> lolHeroList = lolHeroMapper.selectAll();
        PageInfo<LolHero> pageInfo = new PageInfo<LolHero>(lolHeroList);
        return pageInfo;
    }

    /**
     *
     * @param lolHero
     * @return
     */
    @CachePut(key="#p0.id") // p0表示第一个参数
    // 必须要有返回值，否则没有数据放到缓存中
    public LolHero insertHero(LolHero lolHero){
        lolHeroMapper.insertHero(lolHero);
        return lolHero; //
    }

    @CachePut(key="#p0.id") // p0表示第一个参数
    // 必须要有返回值，否则没有数据放到缓存中
    public LolHero updateHero(LolHero lolHero){
        lolHeroMapper.updateHero(lolHero);
        return lolHero; // userMapper.find(u.getId())
    }
//    @Cacheable(key="#p0")// 会先查询缓存，如果缓存中存在，则不执行方法
    @Cacheable(keyGenerator = "keyGenerator")
    public LolHero findById(Integer id){
        System.out.println("findById----------");
        return lolHeroMapper.findById(id);
    }

    // keyGenerator = "keyGenerator"
    @CacheEvict(key="#p0")// 删除缓存中的数据
    public void deleteById(Integer id){
        lolHeroMapper.deleteById(id);
    }


    @CacheEvict(allEntries = true) // 删除缓存名称为herocache，类上
    // 所有缓存，清空失败，缓存不会被清除
    public void deleteAll(){

    }
}
