package com.uddeholm;

import java.util.ArrayList;
import java.util.List;

public class RequestOptimizer {
	
	public List<List<Signal>> GetOptimizedRequestSet(List<Signal> signals) {
		List<List<Signal>> optimizedList;
		List<Integer> datablocks;
		List<Integer> datatypes;
		
		datablocks = GetDatablocks(signals);
		datatypes = GetDatatypes(signals);
		
		optimizedList = SortSignalsByDatablock(datablocks, signals);
		optimizedList = SortSignalByDatatype(datatypes, optimizedList);
		optimizedList = SortSignalsByOffset(optimizedList, 4);
		return optimizedList;
	}

	private List<List<Signal>> SortSignalByDatatype(List<Integer> datatypes, List<List<Signal>> optimizedList) {
		List<List<Signal>> list = new ArrayList<List<Signal>>();
		for(List<Signal> signals : optimizedList) {
			list.addAll(SplitListByDatatype(signals, datatypes));
		}
		return list;
	}

	private List<List<Signal>> SplitListByDatatype(List<Signal> signals, List<Integer> datatypes) {
		List<List<Signal>> list = new ArrayList<List<Signal>>();
		for(Integer datatype : datatypes) {
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

	private List<Integer> GetDatatypes(List<Signal> signals) {
		List<Integer> datatypes = new ArrayList<Integer>();
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
