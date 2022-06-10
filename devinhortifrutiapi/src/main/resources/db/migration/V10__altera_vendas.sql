UPDATE vendas SET total_venda = 11.18 WHERE id = 1;

INSERT INTO vendas (id_comprador, id_vendedor, dt_venda, total_venda,
                    cep, sigla_estado, cidade, logradouro, bairro, complemento,
                    dt_entrega, venda_cancelada) VALUES
(1, 2, '2022-06-10 16:00:00', 9.00, '88085123', 'SC', 'Florianópolis', 'Rua da Tecnologia', 'Abraão', 'Casa', '2022-06-10', false),
(2, 1, '2022-05-28 17:00:00', 19.00, '88085456', 'SC', 'São José', 'Rua do Desenvolvimento', 'Praia Comprida', 'Bloco B 501', '2022-06-01', true);

INSERT INTO item_venda (id_venda, id_produto, preco_unitario, quantidade) VALUES
(3, 1, 5.00, 1),
(3, 2, 4.00, 1),
(4, 1, 5.00, 3),
(4, 2, 4.00, 1)
