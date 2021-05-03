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
		
		S7Signal m1 = new S7Signal("signal1", 2063, 10, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m2 = new S7Signal("signal2", 2063, 18, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m3 = new S7Signal("signal3", 2063, 14, 1, S7Datatypes.REAL, S7MemoryAreas.DB);
		S7Signal m4 = new S7Signal("signal4", 2063, 22, 1, S7Datatypes.REAL, S7MemoryAreas.DB);

		signals.add(m1);
		signals.add(m2);
		signals.add(m3);
		signals.add(m4);
		
		//S7RequestOptimizer optimizer = new S7RequestOptimizer();
		//signalTree = optimizer.CreateOptimizedS7SignalList(signals, 4);
		signalTree = S7RequestOptimizer.CreateOptimizedS7SignalList(signals);
		
		signalTree.Print();
	}


}
