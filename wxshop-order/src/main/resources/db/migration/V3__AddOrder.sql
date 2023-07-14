insert into `order_table` (id, user_id, total_price, address, express_company, express_id, status, shop_id)
values (2, 1, 700, '火星', null, null, 'pending', 1);

insert into order_goods(goods_id, order_id, number)
values (1, 2, 3),
       (2, 2, 4);