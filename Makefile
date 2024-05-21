build:
	mvn compile

unit-test:
	@echo "Executando testes unitários"
	@mvn test

integration-test:
	mvn test -P integration-test

system-test:
	mvn test -P system-test

performance-test:
	mvn gatling:test -P performance-test

test: unit-test integration-test

package:
	mvn package

docker-build: package
	docker build -t fiap-4soat-g48-backend:dev -f ./Dockerfile .

docker-start:
	docker-compose -f docker-compose.yml up -d

docker-stop:
	docker-compose -f docker-compose.yml down
