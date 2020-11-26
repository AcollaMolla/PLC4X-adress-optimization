package com.uddeholm;

public class Machine {
	
	private String name;
	private String address; //ie DB63.DBD0:REAL[4]
	private int datablock;
	private int offset;
	private int size;
	
	public Machine(String name, String address, int datablock, int offset, int size) {
		this.name = name;
		this.address = address;
		this.datablock = datablock;
		this.offset = offset;
		this.size = size;
	}
	
	public String GetAddress() {
		return this.address;
	}
	
	public int GetDatablock() {
		return this.datablock;
	}
	
	public int GetOffset() {
		return this.offset;
	}

}
