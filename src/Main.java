/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import client.ClientThread;
import client.request.Request;
import config.Config;

/**
 *
 * @author armin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here

        Config config = new Config(args);
        try {

            ClientThread clientThread = new ClientThread(
                    config.getServer().getHostAddress(),
                    config.getServer().getPort(),
                    "Main"
            );
            clientThread.start();

            // test register
            Request reg = new Request(Request.REGISTER);
            clientThread.Request(reg);
            System.out.println(reg.getAnswerString());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
