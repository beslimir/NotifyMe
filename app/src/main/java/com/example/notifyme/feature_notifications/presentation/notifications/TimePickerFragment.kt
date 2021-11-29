package com.example.notifyme.feature_notifications.presentation.notifications

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var mCallback: SelectedTime? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        mCallback?.onSelectedTime("$hour:$minute")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = activity as SelectedTime
        } catch (e: ClassCastException) {
            Toast.makeText(activity, "Exception!", Toast.LENGTH_SHORT).show()
        }
    }
}