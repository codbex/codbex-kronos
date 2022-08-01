FROM ghcr.io/codbex/kronos-kyma-runtime as base

ENV CNB_USER_ID=1000
ENV CNB_GROUP_ID=1000
ENV CNB_STACK_ID="com.codbex.kronos"
LABEL io.buildpacks.stack.id="com.codbex.kronos"

RUN groupadd kronos --gid ${CNB_GROUP_ID} && \
  useradd --uid ${CNB_USER_ID} --gid ${CNB_GROUP_ID} -m -s /bin/bash kronos
RUN chown -R kronos:kronos /usr/local/tomcat

FROM base as run

USER ${CNB_USER_ID}:${CNB_GROUP_ID}
RUN chown -R kronos:kronos /usr/local/tomcat

FROM base as build

USER ${CNB_USER_ID}:${CNB_GROUP_ID}
RUN chown -R kronos:kronos /usr/local/tomcat