set foreign_key_checks = 0;

lock tables grupo write, grupo_permissao write, permissao write,
	usuario write, usuario_grupo write, oauth_client_details write;

delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from usuario;
delete from usuario_grupo;
delete from oauth_client_details;

set foreign_key_checks = 1;

alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table usuario auto_increment = 1;

insert into permissao (id, nome, descricao) values (1, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissao (id, nome, descricao) values (3, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissao (id, nome, descricao) values (5, 'CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar usuários');
insert into permissao (id, nome, descricao) values (6, 'EDITAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite criar ou editar usuários');
insert into permissao (id, nome, descricao) values (7, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissao (id, nome, descricao) values (8, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissao (id, nome, descricao) values (9, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissao (id, nome, descricao) values (10, 'GERAR_RELATORIOS', 'Permite gerar relatórios');

insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

-- Adiciona todas as permissoes no grupo do gerente
insert into grupo_permissao (grupo_id, permissao_id)
select 1, id from permissao;

-- Adiciona permissoes no grupo do vendedor
insert into grupo_permissao (grupo_id, permissao_id)
select 2, id from permissao where nome like 'CONSULTAR_%';

-- Adiciona permissoes no grupo do auxiliar
insert into grupo_permissao (grupo_id, permissao_id)
select 3, id from permissao where nome like 'CONSULTAR_%';

-- Adiciona permissoes no grupo cadastrador
insert into grupo_permissao (grupo_id, permissao_id)
select 4, id from permissao where nome like '%_RESTAURANTES' or nome like '%_PRODUTOS';
-- senha: 123
insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'rfaguiar1@gmail.com', '$2y$12$GlQ0PAx8LnZQQwLJV8CzkuZCm8LRO0f/OknfpLATtpignJ0IEA9bS', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@food.com', '$2y$12$GlQ0PAx8LnZQQwLJV8CzkuZCm8LRO0f/OknfpLATtpignJ0IEA9bS', utc_timestamp),
(3, 'José Souza', 'jose.aux@food.com', '$2y$12$GlQ0PAx8LnZQQwLJV8CzkuZCm8LRO0f/OknfpLATtpignJ0IEA9bS', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@food.com', '$2y$12$GlQ0PAx8LnZQQwLJV8CzkuZCm8LRO0f/OknfpLATtpignJ0IEA9bS', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '$2y$12$GlQ0PAx8LnZQQwLJV8CzkuZCm8LRO0f/OknfpLATtpignJ0IEA9bS', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4), (5, 4);

-- authorization spring server table inserts

-- secret: web123
insert into oauth_client_details (
    client_id, resource_ids, client_secret,
    scope, authorized_grant_types, web_server_redirect_uri, authorities,
    access_token_validity, refresh_token_validity, autoapprove
)
values (
           'food-web', null, '$2y$12$Qhmqs9S0NAZBMODrqb/LtOF2toueNWtdhgGizWZHbeasGhS7higfO',
           'READ,WRITE', 'password,refresh_token', null, null,
           60 * 60 * 6, 60 * 24 * 60 * 60, null
           );

-- secret: food123
-- mudar autoapprove para true que ira remover a tela de aprovação ao fazer login
insert into oauth_client_details (
    client_id, resource_ids, client_secret,
    scope, authorized_grant_types, web_server_redirect_uri, authorities,
    access_token_validity, refresh_token_validity, autoapprove
)
values (
           'food-analytics', null, '$2y$12$Yazw5Mgv9C8sEPVDzQxaAOYLFvV/50OIjP9hW.LkOswbhjEI1tLDm',
           'READ,WRITE', 'authorization_code,refresh_token', 'http://localhost:8082', null,
           null, null, null
       );

-- secret: faturamento123
insert into oauth_client_details (
    client_id, resource_ids, client_secret,
    scope, authorized_grant_types, web_server_redirect_uri, authorities,
    access_token_validity, refresh_token_validity, autoapprove
)
values (
           'faturamento', null, '$2y$12$.xefzVdf1QTS5pU4JO6bv.XQ3ta/2ZC1tTQjvEvOtdLOAXpSda6RW',
           'READ', 'client_credentials', null, 'CONSULTAR_PEDIDOS,GERAR_RELATORIOS',
           null, null, null
       );

insert into oauth_client_details (
    client_id, resource_ids, client_secret,
    scope, authorized_grant_types, web_server_redirect_uri, authorities,
    access_token_validity, refresh_token_validity, autoapprove
)
values (
           'webadmin', null, '$2y$12$.xefzVdf1QTS5pU4JO6bv.XQ3ta/2ZC1tTQjvEvOtdLOAXpSda6RW',
           'READ', 'implicit', 'http://aplicacao-cliente', null,
           null, null, true
       );

unlock tables;
