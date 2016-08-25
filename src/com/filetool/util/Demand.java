package com.filetool.util;

import java.util.ArrayList;

public class Demand {
	
	public int sourceId;
	public int destId;
	public ArrayList<Integer> routeIds;
	
	public Demand(int sourceId,int destId,ArrayList<Integer> route){
		this.sourceId=sourceId;
		this.destId=destId;
		this.routeIds=route;
	}
	

}
