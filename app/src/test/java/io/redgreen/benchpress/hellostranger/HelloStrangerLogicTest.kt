package io.redgreen.benchpress.hellostranger

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class HelloStrangerLogicTest {
    val updateSpec = UpdateSpec<HelloStrangerModel, HelloStrangerEvent, Nothing>(HelloStrangerLogic::update)

    @Test
    fun `user can enter name`() {

        val guestModel = HelloStrangerModel.GUEST

        updateSpec.given(guestModel)
            .`when`(EnterNameEvent("Robin"))
            .then(
                assertThatNext(
                    hasModel(HelloStrangerModel("Robin")),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `user can delete name`() {
        val userModel = HelloStrangerModel("Janhavi")

        updateSpec.given(userModel)
            .`when`(DeleteNameEvent)
            .then(
                assertThatNext(
                    hasModel(HelloStrangerModel.GUEST),
                    hasNoEffects()
                )
            )
    }
}
