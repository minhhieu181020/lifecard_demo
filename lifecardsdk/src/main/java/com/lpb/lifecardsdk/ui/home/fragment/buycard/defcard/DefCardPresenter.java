package com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.AreaRequest;
import com.lpb.lifecardsdk.data.model.request.default_.CategoryRequest;
import com.lpb.lifecardsdk.data.model.request.default_.PackageSearchRequest;
import com.lpb.lifecardsdk.data.model.request.default_.ProviderRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.AreaResponse;
import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.DefCardSearchResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefCardPresenter implements DefCardContract.Presenter {
    private Context mContext;
    private DefCardContract.View mViewModel;

    DefCardPresenter(Context context, DefCardContract.View viewModel) {
        this.mContext = context;
        this.mViewModel = viewModel;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getDataLocation() {
        final String functionName = Function.FunctionName.GET_DATA_LOCATION;
        final String functionCode = Function.FunctionCode.GET_DATA_LOCATION;

        String body = StringUtils.convertObjectToBase64(new AreaRequest(Constants.AreaType.CITY, ""));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getArea(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {

                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        AreaResponse areaResponse = new Gson().fromJson(text, AreaResponse.class);
                        mViewModel.setDataLocation(areaResponse.getListArea());
                        mViewModel.hideLoading();

                        // TODO: 20/05/2020
                        // showContent
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (s.equals(Exception.Type.SERVER)) {
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

    @Override
    public void getDataProvider() {
        final String functionName = Function.FunctionName.GET_DATA_PROVIDER_BUY_CARD;
        final String functionCode = Function.FunctionCode.GET_DATA_PROVIDER_BUY_CARD;

        String body = StringUtils.convertObjectToBase64(new ProviderRequest("HLCF", "1"));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getProvider(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        ProviderResponse providerResponse = new Gson().fromJson(text, ProviderResponse.class);
                        mViewModel.setDataProvider(providerResponse.getList());
                        // TODO: 20/05/2020
                        // showContent
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (s.equals(Exception.Type.SERVER)) {
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

    @Override
    public void getDataCategory() {
        final String functionName = Function.FunctionName.GET_DATA_CATEGORY_BUY_CARD;
        final String functionCode = Function.FunctionCode.GET_DATA_CATEGORY_BUY_CARD;

        String body = StringUtils.convertObjectToBase64(new CategoryRequest("", Constants.CategoryType.PKG));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getCategory(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {

                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        CategoryResponse categoryResponse = new Gson().fromJson(text, CategoryResponse.class);
                        mViewModel.setDataCategory(categoryResponse.getLstCate());
                        mViewModel.showContent();
                        mViewModel.hideLoading();
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (s.equals(Exception.Type.SERVER)) {
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

    @Override
    public void getDataCard(Integer categoryID, String keywordQuery, String sort, String mobilePhone, final int pageIndex, int pageSize) {
        final String functionName = Function.FunctionName.GET_DATA_CARD_BUY_CARD;
        final String functionCode = Function.FunctionCode.GET_DATA_CARD_BUY_CARD;

        PackageSearchRequest packageSearchRequest = new PackageSearchRequest(categoryID, keywordQuery, sort, mobilePhone, pageIndex, pageSize, null);
        String body = StringUtils.convertObjectToBase64(packageSearchRequest);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getDefCard(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        DefCardSearchResponse packageSearchResponse = new Gson().fromJson(text, DefCardSearchResponse.class);
                        if (pageIndex == 1) {
                            mViewModel.setDataCard(packageSearchResponse.getCardListConfig());
                        } else {
                            mViewModel.addDataCard(packageSearchResponse.getCardListConfig());
                        }
                        mViewModel.showContent();
                        mViewModel.hideLoading();
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (s.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
                mViewModel.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                mViewModel.setRefreshing(false);
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }


}

