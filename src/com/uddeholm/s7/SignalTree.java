package com.uddeholm.s7;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class SignalTree {
	
	private List<S7Signals> signalTree;
	private int size;
	private long crc32;
	
	public SignalTree() {
		this.signalTree = new ArrayList<S7Signals>();
		this.size = 0;
		this.crc32 = 0;
	}
	
	public void AddSignalsToTree(S7Signals signals) {
		this.signalTree.add(signals);
		this.size++;
		this.crc32 = SetCrc32();
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
	
	public long GetCrc32() {
		return this.crc32;
	}
	
	private long SetCrc32() {
		StringBuilder sb = new StringBuilder();
		Checksum checksum = new CRC32();
		byte[] bytes;
		long checksumvalue = 0;
		
		for(S7Signals signals : this.signalTree) {
			for(S7Signal signal : signals.GetSignals()) {
				sb.append(signal.GetName() + signal.GetAddress() + signal.GetOffset() + signal.GetBitoffset() + signal.GetMemoeyArea() + signal.GetNativeDatatype(false));
			}
		}
		
		bytes = sb.toString().getBytes();
		checksum.update(bytes, 0, bytes.length);
		checksumvalue = checksum.getValue();
		
		return checksumvalue;
	}

}
