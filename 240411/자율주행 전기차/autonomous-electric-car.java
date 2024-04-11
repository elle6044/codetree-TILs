import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,C;
	static int[][] map;
	static boolean[][] v;
	static Car car;
	
	static List<Man> mans=new ArrayList();
	static int[][] start;
	static int[][] end;
	

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		C=Integer.parseInt(st.nextToken());
		
		map=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		st=new StringTokenizer(br.readLine());
		int r=Integer.parseInt(st.nextToken())-1;
		int c=Integer.parseInt(st.nextToken())-1;
		car=new Car(r,c,C);
		
		start=new int[M][2];
		end=new int[M][2];
		for(int i=0;i<M;i++) {
			st=new StringTokenizer(br.readLine());
			int sr=Integer.parseInt(st.nextToken())-1;
			int sc=Integer.parseInt(st.nextToken())-1;
			int er=Integer.parseInt(st.nextToken())-1;
			int ec=Integer.parseInt(st.nextToken())-1;
			start[i][0]=sr;
			start[i][1]=sc;
			end[i][0]=er;
			end[i][1]=ec;
			mans.add(new Man(i,sr,sc));
		}
		
		int answer=0;
		for(int i=0;i<M;i++) {	
			if(!bfs()) {
				answer=-1;
				break;
			}
		}
		if(isEnd()) {
			answer=car.p;
		}
		else {
			answer=-1;
		}
		bw.write(answer+"");
		bw.close();
		
	}
	
	static boolean isEnd() {
		for(Man m:mans) {
			if(m.end==false) {
				return false;
			}
		}
		return true;
	}
	
	
	static boolean bfs() {
		Queue<Point> q=new ArrayDeque();
		v=new boolean[N][N];
		
		int r=car.r;
		int c=car.c;
		
		Man man=null;
		int dist=Integer.MAX_VALUE;
		
		q.offer(new Point(r,c,0));
		v[r][c]=true;
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(Man m:mans) {
				if(m.end==true)continue;
				if(p.r==m.r&&p.c==m.c) {
					if(p.cnt<dist) {
						dist=p.cnt;
						man=m;
					}
					else if(p.cnt==dist) {
						if(p.r<man.r) {
							dist=p.cnt;
							man=m;
						}
						else if(p.r==man.r) {
							if(p.c<man.c) {
								dist=p.cnt;
								man=m;
							}
						}
					}
				}
			}
			
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&!v[nr][nc]&&map[nr][nc]==0) {
					q.offer(new Point(nr,nc,p.cnt+1));
					v[nr][nc]=true;
				}
			}
		}
		
		if(car.p>=dist) {
			car.p-=dist;
			car.r=man.r;
			car.c=man.c;
			int edist=ebfs(man.num);
			if(car.p>=edist) {
				car.p-=edist;
				car.r=end[man.num][0];
				car.c=end[man.num][1];
				car.p+=edist*2;
				man.end=true;
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	static int ebfs(int num) {
		int r=end[num][0];
		int c=end[num][1];
		Queue<Point> q=new ArrayDeque();
		v=new boolean[N][N];
		
		int cnt=Integer.MAX_VALUE;
		
		q.offer(new Point(r,c,0));
		v[r][c]=true;
		while(!q.isEmpty()) {
			Point p=q.poll();
			if(p.r==car.r&&p.c==car.c) {
				cnt=Math.min(cnt, p.cnt);
			}
			
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&!v[nr][nc]&&map[nr][nc]==0) {
					q.offer(new Point(nr,nc,p.cnt+1));
					v[nr][nc]=true;
				}
			}
		}
		
		return cnt;
		
	}
	
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1,};
	static class Point{
		int r,c,cnt;
		public Point(int r, int c, int cnt) {
			this.r=r;
			this.c=c;
			this.cnt=cnt;
		}
	}
	
	static class Car{
		int r,c,p,m;
		public Car(int r, int c, int p) {
			this.r=r;
			this.c=c;
			this.p=p;
			this.m=0;
		}
	}
	
	static class Man{
		int num,r,c;
		boolean end;
		public Man(int num, int r, int c) {
			this.num=num;
			this.r=r;
			this.c=c;
			this.end=false;
		}
	}

}