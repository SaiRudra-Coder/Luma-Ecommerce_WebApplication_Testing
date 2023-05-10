package testNg_dataReading_concept;

import org.testng.annotations.DataProvider;

public class DataReading 
{	
	@DataProvider(name= "Data")
	public Object[][] getData()
	{
		Object[][] data = {{"srinivas", "Akamai"}, {"sri", "Accenture"}};
		return data;
	}
}
