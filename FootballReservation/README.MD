# PORT

listfield-service     port:9001

admin-service         port:9002

manage-service        port:9003

reservation-service   port:9004

users-service         port:9005

# Docker deploy

TO DEPLOY ADD Dockerfile in each service

And fix application.properties datasource.url

## Docker command create db

`docker container run  -d --name mysql-field -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=field_service mysql:latest`

`docker container run  -d --name mysql-user -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=userdb mysql:latest`

`docker container run  -d --name mysql-reservation -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=reservation mysql:latest`

## Docker command create image

$ `mvn clean package` in service folder first

`docker image build -t listfield-service .`

`docker image build -t admin-service .`

`docker image build -t manage-service .`

`docker image build -t reservation-service .`

`docker image build -t users-service .`

## Docker command deploy image

`docker container run  -d --name listfield-service --link mysql-field -p 9001:9001 listfield-service`

`docker container run  -d --name admin-service --link mysql-user -p 9002:9002 admin-service`

`docker container run  -d --name manage-service --link mysql-field -p 9003:9003 manage-service`

`docker container run  -d --name reservation-service --link mysql-reservation -p 9004:9004 reservation-service`

`docker container run  -d --name users-service --link mysql-user -p 9005:9005 users-service`

# Example deployed

## 128.199.213.153

reservation

http://128.199.213.153:9004/reservations

## 128.199.197.151

Use same database admin-user

http://128.199.197.151:9002/admin

http://128.199.197.151:9005


## 188.166.191.248

Use same database listfield-manage 

http://188.166.191.248:9001/listfield

http://188.166.191.248:9003/field/1
