import java.util.*;
import java.io.*;

public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M;
	static int[][] map;
	
	static int[] dr= {0,1,0,-1};
	static int[] dc= {1,0,-1,0};
	
	static Dice dice=new Dice(0,0,0,6,3,2);
	
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		
		map=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		int sum=0;
		
		for(int m=0;m<M;m++) {
			int nr=dice.r+dr[dice.d];
			int nc=dice.c+dc[dice.d];
			if(nr<0||nr>=N||nc<0||nc>N) {
				dice.r-=dr[dice.d];
				dice.c-=dc[dice.d];
				dice.d=(dice.d+2)%4;
			}
			else {
				dice.r=nr;
				dice.c=nc;
			}
			
			sum+=bfs(dice.r,dice.c,map[dice.r][dice.c]);
			
			roll();
			
			if(dice.bottom>map[dice.r][dice.c]) {
				dice.d=(dice.d+1)%4;
			}
			else if(dice.bottom<map[dice.r][dice.c]) {
				dice.d=(dice.d+3)%4;
			}
		}
		
		bw.write(sum+"");
		bw.close();
	}
	
	static boolean[][] v;
	public static int bfs(int r, int c, int score) {
		v=new boolean[N][N];
		Queue<Point>q=new ArrayDeque();
		q.offer(new Point(r,c));
		v[r][c]=true;
		int cnt=1;
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]==score&&!v[nr][nc]) {
					q.offer(new Point(nr,nc));
					v[nr][nc]=true;
					cnt++;
				}
			}
		}
		return score*cnt;
	}
	
	public static void roll() {
		int temp=dice.bottom;
		switch(dice.d) {
		case 0:
			dice.bottom=dice.right;
			dice.right=7-temp;
			break;
		case 1:
			dice.bottom=dice.front;
			dice.front=7-temp;
			break;
		case 2:
			dice.bottom=7-dice.right;
			dice.right=temp;
			break;
		case 3:
			dice.bottom=7-dice.front;
			dice.front=temp;
			break;
		}
	}
	
	static class Dice{
		int r,c,d,bottom,right,front;
		public Dice(int r,int c,int d,int bottom,int right,int front) {
			this.r=r;
			this.c=c;
			this.d=d;
			this.bottom=bottom;
			this.right=right;
			this.front=front;
		}
	}

	static class Point{
		int r,c;
		public Point(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
}