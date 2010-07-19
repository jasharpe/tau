package com.taugame.tau.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.taugame.tau.shared.Card;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface TauService extends RemoteService {
    Boolean joinGame(String game);
    void setReady(boolean ready);
    void submit(Card card1, Card card2, Card card3);
}
