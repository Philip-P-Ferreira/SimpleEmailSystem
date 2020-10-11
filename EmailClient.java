import static utils.EmailProtocol.*;

import java.io.*;
import java.util.*;
import utils.*;

public class EmailClient {
  public static enum COMMANDS {
    SEND_EMAIL,
    FETCH_EMAILS,
    LOG_OUT,
  }
  ;

  public static void main(String[] args) throws InterruptedException {
    Scanner console = new Scanner(System.in);
    System.out.print("\nEnter username to log in: ");
    String username = console.nextLine();

    TcpStream clientStream = new TcpStream();
    boolean session = false;

    try {
      clientStream = new TcpStream(SERVER_ADDRESS, PORT);
      sendProtocolMessage(clientStream, LOG_IN, USERNAME_KEY, username);
      System.out.println("Logged in as user: " + username);

      session = true;
    } catch (IOException e) {
      System.out.println(
          "\nFailed to connect to server. Check IP address and try again\n");
    }

    try {
      while (session) {
        switch (getUserCommand(console)) {
        case FETCH_EMAILS:
          // method to retrieve mail

          break;
        case SEND_EMAIL:
          // method to compose and send new email

          break;
        case LOG_OUT:
          // method for logging out and closing connections
          sendProtocolMessage(clientStream, LOG_OUT);
          session = false;
          break;
        }
      }

    } catch (IOException e) {
      System.out.println("Unable to reach server.");
    }
  }

  public static COMMANDS getUserCommand(Scanner input) {

    int commandNum = -1;
    boolean validInput = false;

    // for easy indexing and returning
    COMMANDS COMMAND_LIST[] = {COMMANDS.SEND_EMAIL, COMMANDS.FETCH_EMAILS,
                               COMMANDS.LOG_OUT};

    // loop as long as input isn't valid
    while (!validInput) {
      // prompt user
      System.out.println("\n1. Send Mail\n2. Read Mail\n3. Exit");
      System.out.println("Select a command (input the corresponding number)");

      while (!validInput) {
        String inputStr = input.nextLine();
        if (inputStr.length() == 1) {
          commandNum = inputStr.charAt(0) - '0';
          if (commandNum > 0 && commandNum < 4) {
            validInput = true;
          } else {
            System.out.println("\nInvalid number. Try again");
          }
        } else {
          System.out.println("\nInvalid input. Try again");
        }
      }
    }
    return COMMAND_LIST[commandNum - 1];
  }
}