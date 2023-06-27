-- 这个lua脚本有问题
function userExists(usersKey, userid, prodid, qtkey)
    local userExist = redis.call("sismember", usersKey, userid)
    if(tonumber(userExist)==1)
    then
       return 2
    end

    local num=redis.call("get",qtkey)
    if(tonumber(num) <= 0)
    then
       return 0
    else
       redis.call("decr",qtkey)
       redis.call("sadd",usersKey,userid)
       return 1
    end
end

local userid=KEYS[1]
local prodid=KEYS[2]
local qtkey="Seckill:Stock:"..prodid
local usersKey="Seckill:User:"..userid

local result = userExists(usersKey, userid, prodid,qtkey)

return result