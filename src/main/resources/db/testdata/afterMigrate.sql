delete store;
delete product;
delete stock;

insert into store (store_id, store_name) values (1, 'PUB 1');

insert into store (store_id, store_name) values (2, 'PUB 2');

insert into store (store_id, store_name) values (3, 'PUB 3');

insert into product(product_id, product_name, product_desc, sku, price) values(1, 'One Product', 'Product One', 'P12345', 20.78);
insert into product(product_id, product_name, product_desc, sku, price) values(2, 'Two Product', 'Product Two', 'K12345', 10.78);
insert into product(product_id, product_name, product_desc, sku, price) values(3, 'Three Product', 'Product One', 'U12345', 30.78);
insert into product(product_id, product_name, product_desc, sku, price) values(4, 'Four Product', 'Product One', 'L12345', 40.78);

insert into stock(store_id, product_id, product_cnt) values(1, 1, 20);
insert into stock(store_id, product_id, product_cnt) values(1, 2, 30);
insert into stock(store_id, product_id, product_cnt) values(1, 3, 5);
insert into stock(store_id, product_id, product_cnt) values(2, 2, 50);
insert into stock(store_id, product_id, product_cnt) values(2, 3, 60);
insert into stock(store_id, product_id, product_cnt) values(3, 1, 70);
insert into stock(store_id, product_id, product_cnt) values(3, 3, 90);

