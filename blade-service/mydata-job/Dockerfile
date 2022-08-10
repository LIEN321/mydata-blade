FROM swr.cn-east-3.myhuaweicloud.com/mydata/alpine-java:openjdk8-openj9_cn_slim

MAINTAINER mydata_work@163.com

RUN mkdir -p /mydata/mydata-job

WORKDIR /mydata/mydata-job

EXPOSE 8106

ADD ./target/mydata-job.jar ./mydata-job.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "mydata-job.jar"]

CMD ["--spring.profiles.active=test"]
