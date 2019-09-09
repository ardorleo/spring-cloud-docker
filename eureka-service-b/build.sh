#!/usr/bin/env bash

#重新打jar包
mvn package

#删除原有镜像
docker image rm -f eureka-service-b:latest

#构建镜像
docker build -t eureka-service-b:latest .

#调用启动脚本
#exec ./start.sh

