create table if not exists `user` (
	id identity, 
	username varchar(255) not null,
	password varchar(255) not null,
	fullname varchar(255),
	street varchar(255),
	city varchar(255) not null,
	state varchar(255),
	zip varchar(255),
	phone_number varchar(255)
);


CREATE TABLE IF NOT EXISTS `taco_order` (
	id identity,  
    delivery_name varchar(50) not null,  
    delivery_street varchar(50) not null,  
    delivery_city varchar(50) not null,  
    delivery_state varchar(50) not null,  
    delivery_zip varchar(10) not null,  
    cc_number varchar(16) not null,  
    cc_expiration varchar(5) not null,  
    cccvv varchar(3) not null,  
    date_order_placed timestamp not null,
    user_id bigint not null
);

-- uncomment if [spring.jpa.hibernate.ddl-auto] property is set to [validate] or [none]
--alter table taco_order add foreign key (user_id) references user(id);


create table if not exists taco (  
	id identity,  
	name varchar(50) not null,  
--	taco_order bigint not null,  
--	taco_order_key bigint not null,  
	date_created timestamp not null
);


create table if not exists ingredient (  
	id varchar(4) not null,  
	name varchar(25) not null,  
	ingredient_type varchar(10) not null
);


-- uncomment if [spring.jpa.hibernate.ddl-auto] property is set to [validate] or [none] 
--create table if not exists taco_ingredients (
--  	taco_id bigint not null,
--  	ingredients_id varchar(4) not null
--);

-- uncomment if [spring.jpa.hibernate.ddl-auto] property is set to [validate] or [none]
--alter table taco_ingredients add foreign key (taco_id) references taco(id);
--alter table taco_ingredients add foreign key (ingredients_id) references ingredient(id);


-- uncomment if [spring.jpa.hibernate.ddl-auto] property is set to [validate] or [none]
--create table if not exists taco_order_tacos (
--  	taco_order_id bigint not null,
-- 	tacos_id bigint not null
--);

-- uncomment if [spring.jpa.hibernate.ddl-auto] property is set to [validate] or [none]
--alter table taco_order_tacos
--    add foreign key (taco_order_id) references taco_order(id);
--alter table taco_order_tacos
--    add foreign key (tacos_id) references taco(id);

    
-- uncomment if [spring.jpa.hibernate.ddl-auto] property is set to [validate] or [none]
--create sequence hibernate_sequence;
--create sequence taco_sequence_generator;
--create sequence user_sequence_generator;


--*******************************************************

--spring in action 6th edition ddl

--create table if not exists ingredient_ref (  
--	ingredient varchar(4) not null,  
--	taco bigint not null,  
--	taco_key bigint not null
--);

--alter table taco add foreign key (taco_order) references taco_order(id);
--alter table ingredient_ref add foreign key (ingredient) references ingredient(id);


