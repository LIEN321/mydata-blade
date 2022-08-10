FROM swr.cn-east-3.myhuaweicloud.com/mydata/alpine-java:openjdk8-openj9_cn_slim

MAINTAINER mydata_work@163.com

RUN mkdir -p /mydata/mydata-test

WORKDIR /mydata/mydata-test

EXPOSE 8106

ADD ./target/mydata-test.jar ./mydata-test.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "mydata-test.jar"]

CMD ["--spring.profiles.active=test"]
