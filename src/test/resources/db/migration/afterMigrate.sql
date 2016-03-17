delete store;
delete product;
delete stock;

insert into store (store_id, store_name) values (1, 'Store 1');

insert into store (store_id, store_name) values (2, 'Store 2');

insert into store (store_id, store_name) values (3, 'Store 3');

insert into product(product_id, product_name, product_desc, sku, price) values(1, 'One Product', 'Product One', 'P12345', 20.78);
insert into product(product_id, product_name, product_desc, sku, price) values(2, 'Two Product', 'Product Two', 'K12345', 10.78);
insert into product(product_id, product_name, product_desc, sku, price) values(3, 'Three Product', 'Product Three', 'U12345', 30.78);
insert into product(product_id, product_name, product_desc, sku, price) values(4, 'Four Product', 'Product Four', 'L12345', 40.78);

insert into stock(stock_id, store_id, product_id, product_cnt) values(1, 1, 1, 0);
insert into stock(stock_id, store_id, product_id, product_cnt) values(2, 1, 2, 30);
insert into stock(stock_id, store_id, product_id, product_cnt) values(3, 1, 3, 0);
insert into stock(stock_id, store_id, product_id, product_cnt) values(4, 2, 2, 0);
insert into stock(stock_id, store_id, product_id, product_cnt) values(5, 2, 3, 0);
insert into stock(stock_id, store_id, product_id, product_cnt) values(6, 3, 1, 0);
insert into stock(stock_id, store_id, product_id, product_cnt) values(7, 3, 3, 0);

