FROM ghcr.io/graalvm/jdk-community:21

VOLUME /tmp

# Install dependencies via microdnf (Oracle Linux minimal)
RUN microdnf install -y \
    nodejs \
    # provides xargs used in ABAP transpilation scripts
    findutils \
    git \
    fontconfig \
    curl \
    && microdnf clean all

# Install ttyd
RUN curl -L https://github.com/tsl0922/ttyd/releases/download/1.7.7/ttyd.x86_64 \
    -o /usr/local/bin/ttyd && \
    chmod +x /usr/local/bin/ttyd

RUN npm install -g esbuild typescript

# Copy ORAS artifacts to the dedicated folder "/opt/codbex"
COPY oras-artifacts/ /opt/codbex/oras-artifacts/

EXPOSE 80

WORKDIR /

COPY target/codbex-kronos-application-*-executable.jar codbex-kronos.jar

ENTRYPOINT ["java", \
    "--add-opens", "java.base/java.lang=ALL-UNNAMED", \
    "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", \
    "--add-opens", "java.base/java.nio=ALL-UNNAMED", \
    "-jar", "/codbex-kronos.jar"]
