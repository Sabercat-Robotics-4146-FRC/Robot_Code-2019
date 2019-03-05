package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;

public class EndGameLift {
    public enum EGL_Enum {
        RAISING,
        LOWERING,
        STOPPED
    }

    EGL_Enum EGL_State;

    public EndGameLift() {
        EGL_State = EGL_Enum.STOPPED;
    }

    public void update() {
        if (EGL_State == EGL_Enum.RAISING) {
            RobotMap.EGL_leftFront.set(ControlMode.PercentOutput, RobotMap.ELG_RAISING_SPEED);
        } else if (EGL_State == EGL_Enum.LOWERING) {
            RobotMap.EGL_leftFront.set(ControlMode.PercentOutput, RobotMap.ELG_LOWERING_SPEED);
        } else if (EGL_State == EGL_Enum.STOPPED) {
            RobotMap.EGL_leftFront.set(ControlMode.PercentOutput, 0.0);
        }
    }

    public void raiseRobot() {
        if (RobotMap.EGLPot.get() < RobotMap.EGL_UPPER_SOFT_STOP) {
            EGL_State = EGL_Enum.RAISING;
        } else {
            EGL_State = EGL_Enum.STOPPED;
        }
    }

    public void lowerRobot() {
        if (RobotMap.EGLPot.get() > RobotMap.EGL_LOWER_SOFT_STOP) {
            EGL_State = EGL_Enum.LOWERING;
        } else {
            EGL_State = EGL_Enum.STOPPED;
        }
    }

    public void stopLift() { // Not used/needed but I'm keeping it around.
        EGL_State = EGL_Enum.STOPPED;
    }
}