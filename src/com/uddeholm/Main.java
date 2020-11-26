package com.uddeholm;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Machine> machines = new ArrayList<Machine>();
		List<List<Machine>> optimizedList;
		Machine m1 = new Machine("signal1", "DB63.DBD0:REAL[4]", 63, 0, 4);
		Machine m2 = new Machine("signal2", "DB63.DBD4:REAL[4]", 63, 4, 4);
		Machine m3 = new Machine("signal3", "DB63.DBD20:REAL[4]", 63, 20, 4);
		Machine m8 = new Machine("signal8", "DB63.DBD24:REAL[4]", 63, 24, 4);
		Machine m7 = new Machine("signal7", "DB63.DBD12:REAL[4]", 63, 12, 4);
		Machine m4 = new Machine("signal4", "DB80.DBD0:REAL[4]", 80, 0, 4);
		Machine m5 = new Machine("signal5", "DB80.DBD16:REAL[4]", 80, 16, 4);
		Machine m6 = new Machine("signal6", "DB60.DBD0:REAL[4]", 60, 0, 4);
		
		machines.add(m8);
		machines.add(m3);
		machines.add(m1);
		machines.add(m2);
		machines.add(m4);
		machines.add(m5);
		machines.add(m6);
		machines.add(m7);
		
		RequestOptimizer optimizer = new RequestOptimizer();
		optimizedList = optimizer.GetOptimizedRequestSet(machines);
		
		PrintOptimizedList(optimizedList);

	}

	private static void PrintOptimizedList(List<List<Machine>> optimizedList) {
		for(List<Machine> list : optimizedList) {
			for(Machine machine : list) {
				System.out.println(machine.GetAddress());
			}
			System.out.println("-------------");
		}
		
	}

}
