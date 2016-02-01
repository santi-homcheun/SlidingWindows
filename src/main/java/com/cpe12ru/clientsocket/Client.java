/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpe12ru.clientsocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author PC-BANK
 */
public class Client {

    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("localhost", 9090);

        while (true) {

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("upload file :");
            String fileName = "D:\\test\\client\\fileClient.txt";
            outToServer.writeBytes(fileName + '\n');

            String modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER : " + modifiedSentence);
            fileName = "D:\\test\\client" + fileName;

            File file = new File(fileName);

            try {
                DataInputStream stream_in = new DataInputStream(new FileInputStream(file));
                byte[] buffer = new byte[512];
                int len = -1;

                while ((len = stream_in.read(buffer)) != -1) {
                    outToServer.write(buffer, 0, len);
                }
                outToServer.close();
            } catch (Exception e) {
                outToServer.close();
                System.out.println("IO Exception : " + e);
            } finally {
                clientSocket.close();
            }

        }
    }
}
