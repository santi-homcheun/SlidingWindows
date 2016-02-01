/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpe12ru.serversocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author PC-BANK
 */
public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket welcomeSocket = new ServerSocket(9090);
        System.out.println("Server Online");
        while (true) {

            Socket connectionSocket = welcomeSocket.accept();
            
            if (connectionSocket.isConnected()) {
                System.out.println("Clinet connected\n");
            }

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            String fileName = inFromClient.readLine();
            System.out.println("Accept filename\n");
            outToClient.writeBytes("Request Complete\n");
            DataInputStream stream_in = new DataInputStream(connectionSocket.getInputStream());
            
            String str[] = fileName.split("\\\\");
            fileName = str[str.length-1];
  
            fileName = "D:\\test\\server\\" + fileName;
            File file = new File(fileName);
            System.out.println("Debug file : " +file);

            try {
                DataOutputStream stream_out = new DataOutputStream(new FileOutputStream(file));
                byte[] buffer = new byte[4096];
                int len = -1;
                while ((len = stream_in.read(buffer)) != -1) {
                    stream_out.write(buffer, 0, len);
                }
            } catch (IOException e) {
                System.out.println("IO Exception : " + e);
                welcomeSocket.close();
            }finally{
                connectionSocket.close();
            }
        }
    }
}
