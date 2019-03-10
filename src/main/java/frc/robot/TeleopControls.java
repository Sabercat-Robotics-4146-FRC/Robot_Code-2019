package frc.robot;

import frc.robot.Subassemblies.Intake.CargoRollerEnum;
import frc.robot.Utilities.Limelight.LEDEnum;

public class TeleopControls {

    public enum DirectionEnum {
        FRONT,
        BACK
    }
    
    public void update() {
        // controlls for elevator
        // <editor-fold>

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
