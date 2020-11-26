package com.uddeholm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestOptimizer {
	
	public List<String> GetOptimizedSignalsList(List<List<Signal>> optimizedList){
		String request;
		List<String> requests = new ArrayList<String>();
		for(List<Signal> signals : optimizedList) {
			requests.add(CreatePlc4xReadRequestItem(signals));
		}
		return requests;
	}
	
	private String CreatePlc4xReadRequestItem(List<Signal> signals) {
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

	public List<List<Signal>> CreateOptimizedSignalList(List<Signal> signals, Integer signalOverhead) {
		List<List<Signal>> optimizedList;
		List<Integer> datablocks;
		List<S7Datatypes> datatypes;
		int overhead = signalOverhead != null ? signalOverhead : 4;
		
		datablocks = GetDatablocks(signals);
		datatypes = GetDatatypes(signals);
		
		optimizedList = SortSignalsByDatablock(datablocks, signals);
		optimizedList = SortSignalByDatatype(datatypes, optimizedList);
		optimizedList = SortSignalsByOffset(optimizedList, overhead);
		optimizedList = SortSignalsByIncreasingOffset(optimizedList);
		return optimizedList;
	}

	private List<List<Signal>> SortSignalsByIncreasingOffset(List<List<Signal>> optimizedList) {
		for(List<Signal> signals : optimizedList) {
			Collections.sort(signals);
		}
		return optimizedList;
	}

	private List<List<Signal>> SortSignalByDatatype(List<S7Datatypes> datatypes, List<List<Signal>> optimizedList) {
		List<List<Signal>> list = new ArrayList<List<Signal>>();
		for(List<Signal> signals : optimizedList) {
			list.addAll(SplitListByDatatype(signals, datatypes));
		}
		return list;
	}

	private List<List<Signal>> SplitListByDatatype(List<Signal> signals, List<S7Datatypes> datatypes) {
		List<List<Signal>> list = new ArrayList<List<Signal>>();
		for(S7Datatypes datatype : datatypes) {
			List<Signal> similarDatatypes = new ArrayList<Signal>();
			for(Signal signal : signals) {
				if(signal.GetDatatype() == datatype) {
					similarDatatypes.add(signal);
				}
			}
			list.add(similarDatatypes);
		}
		return list;
	}

	private List<S7Datatypes> GetDatatypes(List<Signal> signals) {
		List<S7Datatypes> datatypes = new ArrayList<S7Datatypes>();
		for(Signal signal : signals) {
			if(!datatypes.contains(signal.GetDatatype())) {
				datatypes.add(signal.GetDatatype());
			}
		}
		return datatypes;
	}

	private List<List<Signal>> SortSignalsByOffset(List<List<Signal>> optimizedList, int maxOverhead) {
		List<List<Signal>> list = new ArrayList<List<Signal>>();
		for(List<Signal> machines : optimizedList) {
			list.addAll(SplitSignalsByOffset(machines, maxOverhead));
		}
		return list;
	}

	private List<List<Signal>> SplitSignalsByOffset(List<Signal> machines, int maxOverhead) {
		List<List<Signal>> optimizedSubList = new ArrayList<List<Signal>>();
		List<Signal> list = new ArrayList<Signal>();
		List<Signal> temp;
		
		for(int i=0;i<machines.size();i++) {
			if(i == 0) {
				list.add(machines.get(i));
			}
			else {
				if(Math.abs(machines.get(i).GetOffset() - GetLastItem(list).GetOffset()) <= maxOverhead) {
					list.add(machines.get(i));
				}
				else {
					optimizedSubList.add(new ArrayList<Signal>(list));
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

	private Signal GetLastItem(List<Signal> list) {
		Signal lastItem = list.get(list.size()-1);
		return lastItem;
	}

	private List<List<Signal>> SortSignalsByDatablock(List<Integer> datablocks, List<Signal> machines) {
		List<List<Signal>> optimizedList = new ArrayList<List<Signal>>();
		for(int datablock : datablocks) {
			List<Signal> list = new ArrayList<Signal>();
			for(Signal machine : machines) {
				if(machine.GetDatablock() == datablock) {
					list.add(machine);
				}
			}
			optimizedList.add(list);
		}
		return optimizedList;
	}

	private List<Integer> GetDatablocks(List<Signal> machines) {
		List<Integer> datablocks = new ArrayList<Integer>();
		for(Signal machine : machines) {
			if(!datablocks.contains(machine.GetDatablock())) {
				datablocks.add(machine.GetDatablock());
			}
		}
		return datablocks;
	}

}
