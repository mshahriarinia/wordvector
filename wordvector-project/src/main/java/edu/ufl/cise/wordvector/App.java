package edu.ufl.cise.wordvector;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

import edu.ufl.cise.wordvector.io.JavaQueryServer;

/**
 * Hello world!
 * $ mvn exec:java -Dexec.mainClass="edu.ufl.cise.wordvector.App"
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
        System.out.println("Hi");

    		JavaQueryServer jqs = new JavaQueryServer();

    		Instant start, end;
    		start = Instant.now();

    		jqs.loadVectors();
    		Set<String> keySet = jqs.wvTable.keySet();
    		jqs.keysList = keySet.toArray(new String[keySet.size()]);

    		// timer
    		end = Instant.now();
    		System.out.println("Initialization period: " + Duration.between(start, end)); // prints
    																																									// PT1M3.553S
    		start = Instant.now(); // reset timer

    		System.out.println("Total item count:" + jqs.wvTable.size());
    		jqs.console();

    }
}
