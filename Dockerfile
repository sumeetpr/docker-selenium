FROM bellsoft/liberica-openjdk-alpine:17.0.11

WORKDIR /home/selenium-docker

RUN apk add curl jq

ADD target/docker-resources ./
ADD runner.bat               runner.bat


# start the runner_bk.sh
ENTRYPOINT runner.bat