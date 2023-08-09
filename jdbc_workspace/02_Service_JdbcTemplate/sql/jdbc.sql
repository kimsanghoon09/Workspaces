--member 삭제 테이블 생성
--기존 컬럼에서 del_date컬럼 추가
-- localtimestamp 지역대 없는 timestamp 
-- systimestamp, current_timestamp 지역대 정보 포함
create table member_del
as
select 
    m.*, 
    localtimestamp del_date  
from 
    member m
where 
    1 = 0;

--drop table member_del;
desc member_del;

--기본값 추가
alter table member_del
modify del_date default systimestamp;


--삭제트리거 생성
--resource 롤에 create trigger권한이 있기때문에 별도의 DCL없이 진행할 수 있음.
create or replace trigger trig_delete_member
    before 
    delete on member
    for each row
begin
    insert into 
        member_del
    values(
        :old.id, 
        :old.name, 
        :old.gender, 
        :old.birthday, 
        :old.email, 
        :old.point,
        :old.reg_date, 
        default);
end;
/

--데이터확인
select * from member_del;
