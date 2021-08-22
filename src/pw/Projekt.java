package pw;
import java.util.ArrayList;
public class Projekt {
	
	
	public static void main(String[] args) {
		int M = 5; //liczba kas; M>3
		int K = 10; //liczba klientów na kasę
		int N = 2; //działające kasy; N>1
		Thread k[]=new Thread[100];
		ArrayList<Integer> kasy=new ArrayList<Integer>(M);
		
		for(int i = 0;i<100;i++) {
			k[i]=new Klient(""+i,kasy);
			k[i].start();
		}
		for (int i = 0; i<100;i++){
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
