import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class invokeWorkers implements Runnable
{
    /**************************/
    int secondLevelThreads = 3;
    /***************************/
    public invokeWorkers()            // Constructor to get arguments from the main thread
    {
       // Send args from main thread
    }
    
    ExecutorService executorService = Executors.newFixedThreadPool(secondLevelThreads) ;
    
    public void run()
    {
        File dpath = new File("Input");
        String contents[] = dpath.list();
        int x = contents[contents.length - 1].length() - 11;
        String f = "";
        while(contents[contents.length - 1].charAt(x) != '-'){
            f = f + contents[contents.length - 1].charAt(x);
            x--;
        }
        String se = "";
        for(int i = f.length() - 1 ; i >= 0; i--){
            se = se + f.charAt(i);
        }
        //secondLevelThreads = Integer.parseInt(se);
        secondLevelThreads = 16;

        System.out.println(secondLevelThreads);
        for(int i=0; i < secondLevelThreads ; i++)
        {
            Runnable runnableTask = new sendQuery()  ;    //  Pass arg, if any to constructor sendQuery(arg)
            executorService.submit(runnableTask) ;
        }

        sendQuery s = new sendQuery();      // Send queries from current thread
        s.run();

        // Stop further requests to executor service
        executorService.shutdown()  ;
        try
        {
            // Wait for 8 sec and then exit the executor service
            if (!executorService.awaitTermination(8, TimeUnit.SECONDS))
            {
                executorService.shutdownNow();
            } 
        } 
        catch (InterruptedException e)
        {
            executorService.shutdownNow();
        }
    }
}
    
