CREATE TABLE vendas (
    id bigserial,
    id_comprador bigint,
    id_vendedor bigint,
    dt_venda datetime,
    total_venda decimal,
    cep varchar(8),
    sigla_estado varchar(2),
    cidade varchar(100),
    logradouro varchar(200),
    bairro varchar(100),
    complemento varchar(150),
    dt_entrega date,
    venda_cancelada boolean
)