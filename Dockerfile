FROM arm64v8/eclipse-temurin:17-jdk-focal AS build
WORKDIR /workspace/app
COPY . /workspace/app
RUN chmod +x gradlew
RUN ./gradlew clean build -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*-SNAPSHOT.jar)

FROM arm64v8/eclipse-temurin:17-jdk-focal
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.matomaylla.security.SecurityApplication"]
