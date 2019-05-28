package com.project2.student;

import com.project2.main.StudentDTO;
import com.project2.main.UserScanner;

public class StudentController {
	private StudentView view;
	private StudentIService service;
	private UserScanner scan = new UserScanner();
	private static StudentDTO loginStudent;

	public StudentController() {
		view = new StudentView();
		service = new StudentService();

	}

	static {
		loginStudent = new StudentDTO();
	}

	public void subMain() {
		login();

		boolean loop = true;
		while (loop) {

			view.selMain();

			String sel = scan.nextLine();

			if (sel.equals("0")) {
				loop = false;
			} else if (sel.equals("1")) {
				service.info();

			} else if (sel.equals("2")) {
				service.lesson();

			} else if (sel.equals("3")) {
				service.attended();

			} else {
				view.pauseBack();
			}
		} // while

//		회원 = null;

	}// subMain

	private void login() {
		boolean loop = true;
		while (loop) {
			view.login();

			System.out.print("아이디 : ");
			String id = scan.nextLine();

			System.out.print("비밀번호 : ");
			String pw = scan.nextLine();
			/*
			 * if(id.equals("아이디")) { if(pw.equals("해당패스워드")){
			 */
			loop = false;
			view.loginSucess();
			view.pause();
			// 로그인한 학생의 정보를 DTO자료형의 loginStudent에 넣는다.

			/*
			 * } view.pauseBack(); }else { view.pauseBack(); }
			 */

		} // while

	}

}
