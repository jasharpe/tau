
package com.taugame.tau.server;

import java.util.HashMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.taugame.tau.client.TauService;
import com.taugame.tau.shared.Card;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TauServiceImpl extends RemoteServiceServlet implements TauService {
    private GameMaster gm;
    private HashMap<String, String> names;

    @Override
    public void init() throws javax.servlet.ServletException {
        gm = new GameMaster();
        names = new HashMap<String, String>();
    };

    @Override
    synchronized public Boolean joinAs(String name) {
        if (gm.joinAs(name)) {
            names.put(getName(), name);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    synchronized public void setReady(boolean ready) {
        String name = names.get(getName());
        gm.setReady(name, ready);
    }

    @Override
    synchronized public void submit(Card card1, Card card2, Card card3) {
        gm.submit(getName(), card1, card2, card3);
    }

    private String getName() {
        return this.getThreadLocalRequest().getSession().getId();
    }

}
