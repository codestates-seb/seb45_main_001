# base-image
FROM openjdk:11
ENV YOUTUBE_API_KEY AIzaSyB8WrnR8bDlLdTCabm5kDbBBrvj1-P1y80
ENV TMDB_API_KEY ccba510dce22da81439817bcf25e5121
ENV SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT-SECRET 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
ENV SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT-ID 596968448442-q9dgge0n8gmloqorr573mftmrnqo48fj.apps.googleusercontent.com
ENV RDS_URL jdbc:mysql://database-1.c0nr23ybhcgj.ap-northeast-2.rds.amazonaws.com:13306/test?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
ENV MYSQL_USERNAME admin
ENV MYSQL_PASSWORD zheldgowh1!
ENV KOBIS_API_KEY dd93d4428c56b7a47d27bf57dc81e481
ENV KDMB_API_KEY 8Z3A9MG71B4A155KB5M0
# COPY에서 사용될 경로 변수
ARG JAR_FILE=build/libs/*.jar
# jar 빌드 파일을 도커 컨테이너로 복사
COPY ${JAR_FILE} app.jar
# jar 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]