package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

// Our Imports
import frc.robot.Utilities.*;
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
	public static final int PIXY_SIGNATURES_USED = 1;
	public static int checksumErrorCount = 0;

	
	/////// Declarations ///////
	// Controller Declarations
	public static Controller driverController;

	public static Timer timer;

	// Motor Controller Declarations
	public static WPI_TalonSRX leftTop;
	public static WPI_TalonSRX leftBottom;
	public static WPI_TalonSRX rightTop;
	public static WPI_TalonSRX rightBottom;

	public static SpeedControllerGroup leftDriveMotors;
	public static SpeedControllerGroup rightDriveMotors;
	
	//// Sub-System Declarations ////
	public static PixyI2C pixy;
	public static DifferentialDrive drive;
	
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

		leftDriveMotors = new SpeedControllerGroup(leftTop, leftBottom);
		rightDriveMotors = new SpeedControllerGroup(rightTop, rightBottom);

		//// Sub-System Initilization ////
		pixy = new PixyI2C("Main", new I2C(Port.kOnboard, PIXY_ADDRESS));

		drive = new DifferentialDrive(leftDriveMotors, rightDriveMotors);
		drive.setSafetyEnabled(false);
	}
}
