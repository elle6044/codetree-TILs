import java.util.*;
import java.io.*;
public class Main {
	
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,L,R;
	static int[][] map;
	static boolean[][] v;
	

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		L=Integer.parseInt(st.nextToken());
		R=Integer.parseInt(st.nextToken());
		
		map=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		int answer=0;
		
		while(true) {
			
			if(!play()) {
				break;
			}
			answer++;
		}
		
		bw.write(answer+"");
		bw.close();
	}
	
	public static boolean play() {
		v=new boolean[N][N];
		boolean check=false;
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(!v[i][j]) {
					if(bfs(i,j)) {
						check=true;
					}
				}
			}
		}
		return check;
	}
	
	public static boolean bfs(int r, int c) {
		Queue<Point> q=new ArrayDeque();
		Queue<Point> q2=new ArrayDeque();
		int sum=map[r][c];
		v[r][c]=true;
		q.offer(new Point(r,c,map[r][c]));
		q2.offer(new Point(r,c,map[r][c]));
		
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&!v[nr][nc]) {
					int num=Math.abs(p.cnt-map[nr][nc]);
					if(num>=L&&num<=R) {
						sum+=map[nr][nc];
						v[nr][nc]=true;
						q.offer(new Point(nr,nc,map[nr][nc]));
						q2.offer(new Point(nr,nc,map[nr][nc]));
					}
				}
			}
		}
		if(q2.size()==1) {
			return false;
		}
		else {
			int num=sum/q2.size();
			map[r][c]=num;
			
			while(!q2.isEmpty()) {
				Point p=q2.poll();
				map[p.r][p.c]=num;
			}
			return true;
		}
	}
	
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};
	
	static class Point{
		int r,c,cnt;
		public Point(int r, int c, int cnt) {
			this.r=r;
			this.c=c;
			this.cnt=cnt;
		}
	}

}