
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.SampleRobot;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Dashboard;
import frc.robot.Utilities.Limelight;

import edu.wpi.first.wpilibj.CameraServer;

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
        //CameraServer.getInstance().startAutomaticCapture();
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
			//RobotMap.limelight.update();
			RobotMap.timer.update();

			RobotMap.teleopControls.update();

			// RobotMap.arm.update();
			RobotMap.drivetrain.update();
			// RobotMap.elevator.update();
            // RobotMap.intake.update(RobotMap.timer.getDT());
            ConsoleLogger.update(RobotMap.timer.getDT());


			//Dashboard.send("Compressor Enabled", RobotMap.compressor.enabled());
			//Dashboard.send("Pressure Switch Triggered", RobotMap.compressor.getPressureSwitchValue());
            //Dashboard.send("Compressor Current", RobotMap.compressor.getCompressorCurrent());
            
            // if (RobotMap.driverController.getLeftTriggerBool()) {
            //     RobotMap.cargoRoller.set(ControlMode.PercentOutput, 1.0);
            // } else if (RobotMap.driverController.getRightTriggerBool()) {
            //     RobotMap.cargoRoller.set(ControlMode.PercentOutput, -1.0);
            // } else {
            //     RobotMap.cargoRoller.set(ControlMode.PercentOutput, 0.0);
            // }
            
            // if (RobotMap.driverController.getButtonA()) {
            //     RobotMap.driveLeftFront.set(ControlMode.PercentOutput, 0.5);
            // } else if (RobotMap.driverController.getButtonB()) {
            //     RobotMap.driveLeftBack.set(ControlMode.PercentOutput, 0.5);
            // } else if (RobotMap.driverController.getButtonX()) {
            //     RobotMap.driveRightFront.set(ControlMode.PercentOutput, 0.5);
            // } else if (RobotMap.driverController.getButtonY()) {
            //     RobotMap.driveRightBack.set(ControlMode.PercentOutput, 0.5);
            // } else {
            //     RobotMap.driveLeftFront.set(ControlMode.PercentOutput, 0.0);
            //     RobotMap.driveLeftBack.set(ControlMode.PercentOutput, 0.0);
            //     RobotMap.driveRightFront.set(ControlMode.PercentOutput, 0.0);
            //     RobotMap.driveRightBack.set(ControlMode.PercentOutput, 0.0);
            // }
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
		//RobotMap.limelight.setLightMode(Limelight.LEDEnum.OFF);
	}
}