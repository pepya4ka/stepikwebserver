package socket;

import java.io.*;
import java.net.Socket;

public class StepikSocket extends Thread {
    private final Socket socket;

    public StepikSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine = input.readLine();
            while (!inputLine.equals("Bue.")) {
                out.println(inputLine);
                out.flush();
                inputLine = input.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
