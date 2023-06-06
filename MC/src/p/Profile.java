package p;

import java.io.File;
import java.util.HashMap;

public class Profile {
HashMap<String,Profile> map = new HashMap<String,Profile>();
String pname;
String fname;
String lname;
String email;
String address;
String city;
String state;
String zipcode;
String phone;
static FileEditor profile;


public Profile(String p, String f, String l, String email, String address, String city, String state, String zipcode,String phone){
	this.pname = p;
	this.fname=f;
	this.lname=l;
	this.email=email;
	this.address=address;
	this.city=city;
	this.state= state;
	this.zipcode=zipcode;
	this.phone=phone;
}


void initializeStorage(){
	
}
}
