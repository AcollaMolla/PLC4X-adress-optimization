package com.uddeholm.s7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class S7RequestOptimizer {
	
	public List<String> GetOptimizedS7SignalsList(List<List<S7Signal>> optimizedList){
		List<String> requests = new ArrayList<String>();
		for(List<S7Signal> signals : optimizedList) {
			requests.add(CreatePlc4xReadRequestItem(signals));
		}
		return requests;
	}
	
	public static SignalTree CreateOptimizedS7SignalList(List<S7Signal> signals) {
		return CreateOptimized(signals, 4);
	}

	public static SignalTree CreateOptimizedS7SignalList(List<S7Signal> signals, int signalOverhead) {
		int overhead = 4;
		if(signalOverhead > 0) {
			overhead = signalOverhead;
		}
		return CreateOptimized(signals, overhead);
	}
	
	private static SignalTree CreateOptimized(List<S7Signal> signals, int overhead) {
		SignalTree signalTree = new SignalTree();
		
		//If the OPC UA server failed we would receive an empty list here. Set the error flag
		if(signals == null || signals.size() == 0 || signals.isEmpty()) {
			signalTree.SetErrors();
		}
		
		List<List<S7Signal>> optimizedList;
		List<Integer> datablocks;
		List<S7MemoryAreas> memoryAreas;
		
		memoryAreas = GetMemoryAreas(signals);
		datablocks = GetDatablocks(signals);
		
		optimizedList = SortS7SignalsByMemoryArea(signals, memoryAreas);
		optimizedList = SortS7SignalsByDatablock(optimizedList, datablocks);
		optimizedList = SortS7SignalsByOffset(optimizedList, overhead);
		optimizedList = SortS7SignalsByIncreasingOffset(optimizedList);
		
		for(List<S7Signal> signalsInList: optimizedList) {
			S7Signals s = new S7Signals();
			s.AddSignals(signalsInList);
			signalTree.AddSignalsToTree(s);
		}
		return signalTree;
	}

	private String CreatePlc4xReadRequestItem(List<S7Signal> signals) {
		String request;
		String datatype = signals.get(0).GetNativeDatatype(false);
		int length = (signals.get(signals.size()-1).GetOffset()) - (signals.get(0).GetOffset());
		length = (length/signals.get(0).GetSize()) + 1;
		
		request = signals.get(0).GetMemoryAreaAsString() + String.valueOf(signals.get(0).GetDatablock()) + "." + signals.get(0).GetDataTypeShortCode(false) + String.valueOf(signals.get(0).GetOffset());
		request += ":" + datatype + "[" + length + "]";
		return "%" + request;
	}

	private static List<List<S7Signal>> SortS7SignalsByIncreasingOffset(List<List<S7Signal>> optimizedList) {
		for(List<S7Signal> signals : optimizedList) {
			Collections.sort(signals);
		}
		return optimizedList;
	}
	
	private static List<S7Datatypes> GetDatatypes(List<S7Signal> signals) {
		List<S7Datatypes> datatypes = new ArrayList<S7Datatypes>();
		for(S7Signal signal : signals) {
			if(!datatypes.contains(signal.GetNativeDatatype())) {
				datatypes.add(signal.GetNativeDatatype());
			}
		}
		return datatypes;
	}

	private static List<List<S7Signal>> SortS7SignalsByOffset(List<List<S7Signal>> optimizedList, int maxOverhead) {
		List<List<S7Signal>> list = new ArrayList<List<S7Signal>>();
		for(List<S7Signal> machines : optimizedList) {
			list.addAll(SplitS7SignalsByOffset(machines, maxOverhead));
		}
		return list;
	}

	private static List<List<S7Signal>> SplitS7SignalsByOffset(List<S7Signal> machines, int maxOverhead) {
		Collections.sort(machines,  (S7Signal s1, S7Signal s2) -> s1.compareTo(s2));
		List<List<S7Signal>> optimizedSubList = new ArrayList<List<S7Signal>>();
		List<S7Signal> list = new ArrayList<S7Signal>();
		
		for(int i=0;i<machines.size();i++) {
			if(i == 0) {
				list.add(machines.get(i));
			}
			else {
				if(Math.abs(machines.get(i).GetOffset() - GetLastItem(list).GetOffset()) <= maxOverhead) {
					list.add(machines.get(i));
				}
				else {
					optimizedSubList.add(new ArrayList<S7Signal>(list));
					list.clear();
					list.add(machines.get(i));
				}
			}
		}
		if(list != null && list.size() > 0) {
			optimizedSubList.add(list);
		}
		return optimizedSubList;
	}

	private static S7Signal GetLastItem(List<S7Signal> list) {
		S7Signal lastItem = list.get(list.size()-1);
		return lastItem;
	}
	
	private static List<List<S7Signal>> SortS7SignalsByMemoryArea(List<S7Signal> signals, List<S7MemoryAreas> memoryAreas){
		List<List<S7Signal>> optimizedList = new ArrayList<List<S7Signal>>();
		for(S7MemoryAreas memoryArea : memoryAreas) {
			List<S7Signal> list = new ArrayList<S7Signal>();
			for(S7Signal signal : signals) {
				if(signal.GetMemoryArea() == memoryArea) {
					list.add(signal);
				}
			}
			optimizedList.add(list);
		}
		return optimizedList;
	}

	private static List<List<S7Signal>> SortS7SignalsByDatablock(List<List<S7Signal>> optimizedList, List<Integer> datablocks) {
		List<List<S7Signal>> list = new ArrayList<List<S7Signal>>();
		
		for(List<S7Signal> signals : optimizedList) {
			list.addAll(SplitS7SignalsByDatablock(signals, datablocks));
		}
		/*for(int datablock : datablocks) {
			List<S7Signal> list = new ArrayList<S7Signal>();
			for(S7Signal machine : machines) {
				if(machine.GetDatablock() == datablock) {
					list.add(machine);
				}
			}
			optimizedList.add(list);
		}*/
		return list;
	}
	
	private static List<List<S7Signal>> SplitS7SignalsByDatablock(List<S7Signal> signals, List<Integer> datablocks){
		List<List<S7Signal>> optimizedSubList = new ArrayList<List<S7Signal>>();
		
		for(int datablock : datablocks) {
			List<S7Signal> list = new ArrayList<S7Signal>();
			for(S7Signal signal : signals) {
				if(signal.GetDatablock() == datablock) {
					list.add(signal);
				}
			}
			optimizedSubList.add(list);
		}
		
		return optimizedSubList;
	}
	
	private static List<S7MemoryAreas> GetMemoryAreas(List<S7Signal> signals){
		List<S7MemoryAreas> memoryAreas = new ArrayList<S7MemoryAreas>();
		for(S7Signal signal : signals) {
			if(!memoryAreas.contains(signal.GetMemoryArea())) {
				memoryAreas.add(signal.GetMemoryArea());
			}
		}
		return memoryAreas;
	}

	private static List<Integer> GetDatablocks(List<S7Signal> machines) {
		List<Integer> datablocks = new ArrayList<Integer>();
		for(S7Signal machine : machines) {
			if(!datablocks.contains(machine.GetDatablock())) {
				datablocks.add(machine.GetDatablock());
			}
		}
		return datablocks;
	}

}
