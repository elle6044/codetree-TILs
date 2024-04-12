import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	static int[][] map;
	
	static Map<Integer, Target> targets=new TreeMap();

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		map=new int[N][M];
		for(int i=0;i<K;i++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			int s=Integer.parseInt(st.nextToken());
			int d=Integer.parseInt(st.nextToken())-1;
			int b=Integer.parseInt(st.nextToken());
			targets.put(b, new Target(r,c,s,d,b));
			map[r][c]=b;
		}
		
		int answer=0;
		for(int m=0;m<M;m++) {
			answer+=killTarget(m);
			
			moveTarget();
			
//			break;
		}
//		for(int i=0;i<N;i++) {
//			System.out.println(Arrays.toString(map[i]));
//		}
		
		bw.write(answer+"");
		bw.close();
		
	}
	
	static void moveTarget() {
		for(Integer num:targets.keySet()) {
			Target t=targets.get(num);
			if(t.dead) continue;
			
			int r=t.r;
			int c=t.c;
			for(int i=1;i<=t.s;i++) {
				int nr=r+dr[t.d];
				int nc=c+dc[t.d];
				if(nr>=0&&nr<N&&nc>=0&&nc<M) {
					r=nr;
					c=nc;
				}
				else {
					t.d=t.d%2==0?t.d+1:t.d-1;
					r+=dr[t.d];
					c+=dc[t.d];
				}
			}
			map[t.r][t.c]=0;
			
			if(map[r][c]>0) {
				targets.get(map[r][c]).dead=true;
			}
			map[r][c]=num;
			t.r=r;
			t.c=c;
		}
	}
	
	static int killTarget(int m) {
		int score=0;
		for(int i=0;i<N;i++) {
			if(map[i][m]!=0) {
				int num=map[i][m];
				map[i][m]=0;
				
				Target t=targets.get(num);
				score+=t.b;
				t.dead=true;
				break;
			}
		}
		
		return score;
	}
	
	static class Target{
		int r,c,s,d,b;
		boolean dead;
		public Target(int r, int c, int s, int d, int b) {
			this.r = r;
			this.c = c;
			this.s = s;
			this.d = d;
			this.b = b;
			this.dead=false;
		}
	}
	
	static int[] dr= {-1,1,0,0};
	static int[] dc= {0,0,1,-1};

}