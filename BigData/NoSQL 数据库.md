<font face="微软雅黑">

####1. Redis

一个开源的，使用C语言编写，面向“键/值”（Key/Value）对类型数据的分布式NoSQL数据库系统。

<font color='red'>特点是高性能，持久存储，适应高并发的应用场景</font>

Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。 它支持多种类型的数据结构，如 字符串（strings）， 散列（hashes）， 列表（lists）， 集合（sets）， 有序集合（sorted sets） 与范围查询， bitmaps， hyperloglogs 和 地理空间（geospatial） 索引半径查询。 Redis 内置了 复制（replication），LUA脚本（Lua scripting）， LRU驱动事件（LRU eviction），事务（transactions） 和不同级别的 磁盘持久化（persistence）， 并通过 Redis哨兵（Sentinel）和自动 分区（Cluster）提供高可用性（high availability）。

支持数据类型：string(字符串)、list(链表)、set(集合)、zset(sorted set --有序集合)和hash（哈希类型）。这些数据类型都支持push/pop、add/remove及取交集并集和差集及更丰富的操作，而且这些操作都是原子性的。

与Memcached一样，为了保证效率，数据都是缓存在内存中。区别的是Redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步（数据可以从主服务器向任意数量的从服务器上同步，从服务器可以是关联其他从服务器的主服务器。）。

<font color='red'>Redis的出现，很大程度补偿了Memcached这类key/value存储的不足，在部分场合可以对关系数据库起到很好的补充作用。</font>

通过`https://github.com/MSOpenTech/redis/tree/2.6` 下载redis2.6，找到bin目录下的对应文件，解压。

　　①redis-server.exe：服务程序，也是最最最核心的一个程序。说到底，<font color='red'>Redis也就是我们服务器系统上一直运行的一个服务甚至就是一个进程而已</font>。 

　　②redis-check-dump.exe：本地数据库检查

　　③redis-check-aof.exe：更新日志检查

　　④redis-benchmark.exe：性能测试，用以模拟同时由N个客户端发送M个 SETs/GETs 查询。上面所提到的测试结果就是使用的该程序来进行的。

　　⑤redis-cli.exe： 服务端开启后，我们的客户端就可以输入各种命令测试了，例如GET、SET等操作；

###2. Memcached

　Memcached 是一个高性能的分布式内存对象缓存系统，用于动态Web应用以减轻数据库负载。它通过在内存中缓存数据和对象来减少读取数据库的次数，从而提高动态、数据库驱动网站的速度。经过多年的发展，目前已经有很多知名的互联网应用使用到了Memcached，比如：Wikipedia、Flickr、Youtube、Wordpress等等。

  （1）Memcached作为高速运行的分布式缓存服务器，

  具有以下的特点：

  协议简单：使用简单的基于文本行的协议，没有使用复杂的XML协议。因此，通过telnet也能在memcached上保存数据、取得数据；

  基于libevent的事件处理：memcached使用这个libevent库，因此能在Linux、BSD、Solaris等操作系统上发挥其高性能；

  libevent是个程序库，它将Linux的epoll、BSD类操作系统的kqueue等事件处理功能 封装成统一的接口。即使对服务器的连接数增加，也能发挥O(1)的性能。
  libevent: http://www.monkey.org/~provos/libevent/

  内置内存存储方式：为了提高性能，memcached中保存的数据都存储在memcached内置的内存存储空间中；

  由于数据仅存在于内存中，因此重启memcached、重启操作系统会导致全部数据消失。 另外，内容容量达到指定值之后，就基于LRU(Least Recently Used)算法自动删除不使用的缓存。memcached本身是为缓存而设计的服务器，因此并没有过多考虑数据的永久性问题。

  NoSQL中比较优秀的一款产品：Redis，比较好地解决了数据持久化的这个问题，重启Redis不会导致数据丢失。

  不互相通信的分布式：尽管是“分布式”缓存服务器，但服务器端并没有分布式功能，这完全取决于客户端的实现。我们会很惊奇的发现memcached的集群非常easy，简单得甚至只需要在客户端的配置文件中添加服务器IP与端口号；换句话说，我们的应用程序只需要将数据请求给memcached客户端，在memcached客户端中会通过一个分布式算法（一致性Hash算法）从memcached服务器列表中计算一个memcached服务器的地址（如果是读请求，则根据Key在分布式算法中得到缓存有该Key的memcached服务器信息），然后客户端将数据（Key/Value对）传递给计算出来的memcached服务器（如果是读请求，则从计算出来的memcached服务器中读取含有指定Key的数据）；

　　（2）Memcached与Redis的对比

　　①没有必要过多的关心性能，因为二者的性能都已经足够高了。由于Redis只使用单核，而Memcached可以使用多核，所以在比较上，平均每一个核上Redis在存储小数据时比Memcached性能更高。而在100k以上的数据中，Memcached性能要高于Redis，虽然Redis最近也在存储大数据的性能上进行优化，但是比起Memcached，还是稍有逊色。说了这么多，结论是，无论你使用哪一个，每秒处理请求的次数都不会成为瓶颈。（比如瓶颈可能会在网卡）

　　②如果要说内存使用效率，使用简单的key-value存储的话，Memcached的内存利用率更高，而如果Redis采用hash结构来做key-value存储，由于其组合式的压缩，其内存利用率会高于Memcached。当然，这和你的应用场景和数据特性有关。

　　③如果你对数据持久化和数据同步有所要求，那么推荐你选择Redis，因为这两个特性Memcached都不具备。即使你只是希望在升级或者重启系统后缓存数据不会丢失，选择Redis也是明智的。

　　因此，我们可以得出一个结论：在简单的Key/Value应用场景（例如缓存），Memcached拥有更高的读写性能；而在数据持久化和数据同步场景，Redis拥有更加强大的功能和更为丰富的数据类型；