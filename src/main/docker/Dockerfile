FROM java:8
VOLUME /tmp
EXPOSE 9008
ADD ssp-ms-retail.jar app.jar
ENV SPRING_PROFILES_ACTIVE="dev,docker"
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Xmx512m", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
