package com.xgame.service.interfaces;

import java.util.List;

import com.xgame.common.viewmodels.TestViewModel;

public interface ITestService {
	TestViewModel getTest(int id);
	List<TestViewModel> getTests();
	void addTest(String content);
	void removeTest(int id);
	void updateTest(int id, String content);
}
