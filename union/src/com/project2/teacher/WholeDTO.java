package com.project2.teacher;

/**
 * 강사 - 전체 교육생 출결 정보 객체 클래스
 * 
 * @author sist56
 *
 */
public class WholeDTO {
	private String beginday;
	private String begintime;
	private String endday;
	private String name;
	private String State;

	public String getBeginday() {
		return beginday;
	}

	public void setBeginday(String beginday) {
		this.beginday = beginday;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndday() {
		return endday;
	}

	public void setEndday(String endday) {
		this.endday = endday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

}
