package ui;

import java.util.Scanner;

import model.CompanyModel;
import model.ComputerModel;
import model.ComputerModelPreview;

public class UiConsole {
				
	private Scanner scanner;

	public UiConsole() {
		scanner = new Scanner(System.in);
	}
	
	public void write(String msg)
	{
		System.out.println(msg);
	}
	
	public void write(ComputerModel c)
	{
		System.out.println(c.toString());
	}
	
	public void write(CompanyModel m)
	{
		System.out.println(m.toString());
	}
	
	public String getInput()
	{
		System.out.println("Read input (press enter) : ");
		return scanner.nextLine();
	}

	public void write(ComputerModelPreview c) {
		System.out.println(c.toString());
	}
	
	public void write(Exception e)
	{
		System.out.println(e.getMessage());
		System.out.println(e.getStackTrace());
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
