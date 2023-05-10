package luma_homepage;
import seleniumControls.EngineForAll;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.yaml.snakeyaml.Yaml;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Create_and_signin 
{
	public WebDriver driver;
	public EngineForAll efa;
	public Logger log;
	public Map sh;
	
	@BeforeTest(groups = {"smoke"})
	public void loadConfigFile()
	{
		File fl=new File("src/main/resources/lumaProperties.yaml");
		try 
		{
			FileReader fr = new FileReader(fl);
			Yaml yl = new Yaml();
			this.sh=yl.load(fr);
			PropertyConfigurator pc= new PropertyConfigurator();
			pc.configure("src/main/resources/log4j.properties");
			this.log= Logger.getLogger(Create_and_signin.class.getName());
		} 
		catch (FileNotFoundException e) 
		{
			this.log.error("Missing configuration file "+e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@BeforeTest(dependsOnGroups = {"smoke"})
	public void driver_Setup()
	{
		try 
		{
			WebDriverManager.chromedriver().setup();
			this.driver= new ChromeDriver();
			this.driver.manage().window().maximize();
			this.driver.get(this.sh.get("siteUrl").toString());
			this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			this.efa= new EngineForAll(this.driver, this.log, this.sh);
		} 
		catch (WebDriverException e) 
		{
			this.log.error("Web driver Initiation failed due to "+ e.getMessage());
			System.exit(0);
		}		
	}
	
	@AfterTest()
	public void driver_quit()
	{
		this.driver.quit();
	}
	
	@Test(priority = 1)
	public void createAccount() throws InterruptedException
	{
		String caOptionSelector=this.efa.yamlFileValidation("createAccount", "optionClick");
		
		boolean crAccount=this.efa.mouseActions(caOptionSelector, 5);
		if(crAccount==true)
		{
			this.log.info("Create an Account option clicked successfully");
			String pageValSelector=this.efa.yamlFileValidation("createAccount", "pageValSelector");
			boolean pageValidate=this.efa.validation(pageValSelector, 5);
			if(pageValidate==true)
			{
				this.log.info("create an account new page is opened");
				String fNameSelector=this.efa.yamlFileValidation("createAccount", "firstNameSelector");
				String userfName= this.efa.yamlFileValidation("userCredentials", "fname");
				
				boolean firstName=this.efa.sendKeys(fNameSelector, userfName, "css", 3);
				if(firstName==true)
				{
					this.log.info("First name Entered");
					String lNameSelector=this.efa.yamlFileValidation("createAccount", "lastNameSelector");
					String userlName= this.efa.yamlFileValidation("userCredentials", "lname");
					
					boolean lastName=this.efa.sendKeys(lNameSelector, userlName, "css", 5);
					if(lastName==true)
					{
						this.log.info("Last name Entered");
						String checkboxSelector=this.efa.yamlFileValidation("createAccount", "checkBoxSelector");
						boolean checkBox=this.efa.mouseClick(checkboxSelector, 5);
						if(checkBox==true)
						{
							this.log.info("checkbox clicked");
							String emailSelector=this.efa.yamlFileValidation("createAccount", "emailSelector");
							String userEmail= this.efa.yamlFileValidation("userCredentials", "email");
							
							boolean email=this.efa.sendKeys(emailSelector, userEmail, "css", 5);
							if(email==true)
							{
								this.log.info("Email entered");
								String passwordSelector=this.efa.yamlFileValidation("createAccount", "passwordSelector");
								String userPassword= efa.yamlFileValidation("userCredentials", "password");
								
								boolean password=this.efa.sendKeys(passwordSelector, userPassword, "xpath", 5);
								if(password==true)
								{
									this.log.info("Password entered");
									String confirmSelector=this.efa.yamlFileValidation("createAccount", "confirmSelector");
									String userCnPassword=this.efa.yamlFileValidation("userCredentials", "cnpassword");
									
									boolean confirmPass=this.efa.sendKeys(confirmSelector, userCnPassword, "css", 5);
									if(confirmPass==true)
									{
										this.log.info("Confirm password is entered");
										String buttonSelector=this.efa.yamlFileValidation("createAccount", "buttonSelector");
										boolean buttonCreateAC=this.efa.mouseClick(buttonSelector, 5);
										if(buttonCreateAC==true)
										{
											this.log.info("create an account button has clicked");
											String myAccPageVal=this.efa.yamlFileValidation("createAccount", "myAccPageVal");
											boolean myAccountPage=this.efa.validation(myAccPageVal, 5);
											if(myAccountPage==true)
											{
												this.log.info("Customer successfully registering the My account");
												
												boolean dropDownClick=this.efa.mouseActions(".panel .header .customer-name", 5);
												if(dropDownClick==true)
												{
													this.log.info("customer welcome dropdown is activated or clicked");
													boolean signOutClick=this.efa.mouseActions(".customer-menu .header .authorization-link a", 5);
													if(signOutClick==true)
													{
														this.log.info("Customer successfully clicked on Sign Out option and After 5 seconds its redirected to home page");
														this.log.info("Test case is passed");
														Assert.assertTrue(true);
														Thread.sleep(5000);
													}
													else
													{
														this.log.error("Customer welcome-menu is activated, but failed to click on signout button");
														this.log.error("Test case is Failed..");
														Assert.fail("Test case is Failed.");
													}
												}
												else
												{
														this.log.error("Customer Welcome-menu is not activated");
												}
											}
											else
											{
												this.log.error("Failed to redirect to my account page or Failed to register an account");
												Assert.fail("Failed to redirect to my account page");
											}
										}
										else
										{
											this.log.error("Failed to click create an account button");
											Assert.fail("Failed to click create an account button");
										}
									}
									else
									{
										this.log.info("Failed to click on confirm password");
										Assert.fail("Failed to click on confirm password");
									}
								}
							}
							else
							{
								this.log.error("Failed to entered email ");
								Assert.fail("Failed to entered email");
							}
						}
						else
						{
							this.log.error("Failed to click checkbox");
							Assert.fail("Failed to click checkbox");
						}
					}
					else
					{
						this.log.error("Last name not Entered");
						Assert.fail("Last name not Entered");
					}
				}
				else
				{
					this.log.error("First name not entered");
					Assert.fail("First name not entered");
				}
			}
			else
			{
				this.log.error("Failed to redirect to create an account page");
				Assert.fail("Failed to redirect to create an account page");
			}
		}
		else
		{
			this.log.error("Failed to Click on create an account option");
			Assert.fail("Failed to Click on create an account option");
		}
	}
	
	public void navigateHome()
	{
		boolean lumaLogoClick=this.efa.mouseActions(".logo", 5);
		if(lumaLogoClick==true)
		{
			this.log.info("Luma Logo clicked and its successfully navigated to Home page");
			this.log.info("Test Case is Passed");
		}
		else
		{
			this.log.error("Failed to click luma logo option and failed to navigation to home page");
			this.log.error("Test case is Failed");
		}
	}
	
	@Test(dependsOnMethods = {"createAccount"}, priority = 2)
	public void signinAccount()
	{
		String signInOption=this.efa.yamlFileValidation("signinAccount", "optionClick");
		boolean signIn=this.efa.mouseActions(signInOption, 5);
		if(signIn==true)
		{
			this.log.info("Sign In option is successfully clicked");
			String pagaValSelector=this.efa.yamlFileValidation("signinAccount", "pagaValSelector");
			boolean loginPageVal=this.efa.validation(pagaValSelector, 5);
			if(loginPageVal==true)
			{
				this.log.info("Customer login page is opened after clink on the signin option");
				String userEmail= this.efa.yamlFileValidation("userCredentials", "email");
				
				boolean email=this.efa.sendKeys("#email", userEmail, "css", 5);
				if(email==true)
				{
					this.log.info("Registered email is entered successfully completed");
					String userPassword= efa.yamlFileValidation("userCredentials", "password");
					boolean password=this.efa.sendKeys(".control #pass", userPassword, "css", 5);
					if(password==true)
					{
						this.log.info("Password entered");
						String submitSelector=this.efa.yamlFileValidation("signinAccount", "submitSelector");
						boolean submitButton=this.efa.mouseClick(submitSelector, 5);
						if(submitButton==true)
						{
							this.log.info("Submit button successfully clicked");
							
							String signInAcPageSelector=this.efa.yamlFileValidation("signinAccount", "signInAcPageSelector");
							boolean signInAC=this.efa.validation(signInAcPageSelector, 5);
							if(signInAC==true)
							{
								this.log.info("My Account page is displayed. Customer successfully Sign In");
								navigateHome();
							}
							else
							{
								this.log.error("Customer Failed to Sign In his account");
							}
						}
						else
						{
							this.log.error("Failed to clicked on Submit button");
						}
					}
					else
					{
						this.log.error("Failed to enter password");
					}
				}
				else
				{
					this.log.error("Failed to Entered registered email");
				}
			}
			else
			{
				this.log.error("Failed to redirect new Customer login page");
			}
		}
		else
		{
			this.log.error("Faild to click on SignIn option");
		}
	}
	
	@Test(dependsOnMethods = {"signinAccount"}, priority = 3)
	public void lumaYogaBanner()
	{
		String bfbanMsgSelector=this.efa.yamlFileValidation("yogaBanner", "bfbanName");
		String bfBannerMsg=this.efa.getTextMsg(bfbanMsgSelector, 5);
		
		String bannerClickSelector=this.efa.yamlFileValidation("yogaBanner", "bannerClick");
		boolean lumaYoga=this.efa.mouseClick(bannerClickSelector, 5);
		if(lumaYoga==true)
		{
			this.log.info("Successfully clicked on luma yoga banner");
			
			String afbanMsgSelector=this.efa.yamlFileValidation("yogaBanner", "afbanName");
			String afBannerMsg=this.efa.getTextMsg(afbanMsgSelector, 5);
			if(bfBannerMsg!=null && afBannerMsg!=null)
			{
				if(bfBannerMsg.equalsIgnoreCase(afBannerMsg))
				{
					this.log.info("Successfully reloaded to new Luma yoga page");
					navigateHome();
				}
				else
				{
					this.log.error("Failed to reload new page");
				}
			}
			else
			{
				this.log.error("Inside Element data is null");
			}
		}
		else
		{
			this.log.error("Failed to Click on luma Yoga Banner");
		}
		
	}
	
	@Test(dependsOnMethods = {"signinAccount"}, priority = 4)
	public void lumaPants()
	{
		String bfbanMsgSelector=this.efa.yamlFileValidation("pantsBanner", "bfbanMsgSelector");
		String bfBannerMsg=this.efa.getTextMsg(bfbanMsgSelector, 5);
		
		String bannerClick=this.efa.yamlFileValidation("pantsBanner", "bannerClick");
		boolean lumaPants=this.efa.mouseClick(bannerClick, 5);
		if(lumaPants==true)
		{
			this.log.info("Successfully Clicked on the Luma Pants Banner");
			
			String afbanMsgSelector=this.efa.yamlFileValidation("pantsBanner", "afbanMsgSelector");
			String afBannerMsg=this.efa.getTextMsg(afbanMsgSelector, 5);
			if(bfBannerMsg!=null && afBannerMsg!=null)
			{
				if(bfBannerMsg.toLowerCase().contains(afBannerMsg.toLowerCase()))
				{
					this.log.info("Successfully reloaded to new luma pants page");
					navigateHome();
				}
				else
				{
					this.log.error("Failed to open new luma pant page");
				}
			}
			else
			{
				this.log.error("Inside Element data is null");
			}
		}
		else
		{
			this.log.error("Failed to clicked on luma pants banner");
		}
	}
	
	@Test(dependsOnMethods = {"signinAccount"}, priority = 5)
	public void lumaTees()
	{
		String bfbanMsgSelector=this.efa.yamlFileValidation("teesBanner", "bfbanMsgSelector");
		String bfBannerMsg=this.efa.getTextMsg(bfbanMsgSelector, 5);
		
		String bannerClick=this.efa.yamlFileValidation("teesBanner", "bannerClick");
		boolean lumaTeesBanner=this.efa.mouseClick(bannerClick, 5);
		if(lumaTeesBanner==true)
		{
			this.log.info("Successfully clicked on luma tees Banner");
			
			String afbanMsgSelector=this.efa.yamlFileValidation("teesBanner", "afbanMsgSelector");
			String afBannerMsg=this.efa.getTextMsg(afbanMsgSelector, 5);
			if(bfBannerMsg!=null && afBannerMsg!=null)
			{
				if(bfBannerMsg.toLowerCase().contains(afBannerMsg.toLowerCase()))
				{
					this.log.info("Successfully opened luma tees page");
					navigateHome();
				}
				else
				{
					this.log.error("Failed to opned new luma tees page");
				}
			}
			else
			{
				this.log.error("Inside element data in null");
			}
		}
		else
		{
			this.log.error("Failed to clicked on luma Tees Banner ");
		}
		
	}
	
	@Test(dependsOnMethods = {"signinAccount"}, priority = 6)
	public void lumaErin()
	{
		String bfbanMsgSelector=this.efa.yamlFileValidation("erinBanner", "bfbanMsgSelector");
		String bfBannerMsg=this.efa.getTextMsg(bfbanMsgSelector, 5);
		
		String bannerClick=this.efa.yamlFileValidation("erinBanner", "bannerClick");
		boolean lumaErinBanner=this.efa.mouseClick(bannerClick, 5);
		if(lumaErinBanner==true)
		{
			this.log.info("Successfully clicked on luma Erin Banner");
			
			String afbanMsgSelector=this.efa.yamlFileValidation("erinBanner", "afbanMsgSelector");
			String afBannerMsg=this.efa.getTextMsg(afbanMsgSelector, 5);
			if(bfBannerMsg!=null && afBannerMsg!=null)
			{
				if(bfBannerMsg.toLowerCase().contains(afBannerMsg.toLowerCase()))
				{
					this.log.info("Successfully opened luma Erin page");
					navigateHome();
				}
				else
				{
					this.log.error("Failed to opned new luma Erin page");
				}
			}
			else
			{
				this.log.error("Inside element data in null");
			}
		}
		else
		{
			this.log.error("Failed to clicked on luma Erin Banner ");
		}
	}

	@Test(dependsOnMethods = {"signinAccount"}, priority = 7)
	public void lumaFabric()
	{
		String bfbanMsgSelector=this.efa.yamlFileValidation("fabricBanner", "bfbanMsgSelector");
		String bfBannerMsg=this.efa.getTextMsg(bfbanMsgSelector, 5);
		
		String bannerClick=this.efa.yamlFileValidation("fabricBanner", "bannerClick");
		boolean lumaFabricBanner=this.efa.mouseClick(bannerClick, 5);
		if(lumaFabricBanner==true)
		{
			this.log.info("Successfully clicked on luma performance Fabric Banner");
			
			String afbanMsgSelector=this.efa.yamlFileValidation("fabricBanner", "afbanMsgSelector");
			String afBannerMsg=this.efa.getTextMsg(afbanMsgSelector, 5);
			if(bfBannerMsg!=null && afBannerMsg!=null)
			{
				if(bfBannerMsg.toLowerCase().contains(afBannerMsg.split(" ")[0].toString().toLowerCase()))
				{
					this.log.info("Successfully opened luma performance Fabric page");
					navigateHome();
				}
				else
				{
					this.log.error("Failed to opned new luma performance Fabric page");
				}
			}
			else
			{
				this.log.error("Inside element data in null");
			}
		}
		else
		{
			this.log.error("Failed to clicked on luma performance Fabric Banner ");
		}
	}

	@Test(dependsOnMethods = {"signinAccount"}, priority = 8)
	public void lumaEcoFriendlyBanner()
	{
		String bfbanMsgSelector=this.efa.yamlFileValidation("lumaEcoBanner", "bfbanMsgSelector");
		String bfBannerMsg=this.efa.getTextMsg(bfbanMsgSelector, 5);
		
		String bannerClick=this.efa.yamlFileValidation("lumaEcoBanner", "bannerClick");
		boolean EcoFriendlyBanner=this.efa.mouseClick(bannerClick, 5);
		if(EcoFriendlyBanner==true)
		{
			this.log.info("Successfully clicked on luma Eco-friendly Banner");
			
			String afbanMsgSelector=this.efa.yamlFileValidation("lumaEcoBanner", "afbanMsgSelector");
			String afBannerMsg=this.efa.getTextMsg(afbanMsgSelector, 5);
			if(bfBannerMsg!=null && afBannerMsg!=null)
			{
				if(bfBannerMsg.toUpperCase().contains(afBannerMsg.replace(" ", "-").toUpperCase()))
				{
					this.log.info("Successfully opened luma Eco-friendly page");
					navigateHome();
				}
				else
				{
					this.log.error("Failed to opned new luma Eco-friendly page");
				}
			}
			else
			{
				this.log.error("Inside element data in null");
			}
		}
		else
		{
			this.log.error("Failed to clicked on luma Eco-friendly Banner ");
		}
	}
	
	@Test(priority = 9)
	public void productSelection() throws InterruptedException
	{
		String productsSelector=this.efa.yamlFileValidation("productSizeClr", "productSelector");
		String prdctNameSelector=this.efa.yamlFileValidation("productSizeClr", "prdctName");
		String prdctSizesSelector=this.efa.yamlFileValidation("productSizeClr", "prdctSizes");
		String prdctClrsSelector=this.efa.yamlFileValidation("productSizeClr", "prdctClrs");
		String prdctImgSelector=this.efa.yamlFileValidation("productSizeClr", "prdctImg");
		String attribute1Selector=this.efa.yamlFileValidation("productSizeClr", "attribute1");
		String attribute2Selector=this.efa.yamlFileValidation("productSizeClr", "attribute2");
		String sizeEleXpath=this.efa.yamlFileValidation("productSizeClr", "sizeEleXpath");
		boolean prdct_size_clr_val=this.efa.products_size_clr_picking(productsSelector, prdctNameSelector, prdctSizesSelector, prdctClrsSelector, prdctImgSelector, attribute1Selector, attribute2Selector, sizeEleXpath, 5);
		if(prdct_size_clr_val==true)
		{
			String pdctsSelector=this.efa.yamlFileValidation("prdctSelect", "prdctSelected");
			String pdctNameBf_selector=this.efa.yamlFileValidation("prdctSelect", "prdctNamebf");
			String pdctPriceBf_Selector=this.efa.yamlFileValidation("prdctSelect", "prdctPricebf");
			String pdctReviewsBf_Selector=this.efa.yamlFileValidation("prdctSelect", "prdctReviewsbf");
			String nameEle_xpath=this.efa.yamlFileValidation("prdctSelect", "nameEleXpathforClick");
			String pdctNameAf_selector=this.efa.yamlFileValidation("prdctSelect", "prdctnameAf");
			String pdctPriceAf_Selector=this.efa.yamlFileValidation("prdctSelect", "prdctPriceAf");
			String pdctReviewsAf_Selector=this.efa.yamlFileValidation("prdctSelect", "prdctReviewsAf");
			String newPageSize_selectors=this.efa.yamlFileValidation("prdctSelect", "newPagePrdctSizes");
			String newPage_sizeEleXpath=this.efa.yamlFileValidation("prdctSelect", "sizeEleXpath");
			String newPagePrdctClrs=this.efa.yamlFileValidation("prdctSelect", "newPagePrdctClrs");
			String newPageClr_attribute=this.efa.yamlFileValidation("prdctSelect", "clrAttribute");
			String imgEle_selector=this.efa.yamlFileValidation("prdctSelect", "imgEle_selector");
			String imgEle_attribute=this.efa.yamlFileValidation("prdctSelect", "imgEle_attribute");
			
			this.efa.product_select(pdctsSelector, pdctNameBf_selector, pdctPriceBf_Selector, pdctReviewsBf_Selector, nameEle_xpath, pdctNameAf_selector, pdctPriceAf_Selector, pdctReviewsAf_Selector, 
					newPageSize_selectors, newPage_sizeEleXpath, newPagePrdctClrs, newPageClr_attribute, imgEle_selector, imgEle_attribute, 5);
		}
	}
	

}
