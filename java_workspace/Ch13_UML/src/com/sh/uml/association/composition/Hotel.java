package com.sh.uml.association.composition;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
	private String name;
	private List<Room> rooms = new ArrayList<>();
	
	public Hotel(String name, int roomCnt) {
		this.name = name;
		
		for(int i = 0; i < roomCnt; i++) {
			rooms.add(new Room(i + 1));
		}
	}

	@Override
	public String toString() {
		return "Hotel [name=" + name + ", rooms=" + rooms + "]";
	}
}
