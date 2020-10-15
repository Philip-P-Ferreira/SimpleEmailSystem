import static utils.EmailProtocol.*;
import static utils.client.ClientHelper.*;

import java.io.*;
import java.util.*;
import utils.*;

public class EmailClient {
  public static enum COMMANDS {
    SEND_EMAIL,
    FETCH_EMAILS,
    LOG_OUT,
  }

  public static void main(String[] args) {
    // create a console scanner, get username for seesion
    Scanner console = new Scanner(System.in);
    System.out.print("\nEnter username to log in: ");
    String username = console.nextLine();

    // create a new client stream
    TcpStream clientStream = null;
    boolean session = false;

    // try catch block to handle any IOException
    try {
      clientStream = new TcpStream(SERVER_ADDRESS, PORT);
      logUserIn(clientStream, username);

      System.out.println("Logged in as user: " + username);
      session = true;

      // user chooses command, handle accordinly
      // if error, just catch and exit
      while (session) {
        switch (getUserCommand(console)) {
          case FETCH_EMAILS:
            displayEmails(clientStream);
            break;

          case SEND_EMAIL:
            composeEmail(clientStream, console, username);
            break;

          case LOG_OUT:
            System.out.println("Logging out... \n");
            logUserOut(clientStream);
            session = false;
            break;
        }
      }
      clientStream.close();

    } catch (IOException e) {
      System.out.println("Unable to reach server.\n");
    }
  }

  // method for getting user command from console
  public static COMMANDS getUserCommand(Scanner input) {
    int commandNum = -1;
    boolean validInput = false;

    // for easy indexing and returning
    COMMANDS COMMAND_LIST[] = {COMMANDS.SEND_EMAIL, COMMANDS.FETCH_EMAILS, COMMANDS.LOG_OUT};

    // loop as long as input isn't valid
    while (!validInput) {
      // prompt user
      System.out.println("\n1. Send Mail\n2. Read Mail\n3. Exit");
      System.out.println("Select a command (input the corresponding number)");

      // input validation, repeat if invalid
      while (!validInput) {
        String inputStr = input.nextLine();
        commandNum = inputStr.charAt(0) - '0';
        if (inputStr.length() == 1 && commandNum > 0 && commandNum < 4) {
          validInput = true;
        } else {
          System.out.println("\nInvalid input. Try again");
        }
      }
    }
    System.out.println(); // pad output with an extra line
    return COMMAND_LIST[commandNum - 1];
  }

  // method for fetching the list of emails, prints each email to console
  public static void displayEmails(TcpStream stream) throws IOException {
    ArrayList<Email> emailList = fetchEmails(stream);

    if (emailList.size() == 0) {
      System.out.println("Inbox is empty.");
    } else {
      System.out.println("Displaying all messages...");

      for (final Email email : emailList) {
        System.out.println("\nFrom: " + email.from);
        System.out.println(email.body);
      }
    }
  }

  // method for composing and email and sending it to the server, addressed from
  // user
  public static void composeEmail(TcpStream stream, Scanner input, String user) throws IOException {
    System.out.print("To: ");
    String toUser = input.nextLine();

    System.out.print("Body text: ");
    String bodyText = input.nextLine();

    Email emailToSend = new Email(toUser, user, bodyText);
    sendEmail(stream, emailToSend);
  }
}