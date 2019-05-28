package com.project2.teacher;

/**
 * 강사 - 담당 교육생들의 일별 출결 객체 클래스
 * 
 * @author sist56
 *
 */
public class DayDTO {
	private String beginday;
	private String intime;
	private String outtime;
	private String name;
	private String stats;
	private String course;

	public String getBeginday() {
		return beginday;
	}

	public void setBeginday(String beginday) {
		this.beginday = beginday;
	}

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

}
