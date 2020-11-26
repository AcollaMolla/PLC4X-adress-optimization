package com.uddeholm;

import java.util.ArrayList;
import java.util.List;

import com.uddeholm.s7.S7Datatypes;
import com.uddeholm.s7.S7MemoryAreas;
import com.uddeholm.s7.S7RequestOptimizer;
import com.uddeholm.s7.S7Signal;

public class Main {

	public static void main(String[] args) {
		//Create some mock S7Signals to see how the optimization works
		List<S7Signal> signals = new ArrayList<S7Signal>();
		List<List<S7Signal>> optimizedList;
		
		S7Signal m1 = new S7Signal("signal1", "DB63.DBD0:REAL[4]", 63, 0, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m2 = new S7Signal("signal2", "DB63.DBD4:REAL[4]", 63, 4, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m9 = new S7Signal("signal9", "DB63.DBW8:INT[1]", 63, 8, 1, S7Datatypes.INT, S7MemoryAreas.DB);
		S7Signal m10 = new S7Signal("signal10", "DB63.DBW12:INT[1]", 63, 12, 1, S7Datatypes.INT, S7MemoryAreas.DB);
		S7Signal m3 = new S7Signal("signal3", "DB63.DBD20:REAL[4]", 63, 20, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m8 = new S7Signal("signal8", "DB63.DBD24:REAL[4]", 63, 24, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m7 = new S7Signal("signal7", "DB63.DBD12:REAL[4]", 63, 12, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m4 = new S7Signal("signal4", "DB80.DBD0:REAL[4]", 80, 0, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m5 = new S7Signal("signal5", "DB80.DBD16:REAL[4]", 80, 16, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m6 = new S7Signal("signal6", "DB60.DBD0:REAL[4]", 60, 0, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		
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
		
		S7RequestOptimizer optimizer = new S7RequestOptimizer();
		optimizedList = optimizer.CreateOptimizedS7SignalList(signals, 4);
		List<String> optimizedReadRequest = optimizer.GetOptimizedS7SignalsList(optimizedList);
		PrintOptimizedList(optimizedList);
		for(String request : optimizedReadRequest) {
			System.out.println(request);
		}

	}

	private static void PrintOptimizedList(List<List<S7Signal>> optimizedList) {
		for(List<S7Signal> list : optimizedList) {
			for(S7Signal machine : list) {
				System.out.println(machine.GetAddress());
			}
			System.out.println("-------------");
		}
		
	}

}
