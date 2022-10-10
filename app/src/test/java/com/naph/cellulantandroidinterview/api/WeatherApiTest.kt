package com.naph.cellulantandroidinterview.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.naph.cellulantandroidinterview.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStreamReader

@RunWith(JUnit4::class)
class WeatherApiTest {

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

    /*
        Test for Forecast Api
     */

    @Test
    fun testSuccessTodayForecastResponse() {
        mockedResponse = MockResponseFileReader("weatherApiResponse/forecastsuccess.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val finalResponse = runBlocking {
            repository.apiService.getTodayWeatherForecast("dubai")
        }

        Assert.assertEquals(true, finalResponse.code() == 200)
    }

//     Test to Get Tomorrow Forecost Response

    @Test
    fun testSuccessTomorrowForecastResponse() {
        mockedResponse = MockResponseFileReader("weatherApiResponse/forecastsuccess.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val finalResponse = runBlocking {
            repository.apiService.getTomorrowWeatherForecast("dubai")
        }

        Assert.assertEquals(true, finalResponse.code() == 200)
    }

    @Test
    fun testSuccessLaterForecastResponse() {
        mockedResponse = MockResponseFileReader("weatherApiResponse/forecastsuccess.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val finalResponse = runBlocking {
            repository.apiService.getLaterWeatherForecast("dubai")
        }
        Assert.assertEquals(true, finalResponse.code() == 200)
    }

//    Test Eroor 401 response

    @Test
    fun testForecastAppIdResponse() {
        mockedResponse = MockResponseFileReader("weatherApiResponse/error401.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody(mockedResponse)
        )
        val finalResponse = runBlocking {
            repository.apiService.getLaterWeatherForecast("dubai", "868786688899779")
        }
        Assert.assertEquals(false, finalResponse.code() == 401)
    }

    @Test
    fun testNothingForecastToGeoCode() {
        mockedResponse = MockResponseFileReader("weatherApiResponse/error400.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(mockedResponse)
        )
        val finalResponse = runBlocking {
            repository.apiService.getLaterWeatherForecast("")
        }
        Assert.assertEquals(false, finalResponse.code() == 400)
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