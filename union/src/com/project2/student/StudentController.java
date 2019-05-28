package com.project2.student;

import com.project2.main.StudentDTO;
import com.project2.main.StudyDTO;
import com.project2.main.UserScanner;
/**
 * 교육생의 메인클래스
 * @author 유병현
 *
 */
public class StudentController {
	private StudentView view;
	private StudentIService service;
	private UserScanner scan = new UserScanner();

	public StudentController() {
		view = new StudentView();
		service = new StudentService();

	}
	/**
	 * 교육생 메인클래스의 메인역할 메소드
	 */
	public void subMain() {
		StudentDTO student = service.login();
		
		
		if (student!=null) {

			boolean loop = true;
			while (loop) {
				StudyDTO leader = null;
				leader = service.leaderLogin(student.getSeq());
				
				
				view.title(StudentView.MAIN);
				System.out.printf("\t\t\t\t\t\t\t\t\t['%s' 교육생님 안녕하세요!]\n",student.getName());
				if(leader == null) {
					System.out.println();
					view.menu(StudentView.MAIN);
				}else {
					System.out.println();
					System.out.printf("\t\t\t\t\t\t\t\t\t['%s'의 스터디대표]\n",leader.getName());
					System.out.println();
					view.menu(StudentView.LEADER);
				}
				
				String sel = scan.nextLine();
				
				
				if (sel.equals("0")) {//뒤로가기
					loop = false;
				} else if (sel.equals("1")) {//특정 학생의 개인정보
					service.info(student);

				} else if (sel.equals("2")) {//특정 학생의 수강과정보기
					service.lesson(student.getSeq());

				} else if (sel.equals("3")) {
					service.attended(student.getSeq());

				} else if (sel.equals("4")) {
					service.inputAttend(student.getSeq());
					
				} else if (sel.equals("5") && leader != null) {
					service.leader(leader);

				} else if (sel.equals("5") && leader == null) {
					service.followers(student.getSeq());
					
				} else {
					view.pauseBack();
				}
				
				
			} // while
			
		} // if로그인 성공
		
	}// subMain

}
