import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int R,C,K;
	
	static int[][] map=new int[3][3];
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		R=Integer.parseInt(st.nextToken())-1;
		C=Integer.parseInt(st.nextToken())-1;
		K=Integer.parseInt(st.nextToken());
		
		for(int i=0;i<3;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<3;j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		int answer=0;
		while(true) {
			changeMap();
			answer++;
			if(R<map.length&&C<map[0].length&&map[R][C]==K) {
				break;
			}
			if(answer==101) {
				answer=-1;
				break;
			}
		}
		
		bw.write(answer+"");
		bw.close();

	}
	
	public static void changeMap() {
		int h=map.length;
		int w=map[0].length;
		if(h>=w) {
			Map[] maps=new Map[h];
			
			int size=0;
			for(int i=0;i<h;i++) {
				Map<Integer,Integer> m=new HashMap();
				for(int j=0;j<w;j++) {
					int num=map[i][j];
					if(num==0)continue;
					m.put(num, m.getOrDefault(num, 0)+1);
				}
				size=Math.max(size, m.size()*2);
				maps[i]=m;
			}
			map=new int[h][Math.min(100, size)];
			
			int cnt=0;
			for(Map<Integer,Integer> m:maps) {
				PriorityQueue<Point>pq=new PriorityQueue();
				for(int key:m.keySet()) {
					pq.offer(new Point(key,m.get(key)));
				}
				
				int pqSize=pq.size();
				for(int s=0;s<pqSize;s++) {
					Point p=pq.poll();
					map[cnt][s*2]=p.num;
					map[cnt][s*2+1]=p.cnt;
				}
				cnt++;
			}
		}
		else {
			Map[] maps=new Map[w];
			
			int size=0;
			for(int j=0;j<w;j++) {
				Map<Integer,Integer> m=new HashMap();
				for(int i=0;i<h;i++) {
					int num=map[i][j];
					if(num==0)continue;
					m.put(num, m.getOrDefault(num, 0)+1);
				}
				size=Math.max(size, m.size()*2);
				maps[j]=m;
			}
			map=new int[Math.min(100, size)][w];
			
			int cnt=0;
			for(Map<Integer,Integer> m:maps) {
				PriorityQueue<Point>pq=new PriorityQueue();
				for(int key:m.keySet()) {
					pq.offer(new Point(key,m.get(key)));
				}
				
				int pqSize=pq.size();
				for(int s=0;s<pqSize;s++) {
					Point p=pq.poll();
					map[s*2][cnt]=p.num;
					map[s*2+1][cnt]=p.cnt;
				}
				cnt++;
			}
		}
	}
	
	static class Point implements Comparable<Point>{
		int num, cnt;
		public Point(int num,int cnt) {
			this.num=num;
			this.cnt=cnt;
		}
		
		@Override
		public int compareTo(Point o) {
			return this.cnt==o.cnt?this.num-o.num:this.cnt-o.cnt;
		}
	}

}