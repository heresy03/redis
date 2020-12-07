package com.cx.main;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * multi 开启事务
 * 事务命令入队
 * 使用exec命令执行事务
 */
public class JedisTransaction {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // 开启事务
        Transaction transaction = jedis.multi();
        // 命令入队
        transaction.set("name","tzhang");
        transaction.set("age","20");
        transaction.set("address","苏州");
        // 事务执行失败，字符串不能加5
        transaction.incrBy("name",5);
        transaction.incrBy("age",5);
        // 执行事务
        transaction.exec();
        // 取消执行事务
//        transaction.discard();

        System.out.println(jedis.get("name"));
        // 年龄值为25，redis数据库事务不具有回滚性，不会因为，某条命令
        // 的执行失败而终止执行整个事务
        System.out.println(jedis.get("age"));
        System.out.println(jedis.get("address"));

    }
}
