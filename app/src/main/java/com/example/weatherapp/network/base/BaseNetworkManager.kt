package com.example.weatherapp.network.base

import com.example.weatherapp.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

class BaseNetworkManager :
    IBaseNetworkManager {

    private var retrofit: Retrofit? = null


    override fun buildRetrofit(): Retrofit {
        if (retrofit == null) {

            val httpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)

            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request_: Request =
                        chain
                            .request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("charset", "UTF-8")
                            .build()
                    return chain.proceed(request_)
                }
            })

            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        }

        return retrofit as Retrofit

    }
}