create database kmart;

use kmart;

create table user_account (
	id int primary key auto_increment,
	name varchar(60) not null,
	password varchar(60) not null
);

insert into user_account(name, password) values ("adm", "adm");

create table product (
	barcode varchar(60) primary key not null,
	name varchar(60) not null,
	description text,
	sell_price double not null,
	cost double not null,
	inStock int not null,
	unit varchar(20) not null
);

create table transaction_log (
	id int not null primary key auto_increment,
	total_amount double not null,
	transaction_type varchar(4) not null,
	trasaction_timestamp timestamp not null default now()
);


create table purchase (
	id int auto_increment primary key,
	payment_method varchar(10) not null,
	total_paid double not null,
	total_cost double not null,
	change_val double not null
);