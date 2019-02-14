package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;

public class Intake {
    public enum CargoRollerEnum {
        INTAKING,
        OUTPUTTING, // Change this?
        DISABLED
    }
    enum PistonEnum {
        EXTENDED,
        RETRACTED,
        DISABLED
    }

    CargoRollerEnum cargoRollerState;
    PistonEnum pistonState;

    public Intake() {
       cargoRollerState = CargoRollerEnum.DISABLED;
       pistonState = PistonEnum.RETRACTED;
    }

    public void update(double dt) {
        // Cargo Roller
        if (cargoRollerState == CargoRollerEnum.INTAKING) {
            RobotMap.cargoRoller.set(ControlMode.PercentOutput, RobotMap.CARGO_ROLLER_INTAKING_SPEED);
        } else if (cargoRollerState == CargoRollerEnum.OUTPUTTING) {
            RobotMap.cargoRoller.set(ControlMode.PercentOutput, RobotMap.CARGO_ROLLER_OUTPUTTING_SPEED);
        } else if (cargoRollerState == CargoRollerEnum.DISABLED) {
            RobotMap.cargoRoller.set(ControlMode.PercentOutput, 0.0); // have a constand for disabled?
        } else {
            ConsoleLogger.error("Cargo Roller is in an unnacounted for state.");
        }

        // Hatch Manipulator
        if (pistonState == PistonEnum.EXTENDED) {
            RobotMap.leftSolenoid.set(DoubleSolenoid.Value.kForward);
            RobotMap.rightSolenoid.set(DoubleSolenoid.Value.kForward);
        }
    }

    public void setCargoRollerState(CargoRollerEnum cargoRollerState) {
        this.cargoRollerState = cargoRollerState;
    }
}