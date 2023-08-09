-- .sql 한줄 주석
/*
    sql 여러줄 주석
*/
show user;
SHOW USER;

-- sql 실행은 코드 작성후 Ctrl-Enter로 가능하다. 서버로 sql문을 전송해서 응답결과를 출력한다.
-- 어떤 계정을 접속해서 어떤 쿼리를 실행하는 지가 중요하다. 같은 쿼리라도 접속한 계정에 따라 결과가 달라질 수 있다.
-- 쿼리는 저장된 데이터를 제외하고는 대소문자를 구분하지 않는다.
-- 이클립스, 다른 개발툴와 단축키가 다른 경우가 있으므로 주의해야 한다. 

---------------------------------------------------
-- sh계정 생성 @system
---------------------------------------------------
-- oracle을 설치하면 자동으로 sys(슈퍼관리자), system(일반관리자) 관리자계정이 만들어진다.
-- sys는 일반관리자 기능 + db생성권한을 가지고 있다. (특별한 경우가 아니면 사용금지)
-- system는 계정생성, 타계정관리, 기타 db객체를 관리

-- 65096. 00000 -  "invalid common user or role name" 
-- 12c 이후에 일반사용자는 c## 접두사를 사용을 강제하고 있다. c##sh
-- 우회설정
alter session set "_oracle_script" = true; -- session은 현재 계정의 접속을 가리킨다.

create user sh  -- 계정명
identified by sh -- 비밀번호 (대소문자 구분)
default tablespace users; -- tablespace는 실제 데이터가 저장된 db내의 공간이름. system / users / ...

-- 상태: 실패 -테스트 실패: ORA-01045: 사용자 SH는 CREATE SESSION 권한을 가지고있지 않음; 로그온이 거절되었습니다
-- 계정이 존재해도 접속권한(create session)이 없다면 db에 접속할 수 없다.
--grant create session, create table to sh;
grant connect, resource to sh; -- connect롤 (권한 묶음)

alter user sh quota unlimited on users; -- users tablespace 데이터저장할 용량 무한으로 설정

-- 테이블 생성 시도 @sh
-- 01031. 00000 -  "insufficient privileges" 권한이 불충분합니다
create table abc (
    id number
);
-- drop table abc; -- 테이블 삭제

--==============================================
-- 전사 테이블 설정 @sh
--==============================================
-- SQL Structured Query Language 구조화 질의 언어
-- db server의 데이터 및 객체에 대한 조회/생성/수정/삭제 처리하는 언어

-- sh계정이 가진 모든 테이블 조회
select * from tab; -- tab테이블에서 * 모든 컬럼을 조회

select * from employee; -- 사원정보
select * from department; -- 부서정보
select * from job; -- 직급정보
select * from location; -- 지역정보
select * from nation; -- 국가정보 
select * from sal_grade; -- 급여등급

-- table/entity/relation 테이블. 데이터를 보관하는 db객체. 행과 열로 구분되어 있다.
-- column/attribute/field 열. 테이블의 구조. 각 컬럼마다 자료형을 지정.
-- row/tuple/record 행. 테이블 하위에 보관되는 데이터의 단위. 사원한명 정보는 employee의 한 행이 된다.
-- domain 하나의 컬럼이 취할수 있는 원자값의 범위(집합). 성별컬럼의 도메인은 남/여이다.

--==============================================
-- DATA TYPE
--==============================================
-- 1. 문자형
-- 2. 숫자형
-- 3. 날짜형

--------------------------------------------------------
-- 1. 문자형
--------------------------------------------------------
-- char 고정형 문자타입. 최대 2000byte
-- varchar2 가변형 문자타입. 최대 4000byte
-- nchar 유니코드 고정형 문자타입. 최대 2000byte
-- nvarchar2 유니코드 가변형 문자타입. 최대 4000byte
-- long 가변형 문자타입. 최대 2gb (deprecated)
-- clob 가변형 문자타입. 최대 4gb (character large object)

-- char(10)  고정형 10byte. 
--      'korea' 저장시 실제값은 5byte지만, 저장된 데이터는 10byte. 
--      '안녕' 저장시 실제값은 6byte지만, 저장된 데이터는 10byte.                            
--      '잘가잘가' 저장시 실제값이 12byte라서 저장실패.
-- varchar2(10)  가변형 10byte. 
--      'korea' 저장시 실제값은 5byte이고, 저장된 데이터는 5byte. 
--      '안녕' 저장시 실제값은 6byte이고, 저장된 데이터는 6byte.                            
--      '잘가잘가' 저장시 실제값이 12byte라서 저장실패.

-- 한글저장시 xe버젼은 글자당 3byte지만, 상업용버젼은 글자당 2byte씩 처리.
-- sql은 대소문자를 구분하지 않으므로 tblDatatypeChar가 아닌 tbl_datatype_char와 같이 snake casing 처리한다.
create table tbl_datatype_char (
    a char(10),
    b varchar2(10)
);
select * from tbl_datatype_char;
select a, b from tbl_datatype_char;
select a, b, lengthb(a), lengthb(b)  from tbl_datatype_char;

-- 데이터 추가(행단위)
insert into tbl_datatype_char values('hello', 'hello');
insert into tbl_datatype_char values('안녕', '안녕');
insert into tbl_datatype_char values('대한민국', 'hello'); -- ORA-12899: "SH"."TBL_DATATYPE_CHAR"."A" 열에 대한 값이 너무 큼(실제: 12, 최대값: 10)

--------------------------------------------------------
-- 2. 숫자형
--------------------------------------------------------
-- 정수/실수를 구분하지 않는다.
-- number[(precision, scale)]
-- precision : 표현가능한 전체자리수 (1 ~ 38)
-- scale : 소수점이하 자리수 (-84 ~ 127)
-- 1234.567 값에 대한 처리
-- number  1234.567 저장
-- number(10) 1235 저장 (반올림)
-- number(10, 1) 1234.6 저장
-- number(10, -2)  1200 저장
create table tbl_datatype_number (
    a number,
    b number(10),
    c number(10, 1),
    d number(10, -2)
);
insert into tbl_datatype_number values(1234.567, 1234.567, 1234.567, 1234.567);
select * from tbl_datatype_number;

select 10 / 3 from dual;

-----------------------------------------
-- 3. 날짜형
-----------------------------------------
-- date 년월일시분초 
-- timestamp 년월일시분초 + 밀리초 (지역대)
-- tiemstamp with timezone
select sysdate,  to_char(sysdate, 'yyyy-mm-dd hh:mi:ss'), systimestamp from dual;  -- dual 1행짜리 가상테이블 

create table tbl_datatype_date (
    a date,
    b timestamp
);

-- DML :  db 테이블에 직접 추가가 아니라, memory상의 작업이 우선 발생한다.
-- commit 실제 db 적용 | rollback 마지막 commit 시점이후 작업 모두 취소
insert into tbl_datatype_date values (sysdate, systimestamp);

select to_char(a, 'yy/mm/dd hh:mi:ss'), b from tbl_datatype_date;
commit;
rollback;

-- 날짜형끼리의 산술연산을 지원 (단위 : 1일)
-- 날짜 +/- 숫자 -> 날짜형
-- 날짜 - 날짜 -> 숫자형(날짜차이) 
select
    to_char(sysdate - 1, 'yy/mm/dd hh:mi:ss'), -- 현재시각 - 1일
    to_char(sysdate + 100, 'yy/mm/dd hh:mi:ss'),
    to_date('23/09/05', 'yy/mm/dd') - sysdate
from
    dual;

-- 테이블의 구조확인 (컬럼 - 자료형)
desc employee;

--============================================
-- SQL 
--============================================
-- 데이터 정의어 : DDL (Data Definition Language) db객체에 생성 create/수정 alter/삭제 drop
-- 데이터 조작어 : DML (Data Manipulation Language) 테이블 행을 생성 insert/수정 update/삭제 delete
--		                  데이터추출  DQL (Data Query Language)를 보통 포함한 개념. 테이블 행조회 select
-- 데이터 제어어 : DCL (Data Control Language) 권한 부여 grant/회수 revoke
--                        트랜잭션 제어 TCL (Transaction Control Language) 변경사항 적용 commit/취소 rollback


--============================================
-- DQL 1
--============================================
/*
    select (필수) 조회할 컬럼 나열 (5)
    from (필수) 대상 테이블  (1)
    where 행에 대한 조건절  (2)
    group by 행를 그룹핑 (3)
    having 그룹핑된 행에 대한 조건절 (4)
    order by 정렬기준 컬럼 나열 (6)
*/
select
    *
    --emp_name, dept_code
from 
    employee
where
    dept_code = 'D5'
order by 
    emp_name asc;
    
--1. JOB테이블에서 JOB_NAME의 정보만 출력되도록 하시오.
select
    job_name
from
    job;
--2. DEPARTMENT테이블의 내용 전체를 출력하는 SELECT문을 작성하시오.
select
    *
from
    department;
--3. EMPLOYEE 테이블에서 이름(EMP_NAME),이메일(EMAIL),전화번호(PHONE),고용일(HIRE_DATE)만 출력하시오
select
    emp_name, 
    email, 
    phone, 
    hire_date
from
    employee;
--4. EMPLOYEE 테이블에서 고용일(HIRE_DATE) 이름(EMP_NAME),월급(SALARY)를 출력하시오
select
    hire_date, emp_name, salary
from
    employee
order by
    hire_date asc;
--5. EMPLOYEE 테이블에서 월급(SALARY)이 2,500,000원이상인 사람의 EMP_NAME 과 SAL_LEVEL을 출력하시오 
--    (힌트 : >(크다) , <(작다) 를 이용)
select
    emp_name, sal_level
from
    employee
where
    salary >= 2500000
order by 
    sal_level desc;

--6. EMPLOYEE 테이블에서 월급(SALARY)이 350만원 이상이면서 
--    JOB_CODE가 'J3' 인 사람의 이름(EMP_NAME)과 전화번호(PHONE)를 출력하시오
--    (힌트 : AND(그리고) , OR (또는))
select
    emp_name, phone -- 이름(EMP_NAME)과 전화번호(PHONE)를 출력
from
    employee -- EMPLOYEE 테이블에서
where
    salary >= 3500000 -- 월급(SALARY)이 350만원 이상이면서
  and
    job_code = 'J3'; -- JOB_CODE가 'J3' 인 사람
    
---------------------------------------------
-- SELECT
---------------------------------------------
-- 조회할 컬럼 나열
-- 가상컬럼값 출력 - 연산결과, 컬럼값 이어붙이기, 리터럴

-- 연봉 salary * 12
select
    emp_name, 
    salary, 
    bonus,
    nvl(bonus, 0),
--    (salary * 12), 
    (salary + (salary * nvl(bonus, 0))) * 12, 
    '원' "단위"
from
    employee;
    
-- null과는 산술연산(+ - * /) 할 수 없다. 결과가 무조건 null이다.
select
    1 + null,
    1 - null,
    1 * null,
    1 / null
from
    dual;

-- null처리 함수 
-- nvl(col, null_value) : col이 null이 아니면 col 사용, col이 null이면 null_value를 사용
select
    nvl('안녕', '잘가'),
    nvl(null, '잘가')
from
    dual;

-- 별칭 alias 
-- 컬럼의 이름을 변경  : 컬럼 as "별칭"
-- as키워드, ""는 생략가능 (숫자로 시작 또는 특수문자가 포함된 별칭인 경우는 ""는 생략할 수 없다.)
select
    emp_name as "이름",
    salary as "급여",
    phone 전화번호,
    phone "사원 전화번호"
from
    employee;

-- distinct 중복제거기능
-- select절에 단한번만 사용할 수 있다.
-- 여러개의 컬럼에 적용하면, 여러개의 컬럼을 합쳐서 유일하게 처리
select distinct
    dept_code,
    job_code
from    
    employee;

-- 문자열 연결연산
-- +는 산술연산에만 사용가능하다.
-- || 문자열의 연결연산
select
    emp_name,
--    salary + '원'
    salary || '원' "급여(단위)"
from
    employee;

-- + 산술연산시 좌/우항의 값을 모두 숫자로 간주해 처리한다. (자동형변환)
select
    1 + 2, -- 3
    '1' + '2' -- 3
from
    dual;

----------------------------------------------------
-- WHERE
----------------------------------------------------
-- 결과집합 result_set 에 포함될 행을 필터링하는 조건절
-- 조건식의 결과가 true인 경우만 결과집합에 포함된다.
select
    *
from
    employee
where
    dept_code = 'D5';

-- and or 연산자
-- (조건식) and (조건식)
-- (조건식) or (조건식)
-- 부서코드가 D6이고, 급여를 2000000원보다 많이 받는 사원 조회
select
    *
from
    employee
where
    (dept_code = 'D6') 
    and
    (salary > 2000000);

-- 직급코드가 J3, J4인 사원 조회
select
    *
from
    employee
where
    (job_code = 'J3') or (job_code = 'J4');
    
-- 부서코드가 D5가 아닌 사원 조회
-- != <>  ^= 
-- not ()
select 
    *
from
    employee
where
--    dept_code != 'D5';
--    dept_code <> 'D5';
--    dept_code ^= 'D5';
    not (dept_code = 'D5');

-- 범위연산자 between 값1 and 값2
-- (숫자) 값1 이상 값2 이하를 조회
-- (날짜)  값1 부터 값2 까지

-- 급여 250만원이상 350만원이하인 사원 조회
select
    *
from
    employee
where
--    salary between 2500000 and 3500000;
    (salary >= 2500000) and (salary <= 3500000);

-- 입사일이 90년 1월 1일~ 2001년 1월 1일인 사원 조회
select
    *
from
    employee
where
--    hire_date between '90/01/01' and '00/01/01'; -- 2000년 1월 1일 자정
    hire_date not between '95/01/01' and '00/01/01';
-- 날짜형식의 문자열은 자동으로 날짜형 변환처리된다. 되도록 to_date함수 사용할 것!

-- 문자열 패턴비교
-- like  '비교패턴'
-- _ 임의의 글자 하나
-- % 임의의 글자 0개이상
-- 이름이 세글자이고 가운데글자가 '옹'인 사원조회
select
    *
from
    employee
where
    emp_name like '_옹_';

-- 이름이 '식'으로 끝나는 사원 조회
select
    *
from
    employee
where
    emp_name like '%식';
    
-- 이름에 '이'가 들어가는 사원 조회
select
    *
from
    employee
where
    emp_name like '%이%';
    
-- escaping
-- 이메일 _앞의 글자수가 3개인 사원 조회
select
    *
from
    employee
where
    email like '___#_%' escape '#'; -- escaping 문자 지정. \ 추천

-- null 비교
-- null은 연산불가. 산술연산 / 비교연산 불가
select
    *
from
    employee
where
--    dept_code = null; -- Not Working
--    dept_code is null;
    dept_code is not null;    

-- 비교연산자 in
-- 값목록 포함여부
-- D5, D9 부서원 조회
select
    *
from
    employee
where
--    dept_code in ('D5', 'D9');
    dept_code not in ('D5', 'D9');

--------------------------------------------------
-- ORDER BY
--------------------------------------------------
-- 컬럼별 정렬기능.
-- 컬럼명, 별칭, select절의 컬럼순서등으로 지정
-- asc (기본값) | desc
-- 오름차순 ascending 내림차순 desceding
-- 숫자, 문자, 날짜(과거-미래) 모두 지원

select 
    emp_name "사원명",
    dept_code "부서코드"
from
    employee
order by
--    dept_code asc, emp_name asc;
--    부서코드, 사원명;
    2, 1;

--@실습문제
--1. EMPLOYEE 테이블에서 이름 끝이 연으로 끝나는 사원의 이름을 출력하시오
select emp_name from employee where emp_name like '%연';
--2. EMPLOYEE 테이블에서 전화번호 처음 3자리가 010이 아닌 사원의 이름, 전화번호를 출력하시오
select emp_name, phone from employee where phone not like '010%';
--3. EMPLOYEE 테이블에서 메일주소 '_'의 앞이 4자이면서, DEPT_CODE가 D9 또는 D6이고
--고용일이 90/01/01 ~ 00/12/01이면서, 월급이 270만원인 사원의 전체 정보를 출력하시오
SELECT 
	* 
FROM 
	EMPLOYEE 
WHERE 
	(EMAIL LIKE '____#_%' ESCAPE '#') 
    AND (DEPT_CODE='D9' OR DEPT_CODE='D6') 
    AND (HIRE_DATE BETWEEN to_date('90/01/01', 'rr/mm/dd') AND to_date('00/12/01', 'rr/mm/dd'))
    AND (SALARY >= 2700000);


-- 4. tbl_escape_watch 테이블에서 description 컬럼에 99.99% 라는 글자가 들어있는 행만 추출하세요.

create table tbl_escape_watch(
  watchname   varchar2(40)
 ,description    varchar2(200)
);
--drop table tbl_escape_watch;
insert into tbl_escape_watch values('금시계', '순금 99.99% 함유 고급시계');
insert into tbl_escape_watch values('은시계', '고객 만족도 99.99점를 획득한 고급시계');
commit;
select * from tbl_escape_watch;

select *
from tbl_escape_watch
where description like '%99.99\%%' escape '\'; 


--=================================================
-- BUILT-IN FUNCTION
--=================================================
-- function 함수. 일련의 작업절차를 모아 선언후에 호출, 처리결과를 기대할 수 있는 프로그램
-- method 객체안에 포함된 function
-- sql의 함수는 반드시 리턴값이 있다.

-- A. 단일행 처리함수 : 각행별로 처리되는 함수
--   1. 문자함수
--   2. 숫자함수
--   3. 날짜함수
--   4. 형변환함수
--   5. 기타함수
-- B. 그룹함수 : 행을 그룹지어 처리하는 함수

---------------------------------------------------
-- 문자함수
---------------------------------------------------
-- length 문자열의 길이
-- lengthb 문자열의 byte수
select
    email,
    length(email),
    lengthb(email),
    emp_name,
    length(emp_name),
    lengthb(emp_name)
from
    employee;

-- instr(문자열, 검색문자열[, 시작위치[, 발생횟수]]) : 인덱스 반환
select
    instr('kh정보교육원 국가정보원 정보문화사', '정보'), -- 3
    instr('kh정보교육원 국가정보원 정보문화사', '정보', 8), -- 11
    instr('kh정보교육원 국가정보원 정보문화사', '정보', 1, 2), -- 11
    instr('kh정보교육원 국가정보원 정보문화사', '정보', -1), -- 15
    instr('kh정보교육원 국가정보원 정보문화사', 'ㅋㅋㅋㅋㅋㅋ') -- 0
from
    dual;

-- substr(문자열, 시작위치[, 길이]) : 잘라낸 문자열 반환
select
    substr('Show me the money~!', 6, 2), --me
    substr('Show me the money~!', 6), -- me the money~!
    substr('Show me the money~!', -7) -- money~!
from 
    dual;

-- @실습문제 - 이메일에서 아이디를 추출하세요
-- '@' 위치 파악 - 기준으로 잘라내기
select
    email,
    instr(email, '@') -  1 "아이디의 길이",
    substr(email, 1, instr(email, '@') -  1) "아이디"
from
    employee;

-- lpad | rpad (문자열, 길이, 패딩문자)
-- 왼쪽|오른쪽에 padding 문자를 작성해 고정길이의 문자열을 반환
select
    lpad(email, 20, '#'),
    rpad(email, 20, '#')
from
    employee;
    
-- 주문번호 kh-230420-00123 작성
select
    lpad(12, 5, '0'),
    lpad(123, 5, '0'),
    'kh-' || to_char(sysdate, 'yymmdd') || '-' || lpad(123, 5, '0') 주문번호
from
    dual;
    
-- @실습문제 : 남자사원의 사번, 사원명, 주민번호, 연봉을 조회
-- 주민번호 뒤 6자리는 ******로 표시
select
    emp_id,
    emp_name,
    substr(emp_no, 1, 8 ) || '******' "emp_no",
    (salary + salary * nvl(bonus, 0)) * 12 "annul_pay"
from
    employee
where
    substr(emp_no, 8, 1) in ('1', '3');
    
---------------------------------------------
-- 숫자처리함수
---------------------------------------------
-- abs : 절대값 반환
select
    abs(-10), abs(10)
from
    dual;

-- mod : %연산자와 동일. 나머지를 반환
select
    mod(10, 3)
from
    dual;
    
-- 짝수년도 입사자 조회
select
    emp_name,
    hire_date,
    extract(year from hire_date) hire_year
from
    employee
where
    mod(extract(year from hire_date), 2) = 0;

-- ceil : 소수점기준 올림수 반환
select
    ceil(123.456),
    ceil(123.456 * 100) / 100 "123.46" -- 올림해서 소수점이하 둘째자리까지 표현
from
    dual;
-- floor : 소수점기준 내림수 반환
-- trunc(숫자, 소수점이하자리수) : 버림수 반환
select
    floor(45.67),
    floor(45.67 * 10) / 10 "45.6",
    trunc(45.67, 1)
from
    dual;
    
-- round(숫자, 소수점이하자리수) : 반올림수 반환
select
    round(123.456),
    round(123.456, 1),
    round(123.456, -1)
from
    dual;


---------------------------------------------
-- 날짜 처리 함수
---------------------------------------------
--날짜관련 키워드
select
    sysdate, -- db 시스템(os) 
    systimestamp,  -- db 시스템(os) 
    current_date, -- 접속한 session기준 
    current_timestamp -- 접속한 session기준 
from
    dual;

-- add_months(date, number) : 날짜반환
select
    sysdate,
    add_months(sysdate, 12),
    add_months('23/01/31', 1), -- 2/28 해당달의 말일을 반환
    add_months('23/02/28', 1), -- 3/31 해당달의 말일을 반환
    add_months(sysdate, -1)
from
    dual;
    
-- months_between(미래날짜, 과거날짜) : 개월수 차이 반환
select
    trunc(months_between('23/09/05', sysdate), 1)  -- 4.5개월
from
    dual;
    
--@실습문제 : 이름, 입사일, 근무개월수(n개월), 근무개월수(x년 y개월)
-- 퇴사자 제외

select
    emp_name,
    hire_date,
    trunc(months_between(sysdate, hire_date)) || '개월' "근무개월수",
    -- trunc(15 / 12) - trunc(mod(15, 12))
    trunc(months_between(sysdate, hire_date)/12) || '년' || trunc(mod(months_between(sysdate, hire_date),12)) || '개월' "근무개월수"
from
    employee
where
    quit_yn != 'Y';

-- extract : 날짜형에서 형식별로 숫자로 추출
select
    extract(year from sysdate) 년,
    extract(month from sysdate) 월,
    extract(day from sysdate) 일,
    extract(hour from cast(sysdate as timestamp)) 시, -- timestamp로 변환후 추출
    extract(minute from cast(sysdate as timestamp)) 분, 
    extract(second from cast(sysdate as timestamp)) 초
from
    dual;

-- 1990년도 입사자만 조회
select
    emp_name, hire_date
from
    employee
where
    extract(year from hire_date) = 1990;

-- trunc : 시분초 제거
select
    to_char(sysdate, 'yy/mm/dd hh24:mi:ss'),
    to_char(trunc(sysdate), 'yy/mm/dd hh24:mi:ss')
from
    dual;


------------------------------------------------------
-- 형변환함수
------------------------------------------------------
/*
            to_char       to_date
         --------->  --------->
    number          char           date
        <---------   <--------
            to_number      to_char

*/

-- to_char(date, format)
select
    to_char(sysdate, 'yy/mm/dd hh24:mi:ss day d dy'),
    to_char(sysdate, 'fmyy/mm/dd (dy) hh24:mi:ss'), -- 형식변환 중에 생겨난 공백 또는 0을 제거
    to_char(sysdate, 'yyyy"년" mm"월" dd"일"') -- ORA-01821: 날짜 형식이 부적합합니다
from
    dual;
    
-- to_char(number, format)
-- 세자리콤마, 소수점이하처리
-- 형식문자는 항상 값보다 자리수가 커야 한다.
select
    to_char(1234567, '999,999,999'), -- 9 해당자리수의 숫자가 없다면 공백으로 치환, 소수점이하는 0으로 처리
    to_char(1234567, 'fm999,999,999'), -- 형식문자(9)로 생긴 공백 제거
    to_char(1234567, '999,999'), -- #######
    to_char(1234567, '000,000,000'), -- 0 해당자리수의 숫자가 없다면 0으로 치환
    to_char(123.456, '999.99') -- 소수점이하 반올림 처리
from
    dual;
    
-- to_number 
select
--    '1,234,567' + 33 -- 01722. 00000 -  "invalid number"
    to_number('1,234,567', '9,999,999') + 33
from
    dual;

-- to_date
-- 날짜형식의 문자열은 자동으로 날짜타입 변환이 되기도 하지만, 명시적으로 변환해서 사용하자.
select
    to_date('2023/04/04', 'yyyy/mm/dd') + 1
from
    dual;

select
    *
from
    employee
where
--    hire_date > '2000/01/01';
     hire_date > to_date('2000/01/01', 'yyyy/mm/dd');
    
    
-- @실습문제 - 지정한 특정시각(2018년 2월 8일 12시 23분 50초)에서 3시간 후의 날짜정보를 yyyy/mm/dd hh24:mi:ss 형식으로 화면에 출력
select
    to_char(
        to_date('2018년 2월 8일 12시 23분 50초', 'yyyy"년" mm"월" dd"일" hh24"시" mi"분" ss"초"') + (3 / 24),
        'yyyy/mm/dd hh24:mi:ss') 답
from
    dual;
    
-- 1. 현재시각으로 부터 1일 2시간 3분 4초뒤를 나타내세요.(yyyy-mm-dd hh24:mi:ss) 
select
    to_char (
        sysdate + 1 + (2 / 24) + (3 / 24 / 60) + (4 / 24 / 60 / 60),
        'yyyy/mm/dd hh24:mi:ss') 답
from
    dual;

-- 2. 수료일로부터 d-day구하기 : 수료일은 ??일 남았습니다. 출력
select
    to_char(
        to_date('23/09/05', 'yy/mm/dd'),
        'yyyy/mm/dd hh24:mi:ss'),
    '수료일은 ' || ceil(to_date('23/09/05', 'yy/mm/dd') - sysdate) || '일 남았습니다.' "d-day"
from 
    dual;

SELECT
    to_char(
    TO_DATE('23/04/21', 'yy/mm/dd'),
    'yyyy-mm-dd hh24:mi:ss'),
    '수료일은' || ceil(to_date('23/09/05', 'yy/mm/dd') - SYSDATE) || '일 남았습니다.' "d-day"
FROM
    dual;
    
-----------------------------------------------------
-- 기타함수
-----------------------------------------------------
-- nvl(값, null일때 값)
-- nvl2(값, null아닐때 값, null일때 값)
select
    emp_name,
    (salary  * nvl(bonus, 0)) bonus,
    nvl2(bonus, '있음', '없음') 보너스여부
from 
    employee;


-- 선택함수 decode | case

-- decode(표현식, 값1, 결과1, 값2, 결과2, .... [, 기본값])
-- 사원, 성별 조회
select
    emp_name,
    substr(emp_no, 8, 1),
    decode(substr(emp_no, 8, 1), '1', '남', '2', '여', '3', '남', '4', '여') 성별,
    decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') 성별
from
    employee;

-- case 
/*
-- 문법1(decode와 비슷)

case 표현식
    when 값1 then 결과1
    when 값2 then 결과2
    ...
    [else 기본값]
end 


-- 문법2

case
    when 조건식1 then 결과1
    when 조건식2 then 결과2
    ...
    [else 기본값]
end 



*/
select
    emp_name,
    case substr(emp_no, 8, 1)
        when '1' then '남'
        when '2' then '여'
        when '3' then '남'
        when '4' then '여'
    end "성별",
    case
        when substr(emp_no, 8, 1) = '1' then '남'
        when substr(emp_no, 8, 1) = '2' then '여'
        when substr(emp_no, 8, 1) = '3' then '남'
        when substr(emp_no, 8, 1) = '4' then '여'
    end "성별",
    case
        when substr(emp_no, 8, 1) in ('1', '3') then '남'
        when substr(emp_no, 8, 1) in ('2', '4') then '여'
    end "성별"
from
    employee;

-- 직급코드 검사하여 J1 대표, J2/J3 임원, 나머지 평사원으로 출력
-- 이름 / 직급구분 조회
select
    emp_name,
    job_code,
    decode(job_code, 'J1', '대표', 'J2', '임원', 'J3', '임원', '평사원') 구분,
    case job_code
        when 'J1' then '대표'
        when 'J2' then '임원'
        when 'J3' then '임원'
        else '평사원'
    end 구분,
    case
        when job_code = 'J1' then '대표'
        when job_code in ('J2', 'J3') then '임원'
        else '평사원'
    end 구분
from 
    employee;

--==============================================
-- chun계정 생성 @system
--==============================================
alter session set "_oracle_script" = true;
create user chun 
identified by chun
default tablespace users;
grant connect, resource to chun;
alter user chun quota unlimited on users;


--@실습문제
--파일경로를 제외하고 파일명만 아래와 같이 출력하세요.
    
create table tbl_files (
    fileno number(3)
    ,filepath varchar2(500)
);
insert into tbl_files values(1, 'c:\abc\deft\salesinfo.xls');
insert into tbl_files values(2, 'c:\music.mp3');
insert into tbl_files values(3, 'c:\documents\resume.hwp');
commit;
select * 
from tbl_files;

/*
출력결과 :
--------------------------
파일번호          파일명
---------------------------
1             salesinfo.xls
2             music.mp3
3             resume.hwp
---------------------------
*/
select 
    fileno 파일번호,
    instr(filepath, '\', -1),
    substr(filepath,  instr(filepath, '\', -1) + 1) as "파일명"
from tbl_files;

------------------------------------------------
-- 그룹함수
------------------------------------------------
-- group by등을 이용해서 행별로 그룹핑한후에 그룹에 대해서 함수를 실행
-- 그룹별 반환값이 하나이다.
-- group by 미지정시 전체행이 하나의 그룹으로 간주된다.
-- 그룹함수 결과와 일반컬럼을 동시에 표시할 수 없다. (ORA-00937: 단일 그룹의 그룹 함수가 아닙니다. not a single-group group function)

-- sum(col)
-- 해당 컬럼값이 null인 경우 계산에서 제외된다.
select
    emp_name,
    sum(salary)
from
    employee;

-- 남자사원의 급여의 총합
select
    sum(salary)
from
    employee
where
    decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') = '남';
--    substr(emp_no, 8, 1) in ('1', '3');

-- avg(col)
-- 해당 컬럼값이 null인 경우 계산에서 제외된다.
select
    trunc(avg(salary))
from
    employee;

-- 전사원의 bonus 금액 평균
-- 평균 = 총합 / 개수
select salary * bonus, salary * nvl(bonus, 0) from employee;
select
    avg(salary * bonus),  -- 80만원 (null인 행은 개수에서 제외, /9)
    avg(salary * nvl(bonus, 0)) -- 30만원 (제외되는 행없음, /24)
from 
    employee;

-- count(col)
-- col이 null이 아닌 행의 개수를 반환
-- count(*) 전체행수
select
    count(*), -- 24
    count(emp_name), -- 24
    count(bonus), -- 9
    count(dept_code) -- 22
from
    employee;

-- 퇴사자수 조회
select
    count(quit_date)
from
    employee;

-- D5부서원 조회
select
    count(*)
from
    employee
where
    dept_code = 'D5';
    
-- 인턴사원수 조회
select * from employee;
select count(*) - count(dept_code) from employee;
select count(*) from employee where dept_code is null;
-- 가상컬럼(계산결과에 대해 작동)
select
    count(case when dept_code is null then 100 end),
    sum(case when dept_code is null then 1 end)
from
    employee;

-- max(col) | min(col)
-- 최대/최소값을 조회 (최대/최소값을 가진 행을 조회하는 것이 아니다. 그룹함수는 where절에 사용할 수 없다.
-- 숫자, 문자열, 날짜에 대해 모두 작동.
select
    max(salary), 
    min(salary),
    max(hire_date), -- 가장 입사일이 늦은 값
    min(hire_date), -- 가장 입사일이 빠른 값
    max(emp_name),
    min(emp_name)
from
    employee;


--==========================================    
-- DQL2
--==========================================
-- group by
-- having

---------------------------------------------
-- GROUP BY
---------------------------------------------
-- 행을 그룹핑할 기준컬럼을 제시. 1개이상일 수 있다.
-- 컬럼 작성순서는 상관이 없다.
-- 기준컬럼이 null인 경우도 하나의 그룹으로 처리된다.

-- 부서별 급여평균 조회
select * from employee;
select
    dept_code,
    sum(salary),
    count(*),
    avg(salary)
from
    employee
group by
    dept_code;

-- 직급별 사원수를 조회 (직급코드로 정렬 출력)
select
    job_code,
--    emp_name, -- ORA-00979: GROUP BY 표현식이 아닙니다.
    count(*)
from
    employee
group by 
    job_code
order by
    2 desc, 1 asc;
    
-- 가상컬럼에 대해서 그룹핑가능
-- 입사년도별로 인원수 조회
select
    extract(year from hire_date) hire_year,
    count(*)
from
    employee
group by 
    extract(year from hire_date)
order by 
    hire_year;

-- 성별 인원수 조회
select 
    e.*,
    decode(substr(emp_no, 8, 1), '1', 'M', '3', 'M', 'F')
from 
    employee e;
    
select
    decode(substr(emp_no, 8, 1), '1', 'M', '3', 'M', 'F') "gender",
    count(*)
from
    employee
group by
    decode(substr(emp_no, 8, 1), '1', 'M', '3', 'M', 'F');

---------------------------------------------
-- HAVING
---------------------------------------------
-- 그룹핑한 결과에 대한 조건절을 작성
-- group by를 작성한 경우만 쓸 수 있다.
-- 그룹함수를 사용할 수 있다.

-- 부서별 급여 평균이 300만원 이상인 부서조회
select
    dept_code,
    trunc(avg(salary)) "avg_sal"
from
    employee
group by
    dept_code
having
    avg(salary) >= 3000000;
    
-- 부서별 인원수가 3명보다 많은 부서 조회
select
    dept_code,
    count(*)
from
    employee
group by
    dept_code
having
    count(*) > 3;
    
    
-- 두개이상의 컬럼에 대한 group by    
-- 작성한 컬럼순서는 중요치 않다. 컬럼값을 동시에 묶어 비교하기 때문이다.

-- 부서별 직급별 인원수 조회
select *  from employee;

select
    dept_code, 
    job_code,
    count(*)
from
    employee
group by
    job_code, dept_code;

-- 부서별, 성별 인원수 조회
select
    dept_code,
    decode(substr(emp_no, 8, 1), '2', '여', '4', '여', '남') gender,
    count(*) cnt
from
    employee
group by
    dept_code,
    decode(substr(emp_no, 8, 1), '2', '여', '4', '여', '남');

-- rollup 
-- group by절에서 소계를 함께 제공하는 함수
select
    nvl(job_code, '-'),
    count(*)
from
    employee
group by 
    rollup(job_code);
    
-- null값이 포함된 컬럼 기준 소계
-- grouping(col) : 실제 데이터컬럼은 0, rollup등에 의해 만들어진 컬럼인 경우 1 반환
select
--    dept_code,
    grouping(dept_code),
    decode(grouping(dept_code), 0, nvl(dept_code, '인턴'), 1, '총계') dept_code,
    count(*)
from
    employee
group by 
    rollup(dept_code)
order by 
    1;

-- 두개이상의 컬럼에 대한 rollup
-- 직급코드/부서코드로 그룹핑
select
    dept_code, 
    job_code,
    count(*)
from
    employee
group by
    rollup(dept_code, job_code) -- 두개 이상인 경우 첫번째 컬럼에 대한 group by 결과행들이 추가된다.
order by
    1;

select dept_code, count(*) from employee group by dept_code;

--======================================
-- JOIN
--======================================
-- 두개이상의 테이블 레코드(행)를 연결해서 가상테이블 relation 을 생성.
-- 기준컬럼값을 가지고, 행단위로 연결

-- 조인 종류
-- 1. Equi join 기준컬럼에 대한 연산이 동등비교인 경우 (=)
-- 2. Non-equi join 기준컬럼에 대한 연산이 동등비교가 아닌  경우 (between and, is null, in, !=)

-- 조인문법
-- 1. Ansi 표준문법 : dbms 구분없이 사용가능  join키워드, on조건절 사용
-- 2. dbms별 전용문법  : 오라클전용문법. 최적화. 콤마로 테이블연결, where조건절에 조인조건 명시


-- 송종기사원의 부서명 조회
-- employee : 사원명, 부서코드
-- department : 부서코드, 부서명
select 
    dept_code -- D9
from
    employee
where
    emp_name = '송종기';

select
    *
from 
    department
where
    dept_id = 'D9';

select
    dept_title
from
    employee e join department d
        on e.dept_code = d.dept_id
where
    emp_name = '송종기';


------------------------------------------------------
-- EQUI - JOIN
------------------------------------------------------
-- 1. inner join 내부 조인 : 조건에 맞는 행만 result set에 포함
-- 2. outer join 외부 조인 : 내부조인에 좌/우측 테이블 행을 무조건 포함
--   - left outer join
--   - right outer join
--   - full outer join
-- 3. cross join
-- 4. self join
-- 5. multiple join

--+++++++++++++++++++++++++++++++++++++++++++++++++++++
-- INNER JOIN
--+++++++++++++++++++++++++++++++++++++++++++++++++++++
-- 기본조인. inner 키워드는 생략이 가능
-- 조인조건의 기준컬럼이 null이거나 상대테이블에 매칭되는 행이 없다면 제외됨.
-- employee : deparatment n:1 관계의 테이블
select * from employee; -- 24행
select * from department; -- 9행

-- 22행
-- employee에서는 dept_code가 null인 2행이 제외
-- department에서는 employee에 매칭되는 행이 없는 3행이 제외(D3, D4, D7)
select
    *
from
    employee e inner join department d
        on e.dept_code = d.dept_id;

-- department - employee 은  1:N 관계의 테이블
-- department.dept_id컬럼값을 employee.dept_code에서 여러번 참조할 수 있다.

-- (오라클전용문법)
-- 테이블 작성순서는 무관한다.
select
    *
from
    department d, employee e
where
    e.dept_code = d.dept_id;


--++++++++++++++++++++++++++++++++++++++++++++++++++++
-- OUTER JOIN
--++++++++++++++++++++++++++++++++++++++++++++++++++++
-- 외부조인. outer키워드 생략가능
-- left outer join 좌측테이블의 모든 행을 포함
-- right outer join 우측테이블의 모든 행을 포함
-- full outer join 좌우측테이블의 모든 행을 포함

-- 좌측외부조인
-- 사원/부서정보 조회시 부서지정이 안된 사원도 포함하는 경우
-- 24행 = 22행 (inner join) + 2행(dept_code가 null인 사원)
select
    *
from
    employee e left outer join department d
        on e.dept_code = d.dept_id;

--(오라클 전용문법)
-- 좌측외부조인인 경우, 우측테이블 조인조건에 (+)를 추가
select
    *
from
    employee e, department d
where
    e.dept_code = d.dept_id (+);
        

-- 우측외부조인
-- 사원/부서정보 조회시 사원이 없는 부서도 조회하는 경우
-- 25행 = 22행 (inner join) + 3행 (employee에 매칭되는 행이 없던 D3, D4, D7)
select
    *
from
    employee e right join department d
        on e.dept_code = d.dept_id;

-- (오라클전용문법)
select
    *
from
    employee e, department d
where
    e.dept_code (+) = d.dept_id;


-- 완전외부조인
-- 사용할 일 거의 없음.
-- 27행 = 22행 (inner join)  + 2행(employee) + 3행(department)
select
    *
from
    employee e full join department d
        on e.dept_code = d.dept_id;
        
-- (오라클 전용문법 없음)


-- *****조인하려는 두 테이블사이에 내부조인시 누락된 행(기준컬럼이 null 또는 매칭되는 행 없음)을 파악하는 것이 중요하다. *****
-- 조인시 누락된 행이 필요없다면 내부조인이 가장 효율이 좋다.

-- 부서/지역 정보를 동시에 조회
-- 기준컬럼명은 동일할 수도 있고, 다를수 도 있다.
select * from department;
select * from location;

select
    *
from
    department d join location l
        on d.location_id = l.local_code;

-- 사원/직급정보를 동시에 조회
select * from employee;
select * from job;

select
    e.emp_name,
    j.job_code,
    j.job_name
from
    employee e join job j
        on e.job_code = j.job_code;

-- 기준컬럼명이 같을때는 using을 사용할 수 있다.
-- using에 사용된 컬럼명은 별칭으로 접근할수 없다. (ORA-25154: USING 절의 열 부분은 식별자를 가질 수 없음 25154. 00000 -  "column part of USING clause cannot have qualifier")
select
--    j.job_code
--    e.*,
    e.emp_name,
    e.emp_no,
    e.dept_code,
    job_code,
    j.job_name
from
    employee e join job j
        using(job_code);


--++++++++++++++++++++++++++++++++++++++++++++++++
-- CROSS JOIN
--++++++++++++++++++++++++++++++++++++++++++++++++
-- 모든 경우의 수를 고려해서 조인처리
-- 좌측테이블의 모든행과 우측테이블의 모든행이 한번씩 연결
-- Cartesian Product (카테시안의 곱)
-- 216 = 24행 * 9행
select
    *
from
    employee e cross join department d;
    
-- (오라클전용문법)
select
    *
from
    employee e, department d;
    
-- 평균급여와 차이 조회
select
    e.*,
    avg(salary) -- 00937. 00000 -  "not a single-group group function"
from
    employee e;
        

select trunc(avg(salary)) from employee; -- 1행1열 가상테이블

select
    e.emp_name,
    e.salary,
    e.salary - a.avg_sal
from
    employee e cross join (select trunc(avg(salary)) avg_sal from employee) a;


--++++++++++++++++++++++++++++++++++++++++++++
-- SELF JOIN
--++++++++++++++++++++++++++++++++++++++++++++
-- 같은 테이블을 좌우에 두고 조인하는 경우.
-- 컬럼값이 같은 테이블의 다른 컬럼을 참조하는 경우 사용.

-- 사원별 관리자정보 조회
select * from employee;
-- 사번 | 사원명 | 매니져사번 | 매니져명
-- manager_id컬럼이 null 제외
-- 관리자가 아닌 행 제외
select
    *
--    m.emp_name manager_name
from
    employee e left join employee m
        on e.manager_id = m.emp_id;
where 
    e.emp_name = '방명수';
    
-- (오라클 전용문법)
select
    *
from
    employee e, employee m
where
    e.manager_id = m.emp_id (+)
    and
    e.emp_name = '방명수';
    
        
--++++++++++++++++++++++++++++++++++++++++++++++++
-- MULTIPLE JOIN
--++++++++++++++++++++++++++++++++++++++++++++++++
-- 다중조인. 여러테이블을 조인
-- 한번에 조인할수 있는 테이블은 2개다.
-- 외부조인으로 포함된 행은 이후 조인에도 계속 외부조인을 사용해야 한다.
-- 테이블 연결순서가 중요하다.

-- 사원/부서/지역정보를 동시조회
-- employee - department - location 테이블 순으로 연결해야한다. 
select
    e.emp_name,
    j.job_name,
    d.dept_title,
    l.local_name
from
    employee e left join department d
        on e.dept_code = d.dept_id
    left join job j -- employee와 조인되기 때문에 위치가 비교적 자유롭다.
        on e.job_code = j.job_code
    left join location l
        on d.location_id = l.local_code;
        
-- (오라클 전용문법)  : 테이블 나열순서는 중요치 않다.
select
    *
from
    employee e, location l, job j, department d
where
    e.dept_code = d.dept_id (+)
    and
    d.location_id = l.local_code (+)
    and 
    j.job_code = e.job_code
    and
    j.job_name in ('대리', '과장')
    and
    l.local_name like 'ASIA%';
        

-- 직급이 대리 또는 과장이면서 ASIA지역에 근무하는 사원조회
-- 사번, 이름, 직급명(job.job_name), 부서명(department.dept_title), 근무지역명(location.lcal_name), 관리자이름(employee.emp_name)
select
    e.emp_id, e.emp_name,
    j.job_name,
    d.dept_title,
    l.local_name,
    m.emp_name manager_name
from
    employee e
      left join job j
        on e.job_code = j.job_code
      left join department d
        on e.dept_code = d.dept_id
      left join location l
        on d.location_id = l.local_code
      left join employee m
        on e.manager_id = m.emp_id
where
    j.job_name in ('대리', '과장')
    and
    l.local_name like 'ASIA%';

 
--@실습문제 - 조인@sh
-- 3. 가장 나이가 적은 직원의 사번, 사원명, 나이, 부서명, 직급명을 조회하시오. 
-- 나이 조회 (주민번호 -> 나이)
-- min(나이) -> 가장 적은 나이 조회 (가장 나이가 적은 사원 조회가 아닌다.)
-- 조인처리해야할 부서명, 직급명

-- 한국나이 : 현재년도 - 출생년도 + 1
-- 주민번호에 나이를 추출하는 방법 : yy + (1900 | 2000) <-- 주민번호 뒤 첫번째 자리
select
    emp_name,
    substr(emp_no, 1, 2) + decode(substr(emp_no, 8, 1), '1', 1900 , '2', 1900, 2000) birth_year,
    extract(year from sysdate) - (substr(emp_no, 1, 2) + decode(substr(emp_no, 8, 1), '1', 1900 , '2', 1900, 2000))+ 1 age,
--    ((오늘) - (출생일)) / 365
--    months_between(오늘, 출생일) / 12
    substr(emp_no, 1, 2) + decode(substr(emp_no, 8, 1), '1', 1900 , '2', 1900, 2000) || substr(emp_no, 3, 4) birthday,
    trunc(months_between(sysdate, to_date(substr(emp_no, 1, 2) + decode(substr(emp_no, 8, 1), '1', 1900 , '2', 1900, 2000) || substr(emp_no, 3, 4), 'yyyymmdd')) / 12) 만나이
from
    employee;

-- 가장 적은 나이 조회
select
    min(extract(year from sysdate) - (substr(emp_no, 1, 2) + decode(substr(emp_no, 8, 1), '1', 1900 , '2', 1900, 2000))+ 1) min_age
from
    employee;

-- cross join 이용해서 그룹함수 결과와 일반 컬럼 동시 조회
select
    e.emp_name, e.emp_no, d.dept_title, j.job_name
from
    employee e 
        cross join (
            select
                min(extract(year from sysdate) - (substr(emp_no, 1, 2) + decode(substr(emp_no, 8, 1), '1', 1900 , '2', 1900, 2000))+ 1) min_age
            from
                employee) m
        left join department d
            on e.dept_code = d.dept_id
        left join job j
            on e.job_code = j.job_code
where
    extract(year from sysdate) - (substr(emp_no, 1, 2) + decode(substr(emp_no, 8, 1), '1', 1900 , '2', 1900, 2000)) + 1 = m.min_age;


select 
    emp_name
    emp_no,
    to_char(to_date(substr(employee.emp_no, 1, 6), 'yymmdd'), 'yyyy/mm/dd') 생일, -- yy 현재년도 기준으로 0 ~ 99 사이의 값 추측 (2000 ~ 2099)
    to_char(to_date(substr(employee.emp_no, 1, 6), 'rrmmdd'), 'yyyy/mm/dd') 생일  -- rr 현재년도 기준으로 50 ~ 49 사이의 값 추측 (1950 ~ 2049)
from employee;   
    
    
----------------------------------------------------------    
-- NON-EQUI JOIN
----------------------------------------------------------
-- 조인 조건절에서 동등비교연산자가 아닌 다른 연산자를 사용해 연결하는 경우
-- !=, >, <, >=, <=, between and, in 

select * from sal_grade;
select * from employee;

select
    *
from
    employee e join sal_grade s
        on e.salary between s.min_sal and s.max_sal;
    

    
    
--===========================================================
-- SET OPERATOR 집합연산자
--===========================================================
-- 컬럼기준으로 테이블을 합쳐 가상테이블은 만드는 연산자
-- union (합집합) | union all (합집합) | intersect (교집합) | minus (차집합)

-- 집합연산 룰
-- 1. 두 테이블의 컬럼수가 같아야 함.
-- 2. 상응하는 컬럼의 자료형이 상호호환 가능해야함. (char, varchar2 호환가능)
-- 3. order by절은 마지막에 한번만 사용이 가능
-- 4. 컬럼명이 다르다면 첫번째 테이블 기준으로 합쳐짐.

-- 테이블a : 부서코드가 D5인 사원 테이블
select
    emp_id, emp_name, dept_code, salary
from
    employee
where
    dept_code = 'D5'; -- 6행

-- 테이블b : 급여가 300만원 이상인 사원 테이블
select
    emp_id, emp_name, dept_code, salary
from
    employee
where
    salary >= 3000000; -- 9행

---------------------------------------------------
-- UNION | UNION ALL
---------------------------------------------------
-- 합집합연산
-- union 두테이블을 연결후, 중복제거.
-- union all 두테이블을 그대로 연결
-- 정렬이 필요한 경우 order by를 꼭 사용할 것!

-- union all
select
    emp_id 사원명, emp_name, dept_code, salary
from
    employee
where
    dept_code = 'D5'
union all
select
    emp_id, emp_name, dept_code, salary
from
    employee
where
    salary >= 3000000
order by
    emp_name;

-- union
select
    emp_id, emp_name, dept_code, salary
from
    employee
where
    dept_code = 'D5'
union
select
    emp_id, emp_name, dept_code, salary
from
    employee
where
    salary >= 3000000; -- 13 행 

------------------------------------------
-- INTERSECT
------------------------------------------
-- 교집합
-- 위/아래테이블의 중복된 행 반환

select
    emp_id, emp_name, dept_code, salary
from
    employee
where
    dept_code = 'D5'
intersect
select
    emp_id, emp_name, dept_code, salary
from
    employee
where
    salary >= 3000000; 

------------------------------------------
-- MINUS
------------------------------------------
-- 차집합
-- 위 결과집합에서 아래 결과집합과 중복된 행 제거

select
    emp_id, emp_name, dept_code, salary
from
    employee
where
    dept_code = 'D5'
minus
select
    emp_id, emp_name, dept_code, salary
from
    employee
where
    salary >= 3000000; 

-- 판매데이터 관리하기
create table tbl_sales (
    p_name varchar2(50),
    p_count number,
    sale_date date
);

select * from tbl_sales;

-- 2달전 데이터
insert into tbl_sales values('맛동산', 10, '230201');
insert into tbl_sales values('홈런볼', 15, '230203');
insert into tbl_sales values('새우깡', 23, '230210');
insert into tbl_sales values('홈런볼', 30, '230215');
insert into tbl_sales values('다이제', 7, '230222');
insert into tbl_sales values('맛동산', 9, '230225');

-- 지난달 데이터
insert into tbl_sales values('맛동산', 10, '230301');
insert into tbl_sales values('홈런볼', 15, '230303');
insert into tbl_sales values('새우깡', 23, '230310');
insert into tbl_sales values('홈런볼', 30, '230315');
insert into tbl_sales values('다이제', 7, '230322');
insert into tbl_sales values('맛동산', 9, '230325');
insert into tbl_sales values('자가비', 17, '230331');

-- 현재월 데이터
insert into tbl_sales values('맛동산', 10, '230401');
insert into tbl_sales values('홈런볼', 15, '230403');
insert into tbl_sales values('새우깡', 23, '230410');
insert into tbl_sales values('홈런볼', 30, '230415');
insert into tbl_sales values('다이제', 7, '230422');
insert into tbl_sales values('맛동산', 9, '230425');
insert into tbl_sales values('자가비', 17, '230425');


--두달전 판매데이터를 조회
--(언제 조회를 해도 두달전데이터가 잘 조회가 되어야 함.)
-- 현재가 1월일때 지난해 11월 데이터가 잘 조회되는가?
-- 현재날짜와 상관없이 두달전 1일부터 말일까지 데이터가 잘 조회되는가?
-- 2023년 4월에 조회시 2023년 2월 데이터만 조회되는가? (2022년 2월 또는 2021년 2월데이터는 포함되어서는 안된다.)
-- 년월을 동시 조회해야 한다.

select 
    add_months('230202', -2)
from 
    dual;
    
-- 2023년 2월 1일 ~ 2023년 2월 말일
select
    *
from
    tbl_sales
where
    to_char(sale_date, 'yyyy-mm') = to_char(add_months(sysdate, -2), 'yyyy-mm');

-- 지난달 데이터
select
    *
from
    tbl_sales
where
    to_char(sale_date, 'yyyy-mm') = to_char(add_months(sysdate, -1), 'yyyy-mm');

-- 판매데이터는 현재월의 데이터만 관리하고, 달이 바뀌면 지난달 판매테이블을 생성해서 이전시킨다.

-- tbl_sales_2302 - 2월 데이터만 관리
create table tbl_sales_2302
as
select
    *
from
    tbl_sales
where
    to_char(sale_date, 'yyyy-mm') = to_char(add_months(sysdate, -2), 'yyyy-mm');
    
select * from tbl_sales_2302;

-- tbl_sales_2303 - 3월 데이터만 관리
create table tbl_sales_2303
as
select
    *
from
    tbl_sales
where
    to_char(sale_date, 'yyyy-mm') = to_char(add_months(sysdate, -1), 'yyyy-mm');

select * from tbl_sales_2303;


select * from tbl_sales;

-- 지난 판매데이터 삭제
delete from 
    tbl_sales
where
    to_char(sale_date, 'yyyy-mm') = to_char(add_months(sysdate, -2), 'yyyy-mm');


-- 지난 3개월간 판매데이터를 조회
select * from tbl_sales_2302
union all
select * from tbl_sales_2303
union all
select * from tbl_sales;

select
    *
from(
    select * from tbl_sales_2302
    union all
    select * from tbl_sales_2303
    union all
    select * from tbl_sales
)
where
    p_name = '맛동산';
    
-- 1. 지난 3개월 제품별 총 판매량을 조회
select 
    p_name,
    sum(p_count)
from(
    select * from tbl_sales_2302
    union all
    select * from tbl_sales_2303
    union all
    select * from tbl_sales
)
group by 
    p_name
order by 
    2 desc;


-- 2. 지난 3개월 월별 제품별 총 판매량 조회

select 
     to_char(sale_date, 'yyyy-mm') "yyyy-mm",
     p_name, 
     sum(p_count)
from(
    select * from tbl_sales_2302
    union all
    select * from tbl_sales_2303
    union all
    select * from tbl_sales
)
group by
    p_name, to_char(sale_date, 'yyyy-mm');


-- grouping sets
-- group by + union all
-- 각 컬럼명 group by 결과를 합친것과 동일한 결과집합 반환

-- 부서별, 직급별 급여 평균
select
    dept_code, job_code, trunc(avg(salary))
from
    employee
group by
    grouping sets(dept_code, job_code);

select
    null,
    job_code, 
    trunc(avg(salary))
from
    employee
group by
    job_code
union all    
select
    dept_code,
    null, 
    trunc(avg(salary))
from
    employee
group by
    dept_code;

-- 성별, 직급별, 부서별 인원수 조회
-- decode(grouping(col), 0, 실제데이터, 1, '-')
select
    decode(grouping(decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여')), 0, decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여'), '-')  gender,
    decode(grouping(job_code), 0, job_code, '-') job_code,
    decode(grouping(dept_code), 0, nvl(dept_code, '인턴') , '-') dept_code,
    count(*) cnt
from
    employee
group by
    grouping sets(
        decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여'),
        job_code,
        dept_code,
        () -- 전체그룹
    );

select
    count(*)
from
    employee
group by
    ();
    

--=================================================
-- SUB QUERY
--=================================================
-- 하나의 sql(main-query)에 포함된 또 다른 sql(sub-query)
-- 존재하지 않는 값에 근거해 조회할 때 유용하다.
-- 메인쿼리 실행중 서브쿼리를 만나면, 서브쿼리 우선실행후 그 결과를 다시 메인쿼리에 전달하는 구조

-- 종류
-- 1. 일반 subquery : 메인쿼리와 독립적으로 실행가능한 서브쿼리
-- 2. 상관 subquery :  상호연관 서브쿼리. 메인쿼리로 부터 값을 참조해서 실행가능한 서브쿼리 (단독실행 불가)

-- 사용시 유의사항
-- 1. 서브쿼리는 반드시 () 소괄호로 묶어서 작성
-- 2. 서브쿼리내에서는 order by 문법이 지원되지 않음.
-- 3. 되도록 연산자 우측에 작성

-- 노옹철 직원의 관리자이름 조회
select
    m.emp_name manager_name
from
    employee e join employee m
        on e.manager_id = m.emp_id
where
    e.emp_name = '노옹철';

-- 노옹철사원의 관리자아이디 조회 ->  사원테이블에서 사번과 일치하는 행의 사원명을 조회
select manager_id from employee where emp_name = '노옹철';
select emp_name from employee where emp_id = '201';

select
    emp_name
from
    employee
where
    emp_id = (select manager_id from employee where emp_name = '노옹철');

-- 전직원의 평균급여보다 많은 급여를 받는 사원 조회(사원명, 급여)
select avg(salary) from employee;
select emp_name, salary from employee where salary > (평균급여);

select 
    emp_name, salary 
from 
    employee 
where 
    salary > (select avg(salary) from employee);

--------------------------------------------------
-- 1행1열 서브쿼리
--------------------------------------------------
-- 윤은해와 같은 급여를 받는 사원 조회(사번, 사원명, 급여)
select salary from employee where emp_name = '윤은해'; -- sub
select * from employee where salary = 2000000; -- main

select 
    emp_id,
    emp_name,
    salary
from 
    employee 
where 
    salary = (select salary from employee where emp_name = '윤은해')
    and
    emp_name <> '윤은해';

-- 사원테이블에서 최대급여/최소급여를 받는 사원 조회
select max(salary) from employee;

select
    *
from
    employee
where
    salary = (select max(salary) from employee)
    or
    salary = (select min(salary) from employee);

select
    *
from
    employee
where
    salary in ((select max(salary) from employee), (select min(salary) from employee));

-- D1, D2 부서원중에서 (D5부서원의 평균급여)보다 많은 급여를 받는 사원조회 (부서번호, 사번, 사원명, 급여)
select avg(salary) from employee where dept_code = 'D5';
select
    dept_code, emp_id, emp_name, salary
from
    employee
where 
    dept_code in ('D1', 'D2')
    and
    salary > (select avg(salary) from employee where dept_code = 'D5');


-------------------------------------------------
-- n행1열 서브쿼리
-------------------------------------------------
-- 서브쿼리의 조회결과 여러행인 경우, 적절한 연산자(in, any(some), all, exists 등)와 사용해야 한다.

-- 송종기, 하이유가 속한 부서원 조회
select dept_code from employee where emp_name in ('송종기', '하이유');

select
    *
from
    employee
where
--    dept_code = (select dept_code from employee where emp_name in ('송종기', '하이유')); -- ORA-01427: 단일 행 하위 질의에 2개 이상의 행이 리턴되었습니다.
    dept_code in (select dept_code from employee where emp_name in ('송종기', '하이유'));
    
-- 차태연, 박나라, 이오리와 같은 직급의 사원 조회(사번, 사원명, 직급코드)
select
    emp_id, emp_name, job_code
from
    employee
where
    job_code in (select job_code from employee where emp_name in ('차태연', '박나라', '이오리'));
--    job_code in ('J7', 'J6', 'J7');

-- not in
-- 서브쿼리의 조회결과와 하나라도 일치하지 않는 행을 조회

-- 송종기/박나라의 부서에 근무하지 않는 사원 조회
select
    *
from
    employee
where
    dept_code not in (select dept_code from employee where emp_name in ('송종기', '박나라'));

-- (직급이 대표, 부사장)이 아닌 사원의 사번, 사원명, 직급명 조회
-- (직급명은 employee테이블에 존재하지 않는다.)
select
    e.emp_id,
    e.emp_name,
    j.job_name
from
    employee e join job j
    using (job_code)
where
    job_code not in (select job_code from job where job_name in ('대표', '부사장'));


-- Asia1지역에 근무하는 사원 정보출력, 부서코드, 사원명 (메인쿼리 조인하지 않기)
select * from department;
select * from location;
select dept_id from department where location_id = (select local_code from location where local_name = 'ASIA1');

select
    dept_code, emp_name
from
    employee
where
    dept_code in (select dept_id from department where location_id = (select local_code from location where local_name = 'ASIA1'));

-- any | some
-- any(n행1열 서브쿼리) 여러값중 하나와 비교시 참이면 참 반환
-- col > any(...) 여러값의 최소값보다 크면 참
-- col < any(...) 여러값의 최대값보다 작으면 참

-- J3직급의 사원보다 적은 급여를 받는 사원 조회
select
    *
from
    employee
where
    salary < any(select salary from employee where job_code = 'J3'); -- 3400000, 3900000, 3500000

-- all
-- all(n행 1열의 서브쿼리) 포함된 모든 값비교
-- col > all(...) 여러값의 최대값보다 크면 참
-- col < all(...) 여러값의 최소값보다 작으면 참

-- J3직급의 모든 사원보다 적은 급여를 받는 사원 조회
select
    *
from
    employee
where
    salary < all(select salary from employee where job_code = 'J3'); -- 3400000, 3900000, 3500000


----------------------------------------------------------
-- n열 서브쿼리
----------------------------------------------------------
-- 서브쿼리 조회결과가 n열인 경우
-- 행의 수에 따라 사용해야 하는 연산자가 다르다.
-- 1행인 경우 = , n행인 경우 in
-- in연산자는 1행도 n행도 모두 처리할 수 있다.

-- 퇴사한 사원이 한명 있다. 그 사원과 같은 부서, 같은 직급의 사원 조회(이름, 직급코드, 부서코드)
select dept_code, job_code from employee where quit_yn = 'Y';
select dept_code, job_code from employee where quit_date is not null;

select
    *
from
    employee
where
    (dept_code, job_code) in (select dept_code, job_code from employee where quit_yn = 'Y')
    and 
    quit_yn = 'N';

-- 직급별 최소급여를 받는 사원 조회
-- 1.직급별 최소급여
select
    job_code,
    min(salary)
from
    employee
group by
    job_code;

-- 2. n행 m열 서브쿼리 사용
select
    *
from
    employee
where
    (job_code, salary) in (
        select
            job_code,
            min(salary)
        from
            employee
        group by
            job_code
    );

-- 부서별 최대/최소급여를 받는 사원의 부서코드, 사원명, 급여 조회
-- (부서가 없는 인턴사원도 출력)

select dept_code, max(salary) from employee group by dept_code;

select
    dept_code,
    emp_name, 
    salary
from
    employee
where
    (nvl(dept_code, 'D0'), salary) in (select nvl(dept_code, 'D0'), max(salary) from employee group by dept_code)
    or
    (nvl(dept_code, 'D0'), salary) in (select nvl(dept_code, 'D0'), min(salary) from employee group by dept_code)
order by
    1;

-----------------------------------------------
-- 상관 SUBQUERY
-----------------------------------------------
-- 메인쿼리의 값을 받아 서브쿼리를 실행. 그 결과값을 다시 메인쿼리로 반환하는 서브쿼리
-- 단독으로 블럭잡아 실행할 수 없다.

-- 직급별 평균급여보다 많은 급여를 받는 사원 조회
select * from employee;
select avg(salary) from employee where job_code = 'J2';
select
    job_code,
    emp_name,
    salary
from 
    employee e
where   
    salary > (select avg(salary) from employee where job_code = e.job_code)
order by
    1;

-- 부서코드별 평균급여보다 적은 급여를 받는 사원 조회 (부서코드, 사원명, 급여)
-- 인턴도 결과집합에 포함시킬것
select
    nvl(dept_code, '인턴') dept_code, emp_name, salary
from
    employee e
where
    salary < (select avg(salary) from employee where nvl(dept_code, 'abc')  = nvl(e.dept_code, 'abc'))
order by 
    1;

-- exists(상관서브쿼리)
-- where절의 조건식 연산자. true/false 반환
-- 서브쿼리의 결과집합이 1행이상이면(행이 존재하면) 참
-- 서브쿼리의 결과집합이 0행이면(행이 존재하지 않으면) 거짓
select 
    *
from
    employee
where
--    1 = 1; -- 무조건 참
    1 = 0;  -- 무조건 거짓


-- 부서원이 존재하는 부서 조회
select 1 from employee where dept_code = 'D2';
select
    *
from
    department d
where
    exists(select 3 from employee where dept_code = d.dept_id);

-- 관리자 사원만 조회 (emp_id가 다른 사원의 manager_id로 사용되는 사원)
-- exists
select
    *
from
    employee m
where
    exists(select 1 from employee where manager_id = m.emp_id);

-- not exists 최대/최소값 조회 
-- 최대급여를 받는 사원 조회
select
    *
from
    employee e
where
    not exists(select 1 from employee where salary > e.salary);

-- 가장 최근에 입사한 사원 조회
select
    *
from
    employee e
where
    not exists(select 1 from employee where hire_date > e.hire_date);


------------------------------------------------------
-- SCALA SUBQUERY
------------------------------------------------------
-- 단일값 scala
-- 결과값(1행 1열)이 하나인 select절에 사용된 상관서브쿼리

-- 사원명, 부서명을 조회
select dept_title from department where dept_id = 'D3';
select dept_title from department where dept_id = null;
select
    emp_name,
    (select job_name from job where job_code = e.job_code) job_name,
    (select dept_title from department where dept_id = e.dept_code) dept_title
from
    employee e;
-- 서브쿼리는 내부적으로 캐싱되기 때문에 동일한 값으로 서브쿼리가 요구되는 경우 효율이 나쁘지 않다.

    
-- 사원명, 직급코드, 부서코드, 급여, 해당직급 평균급여, 해당부서 평균급여 조회
-- scala subquery사용할 것(group by 금지)
select
    emp_name,
    job_code,
    dept_code,
    salary,
    (select trunc(avg(salary)) from employee where job_code = e.job_code) avg_sal_by_job,
    (select trunc(avg(salary)) from employee where dept_code = e.dept_code) avg_sal_by_dept
from
    employee e;


--------------------------------------------------------
-- INLINE-VIEW
--------------------------------------------------------
-- from절에 사용한 서브쿼리
-- view란? 실제테이블에 근거한 논리적 가상테이블. 실제 테이블과 사용방법은 동일
-- 복잡한 쿼리를 간결하게 사용하거나, 실제테이블을 제한적으로 사용할때 뷰를 사용하면 좋다.
-- 1. inline-view 1회용
-- 2. stored-view db객체로 저장해서 재사용이 가능.

-- 여사원만 조회(사번, 이름, 성별)
select
    emp_id,
    emp_name,
--    dept_code, -- inline-view에 포함된 컬럼만 조회가능
    gender
from (
    select
        emp_id,
        emp_name,
        decode(substr(emp_no, 8, 1), '2', '여', '4', '여', '남') gender
    from
        employee e
)
where
    gender = '여';
    
-- 1990년대(1990 ~ 1999) 입사자만 조회(사번, 사원명, 입사년도)
select
    emp_id,
    emp_name,
    hire_year
from(
    select
        e.*,
        extract(year from hire_date) hire_year
    from
        employee e)
where
    hire_year between 1990 and 1999;

-- 30, 40대 남사원 조회 (사번, 부서명, 성별, 나이)
select
    emp_id,
    emp_name,
    (select dept_title from department where dept_id = e.dept_code) dept_title,
    gender,
    age
from(
    select
        e.*,
        decode(substr(emp_no, 8, 1), '2', '여', '4', '여', '남') gender,
        (extract(year from sysdate)) - (decode(substr(emp_no, 8, 1), '1', 1900, '2', 1900, 2000) + substr(emp_no, 1, 2)) + 1 age
    from
        employee e
    ) e
where
    gender = '남'
    and
    age between 30 and 49;
    
    
--===============================================
-- 고급 쿼리
--===============================================

-------------------------------------------------
-- TON-N 분석
-------------------------------------------------
-- 특정테이블에서 특정컬럼기준 오름차순/내림차순 정렬했을때 상위n개의 행을 조회
-- 이번달 가장 많이 팔린 상품 10가지, 시험성적 1~10위 조회, 연봉상위 Top-5

-- rowid : 테이블 특정 레코드에 접근하기 위한 논리적 주소값. 인덱스객체에서 레코드접근시 사용.
-- rownum : 결과집합 생성시 행단위로 부여되는 식별번호.
--     where절을 사용하거나, inline-view사용시 새로 부여된다.
--     from/where절을 거쳐 생성되며, order by로는 rownum이 변경되지 않는다.
select
    rowid,
    rownum,
    e.*
from
    employee e
order by
    emp_name;

select * from employee where rowid = 'AAAStBAAHAAAAFnAAA'; 

-- 급여 Top-5 조회
select
    rownum new,
    e.*
from (
    select
--        rownum old,
        emp_name,
        salary
    from    
        employee
    order by
        salary desc
) e
where
    rownum between 1 and 5;
    
-- 가장 최근 입사자 5명 조회(사번, 사원명, 부서명, 입사일)
select
    rownum,
    e.*
from(
    select
        emp_id,
        emp_name,
        (select dept_title from department where dept_id = e.dept_code) dept_Title,
        hire_date
    from
        employee e
    order by
        hire_date desc
) e
where
    rownum between 1 and 5;

-- 직급이 대리인 사원중에서 급여 top 3 조회 (사번, 사원명, 직급명, 급여)
select
    *
from(
    select
        emp_id,
        emp_name, 
        job_code,
        job_name,
        salary
    from
        employee e join job j
            using (job_code)
    where
        j.job_name = '대리'
    order by
        salary desc
)
where
    rownum <= 3;
    

-- 부서별 급여 평균 top3 조회 (부서명, 평균급여(버림))
select
    rownum,
    e.*
from (
    select
        dept_code,
        trunc(avg(salary)) avg_sal
    from
        employee
    group by
        dept_code
    order by
        2 desc
) e
where
    rownum <= 3;
    
    
-- offset이 있는 Top-N 분석    
-- rownum은 from...where절이 끝나야 부여가 완성된다.
-- where절에서는 1부터 접근하는 경우는 rownum 사용가능.
-- offset이 발생하면 하나의 inline-view를 추가로 사용해야 한다.
select
    *
from (
    select
        rownum rnum,
        e.*
    from (
        select
            emp_name,
            salary
        from    
            employee
        order by
            salary desc
    ) e
)    
where
    rnum between 6 and 10;
    
-- 부서별 급여평균 4위 ~ 6위 조회
select 
    * 
from(
    select
        rownum rnum,
        e.*
    from (
        select
            dept_code,
            trunc(avg(salary)) avg_sal
        from
            employee
        group by
            dept_code
        order by
            2 desc
    ) e
) e
where
    rnum between 4 and 6;


-------------------------------------------------------
-- 계층형 쿼리
-------------------------------------------------------
-- join은 특정컬럼기준으로 수평적으로 행을 연결하는 데 반해, 계층형쿼리는 특정컬럼기준을 수직적으로 행을 연결
-- 부모행 - 자식행 연결
-- 조직도, 메뉴, 답변형게시판 표현시 사용

-- start with : 루트행을 지정
-- connect by : 부모-자식행을 연결할 조건절 작성. prior키워드를 부모행 컬럼에 작성.
-- level 의사 pseudo 컬럼 : 계층레벨 표현

-- 1. 하향식
-- 사원테이블의 조직도 
-- employee.emp_id (부모행)  <----  employee.manager_id  (자식행)
select * from employee;
select
    level,
    lpad(' ', (level - 1) * 5) || emp_name 조직도,
    e.*
from
    employee e
start with
    emp_id = '200' -- 최상위행을 지정(n개 가능)
connect by
    prior emp_id = manager_id;


-- 2. 상향식
-- 결재선 작성에 유용

-- 윤은해 사원의 결재선 조회
select
    emp_id,
    emp_name,
    manager_id
from
    employee
start with 
    emp_name = '윤은해'
connect by
    prior manager_id = emp_id;
    
-- 메뉴데이터
create table tbl_menu(
    no number primary key, -- 고유식별컬럼
    menu_name varchar2(100),
    parent_no number references tbl_menu(no) -- 참조컬럼 parent_no -> no
);
insert into tbl_menu values(100, '주메뉴1', null);
insert into tbl_menu values(200, '주메뉴2', null);
insert into tbl_menu values(300, '주메뉴3', null);
insert into tbl_menu values(1000, '서브메뉴A', 100);
insert into tbl_menu values(2000, '서브메뉴B', 200);
insert into tbl_menu values(3000, '서브메뉴C', 300);
insert into tbl_menu values(1001, '상세메뉴A1', 1000);
insert into tbl_menu values(1002, '상세메뉴A2', 1000);
insert into tbl_menu values(1003, '상세메뉴A3', 1000);
insert into tbl_menu values(2001, '상세메뉴B1', 2000);
insert into tbl_menu values(3001, '상세메뉴C1', 3000);

select * from tbl_menu;

select
    lpad(' ', (level - 1) * 5) || menu_name || '(' || no || ')' menu
from
    tbl_menu
start with
    parent_no is null
connect by
    prior no = parent_no;


--------------------------------------------------
-- WINDOW 함수
--------------------------------------------------
-- 행과 행간의 관계를 쉽게 정의하기 위한 함수
-- Ansi 표준함수
-- select절에서만 사용가능
-- 순위관련 | 집계관련 | 순서관련 | 비율관련 | 통계관련

--window_function (arguments) over ([partition by절] [order by절] [windowing절])
-- arguments 윈도우함수 인자(0 ~ n개)
-- partition by절 : 윈도우함수내 group by
-- order by절 : 윈도우함수내 order by
-- windowing절 : 대상행 지정

--++++++++++++++++++++++++++++++++++++++++
-- 순위관련
--++++++++++++++++++++++++++++++++++++++++
-- rank() over()
-- 순위부여. 중복된 값이 있다면, 다음순위는 중복된 개수만큼 건너뜀.
select
    emp_name,
    salary,
    dept_code,
    rank() over(order by salary desc) rank, -- 전체 급여 내림차순
    rank() over(partition by dept_code order by salary desc) rank -- 부서내 급여 내림차순
from
    employee
order by
    dept_code;
    
-- dense_rank() over()
-- 순위부여. 중복된 값이 있어도, 다음순위는 건너뛰지 않음.
select
    emp_name,
    salary,
    dense_rank() over(order by salary desc) rank
from
    employee;

--  입사일이 빠른 순서대로 순위 조회(사번, 사원명, 입사일, 순위)
select
    emp_id,
    emp_name,
    hire_date,
    rank () over(order by hire_date) rank
from
    employee;

--  부서내 입사일이 빠른 순서대로 순위 조회(사번, 사원명, 입사일, 부서내 입사일 순위)
select
    emp_id,
    emp_name,
    hire_date,
    dept_code,
    dense_rank () over(partition by dept_code order by hire_date) rank
from
    employee;

-- row_number() over()
-- 중복된 값이 있어도 순위는 중복되지 않는다 ---> 페이징처리 적합
select
    emp_name,
    salary,
    row_number() over(order by salary desc) rank
from    
    employee;

-- 윈도우함수로 top-n분석을 처리하면 offset이 있어도 단한번의 inline-view만 사용하면 된다.
select
    *
from (
    select
        emp_name,
        salary,
        row_number() over(order by salary desc) rank
    from    
        employee
)
where
    rank between 6 and 10;


--부서별 급여 평균 top3 조회 (부서명, 평균급여(버림))
select
    *
from(
    select
        dept_code,
        trunc(avg(salary)) avg_sal,
        rank () over(order by trunc(avg(salary)) desc) rank
    from
        employee
    group by
        dept_code
)
where
    rank between 1 and 3;
    



--부서별 급여 평균 4위 ~ 6위 조회 (부서명, 평균급여(버림))
select
    *
from(
    select
        dept_code,
        trunc(avg(salary)) avg_sal,
        rank () over(order by trunc(avg(salary)) desc) rank
    from
        employee
    group by
        dept_code
)
where
    rank between 4 and 6;

--++++++++++++++++++++++++++++++++++++++++
-- 집계관련
--++++++++++++++++++++++++++++++++++++++++
-- 합계, 평균관련 윈도우함수

-- sum() over()
-- order by와 함께 사용하면 누계를 확인가능.
select
    emp_name, 
    salary,
    dept_code,
    sum(salary) over() 전체급여합, 
    sum(salary) over(partition by dept_code) 부서별급여합,
    sum(salary) over(partition by dept_code order by salary) "부서별급여누계(급여오름차순)"
from
    employee;

-- 지난 3개월 판매데이터에서 월별 제품별 총판매량 조회



select
    to_char(sale_date, 'yyyy-mm') 판매월,
    p_name 제품명,
    p_count 판매수량,
    sum(p_count) over(partition by p_name order by sale_date) 제품별누계,
    sum(p_count) over(order by sale_date) 전체판매수량누계
from(
    select * from tbl_sales_2302
    union all
    select * from tbl_sales_2303
    union all
    select * from tbl_sales
);

-- avg() over()
select
    emp_name, 
    salary,
    dept_code,
    trunc(avg(salary) over()) 전체사원급여평균,
    trunc(avg(salary) over(partition by dept_code)) 부서별급여평균
from
    employee;

-- listagg() within group(order by) over()
-- 컬럼값을 합쳐서 하나의 결과물로 반환
select
    listagg(emp_name, ',') within group (order by emp_name) 전사원이름
from    
    employee;
    
-- 부서별 사원명
select
    dept_code,
    listagg(emp_name, ' | ') within group (order by emp_name) 부서원
from 
    employee
group by
    dept_code;

-- 입사년도별 인원수 조회
/*
    1990 1991 1992 ...
    ----------------
        2      3     0 ...
        
    ---------   
    1990    2
    1991    3
    1992    0
    ...
    --------

*/
select
    sum(decode(extract(year from hire_date), 1998, 1, 0)) "1998",
    sum(decode(extract(year from hire_date), 1999, 1, 0)) "1999",
    sum(decode(extract(year from hire_date), 2000, 1, 0)) "2000",
    sum(decode(extract(year from hire_date), 2001, 1, 0)) "2001",
    sum(decode(extract(year from hire_date), 2002, 1, 0)) "2002",
    sum(decode(extract(year from hire_date), 2003, 1, 0)) "2003",
    sum(decode(extract(year from hire_date), 2004, 1, 0)) "2004",
    sum(decode(extract(year from hire_date), 2005, 1, 0)) "2005"
from
    employee;

select
    extract(year from hire_date) year,
    count(*)
from
    employee
group by
    extract(year from hire_date)
order by
    year;

-- 임의의 행 생성하기
select
    level + 1989 year
from
    dual
connect by
    level <= 30;

select
    y.year,
    nvl(e.cnt, 0) cnt
from
    (
        select
            extract(year from hire_date) year,
            count(*) cnt
        from
            employee
        group by
            extract(year from hire_date)
    ) e right join (
        select
            level + 1989 year
        from
            dual
        connect by
            level <= 30
    ) y
        on e.year = y.year
order by
    year;
    
    
--=================================================
-- DML
--=================================================
-- Data Manipulation Language 데이터 조작어
-- 테이블의 행을 조작하는 sql문
-- 추가(insert) 수정(update) 삭제(delete) 조회(select)
-- CRUD Create Read Update Delete

---------------------------------------------------
-- insert
---------------------------------------------------
-- 테이블의 행을 추가하는 명령어
-- 실행결과 테이블 행수가 증가

-- 문법1 (컬럼지정 없음)
-- 테이블의 컬럼순서, 개수를 정확히 일치시켜야 한다.
-- insert into 테이블 values (컬럼1값, 컬럼2값, ...)

-- 문법2 (컬럼지정)
-- 지정한 컬럼에 대해서만 values절에서 순서대로 값을 작성
-- 컬럼을 생략할 수 있다. (not null 컬럼 생략 불가 (기본값을 지정하면 생략가능))
-- insert into 테이블 (컬럼1, 컬럼2, ...) values (컬럼1값, 컬럼2값, ...)

create table sample (
    a number,
    b number default 10,
    c varchar2(20) not null,
    d date default sysdate not null
);
desc sample; -- 테이블 구조확인

-- 문법1
insert into 
    sample
values(
    10, 20, '안녕', to_date('1999-09-09', 'yyyy-mm-dd')
);
insert into 
    sample
values(
    11, default, '잘가', default
);
insert into 
    sample
values(
    10, default, null, default
);
-- 컬럼별 자료형이 일치해야 한다.
-- 컬럼수가 정확해야 한다.
-- not null컬럼에 null 대입 불가

-- 문법2
-- 생략된 컬럼은 null 또는 지정한 기본값이 대입된다.
-- not null 컬럼은 반드시 값지정
-- not null 컬럼도 기본값을 지정했다면 생략할 수 있다.
insert into
    sample(c, d)
values (
    '여보세요', default
);
insert into
    sample(c)
values (
    'cccccc'
);

select * from sample;
-- DML은 commit 명령을 통해 실제 db에 반영된다.
-- rollback명령은 작업내용을 취소하고, 마지막 commit시점으로 돌아간다. 단 undo가 아니다.

-- 연습용테이블 employee_ex
create table employee_ex
as
select * from employee;
-- 서브쿼리를 이용한 테이블 생성시에는 not null제약조건을 제외환 pk,fk, unique, check제약조건과 지정된 기본값은 모두 제거된다.
alter table
    employee_ex
add constraint pk_employee_ex primary key(emp_id) -- 고유식별컬럼
add constraint uq_employee_ex_emp_no unique(emp_no) -- 중복허용안함
add constraint fk_employee_ex_dept_code foreign key(dept_code) references department(dept_id) -- 외래키
add constraint fk_employee_ex_job_code foreign key(job_code) references job(job_code) -- 외래키
add constraint ck_employee_ex_quit_yn check(quit_yn in ('Y', 'N')) -- 체크제약 도메인지정
modify quit_yn default 'N'
modify hire_date default sysdate;

select * from employee_ex;

-- 3명의 데이터 추가 
-- 3번째는 not null컬러만 작성된 본인데이터 추가
/*
    사번: 301
    이름: 함지민
    주민번호: 781020-2123453
    이메일: hamham@kh.or.kr
    전화번호: 01012343334
    부서코드: D1
    직급코드: J4
    급여등급: S3
    급여: 4300000
    보너스: 0.2
    관리자: 200


    사번: 302
    이름: 장채현
    주민번호: 901123-1080503
    이메일: jang_ch@kh.or.kr
    전화번호: 01033334444
    부서코드: D2
    직급코드: J7
    급여등급: S3
    급여: 3500000
    보너스: 없음
    관리자: 201
*/
desc employee_ex;

insert into 
    employee_ex
values (
    '301', '함지민', '781020-2123453', 'hamham@kh.or.kr', '01012343334', 'D1', 'J4', 'S3', 4300000, 0.2, '200', default, null, default
);
insert into 
    employee_ex (emp_id, emp_name, emp_no, email, phone, dept_code, job_code, sal_level, salary, bonus, manager_id)
values (
    '302', '장채현', '901123-1080503', 'jang_ch@kh.or.kr', '01033334444', 'D2', 'J7', 'S3', 3500000, null, '201'
);

insert into
    employee_ex (emp_id, emp_name, emp_no, job_code, sal_level)
values (
    '303', '홍길동', '990909-1234567', 'J2', 'S2'
);

select * from employee_ex;

-- 서브쿼리를 이용한 insert
-- 사번, 사원명, 부서명 정보만 따로 관리
create table employee_ex2 (
    emp_id varchar2(3),
    emp_name varchar2(50),
    dept_title varchar2(100)
);
insert into employee_ex2 (emp_id, emp_name, dept_title) (
    select
        emp_id,
        emp_name,
        (select dept_title from department where dept_id = e.dept_code) dept_title
    from
        employee e
);

select * from employee_ex2;

-- insert all을 이용해서 여러테이블에 데이터 이전하기
-- employee_job, employee_manager
create table employee_job
as
select emp_id, emp_name, '직급명직급명' job_name from employee where 1 = 0; -- 테이블의 구조만 복사
-- drop table employee_job;

select * from employee_job;
desc employee_job;

create table employee_manager
as
select emp_id, emp_name, manager_id, emp_name manager_name from employee where 1 = 0;
-- drop table employee_manager

alter table employee_manager
modify manager_name null; -- not null 제약조건 -> nullable로 변경

select * from employee_manager;
desc employee_manager;

insert all
into employee_job values(emp_id, emp_name, job_name)
into employee_manager values(emp_id, emp_name, manager_id, manager_name)
select
    emp_id,
    emp_name,
    manager_id,
    (select emp_name from employee where emp_id = e.manager_id) manager_name,
    (select job_name from job where job_code = e.job_code) job_name
from
    employee e;


-- insert all 이용해서 하나의 테이블에 여러 데이터 동시 insert
create table tbl_str (
    str varchar2(50)
);
insert all
    into tbl_str values ('hello')
    into tbl_str values ('byebye')
    into tbl_str values ('ㅌㅌㅌ')
select * from dual;

select * from tbl_str;


----------------------------------------------------
-- UPDATE
----------------------------------------------------
-- 테이블에 저장된 행의 특정컬럼값을 수정하는 명령
-- 실행전후로 행의 수는 변화가 없다.
-- where절에 따라 0행 또는 여러행이 수정될 수 있다.
select * from employee_ex;

update 
    employee_ex
set
    emp_name = '송종식',
    dept_code = 'D8',
    salary = salary + 1000000
where
    emp_id = '201';

-- employee_ex테이블에서 D5부서원들의 급여를 모두 500000원 인상처리.
update
    employee_ex
set
    salary = salary + 500000
where
    dept_code = 'D5';    
    
select * from employee_ex where dept_code = 'D5';

-- 서브쿼리 이용한 update
-- 방명수 사원의 직급을 유재식과 동일하게 수정
select * from employee_ex where emp_name = '방명수';
update
    employee_ex
set
    job_code = (select job_code from employee_ex where emp_name = '유재식')
where
    emp_name = '방명수';

-- 임시환사원의 직급을 과장, 부서를 해외영업3부로 수정
select
    emp_name,
    (select job_name from job where job_code = e.job_code) job_name,
    (select dept_title from department where dept_id = e.dept_code) dept_title
from
    employee_ex e
where
    emp_name = '임시환';


update
    employee_ex
set
    job_code = (select job_code from job where job_name = '과장'),
    dept_code = (select dept_id from department where dept_title = '해외영업3부')
where
    emp_name = '임시환';


-----------------------------------------------
-- DELETE
-----------------------------------------------
-- 테이블에 저장된 행을 삭제하는 명령
-- 실행후 행수가 감소
-- where절에 따라 0행 또는 여러행이 삭제처리될 수 있다.
select * from employee_ex;

delete from
    employee_ex
where
    emp_id = '303';
    
-----------------------------------------------
-- TRUNCATE
-----------------------------------------------
-- 테이블데이터를 잘라내는 DDL
-- 모든 DDL문(create, alter, drop) 실행과 동시에 commit된다.
-- 테이블의 모든 데이터를 삭제(복구 안됨)
-- delete from 과 비교해서 처리 속도 빠름.(DML의 before image 작업이 없다)
truncate table employee_ex;

select * from employee_ex;

-- insert 서브쿼리 이용해서 employee 모든데이터 추가하기
insert into employee_ex (
    select * from employee
);


--====================================
-- DDL
--====================================
-- Data Definition Language 데이터 정의어
-- db객체에 대해 생성  create 수정 alter 삭제 drop 하는 명령어
-- rename, truncate등
-- 자동커밋되므로 유의해야 한다.

-- table, user, view, sequence, index, procedure, function, trigger, synonym ...

--------------------------------------
-- CREATE
--------------------------------------
-- 테이블/컬럼 주석
create table member (
    id varchar2(20),
    password varchar2(20),
    name varchar2(50)
);
-- 테이블 주석
comment on table member is '회원테이블';
comment on table member is '회원관리테이블'; -- 별도의 수정메소드없이 덮어쓰기
comment on table member is ''; -- 삭제 : null값('') 대입 

select * from user_tab_comments where table_name = 'MEMBER';

-- 컬럼 주석
comment on column member.id is '회원아이디';
comment on column member.password is '비밀번호';
comment on column member.name is '회원명';

select * from user_col_comments where table_name = 'MEMBER';


--+++++++++++++++++++++++++++++++++++++++++
-- 제약조건 constraints
--+++++++++++++++++++++++++++++++++++++++++
-- 테이블 생성시 각 컬럼에 대해 제약조건을 설정할 수 있다.
-- 데이터 무결성을 지키기 위해 제한하는 조건
-- 데이커 무결성이란? 데이터의 일관성과 정확성을 유지하는 것.

-- 1. not null 데이터에 null값을 허용하지 않음. 필수입력컬럼
-- 2. unique 데이터에 중복값을 허용하지 않음. 이메일, 주민번호등에 적용
-- 3. primary key 기본키(주키) 고유식별컬럼. 레코드를 구별할 수 있는 컬럼. 
-- 4. foreign key 외래키. 부모테이블의 컬럼값을 참조하는 컬럼(해당컬럼에 존재하는 값만 사용가능) 부서코드, 직급코드...
-- 5. check 도메인을 지정(값목록/값범위) 퇴사여부(Y/N), 점수(0~100)

-- 컬럼레벨 : 컬럼명 옆에 작성
-- 테이블레벨 : 컬럼과 나란히 작성 -> 제약조건이름과 함께 테이블레벨에 작성하는 것이 가장 좋다.

/*
    create table member (
        id varchar2(20) primary key, -- 컬럼레벨
        password varchar2(20),
        name varchar2(50),
        primary key(id) -- 테이블 레벨
    );

*/

--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- NOT NULL
--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- not null 제약조건은 유일하게 컬럼레벨에만 작성이 가능하다. 
-- 제약조건 이름도 작성할 필요 없음.
-- drop table member;
create table member (
    id varchar2(20) not null,
    password varchar2(20) default '1234' not null, -- 기본값은 not null 앞에 작성
    name varchar2(50) not null
);

insert into member values('honggd', null, null); -- ORA-01400: NULL을 ("SH"."MEMBER"."PASSWORD") 안에 삽입할 수 없습니다
insert into member values('honggd', default, '홍길동');

--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- UNIQUE
--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- 중복값 허용하지 않음.
-- 주민번호, 이메일, 닉네임 적용
-- 전화번호에 적용하지 말것.


-- drop table member;
create table member (
    id varchar2(20),
    password varchar2(20) not null, -- 기본값은 not null 앞에 작성
    name varchar2(50) not null, 
--    email varchar2(100) unique, -- 컬럼레벨
    email varchar2(100) not null, -- unique + nn
    constraints uq_member_email unique(email)
);
insert into member values('honggd', '1234', '홍길동', 'honggd@naver.com');
insert into member values('honggd', '1234', '홍기동', 'honggd@naver.com'); -- 오류 보고 - ORA-00001: 무결성 제약 조건(SH.UQ_MEMBER_EMAIL)에 위배됩니다
insert into member values('honggd', '1234', '홍기동', 'honggdgd@naver.com');
insert into member values('sinsa', '1234', '신사임당', null); -- unique제약조건이 걸려있어도 null 값 허용.

select * from member;

-- 컬럼 여러개를 묶어서 복합 unique 제약조건을 걸수도 있다.
-- unique(컬럼1, 컬럼2, ....) 여러 컬럼값을 합쳐서 유일해야 한다.
-- constraints uq_table_col1_col2  unique(col1, col2, ....)

-- 제약조건 조회
-- constraint_name, constraint_type(C(not null, check) | U | P | R), table_name, search_condition 등 확인
select * from user_constraints where table_name = 'MEMBER';

-- column_name
select * from user_cons_columns where table_name = 'MEMBER';

-- 제약조건 조회 쿼리(컬럼명 포함)
select
    ucc.column_name,
    uc.*
from    
    user_constraints uc join user_cons_columns ucc
        on uc.constraint_name = ucc.constraint_name
where
    uc.table_name = 'MEMBER';
    
--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- PRIMARY KEY
--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- 레코드를 구분하기 위한 고유식별에 컬럼에 적용
-- not null과 unique제약이 동시에 적용되며, 한테이블당 하나의 기본키만 적용이 가능하다.
-- 단일기본키 또는 복합기본키(컬럼 여러개) 가능

-- drop table member;
create table member (
    id varchar2(20),
    password varchar2(20) not null, 
    name varchar2(50) not null, 
    email varchar2(100) not null, -- unique + nn
    constraints uq_member_email unique(email),
    constraints pk_member_id primary key(id)
);

insert into member values ('honggd', '1234', '홍길동', 'hongd@naver.com');
insert into member values ('sinsa', '1234', '신사임당', 'sinsa@naver.com');
insert into member values ('leess', '1234', '이순신', 'leess@naver.com');
insert into member values ('gdgd', '1234', '홍길동', 'gdgd@naver.com');
insert into member values (null, '1234', '홍길동', 'gdgd@naver.com'); -- ORA-01400: NULL을 ("SH"."MEMBER"."ID") 안에 삽입할 수 없습니다

select * from member;

select * from member where id = 'leess'; -- 조회결과는 무조건 1행. 식별컬럼 O

select * from member where name = '홍길동'; -- 조회결과는 n행일 수 있다. 식별컬럼 X

-- 복합 PK
-- 여러 컬럼을 묶어서 고유식별컬럼으로 사용
create table tb_order (
    product_no varchar2(100),
    user_id varchar2(100),
    order_date date,
    cnt number default 1,
    constraints pk_tb_order primary key(product_no, user_id, order_date) -- 테이블레벨에만 작성가능
);
insert into tb_order values('p1234', 'honggd', sysdate, default); -- p_no, u_id값이 같아도 sysdate 덕분에 고유하게 식별 가능
insert into tb_order values('p1234', null, sysdate, default); -- ORA-01400: NULL을 ("SH"."TB_ORDER"."USER_ID") 안에 삽입할 수 없습니다

select 
    product_no,
    user_id,
    to_char(order_date, 'yy/mm/dd hh24:mi:ss') order_date,
    cnt
from 
    tb_order;

select
    ucc.column_name,
    uc.*
from    
    user_constraints uc join user_cons_columns ucc
        on uc.constraint_name = ucc.constraint_name
where
    uc.table_name = 'TB_ORDER';


--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- FOREIGN KEY
--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- 외래키
-- 참조무결성을 유지하기 위한 제약조건
-- 참조하는 테이블(부모)에서 제공하는 값만 사용하도록 제한을 거는 것.
-- null값 허용.
-- 참조하는 테이블의 해당컬럼은 pk, uq제약조건이 걸려있어야 한다.

select
    ucc.column_name,
    uc.*
from    
    user_constraints uc join user_cons_columns ucc
        on uc.constraint_name = ucc.constraint_name
where
    uc.table_name = 'EMPLOYEE';


-- shop_member
create table shop_member (
    id varchar2(20),
    name varchar2(100) not null,
    constraints pk_shop_member_id primary key(id)
);

insert into shop_member values('honggd', '홍길동');
insert into shop_member values('sinsa', '신사임당');
insert into shop_member values('yoogs', '유관순');

select * from shop_member;

-- shop_purchase 구매
-- fk 삭제옵션 : 부모레코드 삭제시 자식레코드 처리여부
-- 1. on delete restricted (기본값) - 자식레코드 존재하는 경우 부모레코드 삭제 불가
-- 2. on delete set null - 부모레코드 삭제시 자식레코드의 fk컬럼을 null로 수정
-- 3. on delete cascade - 부모레코드 삭제시 자식레코드도 따라서 삭제

-- drop table shop_purchase
create table shop_purchase (
    no number, -- 구매번호(pk)
    member_id varchar2(20),
    product_id varchar2(50),
    cnt number default 1,
    reg_date date default sysdate,
    constraints pk_shop_purchase_no primary key(no),
    constraints fk_shop_purchase_member_id foreign key(member_id) references shop_member(id) 
--        on delete set null
            on delete cascade
);

insert into shop_purchase values(1, 'honggd', 'p1234', 5, sysdate);
insert into shop_purchase values(2, 'sinsa', 'p555', 10, sysdate);
insert into shop_purchase values(3, 'honggd', 'p9999', 2, sysdate);
insert into shop_purchase values(4, 'sejong', 'p888', 2, sysdate); -- ORA-02291: 무결성 제약조건(SH.FK_SHOP_PURCHASE_MEMBER_ID)이 위배되었습니다- 부모 키가 없습니다
insert into shop_purchase values(4, null, 'p888', 2, sysdate); -- null 허용
insert into shop_purchase values(5, 'sinsa', 'p555', 10, sysdate);
insert into shop_purchase values(6, 'sinsa', 'p555', 10, sysdate);
insert into shop_purchase values(7, 'sinsa', 'p555', 10, sysdate);

select * from shop_purchase;

-- 부모레코드 삭제
delete from shop_member where id = 'honggd'; -- ORA-02292: 무결성 제약조건(SH.FK_SHOP_PURCHASE_MEMBER_ID)이 위배되었습니다- 자식 레코드가 발견되었습니다
select * from shop_member;
-- 자식레코드 먼저 삭제후, 부모레코드 삭제
delete from shop_purchase where member_id = 'honggd';


-- 식별관계 : 부모키를 다시 기본키로 사용하는 경우. fk컬럼인 동시에 pk컬럼으로 사용. 딱 한번만 참조 가능. 1 : 1
-- 비식별관계 : 부모키를 다시 기본키로 사용하지 않는 경우. fk컬럼 중복사용 가능. 여러번 참조 가능. 1 : N

-- shop_member (부모) ---- shop_nickname (자식) 
-- 1 : 1 관계
create table shop_nickname (
    member_id varchar2(20),
    nickname varchar2(100) ,
    constraints fk_shop_nickname_member_id foreign key(member_id) references shop_member(id),
    constraints pk_shop_nickename_member_id primary key(member_id)
);
insert into shop_nickname values ('sinsa', '신어머니');
insert into shop_nickname values ('honggd', '홍아들'); -- ORA-02291: 무결성 제약조건(SH.FK_SHOP_NICKNAME_MEMBER_ID)이 위배되었습니다- 부모 키가 없습니다
insert into shop_nickname values ('yoogs', '유관수니');
insert into shop_nickname values ('sinsa', '신씨네'); -- ORA-00001: 무결성 제약 조건(SH.PK_SHOP_NICKENAME_MEMBER_ID)에 위배됩니다

select * from shop_nickname;

-- 조인
-- 아이디 이름 별명 물품아이디 수량 구매일자
select
    *
from
    shop_member m
        left join shop_nickname n
            on m.id = n.member_id
        left join shop_purchase p
            on m.id = p.member_id;

--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- CHECK
--$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
-- 도메인을 지정. 값목록/값범위 지정

-- drop table member;
create table member (
    id varchar2(20),
    password varchar2(20) not null, 
    name varchar2(50) not null, 
    email varchar2(100) not null, -- unique + nn
    gender char(1),
    point number default 1000,
    constraints uq_member_email unique(email),
    constraints pk_member_id primary key(id),
    constraints ck_member_gender check(gender in ('M', 'F')),
    constraints ck_member_point check(point >= 0)
);

insert into member values ('honggd', '1234', '홍길동', 'honggd@naver.com', 'M', default);
insert into member values ('sinsa', '1234', '신사임당', 'sinsa@naver.com', 'f', default); -- ORA-02290: 체크 제약조건(SH.CK_MEMBER_GENDER)이 위배되었습니다
insert into member values ('sinsa', '1234', '신사임당', 'sinsa@naver.com', 'F', -1000); -- ORA-02290: 체크 제약조건(SH.CK_MEMBER_POINT)이 위배되었습니다

update 
    member
set
--    gender = 'm'
    point = point - 2000
where
    id = 'honggd';

select * from member;

----------------------------------------------    
-- ALTER
----------------------------------------------
-- db객체에 대한 수정

-- table 수정하기 (컬럼/제약조건)
-- add 컬럼/제약조건
-- modify 컬럼(자료형, default, not null)
-- rename 컬럼/제약조건
-- drop 컬럼/제약조건

-- 제약조건은 이름변경외에는 수정할 수 없다. (삭제후 재생성)
-- not null제약조건은 add가 아니라 modify명령어로 처리해야 한다.

create table foo (
    no number,
    username varchar2(20),
    password varchar2(20)
);

-- add 컬럼 (자료형/기본값/not null 지정가능)
-- 컬럼은 맨 마지막에만 추가가능하다.
alter table 
    foo
add
    name varchar2(50) default '김민호' not null;
    
desc foo;

-- add 제약조건
-- pk, uq, fk, ck만 가능. not null은 modify로 수정한다.
alter table
    foo
add
    constraints pk_foo_no primary key(no);

-- modify 컬럼
-- 자료형 변경, 기본값 변경, not null 추가 또는 제거

alter table 
    foo
modify
    password varchar2(300) default '1234' not null;

alter table 
    foo
modify
    password default '' null; -- 기본값 제거, not null -> null로 수정

-- rename 컬럼
alter table
    foo
rename
    column password to pwd;

-- rename 제약조건
alter table
    foo
add
    email varchar2(100) unique;
    
alter table
    foo
rename
    constraints SYS_C008422 to uq_foo_email;


select
    ucc.column_name,
    uc.*
from    
    user_constraints uc join user_cons_columns ucc
        on uc.constraint_name = ucc.constraint_name
where
    uc.table_name = 'FOO';

-- drop 컬럼
alter table
    foo
drop 
    column email;

-- drop 제약조건
-- pk, ck, nn, uq, fk
alter table
    foo
drop
    constraint PK_FOO_NO;

alter table
    foo
drop
    constraint SYS_C008419; -- not null 제약조건 제거
    

-----------------------------------------
-- RENAME
-----------------------------------------
-- 테이블명 변경가능
rename foo to bar; -- 테이블 이름이 변경되었습니다.


-----------------------------------------
-- DROP
-----------------------------------------
-- 객체 삭제
drop table bar;


--=======================================
-- DCL
--=======================================
-- Data Control Language 데이터 제어어
-- DB객체에 대한 권한부여 grant /회수 revoke, 트랜잭션처리등을 담당하는 명령어
-- TCL이 포함하는 개념

-- qwerty계정생성 @system
-- qwerty/qwerty  

-- drop user qwerty;

alter session set "_oracle_script" = true;

create user qwerty
identified by qwerty
default tablespace users;

alter user qwerty identified by qwerty1234;

-- 접속권한, 객체생성권한 부여
-- create session, create table...
-- 권한 직접 부여
grant create session, create table to qwerty;
-- 롤(권한 묶음) 부여
grant connect, resource to qwerty;
-- tablespace에 대한 용량부여
alter user qwerty quota unlimited on users;

-- @system qwerty에게 부여된 권한/롤 조회
-- privilege 권한
select * from dba_sys_privs where grantee = 'QWERTY';
select * from dba_role_privs where grantee = 'QWERTY';


-- @qwerty계정으로 확인 - 사용자에게 부여된 권한/롤 확인
select * from role_sys_privs; -- 부여받은 롤에 포함된 권한
select * from user_sys_privs;
select * from user_role_privs;

-- @sh 테이블 권한 @qwerty에게 부여
-- select권한, insert권한, update권한, delete권한이 각각 관리할 수 있다.
create table coffee (
    name varchar2(50),
    price number not null,
    company varchar2(50),
    constraints pk_coffee primary key(company, name)
);
insert into coffee values ('맥심', 3000, '동서식품');
insert into coffee values ('카누', 5000, '동서식품');
insert into coffee values ('네스카페', 4000, '네슬레');

select * from coffee;

-- 조회권한 부여
-- 테이블 소유주 또는 관리자가 부여할 수 있다.
grant select on coffee to qwerty;
grant insert on coffee to qwerty;
grant select, insert, update, delete on coffee to qwerty;
grant all on coffee to qwerty; -- 테이블권한 모두


-- 권한회수
revoke insert, update, delete on coffee from qwerty;


-- employee테이블에 대해 조회권한만 qwerty에게 부여
grant select on employee to qwerty;


---------------------------------------------
-- TCL
---------------------------------------------
-- Transaction Control Language 트랜잭션 제어어
-- commit, rollback, savepoint

-- 트랜잭션이란
-- 한번에 수행되어야 할 최소한의 작업단위
-- 여러 DML을 묶어서 한번에 commit 혹은 rollback처리해야할 작업량.
-- ALL or NONE

-- 계좌이체
-- A가 100000원을 B에게 송금하는 경우
-- update 계좌 set 잔액 = 잔액 - 100000 where id = A;
-- update 계좌 set 잔액 = 잔액 + 100000 where id = B;
-- 모든 dml이 성공했을 경우 commit, 하나라도 실패한 경우는 rollback


--============================================
-- DATABASE OBJECT 1
--============================================
-- 데이터를 효율적으로 관리하기 위해 준비된 오라클내의 객체
-- table, user, sequence, procedure, function, view, index, package...

-- procedure, trigger, function등은 pl/sql문법 사용하는 객체 -> DB OBJECT 2


-------------------------------------------------
-- DATA DICTIONARY
-------------------------------------------------
-- 자원을 효율적으로 관리하기위한 객체별 메타정보를 관리하는 관리자의 뷰(가상테이블)
-- 일반사용자는 권한을 부여받아 이를 사용하는 것.
-- 사용자는 DD의 내용을 열람만 할뿐, 수정할 필요는 없다.
-- 사용자 DDL을 통해 객체의 정보를 변경할 때에도, DD는 자동으로 반영된다.

-- 종류
-- 1. user_xxx 사용자가 소유한 객체에 대한 정보
-- 2. all_xxx 사용자가 소유한 객체, 권한을 부여받은 객체의 정보
-- 3. dba_xxx 관리자의 DD. 모든 객체에 대한 정보를 확인가능 (일반 사용자는 열람 불가)

-- 모든 dd 조회
select * from dict;

-- user_tables
-- 객체는 반드시 복수형의 이름을 가진다.
-- 사용자가 소유한 테이블 정보
select * from user_tables;

-- 제약조건 user_constraints
select * from user_constraints where table_name = 'EMPLOYEE';

-- 권한 user_sys_privs
-- 롤 user_role_privs
-- 부여받은 롤에 부여된 권한확인 role_sys_privs
select * from user_sys_privs;
select * from user_role_privs;
select * from role_sys_privs;

-- 시퀀스 user_sequences
select * from user_sequences;
-- 인덱스 user_indexes
select * from user_indexes;
-- 뷰 user_views
select * from user_views;

-- all_tables
-- 사용자 소유테이블 포함, 권한을 부여받은 테이블 조회
select * from all_tables;

select * from all_views;

-- dba_xxx
-- @system 또는 @sys로 접속해서 사용이 가능
select * from dba_users;
select * from dba_tables;

select * from dba_tables where owner = 'SH';
select * from sh.employee;

select * from dba_constraints where owner = 'SH';

-- 사용자권한관리
select * from dba_sys_privs where grantee = 'SH';
select * from dba_role_privs where grantee = 'SH';

select * from dba_tab_privs where owner = 'SH';

grant select on sh.coffee to qwerty;


-----------------------------------------------------
-- STORED VIEW
-----------------------------------------------------
-- 하나이상의 실제테이블에서 원하는 정보만 추려낸 가상테이블
-- inline-view와는 달리 객체로 저장해 테이블처럼 사용할 수 있다.
-- 실제 데이터를 가지고 있지 않고, 실제 테이블에 대한 링크개념
-- create view 권한 필요(resource롤에 포함되지 않은 권한)

-- @system에서 권한부여
grant create view to sh;

-- or replace 없으면 새로 만들고, 존재하면 대체
create or replace view view_emp
as
select 
    e.*,
    decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') gender
from
    employee e;


select * from view_emp where gender = '남';

select 
    *
from(
    select 
        e."EMP_ID",e."EMP_NAME",e."EMP_NO",e."EMAIL",e."PHONE",e."DEPT_CODE",e."JOB_CODE",e."SAL_LEVEL",e."SALARY",e."BONUS",e."MANAGER_ID",e."HIRE_DATE",e."QUIT_DATE",e."QUIT_YN",
        decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') gender
    from
        employee e
);

-- dd에서 조회
select * from user_views;

-- 1.복잡한 데이터를 쉽게 열람가능
-- 사번 사원명 부서명 직급명 성별 나이를 view_emp_detail 뷰로 생성

create or replace view view_emp_detail 
as
select
    emp_id,
    emp_name,
    (select dept_title from department where dept_id = e.dept_code) dept_title,
    decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') gender,
    trunc(months_between(
                sysdate, 
                to_date(decode(substr(emp_no, 8, 1), '1', 1900, '2', 1900, 2000) + substr(emp_no, 1, 2) || substr(emp_no, 3, 4))
        ) / 12
    ) age
from
    employee e;
    
select * from view_emp_detail;

-- 2. 제한적인 데이터를 다른 사용자에게 제공
-- view_emp_detail의 읽기권한을 qwerty에 부여
-- employee 읽기 권한은 회수
grant select on view_emp_detail to qwerty;
revoke select on employee from qwerty;

---------------------------------------
-- SEQUENCE
---------------------------------------
-- 정수값을 순차적으로 제공하는 채번객체
-- pk컬럼의 **중복없는** 고유값을 사용
-- start with 속성은 절대 수정이 불가하다. 

-- create sequence 시퀀스명
-- [start with 시작값] 기본값 1
-- [increment by 증감값] 기본값 1
-- [maxvalue 최대값 | nomaxvalue] 기본값 nomaxvalue
-- [minvalue 최소값 | nominvalue] 기본값 nominvalue
-- [ cycle | nocycle ] 기본값 nocycle 최대/최소값 도달시 순환여부
-- [cache 캐싱개수 | nocache ] 기본값 20 - 시퀀스객체 접근을 최소화하는 옵션. 메모리에서 관리

create table tb_items (
    no number,
    name varchar2(100) not null,
    constraints pk_tb_items_no primary key(no)
);

create sequence seq_tb_items_no
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
cache 20;

insert into tb_items values (seq_tb_items_no.nextval, '축구화');
insert into tb_items values (seq_tb_items_no.nextval, '농구화');
insert into tb_items values (seq_tb_items_no.nextval, '배구화');
insert into tb_items values (seq_tb_items_no.nextval, '족구화');

select * from tb_items;

-- 최근발급된 번호
select seq_tb_items_no.currval from dual;

-- DD에서 조회
select * from user_sequences;

-- cache 사용시 유의사항
-- 번호를  메모리에서 관리하기 때문에 휘발될 수 있다.  채번된 번호가 건너뛸수 있다.
-- 1 2 3 4 .... 21 22 23....
-- 채번의 가장 중요한 점은 중복없는 수를 발급하는 것이다.
-- 번호 공백없는 데이터를 관리하는 경우 nocache옵션을 사용해야 한다. 
-- (noche도 롤백하면 건너뛸수 있기때문에 마지막번호 조회해서 +1 처리한 번호를 사용하는 것이 제일 좋다.)
insert into tb_items values ( (select max(no) + 1 from tb_items), '반팔티');


-- 주문
create table tbl_order (
    no varchar2(50),
    product varchar2(100),
    reg_date date default sysdate,
    constraints pk_tbl_order_no primary key(no)
);
create sequence seq_tbl_order_no;

-- kh-20230503-01234 주문번호
insert into
    tbl_order
values(
    'kh-' || to_char(sysdate, 'yyyymmdd') || '-' || to_char(seq_tbl_order_no.nextval, 'fm00000'), 
    '농구공',
    default
);

select * from tbl_order;


-------------------------------------------------
-- INDEX
-------------------------------------------------
-- 색인. sql문 처리속도 향상을 위해 컬럼에 대해 생성하는 객체
-- key-value형식을 작성. key는 컬럼값, value는 레코드에 대한 주소값 (rowid)
-- 별도의 저장공간이 필요한 객체. 테이블 DML 작업시 인덱스에 대한 작업 역시 발생한다.

-- 장점
-- 해당 테이블에 대한 검색속도, 조인처리등이 몹시 빨라진다.

-- 단점
-- DML작업에 따른 후처리때문에 오히려 성능저하가 일어날 수도 있다.

-- 어떤 컬럼에 인덱스를 적용해야 하는가?
-- 1. 선택도(유일한 값 빈도)가 좋은 컬럼 - 회원아이디(선택도 좋음), 이름(선택도 좋은 편) | 성별 (선택도 나쁨) 
-- 2. where절에 주로 사용되는 컬럼
-- 3. 조인시 기준컬럼

-- 인덱스 적용을 피해야 하는 컬럼
-- 1. dml작업이 많이 컬럼
-- 2. null 데이터가 많은 컬럼

-- 인덱스 조회
-- pk, uq제약조건이 걸린 컬럼은 자동으로 인덱스 생성 (직접 관리할 필요 없음)
select * from user_indexes where table_name = 'EMPLOYEE';

-- 실행계획(F10)을 통한 비용 확인
select * from employee where emp_name = '송종기'; -- emp_name 인덱스 없음
select * from employee where emp_id = '201';

-- emp_name컬럼에 대한 인덱스 생성
create index idx_employee_emp_name on employee(emp_name);

select * from employee e join department d on e.dept_code = d.dept_id;

create index idx_employee_dept_code on employee(dept_code);


-- 인덱스 사용시 주의할 점.
-- 인덱스 활용여부 optimizer의 의해서 결정이 되지만, 다음 경우 인덱스를 사용하지 않는다.
-- 1. 인덱스컬럼에 변형이 가해지는 경우
select * from employee where substr(emp_no, 8, 1) in ('1', '3');
-- 2. null 비교하는 경우
select * from employee where emp_name is not null;
-- 3. not 비교하는 경우
select * from employee where emp_name != '송종기';
-- 4. 컬럼타입과 비교하는 값의 타입이 다른 경우
select * from employee where emp_id = 201;


--=================================================
-- PL/SQL
--=================================================
-- Procedural Language Extension to SQL
-- SQL 절차적언어확장. 프로그래밍적요소가 추가된 SQL문

--  유형
-- 1. 익명블럭 - 1회용코드
-- 2. db객체 - 프로시져, 함수, 트리거, 스케쥴러 등

-- 익명블럭 구조
/*

declare
    변수선언구문
begin
    실행구문 (필수)
execption
    예외처리구문
end; (필수)
/ 익명블럭의 종료표시

*/

-- 출력설정 (세션마다)
set serveroutput on;

declare
    name varchar2(100) := '홍길동';
begin
    dbms_output.put_line(name);
end;
/


declare
    v_emp_id char(3) := '&사번';
    v_emp_name varchar2(50);
begin
    select
        emp_name
    into
        v_emp_name
    from
        employee
    where
        emp_id = v_emp_id;
    
    dbms_output.put_line('결과 : ' || v_emp_name);
exception
    when no_data_found then dbms_output.put_line('조회된 사원이 없습니다.');
    when others then dbms_output.put_line('알수 없는 오류가 발생했습니다.');
end;
/

--------------------------------------
-- 변수
--------------------------------------
-- 자료형 - 기본자료형 | 복합자료형
-- 변수종류 - 스칼라변수 | 참조변수

-- 기본자료형
-- 문자형 char, varchar2, clob
-- 숫자형 number, binary_integer, pls_integer
-- 날짜형 date, timestamp
-- 논리형 boolean (true, false, null)

-- 복합자료형
-- 레코드
-- 커서
-- 컬렉션 

-- 스칼라변수 
-- 값하나를 담는 변수, sql 자료형 그대로 사용

-- 참조변수
-- 기존 선언된 자료형을 참조
-- %type, %rowtype, record, collection


-- %type
-- 기존테이블의 컬럼의 자료형을 그대로 사용

declare
    v_emp_id employee.emp_id%type;
    v_emp_name employee.emp_name%type;
    v_phone employee.phone%type;
begin
    v_emp_id := '&사번';
    select
        emp_name, phone
    into
        v_emp_name, v_phone
    from
        employee
    where
        emp_id = v_emp_id;
    
    dbms_output.put_line('이름 : ' || v_emp_name);
    dbms_output.put_line('전화번호 : ' || v_phone);
    
end;
/

-- %rowtype
-- 테이블의 구조. 전체컬럼을 가져와 참조

declare
    v_emp_id employee.emp_id%type;
    emp_row employee%rowtype;
begin
    v_emp_id := '&사번';
    
    select
        *
    into
        emp_row
    from
        employee
    where
        emp_id = v_emp_id;
    
    dbms_output.put_line('이름 : ' || emp_row.emp_name);
    dbms_output.put_line('전화번호 : ' || emp_row.phone);
    
end;
/ 

-- @실습문제 : 사번을 입력받고, 사원명, 주민번호, 부서명을 출력하는 익명블럭 작성
declare
    v_emp_id employee.emp_id%type := '&사번';
    v_emp_name employee.emp_name%type;
    v_emp_no employee.emp_no%type;
    v_dept_title department.dept_title%type;
begin
    select
        e.emp_name, e.emp_no, d.dept_title
    into
        v_emp_name, v_emp_no, v_dept_title
    from
        employee e left join department d
            on e.dept_code = d.dept_id
    where
        e.emp_id = v_emp_id;
    
    dbms_output.put_line('이름 : ' || v_emp_name);
    dbms_output.put_line('주민번호 : ' || v_emp_no);
    dbms_output.put_line('부서명 : ' || v_dept_title);
    
end;
/ 

-- record 사용하기
-- 복합컬럼
declare
    v_emp_id employee.emp_id%type := '&사번';
    
    type my_rec is record(
        v_emp_name employee.emp_name%type,    
        v_emp_no employee.emp_no%type,
        v_dept_title department.dept_title%type
    );
    
    my_row my_rec;
    
begin
    select
        e.emp_name, e.emp_no, d.dept_title
    into
        my_row
    from
        employee e left join department d
            on e.dept_code = d.dept_id
    where
        e.emp_id = v_emp_id;
    
    dbms_output.put_line('이름 : ' || my_row.v_emp_name);
    dbms_output.put_line('주민번호 : ' || my_row.v_emp_no);
    dbms_output.put_line('부서명 : ' || my_row.v_dept_title);
    
end;
/ 

-- pl/sql문에서 dml
-- commit도 함께 작성할 것

select * from member;
desc member;

begin
    insert into 
        member
    values(
        'abcde', '1234', '장발장', 'abcde@naver.com', 'M', default
    );
    commit;
end;
/

-- pl/sql 조건문
-- 1. if문
-- 2. case문

-- if 조건식 then 실행문 end if;
-- if 조건식 then 실행문 else 기본실행문 end if;
-- if 조건식1 then 실행문1 elsif 조건식2 then 실행문2... else 기본실행문 end if;

begin
    if '&이름' = '홍길동' then
        dbms_output.put_line('홍길동님 반갑습니다.');
    else
        dbms_output.put_line('누구냐, 넌?');
    end if;
    
    dbms_output.put_line('~~끝~~');
end;
/

declare
    num number := &숫자;
begin
    if mod(num, 3) = 0 then
        dbms_output.put_line('3의 배수를 입력했습니다.');
    elsif mod(num, 3) = 1 then
        dbms_output.put_line('3으로 나눈 나머지가 1 입니다.');
    else
        dbms_output.put_line('3으로 나눈 나머지가 2 입니다.');
    end if;
end;
/

-- 사용자가 입력한 정수가 양수인지 음수인지 0인지 출력하는 익명블럭을 작성

declare
    num number := &숫자;
begin
    if num > 0 then
        dbms_output.put_line('양수');
        dbms_output.put_line('양수');
        dbms_output.put_line('양수');
    elsif num < 0  then
        dbms_output.put_line('음수');
        dbms_output.put_line('음수');
        dbms_output.put_line('음수');
    else
        dbms_output.put_line('0');
    end if;
end;
/

--@실습문제 : 사번을 입력받고, 해당사원의 급여가 평균급여와 비교결과를 출력하세요.
-- 201번 송종기 사원은 평균보다 많은 급여를 받고 있습니다.
-- 206번 박나라 사원은 평균보다 적은 급여를 받고 있습니다.
-- xxx번 xxx 사원은 평균 급여를 받고 있습니다.
select * from employee;

-- 사번 변수
-- 사원명 변수
-- 사원급여 변수
-- 평균급여 변수 

declare
    v_emp_id employee.emp_id%type := '&사번';
    v_emp_name employee.emp_name%type;
    v_salary employee.salary%type;
    v_avg_sal employee.salary%type;
begin
    -- 평균급여
    select
        avg(salary)
    into
        v_avg_sal
    from
        employee;
    dbms_output.put_line(v_avg_sal);
    
    -- 사원별 이름/급여 조회
    select
        emp_name, salary
    into
        v_emp_name, v_salary
    from
        employee
    where
        emp_id = v_emp_id;
        
    dbms_output.put_line(v_emp_name || ' ' || v_salary);
    
    -- 분기처리
    if v_salary > v_avg_sal then
        dbms_output.put_line(v_emp_id || '번 ' || v_emp_name || ' 사원은 평균보다 많은 급여를 받고 있습니다.');
    elsif v_salary < v_avg_sal then
        dbms_output.put_line(v_emp_id || '번 ' || v_emp_name || ' 사원은 평균보다 적은 급여를 받고 있습니다.');
    else
        dbms_output.put_line(v_emp_id || '번 ' || v_emp_name || ' 사원은 평균 급여를 받고 있습니다.');
    end if;
    

end;
/

-- case문
/*
문법1 값
case 표현식
    when 값1 then 실행문1;
    when 값2 then 실행문2;
    ...
    [else 기본실행문]
end case;

문법2 조건

case
    when 조건식1 then 실행문1;
    when 조건식2 then 실행문2;
    ...
    [else 기본실행문]
end case;

*/
-- 문법1
accept rps prompt '가위1 바위2 보3 중에 입력하세요.' 
declare
    num number := &rps;
begin
    dbms_output.put_line(num);
    case num
        when 1 then dbms_output.put_line('가위를 내셨습니다.');
        when 2 then dbms_output.put_line('바위를 내셨습니다.');
        when 3 then dbms_output.put_line('보를 내셨습니다.');
        else dbms_output.put_line('잘못 입력하셨습니다.'); return; -- 조기리턴
    end case;
end;
/

-- 문법2
accept rps prompt '가위1 바위2 보3 중에 입력하세요.' 
declare
    num number := &rps;
    com number := trunc(dbms_random.value(1, 4)); -- 1.0 이상 4.0미만의 난수 실수 반환
begin
    dbms_output.put_line(num);
    dbms_output.put_line(com);
    -- 사용자
    case 
        when num = 1 then dbms_output.put_line('가위를 내셨습니다.');
        when num = 2 then dbms_output.put_line('바위를 내셨습니다.');
        when num = 3 then dbms_output.put_line('보를 내셨습니다.');
        else dbms_output.put_line('잘못 입력하셨습니다.'); return; -- 조기리턴
    end case;
    -- 컴퓨터
    case com
        when 1 then dbms_output.put_line('컴퓨터는 가위를 냈습니다.');
        when 2 then dbms_output.put_line('컴퓨터는 바위를 냈습니다.');
        when 3 then dbms_output.put_line('컴퓨터는 보를 냈습니다.');
    end case;
    
    -- 가위바위보 결과 출력 
    case
        when com = num 
            then dbms_output.put_line('<<비겼습니다.>>');
        when (num = 1 and com = 3) or (num = 2 and com = 1) or (num = 3 and com = 2) 
            then dbms_output.put_line('<<당신이 이겼습니다.>>');
        else 
            dbms_output.put_line('<<당신이 졌습니다.>>');
    end case;
    
end;
/

-- 반복문
-- 1. 기본반복문 loop
-- 2. while loop
-- 3. for..in loop

-- 기본반복문 (무한반복)

declare
    n number := 1;
begin
    
    loop
        dbms_output.put_line(n);
        n := n + 1; --증감처리  
        
        -- exit 처리
        exit when n = 6;

    end loop;
    
end;
/

-- 1~10사이의 난수 10개 출력
declare
    i number := 1;
    n number;
begin
    loop
        n := trunc(dbms_random.value(1, 11));
        dbms_output.put_line(i || ' : ' || n);
        
        i := i + 1;
        exit when i > 10;
    end loop;
end;
/

-- while loop
-- 조건식일때 참일때 while 반복실행
declare
    n number := 1;
begin
    while n <= 5 loop
        dbms_output.put_line(n);
        n := n + 1;
    end loop;
end;
/

-- 구구단 7단을 출력 (while loop)
declare
    n number;
    dan number := 2;
begin
    while dan <= 9 loop
        
        n := 1;
        while n <= 9 loop
            dbms_output.put_line(dan || ' * ' || n || ' = ' || (dan * n));
            n := n + 1;
        end loop;
        
        dan := dan + 1;
        dbms_output.new_line;
        
    end loop;
end;
/

-- for..in loop
-- 증감변수 선언할 필요 없다.
-- 증감처리는 무조건 +1
-- reverse -1 처리 가능

begin
    for n in 1..10 loop
        dbms_output.put_line(n);
    end loop;
    
    dbms_output.new_line;
    
    for n in reverse 1..10 loop
        dbms_output.put_line(n);
    end loop;
    
end;
/

-- 구구단
begin
    
    for dan in 2..9 loop
        for n in 1..9 loop
            dbms_output.put_line(dan || ' * ' || n || ' = ' || (dan * n));
        end loop;
        dbms_output.new_line;
    end loop;
    
end;
/

-- 200 ~ 223 사원조회
declare
    emprow employee%rowtype;
begin
    for n in 200..223 loop
        select
            *
        into 
            emprow
        from
            employee
        where
            emp_id = n;
            
        dbms_output.put_line(emprow.emp_id || ' : ' || emprow.emp_name);
    end loop;
end;
/

--=========================================
-- DATABASE OBJECT 2
--=========================================
-- pl/sql문법을 사용하는 db객체

-------------------------------------------
-- STORED FUNCTION
-------------------------------------------
-- 저장 사용자함수
-- 미리 컴파일되어 즉시 실행가능한 상태로 보관중이므로, 직접 쿼리를 전송하는 것 대비 효율이 좋다.
-- 무조건 리턴값이 있어야 한다.
-- 함수선언부(변수의 자료형에 크기를 지정하지 않는다), 함수실행부로 구분한다.

create or replace function fn_headphone (p_str varchar2)
return varchar2
is
    -- 지역변수 선언부    
    result varchar2(32767); 
begin
    -- 실행부
    result := 'd' || p_str || 'b';
    return result;
end;
/

-- 일반 sql문에서 함수호출
select
    fn_headphone(emp_name)
from
    employee;

-- 타 pl/sql문(프로시져, 함수)에서 호출
declare
    name varchar2(100) := '&이름';
begin
    dbms_output.put_line('헤드폰을 드려요~ ' || fn_headphone(name));
end;
/

-- 성별을 구하는 함수 fn_gender
create or replace function fn_gender(p_emp_no employee.emp_no%type)
return char
is
begin
    return case substr(p_emp_no, 8, 1)
                    when '1' then '남'
                    when '3' then '남'
                    else '여'
             end;
end;
/

select
    emp_name, fn_gender(emp_no)
from
    employee;

-- 나이를 반환하는 fn_age함수를 작성하고, 사원명, 나이를 조회 (일반 sql문)
create or replace function fn_age(p_emp_no employee.emp_no%type)
return number
is
    birthday date;
begin
    if substr(p_emp_no, 8, 1) in ('1', '2') then
        birthday := to_date(1900 + substr(p_emp_no, 1, 2) || substr(p_emp_no, 3, 4), 'yyyymmdd');
    else 
        birthday := to_date(2000 + substr(p_emp_no, 1, 2) || substr(p_emp_no, 3, 4), 'yyyymmdd');
    end if;
    
    return trunc(months_between(sysdate, birthday) / 12);
end;
/

select 
    emp_name, fn_age(emp_no) age
from
    employee;
    
--=================================================
-- STORED PROCEDURE
--=================================================
-- 일련의 작업절차를 그룹핑한 객체로 호출해서 사용. (일반 sql문에서는 호출불가)
-- 함수와 달리 리턴값이 없다. 대신 out모드 매개변수를 이용해 client에 값전달 가능
-- 미리 컴파일된 형태로 저장되므로, 일반 sql대비 처리효율이 좋다.
-- pl/sql문 또는 exec명령으로 호출/실행할 수 있다.

-- 매개변수에는 in/out 모드가 있다.
--  in 프로시져에 값전달용
-- out 클라이언트에 값전달용 

select * from employee_ex;

-- 부서원 모두 삭제 프로시져
create or replace procedure proc_del_emp_by_dept(
    p_dept_code in employee.dept_code%type -- 자료형 크기는 작성하지 않는다.
)
is
begin
    dbms_output.put_line(p_dept_code || ' 부서원을 삭제합니다');
    
    delete from 
        employee_ex
    where
        dept_code = p_dept_code;
    commit;
end;
/

-- 실행용 익명블럭
begin
    proc_del_emp_by_dept('D5');
end;
/

-- 부서원 조회 프로시져
-- 사번을 받아서 사원명, 부서명, 직급명  조회
-- 조회한 값을 out모드 매개변수에 대입하면 클라이언트에서 확인 가능하다.
create or replace procedure proc_emp (
    p_emp_id in employee.emp_id%type,
    p_emp_name out employee.emp_name%type,
    p_dept_title out department.dept_title%type,
    p_job_name out job.job_name%type
)
is 
begin
    select
        emp_name,
        (select dept_title from department where dept_id = e.dept_code) dept_title,
        (select job_name from job where job_code = e.job_code) job_name
    into
        p_emp_name, p_dept_title, p_job_name
    from
        employee e
    where
        emp_id = p_emp_id;
end;
/

-- 호출용 익명블럭
declare
    v_emp_id employee.emp_id%type := '&사번';
    v_emp_name employee.emp_name%type;
    v_dept_title department.dept_title%type;
    v_job_name job.job_name%type;
begin
    
    -- 사번(in), 사원명(out), 부서명(out), 직급명(out)
    proc_emp(v_emp_id, v_emp_name, v_dept_title, v_job_name);
    
    dbms_output.put_line('사원명 : ' || v_emp_name);
    dbms_output.put_line('부서명 : ' || v_dept_title);
    dbms_output.put_line('직급명 : ' || v_job_name);
    
end;
/

-- 직급명 upsert 예제
-- insert 해당데이터가 없으면 삽입
-- update 해당데이터가 있으면 수정
create table job_ex
as
select * from job;

select * from job_ex;

alter table job_ex
add constraints pk_job_ex_job_code primary key(job_code)
modify job_code varchar2(10)
modify job_name not null;

--  J8 알바 -> insert
--  J7 주임 -> update

create or replace procedure proc_job_ex (
    p_job_code job_ex.job_code%type,
    p_job_name job_ex.job_name%type
)
is
    cnt number;  
begin
    -- 행 존재여부 확인
    select
        count(*)
    into
        cnt
    from
        job_ex
    where
        job_code = p_job_code;

    dbms_output.put_line(cnt);
    
    if cnt = 0 then
        -- insert
        insert into job_ex values (p_job_code, p_job_name);
    else
        -- update
        update job_ex set job_name = p_job_name where job_code = p_job_code;
    end if;
    commit;
end;
/

begin
    proc_job_ex('J8', '일용꾼');
--    proc_job_ex('J1', '보스');
end;
/

select * from job_ex;

------------------------------------------
-- CURSOR
------------------------------------------
-- 쿼리실행결과에 접근할 수 포인터.
-- DQL, DML 모두 cursor를 통해 실행결과 확인 가능

-- 종류
-- 1. 암시적커서(자동)
-- 2. 명시적커서(직접 선언)

-- 속성
-- %rowcount 처리된 행수(DML), fetch된 행수(DQL)
-- %found open이후 fetch된 행이 존재하면 true, 존재하지 않으면 false
-- %notfound open이후 fetch된 행이 존재하면 false, 존재하지 않으면 true
-- %isopen 최근 실행된 sql이 open상태이면 true, close되었으면 false

-- DQL
declare
    emprow employee%rowtype;
begin
    select
        *
    into 
        emprow
    from
        employee
    where
        emp_id = '&사번';
    
    -- 암시적커서 sql
    if sql%found then
        dbms_output.put_line(sql%rowcount || '행이 조회됨');
        dbms_output.put_line(emprow.emp_name);
    end if;
    
end;
/

-- DML
begin
    update 
        employee_ex
    set
        salary = salary + 500000
    where
        dept_code = 'D1';
    dbms_output.put_line(sql%rowcount || '행이 수정되었음'); 
    commit;
    
end;
/

select * from employee_ex;

insert into job_ex values('J9', '견습생');


-- 명시적 커서
-- 사용자가 쿼리결과에 직접 접근해 처리할 수 있게 선언한 객체
-- cursor선언 - open  - fetch(한행씩) - close

declare
    -- 선언
    cursor cs_emp
    is
    select * from employee;
    
    emprow employee%rowtype;
begin
    -- open (쿼리실행)
    open cs_emp;
    
    loop
        -- fetch (한행씩)
        fetch cs_emp into emprow;
        exit when cs_emp%notfound; -- 모든 행을 가져온 경우
        dbms_output.put_line(emprow.emp_id || ' : ' || emprow.emp_name);    
    end loop;
    
    -- close
    close cs_emp;
    
end;
/

-- for..in문을 통한 커서제어
-- 선언 - open - fetch - close 중에 open - fetch - close 과정을 for..in문이 자동으로 처리
-- fetch결과를 담을 변수도 별도의 선언없이 for..in문 안에서 처리

declare
    cursor cs_emp 
    is
    select * from employee e  left join department d on e.dept_code = d.dept_id;
begin
    
    -- row타입변수는 테이블컬럼을 상관치 않는다. (여러테이블 조인해도 ok)
    for erow in cs_emp loop
        dbms_output.put_line(erow.emp_id || ' : ' || erow.emp_name || ' ' || erow.dept_title);
    end loop;
    
end;
/

-- 파라미터를 받는 커서
declare
    cursor cs_emp (p_dept_title department.dept_title%type)
    is
    select * from employee e  left join department d on e.dept_code = d.dept_id where d.dept_title = p_dept_title;
begin
    
    -- row타입변수는 테이블컬럼을 상관치 않는다. (여러테이블 조인해도 ok)
    for erow in cs_emp('&부서명') loop
        dbms_output.put_line(erow.emp_id || ' : ' || erow.emp_name || ' ' || erow.dept_title);
    end loop;
end;
/


--------------------------------------------------
-- TRIGGER
--------------------------------------------------
-- 방아쇠. 연쇄반응의 시작점.
-- 특정이벤트(DML)가 실행되었을때, 연쇄적으로 실행할 코드를 가진 객체

-- 회원탈퇴(DML)시 탈퇴한 회원정보를 탈퇴회원테이블에 insert처리
-- 회원정보수정시, 수정로그를 로그테이블에 insert처리
--  트리거안에서는 TCL처리를 하지 않는다. 선DML의 트랜잭션에 포함되어 처리됨.

/*
create [or replace] trigger 트리거명
    before | after -- 선DML 이전 혹은 이후 실행여부
    insert | update | delete on 테이블명  -- 선DML 대상테이블
    [for each row]  -- 행레벨 트리거 설정 (선DML이 처리행 별로 트리거 실행), 생략하면 문장레벨 트리거(선DML당 1번 실행)
[declare]
    -- 변수 선언 
begin
    -- 실행
end;
/

*/

create table tbl_user (
    id varchar2(20), 
    name varchar2(50),
    constraints pk_tbl_user_id primary key(id)
);

create table tbl_user_log(
    no number, 
    user_id varchar2(20),
    log varchar2(4000),
    log_date date default sysdate,
    constraints pk_tbl_user_log_no primary key(no)
);
create sequence seq_tbl_user_log_no;

-- 의사레코드 :old :new
-- Pseudo Record 트리거에서 사용하는 가상행. 선DML의 값에 접근 가능
-- 행레벨 트리거(for each row) 일때 사용가능
/*
                :old              :new
insert          null              추가된 행
update      변경전 행       변경후 행
delete       삭제전 행       null

*/


create or replace trigger trig_user_log
    before
    insert or update or delete on tbl_user
    for each row 
begin
    if inserting then
        -- 사용자등록시
        insert into 
            tbl_user_log (no, user_id, log)
        values(
            seq_tbl_user_log_no.nextval, 
            :new.id,
            :new.name || '(' || :new.id || ') 사용자 등록'
        );
    elsif updating then
        -- 사용자정보수정시
        insert into 
            tbl_user_log (no, user_id, log)
        values(
            seq_tbl_user_log_no.nextval, 
            :new.id,
            :old.name || ' ---> ' || :new.name || '이름 변경'
        );
    else
        -- 사용자탈퇴시
        insert into 
            tbl_user_log (no, user_id, log)
        values(
            seq_tbl_user_log_no.nextval, 
            :old.id,
            :old.name || '(' || :old.id || ') 사용자 탈퇴'
        );
    end if;

end;
/

insert into tbl_user values('honggd', '홍길동');
update tbl_user set name = '홍길동길동' where id = 'honggd';
delete from tbl_user where id = 'honggd';
    
select * from tbl_user;

select * from tbl_user_log;

-- 상품재고관리
-- 상품테이블(재고수량), 입출고테이블(입고수량 | 출고수량)
create table tb_product(
    pcode varchar2(20),
    pname varchar2(50),
    stock number default 0, -- 재고
    constraints pk_tb_product_pcode primary key(pcode),
    constraints ck_tb_product_stock check(stock >= 0)
);

create table tb_product_io (
    no number, 
    pcode varchar2(20),
    status char(1), -- I  O
    amount number,
    io_date date default sysdate,
    constraints pk_tb_product_io_no primary key(no),
    constraints fk_tb_product_io_pcode foreign key(pcode) references tb_product(pcode)
);

create sequence seq_tb_product_io_no;

insert into
    tb_product
values (
    'apple_iphone_15', '아이폰15', default
);

insert into
    tb_product
values (
    'samsung_galaxy_23', '갤럭시23', default
);

select * from tb_product_io;
select * from tb_product;

insert into
    tb_product_io
values(
    seq_tb_product_io_no.nextval, 
    'samsung_galaxy_23',
    'I',
    30,
    default
);

insert into
    tb_product_io
values(
    seq_tb_product_io_no.nextval, 
    'samsung_galaxy_23',
    'O',
    15,
    default
);

-- 입출고 내역에 따라 자동으로 재고를 관리하는 트리거
create or replace trigger trig_product_stock 
    before
    insert on tb_product_io
    for each row -- 의사레코드 사용가능
begin
    if :new.status = 'I' then
        -- 입고 (수량만큼 재고에 더하기)
        update 
            tb_product
        set
            stock = stock + :new.amount
        where
            pcode = :new.pcode;
    else 
        -- 출고 (수량만큼 재고에 빼기)
        update 
            tb_product
        set
            stock = stock - :new.amount
        where
            pcode = :new.pcode;
    end if;

end;
/

-- before / after 옵션
-- 선DML과 후DML(트리거작업)사이에 fk제약조건이 걸린 경우 이 옵션을 적절히 활용해야 한다.
-- 후DML 테이블이 fk제약조건이 걸린 자식테이블이라면 insert(후DML)은 after로 작동해야 한다.
-- 후DML 테이블이 fk제약조건이 걸린 자식테이블이라면 delete(후DML)은 before로 작동해야 한다.
create table tb_parent(id varchar2(20) primary key);
create table tb_child(parent_id varchar2(20) references tb_parent(id));

insert into tb_parent values ('abcde'); -- 선DML
insert into tb_child values ('abcde'); -- 후DML(트리거) --> after옵션으로 작성

create or replace trigger tirg_parent_child
    after
    insert on tb_parent
    for each row
begin
    insert into tb_child values (:new.id);
end;
/

insert into tb_parent values('honggd');

select * from tb_parent;
select * from tb_child;


create or replace trigger trig_parent_child_del
    before
    delete on tb_parent
    for each row
begin
    delete from tb_child where parent_id = :old.id; -- 후DML
end;
/

delete from tb_parent where id = 'abcde'; -- 선DML










































































