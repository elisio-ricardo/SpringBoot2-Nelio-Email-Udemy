
    create table categoria (
       id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table cidade (
       id integer not null auto_increment,
        nome varchar(255),
        estado_id integer,
        primary key (id)
    ) engine=InnoDB;

    create table cliente (
       id bigint not null auto_increment,
        cpf_ou_cnpj varchar(255),
        email varchar(255),
        nome varchar(255),
        tipo integer,
        primary key (id)
    ) engine=InnoDB;

    create table endereco (
       id integer not null auto_increment,
        bairro varchar(255),
        cep varchar(255),
        complemento varchar(255),
        logradouro varchar(255),
        numero varchar(255),
        cidade_id integer,
        cliente_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table estado (
       id integer not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table item_pedido (
       desconto float(53),
        preco float(53),
        quantidade integer,
        pedido_id bigint not null,
        produto_id bigint not null,
        primary key (pedido_id, produto_id)
    ) engine=InnoDB;

    create table pagamento (
       pedido_id bigint not null,
        estado integer,
        primary key (pedido_id)
    ) engine=InnoDB;

    create table pagamento_com_boleto (
       data_pagamento datetime(6),
        data_vencimento datetime(6),
        pedido_id bigint not null,
        primary key (pedido_id)
    ) engine=InnoDB;

    create table pagamento_com_cartao (
       numero_de_parcelas integer,
        pedido_id bigint not null,
        primary key (pedido_id)
    ) engine=InnoDB;

    create table pedido (
       id bigint not null auto_increment,
        instante datetime(6),
        cliente_id bigint,
        endereco_de_entrega_id integer,
        primary key (id)
    ) engine=InnoDB;

    create table produto (
       id bigint not null auto_increment,
        nome varchar(255),
        preco float(53),
        primary key (id)
    ) engine=InnoDB;

    create table produto_categoria (
       produto_id bigint not null,
        categoria_id bigint not null
    ) engine=InnoDB;

    create table telefone (
       cliente_id bigint not null,
        telefones varchar(255)
    ) engine=InnoDB;

    alter table cliente 
       add constraint UK_cmxo70m08n43599l3h0h07cc6 unique (email);

    alter table cidade 
       add constraint FKkworrwk40xj58kevvh3evi500 
       foreign key (estado_id) 
       references estado (id);

    alter table endereco 
       add constraint FK8b1kcb3wucapb8dejshyn5fsx 
       foreign key (cidade_id) 
       references cidade (id);

    alter table endereco 
       add constraint FK8s7ivtl4foyhrfam9xqom73n9 
       foreign key (cliente_id) 
       references cliente (id);

    alter table item_pedido 
       add constraint FK60ym08cfoysa17wrn1swyiuda 
       foreign key (pedido_id) 
       references pedido (id);

    alter table item_pedido 
       add constraint FKtk55mn6d6bvl5h0no5uagi3sf 
       foreign key (produto_id) 
       references produto (id);

    alter table pagamento 
       add constraint FKthad9tkw4188hb3qo1lm5ueb0 
       foreign key (pedido_id) 
       references pedido (id);

    alter table pagamento_com_boleto 
       add constraint FKcr74vrxf8nfph0knq2bho8doo 
       foreign key (pedido_id) 
       references pagamento (pedido_id);

    alter table pagamento_com_cartao 
       add constraint FKta3cdnuuxclwfh52t4qi432ow 
       foreign key (pedido_id) 
       references pagamento (pedido_id);

    alter table pedido 
       add constraint FK30s8j2ktpay6of18lbyqn3632 
       foreign key (cliente_id) 
       references cliente (id);

    alter table pedido 
       add constraint FK1fihyy2fnocpuwc74674qmfkv 
       foreign key (endereco_de_entrega_id) 
       references endereco (id);

    alter table produto_categoria 
       add constraint FKq3g33tp7xk2juh53fbw6y4y57 
       foreign key (categoria_id) 
       references categoria (id);

    alter table produto_categoria 
       add constraint FK1c0y58d3n6x3m6euv2j3h64vt 
       foreign key (produto_id) 
       references produto (id);

    alter table telefone 
       add constraint FK8aafha0njkoyoe3kvrwsy3g8u 
       foreign key (cliente_id) 
       references cliente (id);
