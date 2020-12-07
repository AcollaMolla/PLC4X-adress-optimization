package com.uddeholm.s7;

import java.util.ArrayList;
import java.util.List;

public class SignalTree {
	
	private List<S7Signals> signalTree;
	private int size;
	
	public SignalTree() {
		this.signalTree = new ArrayList<S7Signals>();
		this.size = 0;
	}
	
	public void AddSignalsToTree(S7Signals signals) {
		this.signalTree.add(signals);
		this.size++;
	}
	
	public List<S7Signals> GetSignalTree(){
		return this.signalTree;
	}
	
	public int GetSize() {
		return this.size;
	}
	
	public void PrintAll() {
		for(S7Signals signals : this.signalTree) {
			for(S7Signal signal : signals.GetSignals()) {
				System.out.println(signal.GetAddress());
			}
		}
	}
	
	public void Print() {
		for(S7Signals signals : this.signalTree) {
			System.out.println(signals.GetAddress());
		}
	}

}
