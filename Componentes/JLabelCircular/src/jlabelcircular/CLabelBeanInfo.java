package jlabelcircular;
import java.awt.Image;
import java.beans.SimpleBeanInfo;
/**
 * @web http://www.jc-mouse.net/
 * @author Mouse
 */
public class CLabelBeanInfo  extends SimpleBeanInfo{
 
    Image icon;
    Image icon32;
    Image iconM;
    Image icon32M;

    public CLabelBeanInfo(){        
            icon = loadImage("/com/bolivia/res/ico16.gif");
            icon32 = loadImage("/com/bolivia/res/ico32.gif");
            iconM = loadImage("/com/bolivia/res/ico16m.gif");
            icon32M = loadImage("/com/bolivia/res/ico32m.gif");        
    }
 
   @Override
   public Image getIcon(int i)
    {
        switch(i)
        {
        case 1:
            return icon;

        case 2:
            return icon32;

        case 3:
            return iconM;

        case 4:
            return icon32M;
        }
        return null;
    }
}
