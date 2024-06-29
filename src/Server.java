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

                //System.out.println(clientChoice); //Debugging
                //writer.println(clientChoice); //Debugging sends value back to client

                switch (clientChoice) {
                    case -1:
                        System.out.println("Startup");
                        break;

                    case 1: //Date and Time
                        System.out.println("1");
                        writer.println(new Date());
                        writer.flush();
                        break;

                    case 2: //Uptime
                        writer.println(upTime()); //in nanoseconds? I don't know need verification
                        writer.flush();
                        break;

                    case 3: //Memory Use
                        writer.println("Free Memory: " + Runtime.getRuntime().freeMemory()); //in bits? needs verification
                        writer.flush();
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

    /**
     * Calculates upTime from System.nanoTime()
     * @return String of system uptime
     */
    private static String upTime(){
        double nanoSeconds = System.nanoTime();     //Retrieves System time in nanoseconds
        double upSeconds =  nanoSeconds/ 1000000000;//It complains if I try to directly convert nanoseconds to days
        double upMinutes = upSeconds / 60;          //So I thought it would be cool to have it display to the seconds
        double upHours = upMinutes / 60;            //There is prob a better way to calculate all of this but I
        int upDays = (int) (upHours / 24);          //was at work, so I just made something quickly - Kian

        //Calculate reminders to display
        int hoursRemain = (int) (upHours % 24);
        int minutesRemain = (int) (upMinutes % 60);
        int secondsRemain = (int) (upSeconds % 60);

        return(upDays + " :Days " + hoursRemain + " :Hours " + minutesRemain + " :Minutes " + secondsRemain + " :Seconds");
    }
}
