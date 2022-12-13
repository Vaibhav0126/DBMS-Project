import java.util.concurrent.ExecutorService ;
import java.util.concurrent.Executors   ;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException  ;

public class client
{
    public static void main(String args[])throws IOException
    {
        /***************************/
        
        /***************************/
        // Creating a thread pool
        File dpath = new File("Input");
        String contents[] = dpath.list();
        int x = 5;
        String f = "";
        while(contents[contents.length - 1].charAt(x) != '-'){
            f = f + contents[contents.length - 1].charAt(x);
            x++;
        }
        //int firstLevelThreads = Integer.parseInt(f);   // Indicate no of users 
        int firstLevelThreads = 16;
        
        ExecutorService executorService = Executors.newFixedThreadPool(firstLevelThreads);
        
        for(int i = 0; i < firstLevelThreads; i++)
        {
            Runnable runnableTask = new invokeWorkers();    //  Pass arg, if any to constructor sendQuery(arg)
            executorService.submit(runnableTask) ;
        }

        executorService.shutdown();
        try
        {    // Wait for 8 sec and then exit the executor service
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS))
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