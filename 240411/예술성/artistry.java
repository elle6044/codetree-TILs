import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N;
	static int[][] map;
	static int[][] gmap;
	static boolean[][] v;
	static int[][] lmap;
	static int[][] temp;
	

	public static void main(String[] args) throws Exception {
		N=Integer.parseInt(br.readLine());
		
		map=new int[N][N];
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		int answer=addScore();
		
		for(int i=0;i<3;i++) {
			rotation();
			int score=addScore();
			answer+=score;
		}
		bw.write(answer+"");
		bw.close();
	}
	
	static void rotation() {
		temp=new int[N][N];
		
		for(int i=0;i<N;i++) {
			temp[N/2][i]=map[i][N/2];
			temp[N-1-i][N/2]=map[N/2][i];
		}
		
		for(int i=0;i<N;i+=(N/2+1)) {
			for(int j=0;j<N;j+=(N/2+1)) {
				for(int i2=0;i2<N/2;i2++) {
					for(int j2=0;j2<N/2;j2++) {
						int r=i+i2;
						int c=j+j2;
						temp[i+N/2-1-(N/2-1-j2)][j+N/2-1-i2]=map[r][c];
					}
				}
			}
		}

		for(int i=0;i<N;i++) {
			map[i]=Arrays.copyOf(temp[i], N);
		}
	}
	
	static int addScore() {
		int sum=0;
		
		int groupSize=makeGroupMap();
		lmap=new int[groupSize+1][groupSize+1];
		
		List<Group> groups=getGroup();
		
		for(int i=0;i<groupSize;i++) {
			for(int j=i+1;j<groupSize;j++) {
				Group a=groups.get(i);
				Group b=groups.get(j);
				if(lmap[a.num][b.num]==0) continue;
				
				sum+=(a.cnt+b.cnt)*a.score*b.score*lmap[a.num][b.num];
			}
		}
		
		return sum;
	}
	
	static List<Group> getGroup() {
		List<Group> groups=new ArrayList();
		
		v=new boolean[N][N];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(!v[i][j]) {
					groups.add(bfsGroup(i,j));
				}
			}
		}
		
		return groups;
	}

	
	static Group bfsGroup(int r, int c) {
		Group g=new Group(gmap[r][c],map[r][c],1);
		Queue<Point> q=new ArrayDeque();
		q.offer(new Point(r,c));
		v[r][c]=true;
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&!v[nr][nc]) {
					if(g.num==gmap[nr][nc]) {
						q.offer(new Point(nr,nc));
						v[nr][nc]=true;
						g.cnt++;
					}
					else {
						lmap[g.num][gmap[nr][nc]]++;
						lmap[gmap[nr][nc]][g.num]++;
					}
				}
			}
		}
		
		
		return g;
	}
	
	static int makeGroupMap() {
		gmap=new int[N][N];
		v=new boolean[N][N];
		
		int num=1;
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(!v[i][j]) {
					bfsGroupMap(i,j,num);
					num++;
				}
			}
		}
		return num-1;
	}
	
	static void bfsGroupMap(int r, int c, int num) {
		Queue<Point> q=new ArrayDeque();
		q.offer(new Point(r,c));
		v[r][c]=true;
		gmap[r][c]=num;
		int score=map[r][c];
		
		while(!q.isEmpty()) {
			Point p=q.poll();
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&!v[nr][nc]&&map[nr][nc]==score) {
					q.offer(new Point(nr,nc));
					v[nr][nc]=true;
					gmap[nr][nc]=num;
				}
			}
		}
	}
	
	
	static int[] dr= {1,-1,0,0};
	static int[] dc= {0,0,1,-1};

	static class Group{
		int num,score,cnt;
		public Group(int num, int score, int cnt) {
			this.score=score;
			this.num=num;
			this.cnt=cnt;
		}
	}
	
	static class Point{
		int r,c;
		public Point(int r, int c) {
			this.r=r;
			this.c=c;
		}
	}
}