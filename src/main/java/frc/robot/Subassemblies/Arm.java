package frc.robot.Subassemblies;

import frc.robot.RobotMap;
import frc.robot.TeleopControls;
import frc.robot.Subassemblies.Elevator.DirectionEnum;
import frc.robot.Utilities.Logger;
import frc.robot.Utilities.MotionProfiling.GeneratedProfiles;
import frc.robot.Utilities.MotionProfiling.MotionProfile;

public class Arm {

    MotionProfile armMotionProfile;

    public Arm() {
        armMotionProfile = new MotionProfile(RobotMap.armPivot);
    }
    
    public enum DirectionEnum {
        FRONT,
        BACK
    }

    public enum ArmEnum {
        HOLD,
        MOVE_ARM_OVER,
        RESET
    }

    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    ArmEnum armState = ArmEnum.HOLD;

    public void update() {

        switch(armState) {
            case HOLD:
                // keep the arm in the same place
                break;

            case MOVE_ARM_OVER:
                if (lastDirection == DirectionEnum.BACK && direction == DirectionEnum.FRONT && 
                        RobotMap.teleopControls.getIsUpdating()) { // going to the front
                    armMotionProfile.startProfile(GeneratedProfiles.backDownToBackTilt);
                    if (RobotMap.elevatorFront.getSelectedSensorPosition() >= RobotMap.ARM_OVER_HEIGHT) { // check >= for actual robot
                        armMotionProfile.disableMotionProfile();
                        armMotionProfile.startProfile(GeneratedProfiles.backTiltToFrontDown);
                        RobotMap.teleopControls.setIsUpdating(false);
                        armMotionProfile.disableMotionProfile(); // disbable profile so that you can run a profile the next time a button is pressed
                    }
                }
                if (lastDirection == DirectionEnum.FRONT && direction == DirectionEnum.BACK &&
                        RobotMap.teleopControls.getIsUpdating()) { // going to the back
                    armMotionProfile.startProfile(GeneratedProfiles.frontDownToFrontTilt);
                    if (RobotMap.elevatorFront.getSelectedSensorPosition() >= RobotMap.ARM_OVER_HEIGHT) { // check >= for actual robot
                        armMotionProfile.disableMotionProfile();
                        armMotionProfile.startProfile(GeneratedProfiles.frontTiltToBackDown);
                        RobotMap.teleopControls.setIsUpdating(false); // stop updating this code so that the motion profiles don't keep running
                        armMotionProfile.disableMotionProfile(); // disbable profile so that you can run a profile the next time a button is pressed
                    }
                } else {
                    Logger.error("Arm is in case MOVE_ARM_OVER but the lastDirection and direction are the same.");
                }
                break;

            case RESET:
                // write this once we know pot values
                break;
        }
    
    }

    public void updateDirection() {
        lastDirection = direction;
    }
    
    public void changeDirection() {
        direction = direction == DirectionEnum.FRONT ? DirectionEnum.BACK : DirectionEnum.FRONT;
    }

    public boolean isArmClear() {
        if (lastDirection == DirectionEnum.FRONT && direction == DirectionEnum.BACK) {
            if (RobotMap.armPot.get() == RobotMap.BACK_ARM_CLEAR_VALUE) { // fix the == once we know pot values
                return true;
            } else {
                return false;
            }
        }
        if (lastDirection == DirectionEnum.BACK && direction == DirectionEnum.FRONT) {
            if (RobotMap.armPot.get() == RobotMap.FRONT_ARM_CLEAR_VALUE) { // fix the == once we know pot values
                return true;
            } else {
                return false;
            }
        } else {
            Logger.error("Checking isArmCLear when not changing direction.");
            return false;
        }
    }

    public void setArmStateHold() {
        armState = ArmEnum.HOLD;
    }

    public void setArmStateMoveArmOver() {
        armState = ArmEnum.MOVE_ARM_OVER;
    }

    public void setArmStateReset() {
        armState = ArmEnum.RESET;
    }
}