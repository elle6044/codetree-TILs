import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N;
	static int[][] map;
	
	static Brush brush;

	public static void main(String[] args)throws Exception {
		N=Integer.parseInt(br.readLine());
		
		map=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		brush=new Brush(N/2,N/2,0);
		
		for(int i=1;i<N*N;i++) {
			moveBrush();
			changeBrush();
		}
		
		
		bw.write(answer+"");
		bw.close();
	}
	
	public static void changeBrush() {
		int r=brush.r;
		int c=brush.c;
		int d=brush.d;
		
		for(int i=1;i<=N/2;i++) {
			int j=i-1;
			if(r==i&&c==j) {
				brush.d=(d+1)%4;
			}
		}
		
		for(int i=0;i<N/2;i++) {
			int j=N-1-i;
			if(r==i&&c==j) {
				brush.d=(d+1)%4;
			}
		}
		
		for(int i=N/2+1;i<N;i++) {
			int j=N-1-i;
			if(r==i&&c==j) {
				brush.d=(d+1)%4;
			}
		}
		
		for(int i=N/2+1;i<N;i++) {
			int j=i;
			if(r==i&&c==j) {
				brush.d=(d+1)%4;
			}
		}
	}
	
	public static void moveBrush() {
		int sum=0;
		int nr=brush.r+dr[brush.d];
		int nc=brush.c+dc[brush.d];
		int d=brush.d;
		
		double all=map[nr][nc];
		map[nr][nc]=0;
		
		sum+=plus(nr+2*dr[d],nc+2*dc[d],(int)(all/100*5));
		
		sum+=plus(nr+dr[d]+dr[(d+1)%4],nc+dc[d]+dc[(d+1)%4],(int)(all/100*10));
		sum+=plus(nr+dr[d]+dr[(d+3)%4],nc+dc[d]+dc[(d+3)%4],(int)(all/100*10));
		
		sum+=plus(nr+dr[(d+1)%4],nc+dc[(d+1)%4],(int)(all/100*7));
		sum+=plus(nr+dr[(d+3)%4],nc+dc[(d+3)%4],(int)(all/100*7));
		
		sum+=plus(nr+2*dr[(d+1)%4],nc+2*dc[(d+1)%4],(int)(all/100*2));
		sum+=plus(nr+2*dr[(d+3)%4],nc+2*dc[(d+3)%4],(int)(all/100*2));
		
		sum+=plus(brush.r+dr[(d+1)%4],brush.c+dc[(d+1)%4],(int)(all/100*1));
		sum+=plus(brush.r+dr[(d+3)%4],brush.c+dc[(d+3)%4],(int)(all/100*1));
		
		sum+=plus(nr+dr[d],nc+dc[d],(int)all-sum);
		
		brush.r=nr;
		brush.c=nc;
	}
	
	static int answer=0;
	
	public static int plus(int r, int c, int cnt) {
		if(r>=0&&r<N&&c>=0&&c<N) {
			map[r][c]+=cnt;
		}
		else {
			answer+=cnt;
		}
		return cnt;
	}

	static int[] dr= {0,1,0,-1};
	static int[] dc= {-1,0,1,0};
	
	static class Brush{
		int r,c,d;
		public Brush(int r, int c, int d) {
			this.r=r;
			this.c=c;
			this.d=d;
		}
	}
	
}