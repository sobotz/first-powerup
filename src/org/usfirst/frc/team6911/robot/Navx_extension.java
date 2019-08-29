package org.usfirst.frc.team6911.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Navx_extension {
    Joystick stick;

    /* Digital IO */
    Victor pwm_out_9; /* E.g., PWM out to motor controller */
    Jaguar pwm_out_8; /* E.g., PWM out to motor controller */
    DigitalInput dig_in_7; /* E.g., input from contact switch */
    DigitalInput dig_in_6; /* E.g., input from contact switch */
    DigitalOutput dig_out_5; /* E.g., output to relay or LED */
    DigitalOutput dig_out_4; /* E.g., output to relay or LED */
    Encoder enc_3and2; /* E.g., Wheel Encoder */
    Encoder enc_1and0; /* E.g., Wheel Encoder */

    /* Analog Inputs */
    AnalogInput an_in_1; /* E.g., Ultrasonic Sensor */
    AnalogTrigger an_trig_0; /* E.g., Proximity Sensor Threshold */
    Counter an_trig_0_counter; /* E.g., Count of an_trig_0 events */

    /* Analog Outputs */
    AnalogOutput an_out_1; /* E.g., Constant-current LED output */
    AnalogOutput an_out_0; /* E.g., Speaker output */

    public final double MXP_IO_VOLTAGE = 3.3f; /* Alternately, 5.0f */;
    public final double MIN_AN_TRIGGER_VOLTAGE = 0.76f;
    public final double MAX_AN_TRIGGER_VOLTAGE = MXP_IO_VOLTAGE - 2.0f;

    public Navx_extension() {
        stick = new Joystick(0);

        /* Construct Digital I/O Objects */
        pwm_out_9 = new Victor(getChannelFromPin(PinType.PWM, 9));
        pwm_out_8 = new Jaguar(getChannelFromPin(PinType.PWM, 8));
        dig_in_7 = new DigitalInput(getChannelFromPin(PinType.DigitalIO, 7));
        dig_in_6 = new DigitalInput(getChannelFromPin(PinType.DigitalIO, 6));
        dig_out_5 = new DigitalOutput(getChannelFromPin(PinType.DigitalIO, 5));
        dig_out_4 = new DigitalOutput(getChannelFromPin(PinType.DigitalIO, 4));
        enc_3and2 = new Encoder(getChannelFromPin(PinType.DigitalIO, 3), getChannelFromPin(PinType.DigitalIO, 2));
        enc_1and0 = new Encoder(getChannelFromPin(PinType.DigitalIO, 1), getChannelFromPin(PinType.DigitalIO, 0));

    }

    public static int getPortextension(int port) {
        return getChannelFromPin(PinType.DigitalIO, port);

    }

    public enum PinType {
        DigitalIO, PWM, AnalogIn, AnalogOut
    };

    public final static int MAX_NAVX_MXP_DIGIO_PIN_NUMBER = 9;
    public final static int MAX_NAVX_MXP_ANALOGIN_PIN_NUMBER = 3;
    public final static int MAX_NAVX_MXP_ANALOGOUT_PIN_NUMBER = 1;
    public final static int NUM_ROBORIO_ONBOARD_DIGIO_PINS = 10;
    public final static int NUM_ROBORIO_ONBOARD_PWM_PINS = 10;
    public final static int NUM_ROBORIO_ONBOARD_ANALOGIN_PINS = 4;

    /* getChannelFromPin( PinType, int ) - converts from a navX-MXP */
    /* Pin type and number to the corresponding RoboRIO Channel */
    /* Number, which is used by the WPI Library functions. */

    public static int getChannelFromPin(PinType type, int io_pin_number) throws IllegalArgumentException {
        int roborio_channel = 0;
        if (io_pin_number < 0) {
            throw new IllegalArgumentException("Error:  navX-MXP I/O Pin #");
        }
        switch (type) {
        case DigitalIO:
            if (io_pin_number > MAX_NAVX_MXP_DIGIO_PIN_NUMBER) {
                throw new IllegalArgumentException("Error:  Invalid navX-MXP Digital I/O Pin #");
            }
            roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_DIGIO_PINS + (io_pin_number > 3 ? 4 : 0);
            break;
        case PWM:
            if (io_pin_number > MAX_NAVX_MXP_DIGIO_PIN_NUMBER) {
                throw new IllegalArgumentException("Error:  Invalid navX-MXP Digital I/O Pin #");
            }
            roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_PWM_PINS;
            break;
        case AnalogIn:
            if (io_pin_number > MAX_NAVX_MXP_ANALOGIN_PIN_NUMBER) {
                throw new IllegalArgumentException("Error:  Invalid navX-MXP Analog Input Pin #");
            }
            roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_ANALOGIN_PINS;
            break;
        case AnalogOut:
            if (io_pin_number > MAX_NAVX_MXP_ANALOGOUT_PIN_NUMBER) {
                throw new IllegalArgumentException("Error:  Invalid navX-MXP Analog Output Pin #");
            }
            roborio_channel = io_pin_number;
            break;
        }
        return roborio_channel;
    }

    /**
     * Runs the motors with arcade steering.
     */

}