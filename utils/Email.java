package utils;

import java.util.HashMap;
import static utils.EmailProtocol.*;

public class Email {
    
    String to;
    String from;
    String body;
    
    public static final String FIELD_DELIM = ";";
    public static final String FIELD_SEPARATOR = ">";
    public static final String TO_FIELD = "to";
    public static final String FROM_FIELD = "from";
    public static final String BODY_FIELD = "body";

    public Email(String to, String from, String body) {
        this.to = to;
        this.from = from;
        this.body = body;
    }

    public Email (String emailString) {
        HashMap<String,String> fieldMap = createProtocolMap(emailString, FIELD_DELIM, TO_FIELD);
        to = fieldMap.get(TO_FIELD);
        from = fieldMap.get(FROM_FIELD);
        body = fieldMap.get(BODY_FIELD);
    }

    public String toString() {
        return TO_FIELD + FIELD_SEPARATOR + to + FIELD_DELIM + FROM_FIELD +
        FIELD_SEPARATOR + from + FIELD_DELIM + BODY_FIELD + FIELD_SEPARATOR +
        body;

    }
}