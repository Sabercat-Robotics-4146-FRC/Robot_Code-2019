
package frc.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

import java.io.IOException;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Utilities.Logger;

public class Robot extends SampleRobot {
	public Robot() {
		
	}

	/**
	 * Runs once when the robot is powered on and called when you are basically guaranteed that
	 * all WPILIBJ stuff will work.
	 */
	@Override
	public void robotInit() {
		RobotMap.init(); // Instantiates things to be used from RobotMap.
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 * This autonomous method mostly deals with choosing the auto to execute.
	 */
	@Override
	public void autonomous() {
		
	}

	/**
	 * This function is run once each time the robot enters operator control mode
	 */
	@Override
	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			RobotMap.timer.update();
			RobotMap.pixy.update();
			
			RobotMap.pixy.blocksToDashboard();
			try{
				Logger.update(RobotMap.timer.getDT());
			} catch(IOException e){
				System.out.println("Logger IOException: " + e);
			}
			// testPixy();
			// RobotMap.drive.arcadeDrive(RobotMap.driverController.getDeadbandLeftYAxis(),
			// 	RobotMap.driverController.getDeadbandRightXAxis());
			
			// if(RobotMap.driverController.getButtonA()){
			// 	RobotMap.leftBottom.set(ControlMode.PercentOutput, 0.3);
			// } else {
			// 	RobotMap.leftBottom.set(ControlMode.PercentOutput, 0.0);
			// }
			
			// This one suposidly decreases input sensitivity at lower speeds.
			// RobotMap.drive.arcadeDrive(RobotMap.driverController.getDeadbandLeftYAxis(),
			// 	RobotMap.driverController.getDeadbandRightXAxis(), true);
		}
	}

	// public void testPixy(){
	// 	PixyBlock test = pixy.readPacket(1);
	// 	if( test != null ){
	// 		test.toDashboard();
	// 	}
	// }

	/**
	 * Runs during test mode.
	 */
	@Override
	public void test() {
		
	}
}
