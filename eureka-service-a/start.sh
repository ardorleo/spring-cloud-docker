#!/usr/bin/env bash

#删除原有容器
docker container rm -f $(docker ps -a | grep "eureka-service-a*"  | awk '{print $1}')

#重新启动一个容器
docker run -d -p 8081:8081 --name eureka-service-a eureka-service-a:latest
