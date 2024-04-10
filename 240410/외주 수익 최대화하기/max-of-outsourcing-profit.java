import java.util.*;
import java.io.*;

public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N;
	
	static int[] time;
	static int[] money;
	
	public static void main(String[] args) throws Exception {
		N=Integer.parseInt(br.readLine());
		
		time=new int[N];
		money=new int[N];
		
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			int t=Integer.parseInt(st.nextToken());
			int p=Integer.parseInt(st.nextToken());
			time[i]=t;
			money[i]=p;
		}
		
		back(0,0);
		
		bw.write(answer+"");
		bw.close();
	}

	static int answer=0;
	public static void back(int t, int sum) {
		if(t==N) {
			answer=Math.max(answer, sum);
			return;
		}
		
		if(t+time[t]<=N) {
			back(t+time[t],sum+money[t]);
		}
		back(t+1,sum);
	}
}