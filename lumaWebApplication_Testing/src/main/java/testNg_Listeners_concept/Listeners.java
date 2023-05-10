package testNg_Listeners_concept;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener
{
	public void onStart(ITestContext context)
	{
		System.out.println("Starting before Test suite.."+context.getName());
	}
	
	public void onFinish(ITestContext arg)
	{
		System.out.println("Finished Test suite.."+arg.getName());
	}
	
	public void onTestStart(ITestResult arg)
	{
		System.out.println("Starting before Test.."+arg.getName());
	}
	
	public void onTestSuccess(ITestResult arg)
	{
		System.out.println("Executed for Success.."+arg.getName());
	}
	
	public void onTestFailure(ITestResult arg)
	{
		System.out.println("Executed for Failure.."+arg.getName());
	}
	
	public void onTestSkipped(ITestResult arg)
	{
		System.out.println("Executed for Skipped.."+arg.getName());
	}
	
}

