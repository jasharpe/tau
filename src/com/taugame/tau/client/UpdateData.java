package com.taugame.tau.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.taugame.tau.shared.Card;
import java.util.ArrayList;
import java.util.List;

//{ 'c': '32', 'b': [[1,2,3,4],[2,3,1,0],...], 'u': 'ara' }
public final class UpdateData extends JavaScriptObject {
    protected UpdateData() {}
    
    /**
     * Returns an UpdateData matching jsonStr.
     */
    public static native UpdateData parseUpdateDataJSON(String jsonStr) /*-{
      return eval("(" + jsonStr + ")");
    }-*/;
    
    public native int getCounter() /*-{
        return this.c;
    }-*/;
    
    public List<Card> getCards() {
        JsArray<CardData> cardDatas = getCardDatas();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < cardDatas.length(); i++) {
            cards.add(cardDatas.get(i).toCard());
        }
        return cards;
    }
    
    private native JsArray<CardData> getCardDatas() /*-{
        return this.b;
    }-*/;
    
    public native String getUser() /*-{
        return this.u;
    }-*/;
}
