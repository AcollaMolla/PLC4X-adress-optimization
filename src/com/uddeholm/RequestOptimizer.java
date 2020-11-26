package com.uddeholm;

import java.util.ArrayList;
import java.util.List;

public class RequestOptimizer {
	
	public List<List<Machine>> GetOptimizedRequestSet(List<Machine> machines) {
		List<List<Machine>> optimizedList;
		List<Integer> datablocks;
		
		datablocks = GetDatablocks(machines);
		optimizedList = SortSignalsByDatablock(datablocks, machines);
		optimizedList = SortSignalsByOffset(optimizedList, 4);
		return optimizedList;
	}

	private List<List<Machine>> SortSignalsByOffset(List<List<Machine>> optimizedList, int maxOverhead) {
		List<List<Machine>> list = new ArrayList<List<Machine>>();
		for(List<Machine> machines : optimizedList) {
			list.addAll(SplitSignalsByOffset(machines, maxOverhead));
		}
		return list;
	}

	private List<List<Machine>> SplitSignalsByOffset(List<Machine> machines, int maxOverhead) {
		List<List<Machine>> optimizedSubList = new ArrayList<List<Machine>>();
		List<Machine> list = new ArrayList<Machine>();
		List<Machine> temp;
		
		for(int i=0;i<machines.size();i++) {
			if(i == 0) {
				list.add(machines.get(i));
			}
			else {
				if(Math.abs(machines.get(i).GetOffset() - GetLastItem(list).GetOffset()) <= maxOverhead) {
					list.add(machines.get(i));
				}
				else {
					optimizedSubList.add(new ArrayList<Machine>(list));
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

	private Machine GetLastItem(List<Machine> list) {
		Machine lastItem = list.get(list.size()-1);
		return lastItem;
	}

	private List<List<Machine>> SortSignalsByDatablock(List<Integer> datablocks, List<Machine> machines) {
		List<List<Machine>> optimizedList = new ArrayList<List<Machine>>();
		for(int datablock : datablocks) {
			List<Machine> list = new ArrayList<Machine>();
			for(Machine machine : machines) {
				if(machine.GetDatablock() == datablock) {
					list.add(machine);
				}
			}
			optimizedList.add(list);
		}
		return optimizedList;
	}

	private List<Integer> GetDatablocks(List<Machine> machines) {
		List<Integer> datablocks = new ArrayList<Integer>();
		for(Machine machine : machines) {
			if(!datablocks.contains(machine.GetDatablock())) {
				datablocks.add(machine.GetDatablock());
			}
		}
		return datablocks;
	}

}
