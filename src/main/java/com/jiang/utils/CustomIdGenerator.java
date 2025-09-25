package com.jiang.utils;


//@Configuration
public class CustomIdGenerator  {

    private final SnowflakeIdConfig snowflakeIdConfig;

    public CustomIdGenerator() {
        // 可以从配置文件或环境变量中读取 workerId 和 datacenterId
        this.snowflakeIdConfig = new SnowflakeIdConfig(1, 1);
    }

    public Long nextId(Object entity) {
        return snowflakeIdConfig.generateUniqueId();
    }
}
