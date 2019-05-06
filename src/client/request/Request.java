/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.request;

/**
 *
 * @author armin
 */
public class Request {

    // request value
    public static final char REQUEST = 0x01;
    public static final char ACT_REQUEST = 0x02;
    // 
    public static final char REQ_HEARTBEAT = 0x00;
    public static final String REQ_HEARTBEAT_YES = "YES";
    //
    private String Ticket;
    private char Message;
    private String Answer;
    private long ResponseTimeout;

    public Request() {
        this.Ticket = makeTicket();
        this.ResponseTimeout = 5000;
    }

    public Request(char message) {
        this.Message = message;
        this.Ticket = makeTicket();
        this.ResponseTimeout = 5000;
    }

    public Request(char message, int ResponseTimeoutSec) {
        this.Message = message;
        this.Ticket = makeTicket();
        this.ResponseTimeout = ResponseTimeoutSec * 1000;
    }

    public Request(String Ticket, char Message, String Answer) {
        this.Ticket = Ticket;
        this.Message = Message;
        this.Answer = Answer;
    }

    private String makeTicket() {
        char[] ticket = new char[10];
        for (int i = 0; i < ticket.length;) {
            char random = (char) ((('Z' - '0') + 1) * Math.random() + '0');
            if ((random >= '0' && random <= '9') || (random >= 'A' && random <= 'Z')) {
                ticket[i] = random;
                i++;
            }
        }
        return ticket.toString();
    }

    public String getTicket() {
        return Ticket;
    }

    public long getResponseTimeout() {
        return ResponseTimeout;
    }

    public String getAnswer() {
        try {
            long now = System.currentTimeMillis();
            while (Answer == null && (System.currentTimeMillis() <= now + ResponseTimeout)) {
                Thread.sleep(25);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Answer;
    }

    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }

    public static String getTicket(String response) {
        return response.substring(2, 12);
    }

    public static String getAnswer(String response) {
        return response.substring(11);
    }

    @Override
    public String toString() {
        String string = "";
        string = string + Request.REQUEST;
        string = string + Message;
        string = string + Ticket;
        return string;
    }

}
