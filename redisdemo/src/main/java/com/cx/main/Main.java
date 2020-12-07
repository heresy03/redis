package com.cx.main;

import redis.clients.jedis.Jedis;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 设置redis数据库连接密码，如果有密码需要设置
//        jedis.auth("123456");
        System.out.println("连接成功");
        System.out.println("服务正在运行：" + jedis.ping());

        //  获取客户端信息
        System.out.println(jedis.getClient());
        //  清空数据库，相当于执行flushall
        System.out.println(jedis.flushAll());
//        查看redis信息，相当于info命令
        System.out.println(jedis.info());
        //  获取数据库中key的数量，相当于执行dbsize命令
        System.out.println(jedis.dbSize());
        //  获取数据库的名字
        System.out.println(jedis.getDB());
        //  获取redis服务器的时间，相当于time命令
        System.out.println(jedis.time());
    }
}
