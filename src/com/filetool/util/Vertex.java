package com.filetool.util;


public  class Vertex {
	
    public int id;
	public Vertex pre;
	public int color;
	public int distance;
	
	
	public Vertex(int id){
		this.id=id;
		this.pre=null;
		this.distance=10000;
	}
	
	public boolean equals(Vertex vertex){
		if(this==vertex)
			return true;
		else if(vertex!=null&&vertex.getClass()==Vertex.class){
			return this.id==vertex.id;
		}
		return false;
	}
	
	public int hashCode(){
		return (int)id;
	}
	
	

	
	 
	
	

}
