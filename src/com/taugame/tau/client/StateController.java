package com.taugame.tau.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class StateController {
    private final TauServiceAsync tauService = GWT.create(TauService.class);
    private final Map<State, View> views = new HashMap<State, View>();
    private final RootPanel gameContainer;

    private String user;
    private State state;

    public StateController(RootPanel gameContainer) {
        this.state = State.NONE;
        this.gameContainer = gameContainer;
    }

    private void initializeGame() {
        View gameView = views.get(State.GAME_IN_PROGRESS);
        if (gameView == null) {
            LobbyModel gameLobby = new LobbyModel(tauService, this);
            GameModel gameModel = new GameModel(tauService, this);
            GameMessageHandler gameMessageHandler =
                new GameMessageHandler(gameLobby, gameModel);
            CometMessageHandler.setUpdateListener(gameMessageHandler);
            CometMessageHandler.setEndGameListener(gameMessageHandler);
            gameView = gameModel.getView();
            views.put(State.GAME_IN_PROGRESS, gameView);
            views.put(State.GAME_LOBBY, gameLobby.getView());
        }
    }

    // forces a change of state despite a lack of control
    public void forceChangeState(State toState) {
        changeState(state, toState);
    }

    public void changeState(State fromState, State toState) {
        // prevent already stale states from grabbing control.
        if (fromState != state) {
            return;
        }

        if (toState == State.NONE) {
            user = null;
            gameContainer.clear();
            views.clear();
            state = toState;
            changeState(State.NONE, State.START);
        } else if (toState == State.START) {
            initializeGame();
            state = toState;
            tauService.join(new AsyncCallback<String>() {
                @Override public void onSuccess(String userName) {
                    if (userName == null) {
                        changeState(State.START, State.ENTER_NAME);
                    } else {
                        user = userName;
                        changeState(State.START, State.GAME_STATE_UNKNOWN);
                    }
                }

                @Override public void onFailure(Throwable caught) {
                    throw new RuntimeException("Failed to join", caught);
                }
            });
        } else if (toState == State.ENTER_NAME) {
            state = toState;
            View nameView = views.get(State.ENTER_NAME);
            if (nameView == null) {
                nameView = new NameModel(tauService, this).getView();
                views.put(State.ENTER_NAME, nameView);
            }
            gameContainer.clear();
            gameContainer.add(nameView.getWidget());
        } else if (toState == State.GAME_STATE_UNKNOWN) {
            state = toState;
            View waitingView = views.get(State.GAME_STATE_UNKNOWN);
            if (waitingView == null) {
                waitingView = new WaitingView();
                views.put(State.GAME_STATE_UNKNOWN, waitingView);
            }
            gameContainer.clear();
            gameContainer.add(waitingView.getWidget());
        } else if (toState == State.GAME_LOBBY) {
            state = toState;
            initializeGame();
            View lobbyView = views.get(State.GAME_LOBBY);
            gameContainer.clear();
            gameContainer.add(lobbyView.getWidget());
        } else if (toState == State.GAME_IN_PROGRESS) {
            state = toState;
            initializeGame();
            View gameView = views.get(State.GAME_IN_PROGRESS);
            gameContainer.clear();
            gameContainer.add(gameView.getWidget());
        } else if (toState == State.GAME_OVER) {
            state = toState;
            View gameOverView = views.get(State.GAME_OVER);
            if (gameOverView == null) {
                gameOverView = new GameOverModel(tauService, this).getView();
                views.put(State.GAME_OVER, gameOverView);
            }
            gameContainer.clear();
            gameContainer.add(gameOverView.getWidget());
        }
    }

    public enum State {
        NONE, START, ENTER_NAME, GAME_STATE_UNKNOWN, GAME_LOBBY, GAME_IN_PROGRESS, GAME_OVER;
    }
}
