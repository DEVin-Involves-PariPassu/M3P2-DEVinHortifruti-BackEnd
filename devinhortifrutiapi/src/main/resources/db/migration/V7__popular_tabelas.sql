INSERT INTO usuarios (login, senha, nome, email, dt_nascimento, is_admin) VALUES
('admin', '123456', 'Administrador DEVinHortifruti', 'admin@devinhortifruti.com', '2000-01-01', true),
('visitante','123456', 'Visitante', 'visitante@devinhortifruti.com', '1990-02-02', false);

INSERT INTO compradores (cpf, nome, email, telefone) VALUES
('07543888913', 'Lucas Bueno Cesario', 'lbc92@hotmail.com', '+554891224064'),
('71612479006', 'Douglas Ialamov', 'douglas_ialamov@estudante.sesisenai.org.br', '+5548912345678'),
('67591207016', 'Camilla Laís Amaral', 'camilla_l_amaral@estudante.sesisenai.org.br', '5548987654321');

INSERT INTO produtos (nome, descricao, url_foto, preco_sugerido, is_ativo) VALUES
('Banana', 'Rica em potássio', 'https://www.infoescola.com/wp-content/uploads/2010/04/banana_600797891.jpg', 5.59, true),
('Maçã','Associada ao fruto proibido da Bíblia, a maçã é uma das frutas mais consumidas e cultivadas do mundo.', 'https://d3ugyf2ht6aenh.cloudfront.net/stores/746/397/products/maca-argentina1-a86acef532d11addf315221676880019-1024-1024.jpg', 3.39, true);

INSERT INTO vendas (id_comprador, id_vendedor, dt_venda, total_venda,
                    cep, sigla_estado, cidade, logradouro, bairro, complemento,
                    dt_entrega, venda_cancelada) VALUES
(1, 1, '2022-05-28 16:00:00', 10.18, '88085123', 'SC', 'Florianópolis', 'Rua da Tecnologia', 'Abraão', 'Casa', '2022-06-01', false),
(2, 2, '2022-05-28 17:00:00', 4.00, '88085456', 'SC', 'São José', 'Rua do Desenvolvimento', 'Praia Comprida', 'Bloco B 501', '2022-06-01', false);

INSERT INTO item_venda (id_venda, id_produto, preco_unitario, quantidade) VALUES
(1, 1, 5.59, 2),
(2, 2, 4.00, 1);

