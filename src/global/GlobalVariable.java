package global;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import client.ClientThread;
import config.Config;
import key.KeysEventDispatcherThread;
import drive.Driver;

/**
 *
 * @author armin
 */
public class GlobalVariable {

    public static Config config;
    public static ClientThread clientThread;
    public static KeysEventDispatcherThread keysEventDispatcher;
    public static Driver driver;

}
