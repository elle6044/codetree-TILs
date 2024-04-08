import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,H,K;
	
	static int[][] rMap;
	static boolean[][] tMap;
	
	static ArrayList<Runner> runners=new ArrayList();
	
	static Police police;
	

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		H=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		rMap=new int[N][N];
		tMap=new boolean[N][N];
		
		for(int i=0;i<M;i++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			int d=Integer.parseInt(st.nextToken())==1?1:2;
			runners.add(new Runner(r,c,d));
		}
		
		for(int i=0;i<H;i++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			tMap[r][c]=true;
		}
		
		police=new Police(N/2,N/2,0);
		
		int answer=0;
		for(int k=1;k<=K;k++) {
			moveRunner();
			
			movePolice();

			answer+=k*catchRunner();
		}
		bw.write(answer+"");
		bw.close();
		
	}
	
	public static int catchRunner() {
		int cnt=0;
		int nr=police.r;
		int nc=police.c;
		for(int i=0;i<3;i++) {
			nr+=dr[police.d];
			nc+=dc[police.d];
			for(Runner r:runners) {
				if(nr==r.r&&nc==r.c&&!tMap[nr][nc]) {
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	public static void movePolice() {
		int nr=police.r+dr[police.d];
		int nc=police.c+dc[police.d];
		
		police.r=nr;
		police.c=nc;
		
		if((nr==0&&nc==0)||(nr==N/2&&nc==N/2)) {
			police.d=(police.d+2)%4;
			police.goOut=police.goOut==false;
		}
		else {
			int nd=police.goOut?(police.d+1)%4:(police.d+3)%4;

			for(int i=0;i<N/2;i++) {
				int r=i;
				int c=i+1;
				if(nr==r&&nc==c) {
					police.d=nd;
				}
			}
			for(int i=0;i<N/2;i++) {
				int r=i;
				int c=N-1-i;
				if(nr==r&&nc==c) {
					police.d=nd;
				}
			}
			
			for(int i=N/2+1;i<N;i++) {
				int r=i;
				int c=N-1-i;
				if(nr==r&&nc==c) {
					police.d=nd;
				}
			}
			
			for(int i=N/2+1;i<N;i++) {
				int r=i;
				int c=i;
				if(nr==r&&nc==c) {
					police.d=nd;
				}
			}
		}
	}
	
	public static void moveRunner() {
		for(Runner runner:runners) {
			int distance=Math.abs(police.r-runner.r)+Math.abs(police.c-runner.c);
			if(distance<=3) {
				int nr=runner.r+dr[runner.d];
				int nc=runner.c+dc[runner.d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N) {
					if(police.r!=nr||police.c!=nc) {
						runner.r=nr;
						runner.c=nc;
					}
				}
				else {
					
					runner.d=(runner.d+2)%4;
					nr=runner.r+dr[runner.d];
					nc=runner.c+dc[runner.d];
					if(police.r!=nr||police.c!=nc) {
						runner.r=nr;
						runner.c=nc;
					}
				}
			}
		}
	}
	
	static int[] dr= {-1,0,1,0};
	static int[] dc= {0,1,0,-1};
	
	static class Runner{
		int r,c,d;
		public Runner(int r, int c, int d) {
			this.r=r;
			this.c=c;
			this.d=d;
		}
	}
	

	static class Police{
		int r,c,d;
		boolean goOut;
		public Police(int r, int c, int d) {
			this.r=r;
			this.c=c;
			this.d=d;
			this.goOut=true;
		}
	}
}