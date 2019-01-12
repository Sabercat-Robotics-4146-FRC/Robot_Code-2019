package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import java.lang.Exception;

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
	public PixyBlock readPacket(int Signature) {
		int checksumData;
        int signatureData;
        
		byte[] rawData = new byte[32];
		
		try {
			pixy.readOnly(rawData, 32);
		} catch (RuntimeException e) {
			System.out.println(name + " - " + e);
        }
        
		if (rawData.length < 32) {
			System.out.println("Byte array length is broken in " + this.name + ", " + " length=" + rawData.length);
			return null;
        }
        
		for (int i = 0; i <= 16; i++) {
            int syncWord = convert(rawData[i + 1], rawData[i + 0]); // Parse first 2 bytes
            
            // Check is first 2 bytes equal a "sync word", which indicates the start of a packet of valid data
            if (syncWord == 0xaa55) { 
				syncWord = convert(rawData[i + 3], rawData[i + 2]); // Parse the next 2 bytes
				if (syncWord != 0xaa55) { // Shifts everything in the case that one syncword is sent
					i -= 2;
                }
                
				// This rest of this parses the rest of the data
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
                Dashboard.send(name + " Sync Word?:", syncWord);
            }
        }
        
		// Assigns our packet to a temp packet, then deletes data so that we
		// dont return old data
		PixyBlock pkt = signatures[Signature - 1]; // These "Signature"s might have to be "signatureData"
		signatures[Signature - 1] = null;
		return pkt;
	}

	private byte[] readData(int len) {
		byte[] rawData = new byte[len];
		try {
			pixy.readOnly(rawData, len);
		} catch (RuntimeException e) {
			System.out.println(name + "  " + e);
		}
		if (rawData.length < len) {
			System.out.println("byte array length is broken in " + name + ", length=" + rawData.length);
			return null;
		}
		return rawData;
	}

	private int readWord() {
		byte[] data = readData(2);
		if (data == null) {
			return 0;
		}
		return convert(data[1], data[0]);
	}

	private PixyBlock readBlock(int checksum) {
		// See Object block format section in
		// http://www.cmucam.org/projects/cmucam5/wiki/Porting_Guide#Object-block-format
		// Each block is 14 bytes, but we already read 2 bytes for checksum in
		// caller so now we need to read rest.

		byte[] data = readData(12);
		if (data == null) {
			return null;
		}
		PixyBlock block = new PixyBlock();
		block.Signature = convert(data[1], data[0]);
		if (block.Signature <= 0 || block.Signature > 7) {
			return null;
		}
		block.X = convert(data[3], data[2]);
		block.Y = convert(data[5], data[4]);
		block.Width = convert(data[7], data[6]);
		block.Height = convert(data[9], data[8]);

		int sum = block.Signature + block.X + block.Y + block.Width + block.Height;
		if (sum != checksum) {
			return null;
		}
		return block;
	}

	private final int MAX_SIGNATURES = 7;
	private final int OBJECT_SIZE = 14;
	private final int START_WORD = 0xaa55;
	private final int START_WORD_CC = 0xaa5;
	private final int START_WORD_X = 0x55aa;

	public boolean getStart() {
		int numBytesRead = 0;
		int lastWord = 0xffff;
		// This while condition was originally true.. may not be a good idea if
		// something goes wrong robot will be stuck in this loop forever. So
		// let's read some number of bytes and give up so other stuff on robot
		// can have a chance to run. What number of bytes to choose? Maybe size
		// of a block * max number of signatures that can be detected? Or how
		// about size of block and max number of blocks we are looking for?
		while (numBytesRead < (OBJECT_SIZE * MAX_SIGNATURES)) {
			int word = readWord();
			numBytesRead += 2;
			if (word == 0 && lastWord == 0) {
				return false;
			} else if (word == START_WORD && lastWord == START_WORD) {
				return true;
			} else if (word == START_WORD_CC && lastWord == START_WORD) {
				return true;
			} else if (word == START_WORD_X) {
				byte[] data = readData(1);
				numBytesRead += 1;
			}
			lastWord = word;
		}
		return false;
	}

	private boolean skipStart = false;

	public PixyBlock[] readBlocks() {
		// This has to match the max block setting in pixymon?
		int maxBlocks = 2;
		PixyBlock[] blocks = new PixyBlock[maxBlocks];

		if (!skipStart) {
			if (getStart() == false) {
				return null;
			}
		} else {
			skipStart = false;
		}
		for (int i = 0; i < maxBlocks; i++) {
			// Should we set to empty PixyPacket? To avoid having to check for
			// null in callers?
			blocks[i] = null;
			int checksum = readWord();
			if (checksum == START_WORD) {
				// we've reached the beginning of the next frame
				skipStart = true;
				return blocks;
			} else if (checksum == START_WORD_CC) {
				// we've reached the beginning of the next frame
				skipStart = true;
				return blocks;
			} else if (checksum == 0) {
				return blocks;
			}
			blocks[i] = readBlock(checksum);
		}
		return blocks;
	}
}
