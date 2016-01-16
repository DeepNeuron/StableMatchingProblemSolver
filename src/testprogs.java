import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 


public class testprogs {
	
	public static void main (String[] args ) throws IOException  {
		ArrayList<Integer> x = new ArrayList<Integer>();
		for(int k=1; k<=15; k+=2){ x.add(k);}
		 
		for(int k=1; k<5; k++) x.remove(k);
		for(int k:x) System.out.print(k + "-");
	}
}