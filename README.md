### 基于Springcloud的微服务解决方案，并将项目容器化

#### 1.版本
+ Springboot:    2.1.7
+ SpringCloud:   Greenwich.SR2
    - Eureka
    - Ribbon
    - Zuul 
    - Feign
+ Springboot admin: 2.1.5
+ Docker:    18.02.0-ce
+ docker-compose:    1.19.0
+ Windows10

#### 2. 模块说明
- admin-server：springcloud微服务的监控模块，可以监控服务的状态、实例数量、内存使用、配置文件等
- api-gateway： 服务的网关，用于服务的统一接入、限流等
- eureka-server： 服务的注册中心，用于服务的注册发现
- eureka-service-a：服务a，服务之间可通过fiegn的方式互相调用
- eureka-service-b：服务b，服务之间可通过fiegn的方式互相调用
- eureka-service-c：服务c，服务之间可通过fiegn的方式互相调用


#### 3.镜像构建
每个子模块下面都有一个Dockerfile和构建镜像的脚本。

Dockerfile:

``` 
FROM java:8u102

LABEL MAINTAINER=wei

#目录不存在会自动创建
WORKDIR /home

ADD target/admin-server-0.0.1-SNAPSHOT.jar /home

ENV JAVA_OPT="-Xms256m -Xmx256m"

ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPT} -jar admin-server-0.0.1-SNAPSHOT.jar" ]
```

build.sh:

```
#!/usr/bin/env bash

#重新打jar包
mvn package

#删除原有镜像
docker image rm -f admin-server:latest

#构建镜像
docker build -t admin-server:latest .

#调用启动脚本
#exec ./start.sh
```
执行这个脚本会将应用打包，并基于此Dockerfille构建一个新的镜像。

#### 4.服务启动
这里的服务编排使用的是docker-compose，这是单机版本的编排工具，更加强大的编排工具应该使用k8s。

docker-compose.yml:
``` 
version: '3'

services:
  eureka-server:                  #服务名
    image: eureka-server:latest   #依赖的镜像
    ports:
      - 8761:8761                 #端口映射，格式：[主机端口：容器端口]

  eureka-service-b:
    image: eureka-service-b:latest
    ports:
      - 8082:8082

  eureka-service-c:
    image: eureka-service-c:latest
    ports:
      - 8083:8083

  eureka-service-a:
    image: eureka-service-a:latest
    links:
      - eureka-service-b
      - eureka-service-c
    ports:
      - 8081:8081

  admin-server:
    image: admin-server:latest
    ports:
      - 8000:8000

  api-gateway:
    image: api-gateway:latest
    ports:
      - 9000:9000
```

- 启动所有容器：
在docker-compose.yml所在的目录执行：docker-compose up -d 
- 启动单个容器：
在docker-compose.yml所在的目录执行：docker-compose up -d 服务名
- 删除容器：
docker rm -f 容器id
- 查看容器日志：
docker logs -f 容器id

#### 5.容器启动后服务的查看和验证
注意，下面的ip 192.168.99.100是docker服务端的ip，注意替换

- eureka注册中心：http://192.168.99.100:8761/
- springboot admin监控中心：http://192.168.99.100:8000

- 测试服务a：http://192.168.99.100:8081/version-a/get
``` 
返回：欢迎访问[service-a]服务，版本是v1.0
```

- 测试服务b：http://192.168.99.100:8082/version-b/get
``` 
返回：欢迎访问[service-b]服务，版本是v1.0
```

- 测试服务c：http://192.168.99.100:8083/version-c/get
``` 
返回：欢迎访问[service-c]服务，版本是v1.0
```

- 测试在服务a中通过fiegn的方式调用服务b和服务c：http://192.168.99.100:8081/version-a/getService
``` 
返回：调用service-b返回的是：[欢迎访问[service-b]服务，版本是v1.0]；调用service-c返回的是：[欢迎访问[service-c]服务，版本是v1.0]
```

- 测试网关：三个服务分别调用的是：

http://192.168.99.100:9000/api/service-a/version-a/get

http://192.168.99.100:9000/api/service-b/version-b/get

http://192.168.99.100:9000/api/service-c/version-c/get
``` 
返回结果和上面测试单个服务的一样
```





