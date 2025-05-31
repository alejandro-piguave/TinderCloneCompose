package com.apiguave.onboarding_ui.create_profile

import java.time.LocalDate

object UserAgePolicy {
    fun getMaxBirthdate(): LocalDate = LocalDate.now().minusYears(18)
}