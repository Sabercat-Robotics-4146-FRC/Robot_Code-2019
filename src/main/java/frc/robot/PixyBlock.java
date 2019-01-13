package frc.robot;

public class PixyBlock {
	public int Signature;
	public int X;
	public int Y;
	public int Width;
	public int Height;
	
	//public int checksumError;
    
    @Override
	public String toString() {
		return "" +
	" S:" + Signature +
	" X:" + X + 
	" Y:" + Y +
	" W:" + Width + 
	" H:" + Height;
	}

	public void toDashboard() {
		Dashboard.send("Signature", Signature);
		Dashboard.send("X-Value", X);
		Dashboard.send("Y-Value", Y);
		Dashboard.send("Width", Width);
		Dashboard.send("Height", Height);
	}

	public int getX(){
		return X;
	}
}
