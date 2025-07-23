FROM eclipse-temurin:21-jdk

ENV TINI_VERSION v0.19.0
RUN apt-get update && apt-get install curl -y && \
    curl -sSL https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini --output /usr/sbin/tini && \
    chmod +x /usr/sbin/tini

RUN groupadd -g 10000 -r app && \
    useradd -u 10001 -g app -m -d /home/app -s /sbin/nologin -c "Docker image user" app

ENV HOME=/home/app
WORKDIR $HOME

COPY target/repo-discovery.jar app.jar

COPY --chown=app:app . $HOME
# USER app

EXPOSE 8080
# ENTRYPOINT ["/usr/sbin/tini", "--"]

ENV JAVA_OPTS="-Xmx128m"
CMD java $JAVA_OPTS -jar app.jar
