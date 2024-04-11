import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	

	public static void main(String[] args) throws Exception {
		Man[] mans=new Man[17];
		int[][] map=new int[4][4];
		
		for(int i=0;i<4;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<4;j++) {
				int num=Integer.parseInt(st.nextToken());
				int d=Integer.parseInt(st.nextToken())-1;
				mans[num]=new Man(num,i,j,d,false);
				map[i][j]=num;
			}
		}
		
		Man start=mans[map[0][0]];
		start.dead=true;
		Police police=new Police(0,0,start.d,map[0][0]);
		map[0][0]=-1;
		
		back(1, map, mans, police);
		
		bw.write(answer+"");
		bw.close();

	}
	
	static int answer=0;
	
	static void back(int cnt, int[][] map, Man[] mans, Police police) {
		
		
		int[][]tmap=new int[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				tmap[i][j]=map[i][j];
			}
		}
		
		Man[] tmans=new Man[17];
		for(int i=1;i<17;i++) {
			Man m=mans[i];
			tmans[i]=new Man(m.num, m.r, m.c, m.d, m.dead);
		}
		
		
		moveMan(tmap, tmans);
		
		
		for(int d=1;d<4;d++) {
			int nr=police.r+dr[police.d]*d;
			int nc=police.c+dc[police.d]*d;
			if(nr>=0&&nr<4&&nc>=0&&nc<4&&tmap[nr][nc]>0) {
				Police tpolice=new Police(police.r, police.c, police.d, police.p);
				
				Man target=tmans[tmap[nr][nc]];
				target.dead=true;
				tmap[nr][nc]=-1;
				tmap[tpolice.r][tpolice.c]=0;
				tpolice.r=nr;
				tpolice.c=nc;
				tpolice.d=target.d;
				tpolice.p+=target.num;
				
				back(cnt+1, tmap, tmans, tpolice);
			}
			answer=Math.max(answer, police.p);
		}
		
	}
	
	static void moveMan(int[][] tmap, Man[] tmans) {
		for(int m=1;m<17;m++) {
			Man man=tmans[m];
			if(man.dead) continue;
			
			for(int d=0;d<8;d++) {
				int nd=(man.d+d)%8;
				int nr=man.r+dr[nd];
				int nc=man.c+dc[nd];
				
				if(nr>=0&&nr<4&&nc>=0&&nc<4&&tmap[nr][nc]!=-1) {
					if(tmap[nr][nc]==0) {
						tmap[nr][nc]=man.num;
						tmap[man.r][man.c]=0;
					}
					else {
						Man nman=tmans[tmap[nr][nc]];

						tmap[nman.r][nman.c]=man.num;
						tmap[man.r][man.c]=nman.num;

						nman.r=man.r;
						nman.c=man.c;
					}
					man.r=nr;
					man.c=nc;
					man.d=nd;
					
					break;
				}
			}
		}
	}
	
	
	static int[] dr= {-1,-1,0,1,1,1,0,-1};
	static int[] dc= {0,-1,-1,-1,0,1,1,1};
	
	static class Man{
		int num,r,c,d;
		boolean dead;
		public Man(int num, int r, int c, int d, boolean dead) {
			this.num=num;
			this.r=r;
			this.c=c;
			this.d=d;
			this.dead=dead;
		}
	}
	
	static class Police{
		int r,c,d,p;
		public Police(int r, int c, int d, int p) {
			this.r=r;
			this.c=c;
			this.d=d;
			this.p=p;
		}
	}

}