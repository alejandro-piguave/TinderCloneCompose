package com.apiguave.feature_auth.register

import java.time.LocalDate

object UserAgePolicy {
    fun getMaxBirthdate(): LocalDate = LocalDate.now().minusYears(18)
}