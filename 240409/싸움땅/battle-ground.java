import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	static PriorityQueue<Integer>[][] gMap;
	static int[][] pMap;
	
	static ArrayList<Player> players=new ArrayList<>();
	static int[] score;
	

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		gMap=new PriorityQueue[N][N];
		pMap=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				int input=Integer.parseInt(st.nextToken());
				PriorityQueue<Integer>pq=new PriorityQueue(Comparator.reverseOrder());
				if(input!=0) {
					pq.offer(input);
				}
				gMap[i][j]=pq;
			}
		}
		
		players.add(new Player(0,0,0,0,0));
		for(int m=1;m<=M;m++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			int d=Integer.parseInt(st.nextToken());
			int s=Integer.parseInt(st.nextToken());
			players.add(m,new Player(m,r,c,d,s));
			pMap[r][c]=m;
		}
		
		score=new int[M+1];


		for(int k=0;k<K;k++) {
			
			movePlayer();
			
//			for(int i=0;i<N;i++) {
//				for(int j=0;j<N;j++) {
//					System.out.print(gMap[i][j].toString()+" ");
//				}
//				System.out.println();
//			}
			
//			for(int i=0;i<N;i++) {
//				System.out.println(Arrays.toString(pMap[i]));
//			}
//			System.out.println();
		}
		

		
		for(int i=1;i<=M;i++) {
			bw.write(score[i]+" ");
		}
		bw.close();
	}
	
	public static void movePlayer() {
		for(int i=1;i<=M;i++) {
			Player p=players.get(i);
			int p1Num=p.num;
			int nr=p.r+dr[p.d];
			int nc=p.c+dc[p.d];
			if(nr<0||nr>=N||nc<0||nc>=N) {
				p.d=(p.d+2)%4;
				nr=p.r+dr[p.d];
				nc=p.c+dc[p.d];
			}
			
			pMap[p.r][p.c]=0;
			p.r=nr;
			p.c=nc;
			
			if(pMap[nr][nc]==0) {
				pMap[nr][nc]=p.num;
				
				if(!gMap[nr][nc].isEmpty()) {
					int pg=p.g;
					int mg=gMap[nr][nc].poll();
					p.g=Math.max(pg, mg);
					if(pg!=0) {
						gMap[nr][nc].offer(Math.min(pg, mg));
					}
				}
			}
			else {
				int p2Num=pMap[nr][nc];
				Player p2=players.get(p2Num);
				
				fight(p,p2);
			}
		}
	}
	
	public static void fight(Player p1, Player p2) {
		Player win;
		Player lose;
		
		if(p1.p+p1.g==p2.p+p2.g) {
			if(p1.p>p2.p) {
				win=p1;
				lose=p2;
			}
			else {
				win=p2;
				lose=p1;
			}
		}
		else if(p1.p+p1.g>p2.p+p2.g) {
			win=p1;
			lose=p2;
		}
		else {
			win=p2;
			lose=p1;
		}
		pMap[win.r][win.c]=win.num;
		score[win.num]+=Math.abs((win.p+win.g)-(lose.p+lose.g));
		
		moveLoser(lose);
		
		if(!gMap[win.r][win.c].isEmpty()) {
			int wg=win.g;
			int mg=gMap[win.r][win.c].poll();
			win.g=Math.max(wg, mg);
			if(wg!=0) {
				gMap[win.r][win.c].offer(Math.min(wg, mg));
			}
		}
	}
	
	public static void moveLoser(Player loser) {
		if(loser.g!=0) {
			gMap[loser.r][loser.c].offer(loser.g);
			loser.g=0;
		}
		
		for(int i=0;i<4;i++) {
			int nd=(loser.d+i)%4;
			int nr=loser.r+dr[nd];
			int nc=loser.c+dc[nd];
			if(nr>=0&&nr<N&&nc>=0&&nc<N&&pMap[nr][nc]==0) {
				pMap[nr][nc]=loser.num;
				loser.r=nr;
				loser.c=nc;
				loser.d=nd;
				if(!gMap[nr][nc].isEmpty()) {
					int pg=loser.g;
					int mg=gMap[nr][nc].poll();
					loser.g=Math.max(pg, mg);
					if(pg!=0) {
						gMap[nr][nc].offer(Math.min(pg, mg));
					}
				}
				break;
			}
		}
	}
	
	static int[] dr= {-1,0,1,0};
	static int[] dc= {0,1,0,-1};
	static class Player{
		int num,r,c,d,p,g;
		public Player(int num,int r, int c, int d, int p) {
			this.num=num;
			this.r=r;
			this.c=c;
			this.d=d;
			this.p=p;
		}
	}

}