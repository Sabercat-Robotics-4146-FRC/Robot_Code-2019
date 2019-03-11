
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
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
		UsbCamera cameraOne = CameraServer.getInstance().startAutomaticCapture(0);
		UsbCamera cameraTwo =CameraServer.getInstance().startAutomaticCapture(1);
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
			if (!RobotMap.compressor.getCompressorNotConnectedFault()) {
				RobotMap.compressor.setClosedLoopControl(true);
			} else {
				RobotMap.compressor.setClosedLoopControl(false);
			}

			RobotMap.limelight.update();
			RobotMap.timer.update();
			RobotMap.teleopControls.update();

			// RobotMap.arm.update();
			// RobotMap.drivetrain.update();
            // RobotMap.elevator.update();
			RobotMap.intake.update(RobotMap.timer.getDT());
			
			ConsoleLogger.update(RobotMap.timer.getDT());
			RobotMap.pilotController.updateRumbleBuzz(RobotMap.timer.getDT());
			
			if (RobotMap.pilotController.getButtonBack()) {
				RobotMap.elevatorFront.setSelectedSensorPosition(0);
			}

			Dashboard.send("Elevator Pos", RobotMap.elevatorFront.getSelectedSensorPosition());
			Dashboard.send("Elevator Error", RobotMap.elevatorFront.getClosedLoopError());
			Dashboard.send("Arm Pos", RobotMap.armPivot.getSelectedSensorPosition());
			Dashboard.send("Arm Error", RobotMap.armPivot.getClosedLoopError());

			Dashboard.send("Elevator LS", RobotMap.elevatorLimitSwitch.get());
			Dashboard.send("Hatch LS", RobotMap.hatchLimitSwitch.get());
			Dashboard.send("Cargo LS", RobotMap.cargoLimitSwitch.get());

            // RobotMap.pilotController.updateRumbleBuzz(RobotMap.timer.getDT());

            // RobotMap.EGL_leftFront.set(ControlMode.PercentOutput, RobotMap.pilotController.getRightTrigger() - RobotMap.pilotController.getLeftTrigger());

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
		RobotMap.limelight.update();
	}
}