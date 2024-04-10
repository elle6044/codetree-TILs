import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	static int[][] officeMap;
	static int[][] coolMap;
	static int[][] pcoolMap;
	static List<Aircon> aircons;
	static List<Wall> walls;
	
	static boolean[][] v;
	
	static int[] updown= {1,3};
	

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		officeMap=new int[N][N];
		coolMap=new int[N][N];
		walls=new ArrayList();
		aircons=new ArrayList();
		
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				int input=Integer.parseInt(st.nextToken());
				if(input<=1) {
					officeMap[i][j]=input;
				}
				else {
					aircons.add(new Aircon(i,j,input-2));
				}
			}
		}
		for(int m=0;m<M;m++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			int s=Integer.parseInt(st.nextToken());
			
			if(s==0) {
				walls.add(new Wall(r,c,1));
				if(r-1>=0&&r-1<N) {
					walls.add(new Wall(r-1,c,3));
				}
			}
			else {
				walls.add(new Wall(r,c,0));
				if(c-1>=0&&c-1<N) {
					walls.add(new Wall(r,c-1,2));
				}
			}
		}
		
		
		int answer=0;
		while(true) {
			if(isEnd()) break;
			if(answer>=100) {
				answer=-1;
				break;
			}
			
			workAircon();
			
			changeCool();
			
			changeOut();
			
			answer++;
		}

		
		bw.write(answer+"");
		bw.close();
	}
	
	public static void changeOut() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(i==0||i==N-1||j==0||j==N-1) {
					if(coolMap[i][j]>0) {
						coolMap[i][j]--;
					}
				}
			}
		}
	}
	
	public static void changeCool() {
		pcoolMap=new int[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				for(int d=0;d<4;d++) {
					int nr=i+dr[d];
					int nc=j+dc[d];
					if(nr>=0&&nr<N&&nc>=0&&nc<N&&coolMap[i][j]>coolMap[nr][nc]) {
						boolean check=true;
						for(Wall wall:walls) {
							if((i==wall.r&&j==wall.c&&d==wall.d)||
									(nr==wall.r&&nc==wall.c&&d==(wall.d+2)%4)) {
								check=false;
								break;
							}
						}
						if(check) {
							int dif=(coolMap[i][j]-coolMap[nr][nc])/4;
							pcoolMap[nr][nc]+=dif;
							pcoolMap[i][j]-=dif;
						}
					}
				}
			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				coolMap[i][j]+=pcoolMap[i][j];
			}
		}
		
		
	}
	
	public static void workAircon() {
		for(Aircon air:aircons) {
			bfsAircon(air);
		}
	}
	
	public static void bfsAircon(Aircon air) {
		v=new boolean[N][N];
		Queue<Wind> q=new ArrayDeque();
		q.offer(new Wind(air.r+dr[air.d],air.c+dc[air.d],5));
		v[air.r+dr[air.d]][air.c+dc[air.d]]=true;
		while(!q.isEmpty()) {
			Wind w=q.poll();
			if(w.cnt==0)continue;
			coolMap[w.r][w.c]+=w.cnt;
			
			int nr=w.r+dr[air.d];
			int nc=w.c+dc[air.d];
			if(nr>=0&&nr<N&&nc>=0&&nc<N&&!v[nr][nc]) {
				boolean check=true;
				for(Wall wall:walls) {
					if((w.r==wall.r&&w.c==wall.c&&air.d==wall.d)||
							(nr==wall.r&&nc==wall.c&&air.d==(wall.d+2)%4)) {
						check=false;
						break;
					}
				}
				if(check) {
					q.offer(new Wind(nr,nc,w.cnt-1));
					v[nr][nc]=true;
				}
			}
			
			for(int i=0;i<2;i++) {
				int nr1=w.r+dr[(air.d+updown[i])%4];
				int nc1=w.c+dc[(air.d+updown[i])%4];
				int nr2=w.r+dr[(air.d+updown[i])%4]+dr[air.d];
				int nc2=w.c+dc[(air.d+updown[i])%4]+dc[air.d];
								
				if(nr2>=0&&nr2<N&&nc2>=0&&nc2<N&&!v[nr2][nc2]) {
					boolean check=true;
					for(Wall wall:walls) {
						if((w.r==wall.r&&w.c==wall.c&&(air.d+updown[i])%4==wall.d)||
								(nr1==wall.r&&nc1==wall.c&&(air.d+updown[i])%4==(wall.d+2)%4)||
								(nr1==wall.r&&nc1==wall.c&&air.d==wall.d)||
								(nr2==wall.r&&nc2==wall.c&&air.d==(wall.d+2)%4)) {
							check=false;
							break;
						}
					}
					if(check) {
						q.offer(new Wind(nr2,nc2,w.cnt-1));
						v[nr2][nc2]=true;
					}
				}
			}
		}
	}
	
	public static boolean isEnd() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(officeMap[i][j]==1&&coolMap[i][j]<K) {
					return false;
				}
			}
		}
		return true;
	}

	static int[] dr= {0,-1,0,1};
	static int[] dc= {-1,0,1,0};
	
	static class Aircon{
		int r,c,d;
		public Aircon(int r, int c, int d) {
			this.r=r;
			this.c=c;
			this.d=d;
		}
	}
	
	static class Wall{
		int r,c,d;
		public Wall(int r, int c, int d) {
			this.r=r;
			this.c=c;
			this.d=d;
		}
	}
	
	static class Wind{
		int r,c,cnt;
		public Wind(int r, int c, int cnt) {
			this.r=r;
			this.c=c;
			this.cnt=cnt;
		}
	}
}