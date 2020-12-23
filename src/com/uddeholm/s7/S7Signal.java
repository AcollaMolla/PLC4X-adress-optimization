package com.uddeholm.s7;

public class S7Signal implements Comparable<S7Signal>{
	
	private String name;
	private Object value;
	private int datablock;
	private int offset;
	private int bitoffset;
	private int size;
	private S7Datatypes nativeDatatype;
	private S7MemoryAreas memoryArea;
	private String datatype;
	
	public S7Signal(String name, Integer datablock, int offset, int size, S7Datatypes datatype, S7MemoryAreas memoryArea) {
		this.name = name;
		this.datablock = datablock != null ? datablock : -1;
		this.offset = offset;
		this.bitoffset = 0;
		this.size = size;
		this.nativeDatatype = datatype;
		this.memoryArea = memoryArea;
		this.datatype = SetDatatype();
	}
	
	public S7Signal(String name, Integer datablock, int offset, int bitoffset, int size, S7Datatypes datatype, S7MemoryAreas memoryArea) {
		this.name = name;
		this.datablock = datablock != null ? datablock : -1;
		this.offset = offset;
		this.bitoffset = bitoffset;
		this.size = size;
		this.nativeDatatype = datatype;
		this.memoryArea = memoryArea;
		this.datatype = SetDatatype();
	}
	
	public String GetName() {
		return this.name;
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
	
	public int GetBitoffset() {
		return this.bitoffset;
	}
	
	public S7Datatypes GetNativeDatatype() {
		return this.nativeDatatype;
	}
	
	public String GetDatatype() {
		return this.datatype;
	}
	
	public String TestMe() {
		return "hello";
	}
	
	public String GetNativeDatatype(boolean asByte) {
		if(asByte) {
			return "BYTE";
		}
		else {
			switch(this.nativeDatatype) {
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
	
	public S7MemoryAreas GetMemoryArea() {
		return this.memoryArea;
	}
	
	public String GetMemoeyArea() {
		return this.memoryArea.toString();
	}
	
	public String GetDataTypeShortCode(boolean asByte) {
		if(asByte) {
			return "DBB";
		}
		else {
			switch(this.nativeDatatype) {
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
		switch(this.nativeDatatype) {
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
	
	private String SetDatatype() {
		switch(this.nativeDatatype) {
		case REAL:
			return "float";
		case INT:
			return "int";
		case TIME:
			return "int";
		case BOOL:
			return "boolean";
		default:
			return "int";
		}
	}

}
