package ui;

import java.util.Scanner;

import model.Company;
import model.Computer;
import model.ComputerPreview;

public class UiConsole {
				
	private Scanner scanner;

	public UiConsole() {
		scanner = new Scanner(System.in);
	}
	
	public void write(String msg)
	{
		System.out.println(msg);
	}
	
	public void write(Computer c)
	{
		System.out.println(c.toString());
	}
	
	public void write(Company m)
	{
		System.out.println(m.toString());
	}
	
	public String getInput()
	{
		System.out.println("> Read input (press enter) : ");
		return scanner.nextLine();
	}

	public void write(ComputerPreview c) {
		System.out.println(c.toString());
	}
	
	public void write(Exception e)
	{
		System.out.println(e.getMessage());
		write(e.getStackTrace());
	}
	
	public void writeFatal(Exception e) {
		System.out.println("Critical Error, the program will stop : " + e.getMessage());
		write(e.getStackTrace());
	}
	
	public void write(StackTraceElement[] st)
	{
		for (StackTraceElement elem : st) {
			System.out.println(elem.toString());
		}
	}

	public void destroy() 
	{
		if (scanner == null)
		{
			return;
		}
		scanner.close();
		scanner = null;
	}

}
