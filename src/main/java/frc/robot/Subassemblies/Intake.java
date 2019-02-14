package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;

public class Intake {
    public enum CargoRollerEnum {
        INTAKING,
        OUTPUTTING, // Change this?
        DISABLED
    }

    CargoRollerEnum cargoRollerState;

    public Intake() {
       cargoRollerState = CargoRollerEnum.DISABLED;
    }

    public void update() {
        if (cargoRollerState == CargoRollerEnum.INTAKING) {
            RobotMap.cargoRoller.set(ControlMode.PercentOutput, RobotMap.CARGO_ROLLER_INTAKING_SPEED);
        } else if (cargoRollerState == CargoRollerEnum.OUTPUTTING) {
            RobotMap.cargoRoller.set(ControlMode.PercentOutput, RobotMap.CARGO_ROLLER_OUTPUTTING_SPEED);
        } else if (cargoRollerState == CargoRollerEnum.DISABLED) {
            RobotMap.cargoRoller.set(ControlMode.PercentOutput, 0.0); // have a constand for disabled?
        }
    }

    public void setCargoRollerState(CargoRollerEnum cargoRollerState) {
        this.cargoRollerState = cargoRollerState;
    }
}