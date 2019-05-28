package com.test.jdbc;

import java.sql.Connection;
import java.sql.Statement;

public class Ex03_Statement {
	
	public static void main(String[] args) {
		
		
		/*
		
		Statement 클래스
		- 모든 SQL 구문을 실행하는 역할
		- 스스로는 해당 쿼리를 어떤 DB에 질의?? > Connection 객체에 의존
		
		
		Statement 클래스의 종류
		1. Statement
			- 기본 클래스
			- Text Query 사용
		2. PreparedStatement
			- 개량 클래스
			- Text Query 사용
			
		--------------------------
			
		3. CollableStatement
			- 프로시저 전용 클래스
			- 프로시저 사용
			
		
		
		
		*/
		
		//주소록에 데이터 1줄 추가하기
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		
		try {
			
			//1. 접속
			conn = util.connect();
			
			//3. 업무 > 쿼리 실행
			// - 반환값이 없는 쿼리 실행
			// - INSERT INTO tblAddress (seq, name, age, tel, address, regdate) VALUES (address_seq.nextVal, '아무개', 25, '010-4321-8765', '서울시 강남구 대치동', DEFAULT);
			
			Statement stat = conn.createStatement();
			
			// 한번에 한문장만 실행이 가능하다. (여러 문장을 실행 못함) > 이건 jdbc만이그렇고 다른언어는 가능한언어가 대부분이다.
			String sql = "INSERT INTO tblAddress (seq, name, age, tel, address, regdate) VALUES (address_seq.nextVal, '아무개', 25, '010-4321-8765', '서울시 강남구 대치동', DEFAULT)";
			
			//SQL Developer 에서 Ctrl + Enter 와 동일한 행동, 쿼리실행
			//적용된 행의 개수
			int result = stat.executeUpdate(sql); 
			
			
			if(result == 1) {
				System.out.println("성공");
			} else {
				System.out.println("실패");
			}
			
			
			//2. 해제
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
		
		
	}
	
	
}
























