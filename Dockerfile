FROM bellsoft/liberica-openjdk-alpine:17.0.11

WORKDIR /home/selenium-docker

RUN apk add curl jq

ADD target/docker-resources ./
ADD runner.sh               runner.sh


# start the runner.sh
ENTRYPOINT sh runner.sh