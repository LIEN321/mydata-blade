FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER smallchill@163.com

RUN mkdir -p /blade/resource

WORKDIR /blade/resource

EXPOSE 8010

ADD ./target/blade-resource.jar ./blade-resource.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "blade-resource.jar"]

CMD ["--spring.profiles.active=test"]
