# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "BLOG_ENTRY" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"title" VARCHAR NOT NULL,"content" text NOT NULL,"date" DATE NOT NULL,"author" BIGINT NOT NULL);
create table "BLOG_ENTRY_TOPIC" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"from" BIGINT NOT NULL,"to" BIGINT NOT NULL);
create table "TOPIC" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"title" VARCHAR NOT NULL,"description" text NOT NULL);
create table "USER" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"username" VARCHAR NOT NULL,"email" VARCHAR NOT NULL,"password" VARCHAR NOT NULL,"lastlogin" DATE);
alter table "BLOG_ENTRY_TOPIC" add constraint "blog_entry_topic_toFk" foreign key("to") references "TOPIC"("id") on update NO ACTION on delete CASCADE;
alter table "BLOG_ENTRY_TOPIC" add constraint "blog_entry_topic_fromFk" foreign key("from") references "BLOG_ENTRY"("id") on update NO ACTION on delete CASCADE;

# --- !Downs

alter table "BLOG_ENTRY_TOPIC" drop constraint "blog_entry_topic_toFk";
alter table "BLOG_ENTRY_TOPIC" drop constraint "blog_entry_topic_fromFk";
drop table "BLOG_ENTRY";
drop table "BLOG_ENTRY_TOPIC";
drop table "TOPIC";
drop table "USER";

