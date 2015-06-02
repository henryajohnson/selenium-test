package automationFramework;
 
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;


 
/**
 * @author henry.johnson
 *
 */
public class FirstTestCase {
	private static  String[] names2 = {"fname", "lname", "address", "city", "state", "zip", "email", "email_confirm", "specialty", "of_age"};
	private static String[] names;
    private static String csv = "/Users/henry.johnson/Documents/workspace/newtest/test-case-1.csv";
    private static String thisDirectory = "/Users/henry.johnson/Documents/workspace/newtest/";
    private static String targetUrl ="";

	/**
	 * @param mydriver
	 */
	private static void runTest(WebDriver mydriver){
		mydriver.findElement(By.className("yes")).click();
        mydriver.findElement(By.id("submit")).click();
        for(int i=0; i<names2.length; i++){
        	String classes = mydriver.findElement(By.name(names2[i])).findElement(By.xpath("..")).getAttribute("class");
    		if(Arrays.asList(classes.split(" ")).contains("red")){
    			System.out.println("-- Test worked on " + names2[i]);
    		}
        }
        
        mydriver.findElement(By.name("fname")).sendKeys("Henry");
        mydriver.findElement(By.name("lname")).sendKeys("Johnson");
        mydriver.findElement(By.name("address")).sendKeys("622 Third Avenue");
        mydriver.findElement(By.name("city")).sendKeys("New York");
        mydriver.findElement(By.name("zip")).sendKeys("10017");
        mydriver.findElement(By.name("email")).sendKeys("HenryAJohnson@gmail.com");
        mydriver.findElement(By.name("email_confirm")).sendKeys("HenryAJohnson@gmail.com");
        
        mydriver.findElement(By.id("submit")).click();
        for(int i=0; i<names2.length; i++){
        	String classes = mydriver.findElement(By.name(names2[i])).findElement(By.xpath("..")).getAttribute("class");
    		if(Arrays.asList(classes.split(" ")).contains("red")){
    			System.out.println("-- Test worked on " + names2[i]);
    		}
        }
	}
	/**
	 * @param mydriver
	 * @param inputs
	 * @param browser
	 * @throws IOException
	 */
	private static void runTestWithInputs(WebDriver mydriver, String inputs[], String browser) throws IOException{

        for(int i=1; i<names.length && i<inputs.length; i++){
        	if(!inputs[i].equals("")){
	        	if(mydriver.findElement(By.name(names[i])).findElement(By.xpath("..")).getAttribute("class").contains("dropdown")){
	        		mydriver.findElement(By.name(names[i])).findElement(By.xpath("..")).click();
	        		mydriver.findElement(By.xpath("//li[contains(text(), '"+inputs[i]+"')]")).click();
	        	}else{
	        		mydriver.findElement(By.name("signup")).findElement(By.name(names[i])).sendKeys(inputs[i]);
	        	}
        	}
        		
        }
        
        mydriver.findElement(By.name("signup")).findElement(By.id("submit")).click();
        String errors = "";
        for(int i=1; i<names.length; i++){
        	String classes = mydriver.findElement(By.name(names[i])).findElement(By.xpath("..")).getAttribute("class");
        	if(Arrays.asList(classes.split(" ")).contains("dropdown")){
        		classes = mydriver.findElement(By.name(names[i])).findElement(By.xpath("..")).findElement(By.xpath("..")).getAttribute("class");
        	}
        	if(Arrays.asList(classes.split(" ")).contains("red")){
    			errors+=names[i];
    			errors+=" ";
    		}
        }
        if(errors == ""){
        	System.out.println("        Test passed");
        }else{
        	System.out.println("        System failed at " + errors);
        }
        //Screenshots!
        if(browser != "HTMLUnit"){
	        File scrFile = ((TakesScreenshot)mydriver).getScreenshotAs(OutputType.FILE);
		    org.apache.commons.io.FileUtils.copyFile(scrFile, new File(thisDirectory+"screenshots/"+browser+"/"+inputs[0]+".jpg"));
        }
	}

	public static void runcsvTest(WebDriver mydriver, String drivername){
		BufferedReader br = null;
		String line;
		File thisdir = new File(thisDirectory+"screenshots/"+drivername);
		try {
			if(!thisdir.exists() && drivername != "HTMLUnit"){
				thisdir.mkdir();
			}
			System.out.println("Testing " + drivername);
			mydriver.findElement(By.className("yes")).click();
			br = new BufferedReader(new FileReader(csv));
			line = br.readLine();
			names = line.split(",");
			
			while ((line = br.readLine()) != null) {
				
			    // use comma as separator
				String[] inputs = line.split(",");
				System.out.println("    Test case : " + inputs[0]);
				runTestWithInputs(mydriver, inputs, drivername);
				
				mydriver.get(targetUrl);
				try {
			       Thread.currentThread().sleep(1000);
			       }
			     catch (InterruptedException e) {
			       e.printStackTrace();
			       }
				mydriver.findElement(By.className("yes")).click();
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
	}
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
 
        // Create a new instance of the Firefox driver
 
    	System.setProperty("webdriver.chrome.driver", "/Users/henry.johnson/Documents/workspace/chromedriver");
        
    	
        WebDriver driver = new FirefoxDriver();
    	WebDriver driver_two = new ChromeDriver();
    	HtmlUnitDriver driver_three = new HtmlUnitDriver(true);
    	java.util.logging.Logger.getLogger("org.openqa.selenium.safari").setLevel(java.util.logging.Level.OFF);
    	WebDriver driver_four = new SafariDriver();
    	
    	java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.openqa.selenium.safari").setLevel(java.util.logging.Level.OFF);
    	
 
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver_two.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver_three.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver_four.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Launch the Online Store Website
 
        driver.get(targetUrl);
        driver_two.get(targetUrl);
        driver_three.get(targetUrl);
        driver_four.get(targetUrl);
        
        String sTitle = driver_two.getTitle();
        String sURL = driver_two.getCurrentUrl();
        System.out.println("We are currently at the " + sTitle + " with the url: " + sURL);
        
       
        
        
        runcsvTest(driver, "Firefox");
        runcsvTest(driver_two, "Chrome");
        runcsvTest(driver_three, "HTMLUnit");
        runcsvTest(driver_four, "Safari");
        
        
        // Close the driver
 
       driver.quit();
       driver_two.quit();
       driver_three.quit();
       driver_four.quit();
 
     }
 
    
}
