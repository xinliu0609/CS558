import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LDG {
	
	
	//input: incomplete matrix, 0, 1, -1, -1 means 'star'
	//output: completed matrix, 0, 1
	
	public static int[][] ldg(int[][] matrix){
	
		int num_rows = matrix.length;
		int num_columns = matrix[0].length;
		
		//construct list of rows
		List<int[]> rowList = new ArrayList<int[]>();
		for(int i = 0; i < num_rows; i++){
			rowList.add(matrix[i]);
		}
		
		//calculate the min distance
		int minDistance = Integer.MAX_VALUE; 
		int row1 = 0;
		int row2 = 0;
		
		for(int i = 0; i < num_rows-1; i++)
			for(int j = i+1; j < num_rows; j++){
				int x = rowDistance(rowList.get(i), rowList.get(j));
				System.out.println("distance between "+i+" and "+j+" is "+ x);
				if(minDistance > x){
					minDistance = x;
					row1 = i;
					row2 = j;
				}
			}
		
		while(minDistance != Integer.MAX_VALUE){
			int[] newRow = merge(rowList.get(row1), rowList.get(row2));
			
			System.out.println("this time merge row "+row1+" and "+row2);
			
			rowList.add(newRow);
			rowList.remove(row1);
			rowList.remove(row2-1);	// this is because after deleting row1, 
									// original row2 is shifted one spot left

			System.out.println("after merge, the resulting matrix is ");
			for(int[] x : rowList){
				for(int m : x){
					System.out.print(m+" ");
				}
				System.out.println();
			}
			
			
			System.out.println("-------------------------------");
			
			minDistance = Integer.MAX_VALUE;
			
			//recalculate the min distance
			for(int i = 0; i < rowList.size()-1; i++)
				for(int j = i+1; j < rowList.size(); j++){
					int x = rowDistance(rowList.get(i), rowList.get(j));
					System.out.println("distance between "+i+" and "+j+" is "+ x);
					if(minDistance > x){
						minDistance = x;
						row1 = i;
						row2 = j;
					}
				}
		}
				
		//return back to matrix
		int[][] result = new int[rowList.size()][num_columns];
		for(int i = 0; i < rowList.size(); i++){
			int[] row = rowList.get(i);
			for(int j = 0; j < num_columns; j++){
				result[i][j] = row[j];
			}
		}
	
		return result;
	}
	
	
	// auxiliary functions
	
	//1. calculate entry distance
	public static int entryDistance(int x, int y){
		
		if(x == y){
			return 0;
		}
		else if(x == -1 || y == -1){
			return 1;
		}
		else{
			return 2;	// 2 stands for 'infinity'
		}
	}
	
	//2. calculate row distance
	public static int rowDistance(int[] r1, int[] r2){
		
		int sum = 0;
		
		for(int j = 0; j < r1.length; j++){
			
			int result = entryDistance(r1[j], r2[j]);              
			if(result == 2){
				return Integer.MAX_VALUE;
			}
			else{
				sum += result;
			}
		}
		return sum;
	}
	
	//3. merge two rows
	public static int[] merge(int[] r1, int[] r2){
		
		int[] result = new int[r1.length];
		for(int i = 0; i < r1.length; i++){
			if(r1[i] == r2[i]){
				result[i] = r1[i];
			}
			else if(r2[i] == -1){
				result[i] = r1[i];
			}
			else{
				result[i] = r2[i];
			}
		}
		return result;
	}
}
