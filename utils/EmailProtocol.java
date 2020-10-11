package utils;

import java.io.IOException;
import java.util.*;

public class EmailProtocol {
    // constants used in server and client
  public static final String COMMAND_KEY = "type";
  public static final String PAIR_DELIM = "&";
  public static final String OK_STATUS = "status: ok";
  public static final String EMAIL_DELIM = "ZZZ";
  public static final String PAIR_SEPARATOR = "=";

  public static final String LOG_IN = "log_in";
  public static final String LOG_IN_ACK = "log_in_ack";
  public static final String USERNAME_KEY = "username";
  public static final String LOG_OUT = "log_out";
  public static final String LOG_OUT_ACK = "log_out_ack";

  public static final String RETRIEVE_EMAILS = "retrieve_emails";
  public static final String RETRIEVE_RESPONSE = "emails";
  public static final String EMAIL_LIST_KEY = "emails";

  public static final String SEND_EMAIL = "send_email";
  public static final String EMAIL_KEY = "email";
  public static final String SEND_EMAIL_ACK = "send_email_ack";

  public static final int PORT = 6789;
  public static final String SERVER_ADDRESS = "10.66.223.142";

  public static HashMap<String,String> createProtocolMap(String str, String delimiter, String separator) {
    String argArr[] = str.split(delimiter);
    HashMap<String, String> map = new HashMap<>();

    for (final String arg : argArr) {
      int sepIndex = arg.indexOf(separator);
      map.put(arg.substring(0, sepIndex), arg.substring(sepIndex + 1));
    }

    return map;
  }

  public static void sendProtocolMessage(TcpStream stream, String type, String argName, String arg) throws IOException {
      stream.write(COMMAND_KEY + PAIR_SEPARATOR + type + PAIR_DELIM + argName + PAIR_SEPARATOR + arg + '\n');
  }

  public static void sendProtocolMessage(TcpStream stream, String type) throws IOException {
      stream.write(COMMAND_KEY + PAIR_SEPARATOR + type + '\n');
  }

  public static void sendProtocolMessage(TcpStream stream, String type, String arg) throws IOException {
        stream.write(COMMAND_KEY + PAIR_SEPARATOR + type + PAIR_DELIM + arg + '\n');
    }
}
