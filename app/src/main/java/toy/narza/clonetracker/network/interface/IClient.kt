package toy.narza.clonetracker.network.`interface`

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.network.constants.LadderMode

interface IClient {
    companion object {
        const val BASE_URL = "https://diablo2.io/";
        const val GET_INFO = "dclone_api.php"
    }

    @GET(IClient.GET_INFO)
    fun getData(): Call<List<CloneData>>

    @GET(IClient.GET_INFO)
    fun getLadder(
        @Query("hc")hcMode: Int,
        @Query("ladder")ladderMode: Int = LadderMode.Ladder.index)
    : Call<List<CloneData>>

    @GET(IClient.GET_INFO)
    fun getNonLadder(
        @Query("hc")hcMode: Int,
        @Query("ladder")ladderMode: Int = LadderMode.Standard.index)
    : Call<List<CloneData>>
}