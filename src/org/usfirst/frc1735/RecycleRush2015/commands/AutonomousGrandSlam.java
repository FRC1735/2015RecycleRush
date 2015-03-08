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
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousGrandSlam extends CommandGroup {
    
    public  AutonomousGrandSlam() {

	// The plan:
	// 1) start with robot around our container (before match start)
	// 2) lift container
	addSequential(new LifterGotoContainer());
	// 3a) Arm lifter
	addParallel(new LifterActiveMode());
	// 3b) Roller in
	addSequential(new CollectRollerIn(2));
	// 3c) drive forward enough to grab tote (and auto-lift)
	//addSequential(new DriveWithLimits(3, 0.5, 0.75)); // Time, Distance, speed

	// Now we have both our container and our tote.
	// 4) Grab the second alliance's container so we can move it out of the way.
	addParallel(new CollectRollerIn(3)); // turn on rollers to grab the container
	addSequential(new DriveWithLimits(3, 5.5, 0.75)); // Time, Distance, speed.  This will just cause the collector to be sucked in against the tote we have.

	// 5) Now turn downfield...
	addSequential(new TurnDownfield());
	// 6) Drive into the Auto Zone
	addSequential(new DriveWithLimits(5, 11, 0.75)); // Time, Distance, speed.
	// 7) and spit it out!
	addSequential(new CollectRollerOut(2));
	// 8) Drive back to the tote line
	addSequential(new DriveWithLimits(5, 11, -0.75)); // Time, Distance, speed.	
	// 9) Turn back towards the totes
	addSequential(new TurnInfield());	
	
	// Get the second alliance's tote!
	// 10a) Arm lifter
	addParallel(new LifterActiveMode());
	// 10b) Engage the roller
	addParallel(new CollectRollerIn(2));
	// 10c) drive forward to get second tote
	addSequential(new DriveWithLimits(3, 1.25, 0.75)); // Time, Distance, speed
	// Now we should have collected the second tote and lifted it.
	
	
	// 11) Grab the third alliance's container and score it
	addParallel(new CollectRollerIn(3)); // turn on rollers to grab the container
	addSequential(new DriveWithLimits(3, 5.5, 0.75)); // Time, Distance, speed.  This will just cause the collector to be sucked in against the tote we have.

	// 12) Now turn downfield...
	addSequential(new TurnDownfield());
	// 13) Drive into the Auto Zone
	addSequential(new DriveWithLimits(5, 11, 0.75)); // Time, Distance, speed.
	// 14) and spit it out!
	addSequential(new CollectRollerOut(2));
	// 15) Drive back to the tote line
	addSequential(new DriveWithLimits(5, 11, -0.75)); // Time, Distance, speed.	
	// 16a) Turn back towards the totes
	addSequential(new TurnInfield());	
	
	// Get the third alliance's tote!
	// 17a) arm lifter
	addParallel(new LifterActiveMode());
	// 13b) Engage the roller
	addParallel(new CollectRollerIn(2));
	// 17c) drive forward to get third tote
	addSequential(new DriveWithLimits(3, 1.25, 0.75));  // Time, Distance, speed
	// Now we should have collected the third tote and lifted it.
	
	
	// Now we have three totes and a container
	// Turn and go to the auto zone to complete our mission
	// 18) Turn 90'
	addSequential(new TurnDownfield());
	// 19) Go forward into the Auto Zone
	addSequential(new DriveWithLimits(3, 11, 0.75)); // Time, Distance, speed   	
	// 20) Drop stack
	addSequential(new LifterGotoDrop());
	// 21) Back up 2 feet
	addSequential(new DriveWithLimits(3, 2, -0.75));  // Time, Distance, speed
	// Done.
    }
}
