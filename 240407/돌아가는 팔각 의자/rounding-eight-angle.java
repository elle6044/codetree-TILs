import java.util.*;
import java.io.*;
public class Main {

	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	
	static int[][] map=new int[4][8];
	static int K;
	
	static int right=2;
	static int left=6;
	
	public static void main(String[] args) throws Exception {
		for(int i=0;i<4;i++) {
			String input=br.readLine();
			for(int j=0;j<8;j++) {
				map[i][j]=input.charAt(j)-48;
			}
		}
		
		K=Integer.parseInt(br.readLine());
		for(int k=0;k<K;k++) {
			st=new StringTokenizer(br.readLine());
			int n=Integer.parseInt(st.nextToken())-1;
			int d=Integer.parseInt(st.nextToken());
			
			location(d,map[n]);
			
			int nl=n;
			int nd=d;
			while(true) {
				nl--;
				nd*=-1;
				if(nl>=0) {
					if(map[nl][right]!=map[nl+1][left-nd]) {
						location(nd,map[nl]);
					}
					else break;
				}
				if(nl<0) {
					break;
				}
			}
			int nr=n;
			nd=d;
			while(true) {
				nr++;
				nd*=-1;
				if(nr<4) {
					if(map[nr][left]!=map[nr-1][right-nd]) {
						location(nd,map[nr]);
					}
					else break;
				}
				if(nr>=4) {
					break;
				}
			}

		}
		
		int sum=0;
		for(int i=1;i<=4;i++) {
			sum+=map[i-1][0]*i;
		}
		bw.write(sum+"");
		bw.close();
	}
	
	public static void location(int d, int[] table) {
		if(d==1) {
			int temp=table[7];
			for(int i=7;i>=1;i--) {
				table[i]=table[i-1];
			}
			table[0]=temp;
		}
		else {
			int temp=table[0];
			for(int i=0;i<7;i++) {
				table[i]=table[i+1];
			}
			table[7]=temp;
		}
	}

}