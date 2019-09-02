#!/usr/bin/env bash

#重新打jar包
mvn package

#删除原有镜像
docker image rm admin-server:latest

#构建镜像
docker build -t admin-server:latest .

#删除原有容器
docker container rm -f $(docker ps -a | grep "admin-server*"  | awk '{print $1}')

#重新启动一个容器
docker run -d -p 8000:8000 --name admin-server admin-server:latest
