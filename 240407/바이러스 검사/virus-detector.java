import java.io.*;
import java.util.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	public static void main(String[] args) throws Exception{
		int N=Integer.parseInt(br.readLine());
		st=new StringTokenizer(br.readLine());
		int[] map=new int[N];
		for(int i=0;i<N;i++) {
			map[i]=Integer.parseInt(st.nextToken());
		}
		st=new StringTokenizer(br.readLine());
		int a=Integer.parseInt(st.nextToken());
		int b=Integer.parseInt(st.nextToken());
		
		long answer=0;
		for(int i=0;i<N;i++) {
			int num=map[i];
			answer++;
			num-=a;
			if(num>0) {
				answer+=num%b==0?num/b:num/b+1;
			}
		}
		
		bw.write(answer+"");
		bw.close();
	}
}