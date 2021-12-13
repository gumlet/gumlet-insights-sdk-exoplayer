package com.gumlet.insights.calls.presenter;

import com.gumlet.insights.PlayerInstance;
import com.gumlet.insights.PlayerNetworkRequest;
import com.gumlet.insights.calls.api.ApiClient;
import com.gumlet.insights.calls.api.ServiceApi;
import com.gumlet.insights.calls.events.PlayerEvents;
import com.gumlet.insights.utils.Util;

import rx.schedulers.Schedulers;

public class PlayerInstancePresenter extends BaseEventPresenter<PlayerEvents>{
    private ServiceApi serviceApi;

    public PlayerInstancePresenter() {
        serviceApi = ApiClient.getClient().create(ServiceApi.class);
    }

    public void playerInit(PlayerInstance playerInstance, PlayerEvents listener){

        try{
            serviceApi.playerInstance(
                    /*"Bearer "+ ApiConfig.BEARER_TOKEN,*/
                    "player_init",
                    playerInstance.getPlayerInstanceId(),
                    playerInstance.getPropertyId(),
                    playerInstance.getSessionId(),
                    playerInstance.getUserId(),
                    playerInstance.getCustomUserId(),
                    playerInstance.getPlayerHeightPixels(),
                    playerInstance.getPlayerWidthPixels(),
                    playerInstance.getMetaPageType(),
                    playerInstance.getMetaPageUrl(),
                    playerInstance.getPlayerSoftware(),
                    playerInstance.getPlayerLanguageCode(),
                    playerInstance.getPlayerName(),
                    playerInstance.getError(),
                    playerInstance.getErrorCode(),
                    playerInstance.getErrorText(),
                    playerInstance.getCustomData1(),
                    playerInstance.getCustomData2(),
                    playerInstance.getCustomData3(),
                    playerInstance.getCustomData4(),
                    playerInstance.getCustomData5(),
                    playerInstance.getCustomData6(),
                    playerInstance.getCustomData7(),
                    playerInstance.getCustomData8(),
                    playerInstance.getCustomData9(),
                    playerInstance.getCustomData10(),
                    playerInstance.getPlayerIntegrationVersion(),
                    playerInstance.getPlayerSoftwareVersion(),
                    playerInstance.getPlayerPreload(),
                    playerInstance.getPlayerAutoPlay(),
                    Util.getTimeStampInMs()
            ).subscribeOn(Schedulers.newThread()).subscribe();

            listener.onPlayerInitSuccess();

        }catch (Exception ex){

        }


    }

    public void network_request(PlayerNetworkRequest playerNetworkRequest,
                            PlayerEvents listener){

        try{
            serviceApi.playerEvents(
                    "network_request",
                    playerNetworkRequest.getRequestId(),
                    playerNetworkRequest.getSessionId(),
                    playerNetworkRequest.getUserId(),
                    playerNetworkRequest.getPropertyId(),
                    playerNetworkRequest.getPlayerInstanceId(),
                    playerNetworkRequest.getRequestStart(),
                    playerNetworkRequest.getRequestResponseStart(),
                    playerNetworkRequest.getRequestResponseEnd(),
                    playerNetworkRequest.getRequestType(),
                    playerNetworkRequest.getRequestHostName(),
                    playerNetworkRequest.getRequestByteLoaded(),
                    playerNetworkRequest.getRequestResponseHeaders(),
                    playerNetworkRequest.getRequestMediaDurationMillis(),
                    playerNetworkRequest.getRequestVideoWidthPixels(),
                    playerNetworkRequest.getRequestVideoHeightPixels(),
                    playerNetworkRequest.getError(),
                    playerNetworkRequest.getErrorCode(),
                    playerNetworkRequest.getErrorText(),
                    Util.getTimeStampInMs()

            ).subscribeOn(Schedulers.newThread()).subscribe();

            listener.onPlayerEventSuccess(playerNetworkRequest.getRequestId());

        }catch (Exception ex){

        }


    }

}
