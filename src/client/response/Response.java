/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.response;

import global.GlobalVariable;
import client.ClientThread;
import client.Header;
import client.parameters.Parameter;
import client.parameters.Parameters;
import client.request.Request;
import client.transfer.TransferProtocol;
import java.io.ByteArrayOutputStream;
import config.Car;
import java.io.IOException;
import java.util.Arrays;
import key.KeyEvent;
import org.json.simple.parser.ParseException;

/**
 *
 * @author armin
 */
public class Response {

    //
    private TransferProtocol transferProtocol;
    private Header header;
    private Parameters requestParameters;
    private Parameters responseParameters;
    //
    private ClientThread clientThread;

    public Response(TransferProtocol transferProtocol, Request request, ClientThread clientThread) {
        this(transferProtocol, request.getHeader(), request.getRequestParameters(), clientThread);
    }

    public Response(TransferProtocol transferProtocol, Header header, Parameters requestParameters, ClientThread clientThread) {
        this.header = header;
        this.header.setType(Header.RESPONSE);
        this.requestParameters = requestParameters;
        this.responseParameters = new Parameters();
        this.clientThread = clientThread;
        setResponseParameters();
    }

    public Response(byte[] bytes) throws ParseException {
        // header
        byte[] header = Arrays.copyOfRange(bytes, 0, 11);
        this.header = new Header(Header.REQUEST, header);
        // responseParameters
        byte[] parmeters = Arrays.copyOfRange(bytes, 11, bytes.length);
        this.responseParameters = new Parameters(parmeters);
    }

    public final void setResponseParameters() {
        try {
            String action = requestParameters.getValue(Parameter.ACTION);
            switch (action) {
                case Parameter.HEARTBEAT: {
                    responseParameters.add(Parameter.RESULT, Parameter.RESULT_1);
                    break;
                }
                case Parameter.KEY: {
                    //
                    String key_code_string = requestParameters.getValue(Parameter.KEY_CODE);
                    int key_code = Integer.parseInt(key_code_string);
                    //
                    String key_event_string = requestParameters.getValue(Parameter.KEY_EVENT);
                    KeyEvent.Event key_event = KeyEvent.Event.valueOf(key_event_string);
                    //
                    String key_mode_string = requestParameters.getValue(Parameter.KEY_MODE);
                    KeyEvent.Mode key_mode = KeyEvent.Mode.valueOf(key_mode_string);
                    //
                    String heartbeat_interval_string = requestParameters.getValue(Parameter.KEY_HEARTBEAT_INTERVAL);
                    int heartbeat_interval = 0;
                    if (heartbeat_interval_string != null) {
                        heartbeat_interval = Integer.parseInt(heartbeat_interval_string);
                    }
                    //
                    String heartbeat_wait_time_string = requestParameters.getValue(Parameter.KEY_HEARTBEAT_INTERVAL);
                    int heartbeat_wait_time = 0;
                    if (heartbeat_wait_time_string != null) {
                        heartbeat_wait_time = Integer.parseInt(heartbeat_wait_time_string);
                    }
                    //
                    KeyEvent keyEvent = new KeyEvent(key_mode, key_code, key_event, heartbeat_interval, heartbeat_wait_time);
                    GlobalVariable.keysEventDispatcher.dispatchKeyEvent(keyEvent);
                    responseParameters.add(Parameter.RESULT, Parameter.RESULT_1);
                    responseParameters.add(Parameter.MESSAGE, key_event + " key code: " + key_code);
                    break;
                }
                default: {
                    String string = Parameter.NO_ANSWER + ", For This Request Action: " + action;
                    responseParameters.add(Parameter.RESULT, Parameter.RESULT_0);
                    responseParameters.add(Parameter.MESSAGE, string);
                    System.err.println(string);
                    break;
                }
            }
        } catch (Exception exception) {
            String string = Parameter.ERROR + ", " + exception.getMessage();
            responseParameters.add(Parameter.RESULT, Parameter.RESULT_0);
            responseParameters.add(Parameter.MESSAGE, string);
            System.err.println(string);
        }
    }

    public Parameters getResponseParameters() {
        return responseParameters;
    }

    public Header getHeader() {
        return header;
    }

    public TransferProtocol getTransferProtocol() {
        return transferProtocol;
    }

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
        byteArrayOutputStream.write(header.getBytes());
        byteArrayOutputStream.write(responseParameters.getJsonBytes());
        return byteArrayOutputStream.toByteArray();
    }

}
