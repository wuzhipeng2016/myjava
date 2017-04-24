#redis安装#

redis-3.2.5.tar.gz
tar xvf redis-3.2.5.tar.gz
cd redis-3.2.5
make

cd /opt/wiseduAppGroups/redis-3.2.5/src
vi ../redis.conf
protected-mode no
bind
requirepass abcdefg
./redis-server ../redis.conf
./redis-serever --protected-mode no
./redis-cli 


redis-cli -h 127.0.0.1 -p 6379
auth abcdefg
select 1
