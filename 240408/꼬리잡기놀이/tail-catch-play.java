import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	static int[][] map;
	
	static ArrayList<Point>[] teams;
	static boolean[] dir;
	static int score=0;

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		int cnt=0;
		map=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}

		teams=new ArrayList[M+1];
		dir=new boolean[M+1];
		Arrays.fill(dir, true);
		
		for(int i=1;i<=M;i++) {
			ArrayList<Point> team=new ArrayList(); 
			teams[i]=team;
		}
		
		findTeam();
		
		for(int k=0;k<K;k++) {
			moveTeam();
			shot(k);
		}
		
		bw.write(score+"");
		bw.close();
	}
	
	public static void shot(int round) {
		int dir=round/N;
		int line=round%N;
		
		switch(dir) {
		case 0:
			lazer(line,0,dir);
			break;
		case 1:
			lazer(N-1,line,dir);
			break;
		case 2 :
			lazer(N-1-line,N-1,dir);
			break;
		case 3 :
			lazer(0,N-1-line,dir);
			break;
		}
	}
	
	public static void lazer(int r, int c, int d) {
		for(int i=0;i<N;i++) {
			int nr=r+dr[d]*i;
			int nc=c+dc[d]*i;
			if(map[nr][nc]!=0&&map[nr][nc]!=4) {
				int idx=map[nr][nc]/10;
				dir[idx]=dir[idx]==false;
				for(int j=0;j<teams[idx].size();j++) {
					Point p=teams[idx].get(j);
					if(p.r==nr&&p.c==nc) {
						score+=Math.pow(p.num, 2);
					}
					p.num=teams[idx].size()-p.num+1;
				}
				return;
			}
		}
	}
	
	public static void moveTeam() {
		for(int i=1;i<=M;i++) {	
			ArrayList<Point> team=teams[i];
			Point first;
			if(dir[i]) {
				map[team.get(team.size()-1).r][team.get(team.size()-1).c]=4;
				for(int j=team.size()-1;j>=1;j--) {
					int r=team.get(j).r;
					int c=team.get(j).c;
					int nr=team.get(j-1).r;
					int nc=team.get(j-1).c;
					team.get(j).r=nr;
					team.get(j).c=nc;

					map[nr][nc]=i*10;
					
				}
				first=team.get(0);
			}
			else {
				map[team.get(0).r][team.get(0).c]=4;
				for(int j=0;j<team.size()-1;j++) {
					int r=team.get(j).r;
					int c=team.get(j).c;
					int nr=team.get(j+1).r;
					int nc=team.get(j+1).c;
					team.get(j).r=nr;
					team.get(j).c=nc;

					map[nr][nc]=i*10;
				}
				first=team.get(team.size()-1);
			}
			
			for(int d=0;d<4;d++) {
				int nr=first.r+dr[d];
				int nc=first.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]==4) {
					first.r=nr;
					first.c=nc;
					map[nr][nc]=i*10;
					break;
				}
			}
			
		}
	}
	
	
	static boolean[][]v;
	public static void findTeam() {
		int num=1;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j]==1) {
					findMan(i,j,num);
					num++;
				}
			}
		}
	}
	
	public static void findMan(int r, int c, int num) {
		int cnt=1;
		v=new boolean[N][N];
		Queue<Point>q=new ArrayDeque();
		q.offer(new Point(r,c,cnt));
		v[r][c]=true;
		teams[num].add(new Point(r,c,cnt++));
		
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]!=4&&
						map[nr][nc]!=0&&!v[nr][nc]) {
					q.offer(new Point(nr,nc,cnt));
					v[nr][nc]=true;
					teams[num].add(new Point(nr,nc,cnt++));
				}
			}
		}
	}
	
	static int[] dr= {0,-1,0,1};
	static int[] dc= {1,0,-1,0};
	static class Point{
		int r,c,num;
		public Point(int r, int c, int num) {
			this.r=r;
			this.c=c;
			this.num=num;
		}
	}

}