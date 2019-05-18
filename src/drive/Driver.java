/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drive;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 *
 * @author armin
 */
public class Driver {

    //
    private GpioController gpioController;
    //
    private GpioPinDigitalOutput forward;
    private GpioPinDigitalOutput backward;
    private GpioPinDigitalOutput left;
    private GpioPinDigitalOutput right;

    public Driver() {
        if (!global.GlobalVariable.config.isSimulationMode()) {
            gpioController = GpioFactory.getInstance();
            //
            forward = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_22, "forward", PinState.LOW);
            backward = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_23, "backward", PinState.LOW);
            left = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_24, "left", PinState.LOW);
            right = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_25, "right", PinState.LOW);
        }
    }

    public void startForward() {
        stopBackward();
        if (!global.GlobalVariable.config.isSimulationMode()) {
            if (forward.isLow()) {
                forward.high();
            }
        } else {
            System.out.println("startForward");
        }
    }

    public void stopForward() {
        if (!global.GlobalVariable.config.isSimulationMode()) {
            if (forward.isHigh()) {
                forward.low();
            }
        } else {
            System.out.println("stopForward");
        }
    }

    public void startBackward() {
        stopForward();
        if (!global.GlobalVariable.config.isSimulationMode()) {
            if (backward.isLow()) {
                backward.high();
            }
        } else {
            System.out.println("startBackward");
        }
    }

    public void stopBackward() {
        if (!global.GlobalVariable.config.isSimulationMode()) {
            if (backward.isHigh()) {
                backward.low();
            }
        } else {
            System.out.println("stopBackward");
        }
    }

    public void startLeft() {
        stopRight();
        if (!global.GlobalVariable.config.isSimulationMode()) {
            if (left.isLow()) {
                left.high();
            }
        } else {
            System.out.println("startLeft");
        }
    }

    public void stopLeft() {
        if (!global.GlobalVariable.config.isSimulationMode()) {
            if (left.isHigh()) {
                left.low();
            }
        } else {
            System.out.println("stopLeft");
        }
    }

    public void startRight() {
        stopLeft();
        if (!global.GlobalVariable.config.isSimulationMode()) {
            if (right.isLow()) {
                right.high();
            }
        } else {
            System.out.println("startRight");
        }
    }

    public void stopRight() {
        if (!global.GlobalVariable.config.isSimulationMode()) {
            if (right.isHigh()) {
                right.low();
            }
        } else {
            System.out.println("stopRight");
        }
    }

    public void stop() {
        stopForward();
        stopBackward();
        stopLeft();
        stopRight();
    }

}
