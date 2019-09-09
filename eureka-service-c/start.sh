#!/usr/bin/env bash

#删除原有容器
docker container rm -f $(docker ps -a | grep "eureka-service-c*"  | awk '{print $1}')

#重新启动一个容器
docker run -d -p 8083:8083 --name eureka-service-c eureka-service-c:latest
