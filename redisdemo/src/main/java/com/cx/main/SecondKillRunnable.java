package com.cx.main;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class SecondKillRunnable implements Runnable{
    String phone = "huawei";
    String userinfo;
    Jedis jedis = new Jedis("127.0.0.1", 6379);

    public SecondKillRunnable(){

    }
    public SecondKillRunnable(String userinfo){
        this.userinfo = userinfo;
    }
    public void run() {
        try {
            jedis.watch(phone);
            String val = jedis.get(phone);
            int valInt = Integer.valueOf(val);
            if(valInt <= 100 && valInt >= 1){
                Transaction transaction = jedis.multi();
                transaction.incrBy(phone, -1);
                // 如果此时watchkeys被改动了，返回null
                List<Object> list = transaction.exec();
                if(list == null || list.size() == 0){
                    String failUserinfo = "fail " + userinfo + " 抢购失败";
                    System.out.println(failUserinfo);
                    jedis.setnx("fail " + userinfo ,failUserinfo);
                } else {
                    for (Object succ:list) {
                        String succUserinfo = "succ" + succ.toString() + userinfo;
                        String succInfo = succUserinfo + " 抢购成功" + (1 - (valInt - 100));
                        System.out.println(succInfo);
                        jedis.setnx(succUserinfo, succInfo);
                    }
                }

            } else {
                String failUserinfo = "kcfail" + userinfo;
                String kcFailinfo =  failUserinfo + "抢购完毕，抢购失败";
                System.out.println(kcFailinfo);
                jedis.set(failUserinfo, kcFailinfo);
                return;
            }
        } catch (Exception e){

        } finally {
            jedis.close();
        }

    }
}
