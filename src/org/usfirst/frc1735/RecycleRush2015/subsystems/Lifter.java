// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc1735.RecycleRush2015.subsystems;

import org.usfirst.frc1735.RecycleRush2015.Robot;
import org.usfirst.frc1735.RecycleRush2015.RobotMap;
import org.usfirst.frc1735.RecycleRush2015.commands.LiftWithJoystick;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Lifter extends PIDSubsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController lifterMotor = RobotMap.lifterLifterMotor;
    Servo ratchetServo = RobotMap.lifterRatchetServo;
    Servo fingerServoLeft = RobotMap.lifterFingerServoLeft;
    Servo fingerServoRight = RobotMap.lifterFingerServoRight;
    DigitalInput toteReadyIndicator = RobotMap.lifterToteReadyIndicator;
    Encoder liftHeightEncoder = RobotMap.lifterLiftHeightEncoder;
    AnalogPotentiometer liftHeightPot = RobotMap.lifterLiftHeightPot;
    DigitalInput fingerLimitSwitchLeft = RobotMap.lifterFingerLimitSwitchLeft;
    DigitalInput fingerLimitSwitchRight = RobotMap.lifterFingerLimitSwitchRight;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // Lifter finger constants
    public static final double FINGER_ENGAGED = 30, // degrees
    		FINGER_DISENGAGED = 5; // Degrees
    
    // Lifter ratchet constants
    public static final double RATCHET_ENGAGED = 0, // degrees
    		RATCHET_DISENGAGED = 40;
    
    // Initialize your subsystem here
    public Lifter() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        super("Lifter", 1.0, 0.0, 0.0);
        setAbsoluteTolerance(0.2);
        getPIDController().setContinuous(false);
        LiveWindow.addActuator("Lifter", "PIDSubsystem Controller", getPIDController());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new LiftWithJoystick());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
        return liftHeightPot.get();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
        lifterMotor.pidWrite(output);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
    }
    
    // ---------------------------------
    // User-defined functions start here
    // ---------------------------------

    // move ratchet servo to desired position in degrees.
    public void ratchetMove(double angle) {
    	RobotMap.lifterRatchetServo.setAngle(angle);
    }
    
    // Wrapper function for handling ratchet plus lifter.   Called by Commands.
    public void lifterMove(double setpoint) {
	    // Because there is a ratchet interlock, we need to determine if the ratchet needs to be disengaged before invoking the PID controller!
	    // If the current encoder value is BELOW (ore equal to) the setpoint, engage the ratchet.
	    // If the current encoder value is ABOVE the setpoint, we have to lower the lift and should DISENGAGE the ratchet.
	    if (Robot.lifter.getPosition() <= setpoint) {
	    	Robot.lifter.ratchetMove(RATCHET_ENGAGED);
		}
		else {
			Robot.lifter.ratchetMove(RATCHET_DISENGAGED);
	    }
	    // Now we can move the lifter into position without damaging anything...
	    Robot.lifter.enable();
	    Robot.lifter.setSetpoint(setpoint);
    }
    
    public void fingerEngage() {
    	fingerMove(FINGER_ENGAGED);
    }
    
    public void fingerDisengage() {
    	fingerMove(FINGER_DISENGAGED);
    }
    
    // Disengage fingers on the lifter to allow a stack to be depositied
    public void fingerMove(double angle) {
    	RobotMap.lifterFingerServoLeft.setAngle(angle);
    	RobotMap.lifterFingerServoRight.setAngle(angle);
    	
    }
    public void liftWithJoystick(Joystick lifterJoy) {
        // Collect the Joystick info.
        // We need to filter out very small joystick values and clamp them to zero
        // so that a slightly off-center joystick doesn't send signals to the motor.
        double joy = lifterJoy.getY();
        
        //Apply Filter
        if (Math.abs(joy) < Robot.m_joystickFilter) {joy = 0;}
       
        // We don't use a PID controller at this point (As we don't have a good feedback mechanism).
        // Even if using a PID, you could use the joystick to bump the setpoint up and down.  :-)
        //
        // Instead, explicitly make sure the PID controller is disabled, and then access the motor controller directly.
        // Use limit switches to prevent us from (literally) going off the rails.
        Robot.lifter.disable(); // Make sure the PID controller is still disabled (in case something enabled it)

        // Handle the ratchet pawl
        // if we are going up, engage the ratchet.
        // If we are going down, disengage the ratchet
        // Negative Y values are joystick-forward.  Define that as "up".
        if (joy < 0) {
	    	Robot.lifter.ratchetMove(RATCHET_ENGAGED);
		}
		else {
			Robot.lifter.ratchetMove(RATCHET_DISENGAGED);
	    }
        
        // finally, move the lifter directly.  Motor controller and joystick both work on -1..1 value range.
        // Optional:  could derate motor by a multiplier if lifter moves too quickly.
        //FIXME:  We need to add limit switches to protect top and bottom lifter movement!
        // Only move the motor if the requested direction is not in the same direction as a pressed limit switch.
        lifterMotor.set(-joy); // Assumes positive voltage is upwards.
        

    }

}
