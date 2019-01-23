package frc.robot.Subassemblies.Pixy;


import frc.robot.Utilities.Dashboard;
import frc.robot.Utilities.Logger;


public class PixyBlock {
	public int Checksum;
	public int Signature;
	public int X;
	public int Y;
	public int Width;
	public int Height;

	public PixyBlock() {
		// Nothing
	}

	public PixyBlock(int Checksum, int Signature, int X, int Y, int Width, int Height) {
		this.Checksum = Checksum;
		this.Signature = Signature;
		this.X = X;
		this.Y = Y;
		this.Width = Width;
		this.Height = Height;

		// System.out.println("=============================");
		// System.out.println(this);
		checkChecksum();
	}
    
    @Override
	public String toString() {
		return
		"\nC: " + Checksum +
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

	public void checkChecksum() {
		if(this.Checksum != getSum()) {
			Logger.error("CHECKSUM DATA DOES NOT CHECKOUT!!!!!!!!!!!!!!!!");
			zero();
		}
	}

	public void zero() {
		Signature = 0;
		X = 0;
		Y = 0;
		Width = 0;
		Height = 0;
	}
}
