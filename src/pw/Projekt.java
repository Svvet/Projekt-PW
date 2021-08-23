package pw;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
public class Projekt {
	
	
	public static void main(String[] args) {
		int M = 5; //liczba kas; M>3
		int K = 3; //liczba klientów na kasę
		int N = 2; //działające kasy; N>1
		int mozliweKasy[]=new int[M];
		Thread k[]=new Thread[20];
		
		ReentrantLock locks[]=new ReentrantLock[M];
		AtomicInteger L=new AtomicInteger(0);//Liczba klientow
		ArrayList<Integer> kasy=new ArrayList<Integer>(M);
		Thread kierownik=new Kierownik(M,K,N,L,kasy);
		kasy.add(0);
		kasy.add(2);
		kasy.add(4);
		for(int i = 0;i<locks.length;i++) {
			locks[i]=new ReentrantLock(true);
		}
		for(int i = 0;i<mozliweKasy.length;i++) {
			mozliweKasy[i]=i;
		}
		kierownik.start();
		for(int i = 0;i<k.length;i++) {
			k[i]=new Klient(""+i,kasy,L,locks);
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
		System.out.println("koniec");
	}

}
