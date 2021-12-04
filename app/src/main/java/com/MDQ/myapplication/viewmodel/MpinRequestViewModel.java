package com.MDQ.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.MDQ.myapplication.datamanager.MpinDataManager;
import com.MDQ.myapplication.datamanager.RegisterDataManager;
import com.MDQ.myapplication.interfaces.ResponseHandler;
import com.MDQ.myapplication.interfaces.viewinterface.MpinRequestInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.MpinResponseInterface;
import com.MDQ.myapplication.interfaces.viewresponceinterface.RegisterResponseInterface;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateMpinRequestModel;
import com.MDQ.myapplication.pojo.jsonrequest.GenerateRegisterRequestModel;
import com.MDQ.myapplication.pojo.jsonresponse.ErrorBody;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateMpinResponseModel;
import com.MDQ.myapplication.pojo.jsonresponse.GenerateRegisterResponseModel;
import com.MDQ.myapplication.utils.ApiClass;

public class MpinRequestViewModel extends MpinRequestBaseViewModel implements MpinRequestInterface {

    private MpinDataManager mpinDataManager;
    private MpinResponseInterface mpinResponseInterface;
    private Context mContext;

    public MpinRequestViewModel(Context mContext, MpinResponseInterface mpinResponseInterface) {
        this.mpinResponseInterface = mpinResponseInterface;
        this.mpinDataManager = new MpinDataManager(mContext);
        this.mContext = mContext;
    }

    private void goGenerateMpin() {
        GenerateMpinRequestModel generateMpinRequestModel=new GenerateMpinRequestModel();
        generateMpinRequestModel.mpin=getMpin();
        mpinDataManager.callEnqueue(ApiClass.MPIN_URL, getToken(),generateMpinRequestModel, new ResponseHandler<GenerateMpinResponseModel>() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onSuccess(GenerateMpinResponseModel item, String message) {
                Log.i("otpR","rr");
                if(item.getMsg()!=null) {
                    Log.i("otpRecevied", item.getMsg());
                    Toast.makeText(mContext, ""+item.getMsg(), Toast.LENGTH_SHORT).show();
                    mpinResponseInterface.generateMpinProcessed(item.getMsg());
                }

            }
            @Override
            public void onFailure(ErrorBody errorBody, int statusCode) {
                Log.i("error" ,errorBody.ErrorMessage);
                mpinResponseInterface.onFailure(errorBody,statusCode);

            }
        });
    }

    @Override
    public void generateMpinRequest() {
        goGenerateMpin();
    }
}
