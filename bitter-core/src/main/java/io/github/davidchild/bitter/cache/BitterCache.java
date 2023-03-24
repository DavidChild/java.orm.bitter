package io.github.davidchild.bitter.cache;

import io.github.davidchild.bitter.cache.cache.CacheEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class BitterCache {

    private long cacheExpTime;
    private CacheEnum cacheEnum;

    private String baseKey;

    private String id;

    // null 值是否要备缓存,默认被缓存
    private boolean nullCache = true;

    private String getKey() {
        if (id == null || id == "") {
            return baseKey;
        } else {
            return baseKey + ":" + id;
        }
    }


    public BitterCache createCache(long cacheExpTime, CacheEnum cacheEnum) {
        this.cacheExpTime = cacheExpTime;
        this.cacheEnum = cacheEnum;
        return this;
    }

    public BitterCache setCacheKey(String baseKey) {
        this.baseKey = baseKey;
        return this;
    }

    public BitterCache setNullCacheClose(boolean nullCache) {
        this.nullCache = nullCache;
        return this;
    }

    public BitterCache setCacheKey(String baseKey, String id) {
        this.baseKey = baseKey;
        this.id = id;
        return this;
    }


    public <T> T dataFrom(Class<T> targetClass, final Function<BitterCache, T> fn) {
        T data = null;
        try {
            if (this.cacheExpTime <= 0) {
                throw new Exception("bitter cache error,that cacheExpTime is not setting");
            }
            if (getKey() == null || getKey() == "") {
                throw new Exception("bitter cache key error,that cache key is not setting");
            }
            if (getKey() == null || getKey() == "") {
                throw new Exception("bitter cache key error,that cache key is not setting");
            }
            if (nullCache && BitterCacheUtils.checkedExistingByKey(this.getKey())) {
                try {
                    T cacheInfo = BitterCacheUtils.getObjectByCache(targetClass, this.getKey());
                    return cacheInfo;
                } catch (Exception ex) {
                    log.error("get cache object,error msg" + ex.getMessage());
                }
            }
            data = fn.apply(this);
            if (!nullCache && data == null) {
                return null;  // 出来的值为null,不被缓存
            }
            BitterCacheUtils.setObjectByCache(this.cacheEnum, data, this.getKey(), this.cacheExpTime);
        } catch (
                Exception ex) {
            log.error("setObjectByCache,error msg" + ex.getMessage());
        }
        return data;
    }

}
