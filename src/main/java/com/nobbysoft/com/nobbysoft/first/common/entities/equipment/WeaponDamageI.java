package com.nobbysoft.com.nobbysoft.first.common.entities.equipment;

import com.nobbysoft.com.nobbysoft.first.common.utils.DICE;
import com.nobbysoft.com.nobbysoft.first.common.views.DicePanelData;

public interface WeaponDamageI {
	
	public int getDamageSMDiceCount() ;
	public void setDamageSMDiceCount(int damageSMDiceCount) ;
	public DICE getDamageSMDICE() ;
	public void setDamageSMDICE(DICE damageSMDICE);
	public int getDamageLMod();
	public void setDamageLMod(int damageLMod);
	public DicePanelData getDamageL();
	public void setDamageL(DicePanelData data);
		
	public int getDamageLDiceCount();
	public void setDamageLDiceCount(int damageLDiceCount) ;
	public DICE getDamageLDICE();
	public void setDamageLDICE(DICE damageLDICE);
	public int getDamageSMMod() ;
	public void setDamageSMMod(int damageSMMod) ;
	public DicePanelData getDamageSM();
	public void setDamageSM(DicePanelData data);
	
	public String getSMDamage();
	public String getLDamage();
	
	
	



	
}
