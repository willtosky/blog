package cn.crybird.blogshow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
@Slf4j
public class BlogShowApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogShowApplication.class, args);
        new BlogShowApplication().rwlock();
    }

    public int MAX = 100;
    public int count = 0;
    public int readCount = 0;
    public Lock rlock = new ReentrantLock();
    public Lock wlock = new ReentrantLock();
    public Thread t = null;
    public void rwlock(){
        final BlogShowApplication b = this;
        for(int i = 0;i < 5;i++){
            new Thread(() -> {
                while(true){
                    try {
                        b.write();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(() -> {
                while(true){
                    try {
                        b.read();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void write() throws InterruptedException {
        wlock.lock();
        if(count < MAX){
            count++;
            log.info(Thread.currentThread().getName() + "-write: " + (count - 1) + " -> " + count);
        }else{
            wlock.unlock();
            return;
        }
        wlock.unlock();
        TimeUnit.MILLISECONDS.sleep(1000);
    }

    public void read() throws InterruptedException {
        rlock.lock();
        if(readCount == 0){
            wlock.lock();
            t = Thread.currentThread();
        }
        readCount++;
        rlock.unlock();

        log.info(Thread.currentThread().getName() + "--read: "+ count);

        rlock.lock();
        readCount--;
        if(t.equals(Thread.currentThread())){
            wlock.unlock();
        }
        rlock.unlock();
        TimeUnit.MILLISECONDS.sleep(1000);

    }

}