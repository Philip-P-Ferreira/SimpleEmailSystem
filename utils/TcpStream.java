package utils;

import java.io.*;
import java.net.*;

public class TcpStream {
  private Socket socket;
  private DataOutputStream outputStream;
  private BufferedReader inputStream;

  public TcpStream(){};

  public TcpStream(String serverAddress, int port) throws IOException {
    socket = new Socket(serverAddress, port);
    outputStream = new DataOutputStream(socket.getOutputStream());
    inputStream =
        new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  public TcpStream(Socket socket) throws IOException {
    this.socket = socket;
    outputStream = new DataOutputStream(socket.getOutputStream());
    inputStream =
        new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  public void write(String str) throws IOException {
    outputStream.writeBytes(str);
  }

  public String read() throws IOException { return inputStream.readLine(); }

  public void close() throws IOException { socket.close(); }
}
