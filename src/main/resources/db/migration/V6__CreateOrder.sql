create table `order`
(
    id              bigint primary key auto_increment,
    user_id         bigint,
    total_price     bigint,      -- 价格，单位分
    address         varchar(1024),
    express_company varchar(16),
    express_id      varchar(128),
    status          varchar(16), -- pending 待付款 paid 已付款 delivered 物流中 received 已收货
    created_at      timestamp not null default now(),
    updated_at      timestamp not null default now()
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci;

create table `order_goods`
(
    id       bigint primary key auto_increment,
    goods_id bigint,
    order_id bigint,
    number   bigint -- 单位 分
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci;

insert into `order` (id, user_id, total_price, address, express_company, express_id, status)
values (1, 1, 1400, '火星', '顺丰', '运单1234567', 'delivered');

insert into order_goods(goods_id, order_id, number)
values (1, 1, 5),
       (2, 1, 9);