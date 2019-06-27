package com.citi.qa.util;

import com.citi.qa.core.base.PageObject;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener
    extends PageObject
    implements ITestListener
{
    WebDriver driver = null;

    String filePath = "D:\\SCREENSHOTS";

    @Override
    public void onTestFailure( ITestResult result )
    {
        System.out.println( "***** Error " + result.getName() + " test has failed *****" );
        String methodName = result.getName().toString().trim();
        takeScreenShot( methodName );
    }

    public void takeScreenShot( String methodName )
    {
        try
        {
            File scrFile = ( (TakesScreenshot) driver ).getScreenshotAs( OutputType.FILE );
            String currentDir = System.getProperty( "user.dir" );

            FileUtils.copyFile( scrFile, new File( currentDir + "screenshots" + System.currentTimeMillis() + ".png" ) );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish( ITestContext context )
    {
    }

    @Override
    public void onTestStart( ITestResult result )
    {
    }

    @Override
    public void onTestSuccess( ITestResult result )
    {
    }

    @Override
    public void onTestSkipped( ITestResult result )
    {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage( ITestResult result )
    {
    }

    @Override
    public void onStart( ITestContext context )
    {
    }
}
