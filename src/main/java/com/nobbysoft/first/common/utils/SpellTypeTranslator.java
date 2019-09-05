package com.nobbysoft.first.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.Spell;

public class SpellTypeTranslator {

	private SpellTypeTranslator() {
		 
	}

	public static String fromSpellToString(Spell spell) {
		List<String> list = new ArrayList<>();
		if(spell.isTypeAbjuration()) { list.add("Abjuration");}
		if(spell.isTypeAlteration()) { list.add("Alteration");}
		if(spell.isTypeCharm()) { list.add("Charm");}
		if(spell.isTypeConjuration()) { list.add("Conjuration");}
		if(spell.isTypeDivination()) { list.add("Divination");}
		if(spell.isTypeEnchantment()) { list.add("Enchantment");}
		if(spell.isTypeEvocation()) { list.add("Evocation");}
		if(spell.isTypeIllusion()) { list.add("Illusion");}
		if(spell.isTypeInvocation()) { list.add("Invocation");}
		if(spell.isTypeNecromantic()) { list.add("Necromantic");}
		if(spell.isTypePhantasm()) { list.add("Phantasm");}
		if(spell.isTypePossession()) { list.add("Possession");}
		if(spell.isTypeSummoning()) { list.add("Summoning");}

		
		StringBuilder sb = new StringBuilder();
		boolean first=true;
		for(String s:list) {
			if(!first) {
				sb.append("/");
			}
			sb.append(s);
			first=false;
		}
		return sb.toString();
	}
	
	public static void fromStringToSpell(String type, Spell spell) {
		setAll(spell,false);
		String t0=type.toUpperCase();
		if(t0.indexOf("ABJURATION")>=0) {
			spell.setTypeAbjuration(true);
		}
			
			
		if(t0.indexOf("ALTERATION")>=0) {
			spell.setTypeAlteration(true);
		}
		if(t0.indexOf("CHARM")>=0) {
			spell.setTypeCharm(true);
		}
		if(t0.indexOf("CONJURATION")>=0) {
			spell.setTypeConjuration(true);
		}
		if(t0.indexOf("DIVINATION")>=0) {
			spell.setTypeDivination(true);
		}
		if(t0.indexOf("ENCHANTMENT")>=0) {
			spell.setTypeEnchantment(true);
		}
		if(t0.indexOf("EVOCATION")>=0) {
			spell.setTypeEvocation(true);
		}
		if(t0.indexOf("ILLUSION")>=0) {
			spell.setTypeIllusion(true);
		}
		if(t0.indexOf("INVOCATION")>=0) {
			spell.setTypeInvocation(true);
		}
		if(t0.indexOf("NECROMANTIC")>=0) {
			spell.setTypeNecromantic(true);
		}
		if(t0.indexOf("PHANTASM")>=0) {
			spell.setTypePhantasm(true);
		}
		if(t0.indexOf("POSSESSION")>=0) {
			spell.setTypePossession(true);
		}
		if(t0.indexOf("SUMMONING")>=0) {
			spell.setTypeSummoning(true);
		}

			
	}


	private static void setAll(Spell spell,boolean value) {
		spell.setTypeAbjuration(value);
		spell.setTypeAlteration(value);
		spell.setTypeCharm(value);
		spell.setTypeConjuration(value);
		spell.setTypeDivination(value);
		spell.setTypeEnchantment(value);
		spell.setTypeEvocation(value);
		spell.setTypeIllusion(value);
		spell.setTypeInvocation(value);
		spell.setTypeNecromantic(value);
		spell.setTypePhantasm(value);
		spell.setTypePossession(value);
	}
}
