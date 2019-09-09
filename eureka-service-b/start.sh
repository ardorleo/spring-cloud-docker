#!/usr/bin/env bash

#删除原有容器
docker container rm -f $(docker ps -a | grep "eureka-service-b*"  | awk '{print $1}')

#重新启动一个容器
docker run -d -p 8082:8082 --name eureka-service-b eureka-service-b:latest
