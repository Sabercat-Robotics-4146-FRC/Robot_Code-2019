
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

		boolean hatchFlag = true;

		while (isOperatorControl() && isEnabled()) {
			RobotMap.limelight.update();
			RobotMap.timer.update();
			RobotMap.teleopControls.update();

			RobotMap.arm.update();
			RobotMap.drivetrain.update();
            RobotMap.elevator.update();
			RobotMap.intake.update(RobotMap.timer.getDT());
			
			ConsoleLogger.update(RobotMap.timer.getDT());
			RobotMap.pilotController.updateRumbleBuzz(RobotMap.timer.getDT());

			if (RobotMap.pilotController.getDPadBool() && hatchFlag) {
				hatchFlag = false;
				if (RobotMap.clawSolenoid.get()) {
					RobotMap.clawSolenoid.set(false);
				} else {
					RobotMap.clawSolenoid.set(true);
				}
			}

			if (!RobotMap.pilotController.getDPadBool()) {
				hatchFlag = true;
			}

			Dashboard.send("Elevator Pos", RobotMap.elevatorFront.getSelectedSensorPosition());
			Dashboard.send("Elevator Error", RobotMap.elevatorFront.getClosedLoopError());
			Dashboard.send("Arm Pos", RobotMap.armPivot.getSelectedSensorPosition());
			Dashboard.send("Arm Error", RobotMap.armPivot.getClosedLoopError());

			Dashboard.send("Elevator LS", RobotMap.elevatorLimitSwitch.get());
			Dashboard.send("Hatch LS", RobotMap.hatchLimitSwitch.get());
			Dashboard.send("Cargo LS", RobotMap.cargoLimitSwitch.get());
		}
	}

	/**
	 * Runs during test mode.
	 */
	@Override
	public void test() {
		while(isTest() && isEnabled()) {
			RobotMap.timer.update();
			ConsoleLogger.update(RobotMap.timer.getDT());

			//Arm - Left Joystick Y Axis
			RobotMap.armPivot.set(ControlMode.PercentOutput, RobotMap.pilotController.getDeadbandLeftYAxis());

			// Elevator - Right Joystick Y Axis
			RobotMap.elevatorFront.set(ControlMode.PercentOutput, RobotMap.pilotController.getDeadbandRightYAxis());

			if (RobotMap.pilotController.getButtonBack()) {
				RobotMap.elevatorFront.setSelectedSensorPosition(0);
			}

			// EGL - Right Trigger, Raise Robot; Left Trigger, Lower Robot
			RobotMap.EGL_leftFront.set(ControlMode.PercentOutput, RobotMap.pilotController.getLeftTrigger()
					 - RobotMap.pilotController.getRightTrigger());

			// Compressor
			if (!RobotMap.compressor.getCompressorNotConnectedFault()) {
				RobotMap.compressor.setClosedLoopControl(true);
			} else {
				RobotMap.compressor.setClosedLoopControl(false);
			}

			if (RobotMap.pilotController.getButtonStart()) {
				RobotMap.compressor.clearAllPCMStickyFaults();
			}

			Dashboard.send("Elevator Pos", RobotMap.elevatorFront.getSelectedSensorPosition());
			Dashboard.send("Elevator Error", RobotMap.elevatorFront.getClosedLoopError());
			Dashboard.send("Arm Pos", RobotMap.armPivot.getSelectedSensorPosition());
			Dashboard.send("Arm Error", RobotMap.armPivot.getClosedLoopError());

			Dashboard.send("Elevator LS", RobotMap.elevatorLimitSwitch.get());
			Dashboard.send("Hatch LS", RobotMap.hatchLimitSwitch.get());
			Dashboard.send("Cargo LS", RobotMap.cargoLimitSwitch.get());

			Dashboard.send("Compressor Enabled", RobotMap.compressor.enabled());
			Dashboard.send("Pressure Switch Triggered", RobotMap.compressor.getPressureSwitchValue());
			Dashboard.send("Compressor Current(Amps)", RobotMap.compressor.getCompressorCurrent());
			Dashboard.send("Compressor Connected", !RobotMap.compressor.getCompressorNotConnectedFault());

			if (RobotMap.compressor.getCompressorCurrentTooHighFault()) {
				ConsoleLogger.error("Compressor Current too High!");
			}
			if (RobotMap.compressor.getCompressorCurrentTooHighStickyFault()) {
				ConsoleLogger.error("Compressor Current too High Sticky Fault!");
			}
			if (RobotMap.compressor.getCompressorShortedFault()) {
				ConsoleLogger.error("Compressor Output appears to be shorted!");
			}
			if (RobotMap.compressor.getCompressorShortedStickyFault()) {
				ConsoleLogger.error("Compressor Output appears to be shorted and sticky faulted!");
			}
		}
		
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