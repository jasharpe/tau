package com.taugame.tau.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sun.grizzly.comet.CometContext;
import com.sun.grizzly.comet.CometEngine;
import com.sun.grizzly.comet.CometEvent;
import com.sun.grizzly.comet.CometHandler;
import com.taugame.tau.client.TauService;
import com.taugame.tau.shared.Card;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TauServiceImpl extends RemoteServiceServlet implements TauService {
    private static final String BEGIN_SCRIPT_TAG = "<script type='text/javascript'>\n";
    private static final String END_SCRIPT_TAG = "</script>\n";
    private static final Logger logger = Logger.getLogger("grizzly");
    private String contextPath;
    private GameMaster gm;
    private HashMap<String, String> names;
    private int c = 0;

    private static final String JUNK =
        "<!-- Comet is a programming technique that enables web " +
        "servers to send data to the client without having any need " +
        "for the client to request it. -->\n";

    public class TauCometHandler implements CometHandler<HttpServletResponse> {
        HttpServletResponse response;

        @Override
        public void attach(HttpServletResponse attachment) {
            this.response = attachment;
        }

        @Override
        public void onEvent(CometEvent event) throws IOException {
            String output = (String) event.attachment();
            logger.info("CometEvent.NOTIFY => {}" + output);
            PrintWriter writer = response.getWriter();
            writer.println(output);
            writer.flush();
        }

        @Override
        public void onInitialize(CometEvent event) throws IOException {}

        @Override
        public void onInterrupt(CometEvent event) throws IOException {
            String script = BEGIN_SCRIPT_TAG + "window.parent.l();\n" + END_SCRIPT_TAG;
            logger.info("CometEvent.INTERRUPT => {}" + script);
            PrintWriter writer = response.getWriter();
            writer.println(script);
            writer.flush();
            removeThisFromContext();
        }

        @Override
        public void onTerminate(CometEvent event) throws IOException {
            removeThisFromContext();
        }

        private void removeThisFromContext() throws IOException {
            response.getWriter().close();
            CometContext context = CometEngine.getEngine().getCometContext(contextPath);
            context.removeCometHandler(this);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        gm = new GameMaster();
        names = new HashMap<String, String>();
        contextPath = config.getServletContext().getContextPath() + "game";
        CometContext context = CometEngine.getEngine().register(contextPath);
        context.setExpirationDelay(60 * 60 * 1000);
    }

    @Override
    synchronized protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setHeader("Cache-Control", "private");
        resp.setHeader("Pragma", "no-cache");

        // For IE, Safari and Chrome, we must output some junk to enable streaming
        PrintWriter writer = resp.getWriter();
        for (int i = 0; i < 10; i++) {
            resp.getWriter().write(JUNK);
        }
        writer.flush();

        TauCometHandler handler = new TauCometHandler();
        handler.attach(resp);
        CometContext context = CometEngine.getEngine().getCometContext(contextPath);
        context.addCometHandler(handler);
    }

    @Override
    synchronized public Boolean joinAs(String name) {
//        if (gm.joinAs(getID())) {
//            names.put(getID(), getID());
        logger.info("a");
            CometContext context = CometEngine.getEngine().getCometContext(contextPath);
            try {
                context.notify(BEGIN_SCRIPT_TAG + toJson(gm.getBoard()) + END_SCRIPT_TAG);
            } catch (Exception e) {
                logger.info(e.toString());
            }
            return true;
//        } else {
//            return false;
//        }
    }

    private String toJson(Iterable<Card> board) {
        StringBuilder sb = new StringBuilder();
        sb.append("window.parent.u({\"c\":" + (c++) + ",\"b\":[");
        boolean first = true;
        for (Card card : board) {
            if (!first) {sb.append(",");}
            sb.append(card.toString());
            first = false;
        }
        sb.append("]});\n");
        return sb.toString();
    }

    @Override
    synchronized public void setReady(boolean ready) {
        gm.setReady(getName(), ready);
    }

    @Override
    synchronized public void submit(Card card1, Card card2, Card card3) {
        Iterable<Card> board = gm.submit(getName(), card1, card2, card3);
        if (board != null) {
            try {
                CometContext context = CometEngine.getEngine().getCometContext(contextPath);
                context.notify(BEGIN_SCRIPT_TAG + toJson(gm.getBoard()) + END_SCRIPT_TAG);
            } catch (IOException e) {
                logger.info(e.toString());
            }
        }
    }

    private String getName() {
        return names.get(getID());
    }

    private String getID() {
        return this.getThreadLocalRequest().getSession().getId();
    }

}
