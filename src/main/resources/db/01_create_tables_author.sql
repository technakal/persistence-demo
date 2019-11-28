drop schema if exists `author_demo`;

create schema `author_demo`;

use `author_demo`;

set foreign_key_checks = 0;

drop table if exists `author`;

create table `author` (
	id int not null auto_increment,
    first_name varchar(60),
    last_name varchar(60),
    primary key (id)
);

drop table if exists `author_book`;

create table `author_book` (
	author_id int not null,
    book_id int not null,
    primary key (author_id, book_id),
    constraint `fk_author_id` foreign key (`author_id`)
    references `author` (id),
    constraint `fk_book_id` foreign key (`book_id`)
    references `book` (id)
);

drop table if exists `book`;

create table `book` (
	id int not null auto_increment,
    title varchar(200),
    publisher varchar(200),
    publication_year int(4),
    primary key (id)
);

set foreign_key_checks = 1;