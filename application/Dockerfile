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

# Copy ORAS artifacts to the dedicated folder "/opt/codbex"
COPY oras-artifacts/ /opt/codbex/oras-artifacts/

EXPOSE 80

COPY target/codbex-kronos-application-*-executable.jar codbex-kronos.jar
# PropertiesLauncher (not -jar / JarLauncher) so AOT-compiled module jars dropped into /modules
# join the classpath (eclipse-dirigible/dirigible#6592). A missing or empty /modules is ignored.
ENTRYPOINT ["java","--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "--add-opens", "java.base/java.nio=ALL-UNNAMED", "-cp", "/codbex-kronos.jar", "-Dloader.path=/modules", "org.springframework.boot.loader.launch.PropertiesLauncher"]
