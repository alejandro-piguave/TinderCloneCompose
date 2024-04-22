package com.apiguave.tinderclonedomain.usecase

import java.time.LocalDate

class GetMaxBirthdateUseCase {

    operator fun invoke(): LocalDate {
        return LocalDate.now().minusYears(18L)
    }
}