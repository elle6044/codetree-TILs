import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,K;
	static int[][] map;
	static int[][] pmap;

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		map=new int[N][N];
		st=new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			map[N-1][i]=Integer.parseInt(st.nextToken());
		}
		
		int answer=0;
		while(true) {
			if(isEnd()) break;
			
			addOne();
			rollDow();
			pressDow();
			foldDow();
			pressDow();

			answer++;
		}
		
		bw.write(answer+"");
		bw.close();
	}
	
	static void foldDow() {
		for(int j=0;j<N/2;j++) {
			map[N-2][N/2+j]=map[N-1][N/2-1-j];
			map[N-1][N/2-1-j]=0;
		}
		
		for(int i=0;i<2;i++) {
			for(int j=0;j<N/4;j++) {
				map[N-3-i][N/4*3+j]=map[N-2+i][N/4*3-1-j];
				map[N-2+i][N/4*3-1-j]=0;
			}
		}
	}
	
	static void pressDow() {
		pmap=new int[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j]>0) {
					for(int d=0;d<4;d++) {
						int nr=i+dr[d];
						int nc=j+dc[d];
						if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]>0) {
							if(map[i][j]>map[nr][nc]) {
								int dif=(map[i][j]-map[nr][nc])/5;
								pmap[i][j]-=dif;
								pmap[nr][nc]+=dif;
							}
						}
					}
				}
			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				map[i][j]+=pmap[i][j];
			}
		}
		
		int idx=0;
		for(int j=0;j<N;j++) {
			for(int i=N-1;i>=0;i--) {
				if(map[i][j]>0) {
					int temp=map[i][j];
					map[i][j]=0;
					map[N-1][idx++]=temp;
				}
			}
			
		}
	}
	
	static void rollDow() {
		int rh=1;
		int rw=1;
		int idx=0;
		while(true) {
			if(N-idx-rw<rh) break;
			int[][] temp=new int[rw][rh];
			for(int i=N-rh;i<N;i++) {
				for(int j=idx;j<idx+rw;j++) {
					temp[(j-idx)][rh-1-(i-(N-rh))]=map[i][j];
					map[i][j]=0;
				
				}
			}
			
			for(int i=0;i<rw;i++) {
				for(int j=0;j<rh;j++) {
					map[N-rw+i-1][idx+rw+j]=temp[i][j];
				}
			}
			
			idx+=rw;
			int t=rw;
			rw=rh;
			rh=t+1;
			
		}
	}
	
	static void addOne() {
		int min=Integer.MAX_VALUE;
		for(int i=0;i<N;i++) {
			min=Math.min(min, map[N-1][i]);
		}
		
		for(int i=0;i<N;i++) {
			if(map[N-1][i]==min) {
				map[N-1][i]++;
			}
		}
		
	}
	
	static boolean isEnd() {
		int min=Integer.MAX_VALUE;
		int max=Integer.MIN_VALUE;
		for(int i=0;i<N;i++) {
			min=Math.min(min, map[N-1][i]);
			max=Math.max(max, map[N-1][i]);
		}
		if(max-min<=K) return true;
		else return false;
	}
	
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};

}