FROM tomcat:8.5-jre8
ADD cts-datasource.war /usr/local/tomcat/webapps/
EXPOSE 8080
COPY context.xml /usr/local/tomcat/conf/
COPY server.xml /usr/local/tomcat/conf/
ENTRYPOINT ["catalina.sh","run"]