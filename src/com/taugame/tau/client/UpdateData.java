package com.taugame.tau.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.taugame.tau.shared.Card;

//{ 'c': '32', 'b': [[1,2,3,4],[2,3,1,0],...], 'u': 'ara' }
public final class UpdateData extends JavaScriptObject {
    protected UpdateData() {}

    public native String getType() /*-{
        return this.t;
    }-*/;

    public native int getCounter() /*-{
        return this.c;
    }-*/;

    public List<Card> getCards() {
        JsArray<CardData> cardDatas = getCardDatas();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < cardDatas.length(); i++) {
            CardData cardData = cardDatas.get(i);
            if (cardData == null) {
                cards.add(null);
            } else {
                cards.add(cardData.toCard());
            }
        }
        return cards;
    }

    private native JsArray<CardData> getCardDatas() /*-{
        return this.b;
    }-*/;

    public native String getUser() /*-{
        return this.u;
    }-*/;

    public native boolean isLobbyUpdate() /*-{
        return this.t == "l";
    }-*/;

    public native boolean isBoardUpdate() /*-{
        return this.t == "b";
    }-*/;

    public native boolean isEndUpdate() /*-{
        return this.t == "e";
    }-*/;

    public native JsArray<ScoreData> getNativeScoreList() /*-{
        return this.s;
    }-*/;

    public List<SimpleImmutablePair<String, Integer>> getScoreList() {
        JsArray<ScoreData> scoreArray = getNativeScoreList();
        List<SimpleImmutablePair<String, Integer>> scoreList =
            new ArrayList<SimpleImmutablePair<String, Integer>>();
        for (int i = 0; i < scoreArray.length(); i++) {
            ScoreData scoreData = scoreArray.get(i);
            scoreList.add(new SimpleImmutablePair<String, Integer>(scoreData.getName(), scoreData.getScore()));
        }
        return scoreList;
    }
}
