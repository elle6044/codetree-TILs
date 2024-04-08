import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,T;
	static int[][] map;
	
	static ArrayList<Integer> wind=new ArrayList<>();
	
	
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		T=Integer.parseInt(st.nextToken());
		
		map=new int[N][M];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				int input=Integer.parseInt(st.nextToken());
				map[i][j]=input;
				if(input==-1) {
					wind.add(i);
				}
			}
		}
		
		for(int t=0;t<T;t++) {
			spreadDust();
			
			cleanDust();
		}
		
		
		int answer=0;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				answer+=map[i][j]==-1?0:map[i][j];
			}
		}
		
		bw.write(answer+"");
		bw.close();
	}
	
	public static void cleanDust() {
		cleanTop();
		cleanDown();
	}
	public static void cleanTop() {
		int sr=wind.get(0);
		int sc=0;
		
		for(int i=sr-2;i>=0;i--) {
			map[i+1][0]=map[i][0];
		}
		
		int temp1=map[sr][M-1];
		for(int j=M-2;j>=1;j--) {
			map[sr][j+1]=map[sr][j];
		}
		map[sr][1]=0;
		
		int temp2=map[0][M-1];
		for(int i=0;i<sr-1;i++) {
			map[i][M-1]=map[i+1][M-1];
		}
		map[sr-1][M-1]=temp1;
		
		for(int j=0;j<M-2;j++) {
			map[0][j]=map[0][j+1];
		}
		map[0][M-2]=temp2;
		
	}
	public static void cleanDown() {
		int sr=wind.get(1);
		int sc=0;
		
		for(int i=sr+1;i<N-1;i++) {
			map[i][0]=map[i+1][0];
		}
		
		int temp1=map[0][N-1];
		for(int j=0;j<M-1;j++) {
			map[N-1][j]=map[N-1][j+1];
		}
		
		for(int i=N-1;i>sr;i--) {
			map[i][M-1]=map[i-1][M-1];
		}
		
		for(int j=M-1;j>1;j--) {
			map[sr][j]=map[sr][j-1];
		}
		map[sr][1]=0;
	}
	
	public static void spreadDust() {
		Queue<Dust> q=new ArrayDeque<>();
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(map[i][j]!=-1) {
					q.offer(new Dust(i,j,map[i][j]));
				}
			}
		}
		
		while(!q.isEmpty()) {
			Dust dust=q.poll();
			int size=dust.size/5;
			for(int d=0;d<4;d++) {
				int nr=dust.r+dr[d];
				int nc=dust.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<M&&map[nr][nc]!=-1) {
					map[nr][nc]+=size;
					map[dust.r][dust.c]-=size;
				}
			}
		}
	}
	
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};
	static class Dust{
		int r,c,size;
		public Dust(int r, int c, int size) {
			this.r=r;
			this.c=c;
			this.size=size;
		}
	}

}