package com.acostim.mastermeme.home.presentation

import androidx.lifecycle.ViewModel
import com.acostim.mastermeme.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _memeTemplates = MutableStateFlow(emptyList<Int>())
    val memeTemplates: StateFlow<List<Int>> = _memeTemplates

    fun loadMemeTemplates() {
        _memeTemplates.value = listOf(
            R.drawable.ajtl_46,
            R.drawable.bbctk_29,
            R.drawable.boardroom_meeting_suggestion_36,
            R.drawable.byuiy_33,
            R.drawable.c1hh_48,
            R.drawable.c9dbh_30,
            R.drawable.change_my_mind_5,
            R.drawable.clown_applying_makeup_31,
            R.drawable.disaster_girl_1,
            R.drawable.eb198_32,
            R.drawable.epic_handshake_2,
            R.drawable.eqjd8_12,
            R.drawable.eyvu_45,
            R.drawable.f0mvv_16,
            R.drawable.grus_plan_9,
            R.drawable.hide_the_pain_harold_7,
            R.drawable.i_bet_hes_thinking_about_other_women_10,
            R.drawable.i_was_told_there_would_be_35,
            R.drawable.iacv_13,
            R.drawable.igo27_47,
            R.drawable.is_this_a_pigeon_21,
            R.drawable.jack_sparrow_being_chased_23,
            R.drawable.jgrgn_44,
            R.drawable.left_exit_12_off_ramp_3,
            R.drawable.leonardo_dicaprio_cheers_24,
            R.drawable.op9wy_25,
            R.drawable.otri4_40,
            R.drawable.p2is_38,
            R.drawable.qx7sw_49,
            R.drawable.rcrc1_39,
            R.drawable.reqtg_50,
            R.drawable.running_away_balloon_14,
            R.drawable.sad_pablo_escobar_4,
            R.drawable.scared_cat_34,
            R.drawable.soh_20,
            R.drawable.su9f_41,
            R.drawable.t8r9a_26,
            R.drawable.the_rock_driving_8,
            R.drawable.third_world_skeptical_kid_11,
            R.drawable.two_buttons_6,
            R.drawable.two_buttons_28,
            R.drawable.u6ylb_19,
            R.drawable.vt4i_27,
            R.drawable.w2e5e_17,
            R.drawable.waiting_skeleton_43,
            R.drawable.wxtd1_42,
            R.drawable.yz6z4_22,
            R.drawable.zoa8_15
        )
    }

}