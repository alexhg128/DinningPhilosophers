package mx.hahn.diningphilosophers;

import android.app.Activity;

import java.lang.*;
import java.lang.reflect.Method;
import java.util.concurrent.Semaphore;

public class Philosophers extends Thread {

    public static PhilosopherStatus[] status = new PhilosopherStatus[5];

    private final static int size = 5;

    private final static int roomSize = 4;

    private static boolean initialized = false;

    private static boolean paused = false;

    private static Method callback;

    private static MainActivity context;

    private static Semaphore[] fork = new Semaphore[size];

    private static Semaphore room = new Semaphore(roomSize);

    private int number;

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber(){
        return this.number;
    }

    public Philosophers(int pnum) {
        this.number = pnum;
        if(!initialized) {
            initializeForks();
            initialized = true;
        }

    }

    public static void setContext(MainActivity o) {
        context = o;
    }

    private static void emitUpdate() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.update();
            }
        });

    }

    private void initializeForks(){
        for(int i = 0;i < size;i++){
            fork[i] = new Semaphore(1);
            status[i] = PhilosopherStatus.ABSENT;
        }
        emitUpdate();
    }

    private void think(){
        try {
            sleep((long) (((Math.random() * 5) + 1) * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat(){
        try {
            sleep((long) (((Math.random() * 5) + 1) * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            emitUpdate();
            status[number] = PhilosopherStatus.THINKING;
            emitUpdate();
            this.think();
            try {
                status[number] = PhilosopherStatus.WAITING;
                emitUpdate();
                room.acquire();
                fork[number].acquire();
                fork[(number+1)%size].acquire();
                status[number] = PhilosopherStatus.EATING;
                emitUpdate();
                this.eat();
                status[number] = PhilosopherStatus.WAITING;
                emitUpdate();
                fork[(number+1)%size].release();
                fork[number].release();
                room.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}