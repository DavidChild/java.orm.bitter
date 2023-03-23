package io.github.davidchild.bitter.cache;

import io.github.davidchild.bitter.cache.cache.CacheEnum;
import io.github.davidchild.bitter.init.BitterConfig;
import io.github.davidchild.bitter.tools.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
class BitterCacheUtils {
    static boolean checkedLocalCache() {
        boolean enable = BitterConfig.getInstance().getCache() != null && BitterConfig.getInstance().getCache().isEnabledLocalCache();
        if (enable) {
            if (BitterConfig.getInstance().getCache().getCaffeine() == null) {
                log.error("pls setting bitter.cache.caffeine in your config or yaml");
                enable = false;
            }
        }
        return enable;
    }

    // 检查分布式缓存是否开启
    static boolean checkedRemoteCache() {
        boolean enable = BitterConfig.getInstance().getCache() != null && BitterConfig.getInstance().getCache().isEnabledRedis();
        if (enable) {
            if (BitterConfig.getInstance().getCache().getRedis() == null) {
                log.error("pls setting bitter.cache.caffeine in your config or yaml,if not, the cache is not enabled.");
                enable = false;
            }
        }
        return enable;
    }


    static <T> T getObjectByCache(Class<T> tagetClass, String key) {
        //是否开启本地缓存
        try {
            if (checkedLocalCache()) {

            }
            if (checkedRemoteCache()) {
                String jsonStr = RedisCache.getValue(key);
                return JsonUtil.string2Object(jsonStr, tagetClass);
            }
        } catch (Exception ex) {
            log.error("getObjectByCache is error that key is:" + key);
        }
        return null;
    }

    static void setObjectByCache(CacheEnum cacheEnum, Object value, String key, long expireTime) {
        // counter the  time span for cache
        if (CacheEnum.CacheSlither == cacheEnum) {
            expireTime = (expireTime / 1000);
        } else {
            expireTime = expireTime - new Date().getTime();
            if (expireTime <= 0) expireTime = 0;
            expireTime = expireTime / 1000;
        }
        try {
            if (checkedLocalCache()) {

            }
            if (checkedRemoteCache()) {
                RedisCache.setValue(key, JsonUtil.object2String(value), (int) expireTime);
            }
        } catch (Exception ex) {
            log.error("setObjectByCache is error that params is: key" + key + " expireTime:" + expireTime + " cacheEnum:" + cacheEnum.toString() + ex.getMessage());
        }

    }

    static boolean checkedExistingByKey(String key) {
        boolean bl = false;
        try {
            if (checkedLocalCache()) {

            }
            if (checkedRemoteCache()) {
                bl = RedisCache.haveTheKey(key);
            }
        } catch (Exception ex) {
            log.error("checkedExistingByKey is error " + ex.getMessage());
        }
        return bl;
    }


}
