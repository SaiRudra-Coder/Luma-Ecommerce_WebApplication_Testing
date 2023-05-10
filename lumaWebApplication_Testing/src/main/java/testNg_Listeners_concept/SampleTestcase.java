package testNg_Listeners_concept;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(testNg_Listeners_concept.Listeners.class)
public class SampleTestcase
{
	@Test
	public void test1()
	{
		System.out.println("Test case 1 ");
		Assert.assertEquals("A", "A");
	}
	
	@Test
	public void test2()
	{
		System.out.println("Test case 2 ");
		Assert.assertEquals("A", "B");
	}
	
	@Test
	public void test3()
	{
		System.out.println("Test case 3 ");
		throw new SkipException("This test case 3 is skipped");
	}
}
