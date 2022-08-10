FROM swr.cn-east-3.myhuaweicloud.com/mydata/alpine-java:openjdk8-openj9_cn_slim

MAINTAINER mydata_work@163.com

RUN mkdir -p /mydata/mydata-manage

WORKDIR /mydata/mydata-manage

EXPOSE 8106

ADD ./target/mydata-manage.jar ./mydata-manage.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "mydata-manage.jar"]

CMD ["--spring.profiles.active=test"]
