package frc.robot;

import frc.robot.Subassemblies.Intake.CargoRollerEnum;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Limelight.LEDEnum;
import frc.robot.Subassemblies.ElevatorAndArm.ScoringPositionEnum;

public class TeleopControls {

    public enum DirectionEnum {
        FRONT,
        BACK
    }
    
    public void update() {
        // controlls for elevator
        // <editor-fold>
        if (RobotMap.pilotController.getRightBumper()) {
            if (RobotMap.pilotController.getLeftBumper()) {
                if (RobotMap.pilotController.getButtonY()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.BACK_TOP_ROCKET_PORT);
                    } else if(RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.FRONT_TOP_ROCKET_PORT);
                    }                    
                } else if (RobotMap.pilotController.getButtonX()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.BACK_MID_ROCKET_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.FRONT_MID_ROCKET_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonB()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.BACK_CARGO_SHIP_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.FRONT_CARGO_SHIP_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonA()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.BACK_BOTTOM_ROCKET_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.FRONT_BOTTOM_ROCKET_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonBack()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.BACK_STORAGE);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.FRONT_STORAGE);
                    }
                }
            } else { 
                if (RobotMap.pilotController.getButtonY()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.BACK_TOP_ROCKET_HATCH);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.FRONT_TOP_ROCKET_HATCH);
                    }
                } else if (RobotMap.pilotController.getButtonX()) {
                    if ( RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.BACK_MID_ROCKET_HATCH);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.FRONT_MID_ROCKET_HATCH);
                    }
                } else if (RobotMap.pilotController.getButtonA()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.BACK_INTAKING_ROCKET_HATCH);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.FRONT_INTAKING_ROCKET_HATCH);
                    }
                } else if (RobotMap.pilotController.getButtonB()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.FRONT) {
                        ConsoleLogger.warning("tried to intake cargo from back");
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPositionEnum.SideEnum.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPositionEnum.INTAKING_CARGO);
                    }
                }
            }          
        } else {

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
}
