package com.consistence.pinyin.kit

import android.content.Context

import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.consistence.pinyin.R
import kotlinx.android.synthetic.main.kit_error_retry.view.*

class ErrorRetryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.kit_error_retry, this)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ErrorRetryView)
            kit_error_retry_message.text = resources.getText(
                    typedArray.getResourceId(
                            R.styleable.ErrorRetryView_ErrorRetryView_text,
                            R.string.kit_error_retry_default_message))
            typedArray.recycle()
        }
    }
}