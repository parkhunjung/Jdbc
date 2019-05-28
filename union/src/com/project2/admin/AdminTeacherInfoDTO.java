package com.project2.admin;

/**
 * 관리자 - 강사 정보 객체 클래스
 * @author sist47
 *
 */
public class AdminTeacherInfoDTO {
	private String seq;
	private String name;
	private String age;
	private String regdate;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
}
