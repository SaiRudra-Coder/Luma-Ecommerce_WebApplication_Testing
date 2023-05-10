package testNg_dataReading_concept;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProvider_concepts 
{
	//This is one way data reading from same class.
//	@DataProvider(name= "Data")
//	public Object[][] getData()
//	{	
//		Object[][] data = {{"srinivas", "Akamai"}, {"sri", "Accenture"}};
//		return data;
//	}
	
	
	//2nd way data reading from another class by mentioning that class name in parameters
	
	@Test(dataProvider = "Data", dataProviderClass = DataReading.class)
	public void test(String name, String company)
	{
		System.out.println(name +" - "+company);
	}
	
}
