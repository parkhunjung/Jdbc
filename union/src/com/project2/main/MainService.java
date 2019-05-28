package com.project2.main;

import java.util.ArrayList;

/**
 * 메인 - 기능 클래스
 * @author sist57
 *
 */
public class MainService {

	

	/**
	 * 메인 - 당일 출석 기본데이터 추가 메소드
	 */
	public void inputTodayAttend() {
		MainDAO dao = new MainDAO();

		String today = dao.today();

		if (today.equals("평일")) {
			ArrayList<String> learningList = dao.learningList();

			for (String seq : learningList) {

				if (dao.check(seq) == 0) {

					dao.inputToday(seq);

				}
			}

		}

	}

}
