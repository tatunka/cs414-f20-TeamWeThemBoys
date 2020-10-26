package com.xgame.restservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.xgame.common.viewmodels.TestViewModel;
import com.xgame.service.interfaces.ITestService;

@RestController
public class TestController {

	@Autowired
	private ITestService testService;

	@GetMapping("/test")
	public TestViewModel getTest(@RequestParam(value = "id", defaultValue = "0") int id) {

		return testService.getTest(id);
	}

	@GetMapping("/tests")
	public List<TestViewModel> getTests() {
		return testService.getTests();
	}

	@DeleteMapping("/test")
	public ResponseEntity<HttpStatus> deleteTest(@RequestParam(value = "id") int id) {
		testService.removeTest(id);

		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PostMapping("/test")
	public ResponseEntity<HttpStatus> addTest(@RequestParam(value = "content") String content) {
		testService.addTest(content);

		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PatchMapping("/test")
	public ResponseEntity<HttpStatus> updateTest(@RequestParam(value = "id") int id,
			@RequestParam(value = "content") String content) {
		testService.updateTest(id, content);

		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/test/error")
	public void error() {
		throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "This is a test error",
				new Exception("This is a test error"));
	}

}