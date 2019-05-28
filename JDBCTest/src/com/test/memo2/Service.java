package com.test.memo2;

import java.util.ArrayList;
import java.util.Scanner;

//업무 담당자
//데이터 입출력, 화면 입출력, 데이터 연산 가공 등등
//단, 업무가 과하거나 반복되면 다른 객체에게 위임(혼자 다하면 복잡도 상승;;;; > 분산)
public class Service implements IService {
	
	//업무 중 일부가 DAO에게 위임 > 선택
	//private DAO dao;
	private View view;
	private Scanner scan;
	
	public Service() {
		//this.dao = new DAO();
		view = new View();
		scan = new Scanner(System.in);
	}

	@Override
	public void add() {
		
		// 메인 컨트롤러 > (위임) > 서비스객체
		view.title(View.ADD);
		
		System.out.print("작성자 : ");
		String name = scan.nextLine();
		name = name.replace("'", "''");
		
		

		System.out.print("메모 : ");
		String memo = scan.nextLine();
		memo = memo.replace("'", "''");
		
		System.out.print("중요도(1.높음, 2.보통, 3.낮음) : ");
		String priority = scan.nextLine();
		
		//1. DB작업 > dao 위임하는 작업
		DAO dao = new DAO();
		
		DTO dto = new DTO(); //2. 서비스 객체가 DAO에게 전달하기 위해 포장하는 작업
		dto.setName(name);
		dto.setMemo(memo);
		dto.setPriority(priority);
		
		
		//3. dao에게 포장한 작업을 전달 > 맡긴 작업을 확인하기 위해 result 체크
		// 성공하면 1 실패하면 0 을 받아서 성공과 실패를 확인
		int result = dao.add(dto);
		
		dao.close(); //작업이끝나면 무조건 닫기
		
		if(result == 1) {
			System.out.println("메모 작성을 완료했습니다.");
		} else {
			System.out.println("메모 작성을 실패했습니다.");
		}
		
		view.pause();
		
		
	}//add

	@Override
	public void list() {
		
		view.title(View.LIST);
		
		System.out.println("[번호]\t[작성자]\t[중요도]\t[날짜]\t\t[메모내용]"); //원래는 뷰로 넘겨야하는 작업
		
		//DB 작업 > dao 위임(select)
		DAO dao = new DAO();
		
		ArrayList<DTO> list = dao.list();
		
		//rs.next() -> 컬럼값 //재탐색 불가능, 부가 기능 X
		//배열 탐색(컬렉션 탐색) //재탐색 가능, 부가 기능 O
		for (DTO dto : list) {
			System.out.printf("%s\t%s\t%s\t%s\t%s\r\n"
								, dto.getSeq()
								, dto.getName()
								, dto.getPriority()
								, dto.getRegdate()
								, dto.getMemo());
		}
		
		
		dao.close();
		
		view.pause();
		
		
	}//list

	@Override
	public void edit() {
		
		view.title(View.EDIT);
		
		System.out.println("[번호]\t[작성자]\t[메모 내용]");
		
		DAO dao = new DAO();
		
		//수정할 메모를 선택하기 위한 목록 가져오기
		ArrayList<DTO> list = dao.elist();
		
		for(DTO dto : list) {
			System.out.printf("%s\t%s\t\t%s\r\n"
								, dto.getSeq()
								, dto.getName()
								, dto.getMemo());
		}
		
		
		System.out.print("수정할 번호 : ");
		String seq = scan.nextLine();
		
		DTO dto = dao.get(seq); //메모 1건 (= 레코드 1개 == DTO 1개)
		
		String name = "";
		String memo = "";
		String priority = "";
		
		if(dto != null) {
			name = dto.getName();
			memo = dto.getMemo();
			priority = dto.getPriority();
		}
		
		
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
		
		
		dto = new DTO();
		dto.setSeq(seq); //수정할 번호(where)
		dto.setName(name);
		dto.setMemo(memo);
		dto.setPriority(priority);
		
		
		int result = dao.edit(dto);
		
		if (result == 1) {
			System.out.println("메모 수정 완료");
		} else {
			System.out.println("메모 수정 실패");
		}
		
		dao.close();
		
		view.pause();
		
	}//edit

	@Override
	public void del() {
		
		view.title(View.DEL);
		
		System.out.println("[번호]\t[작성자]\t[메모 내용]");
		
		DAO dao = new DAO();
		
		//삭제할 메모를 선택하기 위한 목록 가져오기
		ArrayList<DTO> list = dao.elist();
		
		for(DTO dto : list) {
			System.out.printf("%s\t%s\t\t%s\r\n"
								, dto.getSeq()
								, dto.getName()
								, dto.getMemo());
		}
		
		
		
		System.out.print("삭제할 번호 : ");
		String seq = scan.nextLine();
		
		int result = dao.del(seq);
		
		if(result == 1) {
			System.out.println("삭제 성공");
		} else {
			System.out.println("삭제 실패");
		}
		
		dao.close();
		view.pause();
		
		
	}//del

	@Override
	public void search() {
		
		view.title(View.SEARCH);
		
		System.out.print("검색어 입력 : ");
		String word = scan.nextLine();
		
		System.out.println("[번호]\t[작성자]\t[중요도]\t[날짜]\t\t[메모내용]");
		
		DAO dao = new DAO();
		
		ArrayList<DTO> list = dao.list(word);
		
		for (DTO dto : list) {
			System.out.printf("%s\t%s\t%s\t%s\t%s\r\n"
								, dto.getSeq()
								, dto.getName()
								, dto.getPriority()
								, dto.getRegdate()
								, dto.getMemo());
		}
		
		
		dao.close();
		
		view.pause();
		
	}//search
	
	
	
}














