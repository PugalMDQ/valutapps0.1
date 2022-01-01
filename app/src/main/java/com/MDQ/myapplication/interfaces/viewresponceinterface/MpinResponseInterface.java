package com.MDQ.myapplication.interfaces.viewresponceinterface;

import com.MDQ.myapplication.interfaces.StateViewInterface;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;

//Response interface for set mpin
public interface MpinResponseInterface extends StateViewInterface {
    void generateMpinProcessed(String Msg );
    void onFailure(ErrorBody errorBody, int statusCode);

}
