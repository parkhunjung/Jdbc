package com.test.memo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Ex07_PreparedStatement {
	
	public static void main(String[] args) {
		
		//tblInsa 부장 명단 출력(2명 이상이면 급여 많은 사람 선택)
		
		DBUtil util = new DBUtil();
		Connection conn = null;
		Statement stat = null;
		PreparedStatement pstat = null;
		
		try {
			
			conn = util.connect();
			stat = conn.createStatement();
			String sql = "SELECT" + 
					"    buseo," + 
					"    jikwi," + 
					"    (SELECT name FROM tblinsa WHERE buseo = a.buseo AND jikwi = '부장' AND rownum = 1) as name" + 
					" FROM tblinsa a WHERE jikwi = '부장' GROUP BY buseo,jikwi" + 
					"    ORDER BY buseo,jikwi";
			
			
			ResultSet rs = stat.executeQuery(sql);
			
			//하위 쿼리 객체
			sql = "SELECT * FROM tblinsa WHERE buseo = ? AND jikwi <> '부장'";
			pstat = conn.prepareStatement(sql);
			
			
			
			while (rs.next()) {
				System.out.printf("[%s 부장] %s\r\n"
									, rs.getString("buseo")
									, rs.getString("name"));
				
				//또 다른 하위 쿼리 실행
				// - 현재 부서에 속한 나머지 직원 명단 가져오기
				//pstat =  conn.prepareStatement(sql);
				pstat.setString(1, rs.getString("buseo"));
				
				ResultSet subrs = pstat.executeQuery();
				
				while(subrs.next()) {
					System.out.println(" - " + subrs.getString("name"));
				}
				
				subrs.close();
				
			}
			
			rs.close();
			stat.close();
			conn.close();
			pstat.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}
}
