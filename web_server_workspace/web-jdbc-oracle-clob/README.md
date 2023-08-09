# web jdbc oracle clob
dynamic web project 환경
오라클 사용계정 web/web

[sql파일](sql/web.sql)


## 테스트환경

### 1. dbcp 미사용시
OracleResultSet#getCLOB:CLOB - CLOB#getCharacterOutputStream() 사용

```java
CLOB clob = ((OracleResultSet) rset).getCLOB("content");
try (BufferedWriter bw = new BufferedWriter(clob.getCharacterOutputStream())) {
  bw.write("뉴우우우으으으으으으으으으스으으으으으");	// content 값 쓰기							
}
```

### 2. dbcp 사용시
org.apache.tomcat.dbcp.dbcp2.DelegatingResultSet은 oracle.jdbc.OracleResultSet로 형변환 불가
	
```java
Clob clob =	rset.getClob("content"); 
try (BufferedWriter bw = new BufferedWriter(clob.setCharacterStream(0))) {
	bw.write(getBigContent()); // content 값 쓰기							
}
```