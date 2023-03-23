package io.github.davidchild.bitter.cache;

import io.github.davidchild.bitter.init.BitterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

class RedisCache extends RedisBaseCache {

    private final static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    protected static Integer dbIndex = BitterConfig.getInstance().getCache().getRedis().getDatabase();
    @Autowired
    private JedisPool jedisPool;

    /**
     * get data by key from cache
     *
     * @returne
     */
    static String getValue(String key) {
        Jedis redis = null;
        try {
            redis = getJedis();
            return redis.get(key);
        } finally {
            if (redis != null) {
                redis.close();
            }
            //logger.debug( key + " redis time ：" + (DateUtil.getCurrentDate().getTime() - startTime));
        }
    }


    static boolean haveTheKey(String key) {
        Jedis redis = null;
        //long startTime = DateUtil.getCurrentDate().getTime();
        try {
            redis = getJedis();
            return redis.exists(key);
        } finally {
            if (redis != null) {
                redis.close();
            }
            //logger.debug( key + " redis time ：" + (DateUtil.getCurrentDate().getTime() - startTime));
        }
    }

    /**
     * get data by key from redis cache
     *
     * @returne
     */
    static String getAndRemoveValue(String key) {
        Jedis redis = null;
        String value = "";
        try {
            redis = getJedis();
            value = redis.get(key);
            removeValue(key);
        } finally {
            if (redis != null) {
                redis.close();
            }
            //logger.debug( key + "getAndRemoveValue redis time：" + (DateUtil.getCurrentDate().getTime() - startTime));
        }
        return value;
    }

    /**
     * save data to cache
     *
     * @param key
     */
    static void setValue(String key, String value, int expireTime) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.select(dbIndex);
            redis.set(key, value);
            redis.expire(key, expireTime);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * save data to cache
     *
     * @param key
     */
    static void setValue(String key, String value) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.select(dbIndex);
            redis.set(key, value);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    static byte[] getValue(byte[] key) {
        Jedis redis = null;
        //long startTime = DateUtil.getCurrentDate().getTime();
        try {
            redis = getJedis();
            redis.select(dbIndex);
            return redis.get(key);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * save data to cache
     *
     * @param key
     */
    static void setValue(byte[] key, byte[] value) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.select(dbIndex);
            redis.set(key, value);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * delete data
     *
     * @param key
     */
    static void removeValue(String key) {
        Jedis redis = getJedis();
        try {
            getJedis().del(key);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * reset cache time
     *
     * @param jdi
     * @param expireTime
     */
    static void resetExpireTime(String jdi, int expireTime) {
        Jedis redis = null;
        try {
            redis = getJedis();
            redis.select(dbIndex);
            redis.expire(jdi, expireTime);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 获取jedis对象，并指定dbIndex
     */
    private static Jedis getJedis() {
        Jedis jedis = getJedisPool().getResource();
        if (dbIndex == null || dbIndex > 15 || dbIndex < 0) {
            dbIndex = 0;
        }
        jedis.select(dbIndex);
        return jedis;
    }

}
