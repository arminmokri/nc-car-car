/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author armin
 */
public class SocketThread extends Thread {

    //
    private final InetAddress ServerInetAddress;
    private final int ServerPort;
    //
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    //
    private Vector<String> stringDataInputStream;
    //
    private boolean Running;

    public SocketThread(String ServerIP, int ServerPort, String name) throws Exception {
        super(name + "->" + "SocketThread");
        //
        this.ServerInetAddress = InetAddress.getByName(ServerIP);
        this.ServerPort = ServerPort;
        //
        this.stringDataInputStream = new Vector<>();
        //
        this.socket = new Socket(this.ServerInetAddress, this.ServerPort);
        //
        this.dataInputStream = new DataInputStream(this.socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());

    }

    @Override
    public void run() {
        String data;
        while (this.isRunning()) {
            try {
                data = dataInputStream.readLine();
                if (data != null && data.length() > 0) {
                    stringDataInputStream.add(data);
                    data = null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void start() {
        if (!this.isRunning()) {
            this.setRunning(true);
            super.start();
        }
    }

    public synchronized void Stop() {
        if (this.isRunning()) {
            this.setRunning(false);

            try {
                super.stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            try {
                dataInputStream.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            try {
                dataOutputStream.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            try {
                socket.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    protected boolean isRunning() {
        return Running;
    }

    protected void setRunning(boolean Running) {
        this.Running = Running;
    }

    protected InetAddress getServerInetAddress() {
        return ServerInetAddress;
    }

    protected synchronized DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    protected synchronized DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    protected Vector<String> getStringDataInputStream() {
        return stringDataInputStream;
    }

}
