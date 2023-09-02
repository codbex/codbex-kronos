FROM amazoncorretto:20
VOLUME /tmp
COPY target/*.jar codbex-kronos.jar
ENTRYPOINT ["java","--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "-jar", "/codbex-kronos.jar"]
