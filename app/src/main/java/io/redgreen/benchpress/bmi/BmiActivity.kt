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

class BmiActivity : BaseActivity<BmiModel, BmiEvent, Nothing>() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, BmiActivity::class.java))
        }
    }

    private var weightUnit = "Kg" // <!-- FIXME Please fetch these from string resources.
    private var heightUnit = "Cm" // <!-- FIXME Replace all 'var' with 'val'
    private var unitSystem = "Metric Units"

    private val minWeightInKg by lazy(LazyThreadSafetyMode.NONE) {
        resources.getInteger(R.integer.min_weight_kg)
    }

    private val minHeightInCm  by lazy(LazyThreadSafetyMode.NONE) {
        resources.getInteger(R.integer.min_height_cm)
    }

    override fun layoutResId(): Int =
        R.layout.bmi_activity

    override fun setup() {
        setupWeightSeekBar()
        setupHeightSeekBar()
        setupMeasurementUnitSwitch()
    }

    override fun initialModel(): BmiModel =
        BmiModel.modelFor(48.0F,160.0F)

    override fun updateFunction(
        model: BmiModel,
        event: BmiEvent
    ): Next<BmiModel, Nothing> =
        BmiLogic.update(model,event)

    override fun render(model: BmiModel) {
        // TODO, Move this into a view driver
        val weight = "${model.getTheWeight()} $weightUnit"
        val height = "${model.getTheHeight()} $heightUnit"

        bmiTextView.text = model.bmi.toString()
        bmiCategoryTextView.text = model.weightCategory.category // FIXME, add localization
        weightTextView.text = weight
        heightTextView.text = height
        measurementSystemSwitch.text = unitSystem
    }

    override fun effectHandler(): ObservableTransformer<Nothing, BmiEvent> =
        NoOpEffectHandler()

    private fun setupWeightSeekBar() {
        weightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                eventSource.notifyEvent(WeightChangeEvent((minWeightInKg + progress).toFloat()))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                /* no-op */
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                /* no-op */
            }
        })
    }

    private fun setupHeightSeekBar() {
        heightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                eventSource.notifyEvent(HeightChangeEvent((minHeightInCm + progress).toFloat()))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                /* no-op */
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                /* no-op */
            }
        })
    }

    private fun setupMeasurementUnitSwitch() {
        measurementSystemSwitch.setOnCheckedChangeListener { _, usingMetric ->
//            FIXME, This should be covered under tests (possibly a view driver)
            weightUnit = if (usingMetric) "Kg" else "pounds"
            heightUnit = if (usingMetric) "Cm" else "inches"
            unitSystem = if (usingMetric) "Metric" else "Imperial"

            val measurementType = if (usingMetric) MeasurementType.METRIC else MeasurementType.IMPERIAL
            eventSource.notifyEvent(UnitChangeEvent(measurementType))
        }
    }
}
