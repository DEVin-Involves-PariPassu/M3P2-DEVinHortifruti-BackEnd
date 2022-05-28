ALTER TABLE item_venda ADD FOREIGN KEY (id_venda) REFERENCES vendas(id);
ALTER TABLE item_venda ADD FOREIGN KEY (id_produto) REFERENCES produtos(id);
ALTER TABLE vendas ADD FOREIGN KEY (id_comprador) REFERENCES compradores(id);
ALTER TABLE vendas ADD FOREIGN KEY (id_vendedor) REFERENCES usuarios(id);