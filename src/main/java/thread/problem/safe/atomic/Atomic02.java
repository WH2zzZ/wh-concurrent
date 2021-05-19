package thread.problem.safe.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类型操作
 *
 * @Author WangHan
 * @Create 2019/12/3 11:28 下午
 */
public class Atomic02 {

}

class Account{
    private AtomicInteger balance;

    public Account(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    public Integer getBalance(){
        return balance.get();
    }

    public void withdraw(Integer amount){
        Integer balance = getBalance();
        int finalObject = balance - amount;
        this.balance.compareAndSet(balance, finalObject);
    }

}