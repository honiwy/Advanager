package com.avc.advanager.source.remote

import com.avc.advanager.data.LoginInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.response.DeviceInitialResponse
import com.avc.advanager.response.LoginUserResponse
import com.avc.advanager.response.RegisterUserResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 *
 * Build the Moshi object that Retrofit will be using
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//KotlinJsonAdapterFactory把字串轉成資料
/**
 *
 * Build the OkHttpClient object that Retrofit will be using
 */
private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    .build()
//把HttpLoggingInterceptor.Level.BASIC改成HttpLoggingInterceptor.Level.BODY
/**
 *
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 *
 * And using an OkHttpClient with our OkHttpClient object.
 *
 */
var BASE_URL = "http://192.168.1.168/apis/androvideo/"
private var retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()


interface RetrofitApiService {

    /**
     * Returns a Coroutine [Deferred] [Property Object] which can be fetched with await() if
     * in a Coroutine scope.
     */

    @POST("users/initial")
    @Headers("Content-Type: application/json")
    fun postDeviceInitiate(
    ): Deferred<DeviceInitialResponse>

//    //HOME
//    @GET("marketing/hots")
//    fun getMarketingHots(): Deferred<MarketingHotsResult>
//    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
//
//    //CATALOG
//    @GET("products/{catalog}")
//    fun getProducts(
//        @Path("catalog") catalog: String,
//        @Query("paging") nextPaging: Int?
//    ): Deferred<ProductsResult>
//
//    //PROFILE
//    @GET("user/profile")
//    @Headers("Content-Type: application/json;charset=UTF-8")
//    fun getUserInfo(@Header("Authorization") string: String): Deferred<UserProfile>
//
    @POST("users/register")
    @Headers("Content-Type: application/json")
    fun postUserRegister(
    @Body registerInfo: RegisterInfo
    ): Deferred<RegisterUserResponse>

    @POST("users/login")
    @Headers("Content-Type: application/json")
    fun postUserLogin(
        @Body loginInfo: LoginInfo
    ): Deferred<LoginUserResponse>
//
//    @POST("order/checkout")
//    @Headers("Content-Type: application/json")
//    fun postCheckout(
//        @Header("Authorization") token: String,
//        @Body purchaseInfo: PurchaseInfo
//    ): Deferred<Data>

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object RetrofitApi {
    var retrofitService: RetrofitApiService =
        retrofit.create(
            RetrofitApiService::class.java
        )

    fun checkRetrofitUpdateRequired(newIP: String){
        val newUrl = "http://$newIP/apis/androvideo/"
        if (BASE_URL != newUrl) {
            BASE_URL = newUrl
            retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
            retrofitService= retrofit.create(RetrofitApiService::class.java)
        }
    }
}
