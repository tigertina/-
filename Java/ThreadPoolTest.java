import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 一般需要根据任务的类型来配置线程池大小：
	如果是CPU密集型任务，就需要尽量压榨CPU，参考值可以设为 NCPU+1
	如果是IO密集型任务，参考值可以设置为2*NCPU
 * @author smile_tina
 *
 */
public class ThreadPoolTest {

	/**
	 * 从执行结果可以看出，当线程池中线程的数目大于5时，便将任务放入任务缓存队列里面，当任务缓存队列满了之后，便创建新的线程。如果上面程序中，将for循环中改成执行20个任务，就会抛出任务拒绝异常了。
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
		for(int i = 0; i < 16; i++){
			MyTask task = new MyTask(i);
			executor.execute(task);
			System.out.println("线程池中线程数目："+executor.getPoolSize()+",队列中等待执行的任务数目："+executor.getQueue().size()+",已执行完的任务数目"+executor.getCompletedTaskCount());
		}
		executor.shutdown();
	}

}
class MyTask implements Runnable{
	private int taskNum;
	public MyTask(int num){
		this.taskNum = num;
	}
	@Override
	public void run() {
		System.out.println("正在执行task "+taskNum);
		try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕");
	}
}
