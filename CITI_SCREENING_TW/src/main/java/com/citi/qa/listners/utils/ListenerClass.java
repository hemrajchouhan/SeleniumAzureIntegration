package com.citi.qa.listners.utils;
import org.testng.IClass;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * 
 * @author Chouhan_h
 * 
 * TestNg Listeners 
 *
 */

public class ListenerClass extends TestListenerAdapter {

	@Override
	public void onTestStart(ITestResult tr) {
		log("Test Started....");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {

		log("Test '" + tr.getName() + "' PASSED");

		log(tr.getTestClass());

		log("Priority of this method is " + tr.getMethod().getPriority());

		System.out.println(".....");
	}

	@Override
	public void onTestFailure(ITestResult tr) {

		log("Test '" + tr.getName() + "' FAILED");
		log("Priority of this method is " + tr.getMethod().getPriority());
		System.out.println(".....");
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		log("Test '" + tr.getName() + "' SKIPPED");
		System.out.println(".....");
	}

	private void log(String methodName) {
		System.out.println(methodName);
	}

	private void log(IClass testClass) {
		System.out.println(testClass);
	}
}