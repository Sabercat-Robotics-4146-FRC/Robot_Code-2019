package frc.robot.Subassemblies.Pixy;


import frc.robot.Utilities.Dashboard;


public class PixyBlock {
	public int Signature;
	public int X;
	public int Y;
	public int Width;
	public int Height;
    
    @Override
	public String toString() {
		return
		"\nS: " + Signature +
		"\nX: " + X + 
		"\nY: " + Y +
		"\nW: " + Width + 
		"\nH: " + Height;
	}

	public void toDashboard() {
		Dashboard.send("Signature", Signature);
		Dashboard.send("X-Value", X);
		Dashboard.send("Y-Value", Y);
		Dashboard.send("Width", Width);
		Dashboard.send("Height", Height);
	}

	public int getSum() {
		return  Signature + X + Y + Width + Height;
	}

	public void zero() {
		Signature = 0;
		X = 0;
		Y = 0;
		Width = 0;
		Height = 0;
	}
}
