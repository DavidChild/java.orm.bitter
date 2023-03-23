package io.github.davidchild.bitter.cache.impl;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.davidchild.bitter.cache.ICaffeineProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * @author : davidchild
 * @date : 2021/7/19 11:37
 * Description :
 */
@Component
@Slf4j
public class CaffeineProviderImpl implements ICaffeineProvider {

    @Qualifier("biiterCaffeineCacheTemplate")
    @Autowired
    private Cache<String, String> caffeineTemplate;


    @Override
    public void set(String key, String value) {
        caffeineTemplate.put(key, value);
    }

    @Override
    public String getIfPresent(String key) {
        return caffeineTemplate.getIfPresent(key);
    }

    @Override
    public Map<String, String> getAllIfPresent(List<String> key) {
        return caffeineTemplate.getAllPresent(key);
    }

    @Override
    public void setAll(Map<String, String> map) {
        caffeineTemplate.putAll(map);
    }

    @Override
    public void remove(String key) {
        caffeineTemplate.invalidate(key);
    }

    @Override
    public void remove(List<String> keys) {
        if (keys == null || keys.size() == 0) {
            return;
        }
        caffeineTemplate.invalidateAll(keys);
    }

}
