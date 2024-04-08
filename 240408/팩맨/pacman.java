import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int M,T,r,c;
	
	static int[] mdr= {-1,-1,0,1,1,1,0,-1};
	static int[] mdc= {0,-1,-1,-1,0,1,1,1};
	
	static int[] pdr= {-1,0,1,0};
	static int[] pdc= {0,-1,0,1};
	
	static int[][] mMap=new int[4][4];
	static int[][] bMap=new int[4][4];
	
	static Pack pack;
	static Queue<Monster>mq=new ArrayDeque<>();
	static Queue<Egg>eq=new ArrayDeque<>();
	static Queue<Body>bq=new ArrayDeque<>();
	
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		M=Integer.parseInt(st.nextToken());
		T=Integer.parseInt(st.nextToken());
		
		st=new StringTokenizer(br.readLine());
		pack=new Pack(Integer.parseInt(st.nextToken())-1,Integer.parseInt(st.nextToken())-1);
		
		for(int m=0;m<M;m++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			int d=Integer.parseInt(st.nextToken())-1;
			mq.offer(new Monster(r,c,d));
			mMap[r][c]++;
		}
		
		for(int t=0;t<T;t++) {
			copyMonster();
			
			moveMonster();

			movePack();

			removeBody();

			brokeEgg();
		}
		
		int answer=0;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				answer+=mMap[i][j];
			}
		}
		bw.write(answer+"");
		bw.close();
	}
	
	public static void printM() {
		for(int i=0;i<4;i++) {
			System.out.println(Arrays.toString(mMap[i]));
		}
		System.out.println();
	}
	
	public static void brokeEgg() {
		int size=eq.size();
		for(int i=0;i<size;i++) {
			Egg e=eq.poll();
			mq.offer(new Monster(e.r,e.c,e.d));
			mMap[e.r][e.c]++;
		}
	}
	
	public static void removeBody() {
		int size=bq.size();
		for(int i=0;i<size;i++) {
			Body b=bq.poll();
			b.time--;
			if(b.time>0) {
				bq.offer(b);
			}
			else {
				bMap[b.r][b.c]--;
			}
		}
	}
	
	public static void movePack() {
		v=new boolean[4][4];
		max=-1;
		maxR=0;
		maxC=0;
		maxDir=new int[3];
		int r=pack.r;
		int c=pack.c;
		back(r,c,0,new int[3],0);
		
		int nr=pack.r;
		int nc=pack.c;
		for(int d:maxDir) {
			nr+=pdr[d];
			nc+=pdc[d];
			bMap[nr][nc]+=mMap[nr][nc];
			mMap[nr][nc]=0;
			
			int size=mq.size();
			for(int i=0;i<size;i++) {
				Monster m=mq.poll();
				if(m.r!=nr||m.c!=nc) {
					mq.offer(m);
				}
				else {
					bq.offer(new Body(m.r,m.c));
					bMap[m.r][m.c]++;
				}
			}
		}
		
		pack.r=maxR;
		pack.c=maxC;
	}
	static boolean[][] v;
	static int max;
	static int maxR;
	static int maxC;
	static int[] maxDir;
	public static void back(int r, int c, int cnt, int[] dir, int sum) {
		if(cnt==3) {
			if(max<sum) {
				max=sum;
				maxR=r;
				maxC=c;
				for(int i=0;i<3;i++) {
					maxDir[i]=dir[i];
				}
			}
			return;
		}
		
		for(int d=0;d<4;d++) {
			int nr=r+pdr[d];
			int nc=c+pdc[d];
			if(nr>=0&&nr<4&&nc>=0&&nc<4) {
				dir[cnt]=d;
				if(v[nr][nc]) {
					back(nr,nc,cnt+1,dir,sum);
				}
				else {
					v[nr][nc]=true;
					back(nr,nc,cnt+1,dir,sum+mMap[nr][nc]);
					v[nr][nc]=false;
				}
			}
		}
	}
	
	public static void moveMonster() {
		for(Monster m:mq) {
			for(int i=0;i<8;i++) {
				int nd=(m.d+i)%8;
				int nr=m.r+mdr[nd];
				int nc=m.c+mdc[nd];
				if(nr>=0&&nr<4&&nc>=0&&nc<4&&bMap[nr][nc]==0
						&&!(pack.r==nr&&pack.c==nc)) {
					mMap[m.r][m.c]--;
					m.r=nr;
					m.c=nc;
					m.d=nd;
					mMap[nr][nc]++;
					break;
				}
			}
		}
	}
	
	public static void copyMonster() {
		for(Monster m:mq) {
			eq.offer(new Egg(m.r,m.c,m.d));
		}
	}
	
	static class Pack{
		int r,c;
		public Pack(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
	
	static class Monster{
		int r,c,d;
		public Monster(int r, int c, int d) {
			this.r=r;
			this.c=c;
			this.d=d;
		}
	}
	
	static class Egg{
		int r,c,d,time;
		public Egg(int r, int c, int d) {
			this.r=r;
			this.c=c;
			this.d=d;
			this.time=1;
		}
	}
	
	static class Body{
		int r,c,time;
		public Body(int r, int c) {
			this.r=r;
			this.c=c;
			this.time=2;
		}
	}

}