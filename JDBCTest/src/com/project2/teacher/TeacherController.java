package com.project2.teacher;

import java.util.Scanner;

public class TeacherController {

	private TeacherView view;
	private Scanner scan;
	private ITeacherService service;

	public TeacherController() {

		view = new TeacherView();
		scan = new Scanner(System.in);
		service = new TeacherService();
	}

	public void subMain() {

		login();

		boolean loop = true;

		while (loop) {

			view.menu();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				service.lessonSchedule();
			else if (sel.equals("2"))
				service.grade();
			else if (sel.equals("3"))
				service.attended();
			else if (sel.equals("0"))
				loop = false;
			else
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");

		}

	}

	public void login() {

		boolean loop = true;

		while (loop) {
			view.login();

			System.out.print("아이디 : ");
			String tid = scan.nextLine();

			System.out.print("비밀번호 : ");
			String tpw = scan.nextLine();

			loop = false;

			view.loginSucess();

		}
	}

}
