package com.jiang.utils;

/**
 * Snowflake ID Generator
 * 参考Twitter的Snowflake算法实现分布式唯一ID生成器
 * 结构: 1位符号位 + 41位时间戳 + 5位数据中心ID + 5位机器ID + 12位序列号
 * 总共64位，Java的long类型
 * @author jiang
 */
public class SnowflakeIdConfig {
    // ==============================字段==============================
    private final long workerId;          // 机器ID
    private final long datacenterId;      // 数据中心ID
    private long sequence = 0L;           // 序列号

    // 配置参数
    private static final long MAX_WORKER_ID = 37L;
    private static final long MAX_DATACENTER_ID = 37L;
    private static final long TIMESTAMP_BITS = 41L; // 时间戳位数
    // 时间戳最大值，使用~(-1L << TIMESTAMP_BITS)计算
    // 计算方式：将-1左移41位，然后取反，得到一个
    // 41位全1的二进制数，转换为十进制即为
    // 2199023255551L，表示从1970年1月1日
    // 到2199023255551毫秒的时间戳范围
    // 这个时间戳范围是从1970年1月1日到
    // 2199023255551毫秒的时间戳范围
    // 计算方式：将-1左移41位，然后取反，得到一个
    // 41位全1的二进制数，转换为十进制即为
    // 2199023255551L，表示从1970年1月1日
    // 到2199023255551毫秒的时间戳范围
    // 这个时间戳范围是从1970年1月1日到
    // 2199023255551毫秒的时间戳范围
    // 计算方式：将-1左移41位，然后取反，得到一个
    // 41位全1的二进制数，转换为十进制即
    private static final long MAX_TIMESTAMP = ~(-1L << TIMESTAMP_BITS); //
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    // 位移偏移量
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS + DATACENTER_ID_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS;

    private long lastTimestamp = -1L;     // 上次生成ID的时间戳

    // ==============================构造函数==============================
    /**
     * 构造函数
     * @param workerId     机器ID (0 - 31)
     * @param datacenterId 数据中心ID (0 - 31)
     */
    public SnowflakeIdConfig(long workerId, long datacenterId) {
        // 检查workerId和datacenterId是否合法
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("Worker ID must be between 0 and " + MAX_WORKER_ID);
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID must be between 0 and " + MAX_DATACENTER_ID);
        }

        // 设置workerId和datacenterId
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================方法==============================
    /**
     * 生成唯一ID
     * @return 唯一ID
     */
    public synchronized long generateUniqueId() {
        long timestamp = getCurrentTimestamp();

        // 检查时钟回退情况
        // 如果当前时间戳小于上次生成ID的时间戳，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
        }

        // 同一毫秒内自增序列号
        // 如果当前时间戳等于上次生成ID的时间戳，序列号自增
        // 如果当前时间戳不等于上次生成ID的时间戳，
        // 则重置序列号为0
        // 如果序列号达到最大值，则等待下一毫秒
        // 注意：这里的序列号是12位，最大值为4095
        // 通过位运算计算出当前时间戳对应的毫秒数
        // 并与上次生成ID的时间戳进行比较
        // 如果当前时间戳与上次生成ID的时间戳相同，则
        // 序列号自增并进行掩码操作，确保序列号在0到4095之间
        // 如果序列号自增后为0，则等待下一毫秒
        // 如果当前时间戳与上次生成ID的时间戳不同，则
        // 重置序列号为0，表示进入新的毫秒
        // 更新上次生成ID的时间戳为当前时间戳
        // 最后通过位运算组合生成唯一ID
        // 计算当前时间戳对应的毫秒数
        // 并与上次生成ID的时间戳进行比较
        // 如果当前时间戳小于上次生成ID的时间戳，
        // 则抛出异常，表示时钟回退
        // 如果当前时间戳等于上次生成ID的时间戳，
        // 则序列号自增并进行掩码操作，确保序列
        // 号在0到4095之间
        // 如果序列号自增后为0，则等待下一毫秒
        // 如果当前时间戳与上次生成ID的时间戳不同，
        // 则重置序列号为0，表示进入新的毫秒
        // 更新上次生成ID的时间戳为当前时间戳
        // 最后通过位运算组合生成唯一ID
        // 计算当前时间戳对应的毫秒数
        // 并与上次生成ID的时间戳进行比较
        // 如果当前时间戳小于上次生成ID的时间戳，
        // 则抛出异常，表示时钟回退
        // 如果当前时间戳等于上次生成ID的时间戳，
        // 则序列号自增并进行掩码操作，确保序列
        // 号在0到4095之间
        // 如果序列号自增后为0，则等待下一毫秒
        // 如果当前时间戳与上次生成ID的时间戳不同，
        // 则重置序列号为0，表示进入新的毫秒
        // 更新上次生成ID的时间戳为当前时间戳
        // 最后通过位运算组合生成唯一ID
        // 计算当前时间戳对应的毫秒数

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & ((1 << SEQUENCE_BITS) - 1);  // 自增并掩码
            if (sequence == 0) {
                // 序列号用完，等待下一毫秒
                timestamp = getNextTimestamp(lastTimestamp);
            }
        } else {
            sequence = 0L;  // 不同毫秒重置序列号
        }

        lastTimestamp = timestamp;

        // 组合生成唯一ID
        return ((timestamp << TIMESTAMP_SHIFT) |
                (datacenterId << DATACENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence);
    }

    /**
     * 获取当前时间戳
     * @return 当前时间戳（毫秒）
     */
    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 等待下一毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 新的时间戳
     */
    private long getNextTimestamp(long lastTimestamp) {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
        }
        return timestamp;
    }

    // ==============================测试==============================
    public static void main(String[] args) {
        // 创建一个雪花算法生成器，传入机器ID和数据中心ID
        SnowflakeIdConfig idGenerator = new SnowflakeIdConfig(0, 0);
        // 生成10个唯一ID并打印
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                long uniqueId = idGenerator.generateUniqueId();
                System.out.println("Generated Unique ID: " + uniqueId);
            }).start();
        }
    }
}

