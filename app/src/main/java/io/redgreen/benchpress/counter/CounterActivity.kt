package io.redgreen.benchpress.counter

import android.content.Context
import android.content.Intent
import com.spotify.mobius.Next
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.R
import io.redgreen.benchpress.architecture.android.BaseActivity
import io.redgreen.benchpress.architecture.effecthandlers.NoOpEffectHandler
import kotlinx.android.synthetic.main.counter_activity.*

class CounterActivity : BaseActivity<CounterModel, CounterEvent, Nothing>() {
  companion object {
    fun start(context: Context) {
      context.startActivity(Intent(context, CounterActivity::class.java))
    }
  }

  override fun layoutResId(): Int {
    return R.layout.counter_activity
  }

  override fun setup() {
    incrementButton.setOnClickListener { eventSource.notifyEvent(IncrementEvent) }
    decrementButton.setOnClickListener { eventSource.notifyEvent(DecrementEvent) }
  }

  override fun initialModel(): CounterModel {
    return CounterModel.ZERO
  }

  override fun updateFunction(
    model: CounterModel,
    event: CounterEvent
  ): Next<CounterModel, Nothing> {
    return CounterLogic.update(model, event)
  }

  override fun render(model: CounterModel) {
    counterTextView.text = model.counter.toString()
  }

  override fun effectHandler(): ObservableTransformer<Nothing, CounterEvent> {
    return NoOpEffectHandler()
  }
}
