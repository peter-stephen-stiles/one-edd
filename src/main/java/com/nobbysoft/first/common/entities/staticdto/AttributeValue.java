package com.nobbysoft.first.common.entities.staticdto;
import java.io.Serializable;
import java.util.Optional;

public class AttributeValue  implements Serializable{
private int value;
private Optional<Integer> perc;
public AttributeValue(int value,Optional<Integer> perc){
	this.value=value;
	this.perc=perc;
}

public int getValue() {
	return value;
}

public void setValue(int value) {
	this.value = value;
}

public Optional<Integer> getPerc() {
	return perc;
}

public void setPerc(Optional<Integer> perc) {
	this.perc = perc;
}

public AttributeValue(int value){
	this.value=value;
	this.perc= Optional.empty();
}
}
