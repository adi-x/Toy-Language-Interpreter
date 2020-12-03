package app.view;

import app.controller.Controller;

public class RunExample extends Command {
    private Controller service;
    public RunExample(String key, String desc, Controller service) {
        super(key, desc);
        this.service = service;
    }
    public void execute(){
        try {
            service.run();
        } catch (Exception error) {

        }
    }
}
