import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int N,M,K;
	
	static PriorityQueue<Atom> atoms=new PriorityQueue();
	
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		K=Integer.parseInt(st.nextToken());
		
		
		for(int i=0;i<M;i++) {
			st=new StringTokenizer(br.readLine());
			int r=Integer.parseInt(st.nextToken())-1;
			int c=Integer.parseInt(st.nextToken())-1;
			int m=Integer.parseInt(st.nextToken());
			int s=Integer.parseInt(st.nextToken());
			int d=Integer.parseInt(st.nextToken());
			atoms.offer(new Atom(r,c,m,s,d));
		}
		
		for(int k=0;k<K;k++) {
			
			
			moveAtom();
			
			plusAtom();
			
			if(isEnd()) break;
		}
		
		int answer=getAnswer();
		
		bw.write(answer+"");
		bw.close();
	}
	
	static int getAnswer() {
		int sum=0;
		for(Atom a:atoms) {
			sum+=a.m;
		}
		return sum;
	}
	
	static boolean isEnd() {
		return atoms.size()==0;
	}
	
	static void plusAtom() {
		List<Atom> temp=new ArrayList();
		List<Atom> same=new ArrayList();
		
		Atom pre=atoms.poll();
		same.add(pre);
		
		while(!atoms.isEmpty()) {
			Atom a=atoms.poll();
			if(pre.r==a.r&&pre.c==a.c) {
				same.add(a);
			}
			else {
				if(same.size()>=2) {
					int summ=0;
					int sums=0;
					boolean check=true;
					int dir=pre.d%2;
					for(Atom at:same) {
						summ+=at.m;
						sums+=at.s;
						if(dir!=at.d%2) check=false;
					}
					
					int nm=summ/5;
					int ns=sums/same.size();
					if(nm>0) {
						if(check) {
							for(int d=0;d<8;d+=2) {
								temp.add(new Atom(pre.r,pre.c,nm,ns,d));
							}
						}
						else {
							for(int d=1;d<8;d+=2) {
								temp.add(new Atom(pre.r,pre.c,nm,ns,d));
							}
						}
					}
				}
				else {
					temp.add(pre);
				}
				
				same.clear();
				pre=a;
				same.add(a);
			}
		}
		if(same.size()==1) {
			temp.add(pre);
		}
		else {
			int summ=0;
			int sums=0;
			boolean check=true;
			int dir=pre.d%2;
			for(Atom at:same) {
				summ+=at.m;
				sums+=at.s;
				if(dir!=at.d%2) check=false;
			}
			
			int nm=summ/5;
			int ns=sums/same.size();
			if(nm>0) {
				if(check) {
					for(int d=0;d<8;d+=2) {
						temp.add(new Atom(pre.r,pre.c,nm,ns,d));
					}
				}
				else {
					for(int d=1;d<8;d+=2) {
						temp.add(new Atom(pre.r,pre.c,nm,ns,d));
					}
				}
			}
		}
		atoms.addAll(temp);
	}
	
	static void moveAtom() {
		List<Atom> temp=new ArrayList();
		while(!atoms.isEmpty()) {
			Atom a=atoms.poll();
			int nr=a.r+dr[a.d]*a.s;
			int nc=a.c+dc[a.d]*a.s;
			nr=nr>=0?nr%N:nr%N+N;
			nc=nc>=0?nc%N:nc%N+N;
			
			a.r=nr;
			a.c=nc;
			temp.add(a);
		}
		atoms.addAll(temp);
	}
	
	static int[] dr= {-1,-1,0,1,1,1,0,-1};
	static int[] dc= {0,1,1,1,0,-1,-1,-1};
	
	static class Atom implements Comparable<Atom>{
		int r,c,m,s,d;
		public Atom(int r, int c, int m, int s, int d) {
			this.r=r;
			this.c=c;
			this.m=m;
			this.s=s;
			this.d=d;
		}
		@Override
		public int compareTo(Atom o) {
			return this.r==o.r?this.c-o.c:this.r-o.r;
		}
	}

}