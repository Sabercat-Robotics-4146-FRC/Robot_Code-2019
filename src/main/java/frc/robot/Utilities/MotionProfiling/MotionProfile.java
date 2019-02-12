package frc.robot.Utilities.MotionProfiling;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotMap;
import frc.robot.Utilities.Dashboard;

public class MotionProfile {

	public enum MotionProfileEnum {
		DISABLE,
		ENABLE
	}

	MotionProfileEnum motionProfileState = MotionProfileEnum.DISABLE;

    /** new class type in 2019 for holding MP buffer. */
    BufferedTrajectoryPointStream trajectoryPointStream = new BufferedTrajectoryPointStream();
    
    TalonSRX talon;

    public MotionProfile(TalonSRX talon) {
        this.talon = talon;
    }

    public void initBuffer(double[][] profile, int totalCnt) {

		boolean forward = true; // set to false to drive in opposite direction of profile (not really needed
								// since you can use negative numbers in profile).

		TrajectoryPoint point = new TrajectoryPoint(); // temp for for loop, since unused params are initialized
													   // automatically, you can alloc just one

		// clear the buffer, in case it was used elsewhere 
		trajectoryPointStream.Clear();

		// Insert every point into buffer, no limit on size 
		for (int i = 0; i < totalCnt; ++i) {

			double direction = forward ? +1 : -1;
			double positionRot = profile[i][0];
			double velocityRPM = profile[i][1];
			int durationMilliseconds = (int) profile[i][2];

			// for each point, fill our structure and pass it to API 
			point.timeDur = durationMilliseconds;
			point.position = direction * positionRot * RobotMap.kSensorUnitsPerRotation; // Convert Revolutions to
																						  // Units
			point.velocity = direction * velocityRPM * RobotMap.kSensorUnitsPerRotation / 600.0; // Convert RPM to
																								  // Units/100ms
			point.auxiliaryPos = 0;
			point.auxiliaryVel = 0;
			point.profileSlotSelect0 = RobotMap.kPrimaryPIDSlot; // which set of gains would you like to use [0,3]? 
			point.profileSlotSelect1 = 0; // auxiliary PID [0,1], leave zero 
			point.zeroPos = (i == 0); // set this to true on the first point 
			point.isLastPoint = ((i + 1) == totalCnt); // set this to true on the last point 
			point.arbFeedFwd = 0; // you can add a constant offset to add to PID[0] output here 

			trajectoryPointStream.Write(point);
		}
    }

    public void startProfile(double[][] profile, int totalCnt) {
        initBuffer(profile, totalCnt);
		Dashboard.send("Motion Profile setVaue", motionProfileState.toString());
		if (motionProfileState == MotionProfileEnum.DISABLE) {
			motionProfileState = MotionProfileEnum.ENABLE;
			System.out.println("I am running a profile");
			talon.startMotionProfile(trajectoryPointStream, 10, ControlMode.MotionProfile);
        	System.out.println("MP started");
		}
        
    }
    
    public boolean isFinished() {
		return talon.isMotionProfileFinished();
	}

	public void disableMotionProfile() { // this is had be called before another profile can br started
		motionProfileState = MotionProfileEnum.DISABLE;
		Dashboard.send("Motion Profile setVaue", motionProfileState.toString());
		
	}
}