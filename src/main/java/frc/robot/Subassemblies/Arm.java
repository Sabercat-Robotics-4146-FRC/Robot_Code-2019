package frc.robot.Subassemblies;

import frc.robot.TeleopControls;

public class Arm {
    
    public enum DirectionEnum {
        FRONT,
        BACK
    }

    public enum ArmEnum {
        HOLD,
        MOVE_ARM_OVER,
        RESET
    }

    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    ArmEnum armState = ArmEnum.HOLD;

    public void update() {
    
    }

    public void updateDirection() {
        lastDirection = direction;
    }
    
    public void changeDirection() {
        direction = direction == DirectionEnum.FRONT ? DirectionEnum.BACK : DirectionEnum.FRONT;
    }

    //public boolean isArmClear() {
        //if (lastDirection == DirectionEnum.FRONT && direction == DirectionEnum.BACK)
    //}
}