// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc5571.RobotFinal.subsystems;

import org.omg.CORBA.TIMEOUT;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Timer.StaticInterface;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc5571.RobotFinal.OI;
import org.usfirst.frc5571.RobotFinal.Robot;
import org.usfirst.frc5571.RobotFinal.RobotMap;
import org.usfirst.frc5571.RobotFinal.commands.*;

/**
 *
 */
/**
 * @author Bobby Gintz
 * This code was modified on 2-22-2015 to test the gripper on Bulldog1 (practice robot)
 * Gripper function is to close and open with closed loop velocity mode until current spike is seen and then
 * Grip at a fixed number of counts beyond that with current limit applied to motor
 * A voltage ramp rate is applied to keep from stressng the gearbox 
 *
 */
public class Clamp extends Subsystem {
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	CANTalon cANTalonClamp = RobotMap.clampCANTalonClamp;


	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	double axis;
	static double MAX_CLAMP_CURRENT = 5.0;  // limit current to 10A x 12V = 120W  mini CIM motor is rated at 230W and 86A Stall Current
	static double CLOSE_SPEED =200;  // change in encoder position  per 10 ms
	static double OPEN_SPEED = -200;  // change in encoder position  per 10 ms
	double p;
	double i;;
	double d;
	double f;
	int izone;
	double ramprate;  // this should leave the ramp rate uncapped.
	int profile;
	boolean servoHereFlag;
	double servoAtThisPosition;



	public void initCanPID() {

		// Set PID values for Velocity and Position Mode here in profile 0 for gripper
//		p = 1;
//		i = 0.0002;
//		d = .3;
//		f = 0;
//		izone = 0;
//		ramprate = 0;  // this should leave the ramp rate uncapped.
//		profile = 0;
//		cANTalonClamp.setPID(p, i, d, f, izone, ramprate, profile);
//
//		// Set PID values for Servo In Place Posisiotn Mode here in profile 1 for gripper
//		p = 3;
//		i = 0.001;
//		d = .4;
//		f = 0;
//		izone = 0;
//		ramprate = 0;  // this should leave the ramp rate uncapped.
//		profile = 1;
//		cANTalonClamp.setPID(p, i, d, f, izone, ramprate, profile);
		
		// Set PID values for Velocity and Position Mode here in profile 0 for winch
				p = 1.5;
				i = 0.01;
				d = .1;
				f = 0;
				izone = 100;
				ramprate = 0;  // this should leave the ramp rate uncapped.
				profile = 0;
				cANTalonClamp.setPID(p, i, d, f, izone, ramprate, profile);

			//Set PID values for Servo In Place Posisiotn Mode here in profile 1 for winch
				p = 5;
				i = 0.001;
				d = .8;
				f = 0;
				izone = 0;
				ramprate = 0;  // this should leave the ramp rate uncapped.
				profile = 1;
				cANTalonClamp.setPID(p, i, d, f, izone, ramprate, profile);

		// Specify Quadrature Encoder
		cANTalonClamp.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		// Initially set to run Open Loop Mode on joystick Y command. 
		//		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Voltage);
		//		axis = Robot.oi.xboxController.getY();
		//		cANTalonClamp.set(axis); //This will not work because this is only initialized at the start.
	}
	
/***********  ADD THIS FOR BRAKE MODE AND LIMIT SWITCH SUPPORT   
	public void initClampLimits() {
		cANTalonClamp.enableBrakeMode(true);
		cANTalonClamp.enableLimitSwitch(true, true);
	}

	// Change to closed loop control mode and hold the current position
	public void positionMode() {
		servoHereFlag = false;
		cANTalonClamp.setProfile(0);
		cANTalonClamp.ClearIaccum();
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Position);
		cANTalonClamp.set(cANTalonClamp.getPosition());
		SmartDashboard.putString("Servo Status", "Servo Inactive");
	}
********************/
	
	// Change to closed loop control mode and move "count" ticks 
	public void positionMoveByCount(double count) {
		servoHereFlag = false;
		cANTalonClamp.setProfile(0);
		cANTalonClamp.ClearIaccum();
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Position);
		cANTalonClamp.set((cANTalonClamp.getPosition()+count));
		SmartDashboard.putString("Servo Status", "Servo Inactive");

	}

	// Change to Closed Loop Velocity Mode and
	public void speedMode() {
		servoHereFlag = false;
		cANTalonClamp.setProfile(0);
		cANTalonClamp.ClearIaccum();
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Speed);
		axis = Robot.oi.xboxController.getY();
		cANTalonClamp.set(axis*12.0); // need to scale the voltage by the joystick values;
		SmartDashboard.putString("Servo Status", "Servo Inactive");

	}

	public void closeClamp() {
		servoHereFlag = false;
		cANTalonClamp.setProfile(0);
		cANTalonClamp.ClearIaccum();
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Speed);
		cANTalonClamp.set(CLOSE_SPEED);
		SmartDashboard.putString("Servo Status", "Servo Inactive");

	}

	public void openClamp() {
		servoHereFlag = false;
		cANTalonClamp.setProfile(0);
		cANTalonClamp.ClearIaccum();
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Speed);
		cANTalonClamp.set(OPEN_SPEED);
		SmartDashboard.putString("Servo Status", "Servo Inactive");

	}


	public void servoHere(){
		if (!servoHereFlag){ // first time through, so set flag and get the current position
			servoHereFlag = true;
			servoAtThisPosition = cANTalonClamp.getPosition()+32;
			cANTalonClamp.setProfile(1);

		}
		if (!clampCuurenLimited()){
			cANTalonClamp.changeControlMode(CANTalon.ControlMode.Position);
			cANTalonClamp.set(servoAtThisPosition);
			SmartDashboard.putString("Servo Status", "ServoActive");
		}
		else{
			cANTalonClamp.changeControlMode(CANTalon.ControlMode.PercentVbus);
			cANTalonClamp.set(.10);
			SmartDashboard.putString("Servo Status", "CurrentLimited");
		}
		
		
	}


	public void disable_ClampMotor(){
		servoHereFlag = false;
		cANTalonClamp.setProfile(0);
		cANTalonClamp.changeControlMode(CANTalon.ControlMode.PercentVbus);
		cANTalonClamp.set(0);
		SmartDashboard.putString("Servo Status", "Servo Inactive");

	}


//	public void voltageMode() {
//		servoHereFlag = false;
//		//cANTalonClamp.setProfile(0);
//		cANTalonClamp.changeControlMode(CANTalon.ControlMode.Voltage);
//		axis = Robot.oi.xboxController.getY();
//		cANTalonClamp.set(axis);
//		SmartDashboard.putString("Servo Status", "Servo Inactive");
//
//	}

//	public void goForward() {
//		servoHereFlag = false;
//		//cANTalonClamp.setProfile(0);
//		SmartDashboard.putString("Motor Command", "Forward");
//		cANTalonClamp.set(0.1 * (Robot.oi.yAxis));
//		SmartDashboard.putNumber("Motor Current", cANTalonClamp.getOutputCurrent());
//		SmartDashboard.putString("Servo Status", "Servo Inactive");
//
//	}

	public Boolean clampCuurenLimited(){ 
		return(cANTalonClamp.getOutputCurrent() > MAX_CLAMP_CURRENT);
	}

	//	public void buttonStop() {
	//		cANTalonClamp.set(0);
	//		SmartDashboard.putString("Motor Command", "Stop");
	//		SmartDashboard.putNumber("Motor Current", cANTalonClamp.getOutputCurrent());
	//	}

	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
		setDefaultCommand(new ClampCommand());

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void showCanTalonStatus() {

		double currentAmps = cANTalonClamp.getOutputCurrent();
		double outputV = cANTalonClamp.getOutputVoltage();
		double busV = cANTalonClamp.getBusVoltage();
		double quadEncoderPos = cANTalonClamp.getEncPosition();
		double quadEncoderVelocity = cANTalonClamp.getEncVelocity();
		double selectedSensorPos = cANTalonClamp.getPosition();
		double selectedSensorSpeed = cANTalonClamp.getSpeed();
		int closeLoopErr = cANTalonClamp.getClosedLoopError();
		double encoderErr = cANTalonClamp.getClosedLoopError();
		double Iaccum = cANTalonClamp.GetIaccum();
		double quadA = cANTalonClamp.getPinStateQuadA();
		double quadB = cANTalonClamp.getPinStateQuadB();

		{
			SmartDashboard.putNumber("Motor Current", currentAmps);
			SmartDashboard.putNumber("CANTalon1 Output Voltage", outputV);
			SmartDashboard.putNumber("CANTalon1Bus Voltage", busV);
			SmartDashboard.putNumber("Encoder Position", quadEncoderPos);
			SmartDashboard.putNumber("Encoder Velocity", quadEncoderVelocity);
			SmartDashboard.putNumber("Position", selectedSensorPos);
			SmartDashboard.putNumber("Speed", selectedSensorSpeed);
			SmartDashboard.putNumber("Position Error", closeLoopErr);
			SmartDashboard.putNumber("I Accumulation", Iaccum);
			SmartDashboard.putNumber("Closed Loop Err", encoderErr);
			SmartDashboard.putBoolean("ServoHere Flag", servoHereFlag);
			SmartDashboard.putNumber("Quad A", quadA);
			SmartDashboard.putNumber("Quad B", quadB);


		}

	}

}

