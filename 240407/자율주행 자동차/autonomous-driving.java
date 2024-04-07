import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M;
	static int[][] map;
	static boolean[][] v;
	
	static int[] dr= {-1,0,1,0};
	static int[] dc= {0,1,0,-1};
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		
		st=new StringTokenizer(br.readLine());
		int x=Integer.parseInt(st.nextToken());
		int y=Integer.parseInt(st.nextToken());
		int d=Integer.parseInt(st.nextToken());
		
		map=new int[N][M];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		v=new boolean[N][M];
		
		
		bfs(x,y,d);
		
		bw.write(answer+"");
		bw.close();
	}
	static int answer=1;
	
	public static void bfs(int r, int c, int dir) {
		Queue<Point> q=new ArrayDeque();
		q.offer(new Point(r,c,dir));
		v[r][c]=true;
		
		L:while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=1;d<=4;d++) {
				int nd=(p.d+3*d)%4;
				int nr=p.r+dr[nd];
				int nc=p.c+dc[nd];
				if(map[nr][nc]==0) {
					if(!v[nr][nc]) {
						v[nr][nc]=true;
						q.offer(new Point(nr,nc,nd));
						answer++;
						continue L;
					}
				}
			}
			int nd=(p.d+2)%4;
			int nr=p.r+dr[nd];
			int nc=p.c+dc[nd];
			if(map[nr][nc]==0) {
				q.offer(new Point(nr,nc,p.d));
			}
		}
	}
	
	static class Point{
		int r,c,d;
		public Point(int r, int c, int d) {
			this.r=r;
			this.c=c;
			this.d=d;
		}
	}

}