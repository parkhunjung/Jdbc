package com.project2.admin;

import java.util.ArrayList;

import java.util.Scanner;

import com.project2.main.AdminDTO;

public class AdminService implements AdminIService {

	private AdminView view;
	private Scanner scan;
	private AdminSubService subService;
	private AdminDAO dao;
	
	public AdminService() {
	    this.dao = new AdminDAO();
		this.view = new AdminView();
		scan = new Scanner(System.in);
		subService = new AdminSubService();
	}

	@Override
	public void teacheraccountmng() {

		boolean loop = true;
		while (loop) {

			view.title(AdminView.TEACHERACCOUNTMNG);
			view.teachermenu();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				subService.add();
			else if (sel.equals("2"))
				subService.tEdit();
			else if (sel.equals("3"))
				subService.tDel();
			else if (sel.equals("0")){
				loop = false;
			} else {
				System.out.println("잘못된입력입니다. 다시입력해주세요.");
			}
		}

	}

	public void opencourse_subjectmng() {

		boolean loop = true;

		while (loop) {

			view.title(AdminView.OPENCOURSE_SUBJECTMNG);
			view.opencourseMenu();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				subService.courseADD();
			else if (sel.equals("2"))
				subService.courseSET();
			else if (sel.equals("3"))
				subService.courseSEARCH();
			else if (sel.equals("0")){
				loop = false;
			} else {
				System.out.println("잘못된입력입니다. 다시입력해주세요.");
			}
		}

		view.pause();

	}

	@Override
	public void studentmng() {

		boolean loop = true;
		while (loop) {

			view.opencourse_studentmng();
			view.studentmng();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				subService.student_Input();
			else if (sel.equals("2"))
				subService.student_Modified();
			else if (sel.equals("3"))
				subService.student_inquiry();
			else if (sel.equals("0")){
				loop = false;
			} else {
				System.out.println("잘못된입력입니다. 다시입력해주세요.");
			}
		}

	}

	@Override
	public void attendancemng() {

		boolean loop = true;
		while (loop) {

			view.opencourse_attendancemng();
			view.attendancemng();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				subService.attend_management();
			else if (sel.equals("2"))
				subService.attend_inquiry();
			else if (sel.equals("0")){
				loop = false;
			} else {
				System.out.println("잘못된입력입니다. 다시입력해주세요.");
			}
		}

	}

	@Override
	public void testmng_scorecheck() {

		boolean loop = true;

		while (loop) {

			view.testmng_scoreCheckMenu();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				subService.scoreADD();
			else if (sel.equals("2"))
				subService.scoreLIST();
			else if (sel.equals("0")){
				loop = false;
			} else {
				System.out.println("잘못된입력입니다. 다시입력해주세요.");
			}
		}

	}
	
	@Override
	public AdminDTO login() {

		ArrayList<AdminDTO> adminList = dao.adminList();
		
		boolean loop = true;
		AdminDTO admin = null;

		while (loop) {
			view.login();

			System.out.print("아이디(이름) : ");
			String id = scan.nextLine();

			System.out.print("비밀번호(주민번호뒷자리) : ");
			String pw = scan.nextLine();

			for (AdminDTO stl : adminList) {
				if (stl.getName().equals(id) && stl.getSsn().substring(7).equals(pw)) {// 해당 아이디,비밀번호의 회원이 있으면

					// 로그인한 학생의 정보를 DTO자료형의 student에 넣는다.
					admin = stl;

					loop = false;

					view.loginSucess();
					view.pause();

					break;

				} // if 같은게 있으면 브레이크

			} // for

			if (admin == null) {
				System.out.println("올바른 입력이 아닙니다!!!");
				System.out.println("엔터키. 다시입력");
				System.out.println("0. 뒤로가기");
				System.out.print("선택 : ");
				String sel = scan.nextLine();

				if (sel.equals("0")) {
					loop = false;

				}

			} // if 틀린값일때 나갈지 다시시도할지 정함

		} // while

		return admin;
	}

	
}
