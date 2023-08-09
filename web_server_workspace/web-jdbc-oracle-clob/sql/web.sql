--drop table news;
create table news (
    id number,
    content clob,
    constraints pk_news_id primary key(id)
);
--drop sequence seq_news_id;
create sequence sesq_news_id;

select * from news;
select id, dbms_lob.getlength(content) from news;
select id, dbms_lob.substr(content, dbms_lob.getlength(content)) from news; -- 조회실패 null로 출력
-- 한번에 출력하기
select 
    id, 
    dbms_lob.getlength(content) content_length,
    dbms_lob.substr(content, 4000, 1) content1, -- max 4000byte
    dbms_lob.substr(content, 4000, 4001) content2,
    dbms_lob.substr(content, 2000, 8001) content2
from news; 
