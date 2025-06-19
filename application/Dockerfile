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

# Install ORAS from GitHub Releases
ENV ORAS_VERSION=1.1.0
RUN curl -sL https://github.com/oras-project/oras/releases/download/v${ORAS_VERSION}/oras_${ORAS_VERSION}_linux_amd64.tar.gz \
    | tar -xz oras && \
    mv oras /usr/local/bin/oras


# Build-time args
ARG ORAS_USERNAME
ARG ORAS_PASSWORD
ARG ORAS_REPOS=""

# Pull repos only if ORAS_REPOS is not empty
RUN mkdir -p "$ORAS_TARGET_DIR" && \
    if [ -n "$ORAS_REPOS" ]; then \
      echo "$ORAS_PASSWORD" | oras login ghcr.io -u "$ORAS_USERNAME" --password-stdin && \
      IFS=','; for repo in $ORAS_REPOS; do \
        echo "Pulling $repo"; \
        oras pull "$repo" -o "$ORAS_TARGET_DIR"; \
      done; \
    else \
      echo "No ORAS_REPOS provided, skipping pull."; \
    fi

EXPOSE 80

COPY target/codbex-kronos-application-*-executable.jar codbex-kronos.jar
ENTRYPOINT ["java","--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "--add-opens", "java.base/java.nio=ALL-UNNAMED", "-jar", "/codbex-kronos.jar"]
