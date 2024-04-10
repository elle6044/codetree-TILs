import java.util.*;
import java.io.*;

public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N;
	
	static int[] dp;
	public static void main(String[] args) throws Exception {
		N=Integer.parseInt(br.readLine());
		dp=new int[N+2];
		
		for(int i=1;i<=N;i++) {
			st=new StringTokenizer(br.readLine());
			int t=Integer.parseInt(st.nextToken());
			int p=Integer.parseInt(st.nextToken());
			
			dp[i]=Math.max(dp[i], dp[i-1]);
			
			if(i+t<=N+1) {
				dp[i+t]=Math.max(dp[i+t], dp[i]+p);
			}
		}
		bw.write(dp[N+1]+"");
		bw.close();
	}

}