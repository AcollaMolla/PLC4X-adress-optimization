package com.uddeholm.s7;

public class S7MemoryMappings {
	
	public static String GetMemoryArea(S7MemoryAreas memoryArea) {
		switch(memoryArea) {
		case DB:
			return "DB";
		default:
			return "DB";
		}
	}
	
	public static String GetShorthandDatatype(S7Datatypes datatype) {
		return "B";
	}
	
	public static String GetDatatype(S7Datatypes datatype) {
		return "BYTE";
	}

}
