# 1) Gera o front-end React (vai para src/main/resources/static)
FROM node:22-alpine AS front
WORKDIR /app/frontend
COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci
COPY frontend/ ./
RUN npm run build

# 2) Gera o .jar do Spring Boot já com o front dentro
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
COPY --from=front /app/src/main/resources/static ./src/main/resources/static
RUN chmod +x mvnw && ./mvnw package -DskipTests

# 3) Imagem final, só com o Java e o .jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
