FROM amazoncorretto:21-alpine

VOLUME /tmp

RUN apk update && \
    apk add --no-cache \
    ttyd

RUN apk add nodejs npm
RUN npm i -g esbuild
RUN npm i -g typescript
RUN apk add git
RUN apk --no-cache add msttcorefonts-installer fontconfig && update-ms-fonts && fc-cache -f

# Copy ORAS artifacts to the dedicated folder "/codbex"
COPY oras-artifacts/ /codbex/oras-artifacts/

EXPOSE 80

COPY target/codbex-kronos-application-*-executable.jar codbex-kronos.jar
ENTRYPOINT ["java","--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "--add-opens", "java.base/java.nio=ALL-UNNAMED", "-jar", "/codbex-kronos.jar"]
