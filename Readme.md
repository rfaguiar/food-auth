# Getting Started

### MySQL 8 docker
* [MySQL Docker Official image](https://hub.docker.com/_/mysql)
```sh
docker run --rm --name mysql8 --network minha-rede -v $(pwd)/mysql-datadir:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -p 3306:3306 -d mysql:8
```

### Redis for use token store:
* [Redis Docker Official image](https://hub.docker.com/_/redis)
```sh
docker run --network minha-rede -p 6379:6379 --name redis -v $(pwd)/redis-data:/data -d redis:6-alpine redis-server --appendonly yes
```
Validate Redis installation:
```sh
sudo apt install redis-tools
redis-cli
```
show all keys
```sh
KEYS *
```
clear keys:
```sh
FLUSHALL
```
### Chave Assimetrica:
gerar par de chave publica e privada
```sh
keytool -genkeypair -alias food-api -keyalg RSA -keypass 123456 -keystore food-api.jks -storepass 123456789
```
listar chave
```sh
keytool -list -keystore food-api.jks
```
Extraindo a chave pública no formato PEM
extrair com keytool:
```sh
keytool -export -rfc -alias food-api -keystore food-api.jks -file food-api-cert.pem
openssl x509 -pubkey -noout -in food-api-cert.pem > food-api-public-key.pem
```

Converter jks em base64
```sh
cat food-prd.jks | base64 | pbcopy
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

