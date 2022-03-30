create database kmart;

use database kmart;

create table user_account (
	id int primary key auto increment,
	name varchar(60) not null,
	password varchar(60) not null
);