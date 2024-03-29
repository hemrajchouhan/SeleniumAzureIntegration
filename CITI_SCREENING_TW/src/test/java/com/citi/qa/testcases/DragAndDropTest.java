package com.citi.qa.testcases;

import com.citi.qa.core.base.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * @author Chouhan_h Test the DRAG AND DROP functionality of kitchen-sink Home Application
 */
public class DragAndDropTest
    extends PageObject
{

    //private WebDriver webDriver;

    public DragAndDropTest( WebDriver webDriver )
    {
        this.webDriver = webDriver;
        this.webDriver.get( "http://examples.sencha.com/extjs/6.5.0/examples/kitchensink/#tree-two" );
    }

    public void initilization()
        throws InterruptedException
    {

        new WebDriverWait( this.webDriver, 600 ).until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy( By.xpath( "//td[div/span[text()='app']]" ) ) );
        WebElement sourceElement = this.webDriver.findElement( By.xpath( "//td[div/span[text()='app']]" ) );
        WebElement targetElement = this.webDriver.findElement( By.xpath( "//td[div/span[text()='Custom Ext JS']]" ) );
        Actions actions = new Actions( this.webDriver );
        actions.dragAndDrop( sourceElement, targetElement ).perform();
        try
        {
            Thread.sleep( 1000 );
        }
        catch( Exception e )
        {
        }
        try
        {
            this.webDriver.findElement( By.xpath( "//div[div/div/div/div/div[text()='Source']]//span[text()='app']" ) );
        }
        catch( NoSuchElementException e )
        {
            try
            {
                this.webDriver.findElement(
                        By.xpath( "//div[div/div/div/div/div[text()='Custom Build']]//span[text()='app']" ) );
                return;
            }
            catch( NoSuchElementException e2 )
            {
                Assert.assertTrue( false );
            }
        }
        // Assert.assertTrue(false);
    }

}
