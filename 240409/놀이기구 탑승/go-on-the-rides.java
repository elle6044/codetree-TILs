import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N;
	static int[][] map;

	static Man[] mans;
	
	static int[] score= {0,1,10,100,1000};
	
	public static void main(String[] args) throws Exception {
		N=Integer.parseInt(br.readLine());
		
		map=new int[N][N];
		mans=new Man[N*N+1];
		Queue<Integer> q=new ArrayDeque();
		for(int i=1;i<=N*N;i++) {
			st=new StringTokenizer(br.readLine());
			int num=Integer.parseInt(st.nextToken());
			int a=Integer.parseInt(st.nextToken());
			int b=Integer.parseInt(st.nextToken());
			int c=Integer.parseInt(st.nextToken());
			int d=Integer.parseInt(st.nextToken());
			mans[num]=new Man(num,a,b,c,d);
			q.offer(num);
		}
		
		
		for(int i=1;i<=N*N;i++) {
			Man m=mans[q.poll()];
			moveMan(m);
		}

		int answer=getAnswer();
		
		bw.write(answer+"");
		bw.close();
	}
	
	public static int getAnswer() {
		int sum=0;
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				int num=map[i][j];
				Set<Integer>set=mans[num].set;
				int cnt=0;
				
				for(int d=0;d<4;d++) {
					int nr=i+dr[d];
					int nc=j+dc[d];
					if(nr>=0&&nr<N&&nc>=0&&nc<N&&
							set.contains(map[nr][nc])) {
						cnt++;
					}
				}
				
				sum+=score[cnt];
			}
		}
		return sum;
	}
	
	public static void moveMan(Man man) {
		int num=man.num;
		Set<Integer> set=man.set;
		
		PriorityQueue<Point> pq=new PriorityQueue();
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j]==0) {
					int fCnt=0;
					int bCnt=0;
					
					for(int d=0;d<4;d++) {
						int nr=i+dr[d];
						int nc=j+dc[d];
						if(nr>=0&&nr<N&&nc>=0&&nc<N) {
							if(map[nr][nc]==0) {
								bCnt++;
							}
							else {
								if(set.contains(map[nr][nc])) {
									fCnt++;
								}
							}
						}
					}
					
					pq.offer(new Point(fCnt,bCnt,i,j));
				}
			}
		}
		Point p=pq.poll();
		map[p.r][p.c]=num;
	}
	
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};
	
	static class Man{
		int num;
		Set<Integer> set=new HashSet();
		public Man(int num, int a, int b, int c, int d) {
			this.num=num;
			set.add(a);
			set.add(b);
			set.add(c);
			set.add(d);
		}
	}
	
	static class Point implements Comparable<Point>{
		int fCnt, bCnt, r, c;
		public Point(int fCnt, int bCnt, int r, int c) {
			this.fCnt=fCnt;
			this.bCnt=bCnt;
			this.r=r;
			this.c=c;
		}
		@Override
		public int compareTo(Point o) {
			return this.fCnt==o.fCnt?this.bCnt==o.bCnt?this.r==o.r?this.c-o.c:this.r-o.r:o.bCnt-this.bCnt:o.fCnt-this.fCnt;
		}
	}
}