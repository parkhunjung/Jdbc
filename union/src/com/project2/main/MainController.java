package com.project2.main;


import com.project2.admin.AdminController;
import com.project2.student.StudentController;
import com.project2.teacher.TeacherController;

/**
 * 메인 - 메인클래스
 * @author sist57
 *
 */
public class MainController {

	private static MainView mview;
	private static UserScanner scan;
	
	static {

		mview = new MainView();
		scan = new UserScanner();

	}

	/**
	 * 프로그램 실행 메소드
	 * @param args
	 */
	public static void main(String[] args) {
		StudentController student = new StudentController();
		TeacherController teacher = new TeacherController();
		AdminController admin = new AdminController();
		
		MainService service = new MainService();
		
		service.inputTodayAttend();
		

		boolean loop = true;

		while (loop) {

			mview.menu();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				admin.subMain();
			else if (sel.equals("2"))
				teacher.subMain();
			else if (sel.equals("3"))
				student.subMain();
			else if (sel.equals("0"))
				loop = false;
			else
				mview.pauseBack();
		}

		mview.end();

	}

}
