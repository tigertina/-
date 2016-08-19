<font face="微软雅黑">
Hadoop学习笔记一

[TOC]
### 1.Hadoop概述
####（1）数据存储问题

MapReduce提供了一种编程模型，抽象出磁盘读写问题，将其转换为计算一个由成对键值组成的数据集。

####（2）MapReduce特点

* 对于非结构话或者半结构化数据非常有效
* 一种线性的可伸缩的编程模型
* 输入的键值并不是数据固有的属性，是由分析数据的人来选择
* 尝试在计算节点本地存储数据，因此数据访问速度会因为它是本地数据而比较快
* 程序员不用考虑失败情况，自检测map或者reduce任务，然后重新跑
* 被设计为那些需要数分钟或者数小时的作业，这些作业在一个聚集带宽很高的数据中心内可信任的专用硬件设备上运行

####（3）MapReduce模型

Reduce函数的输入类型必须与map函数的输出类型相匹配

####（4）MapReduce 新接口

* 新的API使用Mapper和Reducer都是抽象类
* 使用context Object 上下文独享
* 作业控制的执行由Job类负责，而不是JobClient
* MapReduce作业(job)是客户端执行的单位，它包括<font color = 'red'>输入数据，MapReduce程序和配置信息</font>，Hadoop通过把作业分成若干个(task)来工作，其包括两种类型的任务：map任务和reduce任务
* jobtracker和多个tasktracker。jobtracker通过调度任务在tasktracker上运行，来协调所有运行在系统上的作业
* tasktracker运行任务的同时，吧任务报告传送到jobtracker，jobtracker记录着每项任务的整体进展情况。如果其中一个任务失败，jobtracker可以重新调度任务到另一个tasktracker
* Hadoop把输入数据按照指定规则（setInputFormat）进行分片，发送到MapReduce，Hadoop为每个分片（split）创建一个map任务，由它来运行用户自定义的map函数来分析每个分片的记录
* 当map任务的执行节点和输入数据的存储节点是同一个节点，Hadoop性能达到最佳，这就是所谓的data locality optimization，否则需要网络数据传输
* <font color = 'red'>map任务把输出写入到本地硬盘，而不是hdfs，</font>如果该节点上运行的map任务在map输出给reduce任务处理之前崩溃，那么将在另一个节点上重新运行map任务以再次创建map的输出
* 一个单一的reduce任务的输入往往来自于所有mapper的输出，因此，有序map的输出必须通过网络传输到reduce任务运行的节点，并在那里进行合并，然后传递到用户定义的reduce函数中，为了增加可靠性，<font color = 'red'>reduce的输出通常存储在HDFS中。</font>
* reduce任务的数目并不由输入的大小来决定，而是单独具体指定，<font color = 'blue'>（Map任务数由Hadoop自己决定）</font>
* 如果有多个reducer，<font color = 'blue'>map任务会对其输出进行分区，为每个reduce任务创建一个分区(partition)，每个分区包含许多键（及其关联的值），但每个键的记录都在同一分区中！</font>
* 分区可以通过用户定义的<font color = 'blue'>partition</font>来控制，但通常用默认的分区工具。使用的是hash函数来形成“木桶”键值
* <font color = 'blue'>combiner</font>并不能取代reduce函数（因为reduce函数仍然需要处理不同的map给出的相同键记录），但它可帮助减少map和reduce之间的数据传输，正因为此，是否在MapReduce作业中使用combiner需要谨慎考虑
* 每个reducer会把结果写到公共文件夹中一个独立的文件内，这些文件的命名一般是part-nnnn，nnnn是关联到某个reduce人物的partition的id，各个reducer的结果不会再进行排序和merge。

![](http://i.imgur.com/8blEdCi.png)

####（5）MapReduce特性

* 一个节点就是一个机器，但不同类型的节点可以存在一个机器上，比如datanode和tasktracker可以共存在一个节点，但一台机子上不同存在两个同类型的节点。
* jobtracker全局只能一个，tasktracker是一个节点一个
* Hadoop各种节点都市整合在一起的，不需要特殊安装，只需要配置下各机器的角色就可以
* numReduceTasks指的是设置的reducer任务数量，默认值是1，那么任何整数与1相除的余数肯定是0，。也就是说，getPartition(...)方法返回值总是0。也就是Mapper任务的输出总是给一个Reducer任务，最终只输出到一个文件

####（6）Hadoop流与管道

* Hadoop流支持任何可以从标准输入读取和写入到标准输出中的编程语言

####（7）hdfs设计
* hdfs以块为基础
* hdfs思想：<font color = 'blue'>一次写入，多次读取是最高效的</font>
* hdfs以管理者-工作者的模式运行，namenode管理文件系统的命名空间，维护文件系统树以及这个树内所有的文件和索引目录，这种信息将文件永久保存在本地磁盘：<font color = 'blue'>命名空间镜像和编辑日志</font>名称节点也记录着每个文件的每个块所在的数据节点，但它并不永久保存块的位置，<font color = 'blue'>因为这些信息会在系统启动时由数据节点重建</font>。
* HDFS中的文件只有一个写入者，而且写操作总在文件末尾，不支持多个写入者，或在文件任务任意位置进行修改
* HDFS块大小默认为64MB，但是，HDFS中小宇一个块大小的文件不会占据整个块的空间

（8）Hadoop支持的文件系统
![](http://i.imgur.com/0fY9JiZ.png)

<font color = 'blue'>Hadoop除了hdfs还能集成其他文件系统</font>

[TOC]
### 2.Hadoop应用程序接口
####（1）接口
Hadoop是java编写的所有Hadoop文件系统间的相互作用都是由Java API调解的。
####（2）Thrift

[TOC]
### 3.Hadoop文件存取分析
####（1）文件读取剖析
![](http://i.imgur.com/ZeW29Gy.png)

![](http://i.imgur.com/OJxMuno.png)

####（2）文件写入剖析
![](http://i.imgur.com/IqlleRv.png)

![](http://i.imgur.com/HYlAhiI.png)

[TOC]
### 4.Hadoop操作命令
####（1）访问hdfs命令

`hadoop fs -command [filesystem://]/path`可以访问hdfs或者Hadoop支持的其他文件系统

`hadoop dfs -command [filesystem://]/path`

`hdfs dfs -command [filesystem://]/path` 只能访问hdfs

####（2）管理hdfs页面命令
Namenode、jobtracker和tasktracker的端口号分别是 70，30，60
http://192.168.1.2:50030

或者：
`hdfs://namenode:9000`
`hdfs://jobtracker:9001`


