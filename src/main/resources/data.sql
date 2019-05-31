insert into person(id,name,age,address) values(hibernate_sequence.nextval,'汪云飞',32,'合肥');
insert into person(id,name,age,address) values(hibernate_sequence.nextval,'xx',31,'北京');
insert into person(id,name,age,address) values(hibernate_sequence.nextval,'yy',30,'上海');
insert into person(id,name,age,address) values(hibernate_sequence.nextval,'zz',29,'南京');
insert into person(id,name,age,address) values(hibernate_sequence.nextval,'xx',28,'武汉');
insert into person(id,name,age,address) values(hibernate_sequence.nextval,'bb',27,'合肥');

INSERT into SYS_USER (id,username,password)VALUES (1,'wyf','wyf');
INSERT into SYS_USER (id,username,password)VALUES (2,'wisely','wisely');

INSERT into SYS_ROLE (id,name)VALUES (1,'ROLE_ADMIN');
INSERT into SYS_ROLE (id,name)VALUES (2,'ROLE_USER');

INSERT into SYS_USER_ROLES (SYS_USER_ID,ROLES_ID)VALUES (1,1);
INSERT into SYS_USER_ROLES (SYS_USER_ID,ROLES_ID)VALUES (2,2);
