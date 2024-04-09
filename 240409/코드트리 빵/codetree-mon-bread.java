import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M;
	static int[][] map;
	static boolean[][] v;
	static ArrayList<Mart> marts=new ArrayList<>();
	static ArrayList<Man> mans=new ArrayList<>();
	static boolean[][] cant;
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		
		map=new int[N][N];
		v=new boolean[N][N];
		cant=new boolean[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		marts.add(new Mart(0,0,0));
		mans.add(new Man(0,0,0));
		for(int m=1;m<=M;m++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			marts.add(new Mart(m,r,c));
		}
		
		int answer=1;
		
		while(true) {
			moveMan();
			
			check();
			
			if(answer<=M) {
				addMan(answer);
			}
			
			if(isEnd()) break;
			answer++;
			
//			for(int i=1;i<mans.size();i++) {
//				Man m=mans.get(i);
//				System.out.println(m.r+" "+m.c+" "+m.end);
//			}
//			System.out.println();
		}
		
		bw.write(answer+"");
		bw.close();
	}
	
	public static void check() {
		for(int i=1;i<mans.size();i++) {
			Man man=mans.get(i);
			Mart mart=marts.get(i);
			
			if(man.r==mart.r&&man.c==mart.c) {
				man.end=true;
				cant[man.r][man.c]=true;
			}
		}
	}
	
	public static boolean isEnd() {
		for(int i=1;i<mans.size();i++) {
			if(mans.get(i).end==false) return false;
		}
		return true;
	}
	
	public static void addMan(int num) {
		Mart mart=marts.get(num);
		v=new boolean[N][N];
		PriorityQueue<Point2> q=new PriorityQueue();
		int r=mart.r;
		int c=mart.c;
		v[r][c]=true;
		q.offer(new Point2(r,c,0));
		while(!q.isEmpty()) {
			Point2 p=q.poll();
			if(map[p.r][p.c]==1) {
				mans.add(new Man(num,p.r,p.c));
				cant[p.r][p.c]=true;
				break;
			}
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&
						!cant[nr][nc]&&!v[nr][nc]) {
					v[nr][nc]=true;
					q.offer(new Point2(nr,nc,p.cnt+1));
				}
			}
		}
	}
	
	public static void moveMan() {
		for(int i=1;i<mans.size();i++) {
			Man man=mans.get(i);
			if(man.end) continue;
			
			moveOne(man);
		}
	}
	
	public static void moveOne(Man man) {
		v=new boolean[N][N];
		Queue<Point> q=new ArrayDeque();
		int r=man.r;
		int c=man.c;
		v[r][c]=true;
		for(int d=0;d<4;d++) {
			int nr=r+dr[d];
			int nc=c+dc[d];
			if(nr>=0&&nr<N&&nc>=0&&nc<N&&!cant[nr][nc]&&!v[nr][nc]) {
				q.offer(new Point(nr,nc,d));
				v[nr][nc]=true;
			}
		}
		
		while(!q.isEmpty()) {
			Point p=q.poll();
			if(p.r==marts.get(man.num).r&&p.c==marts.get(man.num).c) {
				man.r+=dr[p.d];
				man.c+=dc[p.d];
				break;
			}
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&!cant[nr][nc]&&!v[nr][nc]) {
					q.offer(new Point(nr,nc,p.d));
					v[nr][nc]=true;
				}
			}
		}
		
	}
	
	static int[] dr= {-1,0,0,1};
	static int[] dc= {0,-1,1,0};
	
	static class Mart{
		int num,r,c;
		public Mart(int num, int r, int c) {
			this.num=num;
			this.r=r;
			this.c=c;
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
	
	static class Point{
		int r,c,d;
		public Point(int r, int c, int d) {
			this.r=r;
			this.c=c;
			this.d=d;
		}
	}
	
	static class Point2 implements Comparable<Point2>{
		int r,c,cnt;
		public Point2(int r, int c, int cnt) {
			this.r=r;
			this.c=c;
			this.cnt=cnt;
		}
		@Override
		public int compareTo(Point2 o) {
			return this.cnt==o.cnt?this.r==o.r?this.c-o.c:this.r-o.r:this.cnt-o.cnt;
		}
	}

}