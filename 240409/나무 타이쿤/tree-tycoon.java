import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M;
	static int[][] map;
	static boolean[][] v;
	
//	static ArrayList<Food> foods=new ArrayList();
	static Queue<Food> foods=new ArrayDeque();
	
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		
		map=new int[N][N];
		v=new boolean[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=1;i<=2;i++) {
			for(int j=0;j<=1;j++) {
				foods.add(new Food(N-i,j));
			}
		}
		
		for(int m=0;m<M;m++) {
			st=new StringTokenizer(br.readLine());
			int d=Integer.parseInt(st.nextToken())-1;
			int p=Integer.parseInt(st.nextToken());
			
			moveFood(d,p);
			
			eatFood();
			
			growTree();
			
			addFood();
			
			v=new boolean[N][N];
		}
		
		int answer=getAnswer();
		bw.write(answer+"");
		bw.close();
	}
	
	public static int getAnswer() {
		int sum=0;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				sum+=map[i][j];
			}
		}
		
		return sum;
	}
	
	public static void addFood() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j]>=2&&!v[i][j]) {
					map[i][j]-=2;
					foods.offer(new Food(i,j));
				}
			}
		}
	}
	
	public static void growTree() {
		int size=foods.size();
		for(int s=0;s<size;s++) {
			Food f=foods.poll();
			v[f.r][f.c]=true;
			for(int d=1;d<8;d+=2) {
				int nr=f.r+dr[d];
				int nc=f.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&map[nr][nc]>=1) {
					map[f.r][f.c]++;
				}
			}
		}
	}
	
	public static void eatFood() {
		for(Food f:foods) {
			map[f.r][f.c]++;
		}
	}
	
	public static void moveFood(int d, int p) {
		for(Food f:foods) {
			int nr=f.r;
			int nc=f.c;
			for(int i=0;i<p;i++) {
				nr+=dr[d];
				if(nr<0) {
					nr=N-1;
				}
				else if(nr>=N) {
					nr=0;
				}
				nc+=dc[d];
				if(nc<0) {
					nc=N-1;
				}
				else if(nc>=N) {
					nc=0;
				}
			}
			
			f.r=nr;
			f.c=nc;
		}
	}
	
	static int[] dr= {0,-1,-1,-1,0,1,1,1};
	static int[] dc= {1,1,0,-1,-1,-1,0,1};
	
	static class Food{
		int r,c;
		public Food(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}

}