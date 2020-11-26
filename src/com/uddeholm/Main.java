package com.uddeholm;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		//Create some mock Signals to see how the optimization works
		List<Signal> signals = new ArrayList<Signal>();
		List<List<Signal>> optimizedList;
		
		Signal m1 = new Signal("signal1", "DB63.DBD0:REAL[4]", 63, 0, 4, S7Datatypes.REAL, S7MemoryAreas.DB);
		Signal m2 = new Signal("signal2", "DB63.DBD4:REAL[4]", 63, 4, 4, S7Datatypes.REAL, S7MemoryAreas.DB);
		Signal m9 = new Signal("signal9", "DB63.DBW8:INT[4]", 63, 8, 4, S7Datatypes.INT, S7MemoryAreas.DB);
		Signal m10 = new Signal("signal10", "DB63.DBW10:INT[4]", 63, 12, 4, S7Datatypes.INT, S7MemoryAreas.DB);
		Signal m3 = new Signal("signal3", "DB63.DBD20:REAL[4]", 63, 20, 4, S7Datatypes.REAL, S7MemoryAreas.DB);
		Signal m8 = new Signal("signal8", "DB63.DBD24:REAL[4]", 63, 24, 4, S7Datatypes.REAL, S7MemoryAreas.DB);
		Signal m7 = new Signal("signal7", "DB63.DBD12:REAL[4]", 63, 12, 4, S7Datatypes.REAL, S7MemoryAreas.DB);
		Signal m4 = new Signal("signal4", "DB80.DBD0:REAL[4]", 80, 0, 4, S7Datatypes.REAL, S7MemoryAreas.DB);
		Signal m5 = new Signal("signal5", "DB80.DBD16:REAL[4]", 80, 16, 4, S7Datatypes.REAL, S7MemoryAreas.DB);
		Signal m6 = new Signal("signal6", "DB60.DBD0:REAL[4]", 60, 0, 4, S7Datatypes.REAL, S7MemoryAreas.DB);
		
		signals.add(m9);
		signals.add(m8);
		signals.add(m3);
		signals.add(m1);
		signals.add(m2);
		signals.add(m4);
		signals.add(m5);
		signals.add(m6);
		signals.add(m7);
		signals.add(m10);
		
		RequestOptimizer optimizer = new RequestOptimizer();
		optimizedList = optimizer.CreateOptimizedSignalList(signals, 4);
		List<String> optimizedReadRequest = optimizer.GetOptimizedSignalsList(optimizedList);
		PrintOptimizedList(optimizedList);
		for(String request : optimizedReadRequest) {
			System.out.println(request);
		}

	}

	private static void PrintOptimizedList(List<List<Signal>> optimizedList) {
		for(List<Signal> list : optimizedList) {
			for(Signal machine : list) {
				System.out.println(machine.GetAddress());
			}
			System.out.println("-------------");
		}
		
	}

}
