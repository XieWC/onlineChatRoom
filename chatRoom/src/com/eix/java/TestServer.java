package com.eix.java;



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
	
    public static void main(String args[]) {
        try {
            ServerSocket s = new ServerSocket(9898);
            Socket s1 = s.accept();
            OutputStream os = s1.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            InputStream is = s1.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            new MyServerReader(dis).start();
            new MyServerWriter(dos).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class MyServerReader extends Thread {
    private DataInputStream dis;

    public MyServerReader(DataInputStream dis) {
        this.dis = dis;
    }

    public void run() {
        String info;
        try {
            while (true) {
                info = dis.readUTF();
                System.out.println("another: " + info);
                if (info.equals("bye")) {
                    System.out.println("exit!");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyServerWriter extends Thread {
    private DataOutputStream dos;

    public MyServerWriter(DataOutputStream dos) {
        this.dos = dos;
    }

    public void run() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String info;
        try {
            while (true) {
                info = br.readLine();
                dos.writeUTF(info);
                if (info.equals("bye")) {
                    System.out.println("exit!");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}