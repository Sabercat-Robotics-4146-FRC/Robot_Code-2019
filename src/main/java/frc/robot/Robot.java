
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import frc.robot.Subassemblies.ElevatorAndArm.ScoringPosition;
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
		// UsbCamera cameraOne = CameraServer.getInstance().startAutomaticCapture(0);
		// UsbCamera cameraTwo = CameraServer.getInstance().startAutomaticCapture(1);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 * This autonomous method mostly deals with choosing the auto to execute.
	 */
	@Override
	public void autonomous() {


        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_STORAGE);

		while (isAutonomous() && isEnabled()) {
			RobotMap.limelight.update();
			RobotMap.timer.update();
			RobotMap.teleopControls.update();

			RobotMap.drivetrain.update();
            RobotMap.elevatorAndArm.update();
			RobotMap.intake.update(RobotMap.timer.getDT());
			
			ConsoleLogger.update(RobotMap.timer.getDT());
			RobotMap.pilotController.updateRumbleBuzz(RobotMap.timer.getDT());

			// These are not in the update loops of their coresponding assemblies
			// because we need to view the values even when we dont want the rest
			// of the assembly to be updating.
			Dashboard.send("Elevator Pos", RobotMap.elevatorFront.getSelectedSensorPosition());
			Dashboard.send("Elevator Error", RobotMap.elevatorFront.getClosedLoopError());
			Dashboard.send("Arm Pos", RobotMap.armPivot.getSelectedSensorPosition());
            Dashboard.send("Arm Error", RobotMap.armPivot.getClosedLoopError());
            Dashboard.send("Arm Offset", RobotMap.armOffset);

			Dashboard.send("Elevator LS", RobotMap.elevatorLimitSwitch.get());
			Dashboard.send("Hatch LS", RobotMap.hatchLimitSwitch.get());
            Dashboard.send("Cargo LS", RobotMap.cargoLimitSwitch.get());
            
            Dashboard.send("Actual State", RobotMap.elevatorAndArm.getCurrentState().toString());
            Dashboard.send("Final State", RobotMap.elevatorAndArm.getFinalState().toString());
		}
	}

	/**
	 * This function is run once each time the robot enters operator control mode
	 */
	@Override
	public void operatorControl() {
        RobotMap.elevatorAndArm.setFinalAndCurrentStates(ScoringPosition.IDLE);
		while (isOperatorControl() && isEnabled()) {
			RobotMap.limelight.update();
			RobotMap.timer.update();
			RobotMap.teleopControls.update();

			RobotMap.drivetrain.update();
            
            RobotMap.elevatorAndArm.update();
            RobotMap.intake.update(RobotMap.timer.getDT());
			
			ConsoleLogger.update(RobotMap.timer.getDT());
            RobotMap.pilotController.updateRumbleBuzz(RobotMap.timer.getDT());

			// These are not in the update loops of their coresponding assemblies
			// because we need to view the values even when we dont want the rest
			// of the assembly to be updating.
			Dashboard.send("Elevator Pos", RobotMap.elevatorFront.getSelectedSensorPosition());
			Dashboard.send("Elevator Error", RobotMap.elevatorFront.getClosedLoopError());
			Dashboard.send("Arm Pos", RobotMap.armPivot.getSelectedSensorPosition());
			Dashboard.send("Arm Error", RobotMap.armPivot.getClosedLoopError());

			Dashboard.send("Elevator LS", RobotMap.elevatorLimitSwitch.get());
			Dashboard.send("Hatch LS", RobotMap.hatchLimitSwitch.get());
            Dashboard.send("Cargo LS", RobotMap.cargoLimitSwitch.get());
            
            // Dashboard.send("Actual State", RobotMap.elevatorAndArm.getCurrentState().toString());
            // Dashboard.send("Final State", RobotMap.elevatorAndArm.getFinalState().toString());

		}
	}

	/**
	 * Runs during test mode.
	 */
	@Override
	public void test() {
		System.out.println("\n\n=====================Testing Controls=====================");
		System.out.println("Arm:\n\t- Pivot for the Arm Motor:\tLeft Joystick Y Axis");
		System.out.println("Elevator:\n\t- Elevator up and down:\tRight Joystick Y Axis\n\t- Reset Elevator Encoder:\tBack Button");
		System.out.println("End Game Lift:\n\t- Raiseing for EGL:\tRight Trigger\n\t- Lowering for EGL:\tLeft Trigger");
		System.out.println("Compressor:\n\t- The compressor will run when plugged in.");
		System.out.println("\n\n*****************************");
		System.out.println("*!!!!!!!!!!WARNING!!!!!!!!!!*");
		System.out.println("*YOU HAVE COMPLETE CONTROL  *");
		System.out.println("*OVER THE ROBOT! IF YOU ARE *");
		System.out.println("*NOT CAREFUL YOU WILL BREAK *");
		System.out.println("*THINGS!!!!!!!!!!!!!!!!!!!!!*");
		System.out.println("*****************************\n\n");
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