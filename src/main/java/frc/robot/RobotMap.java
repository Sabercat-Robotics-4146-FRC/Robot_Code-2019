package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

// Our Imports
import frc.robot.Utilities.*;
import frc.robot.Subassemblies.LEDI2C;
import frc.robot.Subassemblies.Pixy.*;
import frc.robot.Subassemblies.*;

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

	// Motion Profiling Constants
	public final static int kSensorUnitsPerRotation = 4096; // How many sensor units per rotation using CTRE Magnetic Encoder.
	public final static double kNeutralDeadband = 0.001; // Motor neutral dead-band, set to the minimum 0.1%.
	public final static int kPrimaryPIDSlot = 0; // any slot [0,3]

	//public final static Gains kGains_MotProf = new Gains( 1.0, 0.0,  0.0, 1023.0/6800.0,  400,  1.00 );
	
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

	public static TalonSRX motionProfileTalon; // for testing get rid
	public static TalonSRXConfiguration config; 

	// Differential Drive Speed Controller Declaration
	public static SpeedControllerGroup leftDriveMotors;
	public static SpeedControllerGroup rightDriveMotors;

	public static NetworkTable table;
	public static NetworkTableEntry tx;

	//// Sub-System Declarations ////
	public static PixyI2C pixy;
	public static LEDI2C leds;
	public static DifferentialDrive drive; // is this meant to be here?

	public static Elevator elevator;

	
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

		motionProfileTalon = new TalonSRX(12);

		// config = new TalonSRXConfiguration(); // factory default settings
		// /* _config the master specific settings */
        // config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
    	// config.neutralDeadband = kNeutralDeadband; /* 0.1 % super small for best low-speed control */
        // config.slot0.kF = kGains_MotProf.kF;
        // config.slot0.kP = kGains_MotProf.kP;
        // config.slot0.kI = kGains_MotProf.kI;
        // config.slot0.kD = kGains_MotProf.kD;
        // config.slot0.integralZone = (int) kGains_MotProf.kIzone;
        // config.slot0.closedLoopPeakOutput = kGains_MotProf.kPeakOutput;
        // // config.slot0.allowableClosedloopError // left default for this example
		// // config.slot0.maxIntegralAccumulator; // left default for this example
        // // config.slot0.closedLoopPeriod; // left default for this example
        // motionProfileTalon.configAllSettings(config);

        // /* pick the sensor phase and desired direction */
        // motionProfileTalon.setSensorPhase(true);
		// motionProfileTalon.setInverted(false);


		SpeedControllerGroup leftDriveMotors = new SpeedControllerGroup(leftTop, leftBottom); // where should I put these?
		SpeedControllerGroup rightDriveMotors = new SpeedControllerGroup(rightTop, rightBottom);

		table = NetworkTableInstance.getDefault().getTable("limelight");
		tx = table.getEntry("tx");

		//// Sub-System Initilization ////
		pixy = new PixyI2C(PIXY_NAME, new I2C(Port.kOnboard, PIXY_ADDRESS));

		leds = new LEDI2C(new I2C(Port.kOnboard, LED_ADDRESS));

		drive = new DifferentialDrive(leftDriveMotors, rightDriveMotors);

		drive.setRightSideInverted(false);
		drive.setSafetyEnabled(false);

		elevator = new Elevator();
		

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
