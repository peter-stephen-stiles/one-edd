package com.nobbysoft.com.nobbysoft.first;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.FileOutputStream;
import java.io.IOException;

import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SaveLoadPC {

	public void savePC(String fileName, PlayerCharacter pc) throws FileNotFoundException, IOException {

		try (FileOutputStream fos1 = new FileOutputStream(fileName);) {
			try (XMLEncoder xe1 = new XMLEncoder(fos1);) {
				xe1.writeObject(pc);
			}
		}
	}

	public PlayerCharacter loadPC(String fileName) throws FileNotFoundException, IOException {
		try (FileInputStream fos1 = new FileInputStream(fileName);) {
			try (XMLDecoder xe1 = new XMLDecoder(fos1);) {
				PlayerCharacter pc = (PlayerCharacter)xe1.readObject();
				return pc;
			}
		}
	}

}
