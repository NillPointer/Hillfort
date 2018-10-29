package org.wit.hillfort.activities

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_password_changer.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.helpers.sha256
import org.wit.hillfort.main.MainApp

class HillfortSettingsFragment: PreferenceFragmentCompat(), AnkoLogger {

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    addPreferencesFromResource(R.xml.preferences)

    findPreference("pref_user_email").setOnPreferenceChangeListener { preference, newValue -> changeUserEmail(newValue) }
    findPreference("pref_user_password").setOnPreferenceClickListener { changeUserPassword() }
  }

  private fun changeUserEmail(newValue: Any): Boolean{
    val email = newValue.toString()
    if (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
      return true
    }
    Toast.makeText(context, R.string.signup_error_email, Toast.LENGTH_LONG).show()
    return false
  }

  private fun changeUserPassword(): Boolean{
    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_password_changer, null)
    val builder = AlertDialog.Builder(context)
        .setView(dialogView)
        .setTitle(R.string.settings_password)
    val passwordChangerDialog = builder.show()

    dialogView.btn_cancel_dialog.setOnClickListener {passwordChangerDialog.dismiss() }

    dialogView.btn_ok_dialog.setOnClickListener {
      if (dialogView.password_dialog.text.isEmpty() || dialogView.password_dialog.text.length < 5){
        Toast.makeText(context, R.string.signup_error_password, Toast.LENGTH_LONG).show()
      } else if (dialogView.repassword_dialog.text.toString() != dialogView.password_dialog.text.toString()){
        Toast.makeText(context, R.string.signup_error_repassword, Toast.LENGTH_LONG).show()
      } else {
        val passwordHash = sha256(dialogView.password_dialog.text.toString())
        preferenceManager.sharedPreferences.edit().putString("pref_user_password", passwordHash).apply()
        passwordChangerDialog.dismiss()
      }
    }
    return true
  }
}