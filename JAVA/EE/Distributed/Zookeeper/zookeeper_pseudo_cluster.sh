#!/bin/bash

# 导入环境变量
source ./zookeeper_pseudo_cluster.env

cd $ZOOKEEPER_HOME
log_msg "switch ZOOKEEPER_HOME"

mkdir pseudo_cluster_conf && cd pseudo_cluster_conf
mkdir -p zk1/data zk2/data/ zk3/data
touch zk1/zk1.cfg zk2/zk2.cfg zk3/zk3.cfg
touch zk1/data/myid zk2/data/myid zk3/data/myid
echo 1 > zk1/data/myid
echo 2 > zk2/data/myid
echo 3 > zk3/data/myid
