package com.project2.student;

import java.util.Scanner;

import com.project2.main.MainView;
/**
 * 교육생의 출력화면을 담당하는 클래스
 * @author 유병현
 *
 */
public class StudentView {
	public static final int LOGIN = -1;
	public static final int MAIN = 0;
	public static final int INFO = 1;
	public static final int LESSON = 2;
	public static final int SUBJECT = 3;
	public static final int ATTEND = 4;
	public static final int INPUT = 5;
	public static final int LEADER = 6;
	public static final int ROOM = 7;
	public static final int FOLLOWER = 8;
	
	private MainView mview;
	
	public StudentView() {
		mview = new MainView();
	}

	

	public void loginSucess() {// 로그인성공
		System.out.println("\t\t\t\t\t\t\t\t\t\t[로그인 성공이야 !!!]");
	}

	
	public void title(int n) {
		System.out.println("=======================================================================================================================================================================================");

		if(n==StudentView.LOGIN) { //회원의 개인정보 출력
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println(
					"\t\t\t\t\t\t\t\t\t██╗      ██████╗  ██████╗ ██╗███╗   ██╗\r\n" + 
					"\t\t\t\t\t\t\t\t\t██║     ██╔═══██╗██╔════╝ ██║████╗  ██║\r\n" + 
					"\t\t\t\t\t\t\t\t\t██║     ██║   ██║██║  ███╗██║██╔██╗ ██║\r\n" + 
					"\t\t\t\t\t\t\t\t\t██║     ██║   ██║██║   ██║██║██║╚██╗██║\r\n" + 
					"\t\t\t\t\t\t\t\t\t███████╗╚██████╔╝╚██████╔╝██║██║ ╚████║\r\n" + 
					"\t\t\t\t\t\t\t\t\t╚══════╝ ╚═════╝  ╚═════╝ ╚═╝╚═╝  ╚═══╝\r\n" + 
					"                                       "
					);
			System.out.println("\t\t\t\t\t\t\t\t\t\t[교육생 로그인]");
			System.out.println();
			System.out.println();
		}else if(n==StudentView.MAIN) {

			System.out.println(
					"\t\t\t\t\t\t\t\t███████╗████████╗██╗   ██╗██████╗ ███████╗███╗   ██╗████████╗\r\n" + 
					"\t\t\t\t\t\t\t\t██╔════╝╚══██╔══╝██║   ██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝\r\n" + 
					"\t\t\t\t\t\t\t\t███████╗   ██║   ██║   ██║██║  ██║█████╗  ██╔██╗ ██║   ██║   \r\n" + 
					"\t\t\t\t\t\t\t\t╚════██║   ██║   ██║   ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║   \r\n" + 
					"\t\t\t\t\t\t\t\t███████║   ██║   ╚██████╔╝██████╔╝███████╗██║ ╚████║   ██║   \r\n" + 
					"\t\t\t\t\t\t\t\t╚══════╝   ╚═╝    ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   \r\n" + 
					"                                                             "
					);
			
		}else if(n==StudentView.INFO) {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println(
					"\t\t\t\t\t\t\t\t\t██╗███╗   ██╗███████╗ ██████╗ \r\n" + 
					"\t\t\t\t\t\t\t\t\t██║████╗  ██║██╔════╝██╔═══██╗\r\n" + 
					"\t\t\t\t\t\t\t\t\t██║██╔██╗ ██║█████╗  ██║   ██║\r\n" + 
					"\t\t\t\t\t\t\t\t\t██║██║╚██╗██║██╔══╝  ██║   ██║\r\n" + 
					"\t\t\t\t\t\t\t\t\t██║██║ ╚████║██║     ╚██████╔╝\r\n" + 
					"\t\t\t\t\t\t\t\t\t╚═╝╚═╝  ╚═══╝╚═╝      ╚═════╝ \r\n" + 
					"                              "
					);
			System.out.println("\t\t\t\t\t\t\t\t\t\t[교육생 개인정보]");
			System.out.println();
			System.out.println();
		}else if(n==StudentView.LESSON) {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t[수강 과정 목록]");
			System.out.println();
			System.out.println();
			System.out.println("\t\t[번호]\t[과정 기간]\t\t[강의실]\t[과정 상태]\t[총 인원]\t[교육 과정]");
			
		}else if(n==StudentView.SUBJECT) {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t[과정 과목 목록]");
			System.out.println();
			System.out.println();
			System.out.println("\t\t[과목 기간]\t\t[출결 점수]\t[필기 점수]\t[실기 점수]\t[과목명]");
		}else if(n==StudentView.ATTEND) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t[출결 사항 목록]");
			System.out.println();
			System.out.println("\t\t\t\t\t\t[날짜]\t\t[입실]\t[퇴실]\t[상태]\t[과정]");
			
		}else if(n==StudentView.INPUT) {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println(
					"\t\t\t\t\t\t ██████╗██╗  ██╗ ██████╗  ██████╗ ██╗          ██████╗██╗  ██╗███████╗ ██████╗██╗  ██╗\r\n" + 
					"\t\t\t\t\t\t██╔════╝██║  ██║██╔═══██╗██╔═══██╗██║         ██╔════╝██║  ██║██╔════╝██╔════╝██║ ██╔╝\r\n" + 
					"\t\t\t\t\t\t██║     ███████║██║   ██║██║   ██║██║         ██║     ███████║█████╗  ██║     █████╔╝ \r\n" + 
					"\t\t\t\t\t\t██║     ██╔══██║██║   ██║██║   ██║██║         ██║     ██╔══██║██╔══╝  ██║     ██╔═██╗ \r\n" + 
					"\t\t\t\t\t\t╚██████╗██║  ██║╚██████╔╝╚██████╔╝███████╗    ╚██████╗██║  ██║███████╗╚██████╗██║  ██╗\r\n" + 
					"\t\t\t\t\t\t ╚═════╝╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚══════╝     ╚═════╝╚═╝  ╚═╝╚══════╝ ╚═════╝╚═╝  ╚═╝\r\n" + 
					"                                                                                      "
					);
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t[출석 하기]");
			System.out.println();
			System.out.println();
		}else if(n==StudentView.LEADER) {
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t[스터디 강의실 예약]");
			System.out.println();
			System.out.println();
		}else if(n==StudentView.ROOM) {
			System.out.println();
			System.out.println();
			System.out.println(
					"\t\t\t\t\t\t\t██████╗ ███████╗███╗   ██╗████████╗     ██████╗██╗      █████╗ ███████╗███████╗\r\n" + 
					"\t\t\t\t\t\t\t██╔══██╗██╔════╝████╗  ██║╚══██╔══╝    ██╔════╝██║     ██╔══██╗██╔════╝██╔════╝\r\n" + 
					"\t\t\t\t\t\t\t██████╔╝█████╗  ██╔██╗ ██║   ██║       ██║     ██║     ███████║███████╗███████╗\r\n" + 
					"\t\t\t\t\t\t\t██╔══██╗██╔══╝  ██║╚██╗██║   ██║       ██║     ██║     ██╔══██║╚════██║╚════██║\r\n" + 
					"\t\t\t\t\t\t\t██║  ██║███████╗██║ ╚████║   ██║       ╚██████╗███████╗██║  ██║███████║███████║\r\n" + 
					"\t\t\t\t\t\t\t╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝        ╚═════╝╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝\r\n" + 
					"                                                                               "
					);
			System.out.println();
			System.out.println();
		}else if(n==StudentView.FOLLOWER) {
			System.out.println();
			System.out.println();
			System.out.println(
					"\t\t\t\t\t\t███████╗████████╗██╗   ██╗██████╗ ██╗   ██╗     ██████╗██╗  ██╗███████╗ ██████╗██╗  ██╗\r\n" + 
					"\t\t\t\t\t\t██╔════╝╚══██╔══╝██║   ██║██╔══██╗╚██╗ ██╔╝    ██╔════╝██║  ██║██╔════╝██╔════╝██║ ██╔╝\r\n" + 
					"\t\t\t\t\t\t███████╗   ██║   ██║   ██║██║  ██║ ╚████╔╝     ██║     ███████║█████╗  ██║     █████╔╝ \r\n" + 
					"\t\t\t\t\t\t╚════██║   ██║   ██║   ██║██║  ██║  ╚██╔╝      ██║     ██╔══██║██╔══╝  ██║     ██╔═██╗ \r\n" + 
					"\t\t\t\t\t\t███████║   ██║   ╚██████╔╝██████╔╝   ██║       ╚██████╗██║  ██║███████╗╚██████╗██║  ██╗\r\n" + 
					"\t\t\t\t\t\t╚══════╝   ╚═╝    ╚═════╝ ╚═════╝    ╚═╝        ╚═════╝╚═╝  ╚═╝╚══════╝ ╚═════╝╚═╝  ╚═╝\r\n" + 
					"                                                                                       "
					);
			System.out.println();
		}
		

	
	}
	
	public void menu(int n) {
		

		
		if(n == StudentView.LOGIN) {
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t[로그인 실패입니다 !!!]");
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\tEnter. 다시입력");
		}else if(n == StudentView.MAIN) {
			menuMain();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t5. 스터디 확인");
		}else if(n == StudentView.LESSON) {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t원하는 과정[번호]를 입력해주세요");
		}else if(n == StudentView.ATTEND) {
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t원하는 페이지[번호]를 입력해주세요");
		}else if(n == StudentView.LEADER) {
			menuMain();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t5. 스터디 예약");
		}else if(n == StudentView.ROOM) {
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t원하는 강의실[번호]를 입력해주세요");
		}
		
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t\t0. 뒤로가기");
		System.out.println();
		System.out.println();
		System.out.println("=======================================================================================================================================================================================");
		System.out.println();
		System.out.print("\t\t\t\t\t\t\t\t\t선택 : ");

	}

	private void menuMain() {
		System.out.println("\t\t\t\t\t\t\t\t\t\t1. 개인정보 출력");
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t\t2. 수강 과정");
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t\t3. 출결 보기");
		System.out.println();
		System.out.println("\t\t\t\t\t\t\t\t\t\t4. 출석 하기");
	}
	
	

	public void pause() {
		mview.pause();
	}

	public void pauseBack() {
		mview.pauseBack();
	}


}
