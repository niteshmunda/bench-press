package io.redgreen.benchpress.login.schedulers

import io.reactivex.Scheduler

interface AppSchedulers {

    fun main() : Scheduler

    // Add for bg, network, io etc.

}
