package io.github.davidchild.bitter.cache;

import java.util.List;
import java.util.Map;

/**
 * @author : davidchild
 * @date : 2021/7/19 11:37
 * Description :
 */
public interface ICaffeineProvider {
    /**
     * 赋值
     *
     * @param key   不能为 {@literal null}.
     * @param value 不能为 {@literal null}.
     */
    void set(String key, String value);

    /**
     * 获取值
     *
     * @param key 不能为 {@literal null}.
     * @return 值
     */
    String getIfPresent(String key);

    /**
     * 获取所有的键值对
     *
     * @param key 不能为 {@literal null}.
     * @return 集合
     */
    Map<String, String> getAllIfPresent(List<String> key);

    /**
     * 赋值多个
     *
     * @param map 不能为 {@literal null}.
     */
    void setAll(Map<String, String> map);

    /**
     * 移除单个
     *
     * @param key 不能为 {@literal null}.
     */
    void remove(String key);

    /**
     * 移除单个
     *
     * @param keys 不能为 {@literal null}.
     */
    void remove(List<String> keys);

}
