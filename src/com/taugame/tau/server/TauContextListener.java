package com.taugame.tau.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TauContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().getSessionCookieConfig().setMaxAge(Integer.MAX_VALUE);
    }

}
