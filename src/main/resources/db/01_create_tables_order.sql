drop schema if exists `order_demo`;
create schema `order_demo`;
use order_demo;

set foreign_key_checks = 0;

drop table if exists `order`;

create table `order` (
	`id` int not null auto_increment,
    `customer_name` varchar(60),
    `customer_address` varchar(200),
    `order_status` varchar(20),
    `created_time` datetime,
    primary key (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

drop table if exists `item`;

create table `item` (
	id int not null auto_increment,
    item_name varchar(60) not null,
    item_description varchar(200),
    price numeric(6,2),
    in_stock int not null default 0,
    primary key (id)
);

drop table if exists `order_item`;

create table `order_item` (
	id int not null auto_increment,
    item_id int not null,
    quantity int default 1,
    order_id int not null,
    primary key (id),
    constraint `fk_order_id` 
    foreign key (order_id) references `order` (id),
    constraint `fk_item_id`
    foreign key (item_id) references `item` (id)
);

set foreign_key_checks = 1;