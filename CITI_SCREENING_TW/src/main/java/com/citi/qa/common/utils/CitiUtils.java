package com.citi.qa.common.utils;

import com.citi.qa.core.base.PageObject;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

public class CitiUtils
    extends PageObject
{

    static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger( CitiUtils.class );

    public void switchToNewWindow()
    {
        Set s = webDriver.getWindowHandles();
        Iterator itr = s.iterator();
        String w1 = (String) itr.next();
        String w2 = (String) itr.next();
        webDriver.switchTo().window( w2 );
    }

    public void switchToOldWindow()
    {
        Set s = webDriver.getWindowHandles();
        Iterator itr = s.iterator();
        String w1 = (String) itr.next();
        String w2 = (String) itr.next();
        webDriver.switchTo().window( w1 );
    }

    public void switchToParentWindow()
    {
        webDriver.switchTo().defaultContent();
    }

    /*
     * public static String getMethodName() { String methodName = Thread.currentThread().getStackTrace()[1]
     * .getMethodName(); System.out.println(methodName); return methodName; }
     */

    public void waitForElement( WebElement element )
    {

        WebDriverWait wait = new WebDriverWait( webDriver, 10 );
        wait.until( ExpectedConditions.elementToBeClickable( element ) );
    }

    public void waitTillElementFound( WebElement ElementTobeFound, int seconds )
    {
        WebDriverWait wait = new WebDriverWait( webDriver, seconds );
        wait.until( ExpectedConditions.visibilityOf( ElementTobeFound ) );
    }

    public void setWindowSize( int Dimension1, int dimension2 )
    {
        webDriver.manage().window().setSize( new Dimension( Dimension1, dimension2 ) );

    }

    public static void pressKeyDown( WebElement element )
    {
        element.sendKeys( Keys.DOWN );
    }

    public void pressKeyEnter( WebElement element )
    {
        element.sendKeys( Keys.ENTER );
    }

    public static void pressKeyUp( WebElement element )
    {
        element.sendKeys( Keys.UP );
    }

    public static void moveToTab( WebElement element )
    {
        element.sendKeys( Keys.chord( Keys.ALT, Keys.TAB ) );
    }

    public void handleHTTPS_IEbrowser()
    {
        webDriver.navigate().to( "javascript:document.getElementById(‘overridelink’).click()" );
    }

    public void handleHTTPS_Firefox()
    {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates( false );
        webDriver = new FirefoxDriver( profile );
    }

    public void waitTillPageLoad( int i )
    {

        webDriver.manage().timeouts().pageLoadTimeout( i, TimeUnit.SECONDS );

    }

    public static void keyboardEvents( WebElement webelement, Keys key, String alphabet )
    {
        webelement.sendKeys( Keys.chord( key, alphabet ) );

    }

    public void navigate_forward()
    {
        webDriver.navigate().forward();
    }

    public void navigate_back()
    {
        webDriver.navigate().back();
    }

    public void refresh()
    {
        webDriver.navigate().refresh();
    }

    public void waitMyTime( int i )
    {
        webDriver.manage().timeouts().implicitlyWait( i, TimeUnit.SECONDS );

    }

    public static void clearTextField( WebElement element )
    {
        element.clear();

    }

    public static void clickWebelement( WebElement element )
    {
        try
        {
            boolean elementIsClickable = element.isEnabled();
            while( elementIsClickable )
            {
                element.click();
            }

        }
        catch( Exception e )
        {
            System.out.println( "Element is not enabled" );
            e.printStackTrace();
        }
    }

    public void clickMultipleElements( WebElement someElement, WebElement someOtherElement )
    {
        Actions builder = new Actions( webDriver );
        builder.keyDown( Keys.CONTROL ).click( someElement ).click( someOtherElement ).keyUp(
                Keys.CONTROL ).build().perform();
    }

    public void highlightelement( WebElement element )
    {
        for( int i = 0; i < 4; i++ )
        {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript( "arguments[0].setAttribute(‘style’, arguments[1]);", element,
                    "color: solid red; border: 6px solid yellow;" );
            js.executeScript( "arguments[0].setAttribute(‘style’, arguments[1]);", element, "" );

        }

    }

    public boolean checkAlert_Accept()
    {
        try
        {
            Alert a = webDriver.switchTo().alert();
            String str = a.getText();
            System.out.println( str );

            a.accept();
            return true;

        }
        catch( Exception e )
        {

            System.out.println( "no alert " );
            return false;

        }
    }

    public boolean checkAlert_Dismiss()
    {
        try
        {
            Alert a = webDriver.switchTo().alert();
            String str = a.getText();
            System.out.println( str );

            a.dismiss();
            return true;

        }
        catch( Exception e )
        {

            System.out.println( "no alert " );
            return false;

        }
    }

    //select the dropdown using "select by visible text", so pass VisibleText as 'Yellow' to funtion
    public static void fn_Select_VisibleText( WebElement WE, String VisibleText )
    {
        Select selObj = new Select( WE );
        selObj.selectByVisibleText( VisibleText );
    }

    //select the dropdown using "select by index", so pass IndexValue as '2'
    public static void fn_Select_ByIndex( WebElement WE, int IndexValue )
    {
        Select selObj = new Select( WE );
        selObj.selectByIndex( IndexValue );
    }

    //select the dropdown using "select by value", so pass Value as 'thirdcolor'
    public static void fn_Select_ByValue( WebElement WE, String Value )
    {
        Select selObj = new Select( WE );
        selObj.selectByValue( Value );
    }

    public static String fn_TakeSnapshot( WebDriver driver, String DestFilePath )
        throws IOException
    {
        String TS = CitiUtils.fn_GetTimeStamp();
        TakesScreenshot tss = (TakesScreenshot) driver;
        File srcfileObj = tss.getScreenshotAs( OutputType.FILE );
        DestFilePath = DestFilePath + TS + ".png";
        File DestFileObj = new File( DestFilePath );
        FileUtils.copyFile( srcfileObj, DestFileObj );
        return DestFilePath;
    }

    public static String fn_GetTimeStamp()
    {
        DateFormat DF = DateFormat.getDateTimeInstance();
        Date dte = new Date();
        String DateValue = DF.format( dte );
        DateValue = DateValue.replaceAll( ":", "_" );
        DateValue = DateValue.replaceAll( ",", "" );
        return DateValue;
    }

    public static void scrolltoElement( WebElement ScrolltoThisElement )
    {
        Coordinates coordinate = ( (Locatable) ScrolltoThisElement ).getCoordinates();
        coordinate.onPage();
        coordinate.inViewPort();
    }

    public static void checkbox_Checking( WebElement checkbox )
    {
        boolean checkstatus;
        checkstatus = checkbox.isSelected();
        if( checkstatus == true )
        {
            System.out.println( "Checkbox is already checkedUtils" );
        }
        else
        {
            checkbox.click();
            System.out.println( "Checked the checkbox" );
        }
    }

    public static void radiobutton_Select( WebElement Radio )
    {
        boolean checkstatus;
        checkstatus = Radio.isSelected();
        if( checkstatus == true )
        {
            System.out.println( "RadioButton is already checked" );
        }
        else
        {
            Radio.click();
            System.out.println( "Selected the Radiobutton" );
        }
    }

// Unchecking
    public static void checkbox_Unchecking( WebElement checkbox )
    {
        boolean checkstatus;
        checkstatus = checkbox.isSelected();
        if( checkstatus == true )
        {
            checkbox.click();
            System.out.println( "Checkbox is unchecked" );
        }
        else
        {
            System.out.println( "Checkbox is already unchecked" );
        }
    }

    public static void radioButton_Deselect( WebElement Radio )
    {
        boolean checkstatus;
        checkstatus = Radio.isSelected();
        if( checkstatus == true )
        {
            Radio.click();
            System.out.println( "Radio Button is deselected" );
        }
        else
        {
            System.out.println( "Radio Button was already Deselected" );
        }
    }

    public void dragAndDrop( WebElement fromWebElement, WebElement toWebElement )
    {
        Actions builder = new Actions( webDriver );
        builder.dragAndDrop( fromWebElement, toWebElement );
    }

    public void dragAndDrop_Method2( WebElement fromWebElement, WebElement toWebElement )
    {
        Actions builder = new Actions( webDriver );
        Action dragAndDrop = builder.clickAndHold( fromWebElement ).moveToElement( toWebElement ).release(
                toWebElement ).build();
        dragAndDrop.perform();
    }

    public void dragAndDrop_Method3( WebElement fromWebElement, WebElement toWebElement )
        throws InterruptedException
    {
        Actions builder = new Actions( webDriver );
        builder.clickAndHold( fromWebElement ).moveToElement( toWebElement ).perform();
        Thread.sleep( 2000 );
        builder.release( toWebElement ).build().perform();
    }

    public void hoverWebelement( WebElement HovertoWebElement )
        throws InterruptedException
    {
        Actions builder = new Actions( webDriver );
        builder.moveToElement( HovertoWebElement ).perform();
        Thread.sleep( 2000 );

    }

    public void doubleClickWebelement( WebElement doubleclickonWebElement )
        throws InterruptedException
    {
        Actions builder = new Actions( webDriver );
        builder.doubleClick( doubleclickonWebElement ).perform();
        Thread.sleep( 2000 );

    }

    public static String getToolTip( WebElement toolTipofWebElement )
        throws InterruptedException
    {
        String tooltip = toolTipofWebElement.getAttribute( "title" );
        System.out.println( "Tool text : " + tooltip );
        return tooltip;
    }

    public static void selectElementByNameMethod( WebElement element, String Name )
    {
        Select selectitem = new Select( element );
        selectitem.selectByVisibleText( Name );
    }

    public static void selectElementByValueMethod( WebElement element, String value )
    {
        Select selectitem = new Select( element );
        selectitem.selectByValue( value );
    }

    public static void selectElementByIndexMethod( WebElement element, int index )
    {
        Select selectitem = new Select( element );
        selectitem.selectByIndex( index );
    }

    public void clickCheckboxFromList( String xpathOfElement, String valueToSelect )
    {

        List<WebElement> lst = webDriver.findElements( By.xpath( xpathOfElement ) );
        for( int i = 0; i < lst.size(); i++ )
        {
            List<WebElement> dr = lst.get( i ).findElements( By.tagName( "label" ) );
            for( WebElement f : dr )
            {
                System.out.println( "value in the list : " + f.getText() );
                if( valueToSelect.equals( f.getText() ) )
                {
                    f.click();
                    break;
                }
            }
        }
    }

    public void downloadFile( String href, String fileName )
        throws Exception
    {
        URL url = null;
        URLConnection con = null;
        int i;
        url = new URL( href );
        con = url.openConnection();
        File file = new File( ".//OutputData//" + fileName );
        BufferedInputStream bis = new BufferedInputStream( con.getInputStream() );
        BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream( file ) );
        while( ( i = bis.read() ) != -1 )
        {
            bos.write( i );
        }
        bos.flush();
        bis.close();
    }

    public void navigateToEveryLinkInPage()
        throws InterruptedException
    {

        List<WebElement> linksize = webDriver.findElements( By.tagName( "a" ) );
        int linksCount = linksize.size();
        System.out.println( "Total no of links Available: " + linksCount );
        String[] links = new String[linksCount];
        System.out.println( "List of links Available: " );
// print all the links from webpage
        for( int i = 0; i < linksCount; i++ )
        {
            links[i] = linksize.get( i ).getAttribute( "href" );
            System.out.println( linksize.get( i ).getAttribute( "href" ) );
        }
// navigate to each Link on the webpage
        for( int i = 0; i < linksCount; i++ )
        {
            webDriver.navigate().to( links[i] );
            Thread.sleep( 3000 );
            System.out.println( webDriver.getTitle() );
        }
    }

    public void Select_The_CheckBox_from_List( WebElement element, String valueToSelect )
    {
        List<WebElement> allOptions = element.findElements( By.tagName( "input" ) );
        for( WebElement option : allOptions )
        {
            System.out.println( "Option value " + option.getText() );
            if( valueToSelect.equals( option.getText() ) )
            {
                option.click();
                break;
            }
        }
    }

    public void Select_The_Checkbox( WebElement element )
    {
        try
        {
            if( element.isSelected() )
            {
                System.out.println( "Checkbox: " + element + "is already selected" );
            }
            else
            {
                // Select the checkbox
                element.click();
            }
        }
        catch( Exception e )
        {
            System.out.println( "Unable to select the checkbox: " + element );
        }

    }

    public void DeSelect_The_Checkbox( WebElement element )
    {
        try
        {
            if( element.isSelected() )
            {
                //De-select the checkbox
                element.click();
            }
            else
            {
                System.out.println( "Checkbox: " + element + "is already deselected" );
            }
        }
        catch( Exception e )
        {
            System.out.println( "Unable to deselect checkbox: " + element );
        }
    }
}
