package com.citi.qa.testcases;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class UploadFileRobot

{

    public WebDriver driver;

    String URL = "http://docs.sencha.com/extjs/4.2.5/extjs-build/examples/form/file-upload.html";

    @Test
    public void testUpload()
        throws InterruptedException
    {

        driver.get( URL );
        WebElement elementToClick = driver.findElement(
                By.xpath( "*//td[@id='fileuploadfield-1009-browseButtonWrap']" ) );
        elementToClick.click();
        Thread.sleep( 5000 );
        UploadFileRobot.uploadFile( "C:\\realtek.log" );
        driver.quit();
    }

    /**
     * This method will set any parameter string to the system's clipboard.
     */
    public static void setClipboardData( String string )
    {
        //StringSelection is a class that can be used for copy and paste operations.
        StringSelection stringSelection = new StringSelection( string );
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents( stringSelection, null );
    }

    public static void uploadFile( String fileLocation )
    {
        try
        {
            //Setting clipboard with file location
            UploadFileRobot.setClipboardData( fileLocation );
            //native key strokes for CTRL, V and ENTER keys
            Robot robot = new Robot();

            robot.keyPress( KeyEvent.VK_CONTROL );
            robot.keyPress( KeyEvent.VK_V );
            robot.keyRelease( KeyEvent.VK_V );
            robot.keyRelease( KeyEvent.VK_CONTROL );
            robot.keyPress( KeyEvent.VK_ENTER );
            robot.keyRelease( KeyEvent.VK_ENTER );
        }
        catch( Exception exp )
        {
            exp.printStackTrace();
        }
    }
}
