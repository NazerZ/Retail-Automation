package p;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class main {
	static long start;
	public static void main(String args[]) throws Exception{
		/*

	        /* Set the Nimbus look and feel */
	        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
	        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
	         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	         */
	       
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		

        //</editor-fold>

        /* Create and display the form */

        

		
	        dbConnection.connect();
	        
	        dbConnection.fetchBilling("1234");
	       // dbConnection.fetchAddress("pr");
	        
	    //	GUI.fetchTable("billing");
	    //	dbConnection.printDB("billing");
	    
	        
	        System.out.println(dbConnection.getLastTaskId());
	        
	          GUI g = new GUI();
	         
	        g.setVisible(true);
	        dbConnection.fetchTasks(g.jTable1);
	    	
	  
	        
	        
	
		// Xpath = /tagname [@Attribute = Value] 
		System.setProperty("webdriver.chrome.driver",".\\d\\chromedriver.exe");
		String url = "https://www.microcenter.com/product/627519/dell-s2721qs-27-4k-uhd-(3840-x-2160)-60hz-led-monitor";
		//String url = "https://www.pokemoncenter.com/product/290-80817-75727cb2/pokemon-tcg-shining-fates-elite-trainer-box";
		start = System.currentTimeMillis();
		ChromeDriver cd = new ChromeDriver();
		cd.get(url);
		//page(url,cd);
		long finish = System.currentTimeMillis();
		System.out.println(finish - start);
		
	        
	        /*
		dbConnection.connect();
		dbConnection.printDB("billing");
		
	        ChromeDriver cd = new ChromeDriver();
	        cd.get("https://www.microcenter.com/product/");
	        System.out.println(cd.getTitle());
	        */
	}

	
	public static void page(String url,ChromeDriver cd) throws InterruptedException {
	// load page
	// if atc does not exist refresh
	// else atc and checkout
		cd.get(url);
		while(true) {
			cd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(4000);
			List<WebElement> atc = cd.findElements(By.xpath("//*[@id=\"options-button\"]/form/input[8]"));
			if(atc.size()==0) {
				Thread.sleep(11000);
				cd.get(url);
			}
			
			else {
					atc(atc.get(0),cd);
					System.out.println("Successful Checkout!");
					break;
					
				}
			}
		System.out.println("Successful Checkout!");
		
		}
		
		
		
	


	public static void atc(WebElement atcButton, ChromeDriver cd) throws InterruptedException{
		atcButton.click();
		shippingOption(cd);
		checkout(cd);
	}
	
	public static void checkout (ChromeDriver cd) throws InterruptedException {
		cd.get("https://checkout.microcenter.com/checkout.aspx");
		WebElement first=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtFirstName\"]");
		WebElement last=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtLastName\"]");
		WebElement email=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtEmailAddress\"]");
		WebElement address=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtBillAdd1\"]");
		WebElement city=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtBillCity\"]");
		WebElement zipcode=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtBillZipCode\"]");
		WebElement phone=cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_txtPhoneNumber\"]");
		WebElement state = cd.findElementByXPath("//*[@id=\"ucCheckOut_rlvBillingAddress_ctrl0_cbxBillState\"]");  
		Select s = new Select(state);
		s.selectByVisibleText("New York");
		Thread.sleep(200);
		first.sendKeys("Naz");
		Thread.sleep(200);
		last.sendKeys("zahir");
		Thread.sleep(200);
		email.sendKeys("abc123@gmail.com");
		Thread.sleep(200);
		address.sendKeys("34th street");
		Thread.sleep(200);
		city.sendKeys("Bellerose");
		Thread.sleep(200);
		zipcode.sendKeys("11426");
		Thread.sleep(200);
		phone.click();
		phone.sendKeys("1234560000");
		Thread.sleep(200);
		
		cd.findElementByXPath("//*[@id=\"ucCheckOut_btnContinue\"]").click();
		WebDriverWait wait = new WebDriverWait(cd,3);
		WebElement c =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ucCheckOut_lbUseUserAddress\"]")));
		c.click();
		
		Thread.sleep(1500);
		WebElement card = cd.findElementByXPath("//*[@id=\"ucCheckOut_txtCCNumber\"]");
		WebElement month = cd.findElementByXPath("//*[@id=\"ucCheckOut_cbxMonth\"]");
		WebElement year = cd.findElementByXPath("//*[@id=\"ucCheckOut_cbxYear\"]");
		WebElement cvv = cd.findElementByXPath("//*[@id=\"ucCheckOut_txtCCCsv\"]");
		card.sendKeys("123400006789");
		Select m = new Select(month);
		m.selectByVisibleText("03");
		Select y = new Select(year);
		y.selectByVisibleText("2025");
		cvv.sendKeys("123");
		cd.findElementByXPath("//*[@id=\"lbSubmitOrderSecondary\"]").click();
		
	}
	public static void shippingOption(ChromeDriver cd) {
		cd.get("https://cart.microcenter.com/cart.aspx");
		WebElement shipOption = cd.findElementByXPath("//*[@id=\"ContentPlaceHolder1_ucOrderSummary_cbxShippingOption\"]");
		Select s = new Select(shipOption);
		s.selectByIndex(0);
		
			
		}
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
                
                

                String pname = jTextField14.getText();
                String first = jTextField15.getText();
                String last = jTextField16.getText();
                String email = jTextField17.getText();
                String address = jTextField18.getText();
                String city = jTextField19.getText();
                String state = jTextField20.getText();
                String zip = jTextField21.getText();
                String phone = jTextField22.getText();
                
                System.out.println("pname:"+pname);
                System.out.println("first:"+first);
                System.out.println("last:"+last);
                System.out.println("email:"+email);
                System.out.println("address:"+address);
                System.out.println("city:"+city);
                System.out.println("address:"+address);
                System.out.println("state:"+state);
                System.out.println("zip:"+zip);
                System.out.println("phone:"+phone);
                try {
					if(pname.length()==0 || first.length()==0 || last.length()==0|| email.length()==0 || address.length()==0 || state.length() ==0 || city.length() ==0 || zip.length() ==0 || phone.length()==0) {
						JFrame f = new JFrame();
						   JOptionPane.showMessageDialog(f,  "fill out all fields");
					}
					else {
						
					
					dbConnection.insertAddress( pname, first, last, email, address,city,state,zip,phone);

					System.out.println("added");
					}
				} catch (SQLException e) {
					JFrame f = new JFrame();
					   JOptionPane.showMessageDialog(f,  "profile names must be unique");
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
                jButton3ActionPerformed(evt);
            }
        });

        jLabel29.setText("CVV");

        jTextField27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField27ActionPerformed(evt);
            }
        });
        
        
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
                String bname = jTextField23.getText();
                String cnum = jTextField24.getText();
                String month = jTextField25.getText();
                String year = jTextField26.getText();
                String cvv = jTextField27.getText();
                try {
					if(bname.length()==0 || cnum.length()==0 || month.length()==0|| year.length()==0 || cvv.length()==0) {
						JFrame f = new JFrame();
						   JOptionPane.showMessageDialog(f,  "fill out all fields");
					}
					else {
						
					
					dbConnection.insertBilling( bname, cnum, month, year, cvv);

					System.out.println("added");
					}
				} catch (SQLException e) {
					JFrame f = new JFrame();
					   JOptionPane.showMessageDialog(f,  "profile names must be unique");
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
                
                
                
            }
        });
	          String[] states = new String[]{
                "Alabama", "Alaska", "Arizona", "Arkansas", "California",
                "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
                "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
                "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts",
                "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
                "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
                "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma",
                "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
                "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia",
                "Wisconsin", "Wyoming"
            };
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(states));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
	 *
	 */
		

	
		
	
}
