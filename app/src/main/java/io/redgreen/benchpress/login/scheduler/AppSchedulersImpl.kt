package io.redgreen.benchpress.login.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class AppSchedulersImpl : AppSchedulers {
    override fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}