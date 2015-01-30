// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5571.MecanumDriveTest;

import org.usfirst.frc5571.MecanumDriveTest.Robot;
import org.usfirst.frc5571.MecanumDriveTest.commands.*;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton leftBumper;
    public JoystickButton rightBumper;
    public Joystick xboxController;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton aButton;

public NetworkTable table;

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        xboxController = new Joystick(0);
        
        rightBumper = new JoystickButton(xboxController, 6);
        rightBumper.whileHeld(new ClampClose());
        leftBumper = new JoystickButton(xboxController, 5);
        leftBumper.whileHeld(new ClampOpen());

	    
        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());

        SmartDashboard.putData("Drive Train2 Command", new DriveTrain2Command());

        SmartDashboard.putData("ClampStop", new ClampStop());

        SmartDashboard.putData("ClampClose", new ClampClose());

        SmartDashboard.putData("ClampOpen", new ClampOpen());


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    }
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getXboxController() {
        return xboxController;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
/*   
  	public void updateStatus() {
  					yAxis = xboxController.getY();
  					xAxis = xboxController.getX();
  					zAxis = xboxController.getZ();
  					xboxThrottle = xboxController.getThrottle();
  					xboxTwist = xboxController.getTwist();

  					left_xAxis = xboxController.getRawAxis(1);
  					left_yAxis = xboxController.getRawAxis(2);
  					right_xAxis = xboxController.getRawAxis(4);
  					right_yAxis = xboxController.getRawAxis(5);
  					DPAD_LR = xboxController.getRawAxis(6);

  					A_Button = xboxController.getRawButton(1);
  					B_Button = xboxController.getRawButton(2);
  					X_Button = xboxController.getRawButton(3);
  					Y_Button = xboxController.getRawButton(4);
  					Bumper_L = xboxController.getRawButton(5);
  					Bumper_R = xboxController.getRawButton(6);
        //SmartDashboard.putBoolean("Button Being Held:", Robot.testMotor.buttonHeld);
        SmartDashboard.putNumber("Y-Axis Value:", yAxis);
        SmartDashboard.putNumber("X-Axis Value:", xAxis);
        SmartDashboard.putNumber("Z-Axis Value:", zAxis);
        SmartDashboard.putNumber("Throttle Value:", xboxThrottle);
        SmartDashboard.putNumber("Twist Value:", xboxTwist);
        
        SmartDashboard.putNumber("RAW_Left_x:", left_xAxis);
        SmartDashboard.putNumber("RAW_Left_y:", left_yAxis);
        SmartDashboard.putNumber("RAW_Right_x:", right_xAxis);
        SmartDashboard.putNumber("RAW_Right_y:", right_yAxis);
        SmartDashboard.putNumber("RAW_DPAD:", DPAD_LR);
        SmartDashboard.putBoolean("A_Button", A_Button);
        SmartDashboard.putBoolean("B_Button", B_Button);
        SmartDashboard.putBoolean("X_Button", X_Button);
        SmartDashboard.putBoolean("Y_Button", Y_Button);
        SmartDashboard.putBoolean("Bumper_L", Bumper_L);
        SmartDashboard.putBoolean("Bumper_R", Bumper_R);
	}
	*/
}

