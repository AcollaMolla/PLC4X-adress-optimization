package com.uddeholm.s7;

import java.util.ArrayList;
import java.util.List;

public class S7Signals {
	
	private List<S7Signal> signals;
	private S7Signal firstSignal;
	private S7Signal lastSignal;
	private String address;
	
	public S7Signals() {
		this.signals = new ArrayList<S7Signal>();
	}
	
	public void AddSignals(List<S7Signal> signals) {
		for(S7Signal signal : signals) {
			this.AddSignal(signal);
		}
		this.address = this.SetAddress(this.GetFirstSignal());
	}
	
	public List<S7Signal> GetSignals(){
		return this.signals;
	}
	
	public S7Signal GetFirstSignal() {
		return this.firstSignal;
	}
	
	public S7Signal GetLastSignal() {
		return this.lastSignal;
	}
	
	public int GetAddressSize() {
		int size = -1;
		try {
			int first = this.firstSignal.GetOffset();
			int last = this.lastSignal.GetOffset() + this.lastSignal.GetSize();
			size = last - first;
		}catch(Exception e) {e.printStackTrace();}
		return size;
	}
	
	public String GetAddress() {
		return this.address;
	}
	
	private void AddSignal(S7Signal signal) {
		if(this.firstSignal == null || signal.GetOffset() < this.firstSignal.GetOffset()) {
			this.firstSignal = signal;
		}
		if(this.lastSignal == null || signal.GetOffset() > this.lastSignal.GetOffset()) {
			this.lastSignal = signal;
		}
		this.signals.add(signal);
	}
	
	private String SetAddress(S7Signal base) {
		switch(base.GetMemoryArea()) {
		case DB:
			return SetDbAddress(base);
		case I:
			return SetInputsAddress(base);
		case Q:
			return SetOutputsAddress(base);
		case M:
			return SetFlagsMarkersAddress(base);
		default:
			throw new UnsupportedOperationException("Unknown memory area");
		}
	}
	
	private String SetDbAddress(S7Signal base) {
		String address;
		address = base.GetMemoryAreaAsString() + base.GetDatablock() + "." + base.GetDataTypeShortCode(true) + base.GetOffset() + ":" + base.GetNativeDatatype(true) + "[" + this.GetAddressSize() + "]";
		return address;
	}
	
	private String SetInputsAddress(S7Signal base) {
		return SetSimpleStructureAddress(base);
	}
	
	private String SetOutputsAddress(S7Signal base) {
		return SetSimpleStructureAddress(base);
	}
	
	private String SetFlagsMarkersAddress(S7Signal base) {
		return SetSimpleStructureAddress(base);
	}
	
	private String SetSimpleStructureAddress(S7Signal base) {
		String address;
		address = base.GetMemoryAreaAsString() + base.GetOffset() + "." + base.GetBitoffset() + ":" + base.GetNativeDatatype(true) + "[" + this.GetAddressSize() + "]";
		return address;
	}

}
