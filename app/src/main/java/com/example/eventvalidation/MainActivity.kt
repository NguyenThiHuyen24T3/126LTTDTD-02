package com.example.eventvalidation

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPhone: TextInputLayout
    private lateinit var tilMsv: TextInputLayout
    private lateinit var tilClass: TextInputLayout

    private lateinit var edtName: TextInputEditText
    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtPhone: TextInputEditText
    private lateinit var edtMsv: TextInputEditText
    private lateinit var edtClass: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tilName = findViewById(R.id.tilName)
        tilEmail = findViewById(R.id.tilEmail)
        tilPhone = findViewById(R.id.tilPhone)
        tilMsv = findViewById(R.id.tilMsv)
        tilClass = findViewById(R.id.tilClass)

        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        edtPhone = findViewById(R.id.edtPhone)
        edtMsv = findViewById(R.id.edtMsv)
        edtClass = findViewById(R.id.edtClass)

        setupLiveValidation()

        findViewById<MaterialButton>(R.id.btnRegister)
            .setOnClickListener {
                validateAndRegister()
            }
    }

    private fun setupLiveValidation() {
        val fields = listOf(
            edtName to tilName,
            edtEmail to tilEmail,
            edtPhone to tilPhone,
            edtMsv to tilMsv,
            edtClass to tilClass
        )

        fields.forEach { (editText, layout) ->
            editText.addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (layout.error != null) {
                        layout.error = null
                    }
                }

                override fun afterTextChanged(
                    s: android.text.Editable?
                ) = Unit
            })
        }
    }

    private fun validateAndRegister() {
        val name = edtName.text?.toString()?.trim().orEmpty()
        val email = edtEmail.text?.toString()?.trim().orEmpty()
        val phone = edtPhone.text?.toString()?.trim().orEmpty()
        val msv = edtMsv.text?.toString()?.trim().orEmpty()
        val studentClass = edtClass.text?.toString()?.trim().orEmpty()

        tilName.error = null
        tilEmail.error = null
        tilPhone.error = null
        tilMsv.error = null
        tilClass.error = null

        var isValid = true
        var firstInvalid: TextInputEditText? = null

        fun markError(
            layout: TextInputLayout,
            editText: TextInputEditText,
            message: String
        ) {
            layout.error = message
            isValid = false

            if (firstInvalid == null) {
                firstInvalid = editText
            }
        }

        if (name.isBlank()) {
            markError(tilName, edtName, "Vui lòng nhập họ và tên")
        } else if (name.length < 2) {
            markError(tilName, edtName, "Họ tên phải có ít nhất 2 ký tự")
        }

        if (email.isBlank()) {
            markError(tilEmail, edtEmail, "Vui lòng nhập email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            markError(tilEmail, edtEmail, "Email không đúng định dạng")
        }

        if (phone.isBlank()) {
            markError(tilPhone, edtPhone, "Vui lòng nhập số điện thoại")
        } else if (!phone.matches(Regex("^0[0-9]{9}$"))) {
            markError(
                tilPhone,
                edtPhone,
                "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0"
            )
        }

        if (msv.isBlank()) {
            markError(tilMsv, edtMsv, "Vui lòng nhập mã sinh viên")
        } else if (!msv.matches(Regex("^[a-zA-Z0-9]{4,20}$"))) {
            markError(
                tilMsv,
                edtMsv,
                "MSV gồm 4–20 chữ cái hoặc chữ số"
            )
        }

        if (studentClass.isBlank()) {
            markError(tilClass, edtClass, "Vui lòng nhập lớp")
        } else if (!studentClass.matches(Regex("^[a-zA-Z0-9._-]{2,20}$"))) {
            markError(tilClass, edtClass, "Tên lớp không hợp lệ")
        }

        if (!isValid) {
            firstInvalid?.requestFocus()
            firstInvalid?.post {
                val manager = getSystemService(
                    Context.INPUT_METHOD_SERVICE
                ) as InputMethodManager

                firstInvalid?.let {
                    manager.showSoftInput(
                        it,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            }

            Toast.makeText(
                this,
                "Vui lòng kiểm tra lại thông tin!",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("NAME", name)
            putExtra("EMAIL", email)
            putExtra("PHONE", phone)
            putExtra("MSV", msv)
            putExtra("CLASS", studentClass)
        }

        startActivity(intent)
    }
}
