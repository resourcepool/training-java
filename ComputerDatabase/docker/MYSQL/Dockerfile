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

FROM mysql:5.6

COPY $(pwd)../../../config/db/ /docker-entrypoint-initdb.d/

