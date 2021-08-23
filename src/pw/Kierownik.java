package pw;
import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
public class Kierownik extends Thread {
	int M;//Liczba kas
	int K;//Liczba klientow na kase
	int N;//dzialajace kasy
	
	AtomicInteger L;//liczba klientow
	HashSet<Integer> kasy;
	AtomicInteger[] varsToSynch;
	Semaphore kierSem;
	Semaphore klientSem;
	Semaphore chron;
	public Kierownik(int M, int K, AtomicInteger L, 
			HashSet<Integer> kasy,AtomicInteger[] varsToSynch,
			Semaphore kierSem, Semaphore klientSem, Semaphore chron) {
		this.M=M;
		this.K=K;
		this.L=L;
		this.kasy=kasy;
		this.varsToSynch=varsToSynch;
		this.kierSem=kierSem;
		this.klientSem=klientSem;
		this.chron=chron;
	}
	public void run() {
		
		while(true) {
			try{chron.acquire();}
			catch(InterruptedException e) {
				System.out.println(e.getMessage());
				}
			varsToSynch[2].incrementAndGet();
			if(varsToSynch[0].get()==0) {
				varsToSynch[3].incrementAndGet();
				kierSem.release();
			}
			chron.release();
			N=kasy.size();
			if(L.get()>K*N&&N<M) {
				for( int i=0;i<M;i++){
						if(!kasy.contains(i)) {
							kasy.add(i);
							break;
						}
						
					}
					N++;
					
				
			}else if(L.get()<K*(N-1)&&N>2) {
				for(int i=M-1;i>=0;i--) {
					if(kasy.contains(i)) {
						kasy.remove(i);
						break;}
					
				}
				N--;
			}
			System.out.println("Aktywne kasy: "+N);
			try{chron.acquire();}
			catch(InterruptedException e) {
				System.out.println(e.getMessage());
				}
			varsToSynch[3].decrementAndGet();
			varsToSynch[2].decrementAndGet();
			if(varsToSynch[3].get()==0) {
				while(varsToSynch[1].get()<varsToSynch[0].get()) {
					varsToSynch[1].incrementAndGet();
					klientSem.release();
				}
			}
			chron.release();
			try{sleep(500);}
			catch(Exception e) {}
		}
	}
}
