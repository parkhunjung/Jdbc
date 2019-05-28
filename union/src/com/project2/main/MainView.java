package com.project2.main;

import java.util.Scanner;

/**
 * 메인 - 인터페이스 클래스
 * @author sist57
 *
 */
public class MainView {

	public final static int ADMIN = 1;
	public final static int TEACHER = 2;
	public final static int STUDENT = 3;


	public void end() {
		System.out.println("프로그램을 종료 합니다.");
	}

	public void menu() {
		
		System.out.println("=======================================================================================================================================================================================");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(
				"\t\t\t\t\t\t\t █████╗  ██████╗ █████╗ ██████╗ ███████╗███╗   ███╗██╗   ██╗\r\n" + 
				"\t\t\t\t\t\t\t██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝████╗ ████║╚██╗ ██╔╝\r\n" + 
				"\t\t\t\t\t\t\t███████║██║     ███████║██║  ██║█████╗  ██╔████╔██║ ╚████╔╝ \r\n" + 
				"\t\t\t\t\t\t\t██╔══██║██║     ██╔══██║██║  ██║██╔══╝  ██║╚██╔╝██║  ╚██╔╝  \r\n" + 
				"\t\t\t\t\t\t\t██║  ██║╚██████╗██║  ██║██████╔╝███████╗██║ ╚═╝ ██║   ██║   \r\n" + 
				"\t\t\t\t\t\t\t╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝     ╚═╝   ╚═╝   \r\n" + 
				"                                                            "
				);
		System.out.println("\t\t\t\t\t\t\t\t[쌍용교육센터에 오신 것을 환영합니다]");
		System.out.println();
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t\t1. 관리자");
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t\t2. 강사");
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t\t3. 교육생");
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t\t0. 종료");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("=======================================================================================================================================================================================");
		System.out.println();
		System.out.print("\t\t\t\t\t\t\t\t\t선택 : ");

	}

	

	public void pause() {

		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t계속하시려면 엔터를 입력하세요.");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();

	}

	public void pauseBack() {

		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t잘못된 입력입니다.");
		System.out.println("\t\t\t\t\t\t\t\t\t계속하시려면 엔터를 입력하세요.");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();

	}

}
