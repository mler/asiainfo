-- Create table
create table SYJ_TABLE_BEAN
(
  ID               NUMBER(11) not null,
  URL              VARCHAR2(200),
  TABLE_NAME       VARCHAR2(200),
  TABLE_CLASS      VARCHAR2(200),
  TITLE            VARCHAR2(200),
  STATUS           VARCHAR2(200),
  TOTAL            NUMBER(11) default 0,
  CORRECT          NUMBER(11) default 0,
  ERROR            NUMBER(11) default 0,
  CREATE_TIME      DATE,
  CREATE_EMP_CODE  VARCHAR2(200),
  UDDATE_TIME      DATE,
  UPDATE_TIME_CODE VARCHAR2(200)
);
-- Add comments to the table 
comment on table SYJ_TABLE_BEAN
  is '食药监数据抓取映射表';
-- Add comments to the columns 
comment on column SYJ_TABLE_BEAN.ID
  is '编号';
comment on column SYJ_TABLE_BEAN.URL
  is '链接';
comment on column SYJ_TABLE_BEAN.TABLE_NAME
  is '表名';
comment on column SYJ_TABLE_BEAN.TABLE_CLASS
  is '类名';
comment on column SYJ_TABLE_BEAN.TITLE
  is '标题';
comment on column SYJ_TABLE_BEAN.STATUS
  is '状态';
comment on column SYJ_TABLE_BEAN.TOTAL
  is '总数量';
comment on column SYJ_TABLE_BEAN.CORRECT
  is '正确数量';
comment on column SYJ_TABLE_BEAN.ERROR
  is '错误数量';
comment on column SYJ_TABLE_BEAN.CREATE_TIME
  is '创建时间';
comment on column SYJ_TABLE_BEAN.CREATE_EMP_CODE
  is '创建人';
comment on column SYJ_TABLE_BEAN.UDDATE_TIME
  is '更新时间';
comment on column SYJ_TABLE_BEAN.UPDATE_TIME_CODE
  is '更新人';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SYJ_TABLE_BEAN
  add constraint PK_SYJ_TABLE_BEAN primary key (ID);
