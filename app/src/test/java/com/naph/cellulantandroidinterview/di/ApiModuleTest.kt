package com.naph.cellulantandroidinterview.di

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.naph.cellulantandroidinterview.api.WeatherApi
import com.naph.cellulantandroidinterview.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStreamReader

@RunWith(JUnit4::class)
class ApiModuleTest {

    private lateinit var server: MockWebServer
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: WeatherRepository
    private lateinit var mockedResponse: String

    @Before
    fun init() {
        server = MockWebServer()

        var BASE_URL = server.url("https://api.openweathermap.org/data/2.5/").toString()

        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(WeatherApi::class.java)

        repository = WeatherRepository(service)
    }

//    Test for status 200 or Success fetching of our data

    @Test
    fun testSuccessResponse() {
        mockedResponse = MockResponseFileReader("weatherApiResponse/weathersuccess.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val finalResponse = runBlocking {
            repository.apiService.getSearchedData("London, uk")
        }

        Assert.assertEquals(true, finalResponse.code() == 200)
    }

//    Testing for invalid API Key / Status 401
    @Test
    fun testInvalidApi() {
        mockedResponse = MockResponseFileReader("weatherApiResponse/error401.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody(mockedResponse)
        )

        val response = runBlocking {
            repository.apiService.getSearchedData("London, uk")
        }

        Assert.assertEquals(false, response.code() == 401)
    }

    //    Test for page not found status / Error 404

    @Test
    fun testPageNotFound() {
        mockedResponse = MockResponseFileReader("weatherApiResponse/error404.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(mockedResponse)
        )

        val response = runBlocking {
            repository.apiService.getSearchedData("6")
        }

        Assert.assertEquals(true, response.code() == 404)
    }

    //    Testing if there is no location(Nothing to Geocode), / Error 401

    @Test
    fun testNothingToGeocode() {
        mockedResponse = MockResponseFileReader("weatherApiResponse/error400.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(mockedResponse)
        )

        val response = runBlocking {
            repository.apiService.getSearchedData("")
        }

        Assert.assertEquals(true, response.code() == 400)
    }

}

class MockResponseFileReader(path: String) {
    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}