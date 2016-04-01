package com.ql.math.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

	private static Properties ppsRecord = new Properties();
	private static int[][] SudokuRecord = new int[3][];
	private static URI SudokuRecordFile = null;
	
	static{
		InputStream in = null;
		try {
			in = MathAction.class.getClassLoader().getResourceAsStream("sudoku");
			pps.load(in);
			
			SudokuRecordFile = MathAction.class.getClassLoader().getResource("record").toURI();
			in = MathAction.class.getClassLoader().getResourceAsStream("record");
			ppsRecord.load(in);
			
			loadRecord();
		} 
		catch (Exception e) {
			log.error(e.getMessage(),e);
		} 
		finally{
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
		}
	}
	
	private static void loadRecord(){
		for(int i=0;i<3;i++)
			_loadRecord(i);
	}
	
	private static void _loadRecord(int level){
		int count = Integer.parseInt(pps.getProperty("L"+(level+1)));
		SudokuRecord[level] = new int[count];
		for(int i=0;i<count;i++){
			String r = ppsRecord.getProperty("L"+(level+1)+"_R_"+i);
			if(StringUtils.isNumeric(r))
				SudokuRecord[level][i] = Integer.parseInt(r);			
		}
	}
	
	public void getSudoku(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String level = HttpUtil.getAsString(request, "level");
			if(StringUtils.isNumeric(level) == false)
				level = "1";
			int iLevel = Integer.parseInt(level) - 1;
			int count = Integer.parseInt(pps.getProperty("L"+level));
			if(ErrorSudoku[iLevel] != null && ErrorSudoku[iLevel].size() == count){
				log.error("级别"+level+"的题目全错了!");
				HttpJsonUtil.showError(response, "题目获取有误!");
				return;
			}
			int[] sdk = new int[81];
			int[] result = new int[81];
			int index = 0;
			boolean isCheck = false;
			while(isCheck == false){
				Random random = new Random();
				index = random.nextInt(count);
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
			
	        HttpJsonUtil.showInfo(response,JsonUtil.getJsonFromList(new Object[]{sdk,result,SudokuRecord[iLevel][index],index}));
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	public void setSudokuRecord(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			int level = HttpUtil.getAsInt(request, "level") - 1;
			int index = HttpUtil.getAsInt(request, "index");
			int record = HttpUtil.getAsInt(request, "record");
			if(SudokuRecord[level][index] == 0 || record < SudokuRecord[level][index]){
				SudokuRecord[level][index] = record;
				ppsRecord.setProperty("L"+(level+1)+"_R_"+index, String.valueOf(record));
				OutputStream fos = null; 
				try{
					fos = new FileOutputStream(new File(SudokuRecordFile));
					ppsRecord.store(fos, null);
				}
				finally{
					if(fos != null)
						fos.close();
				}
			}
		}
		catch(Exception ex){
		      log.error(ex.getMessage(),ex);
		      HttpJsonUtil.showError(response,ex.getMessage());
		    }
	}
	
	public void getSudokuRecord(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			
			File file = new File(SudokuRecordFile);
			// 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("record".getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
		}
		catch(Exception ex){
		      log.error(ex.getMessage(),ex);
		      HttpJsonUtil.showError(response,ex.getMessage());
		    }
	}
}
