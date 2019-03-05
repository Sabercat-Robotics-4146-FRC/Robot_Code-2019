package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;

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

    public Intake() {
       cargoRollerState = CargoRollerEnum.DISABLED;
       clawState = ClawEnum.RELEASED;
       dtAccumulator = 0;
    }

    public void update(double dt) {
        // Cargo Roller
        if (cargoRollerState == CargoRollerEnum.INTAKING) {
            RobotMap.cargoRoller.set(ControlMode.PercentOutput, RobotMap.CARGO_ROLLER_INTAKING_SPEED);
        } else if (cargoRollerState == CargoRollerEnum.OUTPUTTING) {
            RobotMap.cargoRoller.set(ControlMode.PercentOutput, RobotMap.CARGO_ROLLER_OUTPUTTING_SPEED);
        } else if (cargoRollerState == CargoRollerEnum.DISABLED) {
            RobotMap.cargoRoller.set(ControlMode.PercentOutput, 0.0); // have a constant for disabled?
        } else {
            ConsoleLogger.error("Cargo Roller is in an unnacounted for state.");
        }

        // Hatch Manipulator
        if (this.dtAccumulator < RobotMap.CLAW_RELEASE_TIME) {
            this.dtAccumulator += dt;
        }

        if (RobotMap.hatchLimitSwitch.get() && dtAccumulator >= RobotMap.CLAW_RELEASE_TIME) {
            clawState = ClawEnum.HOLDING;
        }

        if (clawState == ClawEnum.HOLDING) {
            RobotMap.clawSolenoid.set(true);
        } else if (clawState == ClawEnum.RELEASED) {
            RobotMap.clawSolenoid.set(false);
        } else {
            ConsoleLogger.error("Claw is in an unnacounted for state.");
        }
    }

    public void setCargoRollerState(CargoRollerEnum cargoRollerState) {
        this.cargoRollerState = cargoRollerState;
    }

    public void releaseClaw() {
        clawState = ClawEnum.RELEASED;
        dtAccumulator = 0;
        RobotMap.pilotController.setRumbleBuzz(3, 0.3, 0.1);
    }
}