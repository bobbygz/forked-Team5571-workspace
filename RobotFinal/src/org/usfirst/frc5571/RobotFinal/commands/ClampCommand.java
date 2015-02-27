package org.usfirst.frc5571.RobotFinal.commands;

import org.usfirst.frc5571.RobotFinal.Robot;
import org.usfirst.frc5571.RobotFinal.RobotMap;
import org.usfirst.frc5571.RobotFinal.subsystems.Clamp;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ClampCommand extends Command {
	private Clamp canController = Robot.clamp;
	boolean position_held;

	public ClampCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.clamp);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Set up PID values for use later but keep motor in Voltage Mode
		canController.initCanPID();
		/************ INIT BRAKE MODE AND LIMITS  ******
		clampController.initClampLimits();
		SmartDashboard.putString("Clamp MODE:", "Initialized");
		********/
		SmartDashboard.putString("MODE:", "Initialized to Voltage");
		canController.showCanTalonStatus();
	}

	// Called repeatedly when this Command is scheduled to run
	// X button uses velocity control to close clamp and stops motor if current
	// is exceeded
	// Y button uses velocity control to open clamp and stops motor if current
	// is exceeded
	// A button uses position control to move motor a specified number of counts
	// forward
	// B button uses position control to move motor a specified number of counts
	// backward

	protected void execute() {
		double axis;
		if (Robot.oi.X_Button) { // close clamp with current limited protection
			// axis = Robot.oi.xboxController.getY();
			// RobotMap.clampCANTalon1.set(axis);
			// canController.positionMode();
			// SmartDashboard.putString("MODE:", "Position");
			// canController.showCanTalonStatus();
			// Close Clamp
			if (!canController.clampCuurenLimited()) {
				canController.closeClamp();
				SmartDashboard.putString("MODE:", "Closing");
			} else {
				//canController.servoHere();
			}
		}

		else if (Robot.oi.Y_Button) { // open clamp with current limited
										// protection
			// open Clamp
			if (!canController.clampCuurenLimited()) {
				canController.openClamp();
				SmartDashboard.putString("MODE:", "Y - Opening");
				;
			} else { // current limit exceeded

				canController.servoHere();
				SmartDashboard.putString("MODE:",
						"Y - OPEN CURRENT LIMIT EXCEEDED");
			}

		}
//FOR TUNING ONLY DISCONNECT LINKAGE BEFORE USING
//		else if (Robot.oi.A_Button) {
//			canController.positionMoveByCount(1000);
//			SmartDashboard.putString("MODE:", "B - Move+1000");
//
//		}

		//FOR TUNING ONLY DISCONNECT LINKAGE BEFORE USING
		//		else if (Robot.oi.B_Button){ 
//			canController.positionMoveByCount(-1000);
//			SmartDashboard.putString("MODE:", "B - Move-1000");
//		}

		else {
			canController.servoHere();
		}

		if (Robot.oi.Start_Button) {
			canController.disable_ClampMotor();
		}

		// Display status on every execute call
		canController.showCanTalonStatus();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		//canController.showCanTalonStatus();
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		//canController.showCanTalonStatus();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		//canController.showCanTalonStatus();
	}
}
