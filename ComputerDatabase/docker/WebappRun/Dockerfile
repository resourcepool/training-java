FROM tomcat:8-jre8

RUN apt-get update && \
    apt-get install -y mysql-client

# adds /opt/cdb/etc to tomcat classpath
RUN sed -i 's/^shared\.loader=/shared\.loader=\/opt\/cdb\/etc/' $CATALINA_HOME/conf/catalina.properties

COPY cdb-webapp.war $CATALINA_HOME/webapps/cdb-webapp.war

COPY *.properties /opt/cdb/etc/
