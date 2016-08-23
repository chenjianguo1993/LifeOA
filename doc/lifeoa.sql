--1.用户表

create table SYSTEM_USERINFO
(
  id             NUMBER not null,
  username       NVARCHAR2(20),
  userpassword   NVARCHAR2(34),
  sex            NVARCHAR2(2),
  email          NVARCHAR2(20),
  qq             NVARCHAR2(20),
  isenable       NUMBER,
  score          NUMBER,
  createtime     DATE,
  isbetter       NUMBER default 0,
  onlinetime     NUMBER default 0,
  content        NVARCHAR2(1000),
  image          NVARCHAR2(200),
  tel            NVARCHAR2(20),
  address        NVARCHAR2(200),
  position       NVARCHAR2(20),
  belong_unit    NUMBER,
  compellation   NVARCHAR2(20),
  signature_pic  NVARCHAR2(200),
  remarks        NVARCHAR2(1000),
  is_bbs_manager NUMBER default 0
)
;
comment on table SYSTEM_USERINFO
  is '用户信息,登陆用户管理';
comment on column SYSTEM_USERINFO.username
  is '用户帐号';
comment on column SYSTEM_USERINFO.userpassword
  is '密码';
comment on column SYSTEM_USERINFO.sex
  is '性别 1为男,2为女';
comment on column SYSTEM_USERINFO.email
  is '邮箱';
comment on column SYSTEM_USERINFO.qq
  is 'QQ';
comment on column SYSTEM_USERINFO.isenable
  is '是否可用,1可用,0不可用';
comment on column SYSTEM_USERINFO.score
  is '积分';
comment on column SYSTEM_USERINFO.createtime
  is '生成时间';
comment on column SYSTEM_USERINFO.isbetter
  is '是否为高级用户,1是,0不是';
comment on column SYSTEM_USERINFO.onlinetime
  is '是线时长';
comment on column SYSTEM_USERINFO.content
  is '身份证号码MD5加密,其它备注';
comment on column SYSTEM_USERINFO.image
  is '图片';
comment on column SYSTEM_USERINFO.tel
  is '联系电话';
comment on column SYSTEM_USERINFO.address
  is '地址';
comment on column SYSTEM_USERINFO.position
  is '职位';
comment on column SYSTEM_USERINFO.belong_unit
  is '所属单位,0为系统管理员用,非0为有单位的用';
comment on column SYSTEM_USERINFO.compellation
  is '姓名';
comment on column SYSTEM_USERINFO.signature_pic
  is '电子签名';
comment on column SYSTEM_USERINFO.remarks
  is '备注';
comment on column SYSTEM_USERINFO.is_bbs_manager
  is '是否为BBS管理员,1是，0不是';
alter table SYSTEM_USERINFO
  add constraint PK_SYS_USERINFO primary key (ID);