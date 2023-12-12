
CREATE DATABASE HotelManager

drop table STAFF
drop table CUSTOMER
drop table ROOM
drop table PRODUCT
drop table BILL
drop table ROOM_PRODUCT
drop table BILL_PRODUCT


if OBJECT_ID ('STAFF') is not null
drop table STAFF
create table STAFF(
	STAFF_ID INT PRIMARY KEY NOT NULL,
	STAFF_USERNAME VARCHAR(20) NOT NULL,
	STAFF_PASSWORD VARCHAR(20) NOT NULL,
	DISPLAY_NAME TEXT NOT NULL,
	DATE_OF_BIRTH DATE NOT NULL,
	SEX TEXT NOT NULL,
	PHONE INT NOT NULL
)
if OBJECT_ID ('CUSTOMER') is not null
drop table CUSTOMER
create table CUSTOMER(
	CUSTOMER_ID INT PRIMARY KEY NOT NULL,
	CUSTOMER_NAME TEXT NOT NULL,
	DATE_OF_BIRTH DATE NOT NULL,
	SEX TEXT NOT NULL,
	PHONE INT NOT NULL

)
if OBJECT_ID ('ROOM') is not null
drop table ROOM
create table ROOM(
	ROOM_ID INT PRIMARY KEY NOT NULL,
	ROOM_LOCATION INT NOT NULL,
	ROOM_VIEW text not null,
	ROOM_NAME text not null,
	ROOM_TYPE text not null,
	DAY_PRICE INTEGER not null,
	HOUR_PRICE INTEGER not null,
	NOTE text

)

if OBJECT_ID ('PRODUCT') is not null
drop table PRODUCT
create table PRODUCT(
	PRODUCT_ID INT PRIMARY KEY NOT NULL,
	PRODUCT_NAME text not null,
	PRICE money,
	QUANTITY INT not null
)


if OBJECT_ID ('BILL') is not null
drop table BILL
create table BILL(
	BILL_ID INT PRIMARY KEY NOT NULL,
	STAFF_ID INT,
	foreign key (STAFF_ID) references STAFF(STAFF_ID),
	CUSTOMER_ID INT,
	foreign key (CUSTOMER_ID) references CUSTOMER(CUSTOMER_ID),
	ROOM_ID INT not null,
	foreign key (ROOM_ID) references ROOM(ROOM_ID),
	DATE_CHECK_IN date not null,
	DATE_CHECK_OUT date not null,
	NOTE text

)

if OBJECT_ID ('ROOM_PRODUCT') is not null
drop table ROOM_PRODUCT
create table ROOM_PRODUCT(
	PRODUCT_ID INT ,
	ROOM_ID int, 
	PRODUCT_NAME text not null,
	PRICE money,
	foreign key (PRODUCT_ID) references PRODUCT(PRODUCT_ID),
	foreign key (ROOM_ID) references ROOM(ROOM_ID),

	QUANTITY INT
)
if OBJECT_ID ('BILL_PRODUCT') is not null
drop table BILL_PRODUCT
create table BILL_PRODUCT(
	PRODUCT_ID INT  NOT NULL,
	BILL_ID int not null,
	foreign key (PRODUCT_ID) references PRODUCT(PRODUCT_ID),
	foreign key (BILL_ID) references BILL(BILL_ID),
	PRODUCT_NAME text not null,
	PRICE money,
	QUANTITY INT
)