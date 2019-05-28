package com.project2.admin;

import java.util.ArrayList;

import java.util.Scanner;

import com.project2.main.AdminDTO;

/**
 * 관리자 - 기능 클래스 호출 클래스
 * @author sist47
 *
 */
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
				view.pauseBack();
			}
		}
		
		view.pause();

	}

	public void opencourse_subjectmng() {

		boolean loop = true;

		while (loop) {

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
				view.pauseBack();
			}
		}

		view.pause();

	}

	@Override
	public void studentmng() {

		boolean loop = true;
		while (loop) {

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
				view.pauseBack();
			}
		}

		view.pause();
	}

	@Override
	public void attendancemng() {

		boolean loop = true;
		while (loop) {

			view.attendancemng();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				subService.attend_management();
			else if (sel.equals("2"))
				subService.attend_inquiry();
			else if (sel.equals("0")){
				loop = false;
			} else {
				view.pauseBack();
			}
		}
		
		view.pause();

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
				view.pauseBack();
			}
		}
		
		view.pause();

	}
	
	@Override
	public AdminDTO login() {

		dao = new AdminDAO();
		ArrayList<AdminDTO> adminList = dao.adminList();
		
		boolean loop = true;
		AdminDTO admin = null;

		while (loop) {
			view.login();

			System.out.print("\t\t\t\t\t\t\t\t\t아이디(이름) : ");
			String id = scan.nextLine();

			System.out.print("\t\t\t\t\t\t\t\t\t비밀번호(주민번호뒷자리) : ");
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
				System.out.println("\t\t\t\t\t\t\t\t\t올바른 입력이 아닙니다!!!");
				System.out.println("\t\t\t\t\t\t\t\t\t다시입력하시려면 엔터를 입력해주세요.");
				System.out.println("\t\t\t\t\t\t\t\t\t0. 뒤로가기");
				System.out.print("\t\t\t\t\t\t\t\t\t선택 : ");
				String sel = scan.nextLine();

				if (sel.equals("0")) {
					loop = false;

				}

			} // if 틀린값일때 나갈지 다시시도할지 정함

		} // while

		dao.close(); 				//dao 닫으면 메인으로 나갔다가 다시 관리자로 들어올 때 에러남
		return admin;
	}

	
}
