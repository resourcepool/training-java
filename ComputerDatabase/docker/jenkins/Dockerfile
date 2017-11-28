FROM jenkins:latest
 
ENV DEBIAN_FRONTEND noninteractive
 
USER root
 
RUN curl -sSL https://get.docker.com/ | sh \
    && rm -rf /var/lib/apt/lists/*

COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

ENV DOCKER_HOST tcp://docker:2375

# TODO Jenkinsfile
