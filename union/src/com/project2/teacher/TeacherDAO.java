package com.project2.teacher;

import com.project2.admin.SilgiRegiDTO;
import com.project2.main.DBUtil;
import com.project2.main.TeacherDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 강사 - 강사 DB연동 클래스
 * 
 * @author sist56
 *
 */
public class TeacherDAO {

	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private TeacherView tview;

	public TeacherDAO() {

		try {
			DBUtil util = new DBUtil();
			this.conn = util.connect(DBUtil.SERVERIP, DBUtil.SERVERID, DBUtil.SERVERPW);
			this.stat = conn.createStatement();
			tview = new TeacherView();
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("DAO.Constructor");
		}

	}

	public boolean isConnected() {

		try {
			return !this.conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void close() {

		try {
			this.conn.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	/**
	 * 
	 * @return 강사 번호|이름|주민번호|연락처|주소|입사날짜|상태를 반환
	 */
	public ArrayList<TeacherDTO> teacherList() {
		try {

			String sql = "SELECT * FROM tblTeacher WHERE delete_status = '회원'";

			ResultSet rs = stat.executeQuery(sql);

			ArrayList<TeacherDTO> teacherList = new ArrayList<TeacherDTO>();

			while (rs.next()) {

				TeacherDTO dto = new TeacherDTO();

				dto.setSeq(rs.getString("seq"));

				dto.setName(rs.getString("name"));
				dto.setSsn(rs.getString("ssn"));
				dto.setTel(rs.getString("tel"));
				dto.setAddress(rs.getString("address"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setDelete_status(rs.getString("delete_status"));

				teacherList.add(dto);
			}

			rs.close();

			return teacherList;

		} catch (Exception e) {
			System.out.println("TeacherDAO의 teacherList() : " + e.toString());
		}
		return null;

	}

	/**
	 * 
	 * @param 강사번호
	 * @return 강사담당 과정번호|과정명|과정기간|과정상태 반환
	 */
	public ArrayList<CourseDTO> teacher_courseList(TeacherDTO dto) {
		try {

			String sql = "select distinct tc.seq as seq,  tct.title as title,tc.begindate|| ' ~ '||tc.enddate as period ,rpad(tc.status, 10, ' ') as status from tblcoursesubject tcs inner join tblteacher tt on tcs.teacherseq = tt.seq inner join tblcourse tc  on tc.seq = tcs.courseseq              inner join tblcoursetitle tct on tct.seq = tc.coursetitleseq where tt.name = ? order by tc.begindate|| ' ~ '||tc.enddate";

			pstat = conn.prepareStatement(sql);

			String answer = dto.getName();

			pstat.setString(1, answer);

			ResultSet rs = pstat.executeQuery();

			ArrayList<CourseDTO> list = new ArrayList<CourseDTO>();

			while (rs.next()) {

				CourseDTO cDto = new CourseDTO();

				cDto.setCourseID(rs.getString("seq"));
				cDto.setCoursename(rs.getString("title"));
				cDto.setCoursedate(rs.getString("period"));
				cDto.setStatus(rs.getString("status"));

				list.add(cDto);
			}

			rs.close();
			return list;

		} catch (Exception e) {
			System.out.println("TeacherDAO의 teacher_courseList : " + e.toString());
		}

		return null;

	}

	/**
	 * 
	 * @param 선택된 과정 번호
	 * @param 강사  번호
	 * @return 선택된 과목번호|과목명|과목기간|과목설명 반환
	 */
	public ArrayList<SubjectListDTO> SubjectChoose(String n, TeacherDTO teacher) {

		String sql = "select subjectseq, (select subject_name from tblsubject where seq = cs.subjectseq) as subjectname, begindate || ' ~ ' || enddate as subjectperiod, etc as description  from tblCourseSubject cs where courseseq = ? and teacherseq = (select seq from tblTeacher where name = ?)";

		try {
			pstat = conn.prepareStatement(sql);

			System.out.println();

			pstat.setString(1, n);
			pstat.setString(2, teacher.getName());
			ResultSet rs = pstat.executeQuery();

			ArrayList<SubjectListDTO> slist = new ArrayList<SubjectListDTO>();

			while (rs.next()) {

				SubjectListDTO sDto = new SubjectListDTO();

				sDto.setSubjectseq(rs.getString("subjectseq"));
				sDto.setSubjectname(rs.getString("subjectname"));
				sDto.setSubjectperiod(rs.getString("subjectperiod"));
				sDto.setDescription(rs.getString("description"));

				slist.add(sDto);
			}

			rs.close();
			return slist;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;

	}

	/**
	 * 
	 * @param 과정번호
	 * @param 과목번호
	 * @return 교육생번호|교육생명|출석점수|필기점수|실기점수|총점수 반환
	 */
	public ArrayList<FinalScoreDTO> finalchoose(String sel, String subject) {

		String sql = "select      l.seq as seq,     s.name as name,      j.subject_name as subjectname,     (select \"출석점수\" from (SELECT learningseq,  (COUNT (case     when a.status = '출근' then 1 end ) + COUNT (CASE     WHEN a.status = '지각' then 1     when a.status = '외출' then 1     when a.status = '조퇴' then 1     when a.status = '병가' then 1     when a.status = '기타' then 1      END )*0.5) /(SELECT COUNT         (CASE             WHEN TO_CHAR(v.regdate, 'd') IN ('7', '1') THEN NULL             WHEN h.name is not null THEN NULL             ELSE 1         END) AS \"java교육기간의 평일수\" FROM (SELECT TO_DATE((select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd') + level -1 AS regdate FROM dual     CONNECT BY level <= (to_date((select enddate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd')- TO_DATE((select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd') + 1)) v     LEFT OUTER JOIN tblholiday h         ON v.regdate = h.regdate)*20 as \"출석점수\" FROM TBLATTENDED a where intime between (select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?) and (select enddate from tblcoursesubject where courseseq = ? and subjectseq = ?)  GROUP BY learningseq    order by learningseq) where learningseq = l.seq) as attendancescore,      (select count(*) from tblpilgianswer p         inner join tblpilgiexam e             on e.seq = p.pilgiexamseq                 where p.learningseq = l.seq and e.coursesubjectseq = cs.seq                     and p.answer = e.answer) * 2 as pilgi,     (select count(*) from tblsilgianswer p         inner join tblsilgiexam e             on e.seq = p.silgiexamseq                 where p.learningseq = l.seq and e.coursesubjectseq = cs.seq                     and p.answer = e.answer) * 4 as silgi,                               (select count(*) from tblpilgianswer p         inner join tblpilgiexam e             on e.seq = p.pilgiexamseq                 where p.learningseq = l.seq and e.coursesubjectseq = cs.seq                     and p.answer = e.answer) * 2 +     (select count(*) from tblsilgianswer p         inner join tblsilgiexam e             on e.seq = p.silgiexamseq                 where p.learningseq = l.seq and e.coursesubjectseq = cs.seq                     and p.answer = e.answer) * 4 +                     (select \"출석점수\" from (SELECT learningseq,  (COUNT (case     when a.status = '출근' then 1 end ) + COUNT (CASE     WHEN a.status = '지각' then 1     when a.status = '외출' then 1     when a.status = '조퇴' then 1     when a.status = '병가' then 1     when a.status = '기타' then 1      END )*0.5) /(SELECT COUNT         (CASE             WHEN TO_CHAR(v.regdate, 'd') IN ('7', '1') THEN NULL             WHEN h.name is not null THEN NULL             ELSE 1         END) AS \"java교육기간의 평일수\" FROM (SELECT TO_DATE((select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd') + level -1 AS regdate FROM dual     CONNECT BY level <= (to_date((select enddate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd')- TO_DATE((select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd') + 1)) v     LEFT OUTER JOIN tblholiday h         ON v.regdate = h.regdate)*20 as \"출석점수\" FROM TBLATTENDED a where intime between (select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?) and (select enddate from tblcoursesubject where courseseq = ? and subjectseq = ?)  GROUP BY learningseq    order by learningseq) where learningseq = l.seq)                                          as total from tblstudent s     inner join tbllearning l         on s.seq = l.studentseq             inner join tblCourse c                 on c.seq = l.courseseq                     inner join tblcoursesubject cs                         on c.seq = cs.courseseq                             inner join tblsubject j                                 on j.seq = cs.subjectseq                                              where c.seq = ? and cs.subjectseq = ?";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, sel);
			pstat.setString(2, subject);
			pstat.setString(3, sel);
			pstat.setString(4, subject);
			pstat.setString(5, sel);
			pstat.setString(6, subject);
			pstat.setString(7, sel);
			pstat.setString(8, subject);
			pstat.setString(9, sel);
			pstat.setString(10, subject);
			pstat.setString(11, sel);
			pstat.setString(12, subject);
			pstat.setString(13, sel);
			pstat.setString(14, subject);
			pstat.setString(15, sel);
			pstat.setString(16, subject);
			pstat.setString(17, sel);
			pstat.setString(18, subject);
			pstat.setString(19, sel);
			pstat.setString(20, subject);
			pstat.setString(21, sel);
			pstat.setString(22, subject);

			ResultSet rs = pstat.executeQuery();

			ArrayList<FinalScoreDTO> fslist = new ArrayList<FinalScoreDTO>();

			while (rs.next()) {

				FinalScoreDTO fSDto = new FinalScoreDTO();

				fSDto.setSeq(rs.getString("seq"));
				fSDto.setName(rs.getString("name"));
				fSDto.setSubjectname(rs.getString("subjectname"));
				fSDto.setAttendancescore(rs.getString("attendancescore"));
				fSDto.setPilgi(rs.getString("pilgi"));
				fSDto.setSilgi(rs.getString("silgi"));
				fSDto.setTotal(rs.getString("total"));

				fslist.add(fSDto);
			}

			rs.close();
			return fslist;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;

	}

	/**
	 * 
	 * @param 과정번호
	 * @param 과목번호
	 * @return 과목번호|과목명|결석|지각|외출|조퇴|병가|기타|출근|출석배점|필기배점|실기배점 반환
	 */
	public ArrayList<PerScoreListDTO> perscorego(String sel, String subject) {

		String sql = "select      l.seq as seq,     s.name as name,      j.subject_name as subjectname,     (select \"출석점수\" from (SELECT learningseq,  (COUNT (case     when a.status = '출근' then 1 end ) + COUNT (CASE     WHEN a.status = '지각' then 1     when a.status = '외출' then 1     when a.status = '조퇴' then 1     when a.status = '병가' then 1     when a.status = '기타' then 1      END )*0.5) /(SELECT COUNT         (CASE             WHEN TO_CHAR(v.regdate, 'd') IN ('7', '1') THEN NULL             WHEN h.name is not null THEN NULL             ELSE 1         END) AS \"java교육기간의 평일수\" FROM (SELECT TO_DATE((select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd') + level -1 AS regdate FROM dual     CONNECT BY level <= (to_date((select enddate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd')- TO_DATE((select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd') + 1)) v     LEFT OUTER JOIN tblholiday h         ON v.regdate = h.regdate)*20 as \"출석점수\" FROM TBLATTENDED a where intime between (select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?) and (select enddate from tblcoursesubject where courseseq = ? and subjectseq = ?)  GROUP BY learningseq    order by learningseq) where learningseq = l.seq) as attendance,       (select count(*) from tblpilgianswer p         inner join tblpilgiexam e             on e.seq = p.pilgiexamseq                 where p.learningseq = l.seq and e.coursesubjectseq = cs.seq                     and p.answer = e.answer) * 2 as pilgi,     (select count(*) from tblsilgianswer p         inner join tblsilgiexam e             on e.seq = p.silgiexamseq                 where p.learningseq = l.seq and e.coursesubjectseq = cs.seq                     and p.answer = e.answer) * 4 as silgi,                               (select count(*) from tblpilgianswer p         inner join tblpilgiexam e             on e.seq = p.pilgiexamseq                 where p.learningseq = l.seq and e.coursesubjectseq = cs.seq                     and p.answer = e.answer) * 2 +     (select count(*) from tblsilgianswer p         inner join tblsilgiexam e             on e.seq = p.silgiexamseq                 where p.learningseq = l.seq and e.coursesubjectseq = cs.seq                     and p.answer = e.answer) * 4 +                     (select \"출석점수\" from (SELECT learningseq,  (COUNT (case     when a.status = '출근' then 1 end ) + COUNT (CASE     WHEN a.status = '지각' then 1     when a.status = '외출' then 1     when a.status = '조퇴' then 1     when a.status = '병가' then 1     when a.status = '기타' then 1      END )*0.5) /(SELECT COUNT         (CASE             WHEN TO_CHAR(v.regdate, 'd') IN ('7', '1') THEN NULL             WHEN h.name is not null THEN NULL             ELSE 1         END) AS \"java교육기간의 평일수\" FROM (SELECT TO_DATE((select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd') + level -1 AS regdate FROM dual     CONNECT BY level <= (to_date((select enddate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd')- TO_DATE((select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?), 'yyyy-mm-dd') + 1)) v     LEFT OUTER JOIN tblholiday h         ON v.regdate = h.regdate)*20 as \"출석점수\" FROM TBLATTENDED a where intime between (select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?) and (select enddate from tblcoursesubject where courseseq = ? and subjectseq = ?)  GROUP BY learningseq    order by learningseq) where learningseq = l.seq)                                          as total , '0' as notattenance, '0.5' as halfattendance, '1' as attendance, '20' as attscore, '2' as pilgiscore, '4' as silgiscore from tblstudent s     inner join tbllearning l         on s.seq = l.studentseq             inner join tblCourse c                 on c.seq = l.courseseq                     inner join tblcoursesubject cs                         on c.seq = cs.courseseq                             inner join tblsubject j                                 on j.seq = cs.subjectseq                                              where c.seq = ? and cs.subjectseq = ?";

		try {

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, sel);
			pstat.setString(2, subject);
			pstat.setString(3, sel);
			pstat.setString(4, subject);
			pstat.setString(5, sel);
			pstat.setString(6, subject);
			pstat.setString(7, sel);
			pstat.setString(8, subject);
			pstat.setString(9, sel);
			pstat.setString(10, subject);
			pstat.setString(11, sel);
			pstat.setString(12, subject);
			pstat.setString(13, sel);
			pstat.setString(14, subject);
			pstat.setString(15, sel);
			pstat.setString(16, subject);
			pstat.setString(17, sel);
			pstat.setString(18, subject);
			pstat.setString(19, sel);
			pstat.setString(20, subject);
			pstat.setString(21, sel);
			pstat.setString(22, subject);

			ResultSet rs = pstat.executeQuery();

			ArrayList<PerScoreListDTO> pslist = new ArrayList<PerScoreListDTO>();

			while (rs.next()) {

				PerScoreListDTO sDto = new PerScoreListDTO();

				sDto.setSeq(rs.getString("seq"));
				sDto.setSubjectname(rs.getString("subjectname"));
				sDto.setNotattendance(rs.getString("notattenance"));
				sDto.setHalfattendance(rs.getString("halfattendance"));
				sDto.setAttendance(rs.getString("attendance"));
				sDto.setAttscore(rs.getString("attscore"));
				sDto.setPilgiscore(rs.getString("pilgiscore"));
				sDto.setSilgiscore(rs.getString("silgiscore"));

				pslist.add(sDto);
			}

			rs.close();
			return pslist;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	/**
	 * 
	 * @param 과정번호
	 * @param 과목번호
	 * @return 필기번호|문제|답 반환
	 */
	public ArrayList<AnswerPilgiJohaeDTO> answerJohae(String sel, String check1) {

		String sql = "select tpe.seq as seq, rpad(tpe.question, 60, ' ') as question,  tpe.answer as answer from tblpilgiexam tpe inner join tblcoursesubject tcs on tpe.coursesubjectseq = tcs.seq where tcs.courseseq = ? and tcs.subjectseq =?";
		try {
			pstat = conn.prepareStatement(sql);

			System.out.println();

			pstat.setString(1, sel);
			pstat.setString(2, check1);
			ResultSet rs = pstat.executeQuery();

			ArrayList<AnswerPilgiJohaeDTO> apjlist = new ArrayList<AnswerPilgiJohaeDTO>();

			while (rs.next()) {

				AnswerPilgiJohaeDTO apjDto = new AnswerPilgiJohaeDTO();

				apjDto.setSeq(rs.getString("seq"));
				apjDto.setQuesiton(rs.getString("question"));
				apjDto.setAnswer(rs.getString("answer"));

				apjlist.add(apjDto);
			}

			rs.close();
			return apjlist;

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;

	}

	/**
	 * 
	 * @param 과정번호
	 * @param 과목번호
	 * @return 학생번호|학생이름|과목명|필기|실기|출석 반환
	 */
	public ArrayList<NumberOfAnswerDTO> numberOfanswer(String sel, String choice1) {

		String sql = "select l.seq as seq, s.name as name, j.subject_name as subjectname,  (SELECT   (COUNT (case     when a.status = '출근' then 1 end ) + COUNT (CASE     WHEN a.status = '지각' then 1     when a.status = '외출' then 1     when a.status = '조퇴' then 1     when a.status = '병가' then 1     when a.status = '기타' then 1      END )*0.5) FROM TBLATTENDED a where a.learningseq = l.seq and a.intime between (select begindate from tblcoursesubject where courseseq = ? and subjectseq = ?) and (select enddate from tblcoursesubject where courseseq = ? and subjectseq = ?)  GROUP BY a.learningseq) as attendance,      (select count(*) from tblpilgianswer p         inner join tblpilgiexam e             on e.seq = p.pilgiexamseq                 where p.learningseq = l.seq and e.coursesubjectseq = cs.seq and p.answer = e.answer) as pilgi,     (select count(*) from tblsilgianswer p  inner join tblsilgiexam e on e.seq = p.silgiexamseq  where p.learningseq = l.seq and e.coursesubjectseq = cs.seq and p.answer = e.answer) as silgi from tblstudent s     inner join tbllearning l on s.seq = l.studentseq  inner join tblCourse c on c.seq = l.courseseq    inner join tblcoursesubject cs on c.seq = cs.courseseq inner join tblsubject j on j.seq = cs.subjectseq  where c.seq = ? and cs.subjectseq = ?";
		try {
			pstat = conn.prepareStatement(sql);

			System.out.println();

			pstat.setString(1, sel);
			pstat.setString(2, choice1);
			pstat.setString(3, sel);
			pstat.setString(4, choice1);
			pstat.setString(5, sel);
			pstat.setString(6, choice1);

			ResultSet rs = pstat.executeQuery();

			ArrayList<NumberOfAnswerDTO> noalist = new ArrayList<NumberOfAnswerDTO>();

			while (rs.next()) {

				NumberOfAnswerDTO noaDto = new NumberOfAnswerDTO();

				noaDto.setSeq(rs.getString("seq"));
				noaDto.setName(rs.getString("name"));
				noaDto.setSubjectname(rs.getString("subjectname"));
				noaDto.setPilgi(rs.getString("pilgi"));
				noaDto.setSilgi(rs.getString("silgi"));
				noaDto.setAttendance(rs.getString("attendance"));

				noalist.add(noaDto);
			}

			rs.close();
			return noalist;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;

	}

	/**
	 * 
	 * @param 과정번호
	 * @param 과목번호
	 * @return 학생번호|학생이름|과목명|필기|실기|출석 반환
	 */
	public ArrayList<AnswerSilgiJohaeDTO> answerSilgiJohae(String sel, String choice1) {
		String sql = "select tpe.seq as seq, rpad(tpe.question, 60, ' ') as question, tpe.answer as answer from tblsilgiexam tpe inner join tblcoursesubject tcs on tpe.coursesubjectseq = tcs.seq where tcs.courseseq = ? and tcs.subjectseq =?";

		try {
			pstat = conn.prepareStatement(sql);

			System.out.println();

			pstat.setString(1, sel); // 변수 입력
			pstat.setString(2, choice1);
			ResultSet rs = pstat.executeQuery();

			ArrayList<AnswerSilgiJohaeDTO> asjlist = new ArrayList<AnswerSilgiJohaeDTO>();

			while (rs.next()) {

				AnswerSilgiJohaeDTO asjDto = new AnswerSilgiJohaeDTO();

				asjDto.setSeq(rs.getString("seq"));
				asjDto.setQuesiton(rs.getString("question"));
				asjDto.setAnswer(rs.getString("answer"));

				asjlist.add(asjDto);
			}

			rs.close();
			return asjlist;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	/**
	 * 
	 * @param 강사번호
	 * @return 과정번호|과정명|과정기간|강의실|과목명|과목기간|교재|학생수 반환
	 */
	public ArrayList<ScheduleDTO> schedule(TeacherDTO dto) {
		try {

			String sql = "SELECT c.seq as seq, ct.title as coursetitle, c.begindate || ' ~ ' || c.enddate as coursedate, "
					+ "r.name as roomname, s.subject_name as subjectname, cs.begindate || ' ~ ' || cs.enddate as subjectdate, "
					+ "    b.title subjectbook, (SELECT COUNT(*) FROM tbllearning l INNER JOIN tblstudent st ON st.seq = l.studentseq "
					+ "    WHERE l.courseseq = cs.courseseq AND l.ing = '진행중' AND st.delete_status = '회원'"
					+ "    ) AS studentsu" + " FROM tblcoursesubject cs" + " INNER JOIN tblteacher t"
					+ " ON cs.teacherseq = t.seq" + " INNER JOIN tblsubject s" + " ON s.seq = cs.subjectseq"
					+ " INNER JOIN tblcourse c" + " ON c.seq = cs.courseseq" + " INNER JOIN tblcoursetitle ct"
					+ " ON c.coursetitleseq = ct.seq" + " INNER JOIN tblroom r" + " ON r.seq = c.roomseq"
					+ " INNER JOIN tblbook b" + " ON b.seq = cs.bookseq" + " WHERE t.name = ?";

			pstat = conn.prepareStatement(sql);

			String answer = dto.getName();

			pstat.setString(1, answer);

			ResultSet rs = pstat.executeQuery();

			ArrayList<ScheduleDTO> schedule = new ArrayList<ScheduleDTO>();

			while (rs.next()) {
				ScheduleDTO dtoo = new ScheduleDTO();

				dtoo.setSeq(rs.getString("seq"));
				dtoo.setCoursetitle(rs.getString("coursetitle"));
				dtoo.setCoursedate(rs.getString("coursedate"));
				dtoo.setRoomname(rs.getString("roomname"));
				dtoo.setSubjectname(rs.getString("subjectname"));
				dtoo.setSubjectdate(rs.getString("subjectdate"));
				dtoo.setSubjectbook(rs.getString("subjectbook"));
				dtoo.setStudentsu(rs.getString("studentsu"));

				schedule.add(dtoo);
			}

			rs.close();
			return schedule;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;

	}

	/**
	 * 
	 * @param 강사번호
	 * @return 과정명|과정기간|상태 반환
	 */
	public ArrayList<CourseDTO> course(TeacherDTO dto) {
		try {

			String sql = "select distinct tct.title as coursename,tc.begindate|| ' ~ '||tc.enddate as coursedate,tc.status as from tblcoursesubject tcs"
					+ " inner join tblteacher tt" + " on tcs.teacherseq = tt.seq" + " inner join tblcourse tc"
					+ " on tc.seq = tcs.courseseq" + " inner join tblcoursetitle tct"
					+ " on tct.seq = tc.coursetitleseq" + " where tt.name = ?"
					+ " order by tc.begindate|| ' ~ '||tc.enddate";

			pstat = conn.prepareStatement(sql);

			String answer = dto.getName();
			System.out.println(answer);

			pstat.setString(1, answer);

			ResultSet rs = pstat.executeQuery();

			ArrayList<CourseDTO> course = new ArrayList<CourseDTO>();

			while (rs.next()) {
				CourseDTO dtoo = new CourseDTO();

				dtoo.setCoursename(rs.getString("coursename"));
				dtoo.setCoursedate(rs.getString("coursedate"));
				dtoo.setStatus(rs.getString("status"));

				course.add(dtoo);
			}

			return course;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	/**
	 * 
	 * @param 강사번호
	 * @return 시작날짜|시간|종료날짜|이름|상태 반환
	 */
	public ArrayList<WholeDTO> whole(TeacherDTO dto) {
		try {

			String sql = "SELECT" + " TO_CHAR(a.intime,'yyyy-mm-dd') beginday," + " CASE "
					+ " WHEN TO_CHAR(a.intime,'hh24:mi') LIKE '00:00' THEN '없음'" + " ELSE TO_CHAR(a.intime,'hh24:mi')"
					+ " END AS begintime," + " CASE" + " WHEN TO_CHAR(a.outtime,'hh24:mi') LIKE '00:00' THEN '없음'"
					+ " ELSE TO_CHAR(a.outtime,'hh24:mi')" + " END AS endday," + " st.name AS name, a.status AS state"
					+ " FROM tblstudent st" + " INNER JOIN tbllearning l" + " ON l.studentseq = st.seq"
					+ " INNER JOIN tblattended a" + " ON a.learningseq = l.seq" + " INNER JOIN tblcourse c"
					+ " ON c.seq = l.courseseq" + " INNER JOIN tblcoursetitle ct" + " ON ct.seq = c.coursetitleseq"
					+ " inner join tblteacher tt on ct.seq = tt.seq where tt.name =?" + " ORDER BY beginday";

			pstat = conn.prepareStatement(sql);

			String answer = dto.getName();

			pstat.setString(1, answer);

			ResultSet rs = pstat.executeQuery();

			ArrayList<WholeDTO> whole = new ArrayList<WholeDTO>();

			while (rs.next()) {
				WholeDTO dtoo = new WholeDTO();

				dtoo.setBeginday(rs.getString("beginday"));
				dtoo.setBegintime(rs.getString("begintime"));
				dtoo.setEndday(rs.getString("endday"));
				dtoo.setName(rs.getString("name"));
				dtoo.setState(rs.getString("state"));

				whole.add(dtoo);
			}

			return whole;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	/**
	 * 
	 * @param 입력한  년도
	 * @param 입력한  월
	 * @param 입력한  일
	 * @param 강사번호
	 * @return 날짜|출근시간|퇴근시간|이름|상태|과정 반환
	 */
	public ArrayList<DayDTO> dayy(int year, int month, int day, TeacherDTO dto) {
		try {

			String sql = " SELECT" + " DISTINCT TO_CHAR(a.intime,'yyyy-mm-dd') beginday," + " CASE"
					+ " WHEN TO_CHAR(a.intime,'hh24:mi') LIKE '00:00' THEN '없음'" + " ELSE TO_CHAR(a.intime,'hh24:mi')"
					+ " END AS intime," + " CASE" + " WHEN TO_CHAR(a.outtime,'hh24:mi') LIKE '00:00' THEN '없음'"
					+ " ELSE TO_CHAR(a.outtime,'hh24:mi')" + " END AS outtime,"
					+ " st.name AS name, a.status AS stats, ct.title AS course" + " FROM tblstudent st"
					+ " INNER JOIN tbllearning l" + " ON l.studentseq = st.seq" + " INNER JOIN tblattended a"
					+ " ON a.learningseq = l.seq" + " INNER JOIN tblcourse c" + " ON c.seq = l.courseseq"
					+ " inner join tblcoursesubject cc" + " on c.seq = cc.teacherseq" + " inner join tblteacher bb"
					+ " on cc.teacherseq = bb.seq" + " INNER JOIN tblcoursetitle ct" + " ON ct.seq = c.coursetitleseq"
					+ " WHERE TO_CHAR(a.intime,'yyyy') = LTRIM(TO_CHAR(?, '0000')) and TO_CHAR(a.intime,'mm') = LTRIM(TO_CHAR(?, '00')) and TO_CHAR(a.intime,'dd') = LTRIM(TO_CHAR(?, '00'))"
					+ " and bb.name = ?" + " ORDER BY beginday";

			pstat = conn.prepareStatement(sql);
			String answer = dto.getName();

			pstat.setInt(1, year);
			pstat.setInt(2, month);
			pstat.setInt(3, day);
			pstat.setString(4, answer);

			ResultSet rs = pstat.executeQuery();

			ArrayList<DayDTO> dayy = new ArrayList<DayDTO>();

			while (rs.next()) {
				DayDTO dtoo = new DayDTO();
				dtoo.setBeginday(rs.getString("beginday"));
				dtoo.setIntime(rs.getString("intime"));
				dtoo.setOuttime(rs.getString("outtime"));
				dtoo.setName(rs.getString("name"));
				dtoo.setStats(rs.getString("stats"));
				dtoo.setCourse(rs.getString("course"));

				dayy.add(dtoo);
			}
			rs.close();
			return dayy;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	/**
	 * 
	 * @param 입력한  년도
	 * @param 입력한  월
	 * @param 강사번호
	 * @return 날짜|출근시간|퇴근시간|이름|상태|과정 반환
	 */
	public ArrayList<DayDTO> monthh(int year, int month, TeacherDTO dto) {
		try {

			String sql = "SELECT DISTINCT" + " TO_CHAR(a.intime,'yyyy-mm-dd') beginday," + " CASE"
					+ " WHEN TO_CHAR(a.intime,'hh24:mi') LIKE '00:00' THEN '없음'" + " ELSE TO_CHAR(a.intime,'hh24:mi')"
					+ " END AS intime," + " CASE" + " WHEN TO_CHAR(a.outtime,'hh24:mi') LIKE '00:00' THEN '없음'"
					+ " ELSE TO_CHAR(a.outtime,'hh24:mi')" + " END AS outtime,"
					+ " st.name AS name, a.status AS stats, ct.title AS course" + " FROM tblstudent st"
					+ " INNER JOIN tbllearning l" + " ON l.studentseq = st.seq" + " INNER JOIN tblattended a"
					+ " ON a.learningseq = l.seq" + " INNER JOIN tblcourse c" + " ON c.seq = l.courseseq"
					+ " inner join tblcoursesubject cc" + " on c.seq = cc.teacherseq" + " inner join tblteacher bb"
					+ " on cc.teacherseq = bb.seq" + " INNER JOIN tblcoursetitle ct" + " ON ct.seq = c.coursetitleseq"
					+ " WHERE TO_CHAR(a.intime,'yyyy') = LTRIM(TO_CHAR(?, '0000')) and TO_CHAR(a.intime,'mm') = LTRIM(TO_CHAR(?, '00'))"
					+ " and bb.name = ? ORDER BY TO_CHAR(a.intime,'yyyy-mm-dd')";
			pstat = conn.prepareStatement(sql);
			String answer = dto.getName();

			pstat.setInt(1, year);
			pstat.setInt(2, month);
			pstat.setString(3, answer);

			ResultSet rs = pstat.executeQuery();

			ArrayList<DayDTO> monthh = new ArrayList<DayDTO>();

			while (rs.next()) {
				DayDTO dto1 = new DayDTO();

				dto1.setBeginday(rs.getString("beginday"));
				dto1.setIntime(rs.getString("intime"));
				dto1.setOuttime(rs.getString("outtime"));
				dto1.setName(rs.getString("name"));
				dto1.setStats(rs.getString("stats"));
				dto1.setCourse(rs.getString("course"));

				monthh.add(dto1);
			}
			rs.close();
			return monthh;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	/**
	 * 
	 * @param 입력한  년도
	 * @param 강사번호
	 * @return 날짜|출근시간|퇴근시간|이름|상태|과정 반환
	 */
	public ArrayList<DayDTO> yearr(int year, TeacherDTO dto) {
		try {

			String sql = "SELECT DISTINCT" + " TO_CHAR(a.intime,'yyyy-mm-dd') beginday," + " CASE"
					+ " WHEN TO_CHAR(a.intime,'hh24:mi') LIKE '00:00' THEN '없음'" + " ELSE TO_CHAR(a.intime,'hh24:mi')"
					+ " END AS intime," + " CASE" + " WHEN TO_CHAR(a.outtime,'hh24:mi') LIKE '00:00' THEN '없음'"
					+ " ELSE TO_CHAR(a.outtime,'hh24:mi')" + " END AS outtime,"
					+ " st.name AS name, a.status AS stats, ct.title AS course" + " FROM tblstudent st"
					+ " INNER JOIN tbllearning l" + " ON l.studentseq = st.seq" + " INNER JOIN tblattended a"
					+ " ON a.learningseq = l.seq" + " INNER JOIN tblcourse c" + " ON c.seq = l.courseseq"
					+ " inner join tblcoursesubject cc" + " on c.seq = cc.teacherseq" + " inner join tblteacher bb"
					+ " on cc.teacherseq = bb.seq" + " INNER JOIN tblcoursetitle ct" + " ON ct.seq = c.coursetitleseq"
					+ " WHERE TO_CHAR(a.intime,'yyyy') = LTRIM(TO_CHAR(?, '0000')) and bb.name = ?"
					+ " ORDER BY TO_CHAR(a.intime,'yyyy-mm-dd')";
			pstat = conn.prepareStatement(sql);
			String answer = dto.getName();

			pstat.setInt(1, year);
			pstat.setString(2, answer);

			ResultSet rs = pstat.executeQuery();

			ArrayList<DayDTO> yearr = new ArrayList<DayDTO>();

			while (rs.next()) {
				DayDTO dto1 = new DayDTO();

				dto1.setBeginday(rs.getString("beginday"));
				dto1.setIntime(rs.getString("intime"));
				dto1.setOuttime(rs.getString("outtime"));
				dto1.setName(rs.getString("name"));
				dto1.setStats(rs.getString("stats"));
				dto1.setCourse(rs.getString("course"));

				yearr.add(dto1);
			}
			rs.close();
			return yearr;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	/**
	 * 
	 * @param 강사번호
	 * @return 과목명|출결률 반환
	 */
	public ArrayList<SSubjectDTO> subject(TeacherDTO dto) {
		try {

			String sql = " select round(s.출결률,2) as inoutday,s.subject as subject from " + " (SELECT" + " (COUNT"
					+ " (case" + " when a.status = '출근' then 1" + " end" + " ) )" + " /((SELECT COUNT" + " (CASE"
					+ " WHEN TO_CHAR(v.regdate, 'd') IN ('7', '1') THEN NULL" + " WHEN h.name is not null THEN NULL"
					+ " ELSE 1" + " END) AS \"java교육기간의 일수\""
					+ " FROM (SELECT TO_DATE('20190329', 'yyyymmdd') + level -1 AS regdate FROM dual"
					+ " CONNECT BY level <= (to_date('20190425', 'yyyymmdd')- TO_DATE('20190329', 'yyyymmdd') + 1)) v"
					+ " LEFT OUTER JOIN tblholiday h" + " ON v.regdate = h.regdate)*30)*100 as 출결률," + " CASE"
					+ " WHEN intime >= TO_DATE('2019-03-29', 'YYYY-MM-DD') AND "
					+ " intime <= TO_DATE('2019-04-25', 'YYYY-MM-DD') THEN 'ORACLE'" + " END as subject"
					+ " FROM TBLATTENDED a" + " GROUP BY(" + " CASE"
					+ " WHEN intime >= TO_DATE('2019-03-29', 'YYYY-MM-DD') AND "
					+ " intime <= TO_DATE('2019-04-25', 'YYYY-MM-DD') THEN 'ORACLE'" + " END" + " )"
					+ " ) s where subject is not null" + " union"
					+ " select round(s.출결률,2) as inoutday,s.subject as subject from " + " (SELECT" + " (COUNT"
					+ " (case" + " when a.status = '출근' then 1" + " end" + " ) )" + " /((SELECT COUNT" + " (CASE"
					+ " WHEN TO_CHAR(v.regdate, 'd') IN ('7', '1') THEN NULL" + " WHEN h.name is not null THEN NULL"
					+ " ELSE 1" + " END) AS \"java교육기간의 일수\""
					+ " FROM (SELECT TO_DATE('20190128', 'yyyymmdd') + level -1 AS regdate FROM dual"
					+ " CONNECT BY level <= (to_date('20190328', 'yyyymmdd')- TO_DATE('20190128', 'yyyymmdd') + 1)) v"
					+ " LEFT OUTER JOIN tblholiday h" + " ON v.regdate = h.regdate)*30)*100 as 출결률," + " CASE"
					+ " WHEN intime >= TO_DATE('2019-01-28', 'YYYY-MM-DD') AND "
					+ " intime <= TO_DATE('2019-03-28', 'YYYY-MM-DD') THEN 'JAVA'" + " END as subject"
					+ " FROM TBLATTENDED a" + " GROUP BY(" + " CASE"
					+ " WHEN intime >= TO_DATE('2019-01-28', 'YYYY-MM-DD') AND "
					+ " intime <= TO_DATE('2019-03-28', 'YYYY-MM-DD') THEN 'JAVA'" + " END" + " )"
					+ " ) s where subject is not null " + " order by subject";

			pstat = conn.prepareStatement(sql);

			String answer = dto.getName();

			ResultSet rs = pstat.executeQuery();

			ArrayList<SSubjectDTO> subject = new ArrayList<SSubjectDTO>();

			while (rs.next()) {
				SSubjectDTO dtoo = new SSubjectDTO();

				dtoo.setSubject(rs.getString("subject"));
				dtoo.setInoutday(rs.getString("inoutday"));
				subject.add(dtoo);
			}

			rs.close();
			return subject;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	/**
	 * 
	 * @param 강사번호
	 * @return 과목번호|과목명|과목기간|상태 반환
	 */
	public ArrayList<CourseDTO> course2(TeacherDTO dto) {
		try {

			String sql = "select distinct tc.seq as num,tct.title as coursename,"
					+ "tc.begindate|| ' ~ '||tc.enddate as coursedate,tc.status as status from tblcoursesubject tcs"
					+ " inner join tblteacher tt" + " on tcs.teacherseq = tt.seq" + " inner join tblcourse tc"
					+ " on tc.seq = tcs.courseseq" + " inner join tblcoursetitle tct"
					+ " on tct.seq = tc.coursetitleseq" + " where tt.name = ?"
					+ " order by tc.begindate|| ' ~ '||tc.enddate";

			pstat = conn.prepareStatement(sql);

			String answer = dto.getName();

			pstat.setString(1, answer);

			ResultSet rs = pstat.executeQuery();

			ArrayList<CourseDTO> course2 = new ArrayList<CourseDTO>();

			while (rs.next()) {
				CourseDTO dtoo = new CourseDTO();
				dtoo.setNum(rs.getString("num"));
				dtoo.setCoursename(rs.getString("coursename"));
				dtoo.setCoursedate(rs.getString("coursedate"));
				dtoo.setStatus(rs.getString("status"));

				course2.add(dtoo);
			}

			return course2;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

}
