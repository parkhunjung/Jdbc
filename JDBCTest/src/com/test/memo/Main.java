package com.test.memo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	
	private static Scanner scan;
	private static Connection conn;
	private static Statement stat;
	
	
	static {
		scan = new Scanner(System.in);
		try {
			
			DBUtil util = new DBUtil();
			conn = util.connect(); //프로그램 시작 시 DB 접속
			stat = conn.createStatement();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String[] args) {
		
		boolean loop = true;
		
		System.out.println("메모장을 시작합니다.");
		
		
		while(loop) {
			
			System.out.println("============================");
			try {
				System.out.printf("[메모장] - %s\r\n", conn.isClosed() ? "Not Connected" : "Connected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("1. 메모 쓰기");
			System.out.println("2. 메모 읽기");
			System.out.println("3. 메모 수정하기");
			System.out.println("4. 메모 삭제하기");
			System.out.println("5. 메모 검색하기");
			System.out.println("0. 종료");
			System.out.println("============================");
			System.out.print("선택 : ");
			
			String sel = scan.nextLine();
			
			if(sel.equals("1")) add();
			else if(sel.equals("2")) list();
			else if(sel.equals("3")) edit();
			else if(sel.equals("4")) del();
			else if(sel.equals("5")) search();
			else if(sel.equals("0")) loop = false;
			else System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
		}
		
		System.out.println("메모장을 종료합니다.");
		
		try {
			stat.close();
			conn.close();			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

	private static void add() {
		
		System.out.println("[메모 쓰기]");
		
		System.out.print("작성자 : ");
		String name = scan.nextLine();
		name = name.replace("'", "''"); // 오라클은 식별자를 이스케이프 시킬때 ''를 두개를써서 의미를 지운다.
		
		

		System.out.print("메모 : ");
		String memo = scan.nextLine();
		memo = memo.replace("'", "''");
		
		System.out.print("중요도(1.높음, 2.보통, 3.낮음) : ");
		String priority = scan.nextLine();
		
		String sql = String.format("INSERT INTO tblMemo (seq, name, memo, priority, regdate) VALUES (memo_seq.nextVal, '%s', '%s', %s, default)"
									, name, memo, priority);
		
		try {
			
			int result = stat.executeUpdate(sql);
			
			if(result == 1) {
				System.out.println("메모를 작성했습니다.");
			} else {
				System.out.println("메모를 작성하지 못했습니다.");
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		scan.nextLine(); //pause();
		
	}

	private static void list() {
		
		System.out.println("[메모 읽기]");
		
		System.out.println("[번호]\t[작성자]\t[중요도]\t[날짜]\t\t[메모내용]");
		
		//String sql = "SELECT * FROM tblMemo ORDER BY seq DESC";
		//String sql = "SELECT seq, name, memo, TO_CHAR(regdate, 'hh24:mi:ss') AS \"regtime\", CASE WHEN priority = 1 THEN '높음' WHEN priority = 2 THEN '보통' WHEN priority = 3 THEN '낮음' END AS \"priorityLabel\" FROM tblMemo ORDER BY seq DESC";
		String sql = "SELECT * FROM vwMemo";
		
		
		try {
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				// 레코드 1개 > 메모 1건 > 출력
				//System.out.println(rs.getString("memo"));
				System.out.printf("%s\t%s\t\t%s\t\t%s\t%s\r\n"
									, rs.getString("seq")
									, rs.getString("name")
									, rs.getString("priorityLabel")
									, rs.getString("regtime")
									, rs.getString("memo"));
				
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scan.nextLine();
		
	}

	private static void edit() {
		
		//목록 출력 > 선택 > 메모 내용 출력 > 수정 입력 > 수정
		System.out.println("[메모 수정하기]");
		
		System.out.println("[번호]\t[작성자]\t[메모 내용]");
		
		String sql = "SELECT seq, name, memo FROM vwMemo";
		try {
			
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				System.out.printf("%s\t%s\t\t%s\r\n"
									, rs.getString("seq")
									, rs.getString("name")
									, rs.getString("memo"));
			}
			
			
			
			
			rs.close();
			
			System.out.print("수정할 번호 : ");
			String seq = scan.nextLine();
			
			sql = String.format("SELECT * FROM tblMemo WHERE seq = %s", seq);
			
			rs = stat.executeQuery(sql);
			
			String name = "";
			String memo = "";
			String priority = "";
			
			if(rs.next()) {
				name = rs.getString("name");
				memo = rs.getString("memo");
				priority = rs.getString("priority");
			}
			
			rs.close();
			
			System.out.println("기존 작성자 : " + name);
			System.out.print("새로운 작성자 : ");			
			String temp = scan.nextLine();
			
			if(!temp.equals("")) {
				name = temp; //사용자가 입력한 이름으로 교환
				name = name.replace("'", "''");
			}
			
			System.out.println("기존 메모 : " + memo);
			System.out.print("새로운 메모 : ");			
			temp = scan.nextLine();
			
			if(!temp.equals("")) {
				memo = temp; //사용자가 입력한 메모 내용으로 교환
				memo = memo.replace("'", "''");
			}
			
			System.out.println("기존 중요도 : " + priority);
			System.out.print("새로운 중요도(1.높음, 2.보통, 3.낮음) : ");			
			temp = scan.nextLine();
			
			if(!temp.equals("")) {
				priority = temp; //사용자가 입력한 중요도로 교환
			}
			
			//변수 3개 > 사용자 의도가 반영 > 기존값 or 새값
			sql = String.format("UPDATE tblMemo SET name = '%s', memo = '%s'"
								+ ", priority = '%s' WHERE seq = %s"
									, name, memo, priority, seq);
			
			if (stat.executeUpdate(sql) == 1) {
				System.out.println("메모를 수정했습니다.");
			} else {
				System.out.println("메모를 수정하지 못했습니다.");
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scan.nextLine();
		
	}

	private static void del() {
		
		//목록 출력 > 선택 > 삭제
		System.out.println("[메모 삭제하기]");
		
		System.out.println("[번호]\t[작성자]\t[메모 내용]");
		
		String sql = "SELECT seq, name, memo FROM vwMemo";
		try {
			
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				System.out.printf("%s\t%s\t\t%s\r\n"
									, rs.getString("seq")
									, rs.getString("name")
									, rs.getString("memo"));
			}
			
			
			
			
			rs.close();
			
			System.out.print("삭제할 번호 : ");
			String seq = scan.nextLine();
			
			sql = String.format("DELETE FROM tblMemo WHERE seq = %s", seq);
			
			if(stat.executeUpdate(sql) == 1) {
				System.out.println("메모를 삭제했습니다.");
			} else {
				System.out.println("메모를 삭제하지 못했습니다.");
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		scan.nextLine();
		
	}

	private static void search() {
		
		//검색어 입력 > 목록 출력(WHERE)
		System.out.println("[메모 검색하기]");
		
		System.out.print("검색어 입력 : "); //메모 내용 검색
		String word = scan.nextLine();
		
		
		System.out.println("[번호]\t[작성자]\t[중요도]\t[날짜]\t\t[메모내용]");
		
		String sql = String.format("SELECT * FROM vwMemo WHERE memo LIKE '%%%s%%'", word);
		
		
		try {
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				
				System.out.printf("%s\t%s\t\t%s\t\t%s\t%s\r\n"
									, rs.getString("seq")
									, rs.getString("name")
									, rs.getString("priorityLabel")
									, rs.getString("regtime")
									, rs.getString("memo"));
				
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scan.nextLine();
		
		
		
		
		
		
		
	}
}
