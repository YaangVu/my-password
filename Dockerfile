###
# Build to Jar File
###
FROM gradle:6.7.1-jdk15 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

###
# Run Application
###
FROM openjdk:15-alpine
MAINTAINER YaangVu
LABEL Vendor="YaangVu" email="yaangvu@gmail.com" Version="20201118" Description="Docker file for Java Spring Boot"
#Set TimeZone
ENV TZ=Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]