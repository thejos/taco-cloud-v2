delete from taco;
delete from ingredient;
delete from taco_order;
delete from taco_order_tacos;

insert into ingredient (id, name, ingredient_type) values ('FLTO', 'Flour Tortilla', 'WRAP');
insert into ingredient (id, name, ingredient_type) values ('COTO', 'Corn Tortilla', 'WRAP');
insert into ingredient (id, name, ingredient_type) values ('GRBF', 'Ground Beef', 'PROTEIN');
insert into ingredient (id, name, ingredient_type) values ('CARN', 'Carnitas', 'PROTEIN');
insert into ingredient (id, name, ingredient_type) values ('TMTO', 'Diced Tomatoes', 'VEGGIES');
insert into ingredient (id, name, ingredient_type) values ('LETC', 'Lettuce', 'VEGGIES');
insert into ingredient (id, name, ingredient_type) values ('CHED', 'Cheddar', 'CHEESE');
insert into ingredient (id, name, ingredient_type) values ('JACK', 'Monterrey Jack', 'CHEESE');
insert into ingredient (id, name, ingredient_type) values ('SLSA', 'Salsa', 'SAUCE');
insert into ingredient (id, name, ingredient_type) values ('SRCR', 'Sour Cream', 'SAUCE');