FROM ghcr.io/codbex/kronos-kyma-runtime-base AS base

ENV DIRIGIBLE_HOME_URL=/services/v4/web/
ENV CATALINA_HOME=/usr/local/tomcat

ENV CATALINA_OPTS="--add-opens java.base/java.lang.reflect=ALL-UNNAMED"

EXPOSE 8080

CMD ["/usr/local/tomcat/bin/catalina.sh","run"]