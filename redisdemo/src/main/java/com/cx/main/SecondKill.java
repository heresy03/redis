package com.cx.main;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 秒杀： 写入内存  多线程异步处理，分布式处理
 */
public class SecondKill {
    public static void main(String[] args) {
        String phone = "huawei";// key
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.del(phone);
        jedis.set(phone, "100");
        jedis.close();

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new SecondKillRunnable("user" + getRandomString(8)));
        }


    }

    private static String getRandomString(int length){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            stringBuffer.append(base.charAt(number));
        }
        return stringBuffer.toString();
    }
}
