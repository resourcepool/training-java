
# FROM maven:3.5-jdk-8
# WORKDIR /app
# ADD . /app

## sudo docker build -t my-maven .
## sudo docker run -it --name my-maven-script my-maven mvn install
# http://blog.moncoindunet.fr/docker/dockerfile-add-ou-copy/



#---------------------------------------------------------
# (run as CMD)

### IMAGE BUILD (maven + jdk8)
# https://hub.docker.com/_/maven/

# sudo docker container prune
# sudo docker run --name cdb-builder -v $(pwd)/target:/usr/src/cdb/target -v $(pwd):/usr/src/cdb -w /usr/src/cdb maven:3.5-jdk-8 mvn package
# sudo docker start -a cdb-builder
# sudo docker create --name cdb-builder -v $(pwd)/target:/usr/src/cdb/target -v $(pwd):/usr/src/cdb -w /usr/src/cdb maven:3.5-jdk-8 mvn package
# sudo docker create --name cdb-builder -v $(pwd)/target:/usr/src/cdb/target -v $(pwd):/usr/src/cdb -w /usr/src/cdb maven:3.5-jdk-8 mvn package || true
# sudo docker create --name cdb-builder -v $(pwd)/target:/usr/src/cdb/target -v $(pwd):/usr/src/cdb -w /usr/src/cdb maven:3.5-jdk-8 mvn package || # sudo true
# > sudo docker create --name cdb-builder -v $(pwd)/target:/usr/src/cdb/target -v $(pwd):/usr/src/cdb -w /usr/src/cdb maven:3.5-jdk-8 mvn package 2> /dev/null || # sudo true
# sudo docker ps -aq --filter name=cdb-builder

#---------------------------------------------------------
### IMAGE MYSQL
# https://hub.docker.com/r/mysql/mysql-server/

#sudo docker run --name cdb-db -e MYSQL_RANDOM_ROOT_PASSWORD=yes -d mysql/mysql-server:5.7 
#-v /var/lib/mysql:/var/lib/mysql #mount dir containing databases, datas and so on
#-v /etc/mysql/:/etc/mysql/ #mount dir for conf like my.cnf
#-v ${pwd}/../config/db/:/docker-entrypoint-initdb.d/ #mount dir of script to load at start
#-e MYSQL_ROOT_PASSWORD=/tmp/password.txt

# sudo docker container prune

# sudo docker create --name cdb-db \
#   -v $(pwd)/../config/db/:/docker-entrypoint-initdb.d/ \
#   -v /var/lib/mysql:/var/lib/mysql                            #mount dir containing databases, datas and so on
#   -v /etc/mysql/:/etc/mysql/ \                                #mount dir for conf like my.cnf
#   -e MYSQL_RANDOM_ROOT_PASSWORD=yes \
#   -e MYSQL_ROOT_HOST=128.0.0.1 \
#    mysql/mysql-server:5.7

# sudo docker start cdb-db
# sudo docker logs cdb-db #retrieve generated root password
# sudo docker exec -it cdb-db mysql -u admincdb -p


#---------------------------------------------------------
## IMAGE TOMCAT
