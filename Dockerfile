FROM openjdk:11

ARG UID=1937
ARG GID=1937
ARG USERNAME=place2be
ARG GROUPNAME=place2be

ENV HOMEDIR=/home/java
ENV ARTIFACT=place2be-server-0.0.1-SNAPSHOT.jar

RUN groupadd --gid ${GID} ${GROUPNAME} && \
   useradd --uid ${UID} --gid ${GROUPNAME} --home-dir ${HOMEDIR} --shell /bin/bash ${USERNAME}

WORKDIR ${HOMEDIR}

COPY docker-entrypoint.sh .
COPY target/*.jar ${ARTIFACT}

RUN chown -R ${USERNAME}:${GROUPNAME} ${HOMEDIR} && \
    chmod -R o-rwx ${HOMEDIR} && \
    chown ${USERNAME}:${GROUPNAME} docker-entrypoint.sh && \
    chmod 700 docker-entrypoint.sh && \
    chown ${USERNAME}:${GROUPNAME} ${ARTIFACT} && \
    chmod 700 ${ARTIFACT}

USER ${USERNAME}

EXPOSE  8080

ENTRYPOINT [ "./docker-entrypoint.sh" ]
