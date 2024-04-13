import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader Br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter Bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M;
	
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(Br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		
		
		int rr=0;
		int rc=0;
		int br=0;
		int bc=0;
		
		char[][] map=new char[N][M];
		for(int i=0;i<N;i++) {
			String input=Br.readLine();
			for(int j=0;j<M;j++) {
				char word=input.charAt(j);
				map[i][j]=word;
				if(word=='R') {
					rr=i;
					rc=j;
				}
				if(word=='B') {
					br=i;
					bc=j;
				}
			}
		}
		
		back(0,rr,rc,br,bc,map);
		
		if(answer==Integer.MAX_VALUE) {
			answer=-1;
		}
		Bw.write(answer+"");
		Bw.close();
	}
	
	
	static void back(int cnt, int rr, int rc, int br, int bc, char[][] map) {
		if(cnt>10) {
			return;
		}
		
		char[][] tmap=new char[N][M];
		for(int d=0;d<4;d++) {
			if(map[rr+dr[d]][rc+dc[d]]=='#'&&map[br+dr[d]][bc+dc[d]]=='#') {
				continue;
			}
			
			for(int i=0;i<N;i++) {
				for(int j=0;j<M;j++) {
					tmap[i][j]=map[i][j];
				}
			}
			
			int [] rl=new int[2];
			int [] bl=new int[2];
			if(d==0) {
				if(rr>br) {
					rl=moveBall(d,rr,rc,tmap);
					bl=moveBall(d,br,bc,tmap);
				}
				else {
					bl=moveBall(d,br,bc,tmap);
					rl=moveBall(d,rr,rc,tmap);
				}
			}
			else if(d==1) {
				if(rr<br) {
					rl=moveBall(d,rr,rc,tmap);
					bl=moveBall(d,br,bc,tmap);
				}
				else {
					bl=moveBall(d,br,bc,tmap);
					rl=moveBall(d,rr,rc,tmap);
				}
			}
			else if(d==2) {
				if(rc>bc) {
					rl=moveBall(d,rr,rc,tmap);
					bl=moveBall(d,br,bc,tmap);
				}
				else {
					bl=moveBall(d,br,bc,tmap);
					rl=moveBall(d,rr,rc,tmap);
				}
			}
			else if(d==3) {
				if(rc<bc) {
					rl=moveBall(d,rr,rc,tmap);
					bl=moveBall(d,br,bc,tmap);
				}
				else {
					bl=moveBall(d,br,bc,tmap);
					rl=moveBall(d,rr,rc,tmap);
				}
			}
			
			if(tmap[bl[0]][bl[1]]=='O') {
				continue;
			}
			else {
				if(tmap[rl[0]][rl[1]]=='O') {
					answer=Math.min(answer, cnt+1);
					return;
				}
			}
			
			back(cnt+1,rl[0],rl[1],bl[0],bl[1],tmap);
		}
	}
	
	static int answer=Integer.MAX_VALUE;
	
	static int[] moveBall(int dir, int r, int c, char[][] map) {
		int[] answer=new int[2];
		char ball=map[r][c];
		int nr=r;
		int nc=c;
		while(true) {
			nr+=dr[dir];
			nc+=dc[dir];
			if(map[nr][nc]=='#'||map[nr][nc]=='R'||map[nr][nc]=='B') {
				nr-=dr[dir];
				nc-=dc[dir];
				break;
			}
			else if(map[nr][nc]=='O') {
				break;
			}
		}
		answer[0]=nr;
		answer[1]=nc;
		map[r][c]='.';
		if(map[nr][nc]=='.') {
			map[nr][nc]=ball;
		}
		
		return answer;
	}
	
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};
	
	static class Ball{
		int r,c;
		public Ball(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}

}