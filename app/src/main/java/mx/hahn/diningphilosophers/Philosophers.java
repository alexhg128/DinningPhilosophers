package mx.hahn.diningphilosophers;
import java.lang.*;
import java.util.concurrent.Semaphore;
public class Philosophers extends Thread{
    static int size = 5;
    static int roomSize = 4;
    static Semaphore fork[] = new Semaphore[size];
    static Semaphore room = new Semaphore(roomSize);
    int number;
    public void setNumber(int number){
        this.number = number;
    }
    public int getNumber(){
        return this.number;
    }
    public void initializeForks(){
        for(int i = 0;i < size;i++){
            fork[i] = new Semaphore(1);
        }
    }
    void think(){
        try {
            Thread.sleep((long) (((Math.random() * 5) + 1) * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void eat(){
        try {
            Thread.sleep((long) (((Math.random() * 5) + 1) * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        while(true){
            this.think();
            try {
                room.acquire();
                fork[number].acquire();
                fork[(number+1)%size].acquire();
                this.eat();
                fork[(number+1)%size].release();
                fork[number].release();
                room.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}