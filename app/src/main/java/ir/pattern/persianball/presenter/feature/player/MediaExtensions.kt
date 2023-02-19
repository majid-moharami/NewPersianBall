package ir.pattern.persianball.presenter.feature.player

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides

fun DefaultTrackSelector.generateQualityList(): ArrayList<Pair<String, TrackSelectionOverrides.Builder>> {
    //Render Track -> TRACK GROUPS (Track Array)(Video,Audio,Text)->Track
    val trackOverrideList = ArrayList<Pair<String, TrackSelectionOverrides.Builder>>()

    val renderTrack = this.currentMappedTrackInfo
    val renderCount = renderTrack?.rendererCount ?: 0
    for (rendererIndex in 0 until renderCount) {
        if (isSupportedFormat(renderTrack, rendererIndex)) {
            val trackGroupType = renderTrack?.getRendererType(rendererIndex)
            val trackGroups = renderTrack?.getTrackGroups(rendererIndex)
            val trackGroupsCount = trackGroups?.length!!
            if (trackGroupType == C.TRACK_TYPE_VIDEO) {
                for (groupIndex in 0 until trackGroupsCount) {
                    val videoQualityTrackCount = trackGroups[groupIndex].length
                    for (trackIndex in 0 until videoQualityTrackCount) {
                        val isTrackSupported = renderTrack.getTrackSupport(
                            rendererIndex,
                            groupIndex,
                            trackIndex
                        ) == C.FORMAT_HANDLED
                        if (isTrackSupported) {
                            val track = trackGroups[groupIndex]
                            val trackName = createQualityString(track.getFormat(trackIndex))
//                                "${track.getFormat(trackIndex).width} x ${track.getFormat(trackIndex).height}"
                            if (track.getFormat(trackIndex).selectionFlags==C.SELECTION_FLAG_AUTOSELECT){
                                trackName.plus(" (Default)")
                            }
                            val trackBuilder =
                                TrackSelectionOverrides.Builder()
                                    .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
                                    .addOverride(TrackSelectionOverrides.TrackSelectionOverride(track,
                                        listOf(trackIndex)))
                            trackOverrideList.add(Pair(trackName, trackBuilder))
                        }
                    }
                }
            }
        }
    }
    return trackOverrideList
}

fun isSupportedFormat(mappedTrackInfo: MappingTrackSelector.MappedTrackInfo?, rendererIndex: Int): Boolean {
    val trackGroupArray = mappedTrackInfo?.getTrackGroups(rendererIndex)
    return if (trackGroupArray?.length == 0) {
        false
    } else mappedTrackInfo?.getRendererType(rendererIndex) == C.TRACK_TYPE_VIDEO || mappedTrackInfo?.getRendererType(
        rendererIndex
    ) == C.TRACK_TYPE_AUDIO || mappedTrackInfo?.getRendererType(rendererIndex) == C.TRACK_TYPE_TEXT
}

private fun createQualityString(format: Format): String {
    val size = format.width * format.height
    val output = when {
        size == Format.NO_VALUE -> {}
        size <= PIXEL_COUNT_160P -> "160p"
        size <= PIXEL_COUNT_240P -> "240p"
        size <= PIXEL_COUNT_360P -> "360p"
        size <= PIXEL_COUNT_480P -> "480p"
        size <= PIXEL_COUNT_720P -> "720p"
        size <= PIXEL_COUNT_1080P -> "1080p"
        size <= PIXEL_COUNT_1440P -> "1440p"
        size <= PIXEL_COUNT_2160P -> "2160p"
        else -> {}
    }
    return output.toString()
}

private const val PIXEL_COUNT_2160P = 8294400	// 3840*2160
private const val PIXEL_COUNT_1440P = 3686400	// 2560*1440
private const val PIXEL_COUNT_1080P = 2073600	// 1920*1080
private const val PIXEL_COUNT_720P = 921600		// 1280*720
private const val PIXEL_COUNT_480P = 409920		// 584*480
private const val PIXEL_COUNT_360P = 230400		// 640*360
private const val PIXEL_COUNT_240P = 102240		// 426*240
private const val PIXEL_COUNT_160P = 14400		// 160*90