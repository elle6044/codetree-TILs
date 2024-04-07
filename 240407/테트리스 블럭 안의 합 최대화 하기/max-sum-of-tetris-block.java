import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M;
	static int[][] map;
	
	static int maxNum=0;
	
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		
		map=new int[N][M];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				int input=Integer.parseInt(st.nextToken());
				map[i][j]=input;
				maxNum=Math.max(maxNum, input);
			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				back(i,j,1,0,map[i][j]);
			}
		}

		bw.write(answer+"");
		bw.close();
	}
	
	static int[] dr= {1,0,-1,0};
	static int[] dc= {0,1,0,-1};
	static int[] plus= {1,-1};
	static int answer=0;
	public static void back(int r, int c, int cnt, int dir, int sum) {
		if(cnt==4) {
			answer=Math.max(answer, sum);
			return;
		}
		
		for(int d=0;d<4;d++) {
			if(Math.abs(d-dir)==2) continue;
			
			int nr=r+dr[d];
			int nc=c+dc[d];
			if(nr>=0&&nr<N&&nc>=0&&nc<M) {
				back(nr,nc,cnt+1,d,sum+map[nr][nc]);
				
				if(cnt==2&&d==dir) {
					int d2=d==0?3:d-1;
					int nr2=r+dr[d2];
					int nc2=c+dc[d2];
					if(nr2>=0&&nr2<N&&nc2>=0&&nc2<M) {
						back(nr,nc,cnt+2,d,sum+map[nr][nc]+map[nr2][nc2]);
					}
					d2=d==3?0:d+1;
					nr2=r+dr[d2];
					nc2=c+dc[d2];
					if(nr2>=0&&nr2<N&&nc2>=0&&nc2<M) {
						back(nr,nc,cnt+2,d,sum+map[nr][nc]+map[nr2][nc2]);
					}
				}
			}
		}
	}

}