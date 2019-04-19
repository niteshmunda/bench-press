package io.redgreen.benchpress.hellostranger

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.spotify.mobius.Next
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.R
import io.redgreen.benchpress.architecture.android.BaseActivity
import io.redgreen.benchpress.architecture.effecthandlers.NoOpEffectHandler
import kotlinx.android.synthetic.main.hello_stranger_activity.*

class HelloStrangerActivity : BaseActivity<HelloStrangerModel, HelloStrangerEvent, Nothing>() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HelloStrangerActivity::class.java))
        }
    }

    override fun layoutResId(): Int {
        return R.layout.hello_stranger_activity
    }

    override fun setup() {
        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                eventSource.notifyEvent(if (s.isNullOrBlank()) DeleteNameEvent else EnterNameEvent(s.toString()))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    override fun initialModel(): HelloStrangerModel {
        return HelloStrangerModel.GUEST
    }

    override fun updateFunction(
        model: HelloStrangerModel,
        event: HelloStrangerEvent
    ): Next<HelloStrangerModel, Nothing> {
        return HelloStrangerLogic.update(model, event)
    }

    override fun render(model: HelloStrangerModel) {
        if (model.name.isBlank())
            greetingTextView.text = "Hello Stranger"
        else
            greetingTextView.text = "Hello ${model.name}"
    }

    override fun effectHandler(): ObservableTransformer<Nothing, HelloStrangerEvent> {
        return NoOpEffectHandler()
    }
}
