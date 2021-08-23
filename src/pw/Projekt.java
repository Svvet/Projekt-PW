package pw;
import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
public class Projekt {
	
	
	public static void main(String[] args) {
		//zmienne do synchronizacji zamykania i wybierania kas
		AtomicInteger[] varsToSynch=new AtomicInteger[4];
		Semaphore kierSem = new Semaphore(0);
		Semaphore klientSem = new Semaphore(0);
		Semaphore chron = new Semaphore(1);
		int M = 5; //liczba kas; M>3
		int K = 2; //liczba klientów na kasę
		//int N = 2; //działające kasy; N>1
		int mozliweKasy[]=new int[M];
		Thread k[]=new Thread[20];
		ReentrantLock locks[]=new ReentrantLock[M];
		AtomicInteger L=new AtomicInteger(0);//Liczba klientow
		HashSet<Integer> kasy=new HashSet<Integer>();
		Thread kierownik=new Kierownik(M,K,L,kasy,varsToSynch,kierSem,
				klientSem,chron);
		kasy.add(0);
		kasy.add(2);
		for (int i=0;i<varsToSynch.length;i++) {
			varsToSynch[i]=new AtomicInteger(0);
		}
		for(int i = 0;i<locks.length;i++) {
			locks[i]=new ReentrantLock(true);
		}
		for(int i = 0;i<mozliweKasy.length;i++) {
			mozliweKasy[i]=i;
		}
		kierownik.start();
		for(int i = 0;i<k.length;i++) {
			k[i]=new Klient(""+i,kasy,L,locks,varsToSynch,kierSem,
					klientSem,chron);
			k[i].start();
		}
		
		for (int i = 0; i<k.length;i++){
            try{
            k[i].join();
            }
            catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
		try {
			kierownik.join();
		}catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("koniec");
	}

}
