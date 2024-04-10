import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	
	static int[][] map;
	static int[][] v;
	
	static PriorityQueue<Player> players=new PriorityQueue();

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		map=new int[N][N];
		v=new int[N][N];

		PriorityQueue<Player> temp=new PriorityQueue();
		
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				int input=Integer.parseInt(st.nextToken());
				map[i][j]=input;
				if(input>0) {
					temp.offer(new Player(input,i,j));
					v[i][j]=K;
				}
			}
		}
		st=new StringTokenizer(br.readLine());
		for(int i=1;i<=M;i++) {
			int d=Integer.parseInt(st.nextToken())-1;
			Player p=temp.poll();
			p.d=d;
			players.offer(p);
		}
		
		dir=new int[M+1][4][4];
		for(int i=1;i<=M;i++) {
			for(int j=0;j<4;j++) {
				st=new StringTokenizer(br.readLine());
				for(int d=0;d<4;d++) {
					dir[i][j][d]=Integer.parseInt(st.nextToken())-1;
				}
			}
			
		}
		
		int answer=1;
		while(true) {
			
			move();
			time();
			eat();
			
			if(players.size()==1) break;
			
			answer++;
			if(answer>=1000) {
				answer=-1;
				break;
			}
			
			
		}
		bw.write(answer+"");
		bw.close();
	}
	
	public static void time() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(v[i][j]>0) {
					v[i][j]--;
					if(v[i][j]==0) {
						map[i][j]=0;
					}
				}
			}
		}
	}
	
	public static void eat() {
		List<Player> temp=new ArrayList();
		while(!players.isEmpty()) {
			Player p=players.poll();
			
			v[p.r][p.c]=K;
			map[p.r][p.c]=p.num;
			temp.add(p);
		}
		players.addAll(temp);
	}
	
	public static void move() {
		List<Player> temp=new ArrayList();
		while(!players.isEmpty()) {
			Player p=players.poll();
			
			boolean check=false;
			
			for(int d=0;d<4;d++) {
				int nd=dir[p.num][p.d][d];
				
				int nr=p.r+dr[nd];
				int nc=p.c+dc[nd];
				if(nr>=0&&nr<N&&nc>=0&&nc<N) {
					if(v[nr][nc]==0) {
						check=true;
						if(map[nr][nc]==0) {
							map[nr][nc]=p.num;
							p.r=nr;
							p.c=nc;
							p.d=nd;
							temp.add(p);
						}
						break;
					}
				}
			}
			if(!check) {
				for(int d=0;d<4;d++) {
					int nd=dir[p.num][p.d][d];
					int nr=p.r+dr[nd];
					int nc=p.c+dc[nd];
					if(nr>=0&&nr<N&&nc>=0&&nc<N) {
						if(map[nr][nc]==p.num) {
							p.r=nr;
							p.c=nc;
							p.d=nd;
							temp.add(p);	
							break;
						}
					}
				}
			}
		}
		players.addAll(temp);
	}
	
	static int[] dr= {-1,1,0,0};
	static int[] dc= {0,0,-1,1};
	
	static int[][][] dir;

	static class Player implements Comparable<Player>{
		int num,r,c,d;
		public Player(int num, int r, int c) {
			this.num=num;
			this.r=r;
			this.c=c;
			this.d=0;
		}
		@Override
		public int compareTo(Player o) {
			return this.num-o.num;
		}
	}
}