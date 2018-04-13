package demeThread;

public class SecondThread extends Thread {
    @Override
    public void run() {
        for(int i = 0; i < 20; i ++ ){
            System.out.println("2nd i = " + i);
        }
    }
}
