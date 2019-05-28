package com.project2.teacher;

import java.util.Scanner;

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
