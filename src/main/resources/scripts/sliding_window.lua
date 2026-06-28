local limit = tonumber(ARGV[1])
local windowSizeMillis = tonumber(ARGV[2])
local now = tonumber(ARGV[3])
local ttlSeconds = tonumber(ARGV[4])

local currentCount = tonumber(redis.call('GET', KEYS[1]) or "0")
local previousCount = tonumber(redis.call('GET', KEYS[2]) or "0")

local elapsed = now%windowSizeMillis
local weight = (windowSizeMillis-elapsed)/windowSizeMillis
local estimatedCount = previousCount*weight + currentCount

if estimatedCount<limit then
    redis.call('INCR', KEYS[1])
    redis.call('EXPIRE', KEYS[1], ttlSeconds)
    return 1
else
    return 0
end


