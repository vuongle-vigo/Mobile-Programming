package com.example.calculatorproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var currentInput: String = ""
    private var operator: String = ""
    private var firstNumber: Double = 0.0
    private var secondNumber: Double = 0.0
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)

        val btnPlus: Button = findViewById(R.id.btnPlus)
        val btnMinus: Button = findViewById(R.id.btnMinus)
        val btnMultiply: Button = findViewById(R.id.btnMultiply)
        val btnDivide: Button = findViewById(R.id.btnDivide)
        val btnEquals: Button = findViewById(R.id.btnEquals)
        val btnDot: Button = findViewById(R.id.btnDot)
        val btnClear: Button = findViewById(R.id.btnC)
        val btnBackspace: Button = findViewById(R.id.btnBS)

        val numberClickListener = { number: String ->
            if (isNewOperation) {
                currentInput = ""
                isNewOperation = false
            }
            currentInput += number
            tvDisplay.text = currentInput
        }

        btn0.setOnClickListener { numberClickListener("0") }
        btn1.setOnClickListener { numberClickListener("1") }
        btn2.setOnClickListener { numberClickListener("2") }
        btn3.setOnClickListener { numberClickListener("3") }
        btn4.setOnClickListener { numberClickListener("4") }
        btn5.setOnClickListener { numberClickListener("5") }
        btn6.setOnClickListener { numberClickListener("6") }
        btn7.setOnClickListener { numberClickListener("7") }
        btn8.setOnClickListener { numberClickListener("8") }
        btn9.setOnClickListener { numberClickListener("9") }

        btnDot.setOnClickListener {
            if (!currentInput.contains(".")) {
                currentInput += "."
                tvDisplay.text = currentInput
            }
        }

        btnPlus.setOnClickListener { setOperator("+") }
        btnMinus.setOnClickListener { setOperator("-") }
        btnMultiply.setOnClickListener { setOperator("*") }
        btnDivide.setOnClickListener { setOperator("/") }

        btnEquals.setOnClickListener {
            if (operator.isNotEmpty()) {
                secondNumber = currentInput.toDoubleOrNull() ?: 0.0
                val result = calculateResult(firstNumber, secondNumber, operator)
                tvDisplay.text = result.toString()
                currentInput = result.toString()
                operator = ""
                isNewOperation = true
            }
        }

        btnClear.setOnClickListener {
            currentInput = ""
            firstNumber = 0.0
            secondNumber = 0.0
            operator = ""
            tvDisplay.text = "0"
        }

        btnBackspace.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                tvDisplay.text = currentInput
            }
        }
    }

    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toDoubleOrNull() ?: 0.0
            operator = op
            currentInput = ""
        }
    }

    private fun calculateResult(first: Double, second: Double, operator: String): Double {
        return when (operator) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> if (second != 0.0) first / second else Double.NaN // Xử lý chia cho 0
            else -> 0.0
        }
    }
}