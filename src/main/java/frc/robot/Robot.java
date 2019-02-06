
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.SampleRobot;

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
		while (isOperatorControl() && isEnabled()) {
			RobotMap.timer.update();
			RobotMap.arm.update();
			RobotMap.drivetrain.update();
			RobotMap.elevator.update();
			RobotMap.intake.update();

			// Testing DT motors direction code
			// <editor-fold>
			if(RobotMap.driverController.getButtonA()) {
				RobotMap.driveLeftFront.set(ControlMode.Current, 0.3);
			} else {
				RobotMap.driveLeftFront.set(ControlMode.Current, 0.0);
			}

			if(RobotMap.driverController.getButtonB()) {
				RobotMap.driveLeftBack.set(ControlMode.Current, 0.3);
			} else {
				RobotMap.driveLeftBack.set(ControlMode.Current, 0.0);
			}

			if(RobotMap.driverController.getButtonX()) {
				RobotMap.driveRightFront.set(ControlMode.Current, 0.3);
			} else {
				RobotMap.driveRightFront.set(ControlMode.Current, 0.0);
			}

			if(RobotMap.driverController.getButtonY()) {
				RobotMap.driveRightBack.set(ControlMode.Current, 0.3);
			} else {
				RobotMap.driveRightBack.set(ControlMode.Current, 0.0);
			}
			// </editor-fold>

			// Drive Code
			// <editor-fold>
			// RobotMap.drive.arcadeDrive(RobotMap.driverController.getDeadbandLeftYAxis(),
			// 	RobotMap.driverController.getDeadbandRightXAxis());
			// </editor-fold>
		}
	}

	/**
	 * Runs during test mode.
	 */
	@Override
	public void test() {
		
	}
}
