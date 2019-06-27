package com.citi.qa.util;
/***************************************
 * PURPOSE ********************************** - This class implements the WebDriverEventListener, which is included
 * under events. The purpose of implementing this interface is to override all the methods and define certain useful Log
 * statements which would be displayed/logged as the application under test is being run. Do not call any of these
 * methods, instead these methods will be invoked automatically as an when the action done (click, findBy etc).
 */

import com.citi.qa.core.base.PageObject;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.ITestResult;

/**
 * @author chouhan_h
 */
public class WebEventListener
    extends PageObject
    implements WebDriverEventListener
{

    @Override
    public void beforeNavigateTo( String url, WebDriver driver )
    {
        System.out.println( "Before navigating to: '" + url + "'" );
    }

    @Override
    public void afterNavigateTo( String url, WebDriver driver )
    {
        System.out.println( "Navigated to:'" + url + "'" );
    }

    /**
     * @param element
     * @param driver
     */
    public void beforeChangeValueOf( WebElement element, WebDriver driver )
    {
        System.out.println( "Value of the:" + element.toString() + " before any changes made" );
    }

    /**
     * @param element
     * @param driver
     */
    public void afterChangeValueOf( WebElement element, WebDriver driver )
    {
        System.out.println( "Element value changed to: " + element.toString() );
    }

    @Override
    public void beforeClickOn( WebElement element, WebDriver driver )
    {
        System.out.println( "Trying to click on: " + element.toString() );
    }

    @Override
    public void afterClickOn( WebElement element, WebDriver driver )
    {
        System.out.println( "Clicked on: " + element.toString() );
    }

    @Override
    public void beforeNavigateBack( WebDriver driver )
    {
        System.out.println( "Navigating back to previous page" );
    }

    @Override
    public void afterNavigateBack( WebDriver driver )
    {
        System.out.println( "Navigated back to previous page" );
    }

    @Override
    public void beforeNavigateForward( WebDriver driver )
    {
        System.out.println( "Navigating forward to next page" );
    }

    @Override
    public void afterNavigateForward( WebDriver driver )
    {
        System.out.println( "Navigated forward to next page" );
    }

    @Override
    public void onException( Throwable error, WebDriver driver )
    {
        System.out.println( "Exception occured: " + error );
        try
        {
            File scrFile = ( (TakesScreenshot) driver ).getScreenshotAs( OutputType.FILE );
            String currentDir = System.getProperty( "user.dir" );

            FileUtils.copyFile( scrFile,
                    new File( currentDir + "/screenshots/" + System.currentTimeMillis() + ".png" ) );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    public void takescreen( ITestResult result )
        throws IOException
    {
        if( !result.isSuccess() )
        {
            File imageFile = ( (TakesScreenshot) webDriver ).getScreenshotAs( OutputType.FILE );
            String failureImageFileName = result.getMethod().getMethodName()
                    + new SimpleDateFormat( "MM-dd-yyyy_HH-ss" ).format( new GregorianCalendar().getTime() ) + ".png";
            File failureImageFile = new File(
                    System.getProperty( "user.dir" ) + "\\screenshots\\" + failureImageFileName );
            FileUtils.copyFile( imageFile, failureImageFile );
        }
    }

    @Override
    public void beforeFindBy( By by, WebElement element, WebDriver driver )
    {
        System.out.println( "Trying to find Element By : " + by.toString() );
    }

    @Override
    public void afterFindBy( By by, WebElement element, WebDriver driver )
    {
        System.out.println( "Found Element By : " + by.toString() );
    }

    /*
     * non overridden methods of WebListener class
     */

    @Override
    public void beforeScript( String script, WebDriver driver )
    {
    }

    @Override
    public void afterScript( String script, WebDriver driver )
    {
    }

    @Override
    public void beforeAlertAccept( WebDriver driver )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterAlertAccept( WebDriver driver )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterAlertDismiss( WebDriver driver )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeAlertDismiss( WebDriver driver )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeNavigateRefresh( WebDriver driver )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterNavigateRefresh( WebDriver driver )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeChangeValueOf( WebElement element, WebDriver driver, CharSequence[] keysToSend )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterChangeValueOf( WebElement element, WebDriver driver, CharSequence[] keysToSend )
    {
        // TODO Auto-generated method stub

    }

}
