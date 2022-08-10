FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER smallchill@163.com

RUN mkdir -p /blade/admin

WORKDIR /blade/admin

EXPOSE 7002

ADD ./target/blade-admin.jar ./blade-admin.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "blade-admin.jar"]

CMD ["--spring.profiles.active=test"]
