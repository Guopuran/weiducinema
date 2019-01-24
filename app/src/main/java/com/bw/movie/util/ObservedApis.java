package com.bw.movie.util;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 *
 * @详情 RXjava的被观察者
 *
 * @创建日期 2018/12/29 15:00
 *
 */
public interface ObservedApis {

    @GET
    Observable<ResponseBody> get(@Url String url);

    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String, String> parmas);
    @DELETE
    Observable<ResponseBody> delete(@Url String url);
    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> parmas);
    @POST
    @Multipart
    Observable<ResponseBody> postImage(@Url String url, @Part MultipartBody.Part parts);


}

