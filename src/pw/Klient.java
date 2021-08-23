package pw;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
public class Klient extends Thread {
	Random rand = new Random();
	int kasa;
	ArrayList<Integer> kasy;
	AtomicInteger liczbaKlientow;
	ReentrantLock[] locks;
	void sleepRand(int a, int b) {
        
        int res = a + rand.nextInt(b - a + 1);
        try {
            sleep(res);
        } catch (InterruptedException e) {
        }
    }
	int losowaKasa() {
		int wybranaKasa=rand.nextInt(kasy.size());
		return kasy.get(wybranaKasa);
	}
	public Klient(String id, ArrayList<Integer> kasy, AtomicInteger liczbaKlientow,
			ReentrantLock[] locks) {
		super(id);
		this.kasy=kasy;
		this.liczbaKlientow=liczbaKlientow;
		this.locks=locks;
	}
	private void zakupy() {
		sleepRand(1000,3000);
	}
	public void run() {
		System.out.println(this.getName()+" wszedl do sklepu");
		liczbaKlientow.incrementAndGet();
		sleepRand(1000,3000);
		kasa=losowaKasa();
		System.out.println(this.getName()+" wybral kase "+kasa);
		locks[kasa].lock();
		sleepRand(1000,3000);
		locks[kasa].unlock();
		System.out.println(this.getName()+" zakonczyl zakupy");
		liczbaKlientow.decrementAndGet();
		System.out.println(liczbaKlientow.get());
		
	}
}
