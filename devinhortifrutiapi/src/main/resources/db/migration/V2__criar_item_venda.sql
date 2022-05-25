CREATE TABLE item_venda (
    id bigserial primary keygit stat,
    id_venda bigint,
    id_produto bigint,
    preco_unitario decimal,
    quantidade int
);

ALTER TABLE item_venda ADD FOREIGN KEY (id_venda) REFERENCES vendas(id);