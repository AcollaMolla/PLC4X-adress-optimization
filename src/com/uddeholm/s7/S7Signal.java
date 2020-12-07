package com.uddeholm.s7;

public class S7Signal implements Comparable<S7Signal>{
	
	private String name;
	private Object value;
	private int datablock;
	private int offset;
	private int bitoffset;
	private int size;
	private S7Datatypes datatype;
	private S7MemoryAreas memoryArea;
	
	public S7Signal(String name, int datablock, int offset, int size, S7Datatypes datatype, S7MemoryAreas memoryArea) {
		this.name = name;
		this.datablock = datablock;
		this.offset = offset;
		this.bitoffset = 0;
		this.size = size;
		this.datatype = datatype;
		this.memoryArea = memoryArea;
	}
	
	public S7Signal(String name, int datablock, int offset, int bitoffset, int size, S7Datatypes datatype, S7MemoryAreas memoryArea) {
		this.name = name;
		this.datablock = datablock;
		this.offset = offset;
		this.bitoffset = bitoffset;
		this.size = size;
		this.datatype = datatype;
		this.memoryArea = memoryArea;
	}
	
	public void SetValue(Object value) {
		this.value = value;
	}
	
	public Object GetValue() {
		return this.value;
	}
	
	public int GetDatablock() {
		return this.datablock;
	}
	
	public int GetOffset() {
		return this.offset;
	}
	
	public S7Datatypes GetDatatype() {
		return this.datatype;
	}
	
	public String GetStringDatatype(boolean asByte) {
		if(asByte) {
			return "BYTE";
		}
		else {
			switch(this.datatype) {
			case REAL:
				return "REAL";
			case INT:
				return "INT";
			case BOOL:
				return "BYTE";
			default:
				return "REAL";
			}	
		}
	}
	
	public String GetStringMemoryArea() {
		switch(this.memoryArea) {
		case DB:
			return "DB";
		default:
			return "DB";
		}
	}
	
	public String GetDataTypeShortCode(boolean asByte) {
		if(asByte) {
			return "DBB";
		}
		else {
			switch(this.datatype) {
			case REAL:
				return "DBD";
			case BOOL:
				return "DBB";
			case INT:
				return "DBW";
			default:
				return "DBB";
			}	
		}
	}
	
	public int GetSize() {
		switch(this.datatype) {
		case REAL:
			return 4;
		case INT:
			return 2;
		case BOOL:
			return 1;
		default:
			return 1;
		}
	}

	@Override
	public int compareTo(S7Signal o) {
		if(this.offset > o.GetOffset()) {
			return 1;
		}
		else {
			return -1;
		}
	}

	public String GetAddress() {
		return "TODO";
	}

}
