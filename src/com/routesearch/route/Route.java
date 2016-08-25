/**
 * 实现代码文件
 * 
 * @author XXX
 * @since 2016-3-4
 * @version V1.0
 */
package com.routesearch.route;


import com.filetool.util.Demand;
import com.filetool.util.Graph;
import com.filetool.util.LogUtil;
import com.filetool.util.TransUtil;

public final class Route
{
    /**
     * 你需要完成功能的入口
     * 
     * @author XXX
     * @since 2016-3-4
     * @version V1
     */
    public static String searchRoute(String graphContent, String condition)
    {   
    	String routeResult = null;
    	/*
         * 1. 将从文件中读出的字符串，转化为可用数据
         */
    
    	int[][] graphData=TransUtil.trans1(graphContent);
    	Demand demand=TransUtil.trans2(condition);
    	  LogUtil.printLog("End");
    	
    	/*
    	 * 2.图搜索
    	 */
    	Graph graph=new Graph(graphData);
    
        routeResult=graph.finalSort(demand.sourceId,demand.destId,demand.routeIds);
        System.out.println(routeResult);
    	
    	return routeResult;
    	
    }

}