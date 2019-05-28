package com.test.memo2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//데이터 처리 담당자
//주로 Service 객체가 DAO를 호출하는 경우가 많다. > 메소드(업무) 단위로 호출
public class DAO {
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	
	//초기화(선행 작업)
	// - DB접속
	public DAO() {
		try {
		
			DBUtil util = new DBUtil();
			this.conn = util.connect();
			this.stat = conn.createStatement();
		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DAO.Construtor");
		}
		
	}
	
	public boolean isConnected() {
		
		try {
			return !this.conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}//접속중이면 true 접속끊기면 false 반환
	
	
	public void close() {
		
		try {
			
			this.conn.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	//컨트롤러 > 서비스 > DAO
	public int add(DTO dto) {
		
		//1. stat or pstat : 매개변수 유무 (있으면 pstat, 없으면 stat)
		String sql = "INSERT INTO tblMemo(seq, name, memo, priority, regdate) VALUES (memo_seq.nextVal, ?, ?, ?, default)";
		
		try {
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getMemo());
			pstat.setString(3, dto.getPriority());
			
			return pstat.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DAO.add");
		}
		
		return 0;
	}

	public ArrayList<DTO> list() {
		
		try {
			
			String sql = "SELECT * FROM vwMemo";
			
			ResultSet rs = stat.executeQuery(sql);
			
			//ResultSet을 다른객체에게 전달하기 위해 그에 준하는 자료형으로 변경 > ArrayList<DTO>
			//ResultSet == 테이블 == ArrayList<DTO>
			//레코드 == 행
			//컬럼 == 값
			
			ArrayList<DTO> list = new ArrayList<DTO>();
			
			//rs -> 복사 -> list
			while (rs.next()) {
				//레코드 1개 => DTO 1개
				DTO dto = new DTO();
				
				dto.setSeq(rs.getString("seq")); // 컬럼값 => DTO멤버변수
				dto.setName(rs.getString("Name"));
				dto.setMemo(rs.getString("Memo"));
				dto.setPriority(rs.getString("priorityLabel"));
				dto.setRegdate(rs.getString("regtime"));
				
				list.add(dto);
				
				
			}
			
			return list;
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return null;
		
	}//list

	public ArrayList<DTO> elist() {
		try {
			
			String sql = "SELECT seq, name, memo FROM vwMemo";
			
			ResultSet rs = stat.executeQuery(sql);
			
			ArrayList<DTO> list = new ArrayList<DTO>();
			
			while (rs.next()) {
				//레코드 1개 => DTO 1개
				DTO dto = new DTO();
				
				dto.setSeq(rs.getString("seq")); 
				dto.setName(rs.getString("Name"));
				dto.setMemo(rs.getString("Memo"));
				
				list.add(dto);
				
				
			}
			
			return list;
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return null;
	
	}//elist

	public DTO get(String seq) {
		
		try {
			
			//sql = String.format("SELECT * FROM tblMemo WHERE seq = %s", seq);
			String sql = "SELECT * FROM tblMemo WHERE seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			ResultSet rs = pstat.executeQuery();
			
			DTO dto = new DTO(); // 복사용
			
			if(rs.next()) {
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setMemo(rs.getString("Memo"));
				dto.setPriority(rs.getString("priority"));
				dto.setRegdate(rs.getString("regdate"));
				
				return dto; //빼먹지 말것!!
				
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		return null;
	}

	public int edit(DTO dto) {
		
		try {
			
			String sql = "UPDATE tblMemo SET name = ?, memo = ?, priority = ? WHERE seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getMemo());
			pstat.setString(3, dto.getPriority());
			pstat.setString(4, dto.getSeq());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return 0;
	}

	public int del(String seq) {
		
		try {
			
			String sql = "DELETE FROM tblMemo WHERE seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
			
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		return 0;
	}

	public ArrayList<DTO> list(String word) {
		
		try {
			
			String sql = "SELECT * FROM vwMemo WHERE memo LIKE '%' || ? || '%'";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, word);
			
			ResultSet rs = pstat.executeQuery();
			
			//ResultSet을 다른객체에게 전달하기 위해 그에 준하는 자료형으로 변경 > ArrayList<DTO>
			//ResultSet == 테이블 == ArrayList<DTO>
			//레코드 == 행
			//컬럼 == 값
			
			ArrayList<DTO> list = new ArrayList<DTO>();
			
			//rs -> 복사 -> list
			while (rs.next()) {
				//레코드 1개 => DTO 1개
				DTO dto = new DTO();
				
				dto.setSeq(rs.getString("seq")); // 컬럼값 => DTO멤버변수
				dto.setName(rs.getString("Name"));
				dto.setMemo(rs.getString("Memo"));
				dto.setPriority(rs.getString("priorityLabel"));
				dto.setRegdate(rs.getString("regtime"));
				
				list.add(dto);
				
				
			}
			rs.close();
			return list;
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return null;
		
		
		
	}
	
	
	
}











