package com.cx.main;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import sun.swing.StringUIClientPropertyKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Jedis实现排行榜
 */
public class JedisSortBang {
    // 总人数
    static final int TOTAL_SIZE = 30;
    static Jedis jedis = new Jedis("127.0.0.1", 6379);
    static String key = "王者峡谷";

    public static void main(String[] args) {
//        Jedis jedis = new Jedis("127.0.0.1", 6379);

        System.out.println("欢迎来到---游戏");
        List<String> playerList = players();

        System.out.println("进入游戏");
        setPlayScore(playerList);

        System.out.println("排行榜");
        outputRank();
        System.out.println("=====");
        outputScoreBetween(5000,8000);


        jedis.close();
    }

    private static void outputRank(){
        Set<Tuple> scoreList = jedis.zrevrangeWithScores(key, 0, -1);
        for(Tuple tuple:scoreList){
            System.out.println("ID:" + tuple.getElement() + ",score:" + tuple.getScore());
        }
    }

    private static void outputRankTop(int start, int stop){
        // 前五
        Set<Tuple> scoreList = jedis.zrevrangeWithScores(key, 0, 4);
        for(Tuple tuple:scoreList){
            System.out.println("ID:" + tuple.getElement() + ",score:" + tuple.getScore());
        }
    }

    /**
     * 分数区间
     * @param min
     * @param max
     */
    private static void outputScoreBetween(int min, int max){
        // 前五
        Set<Tuple> scoreList = jedis.zrangeByScoreWithScores(key, min, max);
        for(Tuple tuple:scoreList){
            System.out.println("ID:" + tuple.getElement() + ",score:" + tuple.getScore());
        }
    }


    /**
     * 模拟得分并设置如zset中
     * @param playerList
     */
    private static void setPlayScore(List<String> playerList){

        for (int i = 0; i < playerList.size(); i++) {
            int score = (int) (Math.random() * 10000);
            String member = playerList.get(i);
            System.out.println(member + "得分" + score);
            jedis.zadd(key,score, member);
        }
    }

    /**
     * 模拟玩家
     * @return
     */
    private static List<String> players(){
        List<String> playerList = new ArrayList<String>();
        for (int i = 0; i < TOTAL_SIZE; i++) {
            playerList.add(UUID.randomUUID().toString());
        }
        return playerList;
    }
}
