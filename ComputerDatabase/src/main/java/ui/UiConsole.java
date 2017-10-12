package ui;

import java.util.Scanner;

import model.Company;
import model.Computer;
import model.ComputerPreview;

public class UiConsole {

    private Scanner scanner;

    /**
     * ctor, used to init System.in scanner for futur user input.
     */
    public UiConsole() {
        scanner = new Scanner(System.in);
    }

    /**
     * close completely standard input (System.in) and close input scanner.
     */
    public void destroy() {
        if (scanner == null) {
            return;
        }
        scanner.close();
        scanner = null;
    }

    /**
     * @return wait until the user typed something in console and return a complete line
     */
    public String getInput() {
        System.out.println("> Read input (press enter) : ");
        return getLine();
    }

    public String getLine() {
        return scanner.nextLine();
    }

    /**
     * @param m company to display
     */
    public void write(Company m) {
        System.out.println(m.toString());
    }

    /**
     * @param c computer to display
     */
    public void write(Computer c) {
        System.out.println(c.toString());
    }

    /**
     * @param c computer preview to display
     */
    public void write(ComputerPreview c) {
        System.out.println(c.toString());
    }

    /**
     * @param e exception to write with its stacktrace
     */
    public void write(Exception e) {
        System.out.println(e.getMessage());
        write(e.getStackTrace());
    }

    /**
     * @param st stack trace to write
     */
    public void write(StackTraceElement[] st) {
        for (StackTraceElement elem : st) {
            System.out.println(elem.toString());
        }
    }

    /**
     * @param msg common string to display as is
     */
    public void write(String msg) {
        System.out.println(msg);
    }

    /**
     * @param e exception to write before closing completly
     */
    public void writeFatal(Exception e) {
        System.out.println("Critical Error, the program will stop : " + e.getMessage());
        write(e.getStackTrace());
    }
}
