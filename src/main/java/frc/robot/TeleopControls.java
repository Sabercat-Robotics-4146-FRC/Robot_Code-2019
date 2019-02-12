package frc.robot;

import frc.robot.Subassemblies.Elevator;
import frc.robot.Subassemblies.Elevator.LevelEnum;

public class TeleopControls {

    public enum DirectionEnum {
        FRONT,
        BACK
    }

    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    boolean elevatorButtonFlag;
    public boolean changingDirection = false;
    public boolean isUpdating = true;

    public void update() {

        // buttons to move the elevator
        if (RobotMap.driverController.getRightBumper()) { // switch the direction of the arm
            if ((RobotMap.driverController.getButtonA() || RobotMap.driverController.getButtonB() || 
                    RobotMap.driverController.getButtonX() || RobotMap.driverController.getButtonY()) && elevatorButtonFlag) { 
                elevatorButtonFlag = false;
                setIsUpdating(true);
                // if (RobotMap.driverController.getLeftBumper()) { // going to a port height
                //     if (RobotMap.driverController.getButtonA()) {
                //         // switch the direction and go to bottom port height.
                //         RobotMap.arm.updateDirection();
                //         RobotMap.arm.changeDirection();
                //         level = LevelEnum.BOTTOM_PORT;
                //     } else if (RobotMap.driverController.getButtonB()) {      // we still need the bumper for the cargo ship heights
                //         // switch direction and going to ship port height
                //         RobotMap.arm.updateDirection();
                //         RobotMap.arm.changeDirection();
                //         level = LevelEnum.SHIP_PORT;
                //     } else if (RobotMap.driverController.getButtonX()) {
                //         // switch direction and going to mid port height
                //         RobotMap.arm.updateDirection();
                //         RobotMap.arm.changeDirection();
                //         level = LevelEnum.MID_PORT;
                //     } else if (RobotMap.driverController.getButtonY()) {
                //         // switch direction and goin to top port height
                //         RobotMap.arm.updateDirection();
                //         RobotMap.arm.changeDirection();
                //         level = LevelEnum.TOP_PORT;
                //     }
                // } else { // going to a hatch height
                    if (RobotMap.driverController.getButtonA()) {
                        // switch direction and go to intaking hatch height
                        RobotMap.arm.updateDirection();
                        RobotMap.arm.changeDirection();
                        changingDirection = true;
                        RobotMap.elevator.setLevelIntakingHatch();
                    } else if (RobotMap.driverController.getButtonB()) {
                        // switch direction and go to intaking cargo height                                         check this so we can't intake cargo on wrong side of drivetrain
                        RobotMap.arm.updateDirection();
                        RobotMap.arm.changeDirection();
                        changingDirection = true;
                        RobotMap.elevator.setLevelIntakingCargo();
                    } else if (RobotMap.driverController.getButtonX()) {
                        // switch direction and go to mid hatch height
                        RobotMap.arm.updateDirection();
                        RobotMap.arm.changeDirection();
                        changingDirection = true;
                        RobotMap.elevator.setLevelMidHatch();
                    } else if (RobotMap.driverController.getButtonY()) {
                        // switch direction and go to top hatch height
                        RobotMap.arm.updateDirection();
                        RobotMap.arm.changeDirection();
                        changingDirection = true;
                        RobotMap.elevator.setLevelTopHatch();
                    }
               // }
            }
        } else { // staying on the same side of the elevator
            if ((RobotMap.driverController.getButtonA() || RobotMap.driverController.getButtonB() ||
                 RobotMap.driverController.getButtonX() || RobotMap.driverController.getButtonY()) && elevatorButtonFlag) { 
                elevatorButtonFlag = false;
                setIsUpdating(true);
                // if (RobotMap.driverController.getLeftBumper()) { // going to a port height
                //     if (RobotMap.driverController.getButtonA()) {
                //         // go to bottom port height
                //         RobotMap.arm.updateDirection();
                //         level = LevelEnum.BOTTOM_PORT;
                //     } else if (RobotMap.driverController.getButtonB()) {             // we still need the bumper for the cargo ship heights
                //         // go to ship port height
                //         RobotMap.arm.updateDirection();
                //         level = LevelEnum.SHIP_PORT;
                //     } else if (RobotMap.driverController.getButtonX()) {
                //         // go to mid port height
                //         RobotMap.arm.updateDirection();
                //         level = LevelEnum.MID_PORT;
                //     } else if (RobotMap.driverController.getButtonY()) {
                //         // go to top port height
                //         RobotMap.arm.updateDirection();
                //         level = LevelEnum.TOP_PORT;
                //     }
                // } else { // go to hatch height
                    if (RobotMap.driverController.getButtonA()) {
                        // go to intaking hatch height
                        RobotMap.arm.updateDirection();
                        changingDirection = false;
                        RobotMap.elevator.setLevelIntakingHatch();
                    } else if (RobotMap.driverController.getButtonB()) {
                        // go to intaking cargo height                                                               check this so we can't intake cargo on wrong side of drivetrain
                        RobotMap.arm.updateDirection();
                        changingDirection = false;
                        RobotMap.elevator.setLevelIntakingCargo();
                    } else if (RobotMap.driverController.getButtonX()) {
                        // go to mid hatch height
                        RobotMap.arm.updateDirection();
                        changingDirection = false;
                        RobotMap.elevator.setLevelMidHatch();
                    } else if (RobotMap.driverController.getButtonY()) {
                        // go to top hatch height
                        RobotMap.arm.updateDirection();
                        changingDirection = false;
                        RobotMap.elevator.setLevelTopHatch();
                    }
                //}
            }
        }

        if (!RobotMap.driverController.getButtonA() && !RobotMap.driverController.getButtonB() && 
            !RobotMap.driverController.getButtonX() && !RobotMap.driverController.getButtonY()) {
            elevatorButtonFlag = true;
        }
    }

    public boolean getIsUpdating() {
        return isUpdating;
    }

    public void setIsUpdating(boolean isUpdating) {
        this.isUpdating = isUpdating;
    }

}
