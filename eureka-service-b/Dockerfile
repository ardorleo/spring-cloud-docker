FROM java:8u102

LABEL MAINTAINER=wei

#目录不存在会自动创建
WORKDIR /home

ADD target/eureka-service-b-0.0.1-SNAPSHOT.jar /home

ENV JAVA_OPT="-Xms256m -Xmx256m"

ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPT} -jar eureka-service-b-0.0.1-SNAPSHOT.jar" ]