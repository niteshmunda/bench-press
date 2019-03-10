package io.redgreen.benchpress.login

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.login.db.Repository
import io.redgreen.benchpress.login.scheduler.AppSchedulers

object LoginEffectHandler {
    fun create (
        action : LoginAction,
        api : ApiService,
        db : Repository,
        scheduler : AppSchedulers
    ): ObservableTransformer<LoginEffect,  LoginEvent>{
        return RxMobius.subtypeEffectHandler<LoginEffect,LoginEvent>()
            .addAction(NavigateEffect::class.java, action::navigateToHome)
            .addAction(ClearFieldsEffect::class.java, action::clearFields)
            .addAction(RetryEffect::class.java, action::retry, scheduler.main())
            .addTransformer(SaveTokenEffect::class.java, this.saveToDb(db))
            .addTransformer(ApiCallEffect::class.java, login(api))
            .build()
    }

    private fun saveToDb (db : Repository) : ObservableTransformer<SaveTokenEffect, LoginEvent>{
        return ObservableTransformer {
            it.flatMapSingle { effect ->
                db.saveToken(effect.token).map<LoginEvent> {success ->
                    if (success){
                        TokenSavedEvent
                    } else{
                        RetryEvent(effect.token)
                    }
                }
            }
        }
    }

    private fun login (api : ApiService) : ObservableTransformer<ApiCallEffect, LoginEvent> {
        return ObservableTransformer {
            it.flatMapSingle {apiCallEffect ->
                api
                    .login(apiCallEffect.request)
                    .map<LoginEvent> {loginResponse ->
                        LoginSuccessEvent(loginResponse)
                    }.onErrorReturn{
                        LoginTimeoutFailedEvent(NetworkError.TIMEOUT)
                    }
            }
        }
    }

}