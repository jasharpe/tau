package com.taugame.tau.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.taugame.tau.shared.Card;

/**
 * The async counterpart of <code>TauService</code>.
 */
public interface TauServiceAsync {
  void joinAs(String name, AsyncCallback<Boolean> callback);
  void setReady(boolean ready, AsyncCallback<Void> callback);
  void submit(Card card1, Card card2, Card card3, AsyncCallback<Void> callback);
}
