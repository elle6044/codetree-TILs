import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int K,M;
	static int[][] map=new int[5][5];
	static boolean[][] v;
	static Queue<Integer> readyNum=new ArrayDeque();
	

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		K=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		
		map=new int[5][5];
		for(int i=0;i<5;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<5;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		st=new StringTokenizer(br.readLine());
		for(int i=0;i<M;i++) {
			readyNum.offer(Integer.parseInt(st.nextToken()));
		}
		for(int k=0;k<K;k++) {
			if(!rotateMap()) {
				break;
			}
			
			int score=0;
			while(true) {
				int temp=getArt();
				if(temp==0) break;
				fillMap();
				score+=temp;
			}
			bw.write(score+" ");
		}
		bw.close();
	}
	
	static void fillMap() {
		for(int j=0;j<5;j++) {
			for(int i=4;i>=0;i--) {
				if(map[i][j]==0) {
					map[i][j]=readyNum.poll();
				}
			}
		}
	}
	
	static int getArt() {
		v=new boolean[5][5];
		int score=0;
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				if(!v[i][j]) {
					score+=getArtScore(i,j,map[i][j]);
				}
			}
		}
		return score;
	}
	
	static int getArtScore(int r, int c, int num) {
		int score=1;
		Queue<Point> q=new ArrayDeque();
		Set<Integer> set=new HashSet();
		v[r][c]=true;
		q.offer(new Point(r,c));
		set.add(r*5+c);
		
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<5&&nc>=0&&nc<5&&!v[nr][nc]&&map[nr][nc]==num) {
					q.offer(new Point(nr,nc));
					v[nr][nc]=true;
					set.add(nr*5+nc);
					score++;
				}
			}
		}
		if(score>=3) {
			for(int a:set) {
				map[a/5][a%5]=0;
			}
			return score;
		}
		else {
			return 0;
		}
	}
	
	static boolean rotateMap() {
		PriorityQueue<Score> pq=new PriorityQueue();
		int[][] temp=new int[3][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				for(int d=1;d<=3;d++) {
					for(int i2=0;i2<3;i2++) {
						for(int j2=0;j2<3;j2++) {
							temp[j2][3-1-i2]=map[i+i2][j+j2];
						}
					}
					for(int i2=0;i2<3;i2++) {
						for(int j2=0;j2<3;j2++) {
							map[i+i2][j+j2]=temp[i2][j2];
						}
					}
					
					int score=0;
					v=new boolean[5][5];
					for(int i2=0;i2<5;i2++) {
						for(int j2=0;j2<5;j2++) {
							if(!v[i2][j2]) {
								score+=getScore(i2,j2,map[i2][j2]);
							}
						}
					}
					if(score>0) {
						pq.offer(new Score(score,d,i,j));
					}
				}
				
				for(int i2=0;i2<3;i2++) {
					for(int j2=0;j2<3;j2++) {
						temp[j2][3-1-i2]=map[i+i2][j+j2];
					}
				}
				for(int i2=0;i2<3;i2++) {
					for(int j2=0;j2<3;j2++) {
						map[i+i2][j+j2]=temp[i2][j2];
					}
				}
			}
		}
		
		if(pq.isEmpty()) {
			return false;
		}
		else {
			Score max=pq.poll();
			for(int d=0;d<max.d;d++) {
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						temp[j][3-1-i]=map[max.r+i][max.c+j];
					}
				}
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						map[max.r+i][max.c+j]=temp[i][j];
					}
				}
			}
			return true;
		}
	}
	
	static int getScore(int r, int c, int num) {
		int score=1;
		Queue<Point> q=new ArrayDeque();
		v[r][c]=true;
		q.offer(new Point(r,c));
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<5&&nc>=0&&nc<5&&!v[nr][nc]&&map[nr][nc]==num) {
					q.offer(new Point(nr,nc));
					v[nr][nc]=true;
					score++;
				}
			}
		}
		if(score>=3) {
			return score;
		}
		else {
			return 0;
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
	
	static class Score implements Comparable<Score>{
		int score,d,r,c;
		public Score(int score, int d, int r, int c) {
			this.score=score;
			this.d=d;
			this.r=r;
			this.c=c;
		}
		@Override
		public int compareTo(Score o) {
			return this.score==o.score?this.d==o.d?this.c==o.c?this.r-o.r:this.c-o.c:this.d-o.d:o.score-this.score;
		}
		
	}

}