/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package key;

import java.util.Vector;

/**
 *
 * @author armin
 */
public class KeysEventDispatcherThread extends Thread implements KeyEventDispatcher {

    private Vector<KeyEvent> keyEvents;
    //
    private boolean Running;

    public KeysEventDispatcherThread() {
        keyEvents = new Vector<>();
    }

    @Override
    public void run() {
        while (this.isRunning()) {
            try {
                while (!keyEvents.isEmpty()) {
                    KeyEvent keyEvent = keyEvents.firstElement();
                    if (keyEvent.isExpire()) {
                        keyEvents.remove(keyEvent);
                        switch (keyEvent.getCode()) {
                            case java.awt.event.KeyEvent.VK_UP:
                                global.GlobalVariable.driver.stopForward();
                                break;
                            case java.awt.event.KeyEvent.VK_DOWN:
                                global.GlobalVariable.driver.stopBackward();
                                break;
                            case java.awt.event.KeyEvent.VK_LEFT:
                                global.GlobalVariable.driver.stopLeft();
                                break;
                            case java.awt.event.KeyEvent.VK_RIGHT:
                                global.GlobalVariable.driver.stopRight();
                                break;
                            case java.awt.event.KeyEvent.VK_C:
                                break;
                            default:
                                break;

                        }
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
            super.start();
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
        }
    }

    public boolean isRunning() {
        return Running;
    }

    private void setRunning(boolean Running) {
        this.Running = Running;
    }

    @Override
    public boolean dispatchKeyEvent(key.KeyEvent e) {

        switch (e.getCode()) {
            case java.awt.event.KeyEvent.VK_UP:
                if (e.getEvent() == key.KeyEvent.Event.PRESS) {
                    global.GlobalVariable.driver.startForward();
                } else if (e.getEvent() == key.KeyEvent.Event.RELEASE) {
                    global.GlobalVariable.driver.stopForward();
                }
                break;
            case java.awt.event.KeyEvent.VK_DOWN:
                if (e.getEvent() == key.KeyEvent.Event.PRESS) {
                    global.GlobalVariable.driver.startBackward();
                } else if (e.getEvent() == key.KeyEvent.Event.RELEASE) {
                    global.GlobalVariable.driver.stopBackward();
                }
                break;
            case java.awt.event.KeyEvent.VK_LEFT:
                if (e.getEvent() == key.KeyEvent.Event.PRESS) {
                    global.GlobalVariable.driver.startLeft();
                } else if (e.getEvent() == key.KeyEvent.Event.RELEASE) {
                    global.GlobalVariable.driver.stopLeft();
                }
                break;
            case java.awt.event.KeyEvent.VK_RIGHT:
                if (e.getEvent() == key.KeyEvent.Event.PRESS) {
                    global.GlobalVariable.driver.startRight();
                } else if (e.getEvent() == key.KeyEvent.Event.RELEASE) {
                    global.GlobalVariable.driver.stopRight();
                }
                break;
            case java.awt.event.KeyEvent.VK_C:
                break;
            default:
                break;

        }

        if (e.getEvent() == key.KeyEvent.Event.PRESS && e.getMode() == KeyEvent.Mode.PRESS_HEARTBEAT_RELEASE) {
            keyEvents.add(e);
        } else if (e.getEvent() == key.KeyEvent.Event.RELEASE && e.getMode() == KeyEvent.Mode.PRESS_HEARTBEAT_RELEASE) {
            keyEvents.remove(e);
        } else if (e.getEvent() == key.KeyEvent.Event.HEARTBEAT && e.getMode() == KeyEvent.Mode.PRESS_HEARTBEAT_RELEASE) {
            int index = keyEvents.indexOf(e);
            if (index != -1) {
                keyEvents.get(index).updateHeartbeatLastTime();
            }
        }

        return true;
    }
}
