package com.uddeholm;

public class Signal {
	
	private String name;
	private String address; //ie DB63.DBD0:REAL[4]
	private int datablock;
	private int offset;
	private int size;
	private int datatype;
	
	public Signal(String name, String address, int datablock, int offset, int size, int datatype) {
		this.name = name;
		this.address = address;
		this.datablock = datablock;
		this.offset = offset;
		this.size = size;
		this.datatype = datatype;
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
	
	public int GetDatatype() {
		return this.datatype;
	}

}
