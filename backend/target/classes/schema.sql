create schema if not exists restaurant_service;

insert into restaurant_service.positions (position)
values ('POSITION_SELLER');
insert into restaurant_service.positions (position)
values ('POSITION_ADIN');
insert into restaurant_service.branchs_office (address, status)
values ('Address office', 'ON');

insert into restaurant_service.categories (category)
values ('SummerMenu');