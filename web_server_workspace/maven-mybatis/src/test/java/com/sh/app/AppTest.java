package com.sh.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sh.app.common.SqlSessionUtils;

/**
 * 
 * Fixture 메소드
 * - Fixture 테스트에 필요한 상황/환경/의존객체/샘플데이터
 * - @BeforeAll
 * - @AfterAll
 * - @BeforeEach
 * - @AfterEach
 *
 */
class AppTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("@BeforeAll");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("@AfterAll");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("@BeforeEach");
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("@AfterEach");
	}

	/**
	 * assert~ 단정문
	 */
	@Test
	void test() {
		System.out.println("test1");
		int n = 100;
		assertThat(n)
			.isEqualTo(100)
			.isGreaterThan(0)
			.isLessThanOrEqualTo(100);
	}
	
	@Test
	void test2() {
		System.out.println("test2");
		String name = "홍길동";
		assertThat(name)
			.isEqualTo("홍길동");
	}
	
	@DisplayName("SqlSession객체는 null이 아니다.")
	@Test
	void testSqlSessionIsNotNull() throws Exception {
		SqlSession session = SqlSessionUtils.getSqlSession();
		assertThat(session).isNotNull();
	}

}
