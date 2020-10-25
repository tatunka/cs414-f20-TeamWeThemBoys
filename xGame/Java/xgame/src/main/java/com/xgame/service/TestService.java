package com.xgame.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xgame.common.viewmodels.TestViewModel;
import com.xgame.data.ITestRepository;
import com.xgame.data.entities.Test;
import com.xgame.service.interfaces.ITestService;

@Service
public class TestService implements ITestService {

	@Autowired
	private ITestRepository testRepo; 
	
	@Override
	public TestViewModel getTest(int id) {
		
		var test = testRepo.findById(id);
		
		if(test.isPresent()) {
			var t = test.get();
			return new TestViewModel(t.getId(), t.getContent());
		}
		
		return null;
	}

	@Override
	public List<TestViewModel> getTests() {
		return testRepo.findAll()
				.stream()
				.map(f -> new TestViewModel(f.getId(), f.getContent()))
				.collect(Collectors.toList());
	}

	@Override
	public void addTest(String content) {
		var t = new Test(content);
		testRepo.save(t);
	}

	@Override
	public void removeTest(int id) {
		testRepo.deleteById(id);		
	}
	
	@Override
	public void updateTest(int id, String content) {
		var test = testRepo.findById(id);
		
		if(test.isPresent()) {
			var t = test.get();
			t.setContent(content);
			
			testRepo.save(t);
		}
	}

}
