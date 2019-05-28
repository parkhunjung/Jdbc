package com.test.memo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Ex06_PreparedStatement {
	
	
	
	
	public static void main(String[] args) {

		
		//statement > PreparedStatement
		//선택 기준
		//1. 매개변수의 유무
		//	a. 매개변수가 있으면 > PreparedStatement
		//		- INSERT INTO tblAddress (name) VALUES (?)
		//	b. 매개변수가 없으면 > STATEMENT
		// 		- 정적 쿼리 > SELECT + FROM tblAddress
		
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		PreparedStatement pstat = null;
		
		//사용자 입력 + 주소록 추가하기
		String name = "하하하";
		String age = "25";
		String tel = "010-1234-5678";
		String address = "서울시 동대문구 이문동";
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
			//1. SQL
			String sql = String.format("INSERT INTO tblAddress (seq, name, age, tel, address, regdate)"
										+ " Values (address_seq.nextval, '%s', %s, '%s', '%s', default)"
										, name, age, tel, address);
			
			//2. SQL 실행
			//stat.executeUpdate(sql);
			
			
			//3. 자원 해제
			stat.close();
			System.out.println("실행 완료");
			
			sql = "INSERT INTO tblAddress (seq, name, age, tel, address, regdate)"
					+ " Values (address_seq.nextval, ?, ?, ?, ?, default)"; // ? : 오라클 매개변수
			
			pstat = conn.prepareStatement(sql);
			
			//매개변수의 값을 전달하는 과정에서 오라클의 부적합한 문자들을 자동으로 이스케이프 시켜준다.(유효하게 만든다.)
			pstat.setString(1, name);
			pstat.setString(2, age); //*** 오라클 기준(x), 자바 기준(o) age 타입
			pstat.setString(3, tel);
			pstat.setString(4, address);
			
			pstat.executeUpdate();
			
			pstat.close();
			System.out.println("실행 완료");
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	
	}
	
	
}













