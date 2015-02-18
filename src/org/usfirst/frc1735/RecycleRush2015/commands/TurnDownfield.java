// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc1735.RecycleRush2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc1735.RecycleRush2015.Robot;
import org.usfirst.frc1735.RecycleRush2015.RobotMap;

/**
 *
 */
public class  TurnDownfield extends Command {

	public TurnDownfield() {
		this(0.75); //Default to turn at 75% speed
	}
	public TurnDownfield(double magnitudeDirection) {

        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        m_magnitudeDirection = magnitudeDirection;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Grab the current encoder distance as the starting point
    	m_leftStartDistance = RobotMap.driveTrainLeftMotorEncoder.getDistance();
    	m_rightStartDistance = RobotMap.driveTrainRightMotorEncoder.getDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// If red and blue are mirror images, compensate here.
    	// For now, assume we turn left
    	Robot.driveTrain.tankDrive(-m_magnitudeDirection,  m_magnitudeDirection);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        double currentLeftDistance = RobotMap.driveTrainLeftMotorEncoder.getDistance();
        double currentRightDistance = RobotMap.driveTrainRightMotorEncoder.getDistance();
        double leftTravel = Math.abs(currentLeftDistance - m_leftStartDistance);
        double rightTravel = Math.abs(currentRightDistance - m_rightStartDistance);
        return ((leftTravel > m_distanceLimit) || (rightTravel > m_distanceLimit));
   }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    // Member Variables
    double m_magnitudeDirection; 	// target speed/direction
    double m_distanceLimit = 1.5;  		// This is the wheel travel distance to enact a 90' turn.
    double m_leftStartDistance;		// starting absolute distance from encoder
    double m_rightStartDistance;	// starting absolute distance from encoder

}