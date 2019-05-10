package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import global.GlobalVariable;
import client.ClientThread;
import client.parameters.Parameter;
import client.parameters.Parameters;
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

        GlobalVariable.config = new Config(args);
        try {

            String hostAddress = GlobalVariable.config.getServer().getHostAddress();
            int port = GlobalVariable.config.getServer().getPort();

            GlobalVariable.clientThread = new ClientThread(
                    hostAddress,
                    port,
                    "Main"
            );
            GlobalVariable.clientThread.start();

            /*
            // test register
            String username = GlobalVariable.config.getCar().getUsername();
            String password = GlobalVariable.config.getCar().getPassword();
            //
            Parameters parameters = new Parameters();
            parameters.addParameter(Parameter.ACTION, Parameter.REGISTER);
            parameters.addParameter(Parameter.TYPE, Parameter.TYPE_CAR);
            parameters.addParameter(Parameter.USERNAME, username);
            parameters.addParameter(Parameter.PASSWORD, password);
            Request register = new Request(parameters);
            GlobalVariable.clientThread.Request(register);
            System.out.println(register.getResponseParameters().getJsonString());
            System.exit(0);
             */
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
