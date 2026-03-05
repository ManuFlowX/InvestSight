# Usar una imagen oficial de Java 17 súper ligera
FROM eclipse-temurin:17-jdk-alpine

# Crear un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo .jar que acabamos de compilar hacia el contenedor
COPY target/*.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]