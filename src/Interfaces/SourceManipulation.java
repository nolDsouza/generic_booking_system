package Interfaces;

import NWC.model.Origin;

public interface SourceManipulation {

	Origin baseSystem = Origin.getInstance();
	StringBuilder key = new StringBuilder();
	
	abstract void setKey(String iD);
	abstract void setUser(boolean authorised);

}
