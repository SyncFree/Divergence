package moodleLogic;

import java.util.HashSet;
import java.util.Set;

import peersim.core.dcdatastore.util.DataObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jordi
 */
public class Directory implements DataObject<String, Integer> {
    
    private String id;
    private int value;
    Set<String> Path; 

    public Directory(String id) {
        this.id = id;
        this.value = 0;
        this.Path = new HashSet<String>();
    }
    
    public Object clone(){
		Directory nd = null;
		try {
			nd = (Directory) super.clone();
			nd.id = this.id;
			nd.value = this.value;
			nd.Path = new HashSet<String>(this.Path);
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return nd;
    }
    
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public void incValue() {
        this.value+=1;
    }


	@Override
	public String getData() {
		return this.id;
	}


	@Override
	public void setData(String data) {
		this.id = data;
	}


	@Override
	public void setData(String data, Integer metadata) {
		this.id = data;
		this.value = metadata;
	}


	@Override
	public Integer getMetadata() {
		return this.value;
	}


	@Override
	public double computeDivergence(DataObject<?, ?> dataObject) {
		if(!(dataObject instanceof Directory) )
			return 0;
		else {
			Directory other = (Directory) dataObject;
			return Math.abs(this.value - other.value);
		}
	}
}
