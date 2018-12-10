package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ServerThread {
    public static final int ACTION_GET_NOTES = 100;
    public static final int ACTION_SEND_NOTES = 101;

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 3000;

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ArrayList<Note> mNotes;

    void doAction(int action) {
        try {
            socket = new Socket(HOST, PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            switch (action) {
                case ACTION_GET_NOTES: {
                    outputStream.write(100);
                    mNotes = getNotesFromServer(inputStream);
                    break;
                }
                case ACTION_SEND_NOTES:
                    outputStream.write(101);
                    sendNotesToServer(outputStream, mNotes);
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList<Note> getNotesFromServer(InputStream inputStream) {

        ArrayList<Note> notes = new ArrayList<>();
        try {
            byte[] arraySize = new byte[4];
            int actuallyRead = inputStream.read(arraySize);
            if (actuallyRead != 4)
                return notes;
            int arrayLength = ByteBuffer.wrap(arraySize).getInt();
            while (notes.size() != arrayLength)
                notes.add(new Note(inputStream));
            return notes;
        } catch (IOException | EndOfStream e) {
            e.printStackTrace();
        }
        return notes;
    }


    private void sendNotesToServer(OutputStream outputStream, ArrayList<Note> notes) {
        try {
            byte[] arraySize = new byte[4];
            ByteBuffer.wrap(arraySize).putInt(notes.size());
            outputStream.write(arraySize);
            for (Note note : notes) {
                note.write(outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    ArrayList<Note> getNotes() {
        return mNotes;
    }

    void setNotes(ArrayList<Note> notes) {
        mNotes = notes;
    }


}
