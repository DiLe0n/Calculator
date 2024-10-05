package com.example.calculator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var number1: EditText
    private lateinit var number2: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var checkBoth: CheckBox
    private lateinit var spinnerOperations: Spinner
    private lateinit var buttonCalculate: Button
    private lateinit var textResult: TextView
    private lateinit var listView: ListView

    // Lista para almacenar el historial de operaciones
    private val history = mutableListOf<String>()
    private lateinit var listAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar los componentes
        number1 = findViewById(R.id.number1)
        number2 = findViewById(R.id.number2)
        radioGroup = findViewById(R.id.radioGroup)
        checkBoth = findViewById(R.id.checkBoth)
        spinnerOperations = findViewById(R.id.spinnerOperations)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textResult = findViewById(R.id.textResult)
        listView = findViewById(R.id.listView)

        // Configuración del Spinner de operaciones
        val operations = arrayOf("Suma", "Resta", "Multiplicación", "División")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, operations)
        spinnerOperations.adapter = adapter

        // Configuración del ListView para el historial
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, history)
        listView.adapter = listAdapter

        // Botón para calcular
        buttonCalculate.setOnClickListener { calculate() }
    }

    private fun calculate() {
        // Obtener los números ingresados
        val num1 = number1.text.toString().toDoubleOrNull()
        val num2 = number2.text.toString().toDoubleOrNull()

        if (num1 != null && num2 != null) {
            var result = 0.0
            var operationDescription = ""

            // Operación basada en los RadioButtons
            val selectedRadio: Int = radioGroup.checkedRadioButtonId
            if (selectedRadio != -1) {
                val radioButton: RadioButton = findViewById(selectedRadio)
                result = if (radioButton.text == "Suma") {
                    operationDescription = "Suma"
                    num1 + num2
                } else {
                    operationDescription = "Resta"
                    num1 - num2
                }
            }

            // Si el CheckBox está seleccionado, realiza ambas operaciones
            if (checkBoth.isChecked) {
                val sum = num1 + num2
                val sub = num1 - num2
                history.add("Suma: $sum, Resta: $sub")
                listAdapter.notifyDataSetChanged()
            }

            // Operación basada en el Spinner
            val selectedOperation = spinnerOperations.selectedItem.toString()
            result = when (selectedOperation) {
                "Suma" -> {
                    operationDescription = "Suma"
                    num1 + num2
                }
                "Resta" -> {
                    operationDescription = "Resta"
                    num1 - num2
                }
                "Multiplicación" -> {
                    operationDescription = "Multiplicación"
                    num1 * num2
                }
                "División" -> {
                    operationDescription = "División"
                    if (num2 != 0.0) num1 / num2 else Double.NaN
                }
                else -> result
            }

            // Mostrar el resultado en el TextView
            textResult.text = "Resultado: $result"

            // Agregar el resultado al historial
            history.add("$operationDescription de $num1 y $num2 = $result")
            listAdapter.notifyDataSetChanged() // Actualizar el ListView
        } else {
            Toast.makeText(this, "Por favor ingresa números válidos", Toast.LENGTH_SHORT).show()
        }
    }
}

