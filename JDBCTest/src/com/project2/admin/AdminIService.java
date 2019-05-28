package com.project2.admin;

import com.project2.main.AdminDTO;

public interface AdminIService {

	void teacheraccountmng();

	void opencourse_subjectmng();

	void studentmng();

	void attendancemng();

	void testmng_scorecheck();

	AdminDTO login();

}
