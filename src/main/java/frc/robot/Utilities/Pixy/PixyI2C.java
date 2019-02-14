package frc.robot.Utilities.Pixy;

import edu.wpi.first.wpilibj.I2C;
import frc.robot.RobotMap;
import java.util.List;
import java.util.ArrayList;
// Our imports
import frc.robot.Utilities.Dashboard;
import frc.robot.Utilities.ConsoleLogger;

public class PixyI2C {
	public enum PixyIteratorEnum{
		LOOKING,
		POSSIBLE_BLOCK_SYNC,
		POSSIBLE_FRAME_SYNC,
		SYNCED,
	}
	final int SYNC_FIRST_HALF = 0x55;
	final int SYNC_SECOND_HALF = 0xaa;

	PixyIteratorEnum iteratorState = PixyIteratorEnum.LOOKING;

	private String name;
	private I2C pixy;
	
	byte[] rawData;

	private PixyBlock[] blocks; // Stores PixyBlocks. Should not go above 2.
	private List<Integer> bytes; // Stores current assembling block.
	private int blockIndex = 0;
	
	boolean hasNewBlocks = false;

	public PixyI2C(String id, I2C pixy) {
        this.name = "Pixy_" + id; // ID is not used anywhere and is souly for name.
        this.pixy = pixy;

		this.blocks = new PixyBlock[2];
		this.bytes = new ArrayList<Integer>();
	}

	// This method turns raw data from the pixy into readable integers. // Should not be needed anymore...
	// public int convert(byte upper, byte lower) {
	// 	return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
	// }

	// This method converts a single byte into an int that accuratly represents it as unsigned
	public int convertOneByte(byte bt) {
		return ((int) bt & 0xff);
	}

	public int combineConvertedBytes(int upper, int lower) {
		return(upper << 8) | lower;
	}

	public void update() {
		rawData = new byte[30];

		// Reading Pixy Data
		try {
			pixy.readOnly(rawData, rawData.length);
		} catch (RuntimeException e) {
			ConsoleLogger.error(name + " - Error Reading Data - " + e);
		}

		// System.out.println("============================");

		for(int i = 0; i < rawData.length; i++) {
			int currentByte = convertOneByte(rawData[i]);
			// System.out.println("Current Byte: " + currentByte);

			if(currentByte == SYNC_FIRST_HALF) {
				// System.out.println("CB is SYNC_FIRST_HALF");
				if(iteratorState != PixyIteratorEnum.SYNCED) {
					// System.out.println("Currently not in Sync. Switching to POSSIBLE_BLOCK_SYNC");
					iteratorState = PixyIteratorEnum.POSSIBLE_BLOCK_SYNC;
				} else if(iteratorState == PixyIteratorEnum.SYNCED) {
					// System.out.println("Currently in Sync. Switching to POSSIBLE_FRAME_SYNC");
					iteratorState = PixyIteratorEnum.POSSIBLE_FRAME_SYNC;
				}
			} else if(currentByte == SYNC_SECOND_HALF) {
				// System.out.println("CB is SYNC_SECOND_HALF");
				if(iteratorState == PixyIteratorEnum.POSSIBLE_BLOCK_SYNC) {
					// System.out.println("Currently in POSSIBLE_BLOCK_SYNC. Switching to Synced");
					iteratorState = PixyIteratorEnum.SYNCED;
					blockIndex = 1;
				} else if(iteratorState == PixyIteratorEnum.POSSIBLE_FRAME_SYNC) {
					// System.out.println("Currently in POSSIBLE_FRAME_SYNC. Switching to Synced");
					iteratorState = PixyIteratorEnum.SYNCED;
					blockIndex = 0;
				}
			} else {
				// System.out.println("CB was neither sync byte.");
				if(iteratorState == PixyIteratorEnum.POSSIBLE_BLOCK_SYNC || iteratorState == PixyIteratorEnum.POSSIBLE_FRAME_SYNC) {
					// System.out.println("Currently in possible sync. did not find second sync, so added trigger to data.");
					iteratorState = PixyIteratorEnum.SYNCED;
					add(SYNC_FIRST_HALF);
				}
				if(iteratorState == PixyIteratorEnum.SYNCED) {
					add(currentByte);
					// iteratorState = PixyIteratorEnum.LOOKING;
				}
				// NOTE!!!!!: iteratorState is set to looking in add();
			}
		//	<editor-fold>
		// 	Logger.debug("Iterating " + i);
		// 	int currentByte = convertOneByte(rawData[i]);
		// 	Logger.debug("current Byte: " + String.format("0x%02X", currentByte));

		// 	if(currentByte == 0x55) {
		// 		iteratorState = PixyIteratorEnum.POSSIBLE_BLOCK_SYNC;
		// 		Logger.debug("Possible Block Sync State");
		// 		continue;
		// 	}
		// 	if(currentByte == 0xaa && iteratorState == PixyIteratorEnum.POSSIBLE_BLOCK_SYNC) {
		// 		Logger.debug("SYNCED State");
		// 		iteratorState = PixyIteratorEnum.SYNCED;
		// 		blockIndex = 1;
		// 		bytes.clear();
		// 		continue; // Dont want put this byte into the array...
		// 	}
		// 	if(currentByte != 0xaa && iteratorState == PixyIteratorEnum.POSSIBLE_BLOCK_SYNC) {
		// 		Logger.debug("Going back to SYNCED state from Block");				
		// 		bytes.add(0x55);
		// 		iteratorState = PixyIteratorEnum.SYNCED;
		// 	}

		// 	if(currentByte == 0x55 && iteratorState == PixyIteratorEnum.SYNCED) {
		// 		Logger.debug("POSSIBLE_FRAME_SYNC state");
		// 		iteratorState = PixyIteratorEnum.POSSIBLE_FRAME_SYNC;
		// 		continue;
		// 	}
		// 	if(currentByte == 0xaa && iteratorState == PixyIteratorEnum.POSSIBLE_FRAME_SYNC) {
		// 		Logger.debug("-----------------------------\nFRAME Synced State");
		// 		iteratorState = PixyIteratorEnum.SYNCED;
		// 		blockIndex = 0;
		// 		bytes.clear();
		// 		continue; // Dont want put this byte into the array...
		// 	}
		// 	if(currentByte != 0xaa && iteratorState == PixyIteratorEnum.POSSIBLE_FRAME_SYNC) {
		// 		Logger.debug("Going back to SYNCED State from FRAME");
		// 		bytes.add(0x55);
		// 		iteratorState = PixyIteratorEnum.SYNCED;
		// 	}

		// 	if(iteratorState == PixyIteratorEnum.SYNCED) {
		// 		Logger.debug("Adding Current byte " + currentByte + " to bytes");
		// 		bytes.add(currentByte);
		// 	}


		// 	if(bytes.size() == 12) {
		// 		Logger.debug("bytes filled: " + bytes);
		// 		if(iteratorState != PixyIteratorEnum.SYNCED) {
		// 			Logger.error("Reached size 12 but not in a synced state....");
		// 		}
		// 		//if(iteratorState == PixyIteratorEnum.SYNCED){
		// 		System.out.println(blockIndex);
		// 		blocks[blockIndex] = parsePixyData(bytes);
		// 		//}
		// 		iteratorState = PixyIteratorEnum.LOOKING;
		// 		bytes.clear();
		// 	} 
		// </editor-fold>
		}

	} // End of update

	public PixyBlock parsePixyData(List<Integer> data) {
		//System.out.println(data);
		return new PixyBlock(
			combineConvertedBytes(data.get(1), data.get(0)), // Checksum
			combineConvertedBytes(data.get(3), data.get(2)), // Signature
			combineConvertedBytes(data.get(5), data.get(4)), // X
			combineConvertedBytes(data.get(7), data.get(6)), // Y
			combineConvertedBytes(data.get(9), data.get(8)), // Width
			combineConvertedBytes(data.get(11), data.get(10))// Height
		);
	}

	public void add(int i) {
		bytes.add(i);
		ConsoleLogger.debug("Added: " + i);

		if(bytes.size() == 12) {
			ConsoleLogger.debug("Filled bytes into " + blockIndex + ": " + bytes);
			//System.out.println("Filled bytes into " + blockIndex + ": " + bytes);
			if(iteratorState != PixyIteratorEnum.SYNCED) {
				ConsoleLogger.error("Reached size 12 but not in a synced state....");
			}
			blocks[blockIndex] = parsePixyData(bytes);
			//System.out.println(blocks[blockIndex]);
			
			iteratorState = PixyIteratorEnum.LOOKING;
			bytes.clear();
		}
	}

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
			Dashboard.send("Y1", blocks[0].Y);
		}
		if(blocks[1] != null){
			Dashboard.send("X2", blocks[1].X);
			Dashboard.send("Y2", blocks[1].Y);
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