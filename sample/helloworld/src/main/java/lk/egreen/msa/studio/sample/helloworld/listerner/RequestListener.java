package lk.egreen.msa.studio.sample.helloworld.listerner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by dewmal on 8/20/16.
 */
@WebListener
public class RequestListener implements ServletRequestListener {

    private static final Logger LOGGER = LogManager.getLogger(RequestListener.class);

    public void requestDestroyed(ServletRequestEvent sre) {
        LOGGER.info(sre.getServletContext());
    }

    public void requestInitialized(ServletRequestEvent sre) {
        LOGGER.info(sre.getServletContext());
    }
}
