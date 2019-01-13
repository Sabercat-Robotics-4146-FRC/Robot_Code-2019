
package frc.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends SampleRobot {
	// final uint16_t PIXY_START_WORD = 0xaa55;
	// #define PIXY_START_WORD_CC          0xaa56
	// #define PIXY_START_WORDX            0x55aa

	long startTime = 0;
	long totalTime = 0;
	int count = 0;

	final int addr = 0x54;
	Controller controller;
	Timer timer;
	
	PixyI2C pixy;
	public Robot() {
		
	}

	/**
	 * Runs once when the robot is powered on and called when you are basically guaranteed that
	 * all WPILIBJ stuff will work.
	 */
	@Override
	public void robotInit() {
		controller = new Controller(0);
		timer = new Timer();
		
		pixy = new PixyI2C("0", new I2C(Port.kOnboard, addr), 1);
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
			timer.update();
			testPixy();
		}
	}

	public void testPixy(){
		PixyBlock test = pixy.readPacket(1);
		if( test != null ){
			test.toDashboard();
		}
	}

	/**
	 * Runs during test mode.
	 */
	@Override
	public void test() {
		
	}
}
