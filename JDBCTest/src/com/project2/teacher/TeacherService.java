package com.project2.teacher;

public class TeacherService implements ITeacherService {

	private TeacherView tview;
	private TeacherSubView tsview;
	private UserScanner scan;

	public TeacherService() {

		tview = new TeacherView();
		tsview = new TeacherSubView();
		scan = new UserScanner();
	}

	public void schedule() {

		tview.scheduleTitle();

		tview.pause();

	}

	public void course() {

		tview.courseTitle();

		tview.pause();
	}

	@Override
	public void grade() {

		tview.gradeTitle();

		tview.pause();

	}

	public void whole() {

		tview.wholeTitle();

		tview.pause();

	}

	public void subjectAttended() {

		tview.subjectAttendedTitle();

		tview.pause();
	}

	public void day() {

		tview.dayTitle();

		tview.pause();
	}

	public void month() {

		tview.monthTitle();

		tview.pause();
	}

	public void year() {

		tview.yearTitle();

		tview.pause();
	}

	@Override
	public void attended() {

		boolean loop = true;

		while (loop) {

			tsview.attended();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				whole();
			else if (sel.equals("2"))
				period();
			else if (sel.equals("3"))
				subjectAttended();
			else if (sel.equals("0"))
				loop = false;
			else
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
		}

	}

	@Override
	public void lessonSchedule() {
		boolean loop = true;

		while (loop) {

			tsview.lessonSchedule();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				schedule();
			else if (sel.equals("2"))
				course();
			else if (sel.equals("0"))
				loop = false;
			else
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
		}
	}

	public void period() {

		boolean loop = true;

		while (loop) {

			tsview.period();

			String sel = scan.nextLine();

			if (sel.equals("1"))
				day();
			else if (sel.equals("2"))
				month();
			else if (sel.equals("3"))
				year();
			else if (sel.equals("0"))
				loop = false;
			else
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
		}
	}

}
