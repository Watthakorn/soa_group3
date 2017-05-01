listfield-service     port:9001

admin-service         port:9002

manage-service        port:9003

reservation-service   port:9004

users-service         port:9005

TO DEPLOY ADD Dockerfile in each service

And fix application.properties datasource.url

# Docker deploy

`docker container run  -d --name mysql-field -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=field_service mysql:latest`

`docker container run  -d --name mysql-user -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=userdb mysql:latest`

`docker container run  -d --name mysql-reservation -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=reservation mysql:latest`

`docker image build -t listfield-service .`

`docker image build -t admin-service .`

`docker image build -t manage-service .`

`docker image build -t reservation-service .`

`docker image build -t users-service .`

`docker container run  -d --name listfield-service --link mysql-field -p 9001:9001 listfield-service`

`docker container run  -d --name admin-service --link mysql-user -p 9002:9002 admin-service`

`docker container run  -d --name manage-service --link mysql-field -p 9003:9003 manage-service`

`docker container run  -d --name reservation-service --link mysql-reservation -p 9004:9004 reservation-service`

`docker container run  -d --name users-service --link mysql-user -p 9005:9005 users-service`
