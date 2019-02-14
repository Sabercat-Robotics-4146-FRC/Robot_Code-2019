
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.SampleRobot;
import frc.robot.Utilities.Dashboard;

public class Robot extends SampleRobot {
	public Robot() {
		
	}

	/**
	 * Runs once when the robot is powered on and called when you are basically
	 * guaranteed that all WPILIBJ stuff will work.
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
		RobotMap.compressor.setClosedLoopControl(false); // Disables the compressor during tellop. test if needed.

		while (isOperatorControl() && isEnabled()) {
			RobotMap.timer.update();
			RobotMap.teleopControls.update();

			RobotMap.arm.update();
			RobotMap.drivetrain.update();
			RobotMap.elevator.update();
			RobotMap.intake.update(RobotMap.timer.getDT());
			RobotMap.limelight.update();

			// Figure out where to put this ====>>>> (maybe we need an update loop in disabled????)
			Dashboard.send("Compressor Enabled", RobotMap.compressor.enabled());
			Dashboard.send("Pressure Switch Triggered", RobotMap.compressor.getPressureSwitchValue());
			Dashboard.send("Compressor Current", RobotMap.compressor.getCompressorCurrent());

			// Testing DT motors direction code
			// <editor-fold>
			// if(RobotMap.driverController.getButtonA()) {
			// 	RobotMap.driveLeftFront.set(ControlMode.Current, 0.3);
			// } else {
			// 	RobotMap.driveLeftFront.set(ControlMode.Current, 0.0);
			// }

			// if(RobotMap.driverController.getButtonB()) {
			// 	RobotMap.driveLeftBack.set(ControlMode.Current, 0.3);
			// } else {
			// 	RobotMap.driveLeftBack.set(ControlMode.Current, 0.0);
			// }

			// if(RobotMap.driverController.getButtonX()) {
			// 	RobotMap.driveRightFront.set(ControlMode.Current, 0.3);
			// } else {
			// 	RobotMap.driveRightFront.set(ControlMode.Current, 0.0);
			// }

			// if(RobotMap.driverController.getButtonY()) {
			// 	RobotMap.driveRightBack.set(ControlMode.Current, 0.3);
			// } else {
			// 	RobotMap.driveRightBack.set(ControlMode.Current, 0.0);
			// }
			// </editor-fold>
		}
	}

	/**
	 * Runs during test mode.
	 */
	@Override
	public void test() {
		
	}
	
	/**
	 * Runs during disabled mode.
	 */
	@Override
	public void disabled() {
		RobotMap.compressor.setClosedLoopControl(true); // Enables the compressor during disabled. test if needed.
	}
}