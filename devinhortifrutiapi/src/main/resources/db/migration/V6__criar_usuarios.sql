CREATE TABLE usuarios (
    id bigserial primary key,
    login varchar(50),
    senha varchar(255),
    nome varchar (70),
    email varchar(150),
    dt_nascimento date,
    is_admin boolean
)