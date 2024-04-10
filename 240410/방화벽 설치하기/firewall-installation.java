import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M;
	static int[][] map;
	static boolean[][] v;
	
	static int size;
	

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());

		size=N*M;
		map=new int[N][M];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				int input=Integer.parseInt(st.nextToken());
				map[i][j]=input;
				if(input==1) size--;
			}
		}
		size-=3;
		
		back(0,0);
		
		bw.write(answer+"");
		bw.close();
	}
	
	static int answer=0;

	static int count=0;
	public static void back(int cnt, int idx) {
		if(cnt==3) {
			count++;
			bfs();
			return;
		}
		
		for(int i=idx;i<N*M;i++) {
			int r=i/M;
			int c=i%M;
			if(map[r][c]==0) {
				map[r][c]=1;
				back(cnt+1,i+1);
				map[r][c]=0;
			}
		}
	}
	
	public static void bfs() {
		int cnt=0;
		Queue<Point> q=new ArrayDeque();
		v=new boolean[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(map[i][j]==2) {
					q.offer(new Point(i,j));
					v[i][j]=true;
					cnt++;
				}
			}
		}
		
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<M&&map[nr][nc]==0&&!v[nr][nc]) {
					q.offer(new Point(nr,nc));
					v[nr][nc]=true;
					cnt++;
				}
			}
		}
		int noFire=size-cnt;
		answer=Math.max(noFire, answer);
	}
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};
	
	static class Point{
		int r,c;
		public Point(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
}