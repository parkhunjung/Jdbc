package com.project2.teacher;

import java.util.Scanner;

public class TeacherSubView {

	private static Scanner scan;

	static {
		scan = new Scanner(System.in);

	}

	public void lessonSchedule() {

		System.out.println("============================");
		System.out.println("[강의 스케줄 조회 메뉴]");
		System.out.println("1. 스케줄 보기");
		System.out.println("2. 과정별 보기");
		System.out.println("0. 종료");
		System.out.println("============================");
		System.out.print("선택 : ");

	}

	public void attended() {

		System.out.println("============================");
		System.out.println("[교사 출결조회 메뉴]");
		System.out.println("1. 담당학생 전체 출결조회");
		System.out.println("2. 기간별 담당학생 출결조회");
		System.out.println("3. 과목별 출결률 조회");
		System.out.println("0. 종료");
		System.out.println("============================");
		System.out.print("선택 : ");

	}

	public void period() {

		System.out.println("============================");
		System.out.println("[기간별 담당학생 출결조회]");
		System.out.println("1. 일별");
		System.out.println("2. 월별");
		System.out.println("3. 년도별");
		System.out.println("0. 종료");
		System.out.println("============================");
		System.out.print("선택 : ");

	}

}
