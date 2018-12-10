package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Note> noteArrayList = new ArrayList<>();

    public static void main(String[] args) {
        ServerThread serverThread = new ServerThread();
        Scanner scanner = new Scanner(System.in);

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
                    noteArrayList = serverThread.getNotes();
                    printNotes(noteArrayList);
                    break;
                case "save":
                    serverThread.setNotes(noteArrayList);
                    serverThread.doAction(ServerThread.ACTION_SEND_NOTES);
                    printNotes(noteArrayList);
                    break;

                case "add":
                    System.out.println("Enter you note:");
                    String note = scanner.nextLine();
                    noteArrayList.add(new Note(note));
                    printNotes(noteArrayList);
                    break;
                case "remove":
                    System.out.println("Which note to remove?");
                    int index = scanner.nextInt();
                    noteArrayList.remove(index-1);
                    printNotes(noteArrayList);
                    break;
            }
        }
    }

    private static void printNotes(ArrayList<Note> notes) {
        for (int i = 0; i < notes.size(); i++) {
            System.out.println(i+1 + ": " + notes.get(i).text);
        }
    }


}
