import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,Q;
	static int[][] map;
	static int[][] temp;
	static boolean[][] v;
	
	static int msize;

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		Q=Integer.parseInt(st.nextToken());
		
		msize=(int)Math.pow(2, N);
		map=new int[msize][msize];

		for(int i=0;i<map.length;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<map[0].length;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		st=new StringTokenizer(br.readLine());
		for(int q=0;q<Q;q++) {
			int level=Integer.parseInt(st.nextToken());
			if(level!=0) {
				rotation((int)Math.pow(2, level));
			}

			change();
		}

		int[] answer=getAnswer();
		bw.write(answer[0]+"\n"+answer[1]);
		bw.close();
	}
	
	static int[] getAnswer() {
		int[] answer=new int[2];
		v=new boolean[msize][msize];
		
		for(int i=0;i<msize;i++) {
			for(int j=0;j<msize;j++) {
				if(map[i][j]>0&&!v[i][j]) {
					int[] ice=bfsIce(i,j);
					answer[0]+=ice[0];
					answer[1]=Math.max(answer[1], ice[1]);
				}
			}
		}
		
		return answer;
	}
	
	static int[] bfsIce(int r, int c) {
		int[] ice=new int[2];
		Queue<Point> q=new ArrayDeque();
		q.offer(new Point(r,c));
		v[r][c]=true;
		ice[1]++;
		while(!q.isEmpty()) {
			Point p=q.poll();
			ice[0]+=map[p.r][p.c];
			for(int d=0;d<4;d++) {
				int nr=p.r+dr[d];
				int nc=p.c+dc[d];
				if(nr>=0&&nr<msize&&nc>=0&&nc<msize&&!v[nr][nc]&&map[nr][nc]>0) {
					q.offer(new Point(nr,nc));
					v[nr][nc]=true;
					ice[1]++;
				}
			}
		}
		return ice;
	}
	
	static void change() {
		temp=new int[msize][msize];
		for(int i=0;i<msize;i++) {
			for(int j=0;j<msize;j++) {
				if(map[i][j]>0) {
					int cnt=0;
					for(int d=0;d<4;d++) {
						int nr=i+dr[d];
						int nc=j+dc[d];
						if(nr>=0&&nr<msize&&nc>=0&&nc<msize&&map[nr][nc]>0) {
							cnt++;
						}
					}
					if(cnt<3) {
						temp[i][j]--;
					}
				}
			}
		}
		for(int i=0;i<msize;i++) {
			for(int j=0;j<msize;j++) {
				map[i][j]+=temp[i][j];
			}
		}
	}
	
	static void rotation(int size) {
		temp=new int[msize][msize];
		for(int i=0;i<msize;i+=size) {
			for(int j=0;j<msize;j+=size) {
				int n=size;
				int hn=size/2;
				// 1번 그룹을 2번 위치로 이동
		        for (int i2 = i; i2 < i+hn; i2++) {
		            for (int j2 = j; j2 <j+ hn; j2++) {
		                temp[i2][j2 + hn] = map[i2][j2];
		            }
		        }

		        // 2번 그룹을 3번 위치로 이동
		        for (int i2 = i; i2 <i+ hn; i2++) {
		            for (int j2 =j+ hn; j2 <j+n; j2++) {
		                temp[i2 + hn][j2] = map[i2][j2];
		            }
		        }

		        // 3번 그룹을 4번 위치로 이동
		        for (int i2 = i+hn; i2 <i+ n; i2++) {
		            for (int j2 =j+ hn; j2 <j+n; j2++) {
		                temp[i2][j2 - hn] = map[i2][j2];
		            }
		        }

		        // 4번 그룹을 1번 위치로 이동
		        for (int i2 = i+hn; i2 <i+ n; i2++) {
		            for (int j2 = j; j2 <j+ hn; j2++) {
		                temp[i2 - hn][j2] = map[i2][j2];
		            }
		        }
			}
		}
		
		for(int i=0;i<msize;i++) {
			map[i]=Arrays.copyOf(temp[i], msize);
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