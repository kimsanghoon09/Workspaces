-- student 계정 생성  @system
alter session set "_oracle_script" = true;

create user student 
identified by student
default tablespace users;

grant connect, resource to student;

alter user student quota unlimited on users;

-- member테이블 생성 @student
create table member (
    id varchar2(20),
    name varchar2(100) not null,
    gender char(1),
    birthday date,
    email varchar2(500) not null,
    point number default 1000,
    reg_date timestamp default systimestamp,
    constraints pk_member_id primary key (id),
    constraints uq_member_email unique(email),
    constraints ck_member_gender check(gender in ('M', 'F'))
);


insert into
    member
values(
    'honggd', '홍길동', 'M', '1999-09-09', 'honggd@naver.com', default, default
);
insert into
    member
values(
    'gogd', '고길동', 'M', '1980-02-15', 'gogd@naver.com', default, default
);
insert into
    member
values(
    'sinsa', '신사임당', 'F', '1995-05-05', 'sinsa@naver.com', default, default
);
insert into
    member
values(
    'leess', '이순신', null, null, 'leess@naver.com', default, default
);
insert into
    member
values(
    'qwerty', '쿼티', 'F', null, 'qwerty@naver.com', default, default
);

commit;

select * from member order by reg_date desc;
select * from member where id = 'honggd';









