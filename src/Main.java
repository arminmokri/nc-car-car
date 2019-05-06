
import client.ClientThread;
import config.Config;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author armin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Config config = new Config(args);
        try {
            ClientThread clientThread = new ClientThread(
                    config.getServer().getHostAddress(),
                    config.getServer().getPort()
            );
            clientThread.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}