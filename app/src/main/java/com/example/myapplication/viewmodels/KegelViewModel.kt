package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.MockData
import com.example.myapplication.data.models.Member
import com.example.myapplication.data.models.PenaltyItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KegelViewModel : ViewModel() {

    private val _members = MutableStateFlow<List<Member>>(MockData.members)
    val members: StateFlow<List<Member>> = _members.asStateFlow()

    val penalties = MockData.penalties
    val nextSession = "Fr. 20. Juni 2026 · Gasthaus Zum Kegel"
    val clubBalance = 142.80
    val currentUserName = "Noah"
    val currentUserId = "1"

    fun addPenalty(memberId: String, penalty: PenaltyItem) {
        _members.value = _members.value.map { member ->
            if (member.id == memberId) member.copy(debt = member.debt + penalty.amount)
            else member
        }
    }
}
