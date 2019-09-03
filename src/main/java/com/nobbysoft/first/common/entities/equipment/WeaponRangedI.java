package com.nobbysoft.first.common.entities.equipment;

import java.math.BigDecimal;

public interface WeaponRangedI {

	public BigDecimal getFireRate();
	public BigDecimal getRangeS();
	public BigDecimal getRangeM();
	public BigDecimal getRangeL();
	
	public void setFireRate(BigDecimal fireRate);
	public void setRangeS(BigDecimal range);
	public void setRangeM(BigDecimal range);
	public void setRangeL(BigDecimal range);
	
}
