import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K,C;
	
	static int[][] map;
	static int[][] v;
	
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		C=Integer.parseInt(st.nextToken())+1;
		
		map=new int[N][N];
		v=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		int answer=0;
		for(int m=0;m<M;m++) {
			grow();
			spread();
			answer+=kill();
			remove();
		}
		
//		for(int i=0;i<N;i++) {
//			System.out.println(Arrays.toString(map[i]));
//		}
		
		bw.write(answer+"");
		bw.close();
	}
	
	public static void remove() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(v[i][j]>0) {
					v[i][j]--;
				}
			}
		}
	}
	
	public static int kill() {
		int[] location=find();
		int r=location[0];
		int c=location[1];
		
		int sum=map[r][c];
		map[r][c]=0;
		v[r][c]=C;
		
		for(int d=0;d<4;d++) {
			int nr=r;
			int nc=c;
			for(int k=1;k<=K;k++) {
				nr+=kdr[d];
				nc+=kdc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N) {
					if(map[nr][nc]>0) {
						sum+=map[nr][nc];
						map[nr][nc]=0;
						v[nr][nc]=C;
					}
					else {
						v[nr][nc]=C;
						break;
					}
				}
			}
		}
		return sum;
	}
	
	public static int[] find() {
		int[] location=new int[2];
		int max=0;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j]>0) {
					int cnt=countKill(i,j);
					if(max<cnt) {
						max=cnt;
						location[0]=i;
						location[1]=j;
					}
				}
			}
		}
		return location;
	}
	
	public static int countKill(int r, int c) {
		int sum=map[r][c];
		for(int d=0;d<4;d++) {
			int nr=r;
			int nc=c;
			for(int k=1;k<=K;k++) {
				nr+=kdr[d];
				nc+=kdc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N) {
					if(map[nr][nc]>0) {
						sum+=map[nr][nc];
					}
					else {
						break;
					}
				}
			}
		}
		
		return sum;
	}
	
	public static void spread() {
		Queue<Tree> q=new ArrayDeque();
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j]>0) {
					q.offer(new Tree(i,j,map[i][j]));
				}
			}
		}
		int size=q.size();
		for(int i=0;i<size;i++) {
			Tree t=q.poll();
			int cnt=0;
			for(int d=0;d<4;d++) {
				int nr=t.r+tdr[d];
				int nc=t.c+tdc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]==0&&v[nr][nc]==0) {
					cnt++;
				}
			}
			if(cnt==0) continue;
			int num=t.cnt/cnt;
			for(int d=0;d<4;d++) {
				int nr=t.r+tdr[d];
				int nc=t.c+tdc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]==0&&v[nr][nc]==0) {
					q.offer(new Tree(nr,nc,num));
				}
			}
		}
		
		while(!q.isEmpty()) {
			Tree t=q.poll();
			map[t.r][t.c]+=t.cnt;
		}
	}
	
	public static void grow() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j]>0) {
					map[i][j]+=count(i,j);
				}
			}
		}
	}
	
	public static int count(int r, int c) {
		int cnt=0;
		for(int d=0;d<4;d++) {
			int nr=r+tdr[d];
			int nc=c+tdc[d];
			if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]>0) {
				cnt++;
			}
		}
		return cnt;
	}
	
	static int[] tdr= {1,-1,0,0};
	static int[] tdc= {0,0,1,-1};
	
	static class Tree{
		int r,c,cnt;
		public Tree(int r, int c, int cnt) {
			this.r=r;
			this.c=c;
			this.cnt=cnt;
		}
	}
	
	static int[] kdr= {-1,-1,1,1};
	static int[] kdc= {-1,1,1,-1};
	
	static class Kill{
		int r,c;
		public Kill(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
	
	

}