package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge.confirm;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentWaitRechargeResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vannh.lvt on 24/07/2020
 */
public class ConfirmPresenter implements ConfirmContract.Presenter {
    private Context mContext;
    private ConfirmContract.View mViewModel;

    ConfirmPresenter(Context mContext, ConfirmContract.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


    @Override
    public void getDataPaymentWait(String body) {

        if (PresenterUtils.isNetworkConnected(mContext)) {
            mViewModel.showLoading(true);
        } else {
            mViewModel.showError(mContext.getString(R.string.lifecardsdk_cant_connect_internet), "");
            return;
        }

        final String functionName = Function.FunctionName.PAYMENT_WAIT_RECHARGE;
        final String functionCode = Function.FunctionCode.PAYMENT_WAIT_RECHARGE;
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().paymentWaitRecharge(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        PaymentWaitRechargeResponse paymentWaitRechargeResponse = new Gson().fromJson(text, PaymentWaitRechargeResponse.class);
                        mViewModel.setDataPaymentWait(paymentWaitRechargeResponse);
                        mViewModel.hideLoading();
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (status.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }
}
