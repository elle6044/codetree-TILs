import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int L,N,Q;
	static int[][]map;
	static int[][]mapK;
	static boolean[][]v;
	
	static Knight[] knights;
	

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		L=Integer.parseInt(st.nextToken());
		N=Integer.parseInt(st.nextToken());
		Q=Integer.parseInt(st.nextToken());
		
		map=new int[L][L];
		mapK=new int[L][L];
		for(int i=0;i<L;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<L;j++) {
				int input=Integer.parseInt(st.nextToken());
				map[i][j]=input;
			}
		}
		
		knights=new Knight[N+1];
		
		for(int n=1;n<=N;n++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			int h=Integer.parseInt(st.nextToken());
			int w=Integer.parseInt(st.nextToken());
			int k=Integer.parseInt(st.nextToken());
			
			knights[n]=new Knight(r,c,h,w,k);
			for(int i=r;i<r+h;i++) {
				for(int j=c;j<c+w;j++) {
					mapK[i][j]=n;
				}
			}
		}
		
		
		
		for(int q=0;q<Q;q++) {
			st=new StringTokenizer(br.readLine());
			int i=Integer.parseInt(st.nextToken());
			int d=Integer.parseInt(st.nextToken());
			
			if(knights[i].dead) continue;
			move(i,d);
			
			save();
			
//			for(int j=0;j<L;j++) {
//				System.out.println(Arrays.toString(mapK[j]));
//			}
		}
		int answer=getAnswer();
		
		bw.write(answer+"");
		bw.close();
	}
	
	public static int getAnswer() {
		int sum=0;
		for(int i=1;i<=N;i++) {
			Knight k=knights[i];
			if(!k.dead) {
				sum+=k.d;
			}
		}

		return sum;
	}
	
	public static void save() {
		mapK=new int[L][L];
		
		for(int idx=1;idx<=N;idx++) {
			Knight k=knights[idx];
			
			if(k.k<=0) {
				k.dead=true;
			}
			else {
				for(int i=k.r1;i<=k.r2;i++) {
					for(int j=k.c1;j<=k.c2;j++) {
						mapK[i][j]=idx;
					}
				}
			}
		}
	}
	
	public static void move(int idx, int dir) {
		Knight knight=knights[idx];
		Set<Integer> set=canMove(idx,dir);
		if(set!=null) {
			knight.r1+=dr[dir];
			knight.r2+=dr[dir];
			knight.c1+=dc[dir];
			knight.c2+=dc[dir];
			
			for(Integer num:set) {
				Knight k=knights[num];
				
				for(int i=k.r1;i<=k.r2;i++) {
					int nr=i+dr[dir];
					for(int j=k.c1;j<=k.c2;j++) {
						int nc=j+dc[dir];
						if(mapK[nr][nc]!=idx&&map[nr][nc]==1) {
							k.k--;
							k.d++;
						}
					}
				}
				
				k.r1+=dr[dir];
				k.r2+=dr[dir];
				k.c1+=dc[dir];
				k.c2+=dc[dir];
			}
		}
	}
	
	public static Set<Integer> canMove(int idx, int dir) {
		Set<Integer> set=new HashSet();
		
		Knight knight=knights[idx];
		int r=knight.r1;
		int c=knight.c1;
		v=new boolean[L][L];
		Queue<Point> q=new ArrayDeque();
		q.offer(new Point(r,c,idx));
		v[r][c]=true;
		
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				
				if(nr>=0&&nr<L&&nc>=0&&nc<L) {
					if(!v[nr][nc]) {
						if(mapK[nr][nc]==p.num) {
							q.offer(new Point(nr,nc,p.num));
							v[nr][nc]=true;
						}
						else {
							if(mapK[nr][nc]!=0) {
								if(d==dir) {
									q.offer(new Point(nr,nc,mapK[nr][nc]));
									v[nr][nc]=true;
									set.add(mapK[nr][nc]);
								}
							}
							else {
								if(d==dir&&map[nr][nc]==2) {
									return null;
								}
							}
						}
					}
				}
				else {
					if(d==dir) {
						return null;
					}
				}
			}
		}
		
		return set;
	}
	
	static int[] dr= {-1,0,1,0};
	static int[] dc= {0,1,0,-1};
	
	static class Knight{
		int r1,c1,r2,c2,k,d;
		boolean dead;
		public Knight(int r, int c, int h, int w, int k) {
			this.r1=r;
			this.c1=c;
			this.r2=r+h-1;
			this.c2=c+w-1;
			this.k=k;
			this.dead=false;
			this.d=0;
		}
	}
	
	static class Point{
		int r,c,num;
		public Point(int r, int c, int num) {
			this.r=r;
			this.c=c;
			this.num=num;
		}
	}

}