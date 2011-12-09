/*
 * BiblioAdmin2App.java
 */

package biblioadmin2;

import com.db4o.ObjectSet;
import managers.DBManager;
import managers.SocioManager;
import models.Socio;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class BiblioAdmin2App extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {

       show(new BiblioAdmin2View(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of BiblioAdmin2App
     */
    public static BiblioAdmin2App getApplication() {
        return Application.getInstance(BiblioAdmin2App.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(BiblioAdmin2App.class, args);
    }
}
