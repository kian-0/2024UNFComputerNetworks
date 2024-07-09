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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ConcurrentClient {
    //Variable declaration
    private static final ArrayList<Thread> threadArrayList = new ArrayList<>();

    public static void main(String[] args) {
        //User input IP/Port
        Scanner scanner = new Scanner(System.in); //To scan for IP/Port being used

        while (true) {
            System.out.println("Enter IP: ");
            String ip = scanner.nextLine(); //Takes user input port

            System.out.println("Enter port: ");
            int port = scanner.nextInt(); //Takes user input port

            //Try-Catch opening socket to server
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 3000);
                System.out.println("Connected to " + ip + ":" + port);
                socket.close(); //Closing it, so it can be reopened by the threads. This is required just to indicate that the server exists

                //Client Menu
                menu();
                int clientChoice;
                while (true) {
                    clientChoice = scanner.nextInt();

                    if (clientChoice == 0) { //Closes program when 0 is entered
                        System.exit(1);
                    }
                    System.out.println("Client choice " + clientChoice); //Debugging

                    //Ask for how many requests/threads.
                    boolean run = true;
                    int requests = 0;
                    while (run) { //While loop for amount of threads to be created
                        System.out.println("How many requests? 1, 5, 10, 15, 20 or 25");
                        requests = scanner.nextInt();
                        switch (requests) { //Keeps looping to ensure valid amount
                            case 1:
                            case 5:
                            case 10:
                            case 15:
                            case 20:
                            case 25:
                                run = false;
                                System.out.println("Creating " + requests + " request(s).");
                                break;

                            default:
                                System.out.println("Invalid request amount. Try again.");
                                scanner.nextLine(); //Clears any buffer/newline issues.
                                break;
                        }
                    }
                    scanner.nextLine();             //Clears and buffer/newline issues.

                    //Threading Proto-Type
                    //Creates all the threads
                    System.out.println("Sending " + requests + " request(s) to " + ip + ":" + port);
                    for (int i = 0; i < requests; i++) {
                        ConcurrentThread thread = new ConcurrentThread();
                        thread.setValues(ip, port, clientChoice);
                        threadArrayList.add(thread);
                    }

                    //Starts up all the threads
                    long totalTime = 0; //Have to use long as the System.currentTimeMillis is in long
                    for (int i = 0; i < requests; i++) {
                        long startTime = System.currentTimeMillis(); //Starts "timer". gets start time
                        threadArrayList.get(i).start();//Starts thread
                        threadArrayList.get(i).join(); //Waits for the thread to die. I think this is fine for single thread not multi? -Kian
                        long endTime = System.currentTimeMillis(); //Ends "timer". Gets end time

                        long elapsedTime = endTime - startTime; //Calculate elapsedTime.
                        totalTime += elapsedTime;

                        System.out.println("Time elapsed: " + elapsedTime + "ms");
                    }

                    //Display total response time and average per request
                    long avgTime = totalTime / requests;
                    System.out.println("Average response time: " + avgTime + "ms");
                    System.out.println("Total response time: " + totalTime + "ms");

                    threadArrayList.clear();    //Clears threadArraylist, Bricks and dies when you send another request if removed.
                    menu();                     //Calls for menu

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
                threadArrayList.clear();
            }
        }
    }

    /**
     * Displays options when called
     */
    private static void menu() {
        System.out.println("\r\nEnter desired operation");
        System.out.println("1 - Date and Time");
        System.out.println("2 - Uptime");
        System.out.println("3 - Memory use");
        System.out.println("4 - Netstat");
        System.out.println("5 - Current users");
        System.out.println("6 - Running processes");
        System.out.println("0 - Exit \r\n");
    }

}

class ConcurrentThread extends Thread {
    private String ip;
    private int port;
    private int clientChoice;

    @Override
    public void run() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 3000);
            //System.out.println("Connected to " + ip + ":" + port); //For debugging

            //Starting sending to server
            //System.out.println("Opening input stream");
            InputStream inputStream = socket.getInputStream(); //Gets lines from server
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //Reads lines from server

            //Starting read back from server
            //System.out.println("Opening output stream");
            OutputStream outputStream = socket.getOutputStream(); //Opens output stream to server
            PrintWriter writer = new PrintWriter(outputStream, true); //Opens PrintWrite to server

            //Sending to server
            writer.println(clientChoice); //Sends client choice to the server

            //Continues to read BufferedReader until it gets reads end from server
            String serverResponse = bufferedReader.readLine(); //Gets the first line
            while (!serverResponse.equals("end")) { //Loops until it gets and end response from the server
                System.out.println(serverResponse); //prints out server response
                serverResponse = bufferedReader.readLine(); //reads next line from server.
            }

            //Closes all the input/output streams
            writer.close();
            bufferedReader.close();
            socket.close(); //Closes socket
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * A pseudo-constructor? I feel like this is unnecessary, but I am not entirely sure what I'm doing.
     * I don't know another way to allow myThread to open and close the socket without giving them like this.
     * Because I wasn't sure how to or if it was even possible to pass stuff in to the .run()  -Kian
     *
     * @param ip           IP of the server
     * @param port         Port of the server
     * @param clientChoice Number of the operation chosen
     */
    public void setValues(String ip, int port, int clientChoice) {
        this.ip = ip;
        this.port = port;
        this.clientChoice = clientChoice;
    }
}