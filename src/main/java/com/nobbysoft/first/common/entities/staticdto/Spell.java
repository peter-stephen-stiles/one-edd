package com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.first.common.entities.DataDTOInterface;
import com.nobbysoft.first.common.utils.SpellTypeTranslator;

@SuppressWarnings("serial")
public class Spell implements Serializable, DataDTOInterface<String> {
	public Spell() {

	}

	public Object[] getAsRow(){
		return new Object[]{this,spellId,spellClass,level,name,verbal?"V":"",somatic?"S":"",material?"M":""};
	}
	private static final	String[] h = new String[]{"Id","Spell Class","Level","Name","V","S","M"};
	public String[] getRowHeadings(){
		return h;
	}

	public String[] getColumnCodedListTypes() {
		return new String[] {null,Constants.CLI_CLASS};
	}
	private static final Integer[] w =new Integer[]{100,120,50,200,20,20,20};
	public Integer[] getColumnWidths(){
		return w;
	}
	private String spellId;
	private String spellClass;
	private int level;
	private String name;
	private boolean verbal;
	private boolean somatic;
	private boolean material;
	private String materialComponents;
	private String description;

	private String range;
	private String castingTime;
	private String duration;
	private String savingThrow;
	private String areaOfEffect; 
	
	private boolean typeAbjuration;
	private boolean typeAlteration;
	private boolean typeCharm;
	private boolean typeConjuration;
	private boolean typeDivination;
	private boolean typeEnchantment;
	private boolean typeEvocation;
	private boolean typeIllusion;
	private boolean typeInvocation;
	private boolean typeNecromantic;
	private boolean typePhantasm;
	private boolean typePossession;
	private boolean typeSummoning;

	
	private Spell basedUponSpell = null;

	public String getSpellClass() {
		return spellClass;
	}

	public void setSpellClass(String spellClass) {
		this.spellClass = spellClass;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVerbal() {
		return verbal;
	}

	public void setVerbal(boolean verbal) {
		this.verbal = verbal;
	}

	public boolean isSomatic() {
		return somatic;
	}

	public void setSomatic(boolean somatic) {
		this.somatic = somatic;
	}

	public boolean isMaterial() {
		return material;
	}

	public void setMaterial(boolean material) {
		this.material = material;
	}

	public String getMaterialComponents() {
		return materialComponents;
	}

	public void setMaterialComponents(String materialComponents) {
		this.materialComponents = materialComponents;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public Spell getBasedUponSpell() {
		return basedUponSpell;
	}

	public void setBasedUponSpell(Spell basedUponSpell) {
		this.basedUponSpell = basedUponSpell;
	}

	@Override
	public String getKey() {
		return spellId;
	}

	public String getSpellId() {
		return spellId;
	}

	public void setSpellId(String spellId) {
		this.spellId = spellId;
	}

	@Override
	public String toString() {
		return "Spell [spellId=" + spellId + ", spellClass=" + spellClass + ", level=" + level + ", name=" + name
				+ ", verbal=" + verbal + ", somatic=" + somatic + ", material=" + material + ", materialComponents="
				+ materialComponents + ", description=" + description + ", range=" + range + ", castingTime="
				+ castingTime + ", duration=" + duration + ", savingThrow=" + savingThrow + ", areaOfEffect="
				+ areaOfEffect + ", basedUponSpell=" + basedUponSpell + ", spellType="+getSpellType()+"]";
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getCastingTime() {
		return castingTime;
	}

	public void setCastingTime(String castingTime) {
		this.castingTime = castingTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSavingThrow() {
		return savingThrow;
	}

	public void setSavingThrow(String savingThrow) {
		this.savingThrow = savingThrow;
	}

	public String getAreaOfEffect() {
		return areaOfEffect;
	}

	public void setAreaOfEffect(String areaOfEffect) {
		this.areaOfEffect = areaOfEffect;
	}

	public String getSpellType() {
		return SpellTypeTranslator.fromSpellToString(this);
	}

	public void setSpellType(String type) {
		SpellTypeTranslator.fromStringToSpell(type, this);
	}

	public boolean isTypeAbjuration() {
		return typeAbjuration;
	}

	public void setTypeAbjuration(boolean typeAbjuration) {
		this.typeAbjuration = typeAbjuration;
	}

	public boolean isTypeAlteration() {
		return typeAlteration;
	}

	public void setTypeAlteration(boolean typeAlteration) {
		this.typeAlteration = typeAlteration;
	}

	public boolean isTypeCharm() {
		return typeCharm;
	}

	public void setTypeCharm(boolean typeCharm) {
		this.typeCharm = typeCharm;
	}

	public boolean isTypeConjuration() {
		return typeConjuration;
	}

	public void setTypeConjuration(boolean typeConjuration) {
		this.typeConjuration = typeConjuration;
	}

	public boolean isTypeDivination() {
		return typeDivination;
	}

	public void setTypeDivination(boolean typeDivination) {
		this.typeDivination = typeDivination;
	}

	public boolean isTypeEnchantment() {
		return typeEnchantment;
	}

	public void setTypeEnchantment(boolean typeEnchantment) {
		this.typeEnchantment = typeEnchantment;
	}

	public boolean isTypeEvocation() {
		return typeEvocation;
	}

	public void setTypeEvocation(boolean typeEvocation) {
		this.typeEvocation = typeEvocation;
	}

	public boolean isTypeIllusion() {
		return typeIllusion;
	}

	public void setTypeIllusion(boolean typeIllusion) {
		this.typeIllusion = typeIllusion;
	}

	public boolean isTypeInvocation() {
		return typeInvocation;
	}

	public void setTypeInvocation(boolean typeInvocation) {
		this.typeInvocation = typeInvocation;
	}

	public boolean isTypeNecromantic() {
		return typeNecromantic;
	}

	public void setTypeNecromantic(boolean typeNecromantic) {
		this.typeNecromantic = typeNecromantic;
	}

	public boolean isTypePhantasm() {
		return typePhantasm;
	}

	public void setTypePhantasm(boolean typePhantasm) {
		this.typePhantasm = typePhantasm;
	}

	public boolean isTypePossession() {
		return typePossession;
	}

	public void setTypePossession(boolean typePossession) {
		this.typePossession = typePossession;
	}

	public boolean isTypeSummoning() {
		return typeSummoning;
	}

	public void setTypeSummoning(boolean typeSummoning) {
		this.typeSummoning = typeSummoning;
	}

	public String getComponents() {
		String c = "";
		if(verbal) {
			c=c+"V";
		}
		if(somatic) {
			c=c+"S";
		}
		if(material) {
			c=c+"M";
		}
		return c;
	}
	
	
}
