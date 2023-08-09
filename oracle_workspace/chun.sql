--==========================================
-- 춘대학 학사시스템 @chun
--==========================================
select * from tb_department;
select * from tb_student;
select * from tb_class;
select * from tb_professor;
select * from tb_class_professor;
select * from tb_grade;

--@실습문제 :  group by | having 
-- 1. 학과 테이블에서 계열별 정원 평균 조회 (내림차순 정렬)
select
    category,
    trunc(avg(capacity))
from
    tb_department
group by
    category
order by
    2 desc;

-- 2. 학생테이블에서 휴학생을 제외하고 학과별 학생수 조회(인원수 내림차순)
select
    department_no,
    count(*)
from
    tb_student
where
    absence_yn = 'N'
group by 
    department_no
order by 
    2 desc;


-- 3. 교수과목테이블에서 과목별 지정교수가 2명이상이 과목번호/교수인원수 조회
select
    class_no,
    count(*)
from
    tb_class_professor
group by
    class_no
having 
    count(*) >= 2;

-- 4. 학과별로 과목을 구분했을때, 과목구분이 '전공선택'에 한하여 과목수가 10개이상인 데이터의 학과번호/과목구분/과목수 조회
select
    department_no,
    class_type,
    count(*)
from
    tb_class
group by
    department_no, class_type
having
    class_type = '전공선택'
    and
    count(*) >= 10;
    

-- 조인실습문제
-- 1. 필요한 테이블 조회
-- 2. 연결 컬럼 찾기
-- 3. 내부/외부조인 파악 - 누락된 행여부 조사

--1. 의학계열 학과학생들의 학번, 학생명, 학과명 조회
select * from tb_student;
select * from tb_department;
select
    s.student_no, s.student_name, d.department_name
from
    tb_student s  join tb_department d
      on s.department_no = d.department_no
where
    d.category = '의학';


--2.  2005학번 학생명, 담당교수명 조회 (담당교수 없는 학생도 조회)
select * from tb_student where coach_professor_no  is null;
select * from tb_professor;
-- tb_student.coach_professor_no 가 null인 학생은 내부조인에서 제외
-- tb_professor에서 상응하는 tb_student행이 없는 교수행은 내부조인 제외

select
    s.student_name, p.professor_name
from
    tb_student s left join tb_professor p
        on s.coach_professor_no = p.professor_no
where
    extract(year from s.entrance_date) = 2005;


--3. 자연과학 계열의 수업명, 학과명 조회
select * from tb_department;
select * from tb_class;

select
    c.class_name,
    d.department_name
from
    tb_class c left join tb_department d
        on c.department_no = d.department_no
where
    d.category = '자연과학';


--4. 담당학생이 한명도 없는 교수정보 조회
select * from tb_professor;
select * from tb_student;
-- 내부조인 579 (담당교수가 지정된 학생)
-- 좌측외부조인 588 : 내부조인 + 9 (담당교수 없는 학생)
-- 우측외부조인 580 : 내부조인 + 1 (담당학생 없는 교수)
select
    p.*
from
    tb_student s right join tb_professor p
        on s.coach_professor_no = p.professor_no
where
    s.coach_professor_no is null;

-- 수업번호 | 수업명 | 선수수업번호 | 선수수업명
-- 선수수업이 있는 경우만 조회
select
    c.class_no, 
    c.class_name,
    p.class_no, 
    p.class_name
from
    tb_class c join tb_class p
        on c.preattending_class_no = p.class_no;

--@실습문제 - 조인1
--2. 학과별 교수명과 교수인원수를 모두 표시하세요.
--학과지정을 받지 못한 교수여부 조사 -> 1명 있음
select * from tb_department; -- 63
select * from tb_professor; -- 114
select distinct(department_no) from tb_professor; -- 52 (51 + 1)

-- inner join : 113행
--    tb_professor 학과지정 안된 교수 1명이 제외
--    tb_department 교수가 존재하지 않는 학과 12개 제외
-- outer join 
--    tb_department 기준 외부조인 125
--    tb_professor 기준 외부조인 114

select
    d.department_name,
    decode(grouping(p.professor_name), 0, p.professor_name, 1, count(*)) professor_name
from
    tb_department d right join tb_professor p
        on d.department_no = p.department_no
group by 
    rollup(d.department_name, p.professor_name)
order by 
    1;



select 
    department_no 학과번호,
    student_name 학생명
from 
    tb_student 
order by 
--    department_no asc, student_name desc;
--    2, 3 desc;
    학과번호, 학생명 desc;



select
    s.student_name 학생명,
    round(avg(g.point),2) 학점
from
    tb_student s left join tb_grade g
        on s.student_no = g.student_no        
where 
    s.student_name like '%람'
group by 
    s.student_no,
    s.student_name;




