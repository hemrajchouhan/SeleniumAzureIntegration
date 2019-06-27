package com.citi.qa.core.base;

import com.citi.qa.util.Props;
import com.citi.qa.util.WebEventListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import lombok.Getter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WebDriverHelper
    extends EventFiringWebDriver
{
    private static final Logger LOG = LoggerFactory.getLogger( WebDriverHelper.class );

    private static RemoteWebDriver REAL_DRIVER = null;

    private static final Thread CLOSE_THREAD = new Thread()
    {

        @Override
        public void run()
        {
            WebDriverHelper.REAL_DRIVER.quit();
        }
    };

    private static String BROWSER;

    private static String PLATFORM;

    private static String DRIVER_PATH;

    private static String DRIVER_ROOT_DIR;

    private static String SELENIUM_HOST;

    private static String SELENIUM_PORT;

    private static String SELENIUM_REMOTE_URL;

    private static Dimension BROWSER_WINDOW_SIZE;

    public static EventFiringWebDriver e_driver;

    public static WebEventListener eventListener;

    @Getter
    protected static WebDriver webDriver;

    static
    {
        Props.loadRunConfigProps( "/environment.properties" );
        WebDriverHelper.SELENIUM_HOST = Props.getProp( "driverhost" );
        WebDriverHelper.SELENIUM_PORT = Props.getProp( "driverport" );
        WebDriverHelper.PLATFORM = Props.getProp( "platform" );
        WebDriverHelper.BROWSER = Props.getProp( "browser" );
        WebDriverHelper.DRIVER_ROOT_DIR = Props.getProp( "driver.root.dir" );

        if( !WebDriverHelper.DRIVER_ROOT_DIR.equals( "DEFAULT_PATH" ) )
        {
            System.setProperty( "webdriver.gecko.driver", WebDriverHelper.getDriverPath() );
            System.setProperty( "webdriver.chrome.driver", WebDriverHelper.getDriverPath() );
            System.setProperty( "webdriver.ie.driver", WebDriverHelper.getDriverPath() );
        }

        try
        {
            switch( WebDriverHelper.BROWSER.toLowerCase() )
            {
            case "chrome":
                WebDriverHelper.startChromeDriver();
                break;
            case "firefox":
                WebDriverHelper.startFireFoxDriver();
                break;
            case "iexplore":
                WebDriverHelper.startIEDriver();
                break;

            default:
                throw new IllegalArgumentException( "Browser " + WebDriverHelper.BROWSER + " or Platform "
                        + WebDriverHelper.PLATFORM + " type not supported" );
            }

        }
        catch( IllegalStateException e )
        {
            WebDriverHelper.LOG.error( "FIX path for driver.root.dir in pom.xml " + WebDriverHelper.DRIVER_ROOT_DIR
                    + " Browser parameter " + WebDriverHelper.BROWSER + " Platform parameter "
                    + WebDriverHelper.PLATFORM + " type not supported" );
        }
        Runtime.getRuntime().addShutdownHook( WebDriverHelper.CLOSE_THREAD );
    }

    private WebDriverHelper()
    {
        super( WebDriverHelper.REAL_DRIVER );
    }

    private static String getDriverPath()
    {
        WebDriverHelper.DRIVER_PATH = Props.getProp( "driver.root.dir" );
        return WebDriverHelper.DRIVER_PATH;
    }

    private static void startIEDriver()
    {
        DesiredCapabilities capabilities = WebDriverHelper.getInternetExploreDesiredCapabilities();
        if( WebDriverHelper.SELENIUM_HOST == null || WebDriverHelper.SELENIUM_HOST.isEmpty() )
        {
            WebDriverHelper.REAL_DRIVER = new InternetExplorerDriver( capabilities );
        }
        else
        {
            try
            {
                WebDriverHelper.REAL_DRIVER = WebDriverHelper.getRemoteWebDriver( capabilities );
            }
            catch( MalformedURLException e )
            {
                WebDriverHelper.LOG.error( WebDriverHelper.SELENIUM_REMOTE_URL + " Error " + e.getMessage() );
            }
        }
        WebDriverHelper.REAL_DRIVER.manage().window().setSize( WebDriverHelper.BROWSER_WINDOW_SIZE );
    }

    private static void startFireFoxDriver()
    {
        DesiredCapabilities capabilities = WebDriverHelper.getFireFoxDesiredCapabilities();
        if( WebDriverHelper.SELENIUM_HOST == null || WebDriverHelper.SELENIUM_HOST.isEmpty() )
        {
            WebDriverHelper.REAL_DRIVER = new FirefoxDriver();
        }
        else
        {
            try
            {
                WebDriverHelper.REAL_DRIVER = WebDriverHelper.getRemoteWebDriver( capabilities );
            }
            catch( MalformedURLException e )
            {
                WebDriverHelper.LOG.error( WebDriverHelper.SELENIUM_REMOTE_URL + " Error " + e.getMessage() );
            }
        }
        WebDriverHelper.REAL_DRIVER.manage().window().setSize( WebDriverHelper.BROWSER_WINDOW_SIZE );
    }

    @SuppressWarnings( "deprecation" )
    private static WebDriver startChromeDriver()
    {
        DesiredCapabilities capabilities = WebDriverHelper.getChromeDesiredCapabilities();

        if( WebDriverHelper.SELENIUM_HOST == null || WebDriverHelper.SELENIUM_HOST.isEmpty() )
        {
            WebDriverHelper.REAL_DRIVER = new ChromeDriver( ChromeDriverService.createDefaultService(), capabilities );
        }
        else
        {
            try
            {
                WebDriverHelper.REAL_DRIVER = WebDriverHelper.getRemoteWebDriver( capabilities );
            }
            catch( MalformedURLException e )
            {
                WebDriverHelper.LOG.error( WebDriverHelper.SELENIUM_REMOTE_URL + " Error " + e.getMessage() );
            }
        }

        return WebDriverHelper.REAL_DRIVER;
    }

    private static DesiredCapabilities getChromeDesiredCapabilities()
    {

        LoggingPreferences logs = new LoggingPreferences();
        logs.enable( LogType.DRIVER, Level.OFF );
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability( CapabilityType.LOGGING_PREFS, logs );
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments( "--disable-extensions" );
        chromeOptions.addArguments( "--start-maximized" );
        chromeOptions.addArguments( "--test-type" );
        capabilities.setCapability( "chrome.verbose", false );

        capabilities.setCapability( ChromeOptions.CAPABILITY, chromeOptions );
        return capabilities;
    }

    private static DesiredCapabilities getFireFoxDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability( CapabilityType.ACCEPT_SSL_CERTS, true );
        capabilities.setBrowserName( "firefox" );

        capabilities.setCapability( "disable-restore-session-state", true );
        return capabilities;

    }

    private static DesiredCapabilities getInternetExploreDesiredCapabilities()
    {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable( LogType.DRIVER, Level.OFF );
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability( CapabilityType.LOGGING_PREFS, logs );
        capabilities.setCapability( InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true );
        capabilities.setVersion( "9" );
        return capabilities;
    }

    private static RemoteWebDriver getRemoteWebDriver( DesiredCapabilities capabilities )
        throws MalformedURLException
    {
        WebDriverHelper.SELENIUM_REMOTE_URL = "http://" + WebDriverHelper.SELENIUM_HOST + ":"
                + WebDriverHelper.SELENIUM_PORT + "/wd/hub";
        WebDriverHelper.LOG.info( WebDriverHelper.SELENIUM_REMOTE_URL + " Checking Selenium Remote URL" );
        return new RemoteWebDriver( new URL( WebDriverHelper.SELENIUM_REMOTE_URL ), capabilities );
    }

    public static WebDriver getWebDriver()
    {

        return WebDriverHelper.REAL_DRIVER;
    }

    public static void resizeBrowserWindow( Dimension dimension )
    {
        WebDriverHelper.getWebDriver().manage().window().setSize( dimension );
    }

    @Override
    public void close()
    {
        if( Thread.currentThread() != WebDriverHelper.CLOSE_THREAD )
        {
            throw new UnsupportedOperationException(
                    "You shouldn't close this WebDriver. It's shared and will close when the JVM exits." );
        }
        super.close();
    }
}
