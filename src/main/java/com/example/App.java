package com.example;

import com.example.service.FileService;

public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: application requires [path] argument\n[path] example: C:\\Users\\Public\\input1.txt");
            return;
        }
        TimetableController timetableController = new TimetableController(new FileService());
        timetableController.run(args[0]);
    }
}
