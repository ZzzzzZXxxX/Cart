
create database  Cart default charset=utf8;

use Cart;

create table goods (
goodsid int primary key auto_increment,
goodsname varchar(50) not null unique,
price double)engine=innodb default charset=utf8; 

insert into goods(goodsid,goodsname,price) values(1,'张宇高数18讲',40.0);
insert into goods(goodsid,goodsname,price) values(2,'张宇1000题',45.0);
insert into goods(goodsid,goodsname,price) values(3,'恋练有词',47.0);
insert into goods(goodsid,goodsname,price) values(4,'李永乐660题',38.0);
insert into goods(goodsid,goodsname,price) values(5,'李永乐330题',28.0);

create table user (
un varchar(50) not null unique,
pwd varchar(50) not null,
phone varchar(30),
addr varchar(50))engine=innodb default charset=utf8; 



create table cart (
goodsname varchar(50) not null ,
number int not null,
price double,
un varchar(50))engine=innodb default charset=utf8; 

create table order_ (
id varchar(80) not null ,
un varchar(50) not null,
goodsname varchar(50) not null ,
number int not null,
price double not null)engine=innodb default charset=utf8; 