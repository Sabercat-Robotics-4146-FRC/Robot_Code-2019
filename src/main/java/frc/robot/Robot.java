
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
		RobotMap.compressor.setClosedLoopControl(false); // TODO Disables the compressor during tellop. test if needed.

		while (isOperatorControl() && isEnabled()) {
			RobotMap.limelight.update();
			RobotMap.timer.update();

			RobotMap.teleopControls.update();

			// RobotMap.arm.update();
			RobotMap.drivetrain.update();
			// RobotMap.elevator.update();
			// RobotMap.intake.update(RobotMap.timer.getDT());


			Dashboard.send("Compressor Enabled", RobotMap.compressor.enabled());
			Dashboard.send("Pressure Switch Triggered", RobotMap.compressor.getPressureSwitchValue());
			Dashboard.send("Compressor Current", RobotMap.compressor.getCompressorCurrent());
			// Dashboard.send("Compressor Current", RobotMap.compressor.getCompressorCurrent());

			// Testing DT motors direction code
			// <editor-fold>
			// if (RobotMap.driverController.getButtonA()) {
			// 	RobotMap.driveLeftFront.set(ControlMode.Current, 0.3);
			// } else {
			// 	RobotMap.driveLeftFront.set(ControlMode.Current, 0.0);
			// }

			// if (RobotMap.driverController.getButtonB()) {
			// 	RobotMap.driveLeftBack.set(ControlMode.Current, 0.3);
			// } else {
			// 	RobotMap.driveLeftBack.set(ControlMode.Current, 0.0);
			// }

			// if (RobotMap.driverController.getButtonX()) {
			// 	RobotMap.driveRightFront.set(ControlMode.Current, 0.3);
			// } else {
			// 	RobotMap.driveRightFront.set(ControlMode.Current, 0.0);
			// }

			// if (RobotMap.driverController.getButtonY()) {
			// 	RobotMap.driveRightBack.set(ControlMode.Current, 0.3);
			// } else {
			// 	RobotMap.driveRightBack.set(ControlMode.Current, 0.0);
			// }
			// </editor-fold>

			// debugging tools for testing the sensors for the first time
			Dashboard.send("Elevator Limit Switch", RobotMap.elevatorLimitSwitch.get());
			Dashboard.send("Left Cargo Limit switch", RobotMap.cargoLeftLimitSwitch.get());
			Dashboard.send("Right Cargo Limit Switch", RobotMap.cargoRightLimitSwitch.get());
			Dashboard.send("Arm Pot", RobotMap.armPot.get());
			Dashboard.send("Elevator Encoder", RobotMap.elevatorFront.getSelectedSensorPosition());
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
		
	}
}