package com.project2.teacher;

import java.util.Scanner;

/**
 * 강사 - 사용자 인터페이스 클래스
 * @author sist56
 *
 */
public class TeacherSubView {

	private static UserScanner scan;

	static {
		scan = new UserScanner();

	}


	public void attended() {

		System.out.println("\t\t\t\t\t\t\t\t\t============================");
		System.out.println("\t\t\t\t\t\t\t\t\t[강사 출결조회 메뉴]");
		System.out.println("\t\t\t\t\t\t\t\t\t1. 담당교육생 전체 출결조회");
		System.out.println("\t\t\t\t\t\t\t\t\t2. 기간별 담당교육생 출결조회");
		System.out.println("\t\t\t\t\t\t\t\t\t3. 과목별 출결률 조회");
		System.out.println("\t\t\t\t\t\t\t\t\t0. 종료");
		System.out.println("\t\t\t\t\t\t\t\t\t============================");
		System.out.print("\t\t\t\t\t\t\t\t\t선택 : ");

	}

	
	
	public void period() {

	      System.out.println("\t\t\t\t\t\t\t\t\t============================");
	      System.out.println("\t\t\t\t\t\t\t\t\t[기간별 담당교육생 출결조회]");
	      System.out.println("\t\t\t\t\t\t\t\t\t1. 일별");
	      System.out.println("\t\t\t\t\t\t\t\t\t2. 월별");
	      System.out.println("\t\t\t\t\t\t\t\t\t3. 년도별");
	      System.out.println("\t\t\t\t\t\t\t\t\t0. 종료");
	      System.out.println("\t\t\t\t\t\t\t\t\t============================");
	      System.out.print("\t\t\t\t\t\t\t\t\t선택 : ");

}

	public void lessonSchedule() {

	      System.out.println("\t\t\t\t\t\t\t\t\t============================");
	      System.out.println("\t\t\t\t\t\t\t\t\t[강의 스케줄 조회 메뉴]");
	      System.out.println("\t\t\t\t\t\t\t\t\t1. 스케줄 보기");
	      System.out.println("\t\t\t\t\t\t\t\t\t2. 과정별 보기");
	      System.out.println("\t\t\t\t\t\t\t\t\t0. 종료");
	      System.out.println("\t\t\t\t\t\t\t\t\t============================");
	      System.out.print("\t\t\t\t\t\t\t\t\t선택 : ");
	   

	   }
	
	
}

