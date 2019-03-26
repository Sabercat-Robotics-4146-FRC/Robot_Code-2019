package frc.robot;

import frc.robot.Subassemblies.Intake.CargoRollerEnum;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Limelight.LEDEnum;
import frc.robot.Subassemblies.ElevatorAndArm.ScoringPosition;

public class TeleopControls {
    public void update() {
        // controlls for elevator
        // <editor-fold>
        if (RobotMap.pilotController.getRightBumper()) { // Switching Sides
            if (RobotMap.pilotController.getLeftBumper()) { // Port Height
                if (RobotMap.pilotController.getButtonY()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_TOP_ROCKET_PORT);
                    } else if(RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_TOP_ROCKET_PORT);
                    }                    
                } else if (RobotMap.pilotController.getButtonX()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_MID_ROCKET_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_MID_ROCKET_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonB()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_CARGO_SHIP_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_CARGO_SHIP_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonA()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_BOTTOM_ROCKET_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_BOTTOM_ROCKET_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonBack()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_STORAGE);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_STORAGE);
                    }
                }
            } else { // Not A Port Height. Hatch Height
                if (RobotMap.pilotController.getButtonY()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_TOP_ROCKET_HATCH);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_TOP_ROCKET_HATCH);
                    }
                } else if (RobotMap.pilotController.getButtonX()) {
                    if ( RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_MID_ROCKET_HATCH);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_MID_ROCKET_HATCH);
                    }
                } else if (RobotMap.pilotController.getButtonA()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_INTAKING_ROCKET_HATCH);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_INTAKING_ROCKET_HATCH);
                    }
                } else if (RobotMap.pilotController.getButtonB()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        ConsoleLogger.warning("Tried to intake cargo from back. Not allowed.");
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.INTAKING_CARGO);
                    }
                } else if (RobotMap.pilotController.getButtonBack()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_STORAGE);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_STORAGE);
                    }
                }
            }
        } else { // Not Switching Sides
            if (RobotMap.pilotController.getLeftBumper()) { // Port Height
                if (RobotMap.pilotController.getButtonY()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_TOP_ROCKET_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_TOP_ROCKET_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonX()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_MID_ROCKET_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_MID_ROCKET_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonB()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_CARGO_SHIP_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_CARGO_SHIP_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonA()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_BOTTOM_ROCKET_PORT);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_BOTTOM_ROCKET_PORT);
                    }
                } else if (RobotMap.pilotController.getButtonBack()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_STORAGE);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_STORAGE);
                    }
                }
            } else { // Not A Port Height. Hatch Height
                if (RobotMap.pilotController.getButtonY()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_TOP_ROCKET_HATCH);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_TOP_ROCKET_HATCH);
                    }
                } else if (RobotMap.pilotController.getButtonX()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_MID_ROCKET_HATCH);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_MID_ROCKET_HATCH);
                    }
                } else if (RobotMap.pilotController.getButtonA()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_INTAKING_ROCKET_HATCH);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_INTAKING_ROCKET_HATCH);
                    }
                } else if (RobotMap.pilotController.getButtonB()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.INTAKING_CARGO);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        ConsoleLogger.warning("Tried to intake cargo from back. Not allowed.");
                    }
                } else if (RobotMap.pilotController.getButtonBack()) {
                    if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.FRONT) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_STORAGE);
                    } else if (RobotMap.elevatorAndArm.getSide() == ScoringPosition.Side.BACK) {
                        RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_STORAGE);
                    }
                }
            }
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
