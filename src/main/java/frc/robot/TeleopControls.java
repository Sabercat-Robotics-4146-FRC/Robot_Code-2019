package frc.robot;

import frc.robot.Subassemblies.Elevator;
import frc.robot.Subassemblies.Elevator.LevelEnum;
import frc.robot.Subassemblies.Intake.CargoRollerEnum;

public class TeleopControls {

    public enum DirectionEnum {
        FRONT,
        BACK
    }

    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    boolean buttonFlag;
    public boolean isUpdating = true;

    public void update() {

        // buttons to move the elevator
        // <editor-fold>
        if (RobotMap.driverController.getRightBumper()) { // switch the direction of the arm
            if ((RobotMap.driverController.getButtonA() || RobotMap.driverController.getButtonB() || 
                    RobotMap.driverController.getButtonX() || RobotMap.driverController.getButtonY()) && buttonFlag) { 
                buttonFlag = false;
                setIsUpdating(true);
                // if (RobotMap.driverController.getLeftBumper()) { // going to a port height
                //     if (RobotMap.driverController.getButtonA()) {
                //         // switch the direction and go to bottom port height.
                //         updateDirection();
                //         changeDirection();
                //         RobotMap.elevator.setLevelBottomPort();
                //         RobotMap.arm.setArmStateMoveArmOver();
                //     } else if (RobotMap.driverController.getButtonB()) {      // we still need the bumper for the cargo ship heights
                //         // switch direction and going to ship port height
                //         updateDirection();
                //         changeDirection();
                //         RobotMap.elevator.setLevelShipPort();
                //         RobotMap.arm.setArmStateMoveArmOver();
                //     } else if (RobotMap.driverController.getButtonX()) {
                //         // switch direction and going to mid port height
                //         updateDirection();
                //         changeDirection();
                //         RobotMap.elevator.setLevelMidPort();
                //         RobotMap.arm.setArmStateMoveArmOver();
                //     } else if (RobotMap.driverController.getButtonY()) {
                //         // switch direction and goin to top port height
                //         updateDirection();
                //         changeDirection();
                //         RobotMap.elevator.setLevelTopPort();
                //         RobotMap.arm.setArmStateMoveArmOver();
                //     }
                // } else { // going to a hatch height
                    if (RobotMap.driverController.getButtonA()) {
                        // switch direction and go to intaking hatch height
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelIntakingHatch();
                        RobotMap.arm.setArmStateMoveArmOver();
                    } else if (RobotMap.driverController.getButtonB()) {
                        // switch direction and go to intaking cargo height                                         check this so we can't intake cargo on wrong side of drivetrain
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelIntakingCargo();
                        RobotMap.arm.setArmStateMoveArmOver();
                    } else if (RobotMap.driverController.getButtonX()) {
                        // switch direction and go to mid hatch height
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelMidHatch();
                        RobotMap.arm.setArmStateMoveArmOver();
                    } else if (RobotMap.driverController.getButtonY()) {
                        // switch direction and go to top hatch height
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelTopHatch();
                        RobotMap.arm.setArmStateMoveArmOver();
                    }
               // }
            }
        } else { // staying on the same side of the elevator
            if ((RobotMap.driverController.getButtonA() || RobotMap.driverController.getButtonB() ||
                 RobotMap.driverController.getButtonX() || RobotMap.driverController.getButtonY()) && buttonFlag) { 
                buttonFlag = false;
                setIsUpdating(true);
                // if (RobotMap.driverController.getLeftBumper()) { // going to a port height
                //     if (RobotMap.driverController.getButtonA()) {
                //         // go to bottom port height
                //         updateDirection();
                //         RobotMap.elevator.setLevelBottomPort();
                //     } else if (RobotMap.driverController.getButtonB()) {             // we still need the bumper for the cargo ship heights
                //         // go to ship port height
                //         updateDirection();
                //         RobotMap.elevator.setLevelShipPort();
                //     } else if (RobotMap.driverController.getButtonX()) {
                //         // go to mid port height
                //         updateDirection();
                //         RobotMap.elevator.setLevelMidPort();
                //     } else if (RobotMap.driverController.getButtonY()) {
                //         // go to top port height
                //         updateDirection();
                //         RobotMap.elevator.setLevelTopPort();
                //     }
                // } else { // go to hatch height
                    if (RobotMap.driverController.getButtonA()) {
                        // go to intaking hatch height
                        updateDirection();
                        RobotMap.elevator.setLevelIntakingHatch();
                        RobotMap.arm.setArmStateHold();
                    } else if (RobotMap.driverController.getButtonB()) {
                        // go to intaking cargo height                                                               check this so we can't intake cargo on wrong side of drivetrain
                        updateDirection();
                        RobotMap.elevator.setLevelIntakingCargo();
                        RobotMap.arm.setArmStateHold();
                    } else if (RobotMap.driverController.getButtonX()) {
                        // go to mid hatch height
                        updateDirection();
                        RobotMap.elevator.setLevelMidHatch();
                        RobotMap.arm.setArmStateHold();
                    } else if (RobotMap.driverController.getButtonY()) {
                        // go to top hatch height
                        updateDirection();
                        RobotMap.elevator.setLevelTopHatch();
                        RobotMap.arm.setArmStateHold();
                    }
                //}
            }
        }

        if (!RobotMap.driverController.getButtonA() && !RobotMap.driverController.getButtonB() && 
                !RobotMap.driverController.getButtonX() && !RobotMap.driverController.getButtonY()) {
            buttonFlag = true;
        }

        // </editor-fold>
    
        // controlls for drive train
        if (RobotMap.driverController.getButtonStart()) {
            RobotMap.drivetrain.runVisionPID(RobotMap.driverController.getDeadbandLeftYAxis());
        } else {
            RobotMap.drivetrain.setDrivetrainValues(RobotMap.driverController.getDeadbandLeftYAxis(),
                RobotMap.driverController.getDeadbandRightXAxis());
                
            RobotMap.drivetrain.restartVisionPID();
        }

        // controlls for intake/hatch manipulator
        if (RobotMap.driverController.getRightTriggerBool()) {
            RobotMap.intake.setCargoRollerState(CargoRollerEnum.INTAKING);
        } else if (RobotMap.driverController.getLeftTriggerBool()) {
            RobotMap.intake.setCargoRollerState(CargoRollerEnum.OUTPUTTING);
        } else {
            RobotMap.intake.setCargoRollerState(CargoRollerEnum.DISABLED);
        }

    }

    public boolean getIsUpdating() {
        return isUpdating;
    }

    public void setIsUpdating(boolean isUpdating) {
        this.isUpdating = isUpdating;
    }

    public void updateDirection() {
        RobotMap.arm.updateDirection();
        RobotMap.elevator.upadteDirection();
    }

    public void changeDirection() {
        RobotMap.arm.changeDirection();
        RobotMap.elevator.changeDirection();
    }

}
