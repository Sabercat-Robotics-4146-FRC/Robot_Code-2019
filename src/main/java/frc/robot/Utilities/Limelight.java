package frc.robot.Utilities;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    public enum LEDEnum {
        ENABLED,
        BLINKING,
        DISABLED
    }

    private final double enabledCode = 3;
    private final double blinkingCode = 2;
    private final double disabledCode = 1;

    private NetworkTable table;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;

    private double X = 4146;
    private double Y = 4146;
    private double area = 4146;

    private LEDEnum LEDState = LEDEnum.DISABLED;
    private LEDEnum lastLEDState = LEDEnum.DISABLED;

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    }

    public void update() {
        // NOTE!!!! This may be bad querrying the table every iteration.
        if (LEDState == LEDEnum.ENABLED && table.getEntry("ledMode").getDouble(4146) != enabledCode) {
            this.table.getEntry("ledMode").setNumber(enabledCode);
        } else if (LEDState == LEDEnum.BLINKING && table.getEntry("ledMode").getDouble(4146) != blinkingCode) {
            this.table.getEntry("ledMode").setNumber(blinkingCode);
        } else if (LEDState == LEDEnum.DISABLED && table.getEntry("ledMode").getDouble(4146) != disabledCode) {
            this.table.getEntry("ledMode").setNumber(disabledCode);
        }
        
        this.X = tx.getDouble(4146);
        this.Y = ty.getDouble(4146);
        this.area = ta.getDouble(4146);

        Dashboard.send("Limelight X", this.X);
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getArea() {
        return area;
    }

    public void setLightMode(LEDEnum state) {
        this.LEDState = state;
        // if (isEnabled != lastLightState) {
        //     NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(isEnabled ? 3 : 1);
        //     lastLightState = isEnabled;
        // }
    }

    public boolean hasValidTarget() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) >= 1;
    }
}