package seleniumControls;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EngineForAll
{
	public WebDriver driver;
	public Logger log;
	public Map sh;
	public EngineForAll(WebDriver driver, Logger log, Map sh)
	{
		this.driver=driver;
		this.log= log;
		this.sh=sh;
	}
	
	public WebElement getSingleElement(String selector, boolean type, int wait_sec)
	{
		try 
		{
			if(type==true)
			{
				WebDriverWait wt = new WebDriverWait(this.driver, Duration.ofSeconds(wait_sec));
				wt.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
				return driver.findElement(By.cssSelector(selector));
			}
			else
			{
				WebDriverWait wt= new WebDriverWait(this.driver, Duration.ofSeconds(wait_sec));
				wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));
				return this.driver.findElement(By.xpath(selector));
			}
		} 
		catch (IllegalArgumentException e3) 
		{
			this.log.error("Noticed null as a selector");
		}
		catch (InvalidSelectorException  e) 
		{
			this.log.error("Selecting Incorrect Selector");
		}
		catch (NoSuchElementException e1)
		{
			this.log.error("The locators are unable to find or access elements on the web page");
		}
		catch (TimeoutException e2) 
		{
			this.log.error("The command did not execute or complete within wait time");
		}
		return null;
	}
	
	public List<WebElement> getMultiElements(String SelectorType, String selector1, int wait_sec)
	{
		try 
		{
			if(SelectorType.equalsIgnoreCase("cssSelector"))
			{
				WebDriverWait wt = new WebDriverWait(this.driver, Duration.ofSeconds(wait_sec));
				wt.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(selector1)));
				return driver.findElements(By.cssSelector(selector1));
			}
			else
			{
				WebDriverWait wt = new WebDriverWait(this.driver, Duration.ofSeconds(wait_sec));
				wt.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(selector1)));
				return driver.findElements(By.xpath(selector1));
			}
		} 
		catch (IllegalArgumentException e3) 
		{
			this.log.error("Noticed null as a selector");
		}
		catch (InvalidSelectorException  e) 
		{
			this.log.error("Selecting Incorrect Selector");
		}
		catch (NoSuchElementException e1)
		{
			this.log.error("The locators are unable to find or access elements on the web page");
		}
		catch (TimeoutException e2) 
		{
			this.log.error("The command did not execute or complete within wait time");
		}
		return null;
	}
	
	public boolean products_size_clr_picking(String selector1, String selector2, String selector3, String selector4, String selector5, String attributeSelector1, String attributeSelector2, String Selector6,int wait_sec) throws InterruptedException 
	{
		boolean status=false;
		try 
		{
			List<WebElement> ele=this.getMultiElements("cssSelector", selector1, wait_sec);
			if(ele!=null)
			{
				for(WebElement productsList:ele)
				{
					String product_Name=productsList.findElement(By.cssSelector(selector2)).getText();
					this.log.info("Product Name : "+product_Name);
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
					 
					ArrayList size_list = new ArrayList();
					List<WebElement> ui_size= productsList.findElements(By.cssSelector(selector3));
					for(WebElement size: ui_size)
					{
						size_list.add(size.getText());
					}
					this.log.info("Avalible size's are: "+size_list);
					if(size_list.size()>0)
					{
						for(Object size: size_list)
						{
							Actions ac= new Actions(driver);
							WebElement size_ele=productsList.findElement(By.xpath(Selector6.replace("{}", size.toString())));
							ac.moveToElement(size_ele).click().perform();
							this.log.info("Successfully clicked on the size button : "+size.toString());
							
							for(WebElement product_clr:productsList.findElements(By.cssSelector(selector4)))
							{
								String colour=product_clr.getAttribute(attributeSelector2).toLowerCase();
								this.log.info("Clicking on colour button : "+ colour);
								product_clr.click();
								Thread.sleep(1000);
								String img_link = productsList.findElement(By.cssSelector(selector5)).getAttribute("src").toLowerCase();
								if(img_link.contains(colour))
								{
									this.log.info("Successfully Clicked on the colour button : "+colour);
									status=true;
								}
								else
								{
									this.log.error("Failed to click on the colour button: "+ colour);
								}
							}
							this.log.info("---------------------------------");
						}
					}
					else
					{
						this.log.error("There are no sizes are avalible for this product.");
					}
				}
			}
			else
			{
				this.log.error("List is empty");
			}
		} 
		catch (NullPointerException e)
		{
			this.log.error("Inside Element List data is null");
		}
		return status;
		
	}
	
	public LinkedHashMap pdDetails_storing(String selector1, String selector2, String selector3, String selector4, int wait_sec)
	{
		try 
		{
			LinkedHashMap hm = new LinkedHashMap();
			List<WebElement>ele=this.getMultiElements("cssSelector", selector1, wait_sec);
			if(ele!=null)
			{
				
				for(WebElement products:ele)
				{
					String bfproduct_Name=products.findElement(By.cssSelector(selector2)).getText();
					String bfproduct_price=products.findElement(By.cssSelector(selector3)).getText();
					String reviews_count="";
					try
					{
						 reviews_count=products.findElement(By.cssSelector(selector4)).getText().split(" ")[0].strip();
					}
					catch (NoSuchElementException e) 
					{
						this.log.info("Reviews are not avaliable for this product  " +bfproduct_Name);
					}
					hm.put(bfproduct_Name, new String[] {bfproduct_price, reviews_count});
				}
				return hm;
			}
			else
			{
				this.log.error("Product Element is empty");
			}
		} 
		catch (NullPointerException e)
		{
			this.log.error("Inside Element data is null");
		}
		return null;
	}
	
	public boolean product_select(String selector1, String selector2, String selector3,String selector4, 
			String selector5, String selector6, String selector7, String selector8, String selector9, 
			String selector10, String selector11, String selector12, String selector13, String selector14, int wait_sec) throws InterruptedException
	{
		boolean status=false;
		try
		{
			LinkedHashMap hm=pdDetails_storing(selector1, selector2, selector3, selector4, wait_sec);
			if(hm!=null)
			{
				if(hm.size()>0)
				{
					for(Object it: hm.keySet())
					{
						WebElement prod_ele = driver.findElement(By.xpath(selector5.replace("{}", it.toString())));
						Actions ac = new Actions(driver);
						ac.moveToElement(prod_ele).click().perform();
						String[] it_array=(String [])hm.get(it);
						this.log.info("successfully clicked on product "+it+" with price "+it_array[0]+" with reviews count "+it_array[1]);
						JavascriptExecutor js = (JavascriptExecutor)driver;
						js.executeScript("window.scrollTo(0, 200)");
						Thread.sleep(2000);
						
						String afproduct_name="";
						WebElement nameEle=this.getSingleElement(selector6, true, wait_sec);
						if(nameEle!=null)
						{
							afproduct_name=nameEle.getText();
						}
						else
						{
							this.log.error("Name element is null");
						}
						
						String afproduct_price="";
						WebElement priceEle=this.getSingleElement(selector7, true, wait_sec);
						if(priceEle!=null)
						{
							afproduct_price=priceEle.getText();
						}
						else
						{
							this.log.error("Price element is null");
						}
						
						String afproduct_review="";
						try
						{
							afproduct_review=driver.findElement(By.cssSelector(selector8)).getText().strip();
						}
						catch (NoSuchElementException e) 
						{
							this.log.error("Reviews not avaliable for this product "+afproduct_name);
							afproduct_review ="";
						}
						if(it.toString().equalsIgnoreCase(afproduct_name) && it_array[0].toString().equalsIgnoreCase(afproduct_price) && it_array[1].toString().equalsIgnoreCase(afproduct_review.toString()))
						{
							this.log.info("Before -> Product : "+it+", Price : "+it_array[0]+", Reviews : "+it_array[1]+" || After -> Product : "+afproduct_name+", Price : "+afproduct_price+", Reviews : "+afproduct_review);
							this.log.info("Product name  price and reviews count all  are validate sucessfully after clicking on the each product");
							
							ArrayList new_sizes= new ArrayList();
							List<WebElement>ele2=this.getMultiElements("cssSelector", selector9, wait_sec);
							if(ele2!=null)
							{
								for(WebElement product_size: ele2)
								{
									new_sizes.add(product_size.getText());
								}
								this.log.info("Avalible sizes are : "+ new_sizes);
								if(new_sizes.size()>0)
								{
									for(Object size: new_sizes)
									{
										Actions ac1= new Actions(driver);
										WebElement size_selected=driver.findElement(By.xpath(selector10.replace("{}", size.toString())));
										ac1.moveToElement(size_selected).click().perform();
										this.log.info("Successfully clicked on the size button : "+size.toString());
										List<WebElement>ele=this.getMultiElements("cssSelector", selector11, wait_sec);
										if(ele!=null)
										{
											for(WebElement product_clr: ele)
											{
												String colour=product_clr.getAttribute(selector12).toLowerCase();
												this.log.info("Clicking on colour button : "+ colour);
												product_clr.click();
												Thread.sleep(1000);
												
												String img_link="";
												WebElement imgEle=this.getSingleElement(selector13, true, wait_sec);
												if(imgEle!=null)
												{
													img_link=imgEle.getAttribute(selector14).toLowerCase();
												}
												else
												{
													this.log.error("Image element is null");
												}
												if(img_link.contains(colour))
												{
													this.log.info("Successfully Clicked on the colour button : "+colour);
													status =true;
												}
												else
												{
													this.log.error("Failed to click on the colour button: "+ colour);
												}
											}
											this.log.info("---------------------------------");
										}
										else
										{
											this.log.error("Product element is null");
										}
										
									}
									driver.navigate().back();
									Thread.sleep(2000);
								}
								else
								{
									this.log.info("There are no sizes are avalible for this product.");
									driver.navigate().back();
									Thread.sleep(2000);
								}
							}
							else
							{
								this.log.info("sizes and colours are not avaliable for this "+afproduct_name);
								driver.navigate().back();
								Thread.sleep(2000);
							}
						}
						else
						{
							this.log.error("Before product details after clicking the product details both are not matched");
						}
					}
				}
				else
				{
					this.log.error("LinkedHashMap  is empty");
				}
			}
			else
			{
				this.log.error("Linked hasmap key is null");
			}
		}
		catch (NullPointerException e)
		{
			this.log.error("Inside Element List data is null");
		}
		return status;
	}
	
	
	public boolean mouseClick(String selector, int wait_sec)
	{
		WebElement ele=getSingleElement(selector, true, wait_sec);
		if(ele!=null)
		{
			ele.click();
			return true;
		}
		return false;
	}
	
	public String getTextMsg(String selector, int wait_sec)
	{
		try
		{
			WebElement ele=getSingleElement(selector, true, wait_sec);
			if(ele!=null)
			{
				return ele.getText().toString();
			}
		} 
		catch (NullPointerException e)
		{
			this.log.error("Inside Element data is null");
			return null;
		}
		return null;
	}
	
	public boolean mouseActions(String selector, int wait_sec)
	{
		Actions act= new Actions(this.driver);
		WebElement ele=getSingleElement(selector, true, wait_sec);
		if(ele!=null)
		{
			act.moveToElement(ele).click().perform();
//			getSingleElement(ele_selector, true, wait_sec).click();;
			return true;
		}
		return false;
	}
	
	public boolean dropDownHandles(String selector, int selectByIndex, int wait_sec)
	{
		WebElement selection=getSingleElement(selector, true, wait_sec);
		Select sc= new Select(selection);
		sc.selectByIndex(selectByIndex);
		return true;
	}
	
	public boolean validation(String selector, int wait_sec)
	{
		boolean status=true;
		WebElement ele=getSingleElement(selector, true, wait_sec);
		if(ele!=null)
		{
//			this.log.info(ele.getText().toString());
			if(ele.getText().toString().equalsIgnoreCase("Create New Customer Account") || ele.getText().toString().equalsIgnoreCase("Thank you")
					|| ele.getText().toString().equalsIgnoreCase("Customer Login") || ele.getText().toString().equalsIgnoreCase("New Luma Yoga Collection"))
			{
				status=true;
			}		
		}
		else
		{
			this.log.error("Element is null or not Loaded");
			status=false;
		}
		return status;
	}
	
	public boolean sendKeys(String selector, String data, String selectorType, int wait_sec)
	{
		boolean status=false;
		if(selectorType.equalsIgnoreCase("css"))
		{
			WebElement ele=getSingleElement(selector, true, wait_sec);
			if(ele!=null)
			{
				ele.sendKeys(data);
				status=true;
			}
		}
		else if(selectorType.equalsIgnoreCase("xpath"))
		{
			WebElement ele=getSingleElement(selector, false, wait_sec);
			if(ele!=null)
			{
				ele.sendKeys(data);
				status=true;
			}
		}
		return status;
	}
	
	public String yamlFileValidation(String key1, String key2)
	{
		if(this.sh.get(key1)!=null)
		{
			if(key2!=null)
			{
				if(((Map)this.sh.get(key1)).containsKey(key2))
				{
					if(((Map)this.sh.get(key1)).get(key2)!=null)
					{
						return ((Map)this.sh.get(key1)).get(key2).toString();
					}
					else
					{
						this.log.error(key2 +"value is null inthe configure file ");
					}
				}
				else
				{
					this.log.error(key2+" is null in configure file inthe section of "+ key1);
				}
			}
			else
			{
				return this.sh.get(key1).toString();
			}
		}
		else
		{
			this.log.error(key1+ "Is null in the configuration file..");
		}
		return null;
	}
	
	
	
}
