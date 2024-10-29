package com.example.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextSource: EditText
    private lateinit var editTextDestination: EditText
    private lateinit var spinnerSource: Spinner
    private lateinit var spinnerDestination: Spinner

    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "VND" to 24000.0,
        "JPY" to 110.0,
        "GBP" to 0.75
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextSource = findViewById(R.id.editTextSource)
        editTextDestination = findViewById(R.id.editTextDestination)
        spinnerSource = findViewById(R.id.spinnerSource)
        spinnerDestination = findViewById(R.id.spinnerDestination)

        val currencyArray = resources.getStringArray(R.array.currency_list)

        val currencyAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            currencyArray
        )
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSource.adapter = currencyAdapter
        spinnerDestination.adapter = currencyAdapter

        // Lắng nghe sự thay đổi
        editTextSource.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spinnerSource.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerDestination.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun convertCurrency() {
        val sourceCurrency = spinnerSource.selectedItem.toString()
        val destinationCurrency = spinnerDestination.selectedItem.toString()
        val sourceAmount = editTextSource.text.toString().toDoubleOrNull() ?: 0.0

        val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
        val destinationRate = exchangeRates[destinationCurrency] ?: 1.0

        val convertedAmount = (sourceAmount / sourceRate) * destinationRate
        editTextDestination.setText("%.2f".format(convertedAmount))
    }
}
