package toy.narza.clonetracker.network.`interface`

import com.google.gson.annotations.SerializedName

data class CloneDiaData(@SerializedName("progress")
                        val progress: Int,
                        @SerializedName("region")
                        val region: Int,
                        @SerializedName("ladder")
                        val ladderMode: Int,
                        @SerializedName("hc")
                        val mode: Int,
                        @SerializedName("timestamped")
                        val timestamped: Long) {

}
