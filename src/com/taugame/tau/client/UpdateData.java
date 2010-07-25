package com.taugame.tau.client;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.taugame.tau.shared.Card;

//{ 'c': '32', 'b': [[1,2,3,4],[2,3,1,0],...], 'u': 'ara' }
public final class UpdateData extends JavaScriptObject {
    protected UpdateData() {}

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

    public native boolean isLobbyUpdate() /*-{
        return this.t == "l";
    }-*/;

    public native boolean isBoardUpdate() /*-{
        return this.t == "b";
    }-*/;

    public native boolean isEndUpdate() /*-{
        return this.t == "e";
    }-*/;

    public native JsArray<ScoreData> getScoreList() /*-{
        return this.s;
    }-*/;

    public SortedMap<String, Integer> getScoreMap() {
        JsArray<ScoreData> scoreArray = getScoreList();
        SortedMap<String, Integer> scoreMap = new TreeMap<String, Integer>();
        for (int i = 0; i < scoreArray.length(); i++) {
            ScoreData scoreData = scoreArray.get(i);
            scoreMap.put(scoreData.getName(), scoreData.getScore());
        }
        return scoreMap;
    }
}
