package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

// Our Imports
import frc.robot.Utilities.*;
import frc.robot.Subassemblies.LEDI2C;
import frc.robot.Subassemblies.Pixy.*;

public class RobotMap {
	public static Robot ROBOT;
	
	/////// Constants ///////
	// Vision Constants
	public static final int PIXY_UPDATE_RATE = 50; //Htz
	public static final double VISION_BREAK_TOLERANCE = 1.5; //degrees?
	public static final double VISION_kP = 1.0;
	public static final double VISION_kI = 0.0;
	public static final double VISION_kD = 0.0;
	public static final int PIXY_ADDRESS = 0x54;
	public static final int LED_ADDRESS = 0x55;
	public static final int PIXY_SIGNATURES_USED = 1; // Unused? 
	public static final String PIXY_NAME = "Main Pixy";
	public static int checksumErrorCount = 0; // not actually a constant.

	
	/////// Declarations ///////
	// Controller Declarations
	public static Controller driverController;

	public static Timer timer;

	// Motor Controller Declarations
	public static WPI_TalonSRX leftTop;
	public static WPI_TalonSRX leftBottom;
	public static WPI_TalonSRX rightTop;
	public static WPI_TalonSRX rightBottom;
	
	public static TalonSRX elevatorTop;
	public static TalonSRX elevatorBottom;
	public static TalonSRX arm;

	// Differential Drive Speed Controller Declaration
	public static SpeedControllerGroup leftDriveMotors;
	public static SpeedControllerGroup rightDriveMotors;

	//// Sub-System Declarations ////
	public static PixyI2C pixy;
	public static LEDI2C leds;
	public static DifferentialDrive drive; // is this meant to be here?

	
	public static void init() { // This is to be called in robitInit and instantiates stuff.
		/////// Initilizing ///////
		// Controllers Initialization
		driverController = new Controller(0);

		timer = new Timer();

		// Motor Controllers Initialization

		leftTop = new WPI_TalonSRX(1);
		leftBottom = new WPI_TalonSRX(2);
		rightTop = new WPI_TalonSRX(3);
		rightBottom = new WPI_TalonSRX(4);
			
		rightTop.setInverted(true); 
		rightBottom.setInverted(true);

		leftTop.setSafetyEnabled(false);
    	rightTop.setSafetyEnabled(false);
    	leftBottom.setSafetyEnabled(false);
    	rightBottom.setSafetyEnabled(false);
		
		elevatorTop = new TalonSRX(5);
		elevatorBottom = new TalonSRX(6);

		elevatorTop.configFactoryDefault();
		elevatorBottom.configFactoryDefault();
		elevatorBottom.follow(elevatorTop);

		arm = new TalonSRX(7);
		arm.configFactoryDefault();

		SpeedControllerGroup leftDriveMotors = new SpeedControllerGroup(leftTop, leftBottom); // where should I put these?
		SpeedControllerGroup rightDriveMotors = new SpeedControllerGroup(rightTop, rightBottom);

		//// Sub-System Initilization ////
		pixy = new PixyI2C(PIXY_NAME, new I2C(Port.kOnboard, PIXY_ADDRESS));

		leds = new LEDI2C(new I2C(Port.kOnboard, LED_ADDRESS));

		drive = new DifferentialDrive(leftDriveMotors, rightDriveMotors);

		drive.setRightSideInverted(false);
		drive.setSafetyEnabled(false);
		

		// TODO
		// drive = new DifferentialDrive(leftDriveMotors, rightDriveMotors);
		/* diff drive assumes (by default) that 
			right side must be negative to move forward.
			Change to 'false' so positive/green-LEDs moves robot forward.
			Make things go the right way by using motor controller inversions. 
			https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/SixTalonArcadeDrive/src/main/java/frc/robot/Robot.java*/
		// _drive.setRightSideInverted(false);
		// drive.setSafetyEnabled(false);
	}
}
