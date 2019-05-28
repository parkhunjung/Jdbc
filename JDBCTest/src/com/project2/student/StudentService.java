package com.project2.student;

import com.project2.main.UserScanner;

public class StudentService implements StudentIService {

	private StudentView view;
	private UserScanner scan;

	public StudentService() {
		view = new StudentView();
		scan = new UserScanner();
	}

	@Override
	public void info() {// 1

		view.info();

		view.pause();
	}

	@Override
	public void lesson() {// 2

		boolean loop = true;
		while (loop) {

			view.lesson();

			try {
				int num = scan.nextInt();

				if (num == 0) {
					loop = false;
				} else {

					lessonDetail();
				}

			} catch (Exception e) {
				view.pauseBack();
			}

		} // while

	}// lesson

	private void lessonDetail() {// 2-1

		view.lessonDetail();

		view.pause();

	}

	@Override
	public void attended() {// 3

		boolean loop = true;

//		if(20개씩 출력하는데 20개보다 많으면) {
		while (loop) {
			view.attended(0);// 출력

			view.attendedSel();// 선택해!!

			String sel = scan.nextLine();

			if (sel.equals("0")) {
				loop = false;
			} else if (sel.equals("1")) {
				// 좌로 이동!!
				view.attended(-1);
			} else if (sel.equals("2")) {
				// 우로 이동!!
				view.attended(+1);
			} else {
				view.pauseBack();
			}

		}
		/*
		 * }else { 20개보다 적으면 그냥 출력해주면 됩니당!!!
		 * 
		 * }
		 */
		view.pause();
	}

}
