package com.filetool.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Graph {
    
	 public Map<Integer,Edge> edges=new HashMap<Integer, Edge>();
     public Map<Integer, Vertex> vertexs=new HashMap<Integer, Vertex>();
     private int vertexNum=0;
     private int edgeNum=0;
     
    
     //构造函数
     public Graph(int[][] graphData){
    	 /*
    	  * 1.从二维矩阵中读取图的数据
    	  */
    	 for(int i=0;i<graphData.length;i++){
    	    /*
    	     * 2.初始化顶点数据
    	     */
    	 
    	
    		 if(!vertexs.containsKey(graphData[i][1])){
    			vertexs.put(graphData[i][1],new Vertex(graphData[i][1]));
    			vertexNum++;
    		 }
    		 if(!vertexs.containsKey(graphData[i][2])){
    			 vertexs.put(graphData[i][2],new Vertex(graphData[i][2]));
     			 vertexNum++;
    		 }
    	   /*
    	    *3.初始化边集 
    	    */
    		 edgeNum++;
    	     Edge newedge=new Edge(graphData[i][0],graphData[i][1],graphData[i][2],graphData[i][3]);
             if(edges.containsKey(graphData[i][1])){
        	      Edge edge=edges.get(graphData[i][1]);
        	      newedge.next=edge.next;
        	      edge.next=newedge;
        	 }else if(!edges.containsKey(graphData[i][1])){
        		     edges.put(graphData[i][1], newedge);
        	 }
    			
         }
     }
     //将以点表示的路径转换以边表示的路径
     public Boolean vertexIdToEdgeId(ArrayList<Integer> routeVertexIds,StringBuffer buffer){
    	System.out.println(" nihao ");
    	 int edgeId;
    	 for(int i=0;i<routeVertexIds.size()-1;i++){
    		 edgeId=-1;
    		 Edge edge=edges.get(routeVertexIds.get(i));
    		 while(edge!=null){
    			 if(edge.destId==routeVertexIds.get(i+1)){
    			     edgeId=edge.edgeId;
    			     break;
    			 }
    			 edge=edge.next;
    		 }
    		 System.out.println(edgeId);
    		 if(edgeId==-1)
    			 return false;
    		 buffer.append(edgeId);
    		 if(i<routeVertexIds.size()-2)
    		   buffer.append("|");
    		 
    	 }
    	 return true;
     }
     
     
    
     //返回顶点数
     public int getVertexNum(){
    	 return vertexNum;
     }
     
     
     //返回边数
     public int getEdgeNum(){
    	 return edgeNum;
     }
     
     
     //返回所有点
     public Set<Integer> getAllVertex(){
    	 return vertexs.keySet();
     }
     
     
     //返回所有边
     public void printAllEdge(){
    	Set<Entry<Integer,Edge>> set=edges.entrySet();
    	for(Entry<Integer,Edge> entry: set){
    		Edge edge=entry.getValue();
    		while(edge!=null){
    			System.out.println("边集："+"边的id"+ edge.edgeId+"源节点："+edge.sourceId+"目的节点："+edge.destId+"权重："+edge.cost);
    			edge=edge.next;
    		}
    		 
          }
     }
     

     
     
     //进行图搜索前的初始化操作
     public void initial(int singleSourceId){
    	 /*
    	  * 1.定义所有点的集合
    	  */
    	 Collection<Vertex> collection=vertexs.values();
    	 
    	 /*
    	  * 2.遍历所有点，进行初始化
    	  */
    	 for(Vertex vertex:collection){
    		 vertex.distance=100000;
    		 vertex.pre=null;
    		 vertex.color=0;
    		 if(vertex.id==singleSourceId)
    			 vertex.distance=0;
    		 }
    
     }
     

     
     //广度优先搜索
     public void BFS(int sourceId,int destId){
    	 /*
    	  * 1.初始化图
    	  */
    	initial(sourceId);
    	/*
    	 * 2.初始化队列，用于对图层次遍历，并将源顶点压入队列
    	 */
    	ArrayDeque<Vertex> queue=new ArrayDeque<Vertex>();
    	vertexs.get(sourceId).color=1;
    	queue.addLast(vertexs.get(sourceId));
    	/*
    	 * 3.从源节点开始，对图层次遍历，找出最短路径
    	 */
    	while(queue.size()!=0){ //队列非空
    		//取出队列首部的点
    		Vertex vertex=queue.pollFirst();
    		//获取该点就得领接链表
    	    Edge edge=edges.get(vertex.id);
    	        //遍历领接链表
    			while(edge!=null){
    				Vertex vertexNext=vertexs.get(edge.destId);
    				if(vertexNext.color==0){//如果该点未被访问过
    				   vertexNext.color=1;
    				   vertexNext.distance=vertex.distance+edge.cost;
    				   vertexNext.pre=vertex;
    				   queue.addLast(vertexNext);
    				}
    			    edge=edge.next;
    				}
    			vertex.color=2;
    	   
    	}
    	System.out.print(" 广度优先算法 ：");
    	printRoute(vertexs.get(destId));
    	System.out.println(" 权重为 ："+vertexs.get(destId).distance);
    	
    	 
     }
     
     //前序遍历
     public void preOrder(Vertex vertex){
    	 Edge edge=edges.get(vertex.id);
    	 while(edge!=null){
    		 Vertex nextVertex=vertexs.get(edge.destId);
    		 if(nextVertex.color==0){
    		   nextVertex.pre=vertex;
    		   nextVertex.color=1;
    		   nextVertex.distance=vertex.distance+edge.cost;
    		   preOrder(nextVertex);
    		 }
    		 edge=edge.next;
    	 }
    	 
     }
     //深度优先算法
     public void DFS(int sourceId,int destId,ArrayList<Integer> routeId){
    	 /*
    	  * 1.初始化图
    	  */
    	 initial(sourceId);
    	 /*
    	  * 2.对图进行前序遍历
    	  */
    	 Vertex vertex=vertexs.get(sourceId);
    	 preOrder(vertex);
    	 System.out.print("深度优先算法 ：");
    	 printRoute(vertexs.get(destId));
    	 System.out.print(" 权重为 ："+vertexs.get(destId).distance);
    	 
     }
     
     
     
     //松弛算法
     public void relax(int sourceId,int destId,int cost){
    	 Vertex source=vertexs.get(sourceId);
    	 Vertex dest=vertexs.get(destId);
        if(source.distance+cost<dest.distance){
        	dest.distance=source.distance+cost;
        	dest.pre=source;
        }
    
     }

     
     
     
     //Bellman-ford 算法，计算带权最短路径
     public void bellman(int singleSourceId,int endId){
    	 /*
    	  * 1.初始化图的点集
    	  */
    	initial(singleSourceId);
    	
    	/*
    	 * 2.对图的没条边进行|V|-1次的松弛算法
    	 */
    	Collection<Edge> collection=edges.values();
    	for(int i=1;i<vertexs.size()-1;i++){
    		for(Edge edge:collection){
    			relax(edge.sourceId,edge.destId,edge.cost);
    			while(edge.next!=null){
    				edge=edge.next;
    				relax(edge.sourceId,edge.destId,edge.cost);
    			}
    		}
    		
    	}
    	
    	/*      用于检查是否有负权边
    	 *       for(Edge edge:collection){
    	            if(vertexs.get(edge.destId).distance>vertexs.get(edge.sourceId).distance+edge.cost)
    	            return false;
    	          }
    	 */
    	
    	System.out.print(" Bellman_Ford算法  :");
    	printRoute(vertexs.get(endId));
    	System.out.println(" 权重为 ："+vertexs.get(endId).distance);	
     
     }
     
     
     //Dijkstra算法,计算有向正权重图的最短路径
     public void DijSort(int sourceId,int destId){
    	 /*
    	  * 1.初始化图
    	  */
    	 initial(sourceId);
    	 /*
    	  * 2.定义初始化路径的集合， 定义ArrayList集合，并将点集压入
    	  */
    	 ArrayList<Integer> route=new ArrayList<Integer>();
    	 Collection<Vertex> collection=vertexs.values();
         List<Vertex> list=new ArrayList<Vertex>();
         list.addAll(collection);
    	 /*
    	  * 3.对每条边进行一次松弛算法
    	  */
         //点集非空
    	 while(list.size()!=0){
    		 //对点集根据自定义的排序规则排序
    	    Collections.sort(list,new Comparator<Vertex>(){//定义排序规则
    				@Override
    				public int compare(Vertex arg0, Vertex arg1) {
    					// TODO 自动生成的方法存根
    					if(arg0.distance>arg1.distance)
    					    return 1;
    					else if(arg0.distance<arg1.distance)
    						return -1;
    					else
    						return 0;
    				}
             });
    	    //获取点集队列首部的点
    	     Vertex nearVertex=list.get(0);
    		 Edge edge=edges.get(nearVertex.id);
             System.out.print(" "+nearVertex.id+" ");
             //将该点的id压入路径
    		 route.add(nearVertex.id);
    		 //松弛算法
    		 while(edge!=null){
    			 relax(edge.sourceId,edge.destId,edge.cost);
    			 edge=edge.next;
    		 }
    		 //删去队列首部的点
    		 list.remove(0);
    	 
    		
    	 }
    	 /*
    	  * 4.打印路径
    	  */
    	 System.out.print("Dij算法 ：");
    	 printRoute(vertexs.get(destId));
    	 System.out.println(" 权重为 ："+vertexs.get(destId).distance);
    }
     
     public void DijSort(int sourceId,ArrayList<Vertex> routeVertexs){
    	 /*
    	  * 1.初始化图
    	  */
    	 initial(sourceId);
    	 /*
    	  * 2.定义初始化路径的集合， 定义ArrayList集合，并将点集压入
    	  */
    	 ArrayList<Integer> route=new ArrayList<Integer>();
    	 Collection<Vertex> collection=vertexs.values();
         List<Vertex> list=new ArrayList<Vertex>();
         list.addAll(collection);
         list.removeAll(routeVertexs);
    	 /*
    	  * 3.对每条边进行一次松弛算法
    	  */
         //点集非空
    	 while(list.size()!=0){
    		 //对点集根据自定义的排序规则排序
    	    Collections.sort(list,new Comparator<Vertex>(){//定义排序规则
    				@Override
    				public int compare(Vertex arg0, Vertex arg1) {
    					// TODO 自动生成的方法存根
    					if(arg0.distance>arg1.distance)
    					    return 1;
    					else if(arg0.distance<arg1.distance)
    						return -1;
    					else
    						return 0;
    				}
             });
    	    //获取点集队列首部的点
    	     Vertex nearVertex=list.get(0);
    		 Edge edge=edges.get(nearVertex.id);
          
             //将该点的id压入路径
    		 route.add(nearVertex.id);
    		 //松弛算法
    		 while(edge!=null){
    			 relax(edge.sourceId,edge.destId,edge.cost);
    			 edge=edge.next;
    		 }
    		 //删去队列首部的点
    		 list.remove(0);
    	 
    		
    	 }
    	
    }
    
     //记录已确定的节点
    public void flagVertex(Vertex vertex,ArrayList<Integer> routeVertexIds,ArrayList<Vertex> routeVertexs){
    	
    	if(vertex.pre!=null){
    		flagVertex(vertex.pre,routeVertexIds,routeVertexs);
    		//routeVertexIds.add(vertex.id);
        	//routeVertexs.add(vertex);
    	}
    	routeVertexIds.add(vertex.id);
        routeVertexs.add(vertex);
    	
    }
    //finalsort最终算法
     public String finalSort(int sourceId,int destId,ArrayList<Integer> list){
    	
  	 
    	 ArrayList<Integer> routeVertexIds=new ArrayList<Integer>();
    	// routeVertexIds.add(sourceId);
    	 ArrayList<Vertex> routeVertexs=new ArrayList<Vertex>();
    	 //routeVertexs.add(vertexs.get(sourceId));
    	 int nearId;
   	     int routeDistance=0;
   	     int tempDistance=10000;
         
    	 while(list.size()!=0){
    	   
    	    DijSort(sourceId,routeVertexs);
    	    nearId=sourceId;
    	    tempDistance=10000;
    	    for(int id:list){
    		    /* int flag=0;
    		     Vertex vertex=vertexs.get(id);
    		     while(vertex!=null){
    			      if(routeVertexIds.contains(vertex.id))
    			      {          
    			    	  flag=1;
    				      break;
    			      }
    			      vertex=vertex.pre;
    		     }
    		     */
    		    
    		            int distance=vertexs.get(id).distance;
    	                if(distance<tempDistance){
    			              nearId=id;
    			              tempDistance=distance;
    			     
    		     }
    	    }
    	    if(tempDistance==100000)
    	    	return "NA";
    	    routeDistance+=tempDistance;
    	    flagVertex(vertexs.get(nearId).pre,routeVertexIds,routeVertexs);
    	    System.out.println(routeVertexIds);
    	    sourceId=nearId;
    	    list.remove((Integer)nearId);
    	 }
   
 	     DijSort(sourceId,routeVertexs);
 	     if(vertexs.get(destId).distance==100000)
 	    	 return"NA";
 	     else {
 	    	  flagVertex(vertexs.get(destId),routeVertexIds,routeVertexs);
 	    	  routeDistance+=vertexs.get(destId).distance;
 	    	  System.out.print(" finalSort :");
 	    	  System.out.print(routeVertexIds);
 	    	  System.out.println(" 权重为： "+routeDistance);
 	     }
 	     StringBuffer buffer=new StringBuffer();
 	     boolean result=vertexIdToEdgeId(routeVertexIds, buffer);
 	     if(result==false)
 	    	 return "NA";
 	     buffer.append(",");
 	     buffer.append(routeDistance);
 	     String routeResult=new String(buffer);
 	     System.out.println(routeResult);
 	    	 
    	 
    	 return routeResult;
    	 
     }
    	 
    
     
     
     
     
     
     //用递归的方法打印路径
     public void printRoute(Vertex vertex){
    	 if (vertex!=null)
    	 {	
    		 printRoute(vertex.pre);
    	     System.out.print(vertex.id+" ");
    	 }
    	 
    }
     
     
     
   

    	
    		 
 }
     

