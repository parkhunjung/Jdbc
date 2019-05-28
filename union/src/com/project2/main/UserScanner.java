package com.project2.main;

import java.util.Scanner;

/**
 * 메인 - Scanner 오버라이드 클래스
 * @author sist47
 *
 */
public class UserScanner {
	private Scanner scan;

	public UserScanner() {
		scan = new Scanner(System.in);
	}

	public int nextInt() {

		int n = scan.nextInt();
		scan.skip("\r\n");
		return n;
	}

	public String nextLine() {

		return scan.nextLine();

	}

}
