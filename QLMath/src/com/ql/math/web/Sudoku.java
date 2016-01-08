package com.ql.math.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

public class Sudoku{
	
	//检查result是否是sdk的一组解
	public static boolean check(int[] sdk,int[] result){
		//先检查是否一致
		if(sdk.length != result.length)
			return false;
		for(int i=0;i<sdk.length;i++){
			if(sdk[i] != 0 && sdk[i] != result[i]){
				System.out.println("答案与题目不一致");
				return false;
			}
		}
		//检查是否是解
		if(checkRow(result)
			      && checkCol(result)
			      && checkBlock(result))
			return true;
		else
			return false;
	}
	
	private static boolean checkRow(int[] datas){
		  for(int row=0;row<9;row++){
			  int[] tmp = new int[]{0,0,0,0,0,0,0,0,0,0};
			  for(int i=0;i<9;i++){
			    tmp[datas[row*9+i]]++; 
			  }
			  for(int i=1;i<=9;i++){
			    if(tmp[i] != 1){
			      System.out.println("第"+(row+1)+"行有误！有"+tmp[i]+"个"+i);
			      return false;
			    }
			  }
		  }
		  return true;
		}

	private static boolean  checkCol(int[] datas){
		  for(int col=0;col<9;col++){
			  int[] tmp = new int[]{0,0,0,0,0,0,0,0,0,0};
			  for(int i=0;i<9;i++){
			    tmp[datas[i*9+col]]++; 
			  }
			  for(int i=1;i<=9;i++){
			    if(tmp[i] != 1){
			    	System.out.println("第"+(col+1)+"列有误！有"+tmp[i]+"个"+i);
			    	return false;
			    }
			  }
		  }
		  return true;
		}

	private static boolean  checkBlock(int[] datas){
		  //每个小宫格的左上角index
		  int[] points = new int[]{0,3,6,27,30,33,54,57,60};
		  //每个小宫格中小方块对应左上角index的增量
		  int[] steps =  new int[]{0,1,2,9,10,11,18,19,20};
		  for(int i=0;i<points.length;i++){
		    int[] tmp =  new int[]{0,0,0,0,0,0,0,0,0,0};
		    for(int j=0;j<steps.length;j++){
		      int index = points[i] + steps[j];
		      tmp[datas[index]]++;
		    }
			  for(int k=1;k<=9;k++){
			    if(tmp[k] != 1){
			    	System.out.println("第"+(k+1)+"个小九宫格有误！有"+tmp[k]+"个"+k);
			      return false;
			    }
			  }
		  }
		  return true;
	}
	
	public int[] generate(int level,int[] result)throws Exception{
		//构造完整的
		int[][] sdk = generate();
		int cc = 0;
		for (int i = 0; i < sdk.length; i++) {
			for (int j = 0; j < sdk[i].length; j++) {
				result[cc] = sdk[i][j];
				cc++;
			}
		}
//		System.out.println("first");
		int[] qsdk = new int[81];
		Slove sl = null;
		int resultCount = 0;
		int totalCount = 0;
		//挖洞
		do {
			// 更新数独终盘
			int ax, ay;
			double wnf;
			do {
				// 在数独内随机挖洞
				do {
					ax = (int) (Math.random() * 9);
					ay = (int) (Math.random() * 9);
				} while (sdk[ax][ay] == 0);
				sdk[ax][ay] = 0;
				wnf = _wnf(sdk);
			} while (whatLevel(wnf) != level);
			//求解
			cc = 0;
			for (int i = 0; i < sdk.length; i++) {
				for (int j = 0; j < sdk[i].length; j++) {
					qsdk[cc] = sdk[i][j];
					cc++;
				}
			}
			sl = new Slove(qsdk);            
			sl.resolve();            
			resultCount = sl.answers;
			if(resultCount != 1){
				totalCount++;
				if(totalCount > 5)
					throw new Exception("no");
			}
		} while (resultCount != 1);
		
//		resolve(result);
		return qsdk;
	}
	
	// 回溯法解数独 , 求一组解
	public static boolean resolve(int[] sdk) {
		int i, j;
		for (i = 0; i < 81; i++) {
			if (sdk[i] != 0) {
				continue;
			}
			HashMap<String, String> h = new HashMap<String, String>();
			for (j = 0; j < 81; j++) {
				h.put(j / 9 == i / 9 || j % 9 == i % 9 || (j / 27 == i / 27)
						&& ((j % 9 / 3) == (i % 9 / 3)) ? "" + sdk[j] : "0", "1");
			}
			for (j = 1; j <= 9; j++) {
				if (h.get("" + j) == null) {
					sdk[i] = j;
					if(resolve(sdk))
						return true;
				}
			}
			sdk[i] = 0;
			return false;
		}
		return true;
	}

	public int[][] generate(){
		int[][] n = new int[9][9];
		//随机生成第一行
		n[0] = getRandom(9);
		//从第二行开始
		if(genNum(n,1,0) == false)
			return generate();
		else
			return n;
	}
	
	private boolean genNum(int[][] n,int i,int j){
		for(int k=1;k<=9;k++){
			n[i][j] = k;
			if(isCorret(n,i,j)){
				if(j < 8){
					if(genNum(n,i,j+1))
						return true;
				}
				else{
					if(i<8){
						if(genNum(n,i+1,0))
							return true;
					}
					else
						return true;
				}
			}
			n[i][j] = 0;
		}
		return false;
	}
	
	//获取一个乱序列表
	private int[] getRandom(int no) {
        int[] sequence = new int[no];
        for(int i = 0; i < no; i++){
            sequence[i] = i+1;
        }
        Random random = new Random();
        for(int i = 0; i < no; i++){
            int p = random.nextInt(no);
            int tmp = sequence[i];
            sequence[i] = sequence[p];
            sequence[p] = tmp;
        }
        random = null;
/*        for(int i=0;i<no;i++){
        	System.out.print(sequence[i]);
        }
        System.out.println();*/
        return sequence;
    }

	/**
	 * 是否满足行、列和3X3区域不重复的要求
	 */
	private boolean isCorret(int[][] n, int row, int col) {
		return (checkRow(n,row) & checkLine(n,col) & checkNine(n,row, col));
	}

	/**
	 * 检查行是否符合要求
	 */
	private static boolean checkRow(int[][] n, int row) {
		for (int j = 0; j < 8; j++) {
			if (n[row][j] == 0) {
				continue;
			}
			for (int k = j + 1; k < 9; k++) {
				if (n[row][j] == n[row][k]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 检查列是否符合要求
	 */
	private static boolean checkLine(int[][] n, int col) {
		for (int j = 0; j < 8; j++) {
			if (n[j][col] == 0) {
				continue;
			}
			for (int k = j + 1; k < 9; k++) {
				if (n[j][col] == n[k][col]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 检查3X3区域是否符合要求
	 */
	private static boolean checkNine(int[][] n, int row, int col) {
		// 获得左上角的坐标
		int j = row / 3 * 3;
		int k = col / 3 * 3;
		// 循环比较
		for (int i = 0; i < 8; i++) {
			if (n[j + i / 3][k + i % 3] == 0) {
				continue;
			}
			for (int m = i + 1; m < 9; m++) {
				if (n[j + i / 3][k + i % 3] == n[j + m / 3][k + m % 3]) {
					return false;
				}
			}
		}
		return true;
	}

	// 根据WNF值求级别
	private static int whatLevel(double wnf) {
		if (wnf <= 0.19) {
			return 0;
		} else if (wnf <= 0.23) {// 0.19-0.23简单
			return 1;
		} else if (wnf <= 0.35) {// 0.25-0.35中等
			return 2;
		} else if (wnf <= 0.48) {// 0.35-0.48难
			return 3;
		} else {
			return 4;
		}
	}
	
	// 求wnf值（难易程度值）
	private static double _wnf(int[][] sz) {
		// 候选数在全部单元格中所占个数[1:2个 2:5个....]
		int C[] = new int[10];
		// 加权 (候选数)/单元格数
		double W[] = new double[10];
		// 空格数量
		int empty_sum = 0;
		// 找候选数
		for (int x = 0; x < sz.length; x++) {
			for (int y = 0; y < sz[x].length; y++) {
				if (sz[x][y] == 0) {// 空格
					empty_sum++;
					int startx, starty;
					int tem[] = new int[10];
					int a;
					// 取中单元格(3*3)区域
					if (x < 3) {
						startx = 0;
					} else if (x < 6) {
						startx = 3;
					} else {
						startx = 6;
					}
					if (y < 3) {
						starty = 0;
					} else if (y < 6) {
						starty = 3;
					} else {
						starty = 6;
					}
					for (int x2 = startx; x2 < startx + 3; x2++) {
						for (int y2 = starty; y2 < startx + 3; y2++) {
							a = sz[x2][y2];
							tem[a] = a;
						}
					}
					for (int x2 = 0; x2 < 9; x2++) {// 横向
						a = sz[x2][y];
						tem[a] = a;
					}
					for (int y2 = 0; y2 < 9; y2++) {// 纵向
						a = sz[x][y2];
						tem[a] = a;
					}
					// 最多9个候选数 最少0个候选数
					a = 0;
					for (int i = 1; i < tem.length; i++) {// 统计当前单元格的候选数
						if (tem[i] == 0) {
							a++;
						}
					}
					C[a]++;
				}
			}
		}
		int sum_ = 0;
		double avg = 0;
		for (int i = 1; i < C.length; i++) {
			sum_ += C[i];
			avg += (double) (C[i]) * i;
		}
		avg = avg / sum_;
		for (int i = 1; i < W.length; i++) {
			W[i] = i * avg;
		}
		double wnf = 0;
		for (int n = 1; n < W.length; n++) {
			wnf += (W[n] / W[9]) * C[n];
		}
		if(empty_sum == 0)
			return 0;
		wnf = wnf / empty_sum;
		return wnf;
	}
		
	public static void main2(String[] args)throws Exception {
		Sudoku sudoku = new Sudoku();
		int[] sdk = new int[]{1,0,0,0,0,0,0,0,0,0,0,2,3,0,0,0,7,4,0,0,0,2,0,0,0,8,5,6,0,0,8,7,0,0,5,0,0,0,0,9,0,6,0,0,0,0,9,0,0,2,4,0,0,3,5,8,0,0,0,2,0,0,0,9,4,0,0,0,7,8,0,0,0,0,0,0,0,0,0,0,6};
		Slove sl = sudoku.new Slove(sdk);
		sl.resolve();
		System.out.println(sl.answers);
		resolve(sdk);
		for(int s : sdk)
			System.out.print(s+",");
	}
	
	public static void main1(String[] args) {
		Sudoku sudoku = new Sudoku();
		int level = 1;
		{
//			System.out.println("L"+level);
			int index = 97;
			for(int t=index;index<100;t++){
				int[] result = new int[81];
				try {
					int[] qsdk = sudoku.generate(level,result);
					
					int j=0;
					System.out.print("L"+level+"_Q_"+index+"=");
					for(int i=0;i<qsdk.length;i++){
						if(i>0)
							System.out.print(",");
						System.out.print(qsdk[i]);
						j++;
						if(j == 9){
							j = 0;
						}
					}
					System.out.println();
					System.out.print("L"+level+"_A_"+index+"=");
					j=0;
					for(int i=0;i<result.length;i++){
						if(i>0)
							System.out.print(",");
						System.out.print(result[i]);
						j++;
						if(j == 9){
							j = 0;
						}
					}
					System.out.println();
					index++;
				} catch (Exception e) {
//					System.out.println("no:"+t);
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		InputStream in = MathAction.class.getClassLoader().getResourceAsStream("sudoku");
		Properties pps = new Properties();
		pps.load(in);
		int[] sdk = new int[81];
		int[] result = new int[81];
		
		for(int level = 1;level < 4;level++){
			int count = Integer.parseInt(pps.getProperty("L"+level));
			for(int index = 0;index<count;index++){
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
				if(Sudoku.check(sdk, result) == false)
					System.out.println(index);
			}
		}
	}
	
	public class Slove {
		public int answers = 0;
		private char[] te_ = null;
		public String sd_answer = "";

		public Slove(int[] sz) {
			this.te_ = new char[81];
			for (int i = 0; i < sz.length; i++) {
				te_[i] = (sz[i] + "").charAt(0);
			}
		}

		public void resolve() {
			resolve(te_);
		}

		public void resolve(char[] A) {
			int i, j;
			for (i = 0; i < 81; i++) {
				if (A[i] != '0') {
					continue;
				}
				HashMap<String, String> h = new HashMap<String, String>();
				for (j = 0; j < 81; j++) {
					h.put(j / 9 == i / 9 || j % 9 == i % 9
							|| (j / 27 == i / 27)
							&& ((j % 9 / 3) == (i % 9 / 3)) ? "" + A[j] : "0",
							"1");
				}
				for (j = 1; j <= 9; j++) {
					if (h.get("" + j) == null) {
						A[i] = (char) ('0' + j);
						resolve(A);
						if(answers > 1)
							return;
					}
				}
				A[i] = '0';
				return;
			}
			answers +=1;
			return;
		}
	}
}
