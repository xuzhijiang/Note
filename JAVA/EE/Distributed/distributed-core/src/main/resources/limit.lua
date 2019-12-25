-- lua下标从1开始
-- 限流 key(RedisLimit中是使用当前的时间的秒，来限制每秒的流量)
local key = KEYS[1]

-- 限流大小(限流阈值)
local limit = tonumber(ARGV[1])

-- 获取当前流量大小
local curentLimit = tonumber(redis.call('get', key) or "0")

if curentLimit + 1 > limit then
    -- 达到限流大小 返回0(也就是对应RedisLimit中的静态变量FAIL_CODE)
    return 0;
else
    -- 没有达到阈值 value + 1
    redis.call("INCRBY", key, 1)
    -- EXPIRE key seconds
    redis.call("EXPIRE", key, 2)
    return curentLimit + 1
end