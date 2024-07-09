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
import java.util.Scanner;

/**
 * Server class for ISS
 */
public class IterativeServer {

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

            while (true) {
                System.out.println(InetAddress.getLocalHost());
                System.out.println("Server is listening on port: " + port); //Debugging

                Socket socket = serverSocket.accept(); //Taking in client connection
                System.out.println("Client connected");

                System.out.println("Opening input stream");
                InputStream inputStream = socket.getInputStream(); //Taking Client input
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //Reads Client input

                System.out.println("Opening output stream");
                OutputStream outputStream = socket.getOutputStream(); //Opens output stream to client
                PrintWriter writer = new PrintWriter(outputStream, true); //Opens PrintWrite to client

                int clientChoice = Character.getNumericValue(bufferedReader.read()); //Gets client option choice

                switch (clientChoice) {
                    case -1: //Used on start up because it dies after a second set of requests are sent
                        System.out.println("Startup");
                        break;
                    case 1: //Date and Time
                        unixCommand(new String[]{"date"}, writer); //Calls date method
                        writer.println("end"); //End of transmission flag
                        writer.flush();
                        break;
                    case 2: //Uptime
                        unixCommand(new String[]{"uptime"}, writer);
                        writer.println("end"); //End of transmission flag
                        writer.flush();
                        break;
                    case 3: //Memory Use
                        unixCommand(new String[]{"free"}, writer);
                        writer.println("end"); //End of transmission flag
                        writer.flush();
                        break;
                    case 4: //Netstat
                        unixCommand(new String[]{"netstat"}, writer); //Sends netstat command and writer to unixCommand()
                        writer.println("end"); //End of transmission flag
                        writer.flush();
                        break;
                    case 5: //Current Users
                        unixCommand(new String[]{"w"}, writer); //Sends w command(CurrentUsers) and writer to unixCommand()
                        writer.println("end"); //End of transmission flag
                        writer.flush();
                        break;
                    case 6: //Running Processes
                        unixCommand(new String[]{"ps", "-aux"}, writer); //Sends ps -aux (Running processes) and writer to unixCommand
                        writer.println("end"); //End of transmission flag
                        writer.flush();
                        break;
                }
            }
        } catch (Exception e) { //Catches Exception from opening server socket
            System.out.println("Server Exception: " + e.getMessage());
        }
    }

    /**
     * Takes unix Command array and prints to server.
     *
     * @param commands UnixCommand array
     * @param writer   writer to client
     * @throws IOException exception thrower
     */
    private static void unixCommand(String[] commands, PrintWriter writer) throws IOException {
        String returnLines; //Initiates string being sent to client
        Process pro2 = Runtime.getRuntime().exec(commands); //builds the process
        BufferedReader buff = new BufferedReader(new InputStreamReader(pro2.getInputStream())); //Opens bufferedReader of the running command
        while ((returnLines = buff.readLine()) != null) {
            //System.out.println(s); Debugging
            writer.println(returnLines); //Prints to client
        }
        buff.close();
    }

}
