import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	static int[][] map;
	
	static List<Player> players=new ArrayList();
	
	static Player exit;

	public static void main(String[] args) throws Exception {
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
		
		for(int m=0;m<M;m++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			players.add(new Player(r,c));
		}
		
		st=new StringTokenizer(br.readLine());
		int r=Integer.parseInt(st.nextToken())-1;
		int c=Integer.parseInt(st.nextToken())-1;
		exit=new Player(r,c);
		
		
		for(int k=0;k<K;k++) {
			
			movePlayer();

			
			if(isEnd()) break;
			
			rotateMap();

		}
		
		int answer=getAnswer();
		bw.write(answer+"\n"+(exit.r+1)+" "+(exit.c+1));
		bw.close();
	}
	
	static int getAnswer() {
		int sum=0;
		for(Player p:players) {
			sum+=p.cnt;
		}
		return sum;
	}
	
	static boolean isEnd() {
		for(Player p:players) {
			if(!p.out) return false;
		}
		return true;
	}
	
	static void rotateMap() {
		int size=N;
		int er=0;
		int ec=0;
		int sr=0;
		int sc=0;
		
		L:for(int s=2;s<=N;s++) {
			for(int i=0;i<=N-s;i++) {
				for(int j=0;j<=N-s;j++) {
					int sr2=i;
					int sc2=j;
					int er2=i+s-1;
					int ec2=j+s-1;
					if(exit.r>=sr2&&exit.r<=er2&&exit.c>=sc2&&exit.c<=ec2) {
						for(Player p:players) {
							if(p.out)continue;
							if(p.r>=sr2&&p.r<=er2&&p.c>=sc2&&p.c<=ec2) {
								size=s;
								sr=sr2;
								sc=sc2;
								er=er2;
								ec=ec2;
								break L;
							}
						}
					}
				}
			}
		}
		
		int[][] tmap=new int[size][size];
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				tmap[i][j]=map[sr+i][sc+j];
			}
		} 	
		
		for (int i = 0; i < size; i++) {
	        for (int j = 0; j < size; j++) {
	        	map[sr+j][sc+size-1-i]=tmap[i][j]==0?tmap[i][j]:tmap[i][j]-1;
	        }
	    }
		
		for(Player p:players) {
			if(p.out) continue;
			
			if(p.r>=sr&&p.r<=er&&p.c>=sc&&p.c<=ec) {
				int r=p.r;
				int c=p.c;
				p.r=sr + (c - sc);
				p.c=ec - (r - sr);
			}
		}
		
		int r=exit.r;
		int c=exit.c;
		exit.r=sr + (c - sc);
		exit.c=ec - (r - sr);
	}
	
	static void movePlayer() {
		for(Player p:players) {
			if(p.out) continue;
			
			int dist=Math.abs(exit.r-p.r)+Math.abs(exit.c-p.c);
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]==0) {
					int ndist=Math.abs(exit.r-nr)+Math.abs(exit.c-nc);
					if(ndist<dist) {
						p.r=nr;
						p.c=nc;
						p.cnt++;
						break;
					}
				}
			}
			if(p.r==exit.r&&p.c==exit.c) p.out=true;
		}
	}
	
	static class Player{
		int r,c,cnt;
		boolean out;
		public Player(int r, int c) {
			this.r=r;
			this.c=c;
			this.cnt=0;
			this.out=false;
		}
	}
	
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};

}