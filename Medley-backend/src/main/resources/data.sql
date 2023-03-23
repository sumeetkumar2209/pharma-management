insert into role  values(1,'ADMIN');
insert into role  values(2,'APPROVER');
insert into role  values(3,'QUALITY_USER');
insert into role  values(4,'WAREHOUSE_USER');

INSERT INTO USER (USER_ID,contact_no,created_by,creation_timestamp,email_id,first_name,last_name,password,role_id)
values ('009e8ce0afcd4d1098ff7e26dbb2755f','961922065','AUTOMAT',CURRENT_TIMESTAMP,'sumeet@gmail.com',
'sumeet','kumar','$2a$10$yf/QUtMVDHwxIATgdMiahOdH6jst4Z0kcj0JpYucRnkyBKA7J8NxS',1);

INSERT INTO USER (USER_ID,contact_no,created_by,creation_timestamp,email_id,first_name,last_name,password,role_id,SUPERVISOR_ID)
values ('0159cf833d8a418eb84f162f21d8ecd7','961922065','AUTOMAT',CURRENT_TIMESTAMP,'kiran@gmail.com',
'kiran','thakur','$2a$10$yf/QUtMVDHwxIATgdMiahOdH6jst4Z0kcj0JpYucRnkyBKA7J8NxS',1,'009e8ce0afcd4d1098ff7e26dbb2755f');


INSERT INTO USER (USER_ID,contact_no,created_by,creation_timestamp,email_id,first_name,last_name,password,role_id,SUPERVISOR_ID)
values ('8310d77155ef4cebabbce94267d54b38','961922065','AUTOMAT',CURRENT_TIMESTAMP,'chotu@gmail.com',
'Chotu','Singh','$2a$10$yf/QUtMVDHwxIATgdMiahOdH6jst4Z0kcj0JpYucRnkyBKA7J8NxS',2,'009e8ce0afcd4d1098ff7e26dbb2755f');

INSERT INTO USER (USER_ID,contact_no,created_by,creation_timestamp,email_id,first_name,last_name,password,role_id,SUPERVISOR_ID)
values ('41808edee83f4cb79dca9d10fdccff32','961922065','AUTOMAT',CURRENT_TIMESTAMP,'shashank@gmail.com',
'Shashank','Singh','$2a$10$yf/QUtMVDHwxIATgdMiahOdH6jst4Z0kcj0JpYucRnkyBKA7J8NxS',2,'009e8ce0afcd4d1098ff7e26dbb2755f');



INSERT INTO COUNTRY(COUNTRY_CODE, COUNTRY_NAME) VALUES('CAN','CANADA');

INSERT INTO CURRENCY(CURRENCY_CODE, CURRENCY_NAME) VALUES('USD','DOLLAR');

INSERT INTO REVIEW_STATUS(REVIEW_CODE, REVIEW_NAME) VALUES('PE','PENDING');
INSERT INTO REVIEW_STATUS(REVIEW_CODE, REVIEW_NAME) VALUES('DR','DRAFT');
INSERT INTO REVIEW_STATUS(REVIEW_CODE, REVIEW_NAME) VALUES('AP','APPROVED');
INSERT INTO REVIEW_STATUS(REVIEW_CODE, REVIEW_NAME) VALUES('RE','REJECTED');
INSERT INTO REVIEW_STATUS(REVIEW_CODE, REVIEW_NAME) VALUES('MD','MODIFIED');

INSERT INTO STATUS(STATUS_CODE,STATUS_NAME)  VALUES ('AC','ACTIVE');
INSERT INTO STATUS(STATUS_CODE,STATUS_NAME)  VALUES ('IN','INACTIVE');

INSERT INTO QUALIFICATION_STATUS (QUALIFICATION_CODE,QUALIFICATION_NAME )  VALUES ('QF','QUALIFIED');
INSERT INTO QUALIFICATION_STATUS (QUALIFICATION_CODE,QUALIFICATION_NAME )  VALUES ('NQ','NON QUALIFIED');

<!-- newly added  -->
INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (1,'Dashboard', 'dashboard','/dashboard',0);
INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (2,'Supplier', 'flare','/supplier',1);
INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (3,'Customer', 'phonelink','/customer',2);
INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (4,'Transport', 'local_shipping','/transport',3);

INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (5,'Product', 'account_balance_wallet','/product/add',0);
INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (6,'Purchase Orders', 'home','/warehouse',1);
INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (7,'Warehouse', 'shopping_cart','/menu_orders',2);
INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (8,'Goods Returned', 'compare_arrows','/goods',3);
INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (9,'Reports', 'next_week','/reports',3);
INSERT INTO MENU (menu_id,label, icon, link, menu_order) VALUES (10,'Audit Trail', 'account_balance','/audit',3);

insert into ROLE_MENU values (1,1);
insert into ROLE_MENU values(1,2);

