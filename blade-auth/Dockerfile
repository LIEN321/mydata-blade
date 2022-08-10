FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER smallchill@163.com

RUN mkdir -p /blade/auth

WORKDIR /blade/auth

EXPOSE 8010

ADD ./target/blade-auth.jar ./blade-auth.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "blade-auth.jar"]

CMD ["--spring.profiles.active=test"]
