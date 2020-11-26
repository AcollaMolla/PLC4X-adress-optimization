package com.uddeholm.s7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class S7RequestOptimizer {
	
	public List<String> GetOptimizedS7SignalsList(List<List<S7Signal>> optimizedList){
		String request;
		List<String> requests = new ArrayList<String>();
		for(List<S7Signal> signals : optimizedList) {
			requests.add(CreatePlc4xReadRequestItem(signals));
		}
		return requests;
	}

	public List<List<S7Signal>> CreateOptimizedS7SignalList(List<S7Signal> signals, Integer signalOverhead) {
		List<List<S7Signal>> optimizedList;
		List<Integer> datablocks;
		List<S7Datatypes> datatypes;
		int overhead = signalOverhead != null ? signalOverhead : 4;
		
		datablocks = GetDatablocks(signals);
		datatypes = GetDatatypes(signals);
		
		optimizedList = SortS7SignalsByDatablock(datablocks, signals);
		optimizedList = SortS7SignalByDatatype(datatypes, optimizedList);
		optimizedList = SortS7SignalsByOffset(optimizedList, overhead);
		optimizedList = SortS7SignalsByIncreasingOffset(optimizedList);
		return optimizedList;
	}

	private String CreatePlc4xReadRequestItem(List<S7Signal> signals) {
		String request;
		int firstOffset = signals.get(0).GetOffset();
		int lastOffset = signals.get(signals.size()-1).GetOffset();
		String datatype = signals.get(0).GetStringDatatype();
		int length = (signals.get(signals.size()-1).GetOffset()) - (signals.get(0).GetOffset());
		length = (length/signals.get(0).GetSize()) + 1;
		
		request = signals.get(0).GetStringMemoryArea() + String.valueOf(signals.get(0).GetDatablock()) + "." + signals.get(0).GetDataTypeShortCode() + String.valueOf(signals.get(0).GetOffset());
		request += ":" + datatype + "[" + length + "]";
		return "%" + request;
	}

	private List<List<S7Signal>> SortS7SignalsByIncreasingOffset(List<List<S7Signal>> optimizedList) {
		for(List<S7Signal> signals : optimizedList) {
			Collections.sort(signals);
		}
		return optimizedList;
	}

	private List<List<S7Signal>> SortS7SignalByDatatype(List<S7Datatypes> datatypes, List<List<S7Signal>> optimizedList) {
		List<List<S7Signal>> list = new ArrayList<List<S7Signal>>();
		for(List<S7Signal> signals : optimizedList) {
			list.addAll(SplitListByDatatype(signals, datatypes));
		}
		return list;
	}

	private List<List<S7Signal>> SplitListByDatatype(List<S7Signal> signals, List<S7Datatypes> datatypes) {
		List<List<S7Signal>> list = new ArrayList<List<S7Signal>>();
		for(S7Datatypes datatype : datatypes) {
			List<S7Signal> similarDatatypes = new ArrayList<S7Signal>();
			for(S7Signal signal : signals) {
				if(signal.GetDatatype() == datatype) {
					similarDatatypes.add(signal);
				}
			}
			list.add(similarDatatypes);
		}
		return list;
	}

	private List<S7Datatypes> GetDatatypes(List<S7Signal> signals) {
		List<S7Datatypes> datatypes = new ArrayList<S7Datatypes>();
		for(S7Signal signal : signals) {
			if(!datatypes.contains(signal.GetDatatype())) {
				datatypes.add(signal.GetDatatype());
			}
		}
		return datatypes;
	}

	private List<List<S7Signal>> SortS7SignalsByOffset(List<List<S7Signal>> optimizedList, int maxOverhead) {
		List<List<S7Signal>> list = new ArrayList<List<S7Signal>>();
		for(List<S7Signal> machines : optimizedList) {
			list.addAll(SplitS7SignalsByOffset(machines, maxOverhead));
		}
		return list;
	}

	private List<List<S7Signal>> SplitS7SignalsByOffset(List<S7Signal> machines, int maxOverhead) {
		List<List<S7Signal>> optimizedSubList = new ArrayList<List<S7Signal>>();
		List<S7Signal> list = new ArrayList<S7Signal>();
		List<S7Signal> temp;
		
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

	private S7Signal GetLastItem(List<S7Signal> list) {
		S7Signal lastItem = list.get(list.size()-1);
		return lastItem;
	}

	private List<List<S7Signal>> SortS7SignalsByDatablock(List<Integer> datablocks, List<S7Signal> machines) {
		List<List<S7Signal>> optimizedList = new ArrayList<List<S7Signal>>();
		for(int datablock : datablocks) {
			List<S7Signal> list = new ArrayList<S7Signal>();
			for(S7Signal machine : machines) {
				if(machine.GetDatablock() == datablock) {
					list.add(machine);
				}
			}
			optimizedList.add(list);
		}
		return optimizedList;
	}

	private List<Integer> GetDatablocks(List<S7Signal> machines) {
		List<Integer> datablocks = new ArrayList<Integer>();
		for(S7Signal machine : machines) {
			if(!datablocks.contains(machine.GetDatablock())) {
				datablocks.add(machine.GetDatablock());
			}
		}
		return datablocks;
	}

}
