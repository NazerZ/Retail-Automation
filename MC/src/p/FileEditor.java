package p;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileEditor {

	File file;
	Scanner scanner;
	FileWriter fw;
	PrintWriter pw;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Scanner getScanner() {
		return scanner;
	}
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	public FileWriter getFw() {
		return fw;
	}
	public void setFw(FileWriter fw) {
		this.fw = fw;
	}
	public PrintWriter getPw() {
		return pw;
	}
	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}
	
	
	public FileEditor(File file) throws IOException {

		this.file= file;
		this.scanner=new Scanner(file);
		this.fw=new FileWriter(file,true);
		this.pw = new PrintWriter(fw);
	}
	public FileEditor(String file) throws IOException {

		this.file= new File(file);
		this.scanner=new Scanner(this.file);
		this.fw=new FileWriter(file,true);
		this.pw = new PrintWriter(fw);
	}
	public void printAll() {
		while (scanner.hasNextLine()){
			System.out.println(scanner.nextLine());
		}
	}
	public void saveToOutput(String text) {
		pw.println(text);
	}
}
