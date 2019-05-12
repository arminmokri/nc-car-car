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

            // test register
            String username = GlobalVariable.config.getCar().getUsername();
            String password = GlobalVariable.config.getCar().getPassword();
            //
            Parameters parameters = new Parameters();
            parameters.add(Parameter.ACTION, Parameter.REGISTER);
            parameters.add(Parameter.TYPE, Parameter.TYPE_CAR);
            parameters.add(Parameter.USERNAME, username);
            parameters.add(Parameter.PASSWORD, password);
            Request register = new Request(parameters);
            GlobalVariable.clientThread.Request(register);
            String result = register.getResponseParameters().getValue("result");
            if (!result.equals(Parameter.RESULT_1)) {
                throw new Exception(register.getResponseParameters().getValue("message"));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }
}
