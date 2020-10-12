package utils;
import static utils.EmailProtocol.*;

import java.io.*;
import java.util.*;

public class ClientHelper {
  /**
   * logUserIn -
   * accepts a tcp stream and a user name and send the appropriate login request
   * and returns the server's response
   *
   * @param stream - Tcpstream used to communicate to another host
   * @param user - username to log in as
   * @return - server response
   * @throws IOException
   */
  public static String logUserIn(TcpStream stream, String user)
      throws IOException {
    sendProtocolMessage(stream, LOG_IN, USERNAME_KEY, user);
    return stream.read();
  }

  /**
   * sendEmail -
   * accepts a tcpStream and an Email, and sends a "send email" response
   * to the server and returns the server resposne
   *
   * @param stream - Tcpstream used to communicated to another host
   * @param email - Email to send
   * @return - server response
   * @throws IOException
   */
  public static String sendEmail(TcpStream stream, Email email)
      throws IOException {
    sendProtocolMessage(stream, SEND_EMAIL, EMAIL_KEY, email.toString());
    return stream.read();
  }

  /**
   * fetchEmails -
   * accepts a Tcpstream and returns an ArrayList of all emails
   * the currently logged in user's inbox
   *
   * @param stream - Tcpstream used to communicated to another host
   * @return - ArrayList of emails
   * @throws IOException
   */
  public static ArrayList<Email> fetchEmails(TcpStream stream)
      throws IOException {
    sendProtocolMessage(stream, RETRIEVE_EMAILS);
    String emailResponse = stream.read();

    ArrayList<Email> emailList = new ArrayList<>();
    HashMap<String, String> argMap =
        createProtocolMap(emailResponse, PAIR_DELIM, PAIR_SEPARATOR);

    String allEmails = argMap.get(EMAIL_LIST_KEY);
    if (allEmails.equals(EMAIL_DELIM)) {
      return emailList;
    }

    String splitEmails[] = allEmails.split(EMAIL_DELIM);
    for (final String emailStr : splitEmails) {
      emailList.add(new Email(emailStr));
    }

    return emailList;
  }

  /**
   * logUserOut -
   * accepts a tcpstream, sends the log out message, and returns
   * the server response
   *
   * @param stream - Tcpstream used to communicated to another host
   * @return - server response
   * @throws IOException
   */
  public static String logUserOut(TcpStream stream) throws IOException {
    sendProtocolMessage(stream, LOG_OUT);
    String res = stream.read();
    stream.close();

    return res;
  }
}
