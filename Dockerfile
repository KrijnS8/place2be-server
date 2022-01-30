FROM    openjdk:11

ENV     ARTIFACT=place2be-server-0.0.2-SNAPSHOT.jar
ENV     USER=place2b
ENV     HOMEDIR=/user/${USER}

RUN     groupadd ${USER} && \
        useradd --gid ${USER} --home-dir ${HOMEDIR} --shell /bin/bash ${USER}

WORKDIR ${HOMEDIR}

COPY    docker-entrypoint.sh .
COPY    target/${ARTIFACT} .

RUN     chown ${USER}:${USER} docker-entrypoint.sh && \
        chmod 700 docker-entrypoint.sh && \
        chown ${USER}:${USER} ${ARTIFACT} && \
        chmod 700 ${ARTIFACT}

EXPOSE  8080

ENTRYPOINT [ "./docker-entrypoint.sh" ]
