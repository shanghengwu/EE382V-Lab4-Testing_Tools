//package org.openqa.selenium.example;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.*;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import junit.framework.TestCase;

public class Example  extends TestCase{
    public class User{
        private String username;
        private String password;
        
        User(String username, String password){
            this.username = username;
            this.password = password;
        }
    }
    
    protected static ArrayList<User> USERS;
    protected static ArrayList<User> CASE_USERS;
    protected static ArrayList<User> CASE_PASSWORDS;
    protected static ArrayList<String> TEMP_IN_FARENHEIT;
    
    public static void main(String[] args) {
        
        String[] testCaseName = { Example.class.getName() };
        // junit.swingui.TestRunner.main(testCaseName);
        junit.textui.TestRunner.main(testCaseName);
        
    }
    
    @Before
     //public void createUserList(){
     public void setUp(){
        USERS = new ArrayList<User>();
        USERS.add(new User("andy", "apple"));
        USERS.add(new User("bob", "bathtub"));
        USERS.add(new User("charley", "china"));
        
        CASE_USERS = new ArrayList<User>();
        CASE_USERS.add(new User("Andy", "apple"));
        CASE_USERS.add(new User("BoB", "bathtub"));
        CASE_USERS.add(new User("    charley   ", "   china   "));
        
        CASE_PASSWORDS = new ArrayList<User>();
        CASE_PASSWORDS.add(new User("andy", "apPLE"));
        CASE_PASSWORDS.add(new User("bob", "baTHTub"));
        CASE_PASSWORDS.add(new User("charley", "cHINa"));
        
        TEMP_IN_FARENHEIT = new ArrayList();
        TEMP_IN_FARENHEIT.add("0");
        TEMP_IN_FARENHEIT.add("50");
        TEMP_IN_FARENHEIT.add("100");
        TEMP_IN_FARENHEIT.add("150");
        TEMP_IN_FARENHEIT.add("200");
        TEMP_IN_FARENHEIT.add("250");
        TEMP_IN_FARENHEIT.add("300");
        TEMP_IN_FARENHEIT.add("350");
        TEMP_IN_FARENHEIT.add("400");
    }

    @Test
    public void testValidUsers() throws InterruptedException{
        WebDriver driver = new HtmlUnitDriver();
        Thread.sleep(10000);
        for(User user : USERS){
            driver.get("http://apt-public.appspot.com/testing-lab-login.html");
            WebElement userNameField = driver.findElement(By.name("userId"));
            userNameField.clear();
            userNameField.sendKeys(user.username);
            WebElement passwordField = driver.findElement(By.name("userPassword"));
            passwordField.clear();
            passwordField.sendKeys(user.password);
            System.out.println("[Info Entered] username/password: " + user.username + "/" + user.password);
            passwordField.submit();
            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                System.out.println("Exception happens...");
            }
            
            assertTrue(driver.getTitle().contains("Online temperature conversion"));
        }
        System.out.println("All Valid users passed!");
    }

    @Test
    public void testUsernameCaseValidUsers() throws InterruptedException{
        WebDriver driver = new HtmlUnitDriver();
        Thread.sleep(10000);
        for(User user : CASE_USERS){
            driver.get("http://apt-public.appspot.com/testing-lab-login.html");
            WebElement userNameField = driver.findElement(By.name("userId"));
            userNameField.clear();
            userNameField.sendKeys(user.username);
            WebElement passwordField = driver.findElement(By.name("userPassword"));
            passwordField.clear();
            passwordField.sendKeys(user.password);
            System.out.println("[Info Entered] username/password: " + user.username + "/" + user.password);
            passwordField.submit();

            Thread.sleep(500);

            //System.out.println("Title: "+driver.getTitle());

            assertTrue(driver.getTitle().contains("Online temperature conversion"));
        
        }
        System.out.println("All Case Valid users passed!");
    }
    
    @Test
    public void testPasswordCaseValidUsers() throws InterruptedException{
        WebDriver driver = new HtmlUnitDriver();
        Thread.sleep(10000);
        for(User user : CASE_PASSWORDS){
            driver.get("http://apt-public.appspot.com/testing-lab-login.html");
            WebElement userNameField = driver.findElement(By.name("userId"));
            userNameField.clear();
            userNameField.sendKeys(user.username);
            WebElement passwordField = driver.findElement(By.name("userPassword"));
            passwordField.clear();
            passwordField.sendKeys(user.password);
            System.out.println("[Info Entered] username/password: " + user.username + "/" + user.password);
            passwordField.submit();

            Thread.sleep(500);

            //System.out.println("Title: "+driver.getTitle());

            assertFalse(driver.getTitle().contains("Online temperature conversion"));
        
        }
        System.out.println("All Case Valid users passed!");
    }

    @Test
    public void testPrecision() throws InterruptedException{
        WebDriver driver = new HtmlUnitDriver();
        Thread.sleep(10000);
        for(String temp : TEMP_IN_FARENHEIT){
            driver.get("http://apt-public.appspot.com/testing-lab-calculator.html");
            WebElement element = driver.findElement(By.name("farenheitTemperature"));
            element.clear();
            element.sendKeys(temp);
            element.submit();
            
            //driver.get()
            Thread.sleep(500);
            List<WebElement>  lines = driver.findElements(By.xpath("//*[contains(text(), 'Celsius')]"));
            //String[] lines = driver.getPageSource().contains("Celsius");
            String test = driver.findElement(By.cssSelector("h2")).getText();
            //System.out.println(test);
            
            String[] array = test.split(" ");
            double result = Double.parseDouble(array[3]);
            double temp_in_f = Double.parseDouble(temp);
            double temp_in_c = (temp_in_f-32)*5/9;
            
            DecimalFormat df;
            if (temp_in_f>= 0 && temp_in_f<=212)
                df = new DecimalFormat("#.##");
            else
                df = new DecimalFormat("#.#");
            
            System.out.println("Expected/Result: "+df.format(temp_in_c)+"/"+df.format(result));
            assertEquals(df.format(temp_in_c),df.format(result));
            //System.out.println(df.format(temp_in_c));
            
        }
        System.out.println("All Case Valid users passed!");
    }
}