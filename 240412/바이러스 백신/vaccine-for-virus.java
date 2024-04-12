import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M;
	static int[][]map;
	static boolean[][]v;
	static List<Hospital> hospitals=new ArrayList();

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		map=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				int input=Integer.parseInt(st.nextToken());
				map[i][j]=input;
				if(input==2) {
					hospitals.add(new Hospital(i,j));
				}
			}
		}
		
		
		back(0,0,new int[M]);
		
		if(answer==Integer.MAX_VALUE) answer=-1;
		
		bw.write(answer+"");
		bw.close();
	}
	
	static int answer=Integer.MAX_VALUE;
	
	static int bfs(int[] array) {
		int time=0;
		v=new boolean[N][N];
		Queue<Point> q=new ArrayDeque();
		for(int i=0;i<M;i++) {
			Hospital h=hospitals.get(array[i]);
			q.offer(new Point(h.r,h.c));
			v[h.r][h.c]=true;
		}
		
		while(!q.isEmpty()) {
			boolean check=true;
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					if(map[i][j]==0&&!v[i][j]) {
						check=false;
					}
				}
			}
			if(check) break;
			
			int size=q.size();
			for(int s=0;s<size;s++) {
				Point p=q.poll();
				for(int d=0;d<4;d++) {
					int nr=p.r+dr[d];
					int nc=p.c+dc[d];
					if(nr>=0&&nr<N&&nc>=0&&nc<N&&!v[nr][nc]&&map[nr][nc]!=1) {
						q.offer(new Point(nr,nc));
						v[nr][nc]=true;
					}
				}
			}
			time++;
			
		}

		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j]==0&&!v[i][j]) {
					time=Integer.MAX_VALUE;
				}
			}
		}
		
		return time;
	}
	
	static void back(int cnt, int idx, int[] array) {
		if(cnt==M) {
			answer=Math.min(answer, bfs(array));
			return;
		}
		if(idx==hospitals.size()) return;
		
		array[cnt]=idx;
		back(cnt+1,idx+1,array);
		back(cnt,idx+1,array);
	}
	
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};
	
	static class Hospital{
		int r,c;
		public Hospital(int r, int c) {
			this.r=r;
			this.c=c;
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