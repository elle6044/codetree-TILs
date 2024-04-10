import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	static int[][] map;
	static boolean[][] v;
	
	static PriorityQueue<Canon> canons=new PriorityQueue();
	

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		map=new int[N][M];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				int input=Integer.parseInt(st.nextToken());
				map[i][j]=input;
				if(input>0) {
					canons.offer(new Canon(i,j,input));
				}
			}
		}
		
		
		for(int k=1;k<=K;k++) {
			play(k);
			
			if(isEnd()) {
				break;
			}
			
//			for(int i=0;i<N;i++) {
//				System.out.println(Arrays.toString(map[i]));
//			}
		}
		
		int answer=getAnswer();
		
		
		bw.write(answer+"");
		bw.close();
	}
	
	public static boolean isEnd() {
		if(canons.size()==1) {
			return true;
		}
		return false;
	}
	
	public static int getAnswer() {
		int max=0;
		for(Canon c:canons) {
			max=Math.max(max, c.p);
		}
		return max;
	}
	
	public static void play(int time) {
		Canon a=null;
		Canon d=null;
		List<Canon> temp=new ArrayList();
		int size=canons.size();
		for(int i=0;i<size;i++) {
			Canon c=canons.poll();
			if(i==0) {
				a=c;
			}
			else if(i==size-1) {
				d=c;
			}
			temp.add(c);
		}
		canons.addAll(temp);
		
		a.p+=N+M;
		a.t=time;
		
		attack(a,d);
	}
	
	public static void attack(Canon a, Canon d) {
		if(!lazer(a,d)) {
			boom(a,d);
		}
		
		check();
	}
	
	public static void check() {
		List<Canon> temp=new ArrayList();
		while(!canons.isEmpty()) {
			Canon c=canons.poll();
			if(c.p<=0) {
				map[c.r][c.c]=0;
			}
			else {
				map[c.r][c.c]=c.p;
				temp.add(c);
			}
		}
		canons.addAll(temp);
	}
	
	public static void boom(Canon attack, Canon defence) {
		int r=defence.r;
		int c=defence.c;
		
		
		Set<Integer> set=new HashSet();
		for(int d=0;d<8;d++) {
			int nr=(r+bdr[d]+N)%N;
			int nc=(c+bdc[d]+M)%M;
			if(!(nr==attack.r&&nc==attack.c)) {
				if(map[nr][nc]>0) {
					set.add(nr*M+nc);
				}
			}
		}
		
		defence.p-=attack.p;
		for(Canon canon:canons) {
			int idx=canon.r*M+canon.c;
			if(set.contains(idx)) {
				canon.p-=attack.p/2;
			}
			else {
				if(idx!=r*M+c&&idx!=attack.r*M+attack.c) {
					canon.p++;
					map[canon.r][canon.c]++;
				}
			}
		}
	}
	
	public static boolean lazer(Canon attack, Canon defence) {
		v=new boolean[N][M];
		int r=attack.r;
		int c=attack.c;
		Queue<Point> q=new ArrayDeque();
		q.offer(new Point(r,c));
		v[r][c]=true;
		while(!q.isEmpty()) {
			Point p=q.poll();
			if(p.r==defence.r&&p.c==defence.c) {
				defence.p-=attack.p;
				p.s.pop();
				
				for(Canon canon:canons) {
					
					int idx=canon.r*M+canon.c;
					if(p.s.contains(idx)) {
						canon.p-=attack.p/2;
					}
					else {
						if(idx!=r*M+c&&idx!=defence.r*M+defence.c) {
							canon.p++;
							map[canon.r][canon.c]++;
						}
					}
				}
				
				return true;
			}
			
			for(int d=0;d<4;d++) {
				int nr=(p.r+dr[d]+N)%N;
				int nc=(p.c+dc[d]+M)%M;
				
				if(map[nr][nc]>0&&!v[nr][nc]) {
					Point np=new Point(nr,nc);
					np.s.addAll(p.s);
					np.s.push(nr*M+nc);
					q.offer(np);
					v[nr][nc]=true;
				}
			}
		}
		return false;
	}

	static int[] dr= {0,1,0,-1};
	static int[] dc= {1,0,-1,0};
	
	static int[] bdr= {-1,-1,0,1,1,1,0,-1};
	static int[] bdc= {0,1,1,1,0,-1,-1,-1};
	
	static class Canon implements Comparable<Canon>{
		int r,c,p,t;
		public Canon(int r, int c, int p) {
			this.r=r;
			this.c=c;
			this.p=p;
			this.t=0;
		}
		@Override
		public int compareTo(Canon o) {
			return this.p==o.p?this.t==o.t?(this.r+this.c)==(o.r+o.c)?o.c-this.c:(o.r+o.c)-(this.r+this.c):o.t-this.t:this.p-o.p;
		}
	}
	
	static class Point{
		int r,c;
		Stack<Integer> s;
		public Point(int r, int c) {
			this.r=r;
			this.c=c;
			this.s=new Stack();
		}
	}
}