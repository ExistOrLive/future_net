package com.filetool.util;

import java.util.ArrayList;
import java.util.Scanner;

public class TransUtil {
	
   //将图数据的字符串转化为二维数组
	public static int[][] trans1(String graphContent){
		/*
		 * 1.将字符串分割为每条边信息的字符串
		 */
		String[] graphText=graphContent.split("\n");
	
		/*
		 * 2.将字符串数组转化为二维整形数组
		 */
		
    	int[][] graphData=new int[graphText.length][4];
    	
    	for(int i=0;i<graphText.length;i++){
    		 
    	    
    		  @SuppressWarnings("resource")
			  Scanner con=new Scanner(graphText[i]);
    		  con.useDelimiter(",");
    		
    		
    		  for(int j=0;j<4;j++)
    		  {  

    		      graphData[i][j]=con.nextInt();
    		    
    		  }
    	
    	   
    	
    		
    	}
  
    
    	
    	/*
    	 * 3.返回二维整形数组
    	 */
		return graphData;
		
	}
	
	public static Demand trans2(String content){
       String[] contents=content.split(",");
        int sourceId=Integer.parseInt(contents[0]);
	    int destId=Integer.parseInt(contents[1]);
	   
		ArrayList<Integer> routeId=new ArrayList<Integer>();
        String string=contents[2];
        String[] str=string.split("\n");
        String[] ids=str[0].split("\\|");
         for(int i=0;i<ids.length;i++){
			routeId.add(Integer.parseInt(ids[i]));
		}
	
		Demand demand=new Demand(sourceId,destId,routeId);
		return demand;
	}

	
	
	
	
}
