/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.response;

/**
 *
 * @author armin
 */
public class Response {

    // response value
    public static final char RESPONSE = 0x03;
    public static final char ACT_RESPONSE = 0x04;
    //
    public static final char REQ_Username = 0x01;
    //
    private String ticket;
    private char response;
    private String answer;

    public Response(String response) {
        this.response = response.charAt(1);
        ticket = response.substring(2, 12);
        if (this.response == Response.REQ_Username) {
            //this.answer = Global.getMember().getUsername();
        } else {
            System.err.println("NO ANSWER :( " + this.response);
        }
    }

    @Override
    public String toString() {
        String string = "";
        string = string + Response.ACT_RESPONSE;
        string = string + ticket;
        string = string + answer;
        return string;
    }
}
