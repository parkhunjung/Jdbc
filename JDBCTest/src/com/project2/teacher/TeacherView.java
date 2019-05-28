package com.project2.teacher;

import java.util.Scanner;

import com.project2.main.MainView;

public class TeacherView {

	public final static int LESSON = 1;
	public final static int GRADE = 2;
	public final static int ATTENDED = 3;

	private MainView mview;

	public TeacherView() {

		mview = new MainView();

	}

	public void login() {
		System.out.println("[강사 로그인 화면]");
	}

	public void loginSucess() {
		System.out.println("로그인 성공이야 !!!");
	}

	public void menu() {

		System.out.println("============================");
		System.out.println("[강사 메뉴]");
		System.out.println("1. 강의스케줄 조회");
		System.out.println("2. 성적 조회");
		System.out.println("3. 학생 출결조회");
		System.out.println("0. 뒤로가기");
		System.out.println("============================");
		System.out.print("선택 : ");

	}

	public void title(int n) {

		if (n == TeacherView.LESSON) {
			System.out.println("[강의스케줄 조회]");
		} else if (n == TeacherView.GRADE) {
			System.out.println("[성적 조회]");
		} else if (n == TeacherView.ATTENDED) {
			System.out.println("[학생 출결조회]");
		}

	}

	public void scheduleTitle() {
		System.out.println("[스케줄 조회]");
		System.out.println("[과목번호]\t[과정기간]\t[강의실]\t[과목명]\t[과목기간]\t[교재명]\t[학생수]");

	}

	public void courseTitle() {
		System.out.println("[과정별 조회]");
		System.out.println("[과정명]\t[과정기간]");

	}

	public void gradeTitle() {
		System.out.println("[성적 조회]");
		System.out.println("[교육생번호]\t[교육생명]\t[과목명]\t[출석점수]\t[필기점수]\t[실기점수]\t[총점수]");

	}

	public void wholeTitle() {
		System.out.println("[담당학생 전체 출결조회]");
		System.out.println("[출근날짜]\t[출근시간]\t[퇴근시간]\t[이름]\t[상태]");

	}

	public void subjectAttendedTitle() {
		System.out.println("[과목별 출결률 조회]");
		System.out.println("[과목명]\t[출결률]");

	}

	public void dayTitle() {
		System.out.println("[일별 조회]");
		System.out.println("[이름]\t[출근날짜]\t[출근시간]\t[퇴근시간]\t[상태]\t[과정명]\t[과정기간]");

	}

	public void monthTitle() {
		System.out.println("[월별 조회]");
		System.out.println("[이름]\t[출근날짜]\t[출근시간]\t[퇴근시간]\t[상태]\t[과정명]\t[과정기간]");

	}

	public void yearTitle() {
		System.out.println("[년별 조회]");
		System.out.println("[이름]\t[출근날짜]\t[출근시간]\t[퇴근시간]\t[상태]\t[과정명]\t[과정기간]");

	}

	public void pause() {
		mview.pause();
	}

	public void pauseBack() {
		mview.pauseBack();
	}

}
