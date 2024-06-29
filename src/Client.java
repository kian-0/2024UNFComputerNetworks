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
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    //Variable declaration
    private static final ArrayList<Thread> threadArrayList = new ArrayList<>();

    public static void main(String[] args) {
        //User input IP/Port
        Scanner scanner = new Scanner(System.in); //To scan for IP/Port being used

        System.out.println("Enter IP: ");
        String ip = scanner.nextLine(); //Takes user input port

        System.out.println("Enter port: ");
        int port = scanner.nextInt(); //Takes user input port

        //Try-Catch opening socket to server
        try (Socket socket = new Socket(ip, port)) {
            System.out.println("Connected to " + ip + ":" + port);
            socket.close(); //Closing it, so it can be reopened by the threads. This is required just to indicate that the server exists

            //Client Menu
            menu();
            int clientChoice = 9; //Initializes choice and make sure it isn't an option or 0 to go to while
            while (clientChoice != 0) {

                clientChoice = scanner.nextInt();
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
                            System.out.println("Creating " + requests + " requests.");
                            break;

                        default:
                            System.out.println("Invalid request amount. Try again.");
                            scanner.nextLine();
                            break;
                    }
                }
                scanner.nextLine();

                //Threading Proto-Type
                //Creates all the threads
                System.out.println("Sending " + requests + " requests to " + ip + ":" + port);
                for (int i = 0; i < requests; i++) {
                    myThread thread = new myThread();
                    thread.setValues(ip, port, clientChoice);
                    threadArrayList.add(thread);
                }

                //Starts up all the threads
                for (int i = 0; i < requests; i++) {
                    long startTime = System.currentTimeMillis();
                    threadArrayList.get(i).start();
                    threadArrayList.get(i).join();
                    long endTime = System.currentTimeMillis();

                    long elapsedTime = endTime - startTime;

                    System.out.println("Time elapsed: " + elapsedTime + "ms");
                }

                threadArrayList.clear();
                menu();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

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

class myThread extends Thread {
    private String ip;
    private int port;
    private int clientChoice;


    @Override
    public void run() {
        try (Socket socket = new Socket(ip, port)) {
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

            //Waits until the buffered reader has something in it
            while (!bufferedReader.ready()) {
                //noinspection BusyWait
                sleep(1);
            }

            //Receiving from server
                System.out.println(bufferedReader.readLine()); //Prints out the response from the server


            //Closes all the input/output streams
            writer.close();
            bufferedReader.close();
            socket.close(); //Closes socket
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * A constructor? I feel like this is unnecessary, but I am not entirely sure what I'm doing anyway.
     * @param ip IP of the server
     * @param port Port of the server
     * @param clientChoice Number of the operation chosen
     */
    public void setValues(String ip, int port, int clientChoice) {
        this.ip = ip;
        this.port = port;
        this.clientChoice = clientChoice;

    }
}