
package com.taugame.tau.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.taugame.tau.client.TauService;
import com.taugame.tau.shared.Card;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TauServiceImpl extends RemoteServiceServlet implements TauService {

    @Override
    public Boolean joinGame(String game) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setReady(boolean ready) {
        // TODO Auto-generated method stub

    }

    @Override
    public void submit(Card card1, Card card2, Card card3) {
        // TODO Auto-generated method stub

    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html
     *            the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }
}
