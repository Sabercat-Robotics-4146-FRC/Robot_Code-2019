
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
	PixyPacket[] packet1 = new PixyPacket[7];

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
		
		pixy = new PixyI2C("0", new I2C(Port.kOnboard, addr), packet1);
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
		// byte[] ary = new byte[14];
		
		while (isOperatorControl() && isEnabled()) {
			timer.update();
			testPixy();
			// pixy.read(addr, 14, ary);
			//
			// for( int i = 0; i < ary.length; i++ ) {
			// 	// if(ary[i] != 0 || ary[i] != 0.0) {
			// 	// 	Dashboard.send(Integer.toString(i), ary[i]);
			// 	// }
			// 	System.out.println(Integer.toHexString((int)ary[0]));
			// }
			// //System.out.println();
		}
	}

	public void testPixy(){
		
	}

	// public void testGearPixy() {
	// 	for (int i = 0; i < packet1.length; i++){
	// 		packet1[i] = null;
	// 	}
	// 	//System.out.println("working");
	// 	for (int i = 1; i < 8; i++) {
	// 		startTime = System.nanoTime();
	// 		try {
	// 			packet1[i - 1] = pixy.readPacket(i);
	// 		} catch (PixyException e) {
	// 			System.out.println("Pixy Error: " + i + ", There was an exception...");
	// 		}
	// 		if (packet1[i - 1] == null) {
	// 			//System.out.println("gearPixy Error: " + i + " Null pointer");
	// 			continue;
	// 		}
	// 		xValue = packet1[i - 1].X;
	// 		if (xValue != prevXValue){
	// 			Dashboard.send("Pixy update rate", 1.0/(totalTime*1e-9));
	// 			totalTime = 0;
	// 		}
	// 		totalTime += (System.nanoTime() - startTime);
	// 		prevXValue = xValue;
	// 		Dashboard.send("gearPixy X Value: " + i, packet1[i - 1].X);
	// 		Dashboard.send("gearPixy Y Value: " + i, packet1[i - 1].Y);
	// 		Dashboard.send("gearPixy Width Value: " + i, packet1[i - 1].Width);
	// 		Dashboard.send("gearPixy Height Value: " + i, packet1[i - 1].Height);
	// 		Dashboard.send("gearPixy Error: " + i, "False");

	// 		// totalTime += (System.nanoTime() - startTime);
	// 		// count++;
	// 		// Dashboard.send( "Pixy Update rate", totalTime + " - " + ( totalTime >= 1e+9 ) );
	// 		// if( totalTime >= 1E9 ){
	// 		// 	System.out.println("YO");
	// 		// 	Dashboard.send("Pixy update count", count);
	// 		// 	count = 0;
	// 		// 	totalTime = 0;
	// 		// }

	// 	}
	// }

	/**
	 * Runs during test mode.
	 */
	@Override
	public void test() {
		
	}
}
