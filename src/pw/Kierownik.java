package pw;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
public class Kierownik extends Thread {
	int M;//Liczba kas
	int K;//Liczba klientow na kase
	int N;//dzialajace kasy
	AtomicInteger L;//liczba klientow
	ArrayList<Integer> kasy;
	public Kierownik(int M, int K, int N, AtomicInteger L, 
			ArrayList<Integer> kasy) {
		this.M=M;
		this.K=K;
		this.N=N;
		this.L=L;
		this.kasy=kasy;
	}
	public void run() {
		while(true) {
			
			if(L.get()>K*N&&N<M) {
				
					
					for(int i=0;i<M;i++) {
						if(!kasy.contains(i))kasy.add(i);
						break;
					}
					N++;
					
				
			}else if(L.get()<K*(N-1)&&N>2) {
				for(int i=M-1;i>=0;i--) {
					if(kasy.contains(i))kasy.remove(kasy.indexOf(i));
					break;
				}
				N--;
			}
			System.out.println("Aktywne kasy: "+N);
			
			try{sleep(100);}
			catch(Exception e) {}
		}
	}
}
