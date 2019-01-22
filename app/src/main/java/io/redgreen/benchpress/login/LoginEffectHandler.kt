package io.redgreen.benchpress.login

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.login.db.Repository

object LoginEffectHandler {

    fun create(
        action: LoginActions,
        api: ApiService,
        db: Repository
    )
            : ObservableTransformer<LoginEffect, LoginEvent> {
        return RxMobius.subtypeEffectHandler<LoginEffect, LoginEvent>()
            .addConsumer(SaveTokenEffect::class.java) { db.saveToken() }
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
            it.flatMapSingle { apiCallEffect ->
                api
                    .login(apiCallEffect.request)
                    .map<LoginEvent> { loginResponse ->
                        LoginSuccessEvent(loginResponse)
                    }.onErrorReturn {
                        LoginFailedEvent(NetworkError.TIMEOUT)
                    }
            }
        }
    }

}