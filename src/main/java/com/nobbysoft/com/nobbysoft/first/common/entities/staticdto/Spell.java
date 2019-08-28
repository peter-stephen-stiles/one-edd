package com.nobbysoft.com.nobbysoft.first.common.entities.staticdto;

import java.io.Serializable;

import com.nobbysoft.com.nobbysoft.first.common.constants.Constants;
import com.nobbysoft.com.nobbysoft.first.common.entities.DataDTOInterface;

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
				+ ", verbal=" + verbal + ", somatic=" + somatic + ", material=" + material + ", basedUponSpell="
				+ basedUponSpell + ", description=" + description + ", materialComponents=" + materialComponents + "]";
	}

}
