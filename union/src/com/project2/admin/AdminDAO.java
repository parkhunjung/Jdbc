package com.project2.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project2.main.AdminDTO;
import com.project2.main.BookDTO;
import com.project2.main.CourseDTO;
import com.project2.main.CourseSubjectDTO;
import com.project2.main.CourseTitleDTO;
import com.project2.main.DBUtil;
import com.project2.main.LearningDTO;
import com.project2.main.StudentDTO;
import com.project2.main.SubjectDTO;
import com.project2.main.TeacherDTO;


/**
 * 관리자 - DB연동 클래스
 * @author sist47
 *
 */
public class AdminDAO {

	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;

	public AdminDAO() {

		try {
			DBUtil util = new DBUtil();
			this.conn = util.connect(DBUtil.SERVERIP, DBUtil.SERVERID, DBUtil.SERVERPW);
			this.stat = conn.createStatement();

		} catch (SQLException e) {
			System.out.print("AdminDAO 오류 : ");
			e.printStackTrace();
		}

	}
	
	/**
	 * DB연동 확인 메소드
	 * @return
	 */
	public boolean isConnected() {

		try {
			return !this.conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}// 접속중이면 true 접속끊기면 false 반환
	
	
	/**
	 * DB접속 종료 메소드
	 */
	public void close() {

		try {

			this.conn.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	
	/**
	 * 관리자 - 총 과정 개수 반환 메소드
	 * @return
	 */
	public int courseCnt() {

		String sql = "SELECT Count(*) AS cnt FROM tblcoursetitle";

		try {

			ResultSet rs = stat.executeQuery(sql);

			rs.next();
			int result = rs.getInt("cnt");

			rs.close();

			return result;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return 0;
	}
	
	

	/**
	 * 관리자 - 실기 등록 여부 조회 메소드
	 * @param cseq : 입력받은 과정번호
	 * @return
	 */
	public ArrayList<SilgiRegiDTO> sRegi(int cseq) {

		String sql = "SELECT" + "    DISTINCT(s.subject_name) AS subjectName," + "    CASE"
				+ "        WHEN s.seq IN (SELECT courseSubjectSeq FROM tblsilgiexam GROUP BY coursesubjectseq HAVING COUNT(*) = 10) THEN '완료'"
				+ "        ELSE '미완료'" + "    END AS examRegistry," + "    CASE\r\n"
				+ "        WHEN (SELECT COUNT(*) FROM tbllearning WHERE courseseq = ?) * 10 = (SELECT COUNT(*) FROM tblsilgianswer WHERE courseSubjectSeq = s.seq) THEN '완료'"
				+ "        ELSE '미완료'" + "    END AS gradeRegistry" + " FROM tblsubject s"
				+ "    WHERE s.seq IN (SELECT subjectseq FROM tblcourseSubject WHERE courseseq = ?)";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, cseq);
			pstat.setInt(2, cseq);

			ResultSet rs = pstat.executeQuery();

			ArrayList<SilgiRegiDTO> list = new ArrayList<SilgiRegiDTO>();

			while (rs.next()) {

				SilgiRegiDTO sDto = new SilgiRegiDTO();

				sDto.setSubjectName(rs.getString("subjectName"));
				sDto.setExamRegistry(rs.getString("examRegistry"));
				sDto.setGradeRegistry(rs.getString("gradeRegistry"));

				list.add(sDto);
			}

			rs.close();

			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}
	

	/**
	 * 관리자 - 필기 등록 여부 조회 메소드
	 * @param cseq : 입력받은 과정번호
	 * @return
	 */
	public ArrayList<PilgiRegiDTO> pRegi(int cseq) {

		String sql = "SELECT " + "    DISTINCT(s.subject_name) AS subjectName," + "    CASE"
				+ "        WHEN s.seq IN (SELECT courseSubjectSeq FROM tblpilgiexam GROUP BY coursesubjectseq HAVING COUNT(*) =20) THEN '완료'"
				+ "        ELSE '미완료'" + "    END AS examRegistry," + "    CASE"
				+ "        WHEN (SELECT COUNT(*) FROM tbllearning WHERE courseseq = ?) * 20 = (SELECT COUNT(*) FROM tblpilgianswer WHERE courseSubjectSeq = s.seq) THEN '완료'"
				+ "        ELSE '미완료'" + "    END AS gradeRegistry" + " FROM tblsubject s"
				+ "    WHERE s.seq IN (SELECT subjectseq FROM tblcourseSubject WHERE courseseq = ?)";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, cseq);
			pstat.setInt(2, cseq);

			ResultSet rs = pstat.executeQuery();

			ArrayList<PilgiRegiDTO> list = new ArrayList<PilgiRegiDTO>();

			while (rs.next()) {

				PilgiRegiDTO pDto = new PilgiRegiDTO();

				pDto.setSubjectName(rs.getString("subjectName"));
				pDto.setExamRegistry(rs.getString("examRegistry"));
				pDto.setGradeRegistry(rs.getString("gradeRegistry"));

				list.add(pDto);

			}

			rs.close();

			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}
	
	
	/**
	 * 관리자 - 교육생 정보 등록 메소드
	 * @param dto : 입력받은 교육생 객체 변수
	 * @return
	 */
	public int sInput(StudentDTO dto) {

		String sql = "insert into tblStudent(seq, name, ssn, tel, address, regdate, delete_status)"
				+ " values(student_seq.nextval, ?, ?, ?, ?, default, '회원') ";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getSsn());
			pstat.setString(3, dto.getTel());
			pstat.setString(4, dto.getAddress());

			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println("AdminDAO.sInput() : " + e.toString());
		}

		return 0;

	}
	
	
	/**
	 * 관리자 - 교육생 정보 조회 메소드
	 * @return
	 */
	public ArrayList<StudentDTO> slist() {

		try {

			String sql = "SELECT seq, name, ssn, tel, address, TO_CHAR(regdate, 'yyyy-mm-dd') AS regdate, delete_status FROM tblStudent ORDER BY seq";

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();

			while (rs.next()) {

				StudentDTO sDto = new StudentDTO();

				sDto.setSeq(rs.getString("seq"));
				sDto.setName(rs.getString("name"));
				sDto.setSsn(rs.getString("ssn"));
				sDto.setTel(rs.getString("tel"));
				sDto.setAddress(rs.getString("address"));
				sDto.setRegdate(rs.getString("regdate"));
				sDto.setDelete_status(rs.getString("delete_status"));

				list.add(sDto);
			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;

	}
	
	
	/**
	 * 관리자 - 교육생 정보 수정 전, 조회 메소드
	 * @return
	 */
	public ArrayList<StudentDTO> sElist() {

		try {

			String sql = "SELECT seq, name, ssn, tel, rpad(address, 50, ' ') as address, delete_status FROM tblStudent ORDER BY seq";

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();

			while (rs.next()) {

				StudentDTO sDto = new StudentDTO();

				sDto.setSeq(rs.getString("seq"));
				sDto.setName(rs.getString("name"));
				sDto.setSsn(rs.getString("ssn"));
				sDto.setTel(rs.getString("tel"));
				sDto.setAddress(rs.getString("address"));
				sDto.setDelete_status(rs.getString("delete_status"));

				list.add(sDto);

			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println("AdminDAO.sElist() : " + e.toString());
		}

		return null;
	}
	
	/**
	 * 관리자 - 교육생 정보 수정 메소드
	 * @param dto : 수정할 교육생 객체 변수
	 * @return
	 */
	public int sEdit(StudentDTO dto) {

		try {

			String sql = "UPDATE tblstudent SET tel = ?, address = ?, delete_status = ?  where SEQ = ? ";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getTel());
			pstat.setString(2, dto.getAddress());
			pstat.setString(3, dto.getDelete_status());
			pstat.setString(4, dto.getSeq());

			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return 0;
	}
	
	
	/**
	 * 관리자 - 수강중인 교육생 상태 수정 메소드
	 * @param dto : 수정할 교육생번호 객체 변수
	 * @return
	 */
	public int lEdit(LearningDTO dto) {

		try {

			String sql = "UPDATE tbllearning SET ing = '중도탈락', ingdate = TO_CHAR(SYSDATE, 'yyyy-mm-dd') WHERE studentseq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSeq());

			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return 0;
	}
	
	
	
	/**
	 * 관리자 - 특정 교육생 정보 조회 메소드
	 * @param seq : 입력받은 교육생번호
	 * @return
	 */
	public StudentDTO sGet(String seq) {
		String sql = "SELECT * FROM tblStudent WHERE seq = ?";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			ResultSet rs = pstat.executeQuery();

			StudentDTO sDto = new StudentDTO();

			if (rs.next()) {
				sDto.setSeq(rs.getString("seq"));
				sDto.setName(rs.getString("name"));
				sDto.setSsn(rs.getString("ssn"));
				sDto.setTel(rs.getString("tel"));
				sDto.setAddress(rs.getString("address"));
				sDto.setDelete_status(rs.getString("delete_status"));

				return sDto;

			}

		} catch (SQLException e) {
			System.out.print("AdminDAO.get : ");
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	/**
	 * 관리자 - 수강중인 교육생 정보 조회 메소드
	 * @param seq : 입력받은 교육생번호
	 * @return
	 */
	public LearningDTO lGet(String seq) {
		String sql = "SELECT * FROM tbllearning WHERE studentseq = ?";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			ResultSet rs = pstat.executeQuery();

			LearningDTO sDto = new LearningDTO();

			if (rs.next()) {
				sDto.setSeq(rs.getString("seq"));
				sDto.setCourseseq(rs.getString("courseSeq"));
				sDto.setStudentseq(rs.getString("studentseq"));
				sDto.setIng(rs.getString("ing"));
				sDto.setIngdate(rs.getString("ingdate"));

				return sDto;

			}

		} catch (SQLException e) {
			System.out.print("AdminDAO.get : ");
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	/**
	 * 관리자 - 로그인 검사 메소드
	 * @return
	 */
	public ArrayList<AdminDTO> adminList() {
		try {
			String sql = "SELECT * FROM tblAdmin ";

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<AdminDTO> adminList = new ArrayList<AdminDTO>();

			while (rs.next()) {
				AdminDTO dto = new AdminDTO();

				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setSsn(rs.getString("ssn"));
				dto.setTel(rs.getString("tel"));
				dto.setAddress(rs.getString("address"));
				dto.setRegdate(rs.getString("regdate"));

				adminList.add(dto);
			}

			rs.close();
			return adminList;

		} catch (Exception e) {
			System.out.println("AdminDAO의 adminList() : " + e.toString());
		}

		return null;

	}

	
	/**
	 * 관리자 - 과목별 성적 조회 메소드
	 * @param seq : 입력받은 과목번호
	 * @return
	 */
	public ArrayList<GradeSubjectDTO> gradeSubject(String seq) {

		String sql = "SELECT teacherName, rpad(openCourseName, 60, ' ') AS openCourseName, openCoursePeriod, roomName, subjectName, rpad(bookName,30, ' ') AS bookName, studentName, backSsn, pilgi, silgi FROM vwsubjectlist WHERE subjectName IN (SELECT subject_name FROM tblSubject WHERE seq = ? )";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			ResultSet rs = pstat.executeQuery();

			ArrayList<GradeSubjectDTO> list = new ArrayList<GradeSubjectDTO>();

			while (rs.next()) {

				GradeSubjectDTO gDto = new GradeSubjectDTO();

				gDto.setTeacherName(rs.getString("teacherName"));
				gDto.setOpenCourseName(rs.getString("openCourseName"));
				gDto.setOpenCoursePeriod(rs.getString("openCoursePeriod"));
				gDto.setRoomName(rs.getString("roomName"));
				gDto.setSubjectName(rs.getString("subjectName"));
				gDto.setBookName(rs.getString("bookName"));
				gDto.setStudentName(rs.getString("studentName"));
				gDto.setBackSsn(rs.getString("backSsn"));
				gDto.setPilgi(rs.getString("pilgi"));
				gDto.setSilgi(rs.getString("silgi"));

				list.add(gDto);
			}

			rs.close();

			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}
	
	
	
	/**
	 * 관리자 - 교육생별 성적조회 
	 * @param seq : 입력받은 교육생번호
	 * @return
	 */
	public ArrayList<GradeStudentDTO> gradeStudent(String seq) {

		String sql = "SELECT * FROM vwstudentlist WHERE studentName IN (SELECT name FROM tblStudent WHERE seq = ?) ORDER BY openSubjectPeriod";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			ResultSet rs = pstat.executeQuery();

			ArrayList<GradeStudentDTO> list = new ArrayList<GradeStudentDTO>();

			while (rs.next()) {

				GradeStudentDTO gsDto = new GradeStudentDTO();

				gsDto.setStudentName(rs.getString("studentName"));
				gsDto.setBackSsn(rs.getString("backSsn"));
				gsDto.setOpenCourseName(rs.getString("openCourseName"));
				gsDto.setOpenCoursePeriod(rs.getString("openCoursePeriod"));
				gsDto.setRoomName(rs.getString("roomName"));
				gsDto.setTeacherName(rs.getString("teacherName"));
				gsDto.setOpenSubjectName(rs.getString("openSubjectName"));
				gsDto.setOpenSubjectPeriod(rs.getString("openSubjectPeriod"));
				gsDto.setPilgi(rs.getString("pilgi"));
				gsDto.setSilgi(rs.getString("silgi"));

				list.add(gsDto);

			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	
	/**
	 * 관리자 - 강사 정보 등록 메소드
	 * @param tdto : 강사 객체 변수
	 * @return
	 */
	public int add(TeacherDTO tdto) {
		String sql = "insert into tblTeacher (seq, name, ssn, tel, address, regdate, delete_status) values (teacher_seq.nextval, ?, ?, ?, ?, default, '회원')";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, tdto.getName());
			pstat.setString(2, tdto.getSsn());
			pstat.setString(3, tdto.getTel());
			pstat.setString(4, tdto.getAddress());

			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println("AdminDAO.Constructor");
			e.printStackTrace();
		}

		return 0;
	}

	
	/**
	 * 관리자 - 강사 정보 수정 메소드
	 * @param seq : 입력받은 강사번호
	 * @return
	 */
	public int del(String seq) {
		try {
			System.out.println(seq);
			String sql = "UPDATE tblteacher SET delete_status = '탈퇴' WHERE seq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return 0;
	}
	
	
	
	/**
	 * 관리자 - 강사 정보 조회 메소드
	 * @return
	 */
	public ArrayList<TeacherDTO> tlist() {

		try {
			String sql = "SELECT seq,name,ssn,tel, TO_CHAR(regdate, 'yyyy-mm-dd') AS regdate, address, delete_status FROM tblteacher";

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<TeacherDTO> list = new ArrayList<TeacherDTO>();

			while (rs.next()) {

				TeacherDTO dto = new TeacherDTO();

				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setSsn(rs.getString("ssn"));
				dto.setTel(rs.getString("tel"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setAddress(rs.getString("address"));
				dto.setDelete_status(rs.getString("delete_status"));

				list.add(dto);
			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println(e.toString());

		}

		return null;
	}
	

	/**
	 * 관리자 - 강사 정보 수정 메소드
	 * @param dto : 강사 객체 변수
	 * @return
	 */
	public int edit(TeacherDTO dto) {
		try {
			String sql = "UPDATE tblteacher SET name = ?, tel = ?, address = ? where seq = ?";
			pstat = conn.prepareStatement(sql);

			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getTel());
			pstat.setString(3, dto.getAddress());
			pstat.setString(4, dto.getSeq());

			return pstat.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return 0;
	}

	
	
	/**
	 * 관리자 - 특정강사 정보 조회 메소드
	 * @param seq
	 * @return
	 */
	public TeacherDTO get(String seq) {
		try {
			String sql = "SELECT * FROM tblTeacher WHERE seq = ?";
			TeacherDTO dto = new TeacherDTO();
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			ResultSet rs = pstat.executeQuery();

			if (rs.next()) {

				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setAddress(rs.getString("address"));
			}

			rs.close();
			return dto;

		} catch (Exception e) {
			System.out.println("AdminDAO get() : " + e.toString());
		}
		return null;
	}
	
	
	/**
	 * 관리자 - 과정별 출결조회
	 * @param seq : 입력받은 과정번호
	 * @return
	 */
	public ArrayList<AdminAttendSubjectDTO> attendCourse(String seq) {

		String sql = "SELECT " + "    s.name AS name," + "    a.status AS attendStatus,"
				+ "    TO_CHAR(a.intime,'yyyy-mm-dd/hh24:mi') || '~' || TO_CHAR(a.outtime, 'hh24:mi') AS attendTime,"
				+ "    ct.seq AS courseSeq," + "    ct.title AS courseName,"
				+ "    TO_CHAR(c.begindate, 'yyyy-mm-dd') || '~' || TO_CHAR(c.enddate, 'yyyy-mm-dd') AS coursePeriod"
				+ " FROM tblstudent s" + "    INNER JOIN tbllearning l" + "        ON s.seq = l.studentseq"
				+ "            INNER JOIN tblattended a" + "                ON a.learningseq = l.seq"
				+ "                    INNER JOIN tblcourse c" + "                        ON c.seq = l.courseseq"
				+ "                            INNER JOIN tblcoursetitle ct"
				+ "                                ON ct.seq = c.coursetitleseq"
				+ "                                    WHERE c.seq = ?";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			ResultSet rs = pstat.executeQuery();

			ArrayList<AdminAttendSubjectDTO> list = new ArrayList<AdminAttendSubjectDTO>();

			while (rs.next()) {

				AdminAttendSubjectDTO dto = new AdminAttendSubjectDTO();

				dto.setName(rs.getString("name"));
				dto.setAttendStatus(rs.getString("attendStatus"));
				dto.setAttendTime(rs.getString("attendTime"));
				dto.setCourseSeq(rs.getString("courseSeq"));
				dto.setCourseName(rs.getString("courseName"));
				dto.setCoursePeriod(rs.getString("coursePeriod"));

				list.add(dto);
			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}
	
	
	
	/**
	 * 관리자 - 전체 출결 조회 메소드
	 * @return
	 */
	public ArrayList<AdminStudentAttendedDTO> attendWhole() {

		String sql = "SELECT * FROM vwAttendWhole";

		try {
			ResultSet rs = stat.executeQuery(sql);

			ArrayList<AdminStudentAttendedDTO> list = new ArrayList<AdminStudentAttendedDTO>();

			while (rs.next()) {
				AdminStudentAttendedDTO dto = new AdminStudentAttendedDTO();

				dto.setName(rs.getString("studentName"));
				dto.setTitle(rs.getString("courseName"));
				dto.setIntime(rs.getString("intime"));
				dto.setOuttime(rs.getString("outtime"));
				dto.setStatus(rs.getString("attendStatus"));

				list.add(dto);
			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println("AdminDAO whole() : " + e.toString());
		}
		return null;
	}
	
	
	/**
	 * 관리자 - 특정 교육생 출결 조회 메소드
	 * @param name : 입력받은 교육생 이름
	 * @return
	 */
	public ArrayList<AdminStudentAttendedDTO> attendSearch(String name) {

		String sql = "select " + "    s.name as studentName, " + "    ct.title as courseName, "
				+ "    to_char(a.intime, 'yyyy-mm-dd/hh24:mi') as intime ,"
				+ "    to_char(a.outtime, 'yyyy-mm-dd/hh24:mi') as outtime," + "    a.status as attendStatus"
				+ " from tblStudent s" + "    inner join tbllearning l" + "        on l.studentseq = s.seq"
				+ "            inner join tblAttended a" + "                on l.seq = a.learningseq"
				+ "                    inner join tblCourse c" + "                        on l.courseseq = c.seq"
				+ "                            inner join tblCoursetitle ct"
				+ "                                on  c.coursetitleseq = ct.seq"
				+ "                                    where s.name = ?";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, name);

			ResultSet rs = pstat.executeQuery();

			ArrayList<AdminStudentAttendedDTO> list = new ArrayList<AdminStudentAttendedDTO>();

			while (rs.next()) {
				AdminStudentAttendedDTO dto = new AdminStudentAttendedDTO();

				dto.setName(rs.getString("studentName"));
				dto.setTitle(rs.getString("courseName"));
				dto.setIntime(rs.getString("intime"));
				dto.setOuttime(rs.getString("outtime"));
				dto.setStatus(rs.getString("attendStatus"));

				list.add(dto);

			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println("AdminDAO search : " + e.toString());
		}

		return null;
	}
	
	
	/**
	 * 관리자 - 전체 교육생 일별 출결 조회 메소드
	 * @param day : 입력받은 날짜
	 * @return
	 */
	public ArrayList<AdminStudentAttendedDTO> day(String day) {

		String sql = "SELECT" + "    TO_CHAR(a.intime,'yyyy-mm-dd') as 출근날짜," + "    CASE"
				+ "        WHEN TO_CHAR(a.intime,'hh24:mi') LIKE '00:00' THEN '없음'"
				+ "        ELSE TO_CHAR(a.intime,'hh24:mi')" + "    END AS 출근시간," + "    CASE"
				+ "        WHEN TO_CHAR(a.outtime,'hh24:mi') LIKE '00:00' THEN '없음'"
				+ "        ELSE TO_CHAR(a.outtime,'hh24:mi')" + "    END AS 퇴근시간,"
				+ "    st.name AS 이름, a.status AS 상태, ct.title AS 과정명" + "  FROM tblstudent st"
				+ "  INNER JOIN tbllearning l" + " ON l.studentseq = st.seq" + " INNER JOIN tblattended a"
				+ " ON a.learningseq = l.seq" + " INNER JOIN tblcourse c" + " ON c.seq = l.courseseq"
				+ " INNER JOIN tblcoursetitle ct" + " ON ct.seq = c.coursetitleseq"
				+ " WHERE TO_CHAR(a.intime,'yyyy-mm-dd') = ?" + " ORDER BY 출근날짜";

		try {

			ArrayList<AdminStudentAttendedDTO> list = new ArrayList<AdminStudentAttendedDTO>();

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, day);

			ResultSet rs = pstat.executeQuery();

			while (rs.next()) {
				AdminStudentAttendedDTO dto = new AdminStudentAttendedDTO();
				dto.setDay(rs.getString("출근날짜"));
				dto.setIntime(rs.getString("출근시간"));
				dto.setOuttime(rs.getString("퇴근시간"));
				dto.setName(rs.getString("이름"));
				dto.setStatus(rs.getString("상태"));
				dto.setTitle(rs.getString("과정명"));

				list.add(dto);

			}
			return list;

		} catch (Exception e) {
			System.out.println("dao : " + e.toString());
		}

		return null;
	}
	
	
	/**
	 * 관리자 - 전체 교육생 월별 출결 조회 메소드
	 * @param month : 입력받은 월
	 * @return
	 */
	public ArrayList<AdminStudentAttendedDTO> month(String month) {

		String sql = "SELECT" + "    TO_CHAR(a.intime,'yyyy-mm-dd') 출근날짜," + "    CASE"
				+ "        WHEN TO_CHAR(a.intime,'hh24:mi') LIKE '00:00' THEN '없음'"
				+ "        ELSE TO_CHAR(a.intime,'hh24:mi')" + "    END AS 출근시간," + "    CASE"
				+ "        WHEN TO_CHAR(a.outtime,'hh24:mi') LIKE '00:00' THEN '없음'"
				+ "        ELSE TO_CHAR(a.outtime,'hh24:mi')" + "    END AS 퇴근시간,"
				+ "    st.name AS 이름, a.status AS 상태, ct.title AS 과정명" + " FROM tblstudent st"
				+ " INNER JOIN tbllearning l" + " ON l.studentseq = st.seq" + " INNER JOIN tblattended a"
				+ " ON a.learningseq = l.seq" + " INNER JOIN tblcourse c" + " ON c.seq = l.courseseq"
				+ " INNER JOIN tblcoursetitle ct" + " ON ct.seq = c.coursetitleseq"
				+ " WHERE TO_CHAR(a.intime,'yyyy-mm') = ? " + " ORDER BY 출근날짜";

		try {

			ArrayList<AdminStudentAttendedDTO> list = new ArrayList<AdminStudentAttendedDTO>();

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, month);

			ResultSet rs = pstat.executeQuery();

			while (rs.next()) {
				AdminStudentAttendedDTO dto = new AdminStudentAttendedDTO();
				dto.setDay(rs.getString("출근날짜"));
				dto.setIntime(rs.getString("출근시간"));
				dto.setOuttime(rs.getString("퇴근시간"));
				dto.setName(rs.getString("이름"));
				dto.setStatus(rs.getString("상태"));
				dto.setTitle(rs.getString("과정명"));

				list.add(dto);

			}
			return list;

		} catch (Exception e) {
			System.out.println("dao : " + e.toString());
		}

		return null;
	}


	/**
	 * 관리자 - 전체 교육생 년도별 출결 조회 메소드
	 * @param year : 입력받은 년도
	 * @return
	 */
	public ArrayList<AdminStudentAttendedDTO> year(String year) {

		String sql = "SELECT" + "    TO_CHAR(a.intime,'yyyy-mm-dd') 출근날짜," + "    CASE"
				+ "        WHEN TO_CHAR(a.intime,'hh24:mi') LIKE '00:00' THEN '없음'"
				+ "        ELSE TO_CHAR(a.intime,'hh24:mi')" + "    END AS 출근시간," + "    CASE"
				+ "        WHEN TO_CHAR(a.outtime,'hh24:mi') LIKE '00:00' THEN '없음'"
				+ "        ELSE TO_CHAR(a.outtime,'hh24:mi')" + "    END AS 퇴근시간,"
				+ "    st.name AS 이름, a.status AS 상태, ct.title AS 과정명" + " FROM tblstudent st"
				+ " INNER JOIN tbllearning l" + " ON l.studentseq = st.seq" + " INNER JOIN tblattended a"
				+ " ON a.learningseq = l.seq" + " INNER JOIN tblcourse c" + " ON c.seq = l.courseseq"
				+ " INNER JOIN tblcoursetitle ct" + " ON ct.seq = c.coursetitleseq"
				+ " WHERE TO_CHAR(a.intime,'yyyy') = ?" + " ORDER BY 출근날짜";

		try {

			ArrayList<AdminStudentAttendedDTO> list = new ArrayList<AdminStudentAttendedDTO>();

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, year);

			ResultSet rs = pstat.executeQuery();

			while (rs.next()) {
				AdminStudentAttendedDTO dto = new AdminStudentAttendedDTO();
				dto.setDay(rs.getString("출근날짜"));
				dto.setIntime(rs.getString("출근시간"));
				dto.setOuttime(rs.getString("퇴근시간"));
				dto.setName(rs.getString("이름"));
				dto.setStatus(rs.getString("상태"));
				dto.setTitle(rs.getString("과정명"));

				list.add(dto);

			}
			return list;

		} catch (Exception e) {
			System.out.println("dao : " + e.toString());
		}

		return null;
	}
	

	/**
	 * 관리자 - 교육생 출결 관리
	 * @param name : 입력받은 교육생 이름
	 * @param day : 입력받은 날짜
	 * @param status : 입력받은 출결 상태
	 */
	public void management(String name, String day, String status) {

		String st = "";
		if (status.equals("1")) {
			st = "출근";
		} else if (status.equals("2")) {
			st = "지각";
		} else if (status.equals("3")) {
			st = "조퇴";
		} else if (status.equals("4")) {
			st = "결석";
		} else if (status.equals("5")) {
			st = "병가";
		} else if (status.equals("6")) {
			st = "기타";
		}

		try {

			String sql = "UPDATE tblattended SET status = ?"
					+ "    WHERE learningseq = (SELECT l.seq FROM tblstudent s " + "        INNER JOIN tbllearning l "
					+ "        ON s.seq = l.studentseq " + "        WHERE s.name = ?" + "        )"
					+ "    AND TO_CHAR(intime,'yyyy-mm-dd') = ?";

			pstat = conn.prepareStatement(sql);

			pstat.setString(1, st);
			pstat.setString(2, name);
			pstat.setString(3, day);

			int result = pstat.executeUpdate();
			if (result == 1) {
				System.out.println("\t\t\t\t\t\t\t\t\t[업데이트에 성공했습니다.]");
			} else if (result == 0) {
				System.out.println("\t\t\t\t\t\t\t\t\t[업데이트에 실패했습니다.]");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	
	
	/**
	 * 관리자 - 빈 강의실 조회 메소드
	 * @return
	 */
	public ArrayList<AdminEmptyRoomDTO> getEmptyRoom() {

		try {

			String sql = "select * from vwEmptyRoom";

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<AdminEmptyRoomDTO> list = new ArrayList<AdminEmptyRoomDTO>();

			while (rs.next()) {
				AdminEmptyRoomDTO erdto = new AdminEmptyRoomDTO();

				erdto.setSeq(rs.getString("강의실번호"));
				erdto.setName(rs.getString("강의실명"));
				erdto.setLimit(rs.getInt("강의실정원"));

				list.add(erdto);
			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	
	/**
	 * 관리자 - 입력받은 과정명 등록 메소드
	 * @param ctdto : 과정명 객체 변수
	 * @return
	 */
	public int addCourseTitle(CourseTitleDTO ctdto) {

		String sql = "insert into tblcoursetitle (seq, title) values(courseTitle_seq.nextval, ?)";

		try {

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, ctdto.getTitle());

			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return 0;

	}


	/**
	 * 관리자 - 등록한 과정 번호 반환 메소드
	 * @param title : 등록한 과정명
	 * @return
	 */
	public String getCourseSeq(String title) {

		String sql = "select seq from tblCourseTitle where title = ?";
		try {

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, title);

			ResultSet rs = pstat.executeQuery();

			rs.next();
			String result = rs.getString("seq");

			rs.close();

			return result;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;

	}



	/**
	 * 관리자 - 등록 가능한 과목 조회 메소드
	 * @return
	 */
	public ArrayList<SubjectDTO> getSubject() {

		String sql = "SELECT * FROM tblsubject where delete_status = '가능'";

		try {

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<SubjectDTO> list = new ArrayList<SubjectDTO>();

			while (rs.next()) {
				SubjectDTO sdto = new SubjectDTO();

				sdto.setSeq(rs.getString("seq"));
				sdto.setSubject_name(rs.getString("subject_name"));

				list.add(sdto);
			}

			rs.close();

			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}



	/**
	 * 관리자 - 특정 과목 가능 강사 조회 메소드
	 * @param subjectseq : 입력받은 과목번호
	 * @return
	 */
	public ArrayList<AdminTeacherInfoDTO> getTeacherList(String subjectseq) {
		String sql = "SELECT " + "    seq as teacherseq," + "    name AS teachername,"
				+ "    TO_CHAR(SYSDATE, 'yyyy') - ('19' || substr(ssn, 1, 2)) + 1 AS age,"
				+ "    TO_CHAR(regdate, 'yyyy-mm-dd') AS regdate" + " FROM tblteacher "
				+ " WHERE seq in (SELECT teacherseq FROM tblteachersubject WHERE subjectseq = ?) AND delete_status = '회원'";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, subjectseq);

			ResultSet rs = pstat.executeQuery();

			ArrayList<AdminTeacherInfoDTO> list = new ArrayList<AdminTeacherInfoDTO>();

			while (rs.next()) {
				AdminTeacherInfoDTO atdto = new AdminTeacherInfoDTO();

				atdto.setSeq(rs.getString("teacherseq"));
				atdto.setName(rs.getString("teachername"));
				atdto.setAge(rs.getString("age"));
				atdto.setRegdate(rs.getString("regdate"));

				list.add(atdto);
			}

			rs.close();

			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}



	/**
	 * 관리자 - 특정 과목 교재 조회 메소드
	 * @param subjectseq : 입력받은 과목번호
	 * @return
	 */
	public ArrayList<BookDTO> getBook(String subjectseq) {

		String sql = "SELECT * FROM tblbook WHERE subjectseq = ?";

		try {

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, subjectseq);

			ResultSet rs = pstat.executeQuery();

			ArrayList<BookDTO> blist = new ArrayList<BookDTO>();

			while (rs.next()) {
				BookDTO bdto = new BookDTO();

				bdto.setSeq(rs.getString("seq"));
				bdto.setTitle(rs.getString("title"));
				bdto.setPublisher(rs.getString("publisher"));

				blist.add(bdto);
			}

			rs.close();

			return blist;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;

	}



	/**
	 * 관리자 - 과정 등록 메소드
	 * @param cdto : 과정 객체 변수
	 * @return
	 */
	public int registCourse(CourseDTO cdto) {

		String sql = "insert into tblCourse(seq, coursetitleseq, roomseq, status, begindate, enddate, etc)"
				+ "    values(course_seq.nextval, ?, ?, '진행중', ?, ?, ?)";

		try {

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cdto.getCoursetitleseq());
			pstat.setString(2, cdto.getRoomseq());
			pstat.setString(3, cdto.getBegindate());
			pstat.setString(4, cdto.getEnddate());
			pstat.setString(5, cdto.getEtc());

			return pstat.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}



	/**
	 * 관리자 - 과정과목 등록 메소드
	 * @param csdto : 과정과목 객체 변수
	 * @return
	 */
	public int registCourseSubject(CourseSubjectDTO csdto) {

		String sql = "insert into tblCourseSubject(seq, courseseq, subjectseq, bookseq, teacherseq, begindate, enddate, etc)"
				+ "    values(courseSubject_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";

		try {

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, csdto.getCourseseq());
			pstat.setString(2, csdto.getSubjectseq());
			pstat.setString(3, csdto.getBookseq());
			pstat.setString(4, csdto.getTeacherseq());
			pstat.setString(5, csdto.getBegindate());
			pstat.setString(6, csdto.getEnddate());
			pstat.setString(7, csdto.getEtc());

			return pstat.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;

	}



	/**
	 * 관리자 - 등록된 과정 번호 반환 메소드
	 * @return
	 */
	public String getNewCourseSeq() {

		String sql = "select seq from " + "    (select * from tblCourse " + "            order by seq desc)"
				+ "                where rownum = 1";

		try {

			pstat = conn.prepareStatement(sql);

			ResultSet rs = pstat.executeQuery();

			rs.next();

			String result = rs.getString("seq");

			rs.close();

			return result;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}



	/**
	 * 관리자 - 전체 과정 정보 조회 메소드
	 * @return
	 */
	public ArrayList<AdminCourseInfoDTO> courseInfoList() {

		try {

			String sql = "select courseSeq, rpad(courseTitle, 50, ' ') AS courseTitle, TO_CHAR(begindate, 'yyyy-mm-dd') AS begindate, TO_CHAR(enddate, 'yyyy-mm-dd') AS enddate,roomName,status,etc from vwCourseInfo";

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<AdminCourseInfoDTO> list = new ArrayList<AdminCourseInfoDTO>();

			while (rs.next()) {

				AdminCourseInfoDTO dto = new AdminCourseInfoDTO();

				dto.setCourseSeq(rs.getString("courseSeq"));
				dto.setCourseTitle(rs.getString("courseTitle"));
				dto.setBegindate(rs.getString("begindate"));
				dto.setEnddate(rs.getString("enddate"));
				dto.setRoomName(rs.getString("roomName"));
				dto.setStatus(rs.getString("status"));
				dto.setEtc(rs.getString("etc"));

				list.add(dto);
			}

			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	/**
	 * 관리자 - 특정 과정 정보 조회 메소드
	 * @param courseseq : 입력받은 과정번호 
	 * @return
	 */
	public AdminCourseInfoDTO courseInfo(String courseseq) {
		try {

			String sql = "select courseSeq, rpad(courseTitle, 50, ' ') AS courseTitle, TO_CHAR(begindate, 'yyyy-mm-dd') AS begindate, TO_CHAR(enddate, 'yyyy-mm-dd') AS enddate,roomName,status,etc from vwCourseInfo where courseseq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, courseseq);

			ResultSet rs = pstat.executeQuery();

			AdminCourseInfoDTO dto = new AdminCourseInfoDTO();

			if (rs.next()) {

				dto.setCourseSeq(rs.getString("courseSeq"));
				dto.setCourseTitle(rs.getString("courseTitle"));
				dto.setBegindate(rs.getString("begindate"));
				dto.setEnddate(rs.getString("enddate"));
				dto.setRoomName(rs.getString("roomName"));
				dto.setStatus(rs.getString("status"));
				dto.setEtc(rs.getString("etc"));

				return dto;
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}



	/**
	 * 관리자 - 과정 정보 수정 메소드
	 * @param acdto : 과정 객체 변수
	 * @return
	 */
	public int editCourseInfo(AdminCourseInfoDTO acdto) {
		try {

			String sql = "UPDATE tblcourse SET status = ?, etc = ? WHERE seq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, acdto.getStatus());
			pstat.setString(2, acdto.getEtc());
			pstat.setString(3, acdto.getCourseSeq());

			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return 0;
	}



	/**
	 * 관리자 - 특정 과정의 교육생 정보 조회 메소드
	 * @param courseseq : 입력받은 과정번호
	 * @return
	 */
	public ArrayList<StudentDTO> studentInfoList(String courseseq) {/* 예찬 */

		String sql = "SELECT s.* FROM tblstudent s" + "    INNER JOIN tbllearning l" + "        ON s.seq = l.studentseq"
				+ "            WHERE l.courseseq = ?";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, courseseq);

			ResultSet rs = pstat.executeQuery();

			ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();

			while (rs.next()) {

				StudentDTO dto = new StudentDTO();

				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setSsn(rs.getString("ssn"));
				dto.setTel(rs.getString("tel"));
				dto.setAddress(rs.getString("address"));

				list.add(dto);
			}

			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	
	/**
	 * 관리자 - 특정 교육생 조회 메소드
	 * @param name : 입력받은 교육생 이름
	 * @return
	 */
	public ArrayList<AdminStudentSearchDTO> StudentSearch(String name) {

		String sql = "select " + "    st.name as name," + "    st.ssn as ssn, " + "    st.tel as tel,"
				+ "    rpad(st.address, 30, ' ') as address,    " + "    rpad(ct.title, 40, ' ') as learningCourse,"
				+ "    co.begindate || '~' || co.enddate as coursePeriod," + "    ro.name as roomName,"
				+ "    le.ing AS ing," + "    le.ingdate ingdate," + "    st.delete_status status"
				+ " from tblStudent st" + "    inner join tbllearning le" + "     on st.seq = le.studentseq"
				+ "      inner join tblcourse co" + "        on co.seq = le.courseseq"
				+ "            inner join tblCourseTitle ct" + "                on ct.seq = le.courseseq"
				+ "                    inner join tblroom ro" + "                        on ro.seq = co.seq"
				+ "                            where st.name = ?";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, name);

			ResultSet rs = pstat.executeQuery();

			ArrayList<AdminStudentSearchDTO> list = new ArrayList<AdminStudentSearchDTO>();

			while (rs.next()) {
				AdminStudentSearchDTO dto = new AdminStudentSearchDTO();

				dto.setName(rs.getString("name"));
				dto.setSsn(rs.getString("ssn"));
				dto.setTel(rs.getString("tel"));
				dto.setAddress(rs.getString("address"));
				dto.setLearningCourse(rs.getString("learningCourse"));
				dto.setCoursePeriod(rs.getString("coursePeriod"));
				dto.setRoomName(rs.getString("roomName"));
				dto.setIng(rs.getString("ing"));
				dto.setIngdate(rs.getString("ingdate"));
				dto.setStatus(rs.getString("status"));

				list.add(dto);
			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}
	

	/**
	 * 관리자 - 수강중인 교육생 등록 메소드
	 * @param cSel : 입력받은 과정번호
	 * @return
	 */
	public int addLearning(String cSel) {

		String sql = "insert into tblLearning(seq, courseseq, studentseq, ing, ingdate) "
				+ "    values((SELECT MAX(seq) FROM tblLearning)+1, ?, (SELECT MAX(seq) FROM tblStudent), '진행중', default)";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cSel);

			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println("addLearning : " + e.toString());
		}

		return 0;
	}

	
	
	/**
	 * 관리자 - 진행중인 과정 정보 조회 메소드
	 * @return
	 */
	public ArrayList<AdminCourseInfoDTO> courseInfoList2() {

		try {

			String sql = "select courseSeq, rpad(courseTitle, 50, ' ') AS courseTitle, TO_CHAR(begindate, 'yyyy-mm-dd') AS begindate, TO_CHAR(enddate, 'yyyy-mm-dd') AS enddate,roomName,status from vwCourseInfo where status = '진행중'";

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<AdminCourseInfoDTO> list = new ArrayList<AdminCourseInfoDTO>();

			while (rs.next()) {

				AdminCourseInfoDTO dto = new AdminCourseInfoDTO();

				dto.setCourseSeq(rs.getString("courseSeq"));
				dto.setCourseTitle(rs.getString("courseTitle"));
				dto.setBegindate(rs.getString("begindate"));
				dto.setEnddate(rs.getString("enddate"));
				dto.setRoomName(rs.getString("roomName"));
				dto.setStatus(rs.getString("status"));

				list.add(dto);
			}

			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}


}//class DAO 





