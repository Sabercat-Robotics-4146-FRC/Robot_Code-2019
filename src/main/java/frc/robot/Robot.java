package frc.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class Robot extends SampleRobot {
	final int addr = 84;
	
	Controller controller;
	Timer timer;
	
	I2C pixy;
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
		
		pixy = new I2C(I2C.Port.kOnboard, 84);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 * This autonomous method mostly deals with choosing the auto to execute.
	 */
	@Override
	public void autonomous() { // Might be unneeded...
		
	}

	/**
	 * This function is run once each time the robot enters operator control mode
	 */
	@Override
	public void operatorControl() {
		byte[] ary = new byte[14];
		
		while (isOperatorControl() && isEnabled()) {
			timer.update();
			
			pixy.read(addr, 14, ary);
			
			for( int i = 0; i < 14; i++ ) {
				Dashboard.send("This thing", ary[0]);
			}
			System.out.println();
		}
	}

	/**
	 * Runs during test mode.
	 */
	@Override
	public void test() {
		
	}
}
