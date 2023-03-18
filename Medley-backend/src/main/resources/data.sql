insert into role  values(1,'ADMIN');

INSERT INTO USER (USER_ID,contact_no,created_by,creation_timestamp,email_id,first_name,last_name,password,role_id)
values ('009e8ce0afcd4d1098ff7e26dbb2755f','961922065','AUTOMAT',CURRENT_TIMESTAMP,'sumeet@gmail.com',
'sumeet','kumar','$2a$10$yf/QUtMVDHwxIATgdMiahOdH6jst4Z0kcj0JpYucRnkyBKA7J8NxS',1);

INSERT INTO COUNTRY(COUNTRY_CODE, COUNTRY_NAME) VALUES('CAN','CANADA');

INSERT INTO CURRENCY(CURRENCY_CODE, CURRENCY_NAME) VALUES('USD','DOLLAR');

INSERT INTO REVIEW_STATUS(REVIEW_CODE, REVIEW_NAME) VALUES('PE','PENDING');
INSERT INTO REVIEW_STATUS(REVIEW_CODE, REVIEW_NAME) VALUES('DR','DRAFT');
INSERT INTO REVIEW_STATUS(REVIEW_CODE, REVIEW_NAME) VALUES('AP','APPROVED');
INSERT INTO REVIEW_STATUS(REVIEW_CODE, REVIEW_NAME) VALUES('RE','REJECTED');

INSERT INTO STATUS(STATUS_CODE,STATUS_NAME)  VALUES ('AC','ACTIVE');
INSERT INTO STATUS(STATUS_CODE,STATUS_NAME)  VALUES ('IN','INACTIVE');

INSERT INTO QUALIFICATION_STATUS (QUALIFICATION_CODE,QUALIFICATION_NAME )  VALUES ('QF','QUALIFIED');
INSERT INTO QUALIFICATION_STATUS (QUALIFICATION_CODE,QUALIFICATION_NAME )  VALUES ('NQ','NON QUALIFIED');

<!-- newly added  -->
INSERT INTO MENU VALUES(1,'Supplier',1,1,'/medley/api/suppliers');
INSERT INTO MENU VALUES(2,'Customer',1,1,'/medley/api/customers');

insert into ROLE_MENU (1,1);
insert into ROLE_MENU (1,2);

