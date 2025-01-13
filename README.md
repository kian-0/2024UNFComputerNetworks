# Computer Networks 2024 Projects
## Overview
Written for 202450.CNT4504.50238:Computer Networks 

Professor: John Scott Kelly

Summer 2024

Written by Kian Aliwalas & Delanie Crews

## Status
Completed

## Notable Branches
IterativeServer: Accepts one connection not threaded

ConcurrentServer: Accepts multiple connections threaded

## Project Description 
This project requires students to implement an iterative (single-threaded) server for use in a client-server configuration to examine, analyze, and study the effects an iterative server has on the efficiency (average turn-around time) of processing client requests.

## Instructions
* Create two (2) programs: 1) a server that accepts requests from clients, and 2) a multi-threaded client capable of spawning numerous client sessions that connect to the server
    - The server and client programs must connect to the same network address and port
* The iterative (single-threaded) server should handle one client request at a time (serially)
* The concurrent (multi-threaded) server should spawn a new server instance for each client request (in parallel)
* The server must support the following client requests:
    - Date and Time - the date and time on the server
    - Uptime - how long the server has been running since last boot-up
    - Memory Use - the current memory usage on the server
    - Netstat - lists network connections on the server
    - Current Users - list of users currently connected to the server
    - Running Processes - list of programs currently running on the server
* Create a multi-threaded client that transmits requests to the server on a specified network port
* When the server program is launched, the server should
    - Listen for client requests on the specified network address and port
    - When a client request is received, the server should
       * Determine which operation is being requested
       * Perform the requested operation and collect the resulting output   
    - Reply to the client request with the output from the operation performed 
    - Perform any necessary clean up activities
    - Go back to listening for client requests
* When the client program is launched, the client should
    - Request the network address and port to which to connect
    - Request the operation to request (refer to the list above)
    - Request how many client requests to generate (1, 5, 10, 15, 20 and 25)
 

    - Collect the following data
      * Turn-around Time (elapsed time) for each client request
      * The time required for the client request to travel to the server, be processed by the server, and return to the client
    - Total Turn-around Time (sum of the turn-around times for all the client requests)
    - Average Turn-around Time (Total Turn-around Time divided by the number client requests)

