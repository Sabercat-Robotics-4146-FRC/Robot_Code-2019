package frc.robot.MotionProfiling;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MotionProfile {
    BufferedTrajectoryPointStream trajectoryPointStream = new BufferedTrajectoryPointStream();

    TalonSRX talon;
    double[][] points;

    public MotionProfile(TalonSRX talon) {
        this.talon = talon;
    }

    public void fillPoints(double[][] points, boolean flip, boolean forward) { // false, true;
        forward = true; // remove this when you want it...
        this.points = points;

        TrajectoryPoint point = new TrajectoryPoint();

        trajectoryPointStream.Clear();

        for(int i = 0; i < points.length; i++) {
            double direction = forward ? +1 : -1;
            double positionRot = points[i][0];
            double velocityRPM = points[i][1];
            int durationMilliseconds = (int)points[i][2];

            
        }


    }

    public void fire() {

    }
}