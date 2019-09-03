package com.nobbysoft.first.common.servicei;

import java.util.List;

import com.nobbysoft.first.common.entities.staticdto.CharacterClass;
import com.nobbysoft.first.common.utils.CodedListItem;

public interface CharacterClassService extends DataServiceI<CharacterClass,String>{

	public List<CodedListItem<String>>  getSpellClassesAsCodedList();
	
}
