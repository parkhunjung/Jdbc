package com.project2.student;

import java.util.ArrayList;

import com.project2.main.RoomDTO;
import com.project2.main.StudentDTO;
import com.project2.main.StudyDTO;
import com.project2.main.UserScanner;

/**
 * 교육생 기능 클래스
 * @author sist46
 *
 */
public class StudentService implements StudentIService {

	private StudentView view;
	private UserScanner scan;
	private StudentDAO dao;
	private static final int PAGESIZE = 10;
	public StudentService() {
		view = new StudentView();
		scan = new UserScanner();
		dao = new StudentDAO();
	}
	
	
	@Override
	/**
	 * 교육생의 로그인 메소드
	 * @return StudentDTO : 조건에 맞는 교육생정보 반납
	 */
	public StudentDTO login() {// 특정 학생의 로그인

		ArrayList<StudentDTO> studentList = dao.studentList();//모든 학생의정보
		
		boolean loop = true;
		StudentDTO student = null;

		while (loop) {
			view.title(StudentView.LOGIN);
			
			System.out.print("\t\t\t\t\t\t\t\t아이디(이름) : ");
			String id = scan.nextLine();

			System.out.print("\t\t\t\t\t\t\t\t비밀번호(주민번호뒷자리) : ");
			String pw = scan.nextLine();

			for (StudentDTO stl : studentList) {
				if (stl.getName().equals(id) && stl.getSsn().substring(7).equals(pw)) {// 해당 아이디,비밀번호의 회원이 있으면

					
					student = stl;// 로그인한 학생의 정보를 DTO자료형의 student에 넣는다.

					loop = false;//while문을 나가기위해 false

					view.loginSucess();
					view.pause();

					break;//원하는값얻었으니 for문 탈출
				}//if

			} // for

			if (student == null) {//만약 일치하는 회원이없으면(정보가 안담긴 null)
				view.menu(StudentView.LOGIN);//다시입력하려면 엔터키 나갈려면 0번
				String sel = scan.nextLine();

				if (sel.equals("0")) {//0번이면 false로 while문 탈출
					loop = false;
				}//if
			}//if

		} // while

		return student;//안돌려주면 오류남..
	}

	@Override
	/**
	 * 교육생의 개인정보 출력 메소드
	 * @param StudentDTO student : 특정 학생의 정보
	 */
	public void info(StudentDTO student) {// 1 학생의 개인정보 

		view.title(StudentView.INFO);
		//이건 여기서 출력하는게 맞다고 판단된다!!! 인자값을 넘길순없잖아아아 ~~
		System.out.printf("\t\t\t\t\t\t\t\t이름 : %s\n",student.getName());
		System.out.println();
		System.out.printf("\t\t\t\t\t\t\t\t주민번호 : %s\n",student.getSsn());
		System.out.println();
		System.out.printf("\t\t\t\t\t\t\t\t전화번호 : %s\n",student.getTel());
		System.out.println();
		System.out.printf("\t\t\t\t\t\t\t\t주소 : %s\n",student.getAddress());
		System.out.println();
		System.out.printf("\t\t\t\t\t\t\t\t가입날짜 : %s\n",student.getRegdate().substring(0, 10));

		
		view.pause();
	}

	@Override
	/**
	 * 교육생의 수강했던 혹은 수강중인 과정 출력
	 * @param String seq : 특정교육생의 고유번호
	 */
	public void lesson(String seq) {// 2
	
		ArrayList<StudentCourseDTO> courseList = dao.courseList(seq);
		
		boolean loop = true;
		while (loop) {
			view.title(StudentView.LESSON);
			
			
			for (int i = 0; i < courseList.size(); i++) {
				StudentCourseDTO dto = courseList.get(i);
				
				System.out.printf("\t\t[%d]\t%s ~ %s\t%s\t\t%s\t\t%s\t\t%s\n"
						,i+1,dto.getBegindate(),dto.getEnddate()
						,dto.getRoom(),dto.getStatus()
						,dto.getTotalPeople(),dto.getTitle());
			}
			
			
			if(courseList.size()>=1) {//size가 있으면!!
				view.menu(StudentView.LESSON);
				
				try {
					int num = scan.nextInt();
	
					if (num == 0) {
						loop = false;
					} else {
						subject(courseList.get(num-1));
					}
	
				} catch (Exception e) {
					view.pauseBack();
				}

			}else {
				System.out.println("\t\t\t\t\t\t\t\t\t\t[수강한(했던) 과정이 없습니다]");
				view.pause();
				loop = false;
				
			}
			
		} // while

	}// lesson
	
	
//---------------------------------
	private void subject(StudentCourseDTO course) {// 2-1

		
		view.title(StudentView.SUBJECT);
		ArrayList<StudentSubjectDTO> subject =  dao.subjectList(course);
	
		if(subject.size()>=1) {
			for (StudentSubjectDTO dto : subject) {
				System.out.printf("\t\t%s ~ %s\t%s\t\t%s\t\t%s\t\t%s\n"
						,dto.getBeigndate(),dto.getEnddate()
						,dto.getAttendPoint(),dto.getPilPoint(),dto.getSilPoint()
						,dto.getTitle()
						);
			}
		}else {
			System.out.println("\t\t\t\t\t\t\t\t\t\t[해당 과정의 과목이 없습니다] ?과정이있는데 이런일이 발생할수있나?");
		}
		
		view.pause();

	}
//-------------------------------------

	
	@Override
	/**
	 * 교육생의 출결내역 출력 메소드
	 * @param String seq : 특정교육생의 고유번호
	 */
	public void attended(String seq) {// 3
		
		ArrayList<StudentAttendDTO> attend = dao.attendedList(seq);; 
		
				
		boolean loop = true;

		if(attend.size()>=1 && attend.size()<=PAGESIZE) {
			
			attendedDetail(attend, 0, 0);
			
			view.pause();
		}else if(attend.size()>PAGESIZE){
			int tail = (attend.size()%PAGESIZE) != 0 ? 1 : 0; 
					
			attendedDetail(attend, 1, tail);
			
			
			while (loop) {
				
				view.menu(StudentView.ATTEND);// 선택해!!
				
				try {
					scan = new UserScanner();//자꾸 문자열입력하면 엔터키를 가져간다고해야되나? 씹혀서 새로 선언
					int num = scan.nextInt();
					
					
					if (num==0) {
						loop = false;
						//view.pause();
					} else if (num>0 && num <= attend.size()/PAGESIZE + tail) {
						attendedDetail(attend, num, tail);
					} else {
						view.pauseBack();
						attendedDetail(attend, 1, tail);
					}
				} catch (Exception e) {
					view.pauseBack();
					attendedDetail(attend, 1, tail);
				}
			}
		}else {
			view.title(StudentView.ATTEND);
			System.out.println("\t\t\t\t\t\t\t\t\t\t[출결 기록이 없습니다]");
			view.pause();
		}
		
	}

	private void attendedDetail(ArrayList<StudentAttendDTO> attend, int n, int tail) {
		view.title(StudentView.ATTEND);
		
		if(n==0) {
			for (int i = 0; i < attend.size(); i++) {
				StudentAttendDTO dto = attend.get(i);
				System.out.printf("\t\t\t\t\t\t%s\t%s\t%s\t%s\t%s\n"
						,dto.getIntime().substring(0,10)
						,(dto.getIntime().substring(10,16).equals(" 00:00") ? "입실전" : dto.getIntime().substring(10,16))
						,(dto.getOutime().substring(10,16).equals(" 00:00") ? "퇴실전" : dto.getOutime().substring(10,16))
						,dto.getStatus(), dto.getCourseTitle());
			}
		}else if(n==attend.size()/PAGESIZE + tail) {
			for (int i = PAGESIZE*(n-1); i < attend.size(); i++) {
				StudentAttendDTO dto = attend.get(i);
				System.out.printf("\t\t\t\t\t\t%s\t%s\t%s\t%s\t%s\n"
						,dto.getIntime().substring(0,10)
						,(dto.getIntime().substring(10,16).equals(" 00:00") ? "입실전" : dto.getIntime().substring(10,16))
						,(dto.getOutime().substring(10,16).equals(" 00:00") ? "퇴실전" : dto.getOutime().substring(10,16))
						,dto.getStatus(), dto.getCourseTitle());
			}
			System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t%2d/%2d\n",n,attend.size()/PAGESIZE+tail);
		}else {
			for (int i = PAGESIZE*(n-1); i < PAGESIZE*n; i++) {
				StudentAttendDTO dto = attend.get(i);
				System.out.printf("\t\t\t\t\t\t%s\t%s\t%s\t%s\t%s\n"
						,dto.getIntime().substring(0,10)
						,(dto.getIntime().substring(10,16).equals(" 00:00") ? "입실전" : dto.getIntime().substring(10,16))
						,(dto.getOutime().substring(10,16).equals(" 00:00") ? "퇴실전" : dto.getOutime().substring(10,16))
						,dto.getStatus(), dto.getCourseTitle());
			}
			System.out.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t%2d/%2d\n",n,attend.size()/PAGESIZE+tail);
		}
		
	}

	@Override
	/**
	 * 교육생의 당일날 출석하기 메소드
	 * @param String seq : 특정 교육생의 고유번호
	 */
	public void inputAttend(String seq) { 

		view.title(StudentView.INPUT);
		
		String io = dao.checkAttend(seq);//출력용 SELECT문
		int check = dao.inputAttend(seq);//실질적 출석 UPDATE문
	
		if(check!=0) {
			//System.out.printf("\t\t\t\t\t\t\t\t\t\t[출석 성공이야 !!!%s]\n",check);
			System.out.printf("\t\t\t\t\t\t\t\t\t\t[출석 성공이야 !!!]\n");
			System.out.println("");
			System.out.printf("\t\t\t\t\t\t\t\t\t\t[%s]\n",io);
		}else {
			System.out.println("\t\t\t\t\t\t\t\t\t\t[출석 실패입니다 !!!]");
		}
		
		view.pause();

	}
	@Override
	/**
	 * 교육생이 스터디 조장인지 확인하고 맞으면 스터디정보를 반납하는 메소드
	 * @param String seq : 특정 교육생의 고유번호
	 * @return StudyDTO : 특정 스터디의 정보를 반납
	 */
	public StudyDTO leaderLogin(String seq) {
		return dao.leaderLogin(seq); //해당 스터디 자체를 가져올까요???
		
	}

	@Override
	/**
	 * 스터디조장인 교육생의 강의실빌리고 결과를 출력하는 메소드
	 * @param StudyDTO leader : 특정 스터디의 정보
	 */
	public void leader(StudyDTO leader) {
		
		boolean loop = true;
		while(loop) {
			view.title(StudentView.LEADER);
			
			ArrayList<StudentRoomStatusDTO> rentList = dao.leader();
			ArrayList<RoomDTO> roomList = dao.leaderRoomList();
			boolean check = true;
			
			System.out.println("\t\t\t\t\t\t\t\t\t\t[예약 불가]");
			System.out.println("\t\t\t\t\t\t\t\t\t[강의실]\t[스터디명]");
			for (StudentRoomStatusDTO rent : rentList) {
				System.out.printf("\t\t\t\t\t\t\t\t\t%s\t%s\n"
						,rent.getRoomtitle(), rent.getStudytitle());
				
				if(rent.getStudyseq().equals(leader.getSeq())) {
					check = false;//만약 이미 빌린 전적이있으면 못빌림!!
					System.out.println("\t\t\t\t\t\t\t\t\tㄴ>[이미 예약한 강의실이 있습니다]");
					System.out.println();
					break; //해도되고 안해도되고
				}
			}
			System.out.println();
			
			if(roomList.size()<1||check==false) {
				System.out.println("\t\t\t\t\t\t\t\t\t[예약 가능한 강의실이 없습니다]");
				view.pause();
				loop = false;
			}else {
				
				System.out.println("\t\t\t\t\t\t\t\t\t\t[예약 가능]");
				System.out.println("\t\t\t\t\t\t\t\t\t[번호]\t[강의실]\t[정원]");
				
				for (int i=0; i<roomList.size(); i++) {
					RoomDTO room = roomList.get(i);
					System.out.printf("\t\t\t\t\t\t\t\t\t[%s]\t%s\t\t%s\n"
							,i+1 ,room.getName(), room.getLimit());
				}
				
				view.menu(StudentView.ROOM);
				try {
					scan = new UserScanner();//자꾸 문자열입력하면 엔터키를 가져간다고해야되나? 씹혀서 새로 선언
					int num = scan.nextInt();	
					
					if(num==0) {
						loop = false;
					}else if(num>0 && num<=roomList.size()){
						studyRoomRent(leader, roomList.get(num-1));
						loop = false;
						
					}else {
						view.pauseBack();
					}
					
						
				} catch (Exception e) {
					view.pauseBack();
				}	
			}//else 강의실을 빌릴수 있을때
			
		}//while
		
	}

	private void studyRoomRent(StudyDTO leader, RoomDTO room) {
		view.title(StudentView.ROOM);
		int result = dao.studyRoomRent(room.getSeq(), leader.getSeq());
		if(result != 0) {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t[강의실 예약 성공이야 !!!]");
			System.out.println();
			System.out.printf("\t\t\t\t\t\t\t\t\t\t스터디 : [%s]\n",leader.getName());
			System.out.println();
			System.out.printf("\t\t\t\t\t\t\t\t\t\t[%s]을 예약했습니다\n",room.getName());
			System.out.println();
		}else {
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t\t\t\t[강의실 예약 실패입니다 !!!]");
			System.out.println();
		}
		
		
		view.pause();
	}

	@Override
	/**
	 * 스터디 조장이 아닌 교육생의 자신의 스터디의 강의실예약상태 확인 메소드
	 * @param String seq : 특정 교육생의 고유번호
	 */
	public void followers(String seq) {
		StudyDTO dto = dao.followers(seq);
		view.title(StudentView.FOLLOWER);
		if(dto!= null) {
			
			StudentRoomStatusDTO check = dao.followersStudyCheck(dto);
			if(check!= null) {
				System.out.printf("\t\t\t\t\t\t\t\t\t스터디 [%s]은(는)\n", check.getStudytitle());
				System.out.println();
				System.out.printf("\t\t\t\t\t\t\t\t\t오늘 [%s]에 예약입니다\n",check.getRoomtitle());
			}else {
				System.out.printf("\t\t\t\t\t\t\t\t\t스터디 [%s]은(는)\n",dto.getName());
				System.out.println();
				System.out.printf("\t\t\t\t\t\t\t\t\t오늘 예약한 강의실이 없습니다\n");
			}
			
		}else {
			System.out.println();
			System.out.println("수강중인 스터디가 없습니다 관리자에게 문의하여 수강을 신청하세요!");
		}
		System.out.println();
		System.out.println();
		view.pause();
		
	}

	
	
}
