package io.github.davidchild.bitter.cache;

import io.github.davidchild.bitter.init.BitterConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * the remote cache,base redis
 */

class RedisBaseCache {


    private static JedisPoolConfig jedisPoolConfig;
    private static JedisPool jedisPool;


    private static String ip = BitterConfig.getInstance().getCache().getRedis().getHosts().get(0).getIp();

    protected static Integer dbIndex = BitterConfig.getInstance().getCache().getRedis().getDatabase();
    private static int port = BitterConfig.getInstance().getCache().getRedis().getHosts().get(0).getPort();

    private static int timeout = BitterConfig.getInstance().getCache().getRedis().getTimeout();

    private static int maxWait = BitterConfig.getInstance().getCache().getRedis().getLettuce().getPool().getMaxWait();

    private static int maxIdle = BitterConfig.getInstance().getCache().getRedis().getLettuce().getPool().getMaxIdle();

    private static int maxTotal = BitterConfig.getInstance().getCache().getRedis().getLettuce().getPool().getMaxIdle();

    private static String password = BitterConfig.getInstance().getCache().getRedis().getPassword();


    protected static JedisPool getJedisPool() {
        if (jedisPool != null) {
            return jedisPool;
        }
        if (jedisPoolConfig == null) {
            jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(maxTotal);
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWait);
            jedisPoolConfig.setTestOnBorrow(false);//jedis 第一次启动时，会报错
            jedisPoolConfig.setTestOnReturn(true);
        }
        if (password == null || "".equals(password))
            jedisPool = new JedisPool(jedisPoolConfig, ip, port, timeout);
        else
            jedisPool = new JedisPool(jedisPoolConfig, ip, port, timeout, password);
        return jedisPool;
    }


}