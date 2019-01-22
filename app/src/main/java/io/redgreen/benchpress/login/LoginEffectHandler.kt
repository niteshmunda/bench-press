package io.redgreen.benchpress.login

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer

object LoginEffectHandler {

    fun create(action : LoginActions,
               api : ApiService)
            : ObservableTransformer<LoginEffect, LoginEvent> {
        return RxMobius.subtypeEffectHandler<LoginEffect, LoginEvent>()
            .addAction(NavigateEffect::class.java, action::navigateToHome)
            .addAction(ClearFieldsEffect::class.java, action::clearFields)
            .addAction(RetryEffect::class.java, action::retry)
            .addTransformer(
                ApiCallEffect::class.java,
                login(api)
            )
            .build()
    }

    private fun login(api: ApiService): ObservableTransformer<ApiCallEffect, LoginEvent> {
        return ObservableTransformer {
            apiCallEffect -> apiCallEffect.flatMapSingle {

            api.login(it.request).map<LoginResponse> {

            }.onErrorReturn {
                LoginFailedEvent()
            }
        }
        }
    }

}