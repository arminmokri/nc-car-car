package client;

import client.request.Request;
import client.response.Response;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Armin
 */
public class ClientThread extends Thread {

    //
    private SocketThread socketThread;
    private Vector<Request> requests;
    private Vector<Response> responses;
    //RequestThread requestThread;
    //ResponseThread responseThread;
    private boolean Running;
    //
    //private Vector<Request> requests;
    //private Vector<ResponseThread> responses;
    //
    private Timer HeartBeat;

    public ClientThread(String ServerIP, int ServerPort) throws Exception {
        //
        socketThread = new SocketThread(ServerIP, ServerPort, "ClientThread");
        //
        requests = new Vector<>();
        responses = new Vector<>();
        //
        HeartBeat = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Request heartBeat = new Request(Request.REQ_HEARTBEAT);
                    Request(heartBeat);
                    String ans = heartBeat.getAnswer();
                    if (!ans.equals(Request.REQ_HEARTBEAT_YES)) {
                        Stop();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    Stop();
                }
            }
        });
    }

    @Override
    public void run() {

        while (this.isRunning()) {
            try {
                while (!socketThread.getStringDataInputStream().isEmpty()) {

                    String data = socketThread.getStringDataInputStream().remove(0);

                    switch (data.charAt(0)) {
                        case Response.RESPONSE:
                            Response response = new Response(data);
                            this.socketThread.getDataOutputStream().writeUTF(response.toString());
                            break;
                        case Request.ACT_REQUEST:
                            String ticket_temp = Request.getTicket(data);
                            String answer_temp = Request.getAnswer(data);
                            for (int i = 0; i < requests.size(); i++) {
                                if (ticket_temp.equals(requests.get(i).getTicket())) {
                                    Request request = requests.remove(i);
                                    request.setAnswer(answer_temp);
                                    break;
                                }
                            }
                            break;
                        default:
                            data = null;
                            break;
                    }

                }
                Thread.sleep(25);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    @Override
    public synchronized void start() {
        if (!this.isRunning()) {
            this.setRunning(true);
            //
            socketThread.start();
            //
            this.start();
            //
            HeartBeat.start();
        }
    }

    public void Stop() {
        if (this.isRunning()) {
            this.setRunning(false);

            try {
                this.stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            try {
                HeartBeat.stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            try {
                socketThread.Stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return Running;
    }

    private void setRunning(boolean Running) {
        this.Running = Running;
    }

    public void Request(Request request) {
        try {
            requests.add(request);
            socketThread.getDataOutputStream().writeBytes(request.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
