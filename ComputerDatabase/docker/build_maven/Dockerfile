
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

