/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package key;

/**
 *
 * @author armin
 */
public class KeyEvent {

    public enum Mode {
        PRESS, PRESS_RELEASE, PRESS_HEARTBEAT_RELEASE
    };

    public enum Event {
        PRESS, HEARTBEAT, RELEASE
    };

    private Mode mode;
    private int code;
    private Event event;
    private int heartbeatInterval;
    private int heartbeatWaitTime;
    private long heartbeatLastTime;

    public KeyEvent(int code, Event event) {
        this.code = code;
        this.event = event;
    }

    public KeyEvent(Mode mode, int code, Event event, int heartbeatInterval, int heartbeatWaitTime) {
        this.mode = mode;
        this.code = code;
        this.event = event;
        this.heartbeatInterval = heartbeatInterval;
        this.heartbeatWaitTime = heartbeatWaitTime;
        this.heartbeatLastTime = System.currentTimeMillis();
    }

    public boolean isExpire() {
        return heartbeatLastTime + heartbeatInterval + heartbeatWaitTime < System.currentTimeMillis();
    }

    public Mode getMode() {
        return mode;
    }

    public Event getEvent() {
        return event;
    }

    public int getCode() {
        return code;
    }

    public void updateHeartbeatLastTime() {
        setHeartbeatLastTime(System.currentTimeMillis());
    }

    public void setHeartbeatLastTime(long lastHeartbeatTime) {
        this.heartbeatLastTime = lastHeartbeatTime;
    }

    @Override
    public boolean equals(Object obj) {
        KeyEvent keyEvent = (KeyEvent) obj;
        return this.getCode() == keyEvent.getCode();
    }

}
