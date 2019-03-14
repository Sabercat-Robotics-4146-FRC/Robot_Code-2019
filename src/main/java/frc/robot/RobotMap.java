package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

// Our Imports
import frc.robot.Subassemblies.*;
import frc.robot.Utilities.*;
import frc.robot.Utilities.Pixy.*;

public class RobotMap {
	public static Robot ROBOT;
	public static TeleopControls teleopControls;
	
	/////// Constants ///////
	// Vision Constants
	public static final int PIXY_UPDATE_RATE = 50; //Htz
	public static final double PIXY_BREAK_TOLERANCE = 0;
	public static final double PIXY_kP = 0.05;
	public static final double PIXY_kI = 0.0;
	public static final double PIXY_kD = 0.0;
	public static final int PIXY_ADDRESS = 0x54;
	public static final String PIXY_NAME = "Main Pixy";
	public static int checksumErrorCount = 0; // not actually a constant.

	public static final int LIMELIGHT_UPDATE_RATE = 90; //Htz
	public static final double LIMELIGHT_BREAK_TOLERANCE = 0;
	public static final double LIMELIGHT_kP = 0.16;
	public static final double LIMELIGHT_kI = 0.0;
	public static final double LIMELIGHT_kD = 0.0;
	
	public static final int LED_ADDRESS = 0x55;

	//public final static Gains kGains_MotProf = new Gains( 1.0, 0.0,  0.0, 1023.0/6800.0,  400,  1.00 );

	// Elevator Constants
	public static final int FRONT_INTAKING_CARGO_HEIGHT = 0;//
	public static final int FRONT_INTAKING_HATCH_HEIGHT = 0;//
	public static final int FRONT_BOTTOM_PORT_HEIGHT = 10508;//
	public static final int FRONT_SHIP_PORT_HEIGHT = 23434;
	public static final int FRONT_MID_HATCH_HEIGHT = 38310;//
	public static final int FRONT_MID_PORT_HEIGHT = 39890;//
	public static final int FRONT_TOP_HATCH_HEIGHT = 77896;//
	public static final int FRONT_TOP_PORT_HEIGHT = 74348;//

	public static final int BACK_INTAKING_HATCH_HEIGHT = 11297;//
	public static final int BACK_BOTTOM_PORT_HEIGHT = 12603;//
	public static final int BACK_SHIP_PORT_HEIGHT = 23434;
	public static final int BACK_MID_HATCH_HEIGHT = 44571;//
	public static final int BACK_MID_PORT_HEIGHT = 54143;//
	public static final int BACK_TOP_HATCH_HEIGHT = 76312;//
	public static final int BACK_TOP_PORT_HEIGHT = 76125;//

	public static final int ARM_OVER_HEIGHT = 35122 + 5000; // WHEN SETTING THIS VALUE ADD LIKE 5000 TO THE NUMBER

	public static final int LIMELIGHT_PORT_TAPE_HEIGHT = 0;

	public static final int LOWER_SLOWER_HEIGHT = 10508;

	public static final double ELEVATOR_kP = 2.54; // when testing without the arm use p: 0.07 d: 1.0 f: 0.0
	public static final double ELEVATOR_kI = 0.00;
	public static final double ELEVATOR_kD = 0.00;
	public static final double ELEVATOR_kF = 1.18;

	// Arm Constants
	public static final int ARM_FRONT_CARGO_INTAKING_POSITION = 627; // 688 was what we did the values off of //650 // 634
	public static final int ARM_FRONT_LEVEL_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 10; // 683; // -5
	public static final int ARM_FRONT_TILT_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 50; // 588; // -100
	public static final int ARM_FRONT_CARGO_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 76; // 612; // -76					// bottom 644//mid628//top614
	public static final int ARM_FRONT_BOTTOM_CARGO_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 44; // 644; // -44
	public static final int ARM_FRONT_MID_CARGO_POSITION =  ARM_FRONT_CARGO_INTAKING_POSITION - 60; // 628; // -60
	public static final int ARM_FRONT_TOP_CARGO_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 74; // 614; // -74

	public static final int ARM_BACK_LEVEL_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 240; // 433; //-255
	public static final int ARM_BACK_TILT_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 255; // 463; // -225
	public static final int ARM_BACK_CARGO_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 280; // 408; // -280
	public static final int ARM_BACK_BOTTOM_CARGO_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 280; // 408; // -280
	public static final int ARM_BACK_MID_CARGO_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 280; // 408; // -280
	public static final int ARM_BACK_TOP_CARGO_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 280; // 408; // -208
	public static final int ARM_BACK_HATCH_TOP_TILT = ARM_FRONT_CARGO_INTAKING_POSITION - 217; // 297

	// these are arbitrary right now
	public static final int ARM_FRONT_CARGO_SHIP_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 60; // 628; // -60		these are arbitrary right now
	public static final int ARM_BACK_CARGO_SHIP_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 280; // 408; // -280			these are arbitrary right now

	public static final int ARM_FRONT_STORAGE_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 100; // 588; // -100
	public static final int ARM_BACK_STORAGE_POSITION = ARM_FRONT_CARGO_INTAKING_POSITION - 255; // 463; // -225

	public static final int FRONT_ARM_CLEAR_VALUE = ARM_FRONT_CARGO_INTAKING_POSITION - 100; // 588; // -100
	public static final int BACK_ARM_CLEAR_VALUE = ARM_FRONT_CARGO_INTAKING_POSITION - 225; // 463; // -225

	public static final int MIDDLE_ARM_VALUE = ARM_FRONT_CARGO_INTAKING_POSITION - 132; // 556; // -132

	public static final double ARM_kP = 25.0;
	public static final double ARM_kI = 0.0;
	public static final double ARM_kD = 0.0;
	
	// Intake Constants
	public static final double CARGO_ROLLER_INTAKING_SPEED = 0.7;
	public static final double CARGO_ROLLER_OUTPUTTING_SPEED = -0.8;
    public static final double CLAW_RELEASE_TIME = 1.0;
    
    // End Game Lift Constants
    public static final double EGL_UPPER_SOFT_STOP = 5.0;
    public static final double EGL_LOWER_SOFT_STOP = 1.0;
    public static final double ELG_RAISING_SPEED = 8.0;
    public static final double ELG_LOWERING_SPEED = 5.0;

	/////// Declaring ///////
	// Utility Declatations
	public static Timer timer;
    public static Controller pilotController;
    public static Controller copilotController;
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
	public static Solenoid clawSolenoid;

	// Differential Drive Declaration
	public static DifferentialDrive drive;

	/// Sensor Declarations ///
	public static Encoder leftDriveEncoder;
	public static Encoder rightDriveEncoder;

	public static AnalogPotentiometer armPot;
	public static AnalogPotentiometer EGLPot;

	public static DigitalInput elevatorLimitSwitch;
	public static DigitalInput cargoLimitSwitch;
	public static DigitalInput hatchLimitSwitch;

	// public static PigeonIMU pidgey; // TODO

	public static Limelight limelight;

	// Digitial Outputs Declarations
	public static DigitalOutput pixyLEDs;

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
        pilotController = new Controller(0);
        copilotController = new Controller(1);
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
        
        driveLeftFront.setNeutralMode(NeutralMode.Brake);
        driveLeftBack.setNeutralMode(NeutralMode.Brake);
        driveRightFront.setNeutralMode(NeutralMode.Brake);
        driveRightBack.setNeutralMode(NeutralMode.Brake);
		
		elevatorFront = new TalonSRX(5);
		elevatorBack = new TalonSRX(6);

		elevatorFront.configFactoryDefault();
		elevatorBack.configFactoryDefault();
        elevatorBack.follow(elevatorFront);
        
		elevatorFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

		elevatorFront.setInverted(true);
		elevatorBack.setInverted(true);
		elevatorFront.setSensorPhase(true);
		
		elevatorFront.config_kP(0, ELEVATOR_kP);
		elevatorFront.config_kI(0, ELEVATOR_kI);
		elevatorFront.config_kD(0, ELEVATOR_kD);
		elevatorFront.config_kP(0, ELEVATOR_kF);

		armPivot = new TalonSRX(7);
		armPivot.configFactoryDefault();

		armPivot.configSelectedFeedbackSensor(FeedbackDevice.Analog);
		armPivot.setNeutralMode(NeutralMode.Brake);

		armPivot.config_kP(0, ARM_kP);
		armPivot.config_kI(0, ARM_kI);
		armPivot.config_kD(0, ARM_kD);

		cargoRoller = new TalonSRX(8);
		cargoRoller.configFactoryDefault();
		cargoRoller.setNeutralMode(NeutralMode.Brake);

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
		clawSolenoid = new Solenoid(0);

		// Sensor Initilization
		leftDriveEncoder = new Encoder(3, 4, false, Encoder.EncodingType.k4X);
		rightDriveEncoder = new Encoder(5, 6, true, Encoder.EncodingType.k4X);

		armPot = new AnalogPotentiometer(0);
		EGLPot = new AnalogPotentiometer(1);

		elevatorLimitSwitch = new DigitalInput(0);
		cargoLimitSwitch = new DigitalInput(1);
		hatchLimitSwitch = new DigitalInput(2);

		// pidgey = new PigeonIMU(a_talon); TODO

		limelight = new Limelight();

		// Digital Output Initilization
		pixyLEDs = new DigitalOutput(7);

		//// Sub-System Initilization ////
		arm = new Arm();
		drivetrain = new Drivetrain();
		egl = new EndGameLift();
		elevator = new Elevator();
		intake = new Intake();
	}
}
