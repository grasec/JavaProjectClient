package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Note> NOTES;

    public static void main(String[] args) {
        ServerThread serverThread = new ServerThread();
        Scanner scanner = new Scanner(System.in);
        serverThread.doAction(ServerThread.ACTION_GET_NOTES);
        NOTES = serverThread.getNotes();
        printNotes(NOTES);
        while (true) {
            System.out.println("choose an action : \n" +
                    "load \n" +
                    "save \n" +
                    "add \n" +
                    "remove \n");

            String input = scanner.nextLine();
            switch (input) {
                case "load":
                    serverThread.doAction(ServerThread.ACTION_GET_NOTES);
                    NOTES = serverThread.getNotes();
                    break;
                case "save":
                    serverThread.setNotes(NOTES);
                    serverThread.doAction(ServerThread.ACTION_SEND_NOTES);
                    break;

                case "add":
                    System.out.println("Enter you note:");
                    String note = scanner.nextLine();
                    NOTES.add(new Note(note));
                    break;
                case "remove":
                    System.out.println("Which note to remove?");
                    int index = scanner.nextInt();
                    NOTES.remove(index);
                    break;
                case "print":
                    printNotes(NOTES);
                    break;
            }
        }
    }

    private static void printNotes(ArrayList<Note> notes) {
        for (int i = 0; i < notes.size(); i++) {
            System.out.println(i + ": " + notes.get(i));
        }
    }
}
