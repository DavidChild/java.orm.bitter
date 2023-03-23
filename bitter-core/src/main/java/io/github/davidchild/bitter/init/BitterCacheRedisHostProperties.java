package io.github.davidchild.bitter.init;

import lombok.Data;

@Data
public class BitterCacheRedisHostProperties {
    private String ip;
    private Integer port;
}
