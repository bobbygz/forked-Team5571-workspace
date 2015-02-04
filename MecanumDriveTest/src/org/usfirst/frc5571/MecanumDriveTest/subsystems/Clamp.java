// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5571.MecanumDriveTest.subsystems;

import org.usfirst.frc5571.MecanumDriveTest.RobotMap;
import org.usfirst.frc5571.MecanumDriveTest.commands.*;
import org.usfirst.frc5571.MecanumDriveTest.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class Clamp extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    CANTalon cANTalonClamp = RobotMap.clampCANTalonClamp;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    double Yaxis;
    
	public void openMotor() {
		SmartDashboard.putString("Mode", "Open: 3v");
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.PercentVbus);
		cANTalonClamp.set(-.25);
		SmartDashboard.putNumber("Motor Current", cANTalonClamp.getOutputCurrent());
		// This portion of the code is designed to stop the robot from crushing
		// the totes
		// UNTESTED!!
	}
	
	public void closeMotor() {
		SmartDashboard.putString("Clamp Mode", "Close: 3v");
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.PercentVbus);
		cANTalonClamp.set(.25);
		SmartDashboard.putNumber("Motor Current", cANTalonClamp.getOutputCurrent());
		// This portion of the code is designed to stop the robot from crushing
		// the totes
		// UNTESTED!!
	}
	
	public void checkMotorCurrent(double value) {
		if (cANTalonClamp.getOutputCurrent() > value) {
			cANTalonClamp.changeControlMode(CANTalon.ControlMode.Position);
			cANTalonClamp.set(cANTalonClamp.getPosition());
		}
	}

	public void initCanPID() {

		// Set PID values here
		double p = 1;
		double i = 0.001;
		double d = .1;
		double f = 0;
		int izone = 0;
		double ramprate = 36;
		int profile = 0;
		cANTalonClamp.setPID(p, i, d, f, izone, ramprate, profile);

		// Specify Quadrature Encoder
		cANTalonClamp.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		cANTalonClamp.reverseSensor(true);
		//Reverse = true for gripper motor
		//Reverse = false for pulley

		// Initially set to run Open Loop Mode on joystick Y command. 
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.PercentVbus);
		Yaxis = Robot.oi.xboxController.getY();
		cANTalonClamp.set(Yaxis); //This will not work because this is only initialized at the start.
	}
	
	// Change to closed loop control mode and hold the current position
	public void positionMode() {
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Position);
		cANTalonClamp.set(cANTalonClamp.getPosition());
	}
	
	// Change to closed loop control mode and move "count" ticks 
	public void positionMoveByCount(double count) {
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Position);
		cANTalonClamp.set((cANTalonClamp.getPosition()+count));
	}

	// Change to Closed Loop Velocity Mode and
	public void speedMode() {
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Speed);
		Yaxis = Robot.oi.xboxController.getY();
		cANTalonClamp.set(Yaxis*12.0); // need to scale the voltage by the joystick values;
	}

	public void voltageMode() {
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Voltage);
		Yaxis = Robot.oi.xboxController.getY();
		cANTalonClamp.set(Yaxis);
	}

	
    public void showStatus(){
        double currentAmps = cANTalonClamp.getOutputCurrent();
        double outputV = cANTalonClamp.getOutputVoltage();
        double busV = cANTalonClamp.getBusVoltage();
        double quadEncoderPos = cANTalonClamp.getEncPosition();
        double quadEncoderVelocity = cANTalonClamp.getEncVelocity();
        double selectedSensorPos = cANTalonClamp.getPosition();
        double selectedSensorSpeed = cANTalonClamp.getSpeed();
        int    closeLoopErr = cANTalonClamp.getClosedLoopError();
        
        

        SmartDashboard.putNumber("Motor Current", currentAmps);
        SmartDashboard.putNumber("cANTalonClamp Output Voltage", outputV);
        SmartDashboard.putNumber("cANTalonClampBus Voltage", busV);
        SmartDashboard.putNumber("Encoder Position", quadEncoderPos);
        SmartDashboard.putNumber("Encoder Velocity", quadEncoderVelocity);
        SmartDashboard.putNumber("Position", selectedSensorPos);
        SmartDashboard.putNumber("Speed", selectedSensorSpeed);
        SmartDashboard.putNumber("Position Error", closeLoopErr);
    }
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new ClampStop());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

