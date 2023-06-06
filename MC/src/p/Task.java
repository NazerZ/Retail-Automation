package p;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task implements Runnable{
	static Map <String,Task> tasksRunning = new HashMap<String,Task>();
	static int count=0;
	
	int index = 0;
	
	String taskid;
	String storeIndex;
	String store;
	String url;
	String first;
	String last;
	String email;
	String street;
	String city;
	String state;
	String zip;
	String phone;
	String cardnum;
	String cFirst;
	String cLast;
	String mExp;
	String yExp;
	String cvv;
	String siteTitle;
	ChromeDriver cd;
	Thread t;
	int row;
	
	public Task() {
	
	}
	public Task(String url, String address,String  billing,String id,String store,int row) throws SQLException {
		this.row = row;
		this.store = store;
		this.taskid=id;
		System.out.println(taskid);
		System.out.println(url);
		System.out.println("address:" +address);
		System.out.println("billing"+billing);
		ResultSet a =dbConnection.fetchAddress(address);



System.out.println(billing);
		this.url = url;
		//this.checkURL(url);
		
		System.out.println("TASK CONSTRUCTOR");
		first=a.getString("fname");
		last=a.getString("lname");
		email=a.getString("email");
		street=a.getString("street");
		city = a.getString("city");
		state=a.getString("state");
		zip = a.getString("zip");
		phone =a.getString("phone");
		a.close();
		ResultSet b =dbConnection.fetchBilling(billing);
		cFirst = b.getString("fname");
		cLast= b.getString("lname");
		cardnum= b.getString("cnum");
		mExp = b.getString("month");
		yExp = b.getString("year");
		cvv = b.getString("cvv");
		b.close();
		t=new Thread(this,taskid);
		System.out.println(store);
		
		if (store != null) {
		//storeIndex = dbConnection.fetchMCindex(store);
		storeIndex = "029";
		}
	/*	
		System.out.println(first);
		System.out.println(last);
		System.out.println(email);
		System.out.println(street);
		System.out.println(city);
		System.out.println(state);
		System.out.println(zip);
		System.out.println(phone);
		System.out.println(cardnum);
		System.out.println(mExp);
		System.out.println(yExp);
		System.out.println(cvv);
		
		*/
		tasksRunning.put(taskid,this);
		count ++;

		t.start();
	}

	public  static String checkURL(String url,String site) {
		
String ending;


		if (url.startsWith("https://") || url.startsWith("http://")) {
			System.out.println("trigger 1");

		}
		else {
			url = "https://"+url ;
		}

		
		if (site == "Microcenter") {
			
			if(url.toLowerCase().contains("microcenter.com/product")) {
				if (!url.toLowerCase().contains("?storeid=")) {
					//url = url +?storeid=
				}
			}
		}
		

		return url;
		

		/*ChromeDriver chrome = new ChromeDriver();
		chrome.get(url);
		String title = chrome.getTitle();
		System.out.println(title);
			if (title.toLowerCase().contains("error") || title.toLowerCase().contains("not found".toLowerCase())  ) {
				System.out.println("title");
// alert user when page is not found 
				
				JOptionPane.showMessageDialog(new JFrame(), "Page not found");
			return false;
		}
		*/
		
	}
	
	public void mcLaunch() throws SQLException, InterruptedException {
        //ChromeOptions options = new ChromeOptions();
       //options.addArguments("--headless");    
		//cd = new ChromeDriver(options);
		cd = new ChromeDriver();
		cd.get(url);
		
		if (!url.contains("?storeid=")) {
			
			if (store==null) {
				WebDriverWait wait = new WebDriverWait(cd,5);
				WebElement c =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"storeInfoChange\"]/div/ul/li[26]/a")));
				c.click();
				url=cd.getCurrentUrl();
				//now update landing page in database
				dbConnection.updateTask(url,taskid);
			}

			else{
				Thread.sleep(300);
				String index = dbConnection.fetchMCindex(store);
				WebDriverWait wait = new WebDriverWait(cd,5);
				WebElement c =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"storeInfoChange\"]/div/ul/li["+storeIndex+"]/a")));
				c.click();
				System.out.println(index);
				//*[@id="storeInfoChange"]/div/ul/li[26]/a
				url=cd.getCurrentUrl();
				System.out.println(url);
				dbConnection.updateTask(url,taskid);
			}
		}
		GUI.jTable1.setValueAt(cd.getTitle(),row, GUI.procCol);
		mcMonitor();
		
		
		
	}
	public void mcMonitor() throws InterruptedException {
		
		// look for atc button
		
		while(true) {
			List<WebElement> atc = cd.findElements(By.xpath("//*[@id=\"options-button\"]/form/input[8]"));
			
			//monitors until button appears
		int x = 11000;	
		if(atc.size()==0) {
			// if button does not exist, refresh the page in x milliseconds
			// sleep then refresh the url
			cd.get(url);
		}
		
		else {
			// if button exists, grab the button from the list and click it
				//mcATC(atc.get(0));'

			

			try{
				WebElement c=cd.findElementByXPath("//*[@id=\"options-button\"]/form/input[8]");
				c.click();
				}
			
			catch(ElementClickInterceptedException e) {
				Thread.sleep(300);
				System.out.println("CAUGHT");
			}
			
//"//*[@id=\"options-button\"]/form/input[8]"
			

				GUI.jTable1.setValueAt("Adding to cart", row, GUI.procCol);
				
				break;
				
			}
		}
	}
	public void mcATC(WebElement atc) {
		atc.click();
	}
	public void mcCheckout() throws InterruptedException {
		cd.get("https://cart.microcenter.com/cart.aspx");
		WebElement shipOption = cd.findElementByXPath("//*[@id=\"ContentPlaceHolder1_ucOrderSummary_cbxShippingOption\"]");
		Select s = new Select(shipOption);
		s.selectByIndex(0);
		
		
		cd.get("https://checkout.microcenter.com/checkout.aspx");
		WebElement first=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtFirstName\"]");
		WebElement last=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtLastName\"]");
		WebElement email=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtEmailAddress\"]");
		WebElement address=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtBillAdd1\"]");
		WebElement city=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtBillCity\"]");
		WebElement zipcode=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtBillZipCode\"]");
		WebElement phone=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtPhoneNumber\"]");
		WebElement state = cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_cbxBillState\"]");  
		Select st = new Select(state);
		st.selectByVisibleText("New York");
		
		first.sendKeys(this.first);
		Thread.sleep(200);
		last.sendKeys(this.last);
		Thread.sleep(200);
		email.sendKeys(this.email);
		Thread.sleep(200);
		address.sendKeys(this.street);
		Thread.sleep(200);
		city.sendKeys(this.city);
		Thread.sleep(200);
		zipcode.sendKeys(this.zip);
		Thread.sleep(200);
		phone.click();
		phone.sendKeys(this.phone);
		
		Thread.sleep(200);
		
		cd.findElementByXPath("//*[@id=\"ucCheckOut_btnContinue\"]").click();
		WebDriverWait wait = new WebDriverWait(cd,3);
		WebElement c =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ucCheckOut_lbUseUserAddress\"]")));
		c.click();
		
		Thread.sleep(1500);
		WebElement card = cd.findElementByXPath("//*[@id=\"ucCheckOut_txtCCNumber\"]");
		////*[@id="ucCheckOut_txtCCNumber"]
		WebElement month = cd.findElementByXPath("//*[@id=\"ucCheckOut_cbxMonth\"]");
		WebElement year = cd.findElementByXPath("//*[@id=\"ucCheckOut_cbxYear\"]");
		WebElement cvv = cd.findElementByXPath("//*[@id=\"ucCheckOut_txtCCCsv\"]");
		
		card.sendKeys(this.cardnum);
		Select m = new Select(month);
		m.selectByVisibleText(this.mExp);
		Select y = new Select(year);
		y.selectByVisibleText(this.yExp);
		cvv.sendKeys(this.cvv);
		cd.findElementByXPath("//*[@id=\"lbSubmitOrderSecondary\"]").click();
	}

	@Override
	public void run() {
		System.out.println("running...");
		//if == microcenter
		try {
			mcLaunch();
			mcCheckout();
			// now that page is launched attempt to add to cart
		} catch (SQLException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
	}
	
public void end() {
	cd.close();
	System.out.println("Thread stopped");
	t.stop();
	tasksRunning.remove(taskid);
	return;
}
	
	
	


	
	
	
}
