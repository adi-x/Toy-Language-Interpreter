package app;

import app.controller.Controller;
import app.view.Interpreter;
import app.view.View;

public class Main {

    public static void main(String[] args) {
		try {
		    Interpreter.main(args);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}
