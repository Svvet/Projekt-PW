package pw;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashSet;
public class Klient extends Thread {
	Random rand = new Random();
	int kasa;
	HashSet<Integer> kasy;
	AtomicInteger liczbaKlientow;
	ReentrantLock[] locks;
	AtomicInteger[] varsToSynch;
	Semaphore kierSem;
	Semaphore klientSem;
	Semaphore chron;
	void sleepRand(int a, int b) {
        
        int res = a + rand.nextInt(b - a + 1);
        try {
            sleep(res);
        } catch (InterruptedException e) {
        }
    }
	int losowaKasa() {
		Object[] arr=kasy.toArray();
		int wybranaKasa=rand.nextInt(kasy.size());
		System.out.println("Kasy do wyboru: "+kasy.size());
		Integer a = (Integer)arr[wybranaKasa];
		return a;
		
	}
	public Klient(String id,HashSet<Integer> kasy, AtomicInteger liczbaKlientow,
			ReentrantLock[] locks, AtomicInteger[] varsToSynch,
			Semaphore kierSem, Semaphore klientSem, Semaphore chron) {
		super(id);
		this.kasy=kasy;
		this.liczbaKlientow=liczbaKlientow;
		this.locks=locks;
		this.varsToSynch=varsToSynch;
		this.kierSem=kierSem;
		this.klientSem=klientSem;
		this.chron=chron;
	}
	private void zakupy() {
		//sleepRand(1000,10000);
		System.out.println(this.getName()+" wszedl do sklepu");
		liczbaKlientow.incrementAndGet();
		sleepRand(1000,3000);
		
	}
	private void kolejka() {
		//synchronizacja dostepu do dostepnych kas, by klient nie
		//poszedl do zamknietej juz kasy
		try{chron.acquire();}
		catch(InterruptedException e) {
			System.out.println(e.getMessage());
			}
		varsToSynch[0].getAndIncrement();
		if(varsToSynch[2].get()==0) {
			varsToSynch[1].getAndIncrement();
			klientSem.release();
		}
		chron.release();
		try{klientSem.acquire();}
		catch(InterruptedException e) {
			System.out.println(e.getMessage());
			}
		kasa=losowaKasa();//critical
		try{chron.acquire();}
		catch(InterruptedException e) {
			System.out.println(e.getMessage());
			}
		varsToSynch[1].decrementAndGet();
		varsToSynch[0].decrementAndGet();
		if(varsToSynch[1].get()==0&&varsToSynch[3].get()<varsToSynch[2].get()) {
			kierSem.release();
		}
		chron.release();
		System.out.println(this.getName()+" wybral kase "+kasa);
		locks[kasa].lock();
		sleepRand(1000,3000);
		locks[kasa].unlock();
		System.out.println(this.getName()+" zakonczyl zakupy");
		liczbaKlientow.decrementAndGet();
		System.out.println(liczbaKlientow.get());
	}
	public void run() {
		zakupy();
		kolejka();
		
	}
}
