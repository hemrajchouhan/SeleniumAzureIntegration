package com.citi.qa.testcases;

import com.citi.qa.core.base.PageObject;
import org.testng.annotations.Test;

/**
 * @author Chouhan_h TEST WITH FIREFOX BROWSER
 */

public class FirefoxTests
    extends PageObject
{

    @Test
    public void formRegisterTest()
        throws InterruptedException
    {
        FormRegisterTest formRegisterTest = new FormRegisterTest( webDriver );
        formRegisterTest.initilization();
    }

    @Test
    public void dragAndDropTest()
        throws InterruptedException
    {
        DragAndDropTest dragNDropFunction = new DragAndDropTest( webDriver );
        dragNDropFunction.initilization();
    }

    @Test
    public void editorGridTest()
        throws InterruptedException
    {
        EditorGridTest editorGridTest = new EditorGridTest( webDriver );
        editorGridTest.initilization();
    }

}
