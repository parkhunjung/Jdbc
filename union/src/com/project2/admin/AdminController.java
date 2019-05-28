package com.project2.admin;

import java.util.Scanner;

import com.project2.main.AdminDTO;

/**
 * 관리자 - 메인클래스
 * @author sist47
 *
 */
public class AdminController {

	private AdminView view;
	private AdminIService service;
	private Scanner scan;

	public AdminController() {

		view = new AdminView();
		service = new AdminService();
		scan = new Scanner(System.in);

	}

	public void subMain() {
		AdminDTO admin = service.login();

		boolean loop = true;
		if(admin != null ) {
			while (loop) {
	
				view.menu();
	
				String sel = scan.nextLine();
	
				if (sel.equals("1"))
					service.teacheraccountmng();
				else if (sel.equals("2"))
					service.opencourse_subjectmng();
				else if (sel.equals("3"))
					service.studentmng();
				else if (sel.equals("4"))
					service.attendancemng();
				else if (sel.equals("5"))
					service.testmng_scorecheck();
				else if (sel.equals("0")){
					loop = false;
				} else {
					view.pauseBack();
				}
			}
		}
		
		view.end();

	}

}
