package com.cx.main;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 事务的一致性，完整性
 * 卡中100元购买，购买一本40元图书的和一个70元书包，先购买
 * 图书，之后在购买书包，余额不足，购买失败。
 * 说明数据库的一致性，数据的完整性
 */
public class MainTransaction01 {
    static Jedis  jedis = new Jedis("127.0.0.1", 6379);
    public static void main(String[] args) {

         // 付款账户余额
        int payBalance = 0;
        // 收款账户余额
        int receiveBalance = 0;
        int bookPrice = 40;
        int bagPrice = 70;
        String goodBook = "图书";
        String goodBag = "书包";
        jedis.set("payBalance", "100");
        System.out.println("购买图书");
        shopping(goodBook, payBalance, bookPrice, receiveBalance);
        System.out.println("购买书包");
        shopping(goodBag, payBalance, bagPrice, receiveBalance);
    }

    public static boolean shopping(String good, int payBalance, int goodPrice, int receiveBalance){
        // watch命令监控payBalance键
        // 如果监听过程中，这个键被其他事务修改发生了变化，那么操作就会取消
        jedis.watch("payBalance"); // 取消监听--exec  discard  unwatch

        payBalance = Integer.valueOf(jedis.get("payBalance"));
        if(payBalance < goodPrice){
            jedis.unwatch();
            System.out.println("余额不足，购买" + good + "失败");
            return false;
        } else {
            Transaction transaction = jedis.multi();
            transaction.decrBy("payBalance", goodPrice);
            transaction.incrBy("receiveBalance", goodPrice);
            transaction.exec();
            System.out.println(jedis.get("payBalance"));
            System.out.println(jedis.get("receiveBalance"));
            return true;
        }


    }
}
