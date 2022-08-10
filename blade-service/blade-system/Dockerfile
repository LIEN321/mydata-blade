FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER smallchill@163.com

RUN mkdir -p /blade/system

WORKDIR /blade/system

EXPOSE 8106

ADD ./target/blade-system.jar ./blade-system.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "blade-system.jar"]

CMD ["--spring.profiles.active=test"]
