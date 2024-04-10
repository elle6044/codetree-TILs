import java.util.*;
import java.io.*;

public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N;
	
	static int[] dp;
	static int[] time;
	static int[] price;
	
	public static void main(String[] args) throws Exception {
		N=Integer.parseInt(br.readLine());
		
		dp=new int[N+2];
		time=new int[N+2];
		price=new int[N+2];
		
		for(int i=1;i<=N;i++) {
			st=new StringTokenizer(br.readLine());
			int t=Integer.parseInt(st.nextToken());
			int p=Integer.parseInt(st.nextToken());
			
			time[i]=t;
			price[i]=p;
		}
		
		for(int i=1;i<=N+1;i++) {
			int et=i+time[i];
			
			dp[i]=Math.max(dp[i], dp[i-1]);
			
			if(et<=N+1) {
				dp[et]=Math.max(dp[et], dp[i]+price[i]);
			}
		}
		
		
		bw.write(dp[N+1]+"");
		bw.close();
	}
}