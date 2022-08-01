ARG KRONOS_VERSION=latest
FROM ghcr.io/codbex/codbex-kyma:$KRONOS_VERSION as base

ENV CNB_USER_ID=1001
ENV CNB_GROUP_ID=1001
ENV CNB_STACK_ID="com.codbex.kronos"
LABEL io.buildpacks.stack.id="com.codbex.kronos"

RUN chmod -R 777 /usr/local/tomcat

FROM base as run

RUN chmod -R 777 /usr/local/tomcat

FROM base as build

RUN chmod -R 777 /usr/local/tomcat