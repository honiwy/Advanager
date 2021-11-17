package com.avc.advanager.api

import com.avc.advanager.data.AccountInfo
import com.avc.advanager.data.RegisterInfo
import com.avc.advanager.data.Token
import com.avc.advanager.data.source.remote.RetrofitApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

@RunWith(JUnit4::class)
class ApiTest : BaseApiTest() {

    private lateinit var service: RetrofitApiService

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Before
    @Throws(IOException::class)
    fun createService() {
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RetrofitApiService::class.java)
    }

    @Test
    fun getDeviceInitialStatus() = runBlocking {
        enqueueResponse("init_source.json")
        val response =
            service.postDeviceInitiate().body() ?: return@runBlocking

        // Dummy request
        mockWebServer.takeRequest()

        // Check news source
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response.initial_status, CoreMatchers.`is`(0))
    }

    @Test
    fun postUserRegister() = runBlocking {
        enqueueResponse("register_source.json")

        val response = service.postUserRegister(
            RegisterInfo(0, "admin", "P@ssw0rD")
        ).body() ?: return@runBlocking

        // Dummy request
        mockWebServer.takeRequest()

        // Check news source
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response.permissionType, CoreMatchers.`is`(0))
        MatcherAssert.assertThat(response.account, CoreMatchers.`is`("admin"))
    }

    @Test
    fun postUserLogin() = runBlocking {
        enqueueResponse("login_source.json")

        val response = service.postUserLogin(
            AccountInfo("admin", "P@ssw0rD")
        ).body() ?: return@runBlocking

        // Dummy request
        mockWebServer.takeRequest()

        // Check news source
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response.permissionType, CoreMatchers.`is`(99))
        MatcherAssert.assertThat(response.token, CoreMatchers.`is`("7CKKZHFI6O83EIM95XX2HPXL"))
    }

    @Test
    fun postUserLogout() = runBlocking {
        enqueueResponse("logout_source.json")

        val response =
            service.postUserLogout(Token("7CKKZHFI6O83EIM95XX2HPXL")).body() ?: return@runBlocking

        // Dummy request
        mockWebServer.takeRequest()

        // Check news source
        MatcherAssert.assertThat(response, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response.token, CoreMatchers.`is`("7CKKZHFI6O83EIM95XX2HPXL"))
    }

}