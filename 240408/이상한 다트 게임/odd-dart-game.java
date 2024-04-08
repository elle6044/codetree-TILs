import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,Q;
	static int[][] map;
	static boolean[][]v;

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		Q=Integer.parseInt(st.nextToken());
		
		map=new int[N][M];
		
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		for(int q=0;q<Q;q++) {
			st=new StringTokenizer(br.readLine());
			int x=Integer.parseInt(st.nextToken());
			int d=Integer.parseInt(st.nextToken());
			int k=Integer.parseInt(st.nextToken());
			
			rotation(x,d,k);

			
			remove();
		
			
		}
		
		int answer=0;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(map[i][j]!=-1) {
					answer+=map[i][j];
				}
			}
		}
		
		bw.write(answer+"");
		bw.close();
	}
	
	public static void remove() {
		boolean check=false;
		v=new boolean[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(!v[i][j]&&map[i][j]!=-1) {
					if(bfs(i,j,map[i][j])) {
						map[i][j]=-1;
						check=true;
					}
				}
			}
		}
		
		if(!check) {
			regul();
		}
	}
	
	public static void regul() {
		int sum=0;
		int cnt=0;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(map[i][j]!=-1) {
					sum+=map[i][j];
					cnt++;
				}
			}
		}
		if(cnt==0) return;
		int avg=sum/cnt;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(map[i][j]!=-1) {
					if(map[i][j]>avg) {
						map[i][j]--;
					}
					else if(map[i][j]<avg) {
						map[i][j]++;
					}
				}
			}
		}
	}
	
	public static boolean bfs(int r, int c, int num) {
		boolean check=false;
		Queue<Point>q=new ArrayDeque();
		q.offer(new Point(r,c));
		v[r][c]=true;
		
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nc==-1) {
					nc=M-1;
				}
				else if(nc==M) {
					nc=0;
				}
				
				if(nr>=0&&nr<N&&!v[nr][nc]&&map[nr][nc]==num) {
					v[nr][nc]=true;
					map[nr][nc]=-1;
					q.offer(new Point(nr,nc));
					check=true;
				}
			}
		}
		
		return check;
	}
	
	public static void rotation(int x, int d, int k) {
		for(int i=0;i<N;i++) {
			if((i+1)%x!=0) continue;
			
			for(int kk=0;kk<k;kk++) {
				if(d==0) {
					int temp=map[i][M-1];
					for(int j=M-1;j>0;j--) {
						map[i][j]=map[i][j-1];
					}
					map[i][0]=temp;
				}
				else {
					int temp=map[i][0];
					for(int j=0;j<M-1;j++) {
						map[i][j]=map[i][j+1];
					}
					map[i][M-1]=temp;
				}
			}
		}
	}

	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};
	static class Point{
		int r,c;
		public Point(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
}