import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,P,C,D;
	
	static Santa[] santas;
	static int[][] map;
	static int[] rudolf=new int[2];

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		P=Integer.parseInt(st.nextToken());
		C=Integer.parseInt(st.nextToken());
		D=Integer.parseInt(st.nextToken());
		
		st=new StringTokenizer(br.readLine());
		rudolf[0]=Integer.parseInt(st.nextToken())-1;
		rudolf[1]=Integer.parseInt(st.nextToken())-1;
		
		santas=new Santa[P+1];
		map=new int[N][N];
		for(int p=1;p<=P;p++) {
			st=new StringTokenizer(br.readLine());
			int num=Integer.parseInt(st.nextToken());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			santas[num]=new Santa(num,r,c);
			map[r][c]=num;
		}
		
		
		
		
		for(int m=1;m<=M;m++) {
			moveRudolf();
			if(isEnd()) break;
//			for(int i=0;i<N;i++) {
//				System.out.println(Arrays.toString(map[i]));
//			}
//			System.out.println();
			
			moveSanta();
			if(isEnd()) break;
//			for(int i=0;i<N;i++) {
//				System.out.println(Arrays.toString(map[i]));
//			}
			
			healSanta();
			
//			for(int p=1;p<=P;p++) {
//				Santa s=santas[p];
//				System.out.print(s.p+" ");
//			}
//			System.out.println();
//			System.out.println();
			
			
			
		}
		for(int p=1;p<=P;p++) {
			Santa s=santas[p];
			bw.write(s.p+" ");
		}
		bw.close();
	}
	
	public static boolean isEnd() {
		for(int p=1;p<=P;p++) {
			Santa s=santas[p];
			if(!s.dead) {
				return false;
			}
		}
		return true;
	}
	
	public static void healSanta() {
		for(int p=1;p<=P;p++) {
			Santa s=santas[p];
			if(!s.dead) {
				if(s.t>0) {
					s.t--;
				}
				s.p++;
			}
		}
	}
	
	public static void moveSanta() {
		for(int p=1;p<=P;p++) {
			Santa s=santas[p];
			if(s.dead||s.t>0) continue;
			
			int dis=(int)Math.pow(rudolf[0]-s.r, 2)+(int)Math.pow(rudolf[1]-s.c, 2);
			int dir=-1;
			
			for(int d=0;d<4;d++) {
				int nr=s.r+dr2[d];
				int nc=s.c+dc2[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]==0) {
					int ndis=(int)Math.pow(rudolf[0]-nr, 2)+(int)Math.pow(rudolf[1]-nc, 2);
					if(ndis<dis) {
						dis=ndis;
						dir=d;
					}
				}
			}
			if(dir==-1) continue;
			
			map[s.r][s.c]=0;
			s.r+=dr2[dir];
			s.c+=dc2[dir];
			map[s.r][s.c]=s.num;
			
			if(s.r==rudolf[0]&&s.c==rudolf[1]) {
				conflict(1, s,(dir+2)%4);
			}
		}
	}
	
	public static void moveRudolf() {
		Santa s=findSanta();
		
		int r=rudolf[0];
		int c=rudolf[1];
		
		int dis=(int)Math.pow(r-s.r, 2)+(int)Math.pow(c-s.c, 2);
		int dir=0;
		
		for(int d=0;d<8;d++) {
			int nr=r+dr1[d];
			int nc=c+dc1[d];
			if(nr>=0&&nr<N&&nc>=0&&nc<N) {
				int ndis=(int)Math.pow(nr-s.r, 2)+(int)Math.pow(nc-s.c, 2);
				if(ndis<dis) {
					dis=ndis;
					dir=d;
				}
			}
		}
		
		rudolf[0]=r+dr1[dir];
		rudolf[1]=c+dc1[dir];
		
		if(map[rudolf[0]][rudolf[1]]!=0) {
			conflict(0, santas[map[rudolf[0]][rudolf[1]]],dir);
		}
	}
	
	public static void conflict(int attacker, Santa santa, int dir) {
		if(attacker==0) {
			santa.p+=C;
			santa.t=2;
			map[santa.r][santa.c]=0;
			santa.r+=dr1[dir]*C;
			santa.c+=dc1[dir]*C;
			if(santa.r<0||santa.r>=N||santa.c<0||santa.c>=N) {
				santa.dead=true;
			}
			else {
				int nowNum=santa.num;
				while(true) {
					if(map[santas[nowNum].r][santas[nowNum].c]!=0) {
						Santa ns=santas[map[santas[nowNum].r][santas[nowNum].c]];
						map[ns.r][ns.c]=nowNum;
						ns.r+=dr1[dir];
						ns.c+=dc1[dir];
						nowNum=ns.num;
						if(ns.r<0||ns.r>=N||ns.c<0||ns.c>=N) {
							ns.dead=true;
							break;
						}
					}
					else {
						map[santas[nowNum].r][santas[nowNum].c]=nowNum;
						break;
					}
				}
			}
		}
		else {
			santa.p+=D;
			santa.t=2;
			map[santa.r][santa.c]=0;
			santa.r+=dr2[dir]*D;
			santa.c+=dc2[dir]*D;
			if(santa.r<0||santa.r>=N||santa.c<0||santa.c>=N) {
				santa.dead=true;
			}
			else {
				int nowNum=santa.num;
				while(true) {
					if(map[santas[nowNum].r][santas[nowNum].c]!=0) {
						Santa ns=santas[map[santas[nowNum].r][santas[nowNum].c]];
						map[ns.r][ns.c]=nowNum;
						ns.r+=dr2[dir];
						ns.c+=dc2[dir];
						nowNum=ns.num;
						if(ns.r<0||ns.r>=N||ns.c<0||ns.c>=N) {
							ns.dead=true;
							break;
						}
					}
					else {
						map[santas[nowNum].r][santas[nowNum].c]=nowNum;
						break;
					}
				}
			}
		}
	}
	
	public static Santa findSanta() {
		Santa s=null;
		int dis=Integer.MAX_VALUE;
		
		for(int p=1;p<=P;p++) {
			Santa ns=santas[p];
			if(ns.dead) continue;
			int ndis=(int)Math.pow(ns.r-rudolf[0], 2)+(int)Math.pow(ns.c-rudolf[1], 2);
			if(ndis<dis) {
				s=ns;
				dis=ndis;
			}
			else if(ndis==dis) {
				if(ns.r>s.r) {
					s=ns;
					dis=ndis;
				}
				else if(ns.r==s.r) {
					if(ns.c>s.c) {
						s=ns;
						dis=ndis;
					}
				}
			}
		}
		return s;
	}
	
	public static int[] dr1= {-1,-1,0,1,1,1,0,-1};
	public static int[] dc1= {0,1,1,1,0,-1,-1,-1};
	public static int[] dr2= {-1,0,1,0};
	public static int[] dc2= {0,1,0,-1};
	
	static class Santa{
		int num,r,c,t,p;
		boolean dead;
		public Santa(int num, int r, int c) {
			this.num=num;
			this.r=r;
			this.c=c;
			this.t=0;
			this.p=0;
			this.dead=false;
		}
	}

}