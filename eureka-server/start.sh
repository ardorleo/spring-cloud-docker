#!/usr/bin/env bash

#删除原有容器
docker container rm -f $(docker ps -a | grep "eureka-server*"  | awk '{print $1}')

#重新启动一个容器
docker run -d -p 8761:8761 --name eureka-server eureka-server:latest
