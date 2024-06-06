/**
 * Client.Java
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
import java.util.Scanner;

public class Client
{

    public static void main(String[] args)
    {

        Scanner scanner = new Scanner(System.in); //To scan for IP/Port being used

        System.out.println("Enter IP: ");
        String ip = scanner.nextLine(); //Takes user input port

        System.out.println("Enter port");
        int port = scanner.nextInt(); //Takes user input port
        scanner.close();

        try (Socket socket = new Socket(ip,port))
        {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String time = bufferedReader.readLine();

            System.out.println(time);

        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
