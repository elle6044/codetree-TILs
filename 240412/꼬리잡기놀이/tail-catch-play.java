import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	static int[][] map;
	static int[][] tmap;
	static boolean[][] v;
	
	static Team[] teams;

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		map=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		teams=new Team[M+1];
		for(int i=1;i<=M;i++) {
			Team t=new Team();
			teams[i]=t;
		}
		
		makeTeam();
		
		for(int k=0;k<K;k++) {
			
			moveTeam();
			
			shotBall(k);
		}
		

		
		bw.write(answer+"");
		bw.close();
	}
	
	static int answer=0;
	
	static void shotBall(int round) {
		int dir=round/N;
		int r=0;
		int c=0;
		if(dir==0) {
			r=round%N;
			c=0;
		}
		else if(dir==1) {
			r=N-1;
			c=round%N;
		}
		else if(dir==2) {
			r=N-1-round%N;
			c=N-1;
		}
		else if(dir==3) {
			r=0;
			c=N-1-round%N;
		}
		
		
		for(int d=0;d<N;d++) {
			int nr=r+dr[dir]*d;
			int nc=c+dc[dir]*d;
			
			if(tmap[nr][nc]!=0) {
				Team t=teams[tmap[nr][nc]];
				int cnt=1;
				
				if(t.head==1) {
					for(int i=0;i<t.mans.size();i++) {
						Man m=t.mans.get(i);
						if(m.r==nr&&m.c==nc) {
							answer+=Math.pow(cnt, 2);
							t.head=t.head==1?3:1;
							return;
						}
						cnt++;
					}
				}
				else {
					for(int i=t.mans.size()-1;i>=0;i--) {
						Man m=t.mans.get(i);
						if(m.r==nr&&m.c==nc) {
							answer+=Math.pow(cnt, 2);
							t.head=t.head==1?3:1;
							return;
						}
						cnt++;
					}
				}
			}
		}
	}
	
	static void moveTeam() {
		for(int m=1;m<=M;m++) {
			Team t=teams[m];
			if(t.head==1) {
				for(int i=t.mans.size()-1;i>=1;i--) {
					Man back=t.mans.get(i);
					Man fore=t.mans.get(i-1);
					
					if(i==t.mans.size()-1) {
						map[back.r][back.c]=4;
						tmap[back.r][back.c]=0;
					}
//					map[fore.r][fore.c]=back.num;
//					tmap[fore.r][fore.c]=m;
					
					back.r=fore.r;
					back.c=fore.c;
				}
				Man head=t.mans.get(0);
				for(int d=0;d<4;d++) {
					int nr=head.r+dr[d];
					int nc=head.c+dc[d];
					if(nr>=0&&nr<N&&nc>=0&&nc<N&&
							(map[nr][nc]==4||map[nr][nc]==3)) {
						head.r=nr;
						head.c=nc;
						
						map[nr][nc]=head.num;
						tmap[nr][nc]=m;
						
						break;
					}
				}
			}
			else {
				for(int i=0;i<t.mans.size()-1;i++) {
					Man back=t.mans.get(i);
					Man fore=t.mans.get(i+1);
					
					if(i==0) {
						map[back.r][back.c]=4;
						tmap[back.r][back.c]=0;
					}
//					map[fore.r][fore.c]=back.num;
//					tmap[fore.r][fore.c]=m;
					
					back.r=fore.r;
					back.c=fore.c;
				}
				Man head=t.mans.get(t.mans.size()-1);
				for(int d=0;d<4;d++) {
					int nr=head.r+dr[d];
					int nc=head.c+dc[d];
					if(nr>=0&&nr<N&&nc>=0&&nc<N&&
							(map[nr][nc]==4||map[nr][nc]==1)) {
						head.r=nr;
						head.c=nc;
						
						map[nr][nc]=head.num;
						tmap[nr][nc]=m;
						
						break;
					}
				}
			}
			
			for(Man man:t.mans) {
				map[man.r][man.c]=man.num;
				tmap[man.r][man.c]=m;
			}
		}
	}
	
	static void makeTeam() {
		v=new boolean[N][N];
		tmap=new int[N][N];
		int num=1;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j]==1&&!v[i][j]) {
					bfsTeam(i,j,num);
					num++;
				}
			}
		}
	}
	
	static void bfsTeam(int r, int c, int num) {
		Queue<Man> q=new ArrayDeque();
		q.offer(new Man(num,r,c));
		v[r][c]=true;
		tmap[r][c]=num;
		Team t=teams[num];
		t.mans.add(new Man(map[r][c],r,c));
		
		while(!q.isEmpty()) {
			Man p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&!v[nr][nc]) {
					{
						if((p.num==1&&map[nr][nc]==2)||(p.num==2&&map[nr][nc]==3)||(p.num==2&&map[nr][nc]==2)) {
							q.offer(new Man(map[nr][nc],nr,nc));
							v[nr][nc]=true;
							tmap[nr][nc]=num;
							t.mans.add(new Man(map[nr][nc],nr,nc));
						}
					}
				}
			}
		}
	}
	
	static class Team{
		int head=1;
		List<Man> mans=new ArrayList();
	}
	
	static class Man{
		int num,r,c;
		public Man(int num, int r, int c) {
			this.num=num;
			this.r=r;
			this.c=c;
		}
	}
	
	static int[] dr= {0,-1,0,1};
	static int[] dc= {1,0,-1,0};

}