package prv.pgergely.cts.utils;

import java.util.EnumSet;

public enum SourceStates {
	
	ONLINE(true, true, false),
	OFFLINE(true, false, false),
	UPDATING(true, true, true),
	UNREGISTERED(false, false, false);
	
	private static final EnumSet<SourceStates> all = EnumSet.allOf(SourceStates.class);
	
	private boolean isEnabled; 
	private boolean isActive;
	private boolean isUpdate;
	
	private SourceStates(boolean isEnabled, boolean isActive, boolean isUpdate) {
		this.isEnabled = isEnabled;
		this.isActive = isActive;
		this.isUpdate = isUpdate;
	}
	
	public static SourceStates getByBooleans(boolean isEnabled, boolean isActive, boolean isUpdate){
		return all.stream().filter(p -> p.isActive == isActive && p.isEnabled == isEnabled && p.isUpdate == isUpdate).findFirst().get();
	}
	
	public static SourceStates getByName(String name) {
		return all.stream().filter(p -> p.name().toLowerCase().equals(name.toLowerCase())).findFirst().get();
	}
}
