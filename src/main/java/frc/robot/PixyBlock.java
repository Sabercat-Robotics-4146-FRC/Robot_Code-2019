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
}
