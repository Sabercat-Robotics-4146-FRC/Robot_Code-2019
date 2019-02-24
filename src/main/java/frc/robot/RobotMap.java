package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

// Our Imports
import frc.robot.Subassemblies.*;
import frc.robot.Utilities.*;
import frc.robot.Utilities.PID.VisionTurningPID;
import frc.robot.Utilities.Pixy.*;

public class RobotMap {
	public static Robot ROBOT;
	public static TeleopControls teleopControls;
	
	/////// Constants ///////
	// Vision Constants
	public static final int PIXY_UPDATE_RATE = 50; //Htz
	public static final int LIMELIGHT_UPDATE_RATE = 90; //Htz
	public static final double VISION_BREAK_TOLERANCE = 0;
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

	public static final double LIMELIGHT_PORT_TAPE_HEIGHT = 0.0;

	// Arm Constants
	public static final double FRONT_ARM_CLEAR_VALUE = 0.0;
	public static final double BACK_ARM_CLEAR_VALUE = 0.0;
	
	// Intake Constants
	public static final double CARGO_ROLLER_INTAKING_SPEED = 0.7;
	public static final double CARGO_ROLLER_OUTPUTTING_SPEED = -0.8;
	public static final double INTAKE_PISTON_EXTENSION_TIME = 0.5;

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
	public static TalonSRX intakeConveyor;

	public static TalonSRX EGL_leftFront;
	public static TalonSRX EGL_leftBack;
	public static TalonSRX EGL_rightFront;
	public static TalonSRX EGL_rightBack;

	// Speed Controller Group Declaration
	public static SpeedControllerGroup leftDriveMotors;
	public static SpeedControllerGroup rightDriveMotors;

	// Compressor Declaration
	public static Compressor compressor;

	// Solenoid Declaration
	public static Solenoid intakeSolenoid;

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

	// public static PigeonIMU pidgey; // TODO

	public static Limelight limelight;

	//// Sub-System Declarations ////
	public static Arm arm;
	public static Drivetrain drivetrain;
	public static EndGameLift egl;
	public static Elevator elevator;
    public static Intake intake;
    
	public static void init() { // This is to be called in robitInit and instantiates stuff.
		/////// Initilizing ///////
		teleopControls = new TeleopControls(); // move this

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

		intakeConveyor = new TalonSRX(9);
		intakeConveyor.configFactoryDefault();

		EGL_leftFront = new TalonSRX(10);
		EGL_leftBack = new TalonSRX(11);
		EGL_rightFront = new TalonSRX(12);
		EGL_rightBack = new TalonSRX(13);

		EGL_leftFront.configFactoryDefault();
		EGL_leftBack.configFactoryDefault();
		EGL_rightFront.configFactoryDefault();
		EGL_rightBack.configFactoryDefault();

		EGL_rightFront.setInverted(true); // Maybe????????
		EGL_rightBack.setInverted(true);

		EGL_leftBack.follow(EGL_leftFront);
		EGL_rightFront.follow(EGL_leftFront);
		EGL_rightBack.follow(EGL_leftFront);

		// Motor Group Initilization
		leftDriveMotors = new SpeedControllerGroup(driveLeftFront, driveLeftBack);
		rightDriveMotors = new SpeedControllerGroup(driveRightFront, driveRightBack);

		// Differential Drive Initilization
		drive = new DifferentialDrive(leftDriveMotors, rightDriveMotors);
		drive.setRightSideInverted(false);
		drive.setSafetyEnabled(false);

		// Compressor Initilization
		compressor = new Compressor();

		// Solenoid Initilization
		intakeSolenoid = new Solenoid(0);

		// Sensor Initilization
		leftDriveEncoder = new Encoder(3, 4, false, Encoder.EncodingType.k4X);
		rightDriveEncoder = new Encoder(5, 6, true, Encoder.EncodingType.k4X);

		armPot = new AnalogPotentiometer(0);
		EGLPot = new AnalogPotentiometer(1);

		elevatorLimitSwitch = new DigitalInput(0);
		cargoLeftLimitSwitch = new DigitalInput(1);
		cargoRightLimitSwitch = new DigitalInput(2);

		// pidgey = new PigeonIMU(a_talon); TODO

		limelight = new Limelight();

		//// Sub-System Initilization ////
		arm = new Arm();
		drivetrain = new Drivetrain();
		egl = new EndGameLift();
		elevator = new Elevator();
		intake = new Intake();
	}
}
