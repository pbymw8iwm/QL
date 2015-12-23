package com.math.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.web.HttpUtil;
import com.ql.action.QLBaseAction;
import com.ql.common.HttpJsonUtil;
import com.ql.common.JsonUtil;


public class MathAction extends QLBaseAction {

	private static final Log log = LogFactory.getLog(MathAction.class);
	
	private static Properties pps = new Properties();
	private static ArrayList[] ErrorSudoku = new ArrayList[3];
	
	static{
		try {
			InputStream in = MathAction.class.getClassLoader().getResourceAsStream("sudoku");
			pps.load(in);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}  
	}
	
	public void getSudoku(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String level = HttpUtil.getAsString(request, "level");
			int iLevel = Integer.parseInt(level) - 1;
			int count = Integer.parseInt(pps.getProperty("L"+level));
			if(ErrorSudoku[iLevel] != null && ErrorSudoku[iLevel].size() == count){
				log.error("级别"+level+"的题目全错了!");
				HttpJsonUtil.showError(response, "题目获取有误!");
				return;
			}
			int[] sdk = new int[81];
			int[] result = new int[81];
			boolean isCheck = false;
			while(isCheck == false){
				Random random = new Random();
				int index = random.nextInt(count);
				if(ErrorSudoku[iLevel] != null && ErrorSudoku[iLevel].contains(index))
					continue;
				
				String q = pps.getProperty("L"+level+"_Q_"+index);
				String a = pps.getProperty("L"+level+"_A_"+index);
				
				StringTokenizer toKenizer = new StringTokenizer(q, ",");
				int i = 0;        
				while (toKenizer.hasMoreElements()) {           
					sdk[i++] = Integer.valueOf(toKenizer.nextToken());        
				}
				
				toKenizer = new StringTokenizer(a, ",");
				i = 0;        
				while (toKenizer.hasMoreElements()) {           
					result[i++] = Integer.valueOf(toKenizer.nextToken());        
				}
				
				//检查是否正确，以防止不小心修改了文件
				isCheck = Sudoku.check(sdk, result);
				if(isCheck == false){
					log.error("sudoku"+level+":"+index+"有误");
					
					if(ErrorSudoku[iLevel] == null)
						ErrorSudoku[iLevel] = new ArrayList();
					ErrorSudoku[iLevel].add(index);
					if(ErrorSudoku[iLevel] != null && ErrorSudoku[iLevel].size() == count){
						log.error("级别"+level+"的题目全错了!");
						HttpJsonUtil.showError(response, "题目获取有误!");
						return;
					}
				}
			}
			
	        HttpJsonUtil.showInfo(response,JsonUtil.getJsonFromList(new Object[]{sdk,result}));
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
}
