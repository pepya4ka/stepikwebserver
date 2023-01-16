package main;

import socket.StepikSocket;

import java.net.ServerSocket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final long TIME_TO_WORK = TimeUnit.SECONDS.toMillis(10);

    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(5050)) {
            System.out.println("Server started");
            long startTime = new Date().getTime();
            while (new Date().getTime() < startTime + TIME_TO_WORK) {
                new StepikSocket(serverSocket.accept()).start();
            }
        }
    }
}
