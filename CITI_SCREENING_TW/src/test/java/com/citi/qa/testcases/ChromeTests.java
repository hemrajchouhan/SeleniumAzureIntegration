package com.citi.qa.testcases;

import com.citi.qa.core.base.PageObject;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @author Chouhan_h TEST WITH CHROME BROWSER
 */

public class ChromeTests
    extends PageObject
{

    @Test
    public void formRegisterTest()
        throws InterruptedException
    {
        Reporter.log( "Test is started" );
        FormRegisterTest formRegisterTest = new FormRegisterTest( webDriver );
        formRegisterTest.initilization();
    }

    @Test
    public void dragAndDropTest()
        throws InterruptedException
    {
        //Chrome incompatibility https://bugs.chromium.org/p/chromedriver/issues/detail?id=841
        DragAndDropTest dragandDroptest = new DragAndDropTest( webDriver );
        dragandDroptest.initilization();
    }

    @Test
    public void editorGridTest()
        throws InterruptedException
    {
        EditorGridTest editorGridTest = new EditorGridTest( webDriver );
        editorGridTest.initilization();
    }

}
