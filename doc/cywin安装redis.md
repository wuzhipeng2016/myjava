# cygwin安装redis

cygwin下编译http://blog.csdn.net/shan165310175/article/details/45769065#
1.修改/usr/include/netinet/tcp.h文件
在后边添加这几个宏：
#ifndef TCP_KEEPIDLE
#define TCP_KEEPIDLE	 4	/* Start keeplives after this period */
#endif
#ifndef TCP_KEEPINTVL
#define TCP_KEEPINTVL	 5	/* Interval between keepalives */
#endif
#ifndef TCP_KEEPCNT
#define TCP_KEEPCNT 6	/* Number of keepalives before death */
#endif
2.修改\redis-3.0.1\src\fmacros.h 文件
在前面加入这一个宏：
/* Cygwin Fix */   
#ifdef __CYGWIN__   
#ifndef SA_ONSTACK   
#define SA_ONSTACK 0x08000000   
#endif   
#endif
3.修改redis-3.0.1\src\Makefile文件
使用#注释掉： FINAL_LDFLAGS+= -rdynamic

$ wget http://download.redis.io/releases/redis-3.2.5.tar.gz
$ tar xzf redis-3.2.5.tar.gz
$ cd redis-3.2.5
$ make
$ src/redis-server
$ src/redis-cli

http://redis.io/download
http://doc.redisfans.com/
Key（键）
  ● DEL
  ● DUMP
  ● EXISTS
  ● EXPIRE
  ● EXPIREAT
  ● KEYS
  ● MIGRATE
  ● MOVE
  ● OBJECT
  ● PERSIST
  ● PEXPIRE
  ● PEXPIREAT
  ● PTTL
  ● RANDOMKEY
  ● RENAME
  ● RENAMENX
  ● RESTORE
  ● SORT
  ● TTL
  ● TYPE
  ● SCAN
String（字符串）
  ● APPEND
  ● BITCOUNT
  ● BITOP
  ● DECR
  ● DECRBY
  ● GET
  ● GETBIT
  ● GETRANGE
  ● GETSET
  ● INCR
  ● INCRBY
  ● INCRBYFLOAT
  ● MGET
  ● MSET
  ● MSETNX
  ● PSETEX
  ● SET
  ● SETBIT
  ● SETEX
  ● SETNX
  ● SETRANGE
  ● STRLEN
Hash（哈希表）
  ● HDEL
  ● HEXISTS
  ● HGET
  ● HGETALL
  ● HINCRBY
  ● HINCRBYFLOAT
  ● HKEYS
  ● HLEN
  ● HMGET
  ● HMSET
  ● HSET
  ● HSETNX
  ● HVALS
  ● HSCAN
List（列表）
  ● BLPOP
  ● BRPOP
  ● BRPOPLPUSH
  ● LINDEX
  ● LINSERT
  ● LLEN
  ● LPOP
  ● LPUSH
  ● LPUSHX
  ● LRANGE
  ● LREM
  ● LSET
  ● LTRIM
  ● RPOP
  ● RPOPLPUSH
  ● RPUSH
  ● RPUSHX
Set（集合）
  ● SADD
  ● SCARD
  ● SDIFF
  ● SDIFFSTORE
  ● SINTER
  ● SINTERSTORE
  ● SISMEMBER
  ● SMEMBERS
  ● SMOVE
  ● SPOP
  ● SRANDMEMBER
  ● SREM
  ● SUNION
  ● SUNIONSTORE
  ● SSCAN
SortedSet（有序集合）
  ● ZADD
  ● ZCARD
  ● ZCOUNT
  ● ZINCRBY
  ● ZRANGE
  ● ZRANGEBYSCORE
  ● ZRANK
  ● ZREM
  ● ZREMRANGEBYRANK
  ● ZREMRANGEBYSCORE
  ● ZREVRANGE
  ● ZREVRANGEBYSCORE
  ● ZREVRANK
  ● ZSCORE
  ● ZUNIONSTORE
  ● ZINTERSTORE
  ● ZSCAN
Pub/Sub（发布/订阅）
  ● PSUBSCRIBE
  ● PUBLISH
  ● PUBSUB
  ● PUNSUBSCRIBE
  ● SUBSCRIBE
  ● UNSUBSCRIBE
Transaction（事务）
  ● DISCARD
  ● EXEC
  ● MULTI
  ● UNWATCH
  ● WATCH
Script（脚本）
  ● EVAL
  ● EVALSHA
  ● SCRIPT EXISTS
  ● SCRIPT FLUSH
  ● SCRIPT KILL
  ● SCRIPT LOAD
Connection（连接）
  ● AUTH
  ● ECHO
  ● PING
  ● QUIT
  ● SELECT
Server（服务器）
  ● BGREWRITEAOF
  ● BGSAVE
  ● CLIENT GETNAME
  ● CLIENT KILL
  ● CLIENT LIST
  ● CLIENT SETNAME
  ● CONFIG GET
  ● CONFIG RESETSTAT
  ● CONFIG REWRITE
  ● CONFIG SET
  ● DBSIZE
  ● DEBUG OBJECT
  ● DEBUG SEGFAULT
  ● FLUSHALL
  ● FLUSHDB
  ● INFO
  ● LASTSAVE
  ● MONITOR
  ● PSYNC
  ● SAVE
  ● SHUTDOWN
  ● SLAVEOF
  ● SLOWLOG
  ● SYNC
  ● TIME

