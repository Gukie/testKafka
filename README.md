# testKafka
kafka java demo

---
##code refer:
https://my.oschina.net/cloudcoder/blog/299215
http://kelgon.iteye.com/blog/2287985

---

##主要步骤为:
1. 安装并启动 zookeeper
2. 安装并启动 Kafka
3. 编写Java code
4. 遇到的问题

---

##1. 安装并启动 zookeeper

> 
1.1. 下载安装包 并 解压
1.2. 将conf/zoo_sample.cnf文件修改为 conf/zoo.cnf 
1.3. 修改 conf/zoo.cnf 文件，比如将 dataDir 修改为自己想要的目录
1.4. 启动   
```
bin/zkServer.sh start
``` 
    
conf/zoo.cfg 某些参数的解读:
```
server.0=10.0.0.100:4001:4002 
server.1=10.0.0.101:4001:4002 
server.2=10.0.0.102:4001:4002
```
    
server.0/1/2为zk集群中三个node的信息，定义格式为hostname:port1:port2，其中:
> 
port1是node间通信使用的端口，
port2是node选举使用的端口，需确保三台主机的这两个端口都是互通的

---
##2. 安装并启动 Kafka

2.1. 下载 kafka 安装包，并解压
2.2. 修改 config/server.properties 文件以满足自己的需求
2.3.启动   
```    
sudo bin/kafka-server-start.sh config/server.properties &
```


---
##3. 编写Java code

参考代码

---
##4. 遇到的问题

###4.1. 启动 Kafka的时候，会遇到如下问题:
```
/bin/kafka-run-class.sh: 第 258 行: exec: java: 未找到
```

查看了 kafka-run-class.sh 的源码之后，发现这是由于 JAVA_HOME没有被读取到的问题

解决方式:
在 kafka-run-class.sh 文件的最前面加上:
```
export JAVA_HOME=your java home dir
```

###4.2. 调试程序的时候遇到以下的问题

```
org.apache.kafka.common.errors.TimeoutException: Expiring 1 record(s) for page_visits-0 due to 30050 ms has passed since batch creation plus linger time
```

这个问题的原因是:
> 
Cluster 在 request 跟 response的时候，是不一样的
比如Request时候，是: 192.168.158.204:9092
当response的时候，却是: localhost:9092

以下是debug的截图:

Request：

![request code][1]

![request cluster][2]


Response：

![response code][3]

![response cluster][4]


解决方案是: 在server.properties 中添加以下属性

```
advertised.host.name=192.168.158.204
```


原因是：
如果没有指定，Kafka会将从 java.net.InetAddress.getCanonicalHostName() 获取

```
# Hostname and port the broker will advertise to producers and consumers. If not set, 
# it uses the value for "listeners" if configured.  Otherwise, it will use the value
# returned from java.net.InetAddress.getCanonicalHostName().
#advertised.listeners=PLAINTEXT://your.host.name:9092
advertised.host.name=192.168.158.204
#advertised.port=9092
```





  [1]: http://oksd56xj3.bkt.clouddn.com/request_cluster_code.png
  [2]: http://oksd56xj3.bkt.clouddn.com/request_cluster.png
  [3]: http://oksd56xj3.bkt.clouddn.com/response_code.png
  [4]: http://oksd56xj3.bkt.clouddn.com/response_cluster.png