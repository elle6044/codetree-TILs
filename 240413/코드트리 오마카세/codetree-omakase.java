import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;

	static int L,Q;
	
	static Map<Integer,Map<String,Integer>> belts=new HashMap();
	static Map<Integer,String> mans=new HashMap();
	static Map<Integer,Integer> sits=new HashMap();
	
	static int T=1;
	
	public static void main(String[] args) throws Exception{
		st=new StringTokenizer(br.readLine());
		L=Integer.parseInt(st.nextToken());
		Q=Integer.parseInt(st.nextToken());
		
		for(int q=0;q<Q;q++) {
			st=new StringTokenizer(br.readLine());
			int input=Integer.parseInt(st.nextToken());
			int t=Integer.parseInt(st.nextToken());
			
			int cnt=0;
			int dif=t-T;
			if(dif>=L) {
				cnt=L;
			}
			else {
				cnt=t-T;
			}

			for(int i=0;i<cnt;i++) {
				eat();
				T++;
			}
			T=t;
			

			if(input==100) {
				int x=Integer.parseInt(st.nextToken());
				String name=st.nextToken();
				int idx=((x-T+1)%L+L)%L;
				
				Map<String,Integer> belt;
				if(!belts.containsKey(idx)) {
					belt=new HashMap();
				}
				else {
					belt=belts.get(idx);
				}
				belt.put(name, belt.getOrDefault(name, 0)+1);
				belts.put(idx, belt);
				
				eat();
			}
			
			else if(input==200) {
				int x=Integer.parseInt(st.nextToken());
				String name=st.nextToken();
				int n=Integer.parseInt(st.nextToken());
				
				mans.put(x, name);
				sits.put(x, n);
				
				eat();
			}

			else if(input==300) {
				
				eat();

				int man=0;
				int bob=0;
				
				for(Integer sit:sits.values()) {
					if(sit>0) {
						man++;
					}
				}
				
				for(Map<String,Integer> belt:belts.values()) {
					for(Integer a:belt.values()) {
						bob+=a;
					}
				}
				
				System.out.println(man+" "+bob);
			}
//			System.out.println("TIME : "+T);
//			for(Map<String, Integer>belt:belts.values()) {
//				System.out.println(belt.toString());
//				
//			}
//			System.out.println(sits.toString());
//			System.out.println();
		}
	}
	
	static void eat() {
//		System.out.println("time : "+T);
		for(int x:mans.keySet()) {
			int idx=((x-T+1)%L+L)%L;
			if(sits.getOrDefault(x, 0)==0) continue;
			
//			System.out.println("x : "+x+" idx : "+idx);
			String name=mans.get(x);
			if(!belts.containsKey(idx)) continue;
			int remain=belts.get(idx).getOrDefault(name, 0);
			if(remain==0) continue;
			
			int toeat=sits.get(x);
			if(remain>=toeat) {
				belts.get(idx).put(name, remain-toeat);
				sits.put(x, 0);
			}
			else {
				belts.get(idx).put(name, 0);
				sits.put(x, toeat-remain);
			}
		}
		
		

	}

}