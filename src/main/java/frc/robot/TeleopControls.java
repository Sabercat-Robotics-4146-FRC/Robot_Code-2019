package frc.robot;

import frc.robot.Subassemblies.Intake.CargoRollerEnum;
import frc.robot.Utilities.Limelight.LEDEnum;

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
        // controlls for elevator
        // <editor-fold>
        if (RobotMap.pilotController.getRightBumper()) { // switch the direction of the arm
            if ((RobotMap.pilotController.getButtonA() || RobotMap.pilotController.getButtonB()
                    || RobotMap.pilotController.getButtonX() || RobotMap.pilotController.getButtonY())
                    && buttonFlag) {
                buttonFlag = false;
                setIsUpdating(true);
                if (RobotMap.pilotController.getLeftBumper()) { // going to a port height
                    if (RobotMap.pilotController.getButtonA()) {
                        // switch the direction and go to bottom port height.
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelBottomPort();
                    } else if (RobotMap.pilotController.getButtonB()) {      // we still need the bumper for the cargo ship heights
                        // switch direction and going to ship port height
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelShipPort();
                    } else if (RobotMap.pilotController.getButtonX()) {
                        // switch direction and going to mid port height
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelMidPort();
                    } else if (RobotMap.pilotController.getButtonY()) {
                        // switch direction and goin to top port height
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelTopPort();
                    }
                } else { // going to a hatch height
                    if (RobotMap.pilotController.getButtonA()) {
                        // switch direction and go to intaking hatch height
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelIntakingHatch();
                    } else if (RobotMap.pilotController.getButtonB()) {
                        // switch direction and go to intaking cargo height check this so we can't
                        // intake cargo on wrong side of drivetrain
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelIntakingCargo();
                    } else if (RobotMap.pilotController.getButtonX()) {
                        // switch direction and go to mid hatch height
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelMidHatch();
                    } else if (RobotMap.pilotController.getButtonY()) {
                        // switch direction and go to top hatch height
                        updateDirection();
                        changeDirection();
                        RobotMap.elevator.setLevelTopHatch();
                    }
                } // port 
            }
        } else { // staying on the same side of the elevator
            if ((RobotMap.pilotController.getButtonA() || RobotMap.pilotController.getButtonB()
                    || RobotMap.pilotController.getButtonX() || RobotMap.pilotController.getButtonY())
                    && buttonFlag) {
                buttonFlag = false;
                setIsUpdating(true);
                if (RobotMap.pilotController.getLeftBumper()) { // going to a port height
                    if (RobotMap.pilotController.getButtonA()) {
                        // go to bottom port height
                        updateDirection();
                        RobotMap.elevator.setLevelBottomPort();
                    } else if (RobotMap.pilotController.getButtonB()) {             // we still need the bumper for the cargo ship heights
                        // go to ship port height
                        updateDirection();
                        RobotMap.elevator.setLevelShipPort();
                    } else if (RobotMap.pilotController.getButtonX()) {
                        // go to mid port height
                        updateDirection();
                        RobotMap.elevator.setLevelMidPort();
                    } else if (RobotMap.pilotController.getButtonY()) {
                        // go to top port height
                        updateDirection();
                        RobotMap.elevator.setLevelTopPort();
                    }
                } else { // go to hatch height
                    if (RobotMap.pilotController.getButtonA()) {
                        // go to intaking hatch height
                        updateDirection();
                        RobotMap.elevator.setLevelIntakingHatch();
                    } else if (RobotMap.pilotController.getButtonB()) {
                        // go to intaking cargo height check this so we can't intake cargo on wrong side
                        // of drivetrain
                        updateDirection();
                        RobotMap.elevator.setLevelIntakingCargo();
                    } else if (RobotMap.pilotController.getButtonX()) {
                        // go to mid hatch height
                        updateDirection();
                        RobotMap.elevator.setLevelMidHatch();
                    } else if (RobotMap.pilotController.getButtonY()) {
                        // go to top hatch height
                        updateDirection();
                        RobotMap.elevator.setLevelTopHatch();
                    }
                } // port 
            }
        }

        if (!RobotMap.pilotController.getButtonA() && !RobotMap.pilotController.getButtonB()
                && !RobotMap.pilotController.getButtonX() && !RobotMap.pilotController.getButtonY()) {
            buttonFlag = true;
        }

        // </editor-fold>

        // controlls for drive train
        // <editor-fold>
        if (RobotMap.pilotController.getButtonStart()) {
            RobotMap.limelight.setLightMode(LEDEnum.ENABLED);

            if (RobotMap.limelight.hasValidTarget()) {
                RobotMap.drivetrain.runVisionPID(RobotMap.pilotController.getDeadbandLeftYAxis());
            } else {
                RobotMap.drivetrain.setDrivetrainValues(RobotMap.pilotController.getDeadbandLeftYAxis(),
                        RobotMap.pilotController.getDeadbandRightXAxis());
            }
        } else {
            RobotMap.limelight.setLightMode(LEDEnum.DISABLED);
            RobotMap.drivetrain.setDrivetrainValues(RobotMap.pilotController.getDeadbandLeftYAxis(),
                    RobotMap.pilotController.getDeadbandRightXAxis());

            RobotMap.drivetrain.restartVisionPID();
        }
        // </editor-fold>

        // controlls for intake/hatch manipulator
        // <editor-fold>
        if (RobotMap.pilotController.getRightTriggerBool()) {
            RobotMap.intake.setCargoRollerState(CargoRollerEnum.INTAKING);
        } else if (RobotMap.pilotController.getLeftTriggerBool()) {
            RobotMap.intake.setCargoRollerState(CargoRollerEnum.OUTPUTTING);
        } else {
            RobotMap.intake.setCargoRollerState(CargoRollerEnum.DISABLED);
        }

        if (RobotMap.pilotController.getRightBumper()) {
            RobotMap.intake.releaseClaw();
        }
        // </editor-fold>
    
        // controls for EGL
        // <editor-fold>
        if (RobotMap.copilotController.getRightBumper()) {
            RobotMap.egl.raiseRobot();
        } else if (RobotMap.copilotController.getLeftBumper()) {
            RobotMap.egl.lowerRobot();
        }
        // </editor-fold>
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
