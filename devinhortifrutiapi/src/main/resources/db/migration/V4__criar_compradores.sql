create table compradores (
     id bigint primary key,
     cpf varchar(11) not null unique,
     nome varchar(200) not null,
     email varchar(150) not null unique,
     telefone varchar(20)
);
