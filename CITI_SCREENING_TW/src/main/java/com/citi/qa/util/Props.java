package com.citi.qa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Props
{
    private static final Logger LOG = LoggerFactory.getLogger( Props.class );

    private static Properties environmentProps;

    private static Properties properties;

    /**
     * Gets the key from messages.properties for a Site
     *
     * @param key
     **/
    public static String getMessage( String key )
    {

        if( key == null || key.isEmpty() )
        {
            return "";
        }
        return ResourceBundle.getBundle( "props/messages" ).getString( key );
    }

    /**
     * Gets the key from Config.properties related to chosen profile
     *
     * @param key
     **/

    public static String getProp( String key )
    {
        if( key == null || key.isEmpty() )
        {
            return "";
        }
        return Props.properties.getProperty( key );
    }

    public static void loadRunConfigProps( String configPropertyFileLocation )
    {
        Props.environmentProps = new Properties();
        try (InputStream inputStream = Props.class.getResourceAsStream( configPropertyFileLocation ))
        {
            Props.environmentProps.load( inputStream );
            Props.environmentProps.list( System.out );
        }
        catch( IOException e )
        {
            Props.LOG.error( e.getMessage() );
        }
        Props.properties = new Properties();
        try (InputStream inputStream = Props.class.getResourceAsStream(
                Props.environmentProps.getProperty( "profile.path" ) ))
        {
            Props.properties.load( inputStream );
            Props.properties.list( System.out );
        }
        catch( IOException e )
        {
            Props.LOG.error( e.getMessage() );
        }
    }
}
