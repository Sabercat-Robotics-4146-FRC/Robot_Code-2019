package frc.robot.Subassemblies.Pixy;

import edu.wpi.first.wpilibj.I2C;

// Our imports
import frc.robot.Utilities.Dashboard;

public class PixyI2C {
	String name;
    I2C pixy;
    int numSignaturesUsed;

	PixyBlock[] signatures; // Stores a PixyBlock for each signature

	public PixyI2C(String id, I2C pixy, int numSignaturesUsed) {
        this.name = "Pixy_" + id; // ID is not used anywhere and is souly for name.
        this.pixy = pixy;
        this.numSignaturesUsed = numSignaturesUsed;
		
		this.signatures = new PixyBlock[numSignaturesUsed];
	}

	// This method parses raw data from the pixy into readable integers
	public int convert(byte upper, byte lower) {
		return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
	}

    // This method gathers data, then parses that data, and assigns the ints to global variables.
    // The signature should be which number object in pixymon you are trying to get data for.
    // It returns a PixyBlock so you have access to all information given by the pixy (minus checksum).
	public PixyBlock readPacket(int Signature) {
		int checksumData;
        int signatureData;
        
		byte[] rawData = new byte[32];
        
        // Reading Pixy Data
		try {
			pixy.readOnly(rawData, 32);
		} catch (RuntimeException e) {
			System.out.println(name + " - " + e);
        }
        
        // Testing for a bad things that *should* never happen.
		if (rawData.length < 32) {
			System.out.println("Byte array length is broken in " + this.name + ", " + " length=" + rawData.length);
			return null;
        }
        
        // Basically, this searches for the last sync word (start flag) and only checks the first
        // 16 bytes of the 32 byte array becasue the blocks are about 14 to 16 bytes and then
        // parses the next bytes accordingly.
		for (int i = 0; i <= 16; i++) {
            int syncWord = convert(rawData[i + 1], rawData[i + 0]); // Parse first 2 bytes
            
            // Check is first 2 bytes equal a "sync word", which indicates the start of a packet of valid data
            if (syncWord == 0xaa55) { 
				syncWord = convert(rawData[i + 3], rawData[i + 2]); // Parse the next 2 bytes
				if (syncWord != 0xaa55) { // Shifts everything in the case that one syncword is sent
					i -= 2;
                }
                
				// This rest of this code parses the rest of the data
                checksumData = convert(rawData[i + 5], rawData[i + 4]);
                signatureData = convert(rawData[i + 7], rawData[i + 6]);
                
                // Breaks if the recieved block has a signature of 0 or greater than the number
                // of used signatures being used.
				if (signatureData <= 0 || signatureData > signatures.length) {
					break;
				}

                // Makes a PixyBlock in the signatures array under the index of the recieved
                // block's signature (-1 becasue signatures start at 1 but arrays start at 0)
                signatures[signatureData - 1] = new PixyBlock();
                
                //Sets values in the current block's array value
                signatures[signatureData - 1].Signature = signatureData;
				signatures[signatureData - 1].X = convert(rawData[i + 9], rawData[i + 8]);
				signatures[signatureData - 1].Y = convert(rawData[i + 11], rawData[i + 10]);
				signatures[signatureData - 1].Width = convert(rawData[i + 13], rawData[i + 12]);
                signatures[signatureData - 1].Height = convert(rawData[i + 15], rawData[i + 14]);
                
				// Checks whether the data is valid using the checksum *This if block should never be entered*
                if (checksumData != (signatureData + signatures[signatureData - 1].X + signatures[signatureData - 1].Y
                        + signatures[signatureData - 1].Width + signatures[signatureData - 1].Height)) {
                    signatures[signatureData - 1] = null;
					System.out.println("CHECKSUM DATA DOES NOT CHECKOUT!!!!!!!!!!!!!!!!");
				}
				break;
			} else {
                Dashboard.send(name + "Sync Word?:", syncWord);
            }
        }
        
		// Assigns our packet to a temp packet, then deletes data so that we
		// dont return old data
		PixyBlock tempBlock = signatures[Signature - 1];
		signatures[Signature - 1] = null; // May want to add a zeroing method to PixyBlock and then use that.
		return tempBlock;
	}
}
