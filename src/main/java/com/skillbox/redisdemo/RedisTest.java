package com.skillbox.redisdemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static java.lang.System.out;

public class RedisTest {

    private static final int DELETE_SECONDS_AGO = 2;
    
    private static final int RPS = 500;

    private static final int USERS = 1000;

    private static final int SLEEP = 1;

    private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");

    private static void log(int UsersOnline) {
        String log = String.format("[%s] Пользователей онлайн: %d", DF.format(new Date()), UsersOnline);
        out.println(log);
    }

    public static void main(String[] args) throws InterruptedException {

        RedisStorageQueue redis = new RedisStorageQueue();
        redis.init();
        Random random = new Random();
       
        while (true) {
            String user = redis.showNextUser();
            out.println("Показан на главной: " + user);

            if (random.nextInt(10) == 0) {
                int lucky = random.nextInt(20) + 1;
                String luckyUser = "user_" + lucky;
                redis.moveToFront(luckyUser);
                System.out.println("Пользователь оплатил продвижение: " + luckyUser);
            }
            Thread.sleep(SLEEP);
        }
    }
}
