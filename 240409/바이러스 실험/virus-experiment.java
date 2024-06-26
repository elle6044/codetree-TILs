import java.util.*;
import java.io.*;

public class Main {
	
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	static int[][] map;
	static int[][] plus;
	
	static PriorityQueue<Virus> viruses=new PriorityQueue();
	static ArrayList<Virus> deads=new ArrayList<>();

	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		
		
		
		map=new int[N][N];
		plus=new int[N][N];
		
		for(int i=0;i<N;i++) {
			Arrays.fill(map[i], 5);
		}
		
		for(int i=0;i<N;i++) {
			st=new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				plus[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0;i<M;i++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			int age=Integer.parseInt(st.nextToken());
			viruses.add(new Virus(r,c,age));
		}
		
		
		for(int k=0;k<K;k++) {
			eatFood();
			
			chageFood();
			
			spreadVirus();
			
			plusFood();
			
//			System.out.println(k);
		}
		
		int answer=viruses.size();
		bw.write(answer+"");
		bw.close();
	}

	
	public static void plusFood() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				map[i][j]+=plus[i][j];
			}
		}
	}
	
	public static void spreadVirus() {
		List<Virus> temp=new ArrayList();
		while(!viruses.isEmpty()) {
			Virus v=viruses.poll();
			if(v.age%5==0) {
				for(int d=0;d<8;d++) {
					int nr=v.r+dr[d];
					int nc=v.c+dc[d];
					if(nr>=0&&nr<N&&nc>=0&&nc<N) {
						temp.add(new Virus(nr,nc,1));
					}
				}
			}
			temp.add(v);
		}
		
		viruses.addAll(temp);
	}
	
	public static void chageFood() {
		for(Virus v:deads) {
			map[v.r][v.c]+=v.age/2;
		}
		deads.clear();
	}
	
	public static void eatFood() {
		List<Virus> temp=new ArrayList();
		while(!viruses.isEmpty()) {
			Virus v=viruses.poll();
			if(v.age<=map[v.r][v.c]) {
				map[v.r][v.c]-=v.age;
				v.age++;
				temp.add(v);
			}
			else {
				deads.add(v);
			}
		}
		
		viruses.addAll(temp);
	}
	
	
	
	static int[] dr= {-1,-1,0,1,1,1,0,-1};
	static int[] dc= {0,1,1,1,0,-1,-1,-1,};
	static class Virus implements Comparable<Virus>{
		int r,c,age;
		public Virus(int r, int c, int age) {
			this.r=r;
			this.c=c;
			this.age=age;
		}
		@Override
		public int compareTo(Virus o) {
			return this.age-o.age;
		}
	}
}