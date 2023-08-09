-- @web 계정
create table student(
    id number,
    name varchar2(100) not null,
    tel char(11) not null,
    created_at date default sysdate,
    constraints pk_student_id primary key(id)
);
create sequence seq_student_id;

insert into student values(seq_student_id.nextval, '홍길동', '01012341234', default);
insert into student values(seq_student_id.nextval, '신사임당', '01033334444', default);
insert into student values(seq_student_id.nextval, '강감찬', '01055556666', default);

select * from student order by id desc;

--delete from student where name like '유관순%';

-- 페이징을 위해 행추가
insert all
into student values (seq_student_id.nextval, name, tel, default)
select * from student;

-- web계정 -> sh계정
-- sh/system계정에서 web계정에게 다음 테이블에 대한 권한 부여
grant select on sh.employee to web;
grant select on sh.department to web;
grant select on sh.job to web;
grant select on sh.sal_grade to web;

select * from sh.employee;
select * from sh.department;
select * from sh.job;
select * from sh.sal_grade;

-- synonym 동의어 객체 생성
-- create synonym 권한 부여
grant create synonym to web;

-- 별칭 객체
create synonym emp for sh.employee;
create synonym dept for sh.department;
create synonym job for sh.job;
create synonym sal for sh.sal_grade;

select * from emp;
select * from dept;
select * from job;
select * from sal;







