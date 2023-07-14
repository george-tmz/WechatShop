alter table `order_table`
    add column (shop_id bigint);

update `order_table` set shop_id = 1 where id = 1