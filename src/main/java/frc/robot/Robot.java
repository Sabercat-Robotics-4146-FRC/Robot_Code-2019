
package frc.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

import java.io.IOException;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

import frc.robot.Utilities.Dashboard;
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
		TalonSRXConfiguration config = new TalonSRXConfiguration();

		/**
		 * PID Gains may have to be adjusted based on the responsiveness of control loop
		 * 	           			  kP   kI    kD     kF(why)      Iz   PeakOut */
		Gains kGains = new Gains( 1.0, 0.0, 0.0, 1023.0/6800.0, 400, 1.00 );

		/* _config the master specific settings */
        config.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Absolute;
        config.neutralDeadband = 0.001; /* 0.1 % super small for best low-speed control. the min value. */
        config.slot0.kF = kGains.kF;
        config.slot0.kP = kGains.kP;
        config.slot0.kI = kGains.kI;
        config.slot0.kD = kGains.kD;
        config.slot0.integralZone = (int)kGains.kIzone;
        config.slot0.closedLoopPeakOutput = kGains.kPeakOutput;
        // _config.slot0.allowableClosedloopError // left default for this example
        // _config.slot0.maxIntegralAccumulator; // left default for this example
        // _config.slot0.closedLoopPeriod; // left default for this example
		RobotMap.elevatorTop.configAllSettings(config);

		while (isOperatorControl() && isEnabled()) {
			// RobotMap.timer.update();
			
			// RobotMap.elevatorTop.set(ControlMode.PercentOutput, RobotMap.driverController.getDeadbandLeftYAxis());
			// RobotMap.arm.set(ControlMode.PercentOutput, RobotMap.driverController.getDeadbandLeftYAxis());

			

			// try{
			// 	Logger.update(RobotMap.timer.getDT());
			// } catch(IOException e){
			// 	System.out.println("Logger IOException: " + e);
			// }

			// Drive Code
			// <editor-fold>
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
