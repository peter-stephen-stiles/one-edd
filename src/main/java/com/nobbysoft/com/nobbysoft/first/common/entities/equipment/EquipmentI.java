package com.nobbysoft.com.nobbysoft.first.common.entities.equipment;

public interface EquipmentI {
	
public EquipmentType getType();
public String getName();
public void setName(String name) ;
public EquipmentHands getRequiresHands();
public void setRequiresHands(EquipmentHands requiresHands) ;
public int getCapacityGP() ;
public void setCapacityGP(int capacityGP);
public int getEncumberanceGP() ;
public void setEncumberanceGP(int encumberanceGP);;

}
