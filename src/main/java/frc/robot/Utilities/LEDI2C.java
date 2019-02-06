package frc.robot.Utilities;

import edu.wpi.first.wpilibj.I2C;

public class LEDI2C {
    enum ColorEnum {
        RED,
        GREEN,
        BLUE,
        PURPLE,
        ORANGE,
        YELLOW,
        WHITE,
        IDLE,
        OFF
    }

    I2C leds;
    ColorEnum colorState;

    public LEDI2C(I2C leds) {
        this.leds = leds;
        this.colorState = ColorEnum.IDLE;
    }
    
    public void changeColor (ColorEnum state) {
        switch(state) {
            case RED:
                send('R');
            break;
            case GREEN:
                send('G');
            break;
            case BLUE:
                send('B');
            break;
            case PURPLE:
                send('P');
            break;
            case ORANGE:
                send('O');
            break;
            case YELLOW:
                send('Y');
            break;
            case WHITE:
                send('W');
            break;
            case IDLE:
                send('I');
            break;
            case OFF:
                send('0');
            break;
        }
    }

    public void send(char data){
        leds.writeBulk(new byte[]{(byte)data}); // lol
    }
}