package com.uddeholm;

import java.util.ArrayList;
import java.util.List;

import com.uddeholm.s7.S7Datatypes;
import com.uddeholm.s7.S7MemoryAreas;
import com.uddeholm.s7.S7RequestOptimizer;
import com.uddeholm.s7.S7Signal;
import com.uddeholm.s7.SignalTree;

public class Main {

	public static void main(String[] args) {
		//Create some mock S7Signals to see how the optimization works
		List<S7Signal> signals = new ArrayList<S7Signal>();
		SignalTree signalTree;
		
		S7Signal m1 = new S7Signal("signal1", 63, 0, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m2 = new S7Signal("signal2", 63, 4, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		/*S7Signal m3 = new S7Signal("signal3", 63, 20, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m4 = new S7Signal("signal4", 80, 0, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m5 = new S7Signal("signal5", 80, 16, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m6 = new S7Signal("signal6", 60, 0, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m7 = new S7Signal("signal7", 63, 12, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m8 = new S7Signal("signal8", 63, 24, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m9 = new S7Signal("signal9", 63, 8, 1, S7Datatypes.INT, S7MemoryAreas.DB);
		S7Signal m10 = new S7Signal("signal10", 63, 12, 1, S7Datatypes.INT, S7MemoryAreas.DB);*/
		S7Signal m11 = new S7Signal("signal11", null, 88, 0, 1, S7Datatypes.BOOL, S7MemoryAreas.M);
		S7Signal m12 = new S7Signal("signal12", 0, 86, 0, 1, S7Datatypes.BOOL, S7MemoryAreas.DB);

		
		/*signals.add(m9);
		signals.add(m8);
		signals.add(m3);*/
		signals.add(m1);
		signals.add(m2);
		/*signals.add(m4);
		signals.add(m5);
		signals.add(m6);
		signals.add(m7);
		signals.add(m10);*/
		signals.add(m11);
		signals.add(m12);
		
		//S7RequestOptimizer optimizer = new S7RequestOptimizer();
		//signalTree = optimizer.CreateOptimizedS7SignalList(signals, 4);
		signalTree = S7RequestOptimizer.CreateOptimizedS7SignalList(signals);
		
		signalTree.Print();
	}


}
