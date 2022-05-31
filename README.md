# M3P2-DEVinHortifruti-BackEnd
Repositório back-end do projeto 2 do módulo 3.

- Para rodar os containeres do Postgres e do RabbitMQ:
  - cd docker
  - docker-compose up

- Para rodar a aplicação:
  - ./mvnw spring-boot:run

- Para usar o usuário admin2 nos testes com autenticação e autorização, basta colocar o JSON seguinte no body do POST /login. Ao fazer isso, será gerado um token que deverá ser colocado em todas as requisições.
 - {
	"login": "admin2",
	"senha": "(d4NO8^ie#"
}
