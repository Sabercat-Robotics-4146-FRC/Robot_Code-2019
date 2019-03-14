package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Dashboard;

public class Intake {
    public enum CargoRollerEnum {
        INTAKING,
        OUTPUTTING,
        DISABLED
    }
    public enum ClawEnum {
        RELEASED,
        HOLDING
    }

    CargoRollerEnum cargoRollerState;
    ClawEnum clawState;
    double dtAccumulator;
    boolean cargoRumbleFlag;
    boolean hatchFlag;

    public Intake() {
       cargoRollerState = CargoRollerEnum.DISABLED;
       clawState = ClawEnum.HOLDING;
       dtAccumulator = 0;
       cargoRumbleFlag = false;
       hatchFlag = true;
    }

    public void update(double dt) {
        // Cargo Roller

        if (cargoRollerState == CargoRollerEnum.INTAKING) {
            if (RobotMap.cargoLimitSwitch.get()) {
                RobotMap.pilotController.setRumble(1, false);
                cargoRumbleFlag = true;
            } else {
                RobotMap.pilotController.setRumble(0, false);
                cargoRumbleFlag = false;
            }

            RobotMap.cargoRoller.set(ControlMode.PercentOutput, RobotMap.CARGO_ROLLER_INTAKING_SPEED);
        } else if (cargoRollerState == CargoRollerEnum.OUTPUTTING) {
            if (cargoRumbleFlag) {
                cargoRumbleFlag = false;
                RobotMap.pilotController.setRumble(0, false);
            }

            RobotMap.cargoRoller.set(ControlMode.PercentOutput, RobotMap.CARGO_ROLLER_OUTPUTTING_SPEED);
        } else if (cargoRollerState == CargoRollerEnum.DISABLED) {
            if (cargoRumbleFlag) {
                cargoRumbleFlag = false;
                RobotMap.pilotController.setRumble(0, false);
            }

            RobotMap.cargoRoller.set(ControlMode.PercentOutput, 0.0); // have a constant for disabled?
        } else {
            ConsoleLogger.error("Cargo Roller is in an unnacounted for state.");
        }

        // Hatch Manipulator
        if (clawState == ClawEnum.HOLDING) {
            RobotMap.clawSolenoid.set(true);
        } else if (clawState == ClawEnum.RELEASED) {
            RobotMap.clawSolenoid.set(false);
        } else {
            ConsoleLogger.error("Hatch Manipulator is in an unnacounted for state.");
        }

        // Original Code using a limit switch

        // if (this.dtAccumulator < RobotMap.CLAW_RELEASE_TIME) {
        //     this.dtAccumulator += dt;
        // }

        // if (RobotMap.hatchLimitSwitch.get() && dtAccumulator >= RobotMap.CLAW_RELEASE_TIME) {
        //     clawState = ClawEnum.HOLDING;
        // }

        // if (clawState == ClawEnum.HOLDING) {
        //     RobotMap.clawSolenoid.set(true);
        // } else if (clawState == ClawEnum.RELEASED) {
        //     RobotMap.clawSolenoid.set(false);
        // } else {
        //     ConsoleLogger.error("Claw is in an unnacounted for state.");
        // }
    }

    public void setCargoRollerState(CargoRollerEnum cargoRollerState) {
        this.cargoRollerState = cargoRollerState;
    }

    public void releaseClaw() { // Currently unused. Was used with original hatch code.
        clawState = ClawEnum.RELEASED;
        dtAccumulator = 0;
        RobotMap.pilotController.setRumbleBuzz(3, 0.1, 0.05);
    }

    public void toggleClaw() {
        clawState = clawState == ClawEnum.RELEASED ? ClawEnum.HOLDING : ClawEnum.RELEASED;
    }
}