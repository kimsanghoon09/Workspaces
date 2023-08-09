--===================================
-- ajax
--===================================
create table celeb (
    no number,
    name varchar2(100) not null,
    profile varchar2(200) default 'default.png', -- 저장된 프로필사진 경로
    celeb_type varchar2(50),
    constraint pk_celeb_no primary key(no) 
);

create sequence seq_celeb_no;

insert into celeb values(seq_celeb_no.nextval, '박보검', '박보검.jpg', 'ACTOR');
insert into celeb values(seq_celeb_no.nextval, '쥴리아 로버츠', 'Julia_Roberts.jpg', 'ACTOR');
insert into celeb values(seq_celeb_no.nextval, '맷 데이먼', 'Matt_Damon.jpg', 'ACTOR');
insert into celeb values(seq_celeb_no.nextval, '차은우', '차은우.png', 'SINGER');
insert into celeb values(seq_celeb_no.nextval, '춘리', '춘리.png', 'MODEL');
insert into celeb values(seq_celeb_no.nextval, '카리나', '카리나.png', 'SINGER');
insert into celeb values(seq_celeb_no.nextval, '정재영', '정재영.jpg', 'ACTOR');
insert into celeb values(seq_celeb_no.nextval, '아이유', '아이유.jpg', 'SINGER');
insert into celeb values(seq_celeb_no.nextval, '오킹', '오킹.jpg', 'ENTERTAINER');
insert into celeb values(seq_celeb_no.nextval, '김고은', '김고은.jpg', 'ACTOR');
insert into celeb values(seq_celeb_no.nextval, '매즈 미켈슨', '매즈미켈슨.jpg', 'ACTOR');
commit;
select * from celeb;
