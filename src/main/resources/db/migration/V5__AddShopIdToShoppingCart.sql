alter table shopping_cart
    add column (shop_id bigint);

delete
from shopping_cart
where user_id = 1;

insert into shopping_cart(user_id, goods_id, shop_id, number, status)
values (1, 1, 1, 100, 'ok');
insert into shopping_cart(user_id, goods_id, shop_id, number, status)
values (1, 4, 2, 200, 'ok');
insert into shopping_cart(user_id, goods_id, shop_id, number, status)
values (1, 5, 2, 300, 'ok');

