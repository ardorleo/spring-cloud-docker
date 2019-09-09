#!/usr/bin/env bash

#删除原有容器
docker container rm -f $(docker ps -a | grep "api-gateway*"  | awk '{print $1}')

#重新启动一个容器
docker run -d -p 9000:9000 --name api-gateway api-gateway:latest
