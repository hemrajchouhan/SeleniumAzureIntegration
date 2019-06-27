/**
 * @author rahul.rathore 06-Aug-2016
 */
package com.citi.qa.genericHelper;

import com.citi.qa.core.base.PageObject;
import com.citi.qa.helper.logger.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Chouhan_h
 */
public class GenericHelper
    extends PageObject

{

    // private WebDriver driver;

    private Logger oLog = LoggerHelper.getLogger( GenericHelper.class );

    public GenericHelper( WebDriver driver )
    {
        this.webDriver = driver;
        oLog.debug( "GenericHelper : " + this.webDriver.hashCode() );
    }

    public WebElement getElement( By locator )
    {
        oLog.info( locator );
        if( IsElementPresentQuick( locator ) )
        {
            return webDriver.findElement( locator );
        }

        try
        {
            throw new NoSuchElementException( "Element Not Found : " + locator );
        }
        catch( RuntimeException re )
        {
            oLog.error( re );
            throw re;
        }

    }

    public boolean IsElementPresentQuick( By locator )
    {
        boolean flag = webDriver.findElements( locator ).size() >= 1;
        oLog.info( flag );
        return flag;
    }

}
