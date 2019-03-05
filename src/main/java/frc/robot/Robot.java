
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.SampleRobot;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Dashboard;
import frc.robot.Utilities.Limelight.LEDEnum;

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
			RobotMap.limelight.update();
			RobotMap.timer.update();
			RobotMap.teleopControls.update();

			// RobotMap.arm.update();
			// RobotMap.drivetrain.update();
            // RobotMap.elevator.update();
            // RobotMap.intake.update(RobotMap.timer.getDT());
            ConsoleLogger.update(RobotMap.timer.getDT());

            // RobotMap.pilotController.updateRumbleBuzz(RobotMap.timer.getDT());

            RobotMap.EGL_leftFront.set(ControlMode.PercentOutput, RobotMap.pilotController.getRightTrigger() - RobotMap.pilotController.getLeftTrigger());

            // RobotMap.elevatorFront.set(ControlMode.PercentOutput, RobotMap.pilotController.getRightTrigger() - RobotMap.pilotController.getLeftTrigger());

			//Dashboard.send("Compressor Enabled", RobotMap.compressor.enabled());
			//Dashboard.send("Pressure Switch Triggered", RobotMap.compressor.getPressureSwitchValue());
            //Dashboard.send("Compressor Current", RobotMap.compressor.getCompressorCurrent());
            
            // if (RobotMap.pilotController.getLeftTriggerBool()) {
            //     RobotMap.cargoRoller.set(ControlMode.PercentOutput, 1.0);
            // } else if (RobotMap.pilotController.getRightTriggerBool()) {
            //     RobotMap.cargoRoller.set(ControlMode.PercentOutput, -1.0);
            // } else {
            //     RobotMap.cargoRoller.set(ControlMode.PercentOutput, 0.0);
            // }
            
            // if (RobotMap.pilotController.getButtonA()) {
            //     RobotMap.driveLeftFront.set(ControlMode.PercentOutput, 0.5);
            // } else if (RobotMap.pilotController.getButtonB()) {
            //     RobotMap.driveLeftBack.set(ControlMode.PercentOutput, 0.5);
            // } else if (RobotMap.pilotController.getButtonX()) {
            //     RobotMap.driveRightFront.set(ControlMode.PercentOutput, 0.5);
            // } else if (RobotMap.pilotController.getButtonY()) {
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
		RobotMap.limelight.setLightMode(LEDEnum.DISABLED);
	}
}