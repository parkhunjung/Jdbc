package com.project2.main;

import java.util.Scanner;

public class MainView {

	public final static int ADMIN = 1;
	public final static int TEACHER = 2;
	public final static int STUDENT = 3;

	public void begin() {
		System.out.println("쌍용교육센터에 오신것을 환영합니다.");
	}

	public void end() {
		System.out.println("프로그램을 종료 합니다.");
	}

	public void menu() {
		System.out.println("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈");
		System.out.println("██╗  ██╗███████╗██╗     ██╗      ██████╗ \r\n"
				+ "██║  ██║██╔════╝██║     ██║     ██╔═══██╗\r\n" + "███████║█████╗  ██║     ██║     ██║   ██║\r\n"
				+ "██╔══██║██╔══╝  ██║     ██║     ██║   ██║\r\n" + "██║  ██║███████╗███████╗███████╗╚██████╔╝\r\n"
				+ "╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝ ╚═════╝ \r\n" + "                                         ");
		System.out.println("[쌍용교육센터]");
		System.out.println("1. 관리자");
		System.out.println("2. 교사");
		System.out.println("3. 학생");
		System.out.println("0. 종료");
		System.out.println("============================");
		System.out.print("선택 : ");

	}

	public void title(int n) {

		if (n == MainView.ADMIN) {
			System.out.println("[관리자]");
		} else if (n == MainView.TEACHER) {
			System.out.println("[교사]");
		} else if (n == MainView.STUDENT) {
			System.out.println("[학생]");
		}

	}

	public void pause() {

		System.out.println();
		System.out.println("계속하시려면 엔터를 입력하세요.");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();

	}

	public void pauseBack() {

		System.out.println();
		System.out.println("잘못된 입력입니다.");
		System.out.println("계속하시려면 엔터를 입력하세요.");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();

	}

}
