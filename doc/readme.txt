--家庭办公系统数据库准备语句
--1.建表空间
create tablespace lifeoa_data
logging
datafile 'D:/oracle/lifeoa/lifeoa_data.DBF'
size 32m
autoextend on
next 32m
extent management local;

--2.创建用户并指定空间
create user lifeoa
identified by lifeoa
default tablespace lifeoa_data;

grant create any view,drop any view,
connect,resource,create session to lifeoa;












