FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER smallchill@163.com

RUN mkdir -p /blade/report

WORKDIR /blade/report

EXPOSE 8108

ADD ./target/blade-report.jar ./blade-report.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "blade-report.jar"]

CMD ["--spring.profiles.active=test"]
