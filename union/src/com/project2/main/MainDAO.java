package com.project2.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * 메인 - 메인 DB연동 클래스
 * @author sist57
 *
 */
public class MainDAO {
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;

	public MainDAO() {

		try {
			DBUtil util = new DBUtil();
			conn = util.connect(DBUtil.SERVERIP, DBUtil.SERVERID, DBUtil.SERVERPW);
			stat = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MainDAO클래스의 생성자 : " + e.toString());
		}
	}

	/**
	 * 메인 - 평일 확인 메소드
	 * @return
	 */
	public String today() {

		String sql = "SELECT " + "    CASE " + "        WHEN TO_CHAR(SYSDATE,'D') IN ('7','1') THEN '휴일' "
				+ "        ELSE '평일' " + "    END AS today" + " FROM DUAL "
				+ " WHERE TO_CHAR(SYSDATE,'YYYYMMDD') NOT IN (SELECT TO_CHAR(regdate,'YYYYMMDD') FROM tblholiday)";
		// 오늘이 공휴일이 아닌것만 뽑아와서 평일인지 휴일인지 반납한다!!
		try {
			ResultSet rs = stat.executeQuery(sql);

			String today = null;
			if (rs.next()) {
				today = rs.getString("today");

			}

			return today;

		} catch (Exception e) {
			System.out.println("MainDAO의 today() : " + e.toString());
		}

		return null;
	}

	
	
	/**
	 * 메인 - 수강중인 정보 번호 반환 메소드
	 * @return
	 */
	public ArrayList<String> learningList() {
		try {
			String sql = "SELECT seq FROM tbllearning WHERE ing = '진행중'";

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<String> list = new ArrayList<String>();

			while (rs.next()) {
				String learn = rs.getString("seq");
				list.add(learn);
			}

			return list;

		} catch (Exception e) {
			System.out.println("MainDAO의 learingList() : " + e.toString());
		}

		return null;

	}

	//TODO 여기!
	/**
	 * 메인 -  
	 * @param seq : 수강중인 정보번호
	 * @return
	 */
	public int check(String seq) {
		try {
			String sql = "SELECT COUNT(*) AS cnt FROM tblattended WHERE learningseq = ? AND TO_CHAR(intime,'YYYYMMDD') = TO_CHAR(SYSDATE,'YYYYMMDD')";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			ResultSet rs = pstat.executeQuery();

			int cnt = -1;
			if (rs.next()) {

				cnt = rs.getInt("cnt");
			}
			return cnt;

		} catch (Exception e) {
			System.out.println("MainDAO의 check() : " + e.toString());
		}

		return -1;

	}

	//TODO 여기!
	/**
	 * 메인 - 
	 * @param seq : 
	 */
	public void inputToday(String seq) {
		String sql = "INSERT INTO tblattended VALUES((SELECT MAX(seq) FROM tblattended)+1, TO_CHAR(SYSDATE,'YYYYMMDD'), TO_CHAR(SYSDATE,'YYYYMMDD'), '결석', ?)";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			pstat.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("MainDAO클래스의 inputToday() : " + e.toString());
			e.printStackTrace();
		}

	}
}