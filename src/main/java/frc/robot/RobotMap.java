package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

// Our Imports
import frc.robot.Subassemblies.*;
import frc.robot.Utilities.*;
import frc.robot.Utilities.Pixy.*;

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
	public static final String PIXY_NAME = "Main Pixy";
	public static int checksumErrorCount = 0; // not actually a constant.

	// Motion Profiling Constants
	public final static int kSensorUnitsPerRotation = 4096; // How many sensor units per rotation using CTRE Magnetic Encoder.
	public final static double kNeutralDeadband = 0.001; // Motor neutral dead-band, set to the minimum 0.1%.
	public final static int kPrimaryPIDSlot = 0; // any slot [0,3]

	//public final static Gains kGains_MotProf = new Gains( 1.0, 0.0,  0.0, 1023.0/6800.0,  400,  1.00 );

	// Elevator Constants
	public static final double FRONT_INTAKING_CARGO_HEIGHT = 0.0;
	public static final double FRONT_INTAKING_HATCH_HEIGHT = 0.0;
	public static final double FRONT_BOTTOM_PORT_HEIGHT = 0.0;
	public static final double FRONT_SHIP_PORT_HEIGHT = 0.0;
	public static final double FRONT_MID_HATCH_HEIGHT = 0.0;
	public static final double FRONT_MID_PORT_HEIGHT = 0.0;
	public static final double FRONT_TOP_HATCH_HEIGHT = 0.0;
	public static final double FRONT_TOP_PORT_HEIGHT = 0.0;
	public static final double BACK_INTAKING_HATCH_HEIGHT = 0.0;
	public static final double BACK_BOTTOM_PORT_HEIGHT = 0.0;
	public static final double BACK_SHIP_PORT_HEIGHT = 0.0;
	public static final double BACK_MID_HATCH_HEIGHT = 0.0;
	public static final double BACK_MID_PORT_HEIGHT = 0.0;
	public static final double BACK_TOP_HATCH_HEIGHT = 0.0;
	public static final double BACK_TOP_PORT_HEIGHT = 0.0;

	public static final double ARM_OVER_HEIGHT = 0.0;
	
	/////// Declaring ///////
	// Utility Declatations
	public static Timer timer;
	public static Controller driverController;
	public static PixyI2C pixy;
	public static LEDI2C leds;

	/// Motor Controller Declarations ///
	public static WPI_TalonSRX driveLeftFront;
	public static WPI_TalonSRX driveLeftBack;
	public static WPI_TalonSRX driveRightFront;
	public static WPI_TalonSRX driveRightBack;
	
	public static TalonSRX elevatorFront;
	public static TalonSRX elevatorBack;
	public static TalonSRX armPivot;
	public static TalonSRX cargoRoller;

	public static TalonSRX EGL_leftTop;
	public static TalonSRX EGL_leftBottom;
	public static TalonSRX EGL_rightTop;
	public static TalonSRX EGL_rightBottom;

	// Speed Controller Group Declaration
	public static SpeedControllerGroup leftDriveMotors;
	public static SpeedControllerGroup rightDriveMotors;

	// Differential Drive Declaration
	public static DifferentialDrive drive;

	/// Sensor Declarations ///
	public static Encoder leftDriveEncoder;
	public static Encoder rightDriveEncoder;

	public static AnalogPotentiometer armPot;
	public static AnalogPotentiometer EGLPot;

	public static DigitalInput elevatorLimitSwitch;
	public static DigitalInput cargoLeftLimitSwitch;
	public static DigitalInput cargoRightLimitSwitch;

	public static PigeonIMU pidgey;

	//// Sub-System Declarations ////
	public static Arm arm;
	public static Drivetrain drivetrain;
	public static EGL egl;
	public static Elevator elevator;
	public static Intake intake;
	
	public static void init() { // This is to be called in robitInit and instantiates stuff.
		/////// Initilizing ///////
		// Utility Initilization
		timer = new Timer();
		driverController = new Controller(0);
		pixy = new PixyI2C(PIXY_NAME, new I2C(Port.kOnboard, PIXY_ADDRESS));
		leds = new LEDI2C(new I2C(Port.kOnboard, LED_ADDRESS));

		// Motor Controller Initialization
		driveLeftFront = new WPI_TalonSRX(1);
		driveLeftBack = new WPI_TalonSRX(2);
		driveRightFront = new WPI_TalonSRX(3);
		driveRightBack = new WPI_TalonSRX(4);

		driveLeftFront.configFactoryDefault();
		driveLeftBack.configFactoryDefault();
		driveRightFront.configFactoryDefault();
		driveRightBack.configFactoryDefault();
		driveLeftFront.setSafetyEnabled(false);
		driveLeftBack.setSafetyEnabled(false);
		driveRightFront.setSafetyEnabled(false);
		driveRightBack.setSafetyEnabled(false);
		driveRightFront.setInverted(true); // Inverted for the drive
		driveRightBack.setInverted(true);
		
		elevatorFront = new TalonSRX(5);
		elevatorBack = new TalonSRX(6);

		elevatorFront.configFactoryDefault();
		elevatorBack.configFactoryDefault();
		elevatorBack.follow(elevatorFront);

		armPivot = new TalonSRX(7);
		armPivot.configFactoryDefault();

		cargoRoller = new TalonSRX(8);
		cargoRoller.configFactoryDefault();
		cargoRoller.setInverted(true); // Maybe???????

		EGL_leftTop = new TalonSRX(9);
		EGL_leftBottom = new TalonSRX(10);
		EGL_rightTop = new TalonSRX(11);
		EGL_rightBottom = new TalonSRX(12);

		EGL_leftTop.configFactoryDefault();
		EGL_leftBottom.configFactoryDefault();
		EGL_rightTop.configFactoryDefault();
		EGL_rightBottom.configFactoryDefault();

		EGL_rightTop.setInverted(true); // Maybe????????
		EGL_rightBottom.setInverted(true);

		// Motor Group Initilization
		leftDriveMotors = new SpeedControllerGroup(driveLeftFront, driveLeftBack);
		rightDriveMotors = new SpeedControllerGroup(driveRightFront, driveRightBack);

		// Differential Drive Initilization
		drive = new DifferentialDrive(leftDriveMotors, rightDriveMotors);
		drive.setRightSideInverted(false);
		drive.setSafetyEnabled(false);

		// Sensor Initilization
		leftDriveEncoder = new Encoder(1, 2, false, Encoder.EncodingType.k4X);
		rightDriveEncoder = new Encoder(3, 4, true, Encoder.EncodingType.k4X);

		armPot = new AnalogPotentiometer(0);
		EGLPot = new AnalogPotentiometer(1);

		elevatorLimitSwitch = new DigitalInput(0);
		cargoLeftLimitSwitch = new DigitalInput(1);
		cargoRightLimitSwitch = new DigitalInput(2);

		//pidgey = new PigeonIMU(a_talon);

		//// Sub-System Initilization ////
		arm = new Arm();
		drivetrain = new Drivetrain();
		egl = new EGL();
		elevator = new Elevator();
		intake = new Intake();
	}
}
