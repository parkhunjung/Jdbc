package com.project2.student;

import java.util.Scanner;

import com.project2.main.MainView;

public class StudentView {

	private MainView mview;

	public StudentView() {
		mview = new MainView();
	}

	public void login() {// 로그인
		System.out.println("[교육생 로그인 화면]]");
	}

	public void loginSucess() {// 로그인성공
		System.out.println("로그인 성공이야 !!!");
	}

	public void selMain() { // 메인!!
		System.out.println("============================");
		System.out.println("[특정 교육생 메인 화면]");
		System.out.println("1. 개인정보 출력");
		System.out.println("2. 수강 과정");
		System.out.println("	ㄴ출력 + 특정과정 선택시 점수(출결, 실기, 필기)");
		System.out.println("3. 출결 관리");
		System.out.println("0. 뒤로가기");
		System.out.println("============================");
		System.out.print("선택 : ");
	}

	public void info() { // 1

		System.out.println("[특정 교육생 개인정보 출력]");
		System.out.println("이름 : xxx");
		System.out.println("나이 : xxx");
		System.out.println(".....");
		// ㄴ 회원의 개인정보를 출력한다.
		// SELECT * FROM tblStudent WHERE name = ? or seq = ?
	}

	public void lesson() { // 2
		System.out.println("============================");
		System.out.println("[특정 교육생의 수강중인 or 했던 or 중도포기 과정 출력]");
		System.out.println("1. xxxxx과정");
		System.out.println("2. xxxxx과정");
		System.out.println("...");
		// SELECT * FROM tblCourse WHERE seq
		// IN (SELECT courseseq FROM tblleaning WHERE studentseq = leaning)
		// ORDER BY 상태(진행중 - 수료 - 중도포기 순)
		System.out.println("0. 뒤로가기");
		System.out.println("============================");
		System.out.print("선택 : ");

	}

	public void lessonDetail() {// 2-1
		System.out.println("[특정교육생의 특정과정 선택 화면]");
		System.out.println("과목1	출결	실기	필기	기간(선택? 필수?)");
		System.out.println("과목2	출결	실기	필기");
		System.out.println("아직 끝나지않은 과목이면 미완같이 뜨게 하기");

	}

	public void attended(int n) {// 3
		System.out.println("[출결관리]");
		System.out.println("날짜		입장	퇴장	상태	과정");
		System.out.println("2019-01-12	09:00	18:00	지각	자바왕!");
		System.out.println("2019-01-13	09:00	18:00	지각	자바왕!");
		System.out.println(".....");

	}

	public void attendedSel() {// 3-1
		System.out.println("============================");
		System.out.println("0. 뒤로가기");
		System.out.println("1. 페이지 <<<<<");
		System.out.println("2. 페이지 >>>>>");
		System.out.println("============================");
		System.out.println("선택 : ");

	}

	public void pause() {
		mview.pause();
	}

	public void pauseBack() {
		mview.pauseBack();
	}

}
