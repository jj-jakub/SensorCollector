package com.jj.core.data.hardware.gps.analysis

import com.jj.domain.model.analysis.AnalysedSample

class VelocityTestUtils {
    data class CurrentVelocityTestingParam(
        val firstSample: AnalysedSample.AnalysedGPSSample,
        val secondSample: AnalysedSample.AnalysedGPSSample,
        val currentVelocity: Double,
    )

    data class AverageVelocityTestingParam(
        val sample: AnalysedSample.AnalysedGPSSample,
        val currentVelocity: Double,
        val averageVelocity: Double,
    )

    companion object {
        const val velocityTolerance = 0.00000000001
        val analysedGPSSamples = listOf(
            AnalysedSample.AnalysedGPSSample(1.0, 1.0, 1000),
            AnalysedSample.AnalysedGPSSample(1.0, 1.01, 2000), // 4003.5384813722853
            AnalysedSample.AnalysedGPSSample(1.0, 1.05, 3000), // 16014.153925396278
            AnalysedSample.AnalysedGPSSample(1.0, 1.1, 10000), // 2859.670343810814
            AnalysedSample.AnalysedGPSSample(1.0, 2.0, 200000), // 1896.4129589227055
            AnalysedSample.AnalysedGPSSample(1.0, 3.0, 1000000), // 500.4423082369942
            AnalysedSample.AnalysedGPSSample(1.0, 5.0, 2000000), // 800.7076838913339
            AnalysedSample.AnalysedGPSSample(1.0, 5.5, 100000000), // 2.042621672196148
        )


        private const val FIRST_CURRENT_VELOCITY = 4003.5384813722853
        private const val SECOND_CURRENT_VELOCITY = 16014.153925396278
        private const val THIRD_CURRENT_VELOCITY = 2859.6703438108148
        private const val FOURTH_CURRENT_VELOCITY = 1896.4129589227055
        private const val FIFTH_CURRENT_VELOCITY = 500.4423082369942
        private const val SIXTH_CURRENT_VELOCITY = 800.7076838913339
        private const val SEVENTH_CURRENT_VELOCITY = 2.042621672196148
        const val FINAL_AVERAGE_VELOCITY = 3725.2811890432304

        val averageVelocityTestingSet = listOf(
            AverageVelocityTestingParam(
                sample = analysedGPSSamples[0],
                currentVelocity = 0.0,
                averageVelocity = 0.0
            ),
            AverageVelocityTestingParam(
                sample = analysedGPSSamples[1],
                currentVelocity = FIRST_CURRENT_VELOCITY,
                averageVelocity = FIRST_CURRENT_VELOCITY
            ),
            AverageVelocityTestingParam(
                sample = analysedGPSSamples[2],
                currentVelocity = SECOND_CURRENT_VELOCITY,
                averageVelocity = (SECOND_CURRENT_VELOCITY + FIRST_CURRENT_VELOCITY) / 2
            ), // 10008.8462034
            AverageVelocityTestingParam(
                sample = analysedGPSSamples[3],
                currentVelocity = THIRD_CURRENT_VELOCITY,
                averageVelocity = (SECOND_CURRENT_VELOCITY + FIRST_CURRENT_VELOCITY + THIRD_CURRENT_VELOCITY) / 3
            ), // 7625.78758353
            AverageVelocityTestingParam(
                sample = analysedGPSSamples[4],
                currentVelocity = FOURTH_CURRENT_VELOCITY,
                averageVelocity = (SECOND_CURRENT_VELOCITY + FIRST_CURRENT_VELOCITY + THIRD_CURRENT_VELOCITY + FOURTH_CURRENT_VELOCITY) / 4
            ), // 6193.44392738
            AverageVelocityTestingParam(
                sample = analysedGPSSamples[5],
                currentVelocity = FIFTH_CURRENT_VELOCITY,
                averageVelocity = (SECOND_CURRENT_VELOCITY + FIRST_CURRENT_VELOCITY + THIRD_CURRENT_VELOCITY + FOURTH_CURRENT_VELOCITY + FIFTH_CURRENT_VELOCITY) / 5
            ), // 5054.84360355
            AverageVelocityTestingParam(
                sample = analysedGPSSamples[6],
                currentVelocity = SIXTH_CURRENT_VELOCITY,
                averageVelocity = (SECOND_CURRENT_VELOCITY + FIRST_CURRENT_VELOCITY + THIRD_CURRENT_VELOCITY + FOURTH_CURRENT_VELOCITY + FIFTH_CURRENT_VELOCITY + SIXTH_CURRENT_VELOCITY) / 6
            ), // 4345.82095027
            AverageVelocityTestingParam(
                sample = analysedGPSSamples[7],
                currentVelocity = SEVENTH_CURRENT_VELOCITY,
                averageVelocity = (SECOND_CURRENT_VELOCITY + FIRST_CURRENT_VELOCITY + THIRD_CURRENT_VELOCITY + FOURTH_CURRENT_VELOCITY + FIFTH_CURRENT_VELOCITY + SIXTH_CURRENT_VELOCITY + SEVENTH_CURRENT_VELOCITY) / 7
            ), // 3725.28118904
        )

        @JvmStatic
        fun currentVelocityTestingSet() = listOf(
            CurrentVelocityTestingParam(analysedGPSSamples[0], analysedGPSSamples[1], FIRST_CURRENT_VELOCITY),
            CurrentVelocityTestingParam(analysedGPSSamples[1], analysedGPSSamples[2], SECOND_CURRENT_VELOCITY),
            CurrentVelocityTestingParam(analysedGPSSamples[2], analysedGPSSamples[3], THIRD_CURRENT_VELOCITY),
            CurrentVelocityTestingParam(analysedGPSSamples[3], analysedGPSSamples[4], FOURTH_CURRENT_VELOCITY),
            CurrentVelocityTestingParam(analysedGPSSamples[4], analysedGPSSamples[5], FIFTH_CURRENT_VELOCITY),
            CurrentVelocityTestingParam(analysedGPSSamples[5], analysedGPSSamples[6], SIXTH_CURRENT_VELOCITY),
            CurrentVelocityTestingParam(analysedGPSSamples[6], analysedGPSSamples[7], SEVENTH_CURRENT_VELOCITY),
        )
    }
}