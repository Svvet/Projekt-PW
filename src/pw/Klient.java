package pw;
import java.util.Random;
import java.util.ArrayList;
public class Klient extends Thread {
	int kasa;
	ArrayList<Integer> kasy;
	void sleepRand(int a, int b) {
        Random rand = new Random();
        int res = a + rand.nextInt(b - a + 1);
        try {
            sleep(res);
        } catch (InterruptedException e) {
        }
    }
	public Klient(String id, ArrayList<Integer> kasy) {
		super(id);
		this.kasy=kasy;
	}
	public void run() {
		sleepRand(1000,5000);
		System.out.println(this.getName());
	}
}
