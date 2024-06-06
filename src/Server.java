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
public class Server
{
    /**
     * main
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in); //To scan for port being used
        System.out.println("Enter port");
        int port = scanner.nextInt(); //Takes user input port
        scanner.close();

        //Try-Catch for Server socket
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is listening on port: " + port);

            //while (true)
            //{
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                OutputStream outputStream = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream, true);

                writer.println("Hello Server");
            //}
        }
        catch (Exception e)
        { //Catches Exception from opening server socket
            System.out.println("Server Exception: " + e.getMessage());
        }
    }
}
