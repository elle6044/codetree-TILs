import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,K;
	static int[] map;
	static boolean[] v;
	static int idx=0;

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		map=new int[2*N];
		v=new boolean[2*N];
		st=new StringTokenizer(br.readLine());
		for(int i=0;i<2*N;i++) {
			map[i]=Integer.parseInt(st.nextToken());
		}
		
		int answer=1;
		while(true) {
			rotation();
			
			move();
			
			add();
			
			if(getCnt()>=K) break;
			answer++;
		}
		
		bw.write(answer+"");
		bw.close();
	}
	public static int getCnt() {
		int cnt=0;
		for(int i=0;i<2*N;i++) {
			if(map[i]==0) cnt++;
		}
		return cnt;
	}
	
	public static void add() {
		if(!v[idx]&&map[idx]>0) {
			v[idx]=true;
			map[idx]--;
		}
	}
	
	public static void move() {
		int idx2=(idx+N-1)%(2*N);
		
		int now=idx2;
		int next=idx2;
		for(int i=1;i<N;i++) {
			now--;
			if(now<0) now=2*N-1;
			
			if(v[now]&&!v[next]&&map[next]>0) {
				v[now]=false;
				map[next]--;
				if(next!=idx2) {
					v[next]=true;
				}
			}
			next=now;
		}
	}
	
	public static void rotation() {
		idx--;
		if(idx<0) idx=2*N-1;
		
		int idx2=(idx+N-1)%(2*N);
		
		if(v[idx2]) {
			v[idx2]=false;
		}
	}
}