<font face="微软雅黑">
Hadoop学习笔记二
[TOC]
### 1.组件
####（1）模块分布

####（2）namenode
namenode管理者文件系统的namespace，它维护者文件系统树（filesystem tree）以及文件树中所有的文件和文件夹的元数据（metadata）。管理这些信息的文件有两个，分别是namespace镜像文件和操作日志文件，这些信息被cache在RAM中，当然，这两个文件也会被持久化存储在本地硬盘中。Namenode记录着每个文件中每个块所在的数据节点的位置，但是他并不持久化存储这些信息，因为这些信息会从系统启动时候从数据节点重建。

Namenode结构抽象图为：

![](http://i.imgur.com/xGqqYkJ.png)

客户端代表用户与Namenode和datanode交互来访问文件系统。

Hadoop两种容错机制

* 将持久化存储在本地磁盘的文件系统元数据备份。<font color = 'blue'><Hadoop可以通过配置Namenode将他的持久化状态文件写到不同的文件系统中，这些写操作是同步并且原子化的。</font>
* 运行辅助的Namenode(secondary Namenode)。但是并不能作为Namenode，它的主要作用是定期将namespace镜像文件和操作日志文件合并，以防止操作文件日志变得过大。通常secondary Namenode运行在一个单独的物理机上，因为合并操作需要占用大量的CPU时间以及和Namenode相当的内存。保存合并后的namespace备份。

####（3）datanode
文件系统的工作节点，根据客户端或者Namenode的调度存储和检索数据，并且定期向Namenode发送他们所存储的块（block）列表。

集群中的每个服务器都运行在一个datanode后台程序，这个后台程序负责把hdfs数据块度写到本地的文件系统。

####（7）Namenode和secondary Namenode通讯机制
客户端进行写操作，Namenode会在edit log记录下来，并在内存中保存一份文件系统的元数据。

secondary Namenode的处理，试讲fsimage和edites文件周期合并，不会造成Namenode重启时造成长时间不可访问的情况


####（8）jobClient
![](http://i.imgur.com/0V3opas.png)

![](http://i.imgur.com/OTOmjdz.png)

####（9）Jobtracker
<font color = 'blue'>客户端的JobClient把作业的所有相关信息都保存到了jobtracker的系统目录下。</font>jobclient向jobtracker正式提交作业时只传给它一个该作业的jobId。

####（10）tasktracker

每个tasktracker可以产生多个java虚拟机（JVM），用于并行处理多个map以及reduce任务


####（11）tasktracker内部设计与实现
<font color = 'blue'>tasktracker节点作为工作节点不仅要和jobtracker节点进行频繁的交互来获取任务的任务并负责在本地执行他们，而且也要和其他的tasktracker节点交互来协同完成同一个作业。</font>

[TOC]
### 2.数据类型
####（1）Hadoop数据类型
* booleanWritable：标准布尔型数值
* ByteWritable：单字节数值
* DoubleWritable：双字节数
* FloatWritable：浮点数
* IntWritable：整型数
* LongWritable：长整形数
* Text：使用UTF8格式存储的文本
* NullWritable：当<key,value>中的key或value为空的时使用

[TOC]
### 3.核心思想
分而治之，始终以<key,value>为处理单元

[TOC]
### 4.Apache项目

* pig 一种数据流语言和运行环境，用于检索非常大的数据集，pig运行在MapReduce和HDFS的集群上
* HBase 一个分布式地列存储数据库
* Zookeeper 一个分布式地、高可用的协调服务。Zookeeper提供分布式锁之类的基本服务用于构建分布式应用
* Hive 分布式数据仓库，Hive管理HDFS中存储的数据，并提供基于SQL的查询语言（由运行时引擎翻译成MapReduce作业）用以查询数据