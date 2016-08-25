package com.filetool.util;

public class Edge {
      public int edgeId;
	  public int sourceId;
      public int destId;
      public int cost;
      public Edge next;
      
      //构造函数
      public Edge(int edgeId,int sourceId,int destId,int cost){
    	  this.edgeId=edgeId;
    	  this.sourceId=sourceId;
    	  this.destId=destId;
    	  this.cost=cost;
    	  next=null;
      }

      //计算hashcode
	  @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cost;
		result = prime * result + destId;
		result = prime * result + sourceId;
		return result;
	}
	  
	  //比较是否相等
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (cost != other.cost)
			return false;
		if (destId != other.destId)
			return false;
		if (sourceId != other.sourceId)
			return false;
		return true;
	}
      
}
