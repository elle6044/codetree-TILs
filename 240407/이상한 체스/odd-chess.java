import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M;
	static int[][] map;
	
	static ArrayList<Horse>horses=new ArrayList();
	static int horseCnt;
	
	static int[][][] dr= {{{-1},{0},{1},{0}},
			{{-1,1},{0,0}},
			{{-1,0},{0,1},{1,0},{0,-1}},
			{{0,-1,0},{-1,0,1},{0,1,0},{1,0,-1}},
			{{-1,0,1,0}}};
	static int[][][] dc= {{{0},{1},{0},{-1}},
			{{0,0},{1,-1}},
			{{0,1},{1,0},{0,-1},{-1,0}},
			{{-1,0,1},{0,1,0},{1,0,-1},{0,-1,0}},
			{{0,1,0,-1}}};
	public static void main(String[] args) throws Exception {
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		
		map=new int[N][M];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				int input=Integer.parseInt(st.nextToken());
				map[i][j]=input;
				if(input!=0&&input!=6) {
					horses.add(new Horse(i,j,input-1));
				}
				if(input==6) {
					map[i][j]=-1;
				}
			}
		}
		horseCnt=horses.size();

		back(0);
		
		bw.write(answer+"");
		bw.close();
		
	}
	
	static int answer=Integer.MAX_VALUE;
	public static void back(int cnt) {
		if(cnt==horseCnt) {
			int sum=0;
			for(int i=0;i<N;i++) {
				for(int j=0;j<M;j++) {
					if(map[i][j]==0) {
						sum++;
					}
				}
			}
			answer=Math.min(answer, sum);
			return;
		}
		
		Horse h=horses.get(cnt);
		for(int d1=0;d1<dr[h.type].length;d1++) {
			for(int d2=0;d2<dr[h.type][d1].length;d2++) {
				int nr=h.r;
				int nc=h.c;
				while(true) {
					if(nr<0||nr>=N||nc<0||nc>=M||map[nr][nc]==-1) {
						break;
					}
					
					map[nr][nc]++;
					nr+=dr[h.type][d1][d2];
					nc+=dc[h.type][d1][d2];
				}
			}
			
			back(cnt+1);
			
			for(int d2=0;d2<dr[h.type][d1].length;d2++) {
				int nr=h.r;
				int nc=h.c;
				
				while(true) {
					if(nr<0||nr>=N||nc<0||nc>=M||map[nr][nc]==-1) {
						break;
					}
					
					map[nr][nc]--;
					nr+=dr[h.type][d1][d2];
					nc+=dc[h.type][d1][d2];
				}
			}
		}
	}
	
	static class Horse{
		int r,c,type;
		public Horse(int r, int c, int type) {
			this.r=r;
			this.c=c;
			this.type=type;
		}
	}

}