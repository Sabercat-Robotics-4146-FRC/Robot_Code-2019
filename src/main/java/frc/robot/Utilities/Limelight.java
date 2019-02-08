package frc.robot.Utilities;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    private NetworkTable table;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;

    private double X = 4146;
    private double Y = 4146;
    private double area = 4146;

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    }

    public void update() {
        this.X = tx.getDouble(4146);
        this.Y = ty.getDouble(4146);
        this.area = ta.getDouble(4146);
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
}