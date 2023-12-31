create table goods
(
    id          bigint primary key auto_increment,
    shop_id     bigint,
    name        varchar(100),
    description varchar(1024),
    details     text,
    img_url     varchar(1024),
    price       bigint,      -- 单位 分
    stock       int       not null default 0,
    status      varchar(16), -- 'ok' 正常 'deleted' 已经删除
    created_at  timestamp not null default now(),
    updated_at  timestamp not null default now()
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci;

insert into goods(id, shop_id, name, description, details, img_url, price, stock, status)
values (1, 1, 'goods1', 'desc1', 'details1', 'url1', 100, 5, 'ok');
insert into goods(id, shop_id, name, description, details, img_url, price, stock, status)
values (2, 1, 'goods2', 'desc2', 'details2', 'url2', 100, 5, 'ok');
insert into goods(id, shop_id, name, description, details, img_url, price, stock, status)
values (3, 2, 'goods3', 'desc2', 'details3', 'url3', 100, 5, 'ok');
insert into goods(id, shop_id, name, description, details, img_url, price, stock, status)
values (4, 2, 'goods4', 'desc2', 'details4', 'url4', 100, 5, 'ok');
insert into goods(id, shop_id, name, description, details, img_url, price, stock, status)
values (5, 2, 'goods5', 'desc2', 'details5', 'url5', 200, 5, 'ok');