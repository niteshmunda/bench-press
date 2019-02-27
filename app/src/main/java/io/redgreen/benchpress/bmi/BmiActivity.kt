package io.redgreen.benchpress.bmi

import android.content.Context
import android.content.Intent
import android.widget.SeekBar
import com.spotify.mobius.Next
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.R
import io.redgreen.benchpress.architecture.android.BaseActivity
import io.redgreen.benchpress.architecture.effecthandlers.NoOpEffectHandler
import kotlinx.android.synthetic.main.bmi_activity.*

// 30 + progress / 120 + progress -> Please pick these values from a configuration source.

class BmiActivity : BaseActivity<BmiModel, BmiEvent, Nothing>(){

    val min_weight_kg = 30
    val min_height_cm = 120
    private var weightUnit : String = "Kg"
    private var heightUnit : String = "Cm"
    var unitSystem : String = "Metric Units"

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, BmiActivity::class.java))
        }
    }

    override fun layoutResId(): Int {
        return R.layout.bmi_activity
    }

    override fun setup() {
        weightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                eventSource.notifyEvent(WeightChangeEvent((min_weight_kg+progress).toFloat()))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                /* no-op */
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                /* no-op */
            }
        })

        heightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                eventSource.notifyEvent(HeightChangeEvent((min_height_cm+progress).toFloat()))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                /* no-op */
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                /* no-op */
            }
        })
        measurementSystemSwitch.setOnCheckedChangeListener { _, usingMetric ->
            val measurementType = if (usingMetric) MeasurementType.METRIC else MeasurementType.IMPERIAL
            weightUnit = if (usingMetric) "Kg" else "pounds"
            heightUnit = if (usingMetric) "Cm" else "inches"
            unitSystem = if (usingMetric) "Metric" else "Imperial"

            eventSource.notifyEvent(UnitChangeEvent(measurementType))
        }
    }

    override fun initialModel(): BmiModel {
        return BmiModel.DEFAULT
    }

    override fun updateFunction(model: BmiModel, event: BmiEvent): Next<BmiModel, Nothing> {
        return BmiLogic.update(model,event)
    }

    override fun render(model: BmiModel) {
        val weight : String = model.getTheWeight().toString() + " " + weightUnit
        val height : String = model.getTheHeight().toString() + " " + heightUnit

        bmiTextView.text = model.getBmi().toString()
        bmiCategoryTextView.text = model.getWeightCategory()
        weightTextView.text = weight
        heightTextView.text = height
        measurementSystemSwitch.text = unitSystem
    }

    override fun effectHandler(): ObservableTransformer<Nothing, BmiEvent> {
        return NoOpEffectHandler()
    }
}
