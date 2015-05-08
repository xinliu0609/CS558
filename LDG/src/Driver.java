import java.util.*;

public class Driver {
	
	public static void main(String[] args){
		
		int[][] sample = {{1, -1, -1, -1, 0},{-1, 1, -1, 0, 0}, {-1, -1, 1, 0, 0}, {-1, 0, 0, 1, -1}, {0, 0, 0, -1, 1}};
		int[][] result = LDG.ldg(sample);
		
		for(int i = 0; i < result.length; i++){
			for(int j = 0; j < result[0].length; j++){
				System.out.print(result[i][j] + " ");
			}
			System.out.println();
		}
	}

}
