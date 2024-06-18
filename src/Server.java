/**
 * Server.Java
 * -----------------------
 * Authors:
 * Kian Aliwalas
 * Delanie Crews
 * -----------------------
 * CNT4504 Summer 2024
 * Instructor: Scott Kelly
 * -----------------------
 * Iterative Socket Server
 * Due Date 07/05/24
 * -----------------------
 * Implement an iterative (single-threaded) server for use in a client-server
 * Configured to examine, analyze, and study the effects an iterative server
 * has on the efficiency (average turn-around time) of processing client requests.
 */

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;

/**
 * Server class for SSI
 */
public class Server {

    /**
     * main
     */
    public static void main(String[] args) {
        //User input Port
        Scanner scanner = new Scanner(System.in); //To scan for port being used
        System.out.println("Enter port");
        int port = scanner.nextInt(); //Takes user input port
        scanner.close();

        //Try-Catch for Server socket
        try (ServerSocket serverSocket = new ServerSocket(port)) //Opens Server
        {
            System.out.println("Server is listening on port: " + port); //Debugging


                Socket socket = serverSocket.accept(); //Taking in client connection
                System.out.println("Client connected");

                System.out.println("Opening input stream");
                InputStream inputStream = socket.getInputStream(); //Taking Client input
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream); //Reads Client input

                System.out.println("Opening output stream");
                OutputStream outputStream = socket.getOutputStream(); //Opens output stream to client
                PrintWriter writer = new PrintWriter(outputStream, true); //Opens PrintWrite to client

                //System.out.println(inputStreamReader.read());

            int clientChoice = 9; //Initializes clientChoice to get the while to work properly

            while (clientChoice != 0) {
                clientChoice = Character.getNumericValue(inputStreamReader.read()); //Gets client option choice
                System.out.println(clientChoice); //Debugging

                //writer.println(clientChoice); //Debugging sends value back to client

                switch (clientChoice) {
                    case 1: //Date and Time
                            date(writer);
                        break;

                    case 2: //Uptime

                        break;

                    case 3: //Memory Use

                        break;

                    case 4: //Netstat

                        break;

                    case 5: //Current Users

                        break;

                    case 6: //Running Processes

                        break;

                }

            }
        } catch (Exception e) { //Catches Exception from opening server socket
            System.out.println("Server Exception: " + e.getMessage());
        }
    }

    private static void date(PrintWriter writer){
        writer.println(new Date().toString());
    }


}
