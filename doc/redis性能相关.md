# info
#microsecond 1s=1,000,000microsecond
slowlog-log-slower-than 0
slowlog-max-len 10
#CONFIG SET slowlog-log-slower-than 1000
info 
info memory

# 日志
redis日志级别分为debug，verbose，notice，warning。在redis.conf配置文件中，通过loglevel配置选项来配置redis的日志级别。例如 loglevel debug。其中warning的级别最高。在源码中，通过四个宏定义来定义此四个级别。
#define REDIS_DEBUG 0
#define REDIS_VERBOSE 1
#define REDIS_NOTICE 2
#define REDIS_WARNING 3
 
void redisLog(int level, const char *fmt, ...)
redis在调用redislog函数后，首先判断level级别是否低于所配置的日志级别，如果低于，则不记录日志。如果高于所设置的日志级别，则记录日志。源码如下：
if (level < server.verbosity) return;

  1. logfile "/usr/redis/log/redis.log"#日志保存路径  
  
##redis客户端连接数
    ##redis通过监听一个TCP端口或socket的方式接收来自客户端的连接，当与客户端建立连接后，redis内部会进行如下操作：（1）客户端socket会被设置为非阻塞模式，因为redis在网络时间处理上采用的是非阻塞多路复用模型；（2）然后为这个socket设置TCP_NODELAY属性，禁用Nagle算法；（3）然后创建一个可读的文件事件用于监听这个客户端socket的数据发送。
 
##redis最大连接数
##（1.1）2.6之后版本，可以修改最大连接数配置，默认10000，可以在redis.conf配置文件中修改
...
# maxclients 10000
...
##（1.2）启动redis.service服务时加参数--maxclients 100000来设置最大连接数限制
redis-server --maxclients 100000 -f /etc/redis.conf
##命令行查看 & 修改稿redis最大连接数
127.0.0.1:6379> CONFIG GET maxclients
    ##1) "maxclients"
    ##2) "10000"
127.0.0.1:6379>
 
##redis-cli命令控制行中获取客户端信息命令
CLIENT LIST获取客户端列表
CLIENT SETNAME    设置当前连接点redis的名称
CLIENT GETNAME    查看当前连接的名称
CLIENT KILL ip:port    杀死指定连接
CLIENT LIST
    ##id=3 addr=127.0.0.1:36588 fd=5 name= age=7 idle=0 flags=N db=0 sub=0 psub=0 multi=-1 qbuf=0 qbuf-free=32768 obl=0 oll=0 omem=0 events=r cmd=client
CLIENT SETNAME js
    ##OK
CLIENT LIST
    ##id=3 addr=127.0.0.1:36588 fd=5 name=js age=37 idle=0 flags=N db=0 sub=0 psub=0 multi=-1 qbuf=0 qbuf-free=32768 obl=0 oll=0 omem=0 events=r cmd=client
CLIENT GETNAME
    ##"js"
CLIENT KILL id 3
    ##(integer) 0
