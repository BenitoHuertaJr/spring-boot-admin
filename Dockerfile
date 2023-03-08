FROM openjdk:11-jdk as JDK
FROM maven:3.8.4-jdk-11 as MAVEN

FROM docker/compose:debian-1.29.2

COPY --from=JDK /usr/local/openjdk-11 /usr/local/openjdk-11
ENV JAVA_HOME=/usr/local/openjdk-11
RUN { echo '#/bin/sh'; echo 'echo "$JAVA_HOME"'; } > /usr/local/bin/docker-java-home && chmod +x /usr/local/bin/docker-java-home && [ "$JAVA_HOME" = "$(docker-java-home)" ]
ENV PATH=$JAVA_HOME/bin:$PATH

ARG USER_HOME_DIR="/root"

COPY --from=MAVEN /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY --from=MAVEN /usr/share/maven /usr/share/maven
RUN ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME=/usr/share/maven
ENV MAVEN_CONFIG="$USER_HOME_DIR/.m2"

RUN echo 'JAVA_HOME=/usr/local/openjdk-11'>/root/env \
    && echo 'MAVEN_HOME=/usr/share/maven'>>/root/env \
    && echo 'MAVEN_CONFIG="$USER_HOME_DIR/.m2"'>> /root/env \
    && chmod +x /root/env\
    && source /root/env

WORKDIR /
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run