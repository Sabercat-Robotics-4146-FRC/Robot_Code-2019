package frc.robot.Subassemblies.Pixy;

import edu.wpi.first.wpilibj.I2C;
import frc.robot.RobotMap;
// Our imports
import frc.robot.Utilities.Dashboard;

public class PixyI2C {
	private String name;
    private I2C pixy;

	private byte[] rawData;
	private byte[] overflowRawData;
	private PixyBlock[] blocks; // Stores PixyBlock's
	boolean useOverflow = false;
	boolean hasNewBlocks = false;

	public PixyI2C(String id, I2C pixy) {
        this.name = "Pixy_" + id; // ID is not used anywhere and is souly for name.
        this.pixy = pixy;

		this.blocks = new PixyBlock[2];
	}

	// This method parses raw data from the pixy into readable integers
	public int convert(byte upper, byte lower) {
		return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
	}

	// This method converts a single byte into an int that accuratly represents it as unsigned
	public int convertOneByte(byte bt) { // I could use this in convert.
		return ((int) bt & 0xff);
	}

	public void update() { // Gets pixy data and points putInArray to where each block starts and where to put it.
		/* This comment is no longer relevant. I found the time.
		This is very large so that it definatly catches two blocks of pixy data. However this
		method is most definatly flawed. The pixy could be sending 0s representing nothing/
		no new information while getting the 64 bytes from it but then in the middle or anywhere
		it could suddenly decide is has new information and begin sending it. This could cause
		information to be split between two runs of read. We are fixing this by simply dropping
		the last bytes that could cause this. The most optimal method would be to write something
		that can wrap information from each read. But I ant got time for that rn. (Probably will
		find some time....)*/
		rawData = new byte[30]; // 30 for a single frame with two block (three sync words total)

		// Reading Pixy Data
		try {
			pixy.readOnly(rawData, rawData.length);
		} catch (RuntimeException e) {
			System.out.println(name + " - " + e);
		}
		
		// Testing for a bad thing that *should* never happen.
		if (rawData.length != 30) {
			System.out.println("Byte array length is broken in " + this.name + ", " + " length=" + rawData.length);
			return;
		}

		for(int i = 0; i < rawData.length - 2; i += 1) { // i dont think it should be rawData.length - 1 but maybe?(prob doesnt matter cuz its even and +=2)
			System.out.println(String.format("0x%04X", convert(rawData[i+1], rawData[i])));

			if(convertOneByte(rawData[i]) == 0x55 && convertOneByte(rawData[i + 1]) == 0xaa) {// Checks for first sync word.
				// Checks for begginning of new frame represented by another sync word.
				if(convertOneByte(rawData[i + 2]) == 0x55 && convertOneByte(rawData[i + 3]) == 0xaa) {
					// System.out.println("Found two Sync words.");
					i += 4; // Skips the 2 sync words representing the start of the frame.
					putInArray(i, 0); // Puts the first recieved block in the first position.
				} else { // Means its a new block but not a new frame.
					// System.out.println("First Sync word was found but no follow up...");
					i += 2; // Skips the sync word representing the start of the block.
					putInArray(i, 1); // Puts the second recieved block in the second position.
				}
			}
		}

		// This stuff here is for debugging.
		// <editor-fold>
		// System.out.println( "------Here------" );
		// // System.out.println(String.format("0x%02X", rawData[1]) + " and " + String.format("0x%02X", rawData[0]) + " make" );
		// // System.out.println(String.format("0x%04X",convert(rawData[1], rawData[0])));
		// for( int i = 0; i < rawData.length; i += 1){
		// 	System.out.println(String.format("0x%02X", rawData[i]));//convert(rawData[i + 1], rawData[i + 0])));
		// 	// if( convert(rawData[i + 1], rawData[i + 0]) == 0xaa55 && convert(rawData[i + 3], rawData[i + 2]) == 0xaa55 ){
		// 	// 	System.out.println( "Sync found at " + i );
		// 	// }
		// }

		// if( convert(rawData[0 + 1], rawData[0 + 0]) != 0 ){
		// 	// if( convert(rawData[0 + 1], rawData[0 + 0]) != 0xaa55 && convert(rawData[0 + 1], rawData[0 + 0]) != 43605 ){
		// 	// 	System.out.println( convert(rawData[0 + 1], rawData[0 + 0]) );
		// 	// } else {
		// 	// 	System.out.println( "VVVVAAAAATTTTT" );
		// 	// }
		// } else {
		// 	System.out.println( "Help me....." );
		// }

		// // Basically, this searches for the last sync word (start flag) and only checks the first
        // // 16 bytes of the 32 byte array becasue the blocks are about 14 to 16 bytes and then
        // // parses the next bytes accordingly.
		// for (int i = 0; i <= 16; i++) {
        //     int syncWord = convert(rawData[i + 1], rawData[i + 0]); // Parse first 2 bytes
            
        //     // Check is first 2 bytes equal a "sync word", which indicates the start of a packet of valid data
        //     if (syncWord == 0xaa55) {
		// 		syncWord = convert(rawData[i + 3], rawData[i + 2]); // Parse the next 2 bytes
		// 		if (syncWord != 0xaa55) { // Shifts everything in the case that one syncword is sent
		// 			i -= 2;
        //         }
                
		// 		// This rest of this code parses the rest of the data
        //         checksumData = convert(rawData[i + 5], rawData[i + 4]);
        //         signatureData = convert(rawData[i + 7], rawData[i + 6]);
                
        //         // Breaks if the recieved block has a signature of 0 or greater than the number
        //         // of used signatures being used.
		// 		if (signatureData <= 0 || signatureData > numOfSignaturesUsed) {
		// 			break;
		// 		}

        //         // Makes a PixyBlock in the signatures array under the index of the recieved
        //         // block's signature (-1 becasue signatures start at 1 but arrays start at 0)
        //         blocks[currentIndex] = new PixyBlock();
                
        //         //Sets values in the current block's array value
        //         blocks[currentIndex].Signature = signatureData;
		// 		blocks[currentIndex].X = convert(rawData[i + 9], rawData[i + 8]);
		// 		blocks[currentIndex].Y = convert(rawData[i + 11], rawData[i + 10]);
		// 		blocks[currentIndex].Width = convert(rawData[i + 13], rawData[i + 12]);
        //         blocks[currentIndex].Height = convert(rawData[i + 15], rawData[i + 14]);
                
		// 		// Checks whether the data is valid using the checksum *This if block should never be entered*
        //         if (checksumData != (blocks[currentIndex].Signature + blocks[currentIndex].X + blocks[currentIndex].Y
        //                 + blocks[currentIndex].Width + blocks[currentIndex].Height)) {
		// 			blocks[currentIndex] = null;
		// 			System.out.println("CHECKSUM DATA DOES NOT CHECKOUT!!!!!!!!!!!!!!!!");
		// 		}
		// 		break;
		// 	} else {
        //         Dashboard.send(name + "Sync Word?:", syncWord);
		// 	}
		// }
		
		// // Switches between index 0 and 1.
		// currentIndex = currentIndex == 1 ? 0 : 1;
		// </editor-fold>
	} // End of updateBlocks

	// startIndex = start position of a block of information not counting the sync words.
	// blockIndex = which position to place the new PixyBlock in.
	private void putInArray(int startIndex, int blockIndex) {
		// <editor-fold>
		// Some Debug Stuff
		// System.out.println("=======================");
		// System.out.println(String.format("0x%02X", rawData[startIndex]));
		// System.out.println(String.format("0x%02X", rawData[startIndex+1]));

		// System.out.println(String.format("0x%02X", rawData[startIndex+2]));
		// System.out.println(String.format("0x%02X", rawData[startIndex+3]));

		// System.out.println(String.format("0x%02X", rawData[startIndex+4]));
		// System.out.println(String.format("0x%02X", rawData[startIndex+5]));

		// System.out.println(String.format("0x%02X", rawData[startIndex+6]));
		// System.out.println(String.format("0x%02X", rawData[startIndex+7]));
		
		// System.out.println(String.format("0x%02X", rawData[startIndex+8]));
		// System.out.println(String.format("0x%02X", rawData[startIndex+9]));
		
		// System.out.println(String.format("0x%02X", rawData[startIndex+10]));
		// System.out.println(String.format("0x%02X", rawData[startIndex+11]));
		
		// System.out.println(String.format("0x%02X", rawData[startIndex+12]));
		// System.out.println(String.format("0x%02X", rawData[startIndex+13]));
		
		// System.out.println(String.format("0x%02X", rawData[startIndex+14]));
		// System.out.println(String.format("0x%02X", rawData[startIndex+15]));

		// System.out.println(String.format("0x%04X", convert(rawData[startIndex+1], rawData[startIndex])));
		// System.out.println(String.format("0x%04X", convert(rawData[startIndex+3], rawData[startIndex+2])));
		// System.out.println(String.format("0x%04X", convert(rawData[startIndex+5], rawData[startIndex+4])));
		// System.out.println(String.format("0x%04X", convert(rawData[startIndex+7], rawData[startIndex+6])));
		// System.out.println(String.format("0x%04X", convert(rawData[startIndex+9], rawData[startIndex+8])));
		// System.out.println(String.format("0x%04X", convert(rawData[startIndex+11], rawData[startIndex+10])));
		// System.out.println(String.format("0x%04X", convert(rawData[startIndex+13], rawData[startIndex+12])));
		// System.out.println(String.format("0x%04X", convert(rawData[startIndex+15], rawData[startIndex+14])));
		// </editor-fold>
		
		// Tests if start point and its sequential data does not totally fit in the array and
		// fills the overflow array with the remaining bytes. 12 is block size minus sync.
		System.out.print( startIndex + " > " + ( rawData.length - 12 ) + ": " + (startIndex > rawData.length - 12) );
		if(startIndex > rawData.length - 12) {
			System.out.println("Wrapping pixy sends...");
			overflowRawData = new byte[12];
			byte[] tempRawData = new byte[12 - (rawData.length - (startIndex - 1))]; // Size of the remaining needed bytes

			// Reading Pixy Data
			try {
				pixy.readOnly(tempRawData, tempRawData.length);
			} catch (RuntimeException e) {
				System.out.println(name + " - " + e);
			}

			// Checks to see if the pixy has sent new data instead of overflow data.
			if(convertOneByte(tempRawData[0]) == 0x55 && convertOneByte(tempRawData[1]) == 0xaa){
				System.out.println("Pixy sent new data instead of continuing data");
				rawData = tempRawData;
				putInArray(0, 0); // Check logic confirming rawData doesnt stay the length of tempRawData indefinatly.(it shouldnt)
				return;
			}

			int j = 0; // could just use math in place of this variable but it makes it easier to read.
			// Fills overflow array with the remaining in rawData and the next ones in overflowRawData.
			for(int i = 0; i < overflowRawData.length - 2; i++) {
				if(i < rawData.length) {
					overflowRawData[i] = rawData[i + startIndex];
				} else {
					overflowRawData[i] = tempRawData[j];
					j++;
				}
			}
			useOverflow = true;
		} else {
			System.out.println("Not Wrapping Pixy Sends...");
		}

		int checksumData;
		int signatureData;
		if( useOverflow ) {
			checksumData = convert(overflowRawData[1], overflowRawData[0]);
			signatureData = convert(overflowRawData[3], overflowRawData[2]);

			// Returns if the recieved block has a signature of 0 or greater than the number
			// of used signatures being used (1 right now).
			if (signatureData <= 0 || signatureData > 1) {
				System.out.println("Invalid Signature " + signatureData + " in " + name);
				return;
			}

			blocks[blockIndex] = new PixyBlock();

			//Sets values in the current block's array value
			blocks[blockIndex].Signature = signatureData;
			blocks[blockIndex].X = convert(overflowRawData[5], overflowRawData[4]);
			blocks[blockIndex].Y = convert(overflowRawData[7], overflowRawData[6]);
			blocks[blockIndex].Width = convert(overflowRawData[9], overflowRawData[8]);
			blocks[blockIndex].Height = convert(overflowRawData[11], overflowRawData[10]);

			useOverflow = false;
		} else {
			checksumData = convert(rawData[startIndex + 1], rawData[startIndex]);
			signatureData = convert(rawData[startIndex + 3], rawData[startIndex + 2]);

			// Returns if the recieved block has a signature of 0 or greater than the number
			// of used signatures being used (1 right now).
			if (signatureData <= 0 || signatureData > 1) {
				System.out.println("Invalid Signature " + signatureData + " in " + name);
				return;
			}

			blocks[blockIndex] = new PixyBlock();

			//Sets values in the current block's array value
			blocks[blockIndex].Signature = signatureData;
			blocks[blockIndex].X = convert(rawData[startIndex + 5], rawData[startIndex + 4]);
			blocks[blockIndex].Y = convert(rawData[startIndex + 7], rawData[startIndex + 6]);
			blocks[blockIndex].Width = convert(rawData[startIndex + 9], rawData[startIndex + 8]);
			blocks[blockIndex].Height = convert(rawData[startIndex + 11], rawData[startIndex + 10]);
		}

		// Checks whether the data is valid using the checksum *This if block should never be entered*
		if (checksumData != blocks[blockIndex].getSum()) {
			blocks[blockIndex] = null;
			System.out.println("CHECKSUM DATA DOES NOT CHECKOUT!!!!!!!!!!!!!!!!");
			return;
		}
		hasNewBlocks = true;
	} // End of putInArray

	public PixyBlock[] getBlocks() {
		hasNewBlocks = false;
		return blocks;
	}

	public boolean hasNewBlocks() {
		return hasNewBlocks;
	}

	public void blocksToDashboard() {
		if(blocks[0] != null){
			Dashboard.send("X1", blocks[0].X);
		}
		if(blocks[1] != null){
			Dashboard.send("X2", blocks[1].X);
		}
	}
} // End of class
	// Old Stuff
	// <editor-fold>
    // This method gathers data, then parses that data, and assigns the ints to global variables.
    // The signature should be which number object in pixymon you are trying to get data for.
    // It returns a PixyBlock so you have access to all information given by the pixy (minus checksum).
	// public PixyBlock readPacket(int Signature) {
	// 	int checksumData;
    //     int signatureData;
        
	// 	byte[] rawData = new byte[32];
        
    //     // Reading Pixy Data
	// 	try {
	// 		pixy.readOnly(rawData, 32);
	// 	} catch (RuntimeException e) {
	// 		System.out.println(name + " - " + e);
    //     }
        
    //     // Testing for a bad things that *should* never happen.
	// 	if (rawData.length < 32) {
	// 		System.out.println("Byte array length is broken in " + this.name + ", " + " length=" + rawData.length);
	// 		return null;
    //     }
        
    //     // Basically, this searches for the last sync word (start flag) and only checks the first
    //     // 16 bytes of the 32 byte array becasue the blocks are about 14 to 16 bytes and then
    //     // parses the next bytes accordingly.
	// 	for (int i = 0; i <= 16; i++) {
    //         int syncWord = convert(rawData[i + 1], rawData[i + 0]); // Parse first 2 bytes
            
    //         // Check is first 2 bytes equal a "sync word", which indicates the start of a packet of valid data
    //         if (syncWord == 0xaa55) { 
	// 			syncWord = convert(rawData[i + 3], rawData[i + 2]); // Parse the next 2 bytes
	// 			if (syncWord != 0xaa55) { // Shifts everything in the case that one syncword is sent
	// 				i -= 2;
    //             }
                
	// 			// This rest of this code parses the rest of the data
    //             checksumData = convert(rawData[i + 5], rawData[i + 4]);
    //             signatureData = convert(rawData[i + 7], rawData[i + 6]);
                
    //             // Breaks if the recieved block has a signature of 0 or greater than the number
    //             // of used signatures being used.
	// 			if (signatureData <= 0 || signatureData > blocks.length) {
	// 				break;
	// 			}

    //             // Makes a PixyBlock in the signatures array under the index of the recieved
    //             // block's signature (-1 becasue signatures start at 1 but arrays start at 0)
    //             blocks[signatureData - 1] = new PixyBlock();
                
    //             //Sets values in the current block's array value
    //             blocks[signatureData - 1].Signature = signatureData;
	// 			blocks[signatureData - 1].X = convert(rawData[i + 9], rawData[i + 8]);
	// 			blocks[signatureData - 1].Y = convert(rawData[i + 11], rawData[i + 10]);
	// 			blocks[signatureData - 1].Width = convert(rawData[i + 13], rawData[i + 12]);
    //             blocks[signatureData - 1].Height = convert(rawData[i + 15], rawData[i + 14]);
                
	// 			// Checks whether the data is valid using the checksum *This if block should never be entered*
    //             if (checksumData != (signatureData + blocks[signatureData - 1].X + blocks[signatureData - 1].Y
    //                     + blocks[signatureData - 1].Width + blocks[signatureData - 1].Height)) {
	// 				blocks[signatureData - 1] = null;
	// 				System.out.println("CHECKSUM DATA DOES NOT CHECKOUT!!!!!!!!!!!!!!!!");
	// 			}
	// 			break;
	// 		} else {
    //             Dashboard.send(name + "Sync Word?:", syncWord);
    //         }
    //     }
        
	// 	// Assigns our packet to a temp packet, then deletes data so that we dont return old data
	// 	PixyBlock tempBlock = blocks[Signature - 1];
	// 	blocks[Signature - 1].zero();
	// 	return tempBlock;
	// }
	// </editor-fold>