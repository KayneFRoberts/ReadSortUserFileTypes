package tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Kayne
 *
 */

/**
 * Runs all Junit tests in tests package
 **/

@RunWith(Suite.class)

@Suite.SuiteClasses({
   OpenCsvTest.class,
   OpenJsonTest.class,
   UserTest.class,
   OpenXmlTest.class
})

public class JunitTestSuite {   
} 
