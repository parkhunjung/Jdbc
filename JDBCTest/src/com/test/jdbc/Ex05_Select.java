package com.test.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Date;

public class Ex05_Select {
	public static void main(String[] args) {
		
		
		
//		m1();
//		m2();
//		m3();
//		m4();
//		m5();
//		m6();
//		t1();
//		m7();
		m8();
	}

	private static void m8() {
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
			String sql = "SELECT * FROM tblInsa WHERE num = 1001";
			
			rs = stat.executeQuery(sql);
			
			if(rs.next()) {
				//1. 컬럼값 얻기(******)
				System.out.println(rs.getString(1)); //num
				System.out.println(rs.getString("name"));
				
				//2. 레코드(컬럼) 정보 얻기
				ResultSetMetaData meta =  rs.getMetaData();
				System.out.println(meta.getColumnCount()); //결과셋의 컬럼 수
				
				for(int i=1; i<meta.getColumnCount(); i++) {
					System.out.println(meta.getColumnLabel(i));
					System.out.println(meta.getColumnTypeName(i));
					System.out.println(meta.getColumnDisplaySize(i));
					System.out.println(rs.getString(i));
					System.out.println();
				}
				
				
				
			}
			
			rs.close();
			stat.close();
			conn.close();
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}

	private static void m7() {
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			//1. 기획부 직원 명단 출력
			//2. 인원수 출력
			
			conn = util.connect();
			stat = conn.createStatement();
			
			String sql = "SELECT * FROM tblInsa WHERE buseo = '기획부'";
			
			//** ResultSet은 본인 참조하고 있는 결과 테이블의 행의 개수를 모른다.(알 수 있는 방법이 없음)
			
			rs = stat.executeQuery(sql);
			
			
			//레코드 1개 : if(rs.next())
			//레코드 N개 : while (rs.next())
			
			int cnt = 0;
			while(rs.next()) {
				cnt++;
			}
			System.out.println("인원수 : " + cnt);			
			
			String sql2 = "SELECT COUNT(*) AS cnt FROM tblInsa WHERE buseo = '기획부'";
			//다른 쿼리를 날려야 하는 상황에서 Connection, Statement, ResultSet 재사용성 문제?
			//1. Connection : 재사용 가능
			//2. Statement : 재사용 가능(SQL 종속적이지 않다. SQL 얼마든지 교체 가능)
			//3. ResultSet : 재사용 가능. 해당 결과를 다 소비하기 전에 교체하면 안된다.
			// 쿼리 1개당 ResultSet 1개를 따로 만드는것을 추천
			ResultSet rs2 = stat.executeQuery(sql2);
			
			if(rs2.next()) {
				System.out.println("총 직원수 : " + rs2.getString("cnt"));
			}
			
			rs = stat.executeQuery(sql); //쿼리 재실행(커서의 위치를 초기화하기위해서)
			while (rs.next()) {
				//1바퀴 > 레코드 1개
				System.out.printf("[%s] %s - %s\r\n"
									, rs.getString("buseo")
									, rs.getString("name")
									, rs.getString("jikwi"));
				
				
				
			}
			//System.out.println("인원수 : " + cnt);
			
			rs.close();
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}

	private static void t1() {
		String jumin = "920311-1023511";
		int sum = 0;
		
		jumin = jumin.replace("-", "");
		
		for (int i=0; i<jumin.length()-1; i++) {
			int n = Integer.parseInt(jumin.substring(i, i+1));
		
		
			sum += n * (i%8 + 2);
		}
		
		String valid = (11 - (sum%11))%10 + "";
		
		if(jumin.substring(12).equals(valid)) {
			System.out.println(jumin);
			System.out.println("올바름");
		} else {
			System.out.println("xx");
		}
		
	}

	private static void m6() {
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		try {

			conn = util.connect();
			stat = conn.createStatement();

			String sql = String.format("SELECT name, buseo, jikwi FROM tblInsa ORDER BY name ASC");
			rs = stat.executeQuery(sql);
			
			
			while(rs.next()) {
				System.out.print(rs.getString("name") + ", ");
				System.out.print(rs.getString("buseo") + ", ");
				System.out.println(rs.getString("jikwi"));

			}
//			rs.next();
//			System.out.println(rs.getString("name"));
			
			rs.close();
			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}

	private static void m5() {
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		try {

			conn = util.connect();
			stat = conn.createStatement();

			//Text Query : DB 클라이언트툴에서 작성해서 복붙
			//조인할 때와 뷰 작성 시에 자주 발생 : 모호한 열이름 > 별칭으로 구분시킬것
			String sql = String.format("SELECT m.name AS mname, w.name AS wname FROM tblMen m INNER JOIN tblWomen w ON m.name = w.couple where rownum = 1");
			rs = stat.executeQuery(sql);

			if(rs.next()) {
				
				//남자이름 출력
				System.out.println(rs.getString("mname"));
				//여자이름 출력
				System.out.println(rs.getString("wname"));
				
			}
			
			
			rs.close();
			stat.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}

	private static void m4() {
		
		//seq입력 > 1개 레코드의 여러 컬럼 출력하기
		//select * from tblinsa where num = 1001;
		int num = 1001;
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
			for(int i = num; i <= 1060; i++ ) {
				
				String sql = "SELECT a.*, basicpay + sudang AS total FROM tblInsa a WHERE num = " + num;
				rs = stat.executeQuery(sql);
				num++;
				
				if(rs.next()) {
					
					//컬럼 여러개
					String name = rs.getString("name");
					String buseo = rs.getString("buseo");
					String jikwi = rs.getString("jikwi");
					int basicpay = rs.getInt("basicpay");
					int sudang = rs.getInt("sudang");
					int total = rs.getInt("total");
					
					System.out.println(name);
					System.out.println(buseo);
					System.out.println(jikwi);
					System.out.println(basicpay + sudang);
					System.out.println(total);
					
					
				} else {
					System.out.printf("직원 번호 %s인 직원은 존재하지 않습니다.", num);
				}
			}
			rs.close();
			stat.close();
			conn.close();
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
		
	}

	private static void m3() {
		
		//날짜값 반환
		// > select > 1행 1열 반환
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
			String sql = "select min(completedate) AS cdate FROM tblTodo";
			
			rs = stat.executeQuery(sql);
			
			if(rs.next()) {
				
				//오라클의 date형 > 자바의 어떤 자료형? : String(권장)
//				Date cdate = rs.getDate("cdate");
//				Date cdate = rs.getDate("cdate");
//				System.out.printf("%tF %tT\r\n", cdate, cdate);
				
				String cdate = rs.getString("cdate");
				System.out.println(cdate);
				System.out.println(cdate.split(" ")[0]);
				System.out.println(cdate.split(" ")[1]);
				
				
				//[오라클] 		-> 		[자바]
				//number		-> 		getInt() 추가 산술 연산 필요 O
				//number		-> 		getDouble() 추가 산술 연산 필요 O
				//number		-> 		getString() 추가 산술 연산 필요 X + 출력
				//varchar2		-> 		getString()
				//date			-> 		getString()
				
				
				
				
			}
			
			rs.close();
			stat.close();
			conn.close();
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}

	private static void m2() {
		
		//단일값 반환
		// > select > 1행 1열 반환
		// 1번 주소록의 이름 가져오기
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			
			//ORA-00923: FROM keyword not found where expected => FROM 절을 빼먹었을때(오라클에러)
			String sql = "SELECT name FROM tblAddress WHERE seq = 1";
			rs = stat.executeQuery(sql);
			
			
			//rs.next();
			if (rs.next()) {
				String name = rs.getString("name"); //결과 집합을 모두 소모했음 => 존재하지않는 행을 가르켰을때(자바에러)
				System.out.println(name);
				
			} 
			
			rs.close();
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}

	private static void m1() {
		
		//단일값 반환
		// > select > 1행 1열 반환
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null; //select 결과 테이블 참조 객체(구조가 cursor와 유사함)
		
		try {
		
			conn = util.connect();
			
			
			if(!conn.isClosed()) {
				
				stat = conn.createStatement();
				String sql = "SELECT COUNT(*) AS 행수 FROM tblAddress";
				
				//반환값 없는 쿼리
				//int result = stat.executeUpdate(sql);
				
				//반환값이 있는 쿼리
				rs = stat.executeQuery(sql); // 반환값이 ResultSet 자료형임
				
				//rs : SELECT 결과값
				rs.next(); // 커서를 아래로(다음 레코드로) 한칸 전진
				
				//rs.getXXX(특정컬럼)
				//rs.getInt(index); : 결과셋의 컬럼 위치(index) 1부터 시작(***)
				//rs.getInt("컬럼명"); : 결과셋의 컬럼명
				
//				int cnt = rs.getInt(1);
//				int cnt = rs.getInt("행수"); // **************************압도적으로 많이쓴다.
				String cnt = rs.getString("행수"); // ****************************
				
				//많이쓴다
//				rs.getInt();
//				rs.getDouble();
//				rs.getString();
				
				
				System.out.println("주소록 행 개수 : " + cnt);
				
				//자원해제
				rs.close();
				stat.close();
				conn.close();
				
			} else {
				System.out.println("관리자에게 연락하세요.");
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
		
	}
}
