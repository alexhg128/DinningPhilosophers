package mx.hahn.diningphilosophers;

import android.app.Activity;

import java.lang.*;
import java.lang.reflect.Method;
import java.util.concurrent.Semaphore;

public class Philosophers extends Thread {

    public static PhilosopherStatus[] status = new PhilosopherStatus[5];

    public static boolean[] forkStatus = new boolean[5];

    public static boolean causeDeadlock = false;

    private final static int size = 5;

    private final static int roomSize = 4;

    private static boolean initialized = false;

    private static boolean paused = false;

    private static Method callback;

    private static MainActivity context;

    private static Semaphore[] fork = new Semaphore[size];

    private static Semaphore room = new Semaphore(roomSize);

    private int number;

    private boolean stop = false;

    public void stopPhilosopher() {
        this.stop = true;
        initialized = false;
    }

    public static void resetForks() {
        for(Semaphore s : fork) {
            s.release();
        }
        room.release(roomSize);
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber(){
        return this.number;
    }

    public Philosophers(int pnum) {
        this.number = pnum;
        causeDeadlock = false;
        this.stop = false;
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

    public void initializeForks(){
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
        causeDeadlock = false;
        while(true){
            if(stop) {
                status[number] = PhilosopherStatus.ABSENT;
                emitUpdate();
                return;
            }
            emitUpdate();
            status[number] = PhilosopherStatus.THINKING;
            emitUpdate();
            this.think();
            try {
                status[number] = PhilosopherStatus.WAITING;
                emitUpdate();
                room.acquire();
                fork[number].acquire();
                forkStatus[number] = true;
                emitUpdate();
                fork[(number+1)%size].acquire();
                forkStatus[(number+1)%size] = true;
                emitUpdate();
                status[number] = PhilosopherStatus.EATING;
                emitUpdate();
                this.eat();
                status[number] = PhilosopherStatus.WAITING;
                emitUpdate();
                fork[(number+1)%size].release();
                forkStatus[(number+1)%size] = false;
                emitUpdate();
                if(!causeDeadlock){
                    fork[number].release();
                    forkStatus[number] = false;
                    emitUpdate();
                }
                room.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}