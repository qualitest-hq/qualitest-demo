# 质衡 Demo 后端（demo-admin）多阶段构建
# 构建上下文：仓库根目录

FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /src

COPY pom.xml .
COPY test-support-starter/pom.xml test-support-starter/
COPY demo-admin/pom.xml demo-admin/
COPY demo-common/pom.xml demo-common/
COPY demo-framework/pom.xml demo-framework/
COPY demo-system/pom.xml demo-system/
COPY demo-quartz/pom.xml demo-quartz/
COPY demo-generator/pom.xml demo-generator/

RUN mvn -B -q -DskipTests dependency:go-offline || true

COPY test-support-starter test-support-starter
COPY demo-admin demo-admin
COPY demo-common demo-common
COPY demo-framework demo-framework
COPY demo-system demo-system
COPY demo-quartz demo-quartz
COPY demo-generator demo-generator

RUN mvn -B -DskipTests -pl demo-admin -am package

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /src/demo-admin/target/demo-admin.jar /app/app.jar

ENV JAVA_OPTS="-Xms256m -Xmx1024m -Duser.timezone=Asia/Shanghai" \
    SPRING_PROFILES_ACTIVE=docker \
    DEMO_PROFILE=/data/upload

RUN mkdir -p /data/upload

EXPOSE 8801

HEALTHCHECK --interval=15s --timeout=5s --start-period=90s --retries=10 \
  CMD curl -fsS "http://127.0.0.1:8801/captchaImage" >/dev/null || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
