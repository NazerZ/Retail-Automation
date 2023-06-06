package p;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class dbConnection {
static Connection c = null;
static final String dbname = "new.db";


	public static void connect() {
	    try {
	        Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:sqlite.db");
	    	 //c=DriverManager.getConnection("testdb.db");
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
}
	public static void updateDB(String table, String attributeNew,String profname,String column) throws SQLException {
	    String q = "UPDATE " + table +" set "+column+" = "+attributeNew+" where bname='"+profname+"';";
	    Statement s = c.createStatement();
	    s.execute(q);
	    
	}
	
	public static void insertBilling(String name,String first,String last,String cnum,String month,String year,String cvv) throws SQLException {
		c.close();
		dbConnection.connect();
	Statement s=c.createStatement();
	
	String q = "Insert or ignore into billing (bname,fname,lname,cnum,month,year,cvv)" +
	"Values ('"+name+"', '"+first+"', '"+last+"', '"+cnum+"','"+month+"','"+year+"','"+cvv+"');";
	s.executeUpdate(q);
	s.close();
	c.close();
	dbConnection.connect();
	Statement statement=c.createStatement();
	String update = "update billing "
			+ "SET fname= '"  + first + "', lname = '" + last +"',"
			+ "cnum = '"+cnum+"', month='"+month+"',year = '" + year+"', cvv='" + cvv+"'"
			+ " WHERE bname = '" + name+"';";
	statement.executeUpdate(update);
	
	/*
	 * 	String update = "update address "
			+ " SET fname = '"+fname+ "', lname='" + lname+"', email = '"+email + "', street='" + address +"', city = '" + city +"', state = '"+ state+"', zip ='" +zip+"', phone ='" +phone+"'"
			+ "WHERE pname='" +name+"';";
			statement.executeUpdate(update);
	 */
	}
	
	
	public static void insertAddress(String name,String fname,String lname,String email	,String address,String city, String state,String zip,String phone) throws SQLException {

		
		
	c.close();
	dbConnection.connect();
	Statement s=c.createStatement();
	
	String q = "Insert or ignore into address (pname,fname,lname,email,street,city,state,zip,phone)" +
	"Values ('"+name+"', '"+fname+"','"+lname+"','"+email+"','"+address+"','"+city+"','"+state+"','"+zip+"','"+phone+"');";
	
	s.executeUpdate(q);
	
	s.close();
	c.close();
	dbConnection.connect();
	Statement statement=c.createStatement();

	String update = "update address "
			+ " SET fname = '"+fname+ "', lname='" + lname+"', email = '"+email + "', street='" + address +"', city = '" + city +"', state = '"+ state+"', zip ='" +zip+"', phone ='" +phone+"'"
			+ "WHERE pname='" +name+"';";
	statement.executeUpdate(update);
	
	}
	public static void printDB(String tablename) throws SQLException {
		 Statement s = (c.createStatement());
	      ResultSet rs = s.executeQuery("Select * from "+tablename+";");
	      
	      while ( rs.next() ) {
	    	  
	    	  String bname=rs.getString("bname");
	    	  String cnum=rs.getString("cnum");
	    	  String month=rs.getString("month");
	    	  String year=rs.getString("year");
	    	  String cvv=rs.getString("cvv");
	    	  System.out.println("bname: " + bname);
	    	  System.out.println("cnum: " + cnum);
	    	  System.out.println("month: " + month);
	    	  System.out.println("year: " + year);
	    	  System.out.println("cvv: " + cvv);   
	      }
	}
	public static void insertTask(String site,String url, String address,String billing, String delay,String inStore) throws SQLException {
		c.close();
		dbConnection.connect();
		String d = null;
		if (delay != "") {
			d=delay;
		}
		
		//Statement s=c.createStatement();
		String q = "INSERT INTO task (site,url,billing,address,delay,inStore)" +
		"Values (?,?,?,?,?,?)";
		PreparedStatement pst=c.prepareStatement(q);
		pst.setString(1,site);
		pst.setString(2,url);
		pst.setString(3,billing);
		pst.setString(4,address);
		pst.setString(5,delay);
		pst.setString(6, inStore);
		pst.execute();
		pst.close();
		//s.execute(q);


		
	}
	
	public static void loadAddressBox(JComboBox<String> cb) throws SQLException {
		c.close();
		dbConnection.connect();
		String sql = "Select * from address";
		Statement s = c.createStatement();
		
		ResultSet rs =  s.executeQuery(sql);
		while (rs.next()){
			String profile = rs.getString("pname");
			cb.addItem(profile);
		}
	}
	public static void loadBillingBox(JComboBox<String> cb) throws SQLException {
		c.close();
		dbConnection.connect();
		String sql = "Select * from billing";
		Statement s = c.createStatement();
		
		ResultSet rs =  s.executeQuery(sql);
		while (rs.next()){
			String profile = rs.getString("bname");
			cb.addItem(profile);
		}
	}
	public static void loadSiteBox(JComboBox<String> cb) throws SQLException{
		c.close();
		dbConnection.connect();
		String sql = "Select * from website";
		Statement s = c.createStatement();
		ResultSet rs =  s.executeQuery(sql);
		while (rs.next()){
			String profile = rs.getString("website");
			cb.addItem(profile);
		}
	}
	public static Integer getLastTaskId() throws SQLException {
		c.close();
		dbConnection.connect();
		String sql = "Select MAX(taskID) as 'a'"
				
				+ " FROM task";
		Statement s = c.createStatement();
		ResultSet rs=s.executeQuery(sql);
		int a =rs.getInt("a");
		rs.close();
		c.close();
		dbConnection.connect();
		return (a);
	}
	public static void deleteTask(String id) throws SQLException {
		c.close();
		dbConnection.connect();
		String sql = "Delete from task where taskID = "+ id +";";
		Statement s = c.createStatement();
		s.execute(sql);
		
	}
	public static ResultSet fetchBilling(String billing) throws SQLException {
		c.close();
		dbConnection.connect();
		String sql="Select *"
				+ " From billing "
				+ "WHERE bname ='" +billing+"' ;";
		Statement s = c.createStatement();
		ResultSet rs =s.executeQuery(sql);
/*
  	  String bname=rs.getString("bname");
  	  
  	   use later for copy paste
  	   String first = rs.getString("fname");
  	   String last = rs.getString("lname");
		String cnum=rs.getString("cnum");
  	  String month=rs.getString("month");
  	  String year=rs.getString("year");
  	  String cvv=rs.getString("cvv");
  	  //System.out.println("bname: " + bname);
  	  System.out.println("cnum: " + cnum);
  	  System.out.println("month: " + month);
  	  System.out.println("year: " + year);
  	  System.out.println("cvv: " + cvv);   
		*/
		
		return rs;

	}
	public static ResultSet fetchAddress(String profile) throws SQLException{
		c.close();
		dbConnection.connect();
		String sql="Select * "
				+ "From address "
				+ "WHERE pname ='" +profile+"';";
		Statement s = c.createStatement();
		ResultSet rs =s.executeQuery(sql);
		

		/*String first=rs.getString("fname");
		String last=rs.getString("lname");
		String email=rs.getString("email");
		String street=rs.getString("street");
		String city = rs.getString("city");
		String 	state=rs.getString("state");
		String 	zip = rs.getString("zip");
		String phone =rs.getString("phone");
		
		System.out.println("First: " + first);
		System.out.println("Last: " + last);
		System.out.println("email: " + email);
		System.out.println("city: " + city);
		System.out.println("state: " + state);
		System.out.println("zip: " + zip);
		System.out.println("phone: " + phone);
		*/
		return rs;		
	}
    public static void fetchTasks(JTable jt) throws SQLException {
		c.close();
		dbConnection.connect();
    	
    	DefaultTableModel dt = (DefaultTableModel) jt.getModel();
    	String sql = "Select * From 'task';";
    	Statement s = c.createStatement();
    	ResultSet rs = s.executeQuery(sql);
    	ResultSetMetaData metaDt = rs.getMetaData();
    	System.out.println(metaDt);
    	System.out.println("here0");
    	
    	while(rs.next()) {
    		Vector row = new Vector();
    		row.add(rs.getString("taskID"));
    		row.add(rs.getString("site"));
    		row.add(rs.getString("url"));
    		row.add(rs.getString("address"));
    		row.add(rs.getString("billing"));
    		row.add(rs.getString("delay"));
    		row.add(rs.getString("inStore"));
    		row.add("Start");
    		row.add("Delete");
    		
    		dt.addRow(row);	
    	}
    }
    
    
    public static Vector fetchMCStore(String state) throws SQLException {
    	Vector<String> items = new Vector();
    	c.close();
    	dbConnection.connect();
    	String sql = "Select city "
    			+ "FROM microcenter "
    			+ "WHERE state ='" + state + "';";
    	Statement s = c.createStatement();
    	ResultSet rs = s.executeQuery(sql);
    	while (rs.next()) {
    		System.out.println(rs.getString("city"));
    		items.add(rs.getString("city"));
    	}
    	
    	
    	
    	return items;
    	
    	
    	
    }
    public static String fetchMCindex(String store) throws SQLException {
    	c.close();
    	dbConnection.connect();
    	String sql = "Select dex "
    			+ "FROM microcenter "
    			+ "WHERE city = '"+store+"';";
    	System.out.println(sql);
    	Statement s = c.createStatement();
    	ResultSet rs = s.executeQuery(sql);
    	String result=rs.getString("dex");
    	c.close();
    	dbConnection.connect();
    	return result;
    	
    }
    	public static void updateTask( String url,String id) throws SQLException {
        	c.close();
        	dbConnection.connect();
    	    String q = "UPDATE task set url = '"+url+"' where taskID="+Integer.parseInt(id)+";";
    	    Statement s = c.createStatement();
    	    s.execute(q);
    	    
    	
    }
    
    /*
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     *      Add task button
     *      jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            try {
				
					String url = jTextField2.getText();
					String address = (String) jComboBox2.getSelectedItem();
					String billing = (String) jComboBox3.getSelectedItem();
					String delay = jTextField1.getText();
					int d;
					try {
						if (delay.length()!=0) {
							
						
						    d= Integer.parseInt(delay);
						}
					}
						catch (NumberFormatException e)
						{

							   JFrame f= new JFrame();
							   JOptionPane.showMessageDialog(f, "delay not an integer");
						   
						}
					

					
					if(url.length() ==0) {
						JFrame frame = new JFrame();
						JOptionPane.showMessageDialog(frame,"ERROR: Empty URL");
					}
					else {
						DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
						dbConnection.insertTask(url,billing,address,delay);
						int id = dbConnection.getLastTaskId();
						
					//	String s =(String) tableModel.getValueAt(rows-1, 0) ;
						Vector row = new Vector();
						row.add(id);
						row.add(url);
						row.add(address);
						row.add(billing);
						row.add(delay);
						row.add("Start");
						row.add("Delete");
						
						tableModel.addRow(row);
						jTable1.setModel(tableModel);

					//GUI.fetchTasks(jTable1);
					}

					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  }
            });
            
                    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>());
        dbConnection.loadAddressBox(jComboBox2);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>());
        dbConnection.loadBillingBox(jComboBox3);
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
                jScrollPane1.setViewportView(jTable1);

        AbstractAction ss = new AbstractAction() {
        	public void actionPerformed(ActionEvent e) {

                int row = Integer.valueOf( e.getActionCommand() );
                int column = jTable1.getSelectedColumn();
                jTable1.setValueAt("Stop",row,column);

                int col = jTable1.getSelectedColumn();
                System.out.println("working"+col);
        	}
        };
        AbstractAction delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
            
            	JTable table =(JTable)e.getSource();
            	
                int modelRow = Integer.valueOf( e.getActionCommand() );
                int column = table.getSelectedColumn();
                
                System.out.println(modelRow);
                
                ((DefaultTableModel)table.getModel()).removeRow(modelRow);
                
                
                System.out.println("Row:" + modelRow + "    "+ column);
                System.out.println("Working");
            }
        };
        ButtonColumn buttonColumn1 = new ButtonColumn(jTable1, delete, 6);
        ButtonColumn buttonColumn = new ButtonColumn(jTable1, ss, 5);
     */
	
	
	
}
