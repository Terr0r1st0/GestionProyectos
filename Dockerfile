FROM openjdk:17
COPY "./target/GestionProyectos-1-1.jar" "app.jar"
EXPOSE 8047
ENTRYPOINT [ "java", "-jar", "app.jar" ]