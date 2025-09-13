package com.skillbox.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RList;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class RedisStorageQueue {

    // Объект для работы с Redis
    private RedissonClient redisson;

    // Объект для работы с ключами
    private RList<String> userQueue;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> onlineUsers;

    private final static String KEY = "USER_QUEUE";


    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        userQueue = redisson.getList(KEY);
        userQueue.clear();;
        for (int i = 1; i <= 20; i++){
            userQueue.add("user_" + i);
        }
    }

    void shutdown() {
        redisson.shutdown();
    }

    public String showNextUser() {
        if (userQueue.isEmpty()) return null;
        String user = userQueue.remove(0); // LPOP
        userQueue.add(user); // RPUSH
        return user;
    }

    public void moveToFront(String userId) {
        userQueue.remove(userId);
        userQueue.add(0, userId); // LPUSH
    }

    public List<String> getQueueSnapshot() {
        return userQueue.readAll();
    }


}
