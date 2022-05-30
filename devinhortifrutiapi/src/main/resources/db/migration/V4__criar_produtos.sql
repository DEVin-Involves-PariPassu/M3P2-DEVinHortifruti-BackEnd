CREATE TABLE produtos (
    id bigserial primary key,
    nome varchar(150) not null unique,
    descricao text,
    url_foto varchar(200),
    preco_sugerido decimal,
    is_ativo boolean
);
