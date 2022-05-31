CREATE TABLE item_venda (
    id bigserial primary key,
    id_venda bigint,
    id_produto bigint,
    preco_unitario decimal,
    quantidade int
);