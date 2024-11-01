package com.example.calculadoracashea

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myandroidapplication.CalculadoraCashea
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    val calculadoraCashea = CalculadoraCashea()

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        calculadoraCashea()

    }

    // Metodo que, usando Threads, muestre la inicial y las cuotas de una orden de pago usando cashea
    private fun calculadoraCashea() {

        //Widgets
        val nivelCasheaGroup: RadioGroup = findViewById(R.id.nivelCasheaGroup)
        val cuotasGroup = findViewById<RadioGroup>(R.id.cuotasGroup)
        val saldoText: EditText = findViewById(R.id.saldoText)
        val montoText: EditText = findViewById(R.id.montoText)
        val resultadoText: TextView = findViewById(R.id.ResultadosText)

        var boolEntrada = fun(): Boolean { return nivelCasheaGroup.checkedRadioButtonId > 0 &&
                                            (saldoText.text.isNotEmpty() && saldoText.text.toString().toFloat() >= 1.0F) &&
                                            (montoText.text.isNotEmpty()) && montoText.text.toString().toFloat() >= 25.0F &&
                                            cuotasGroup.checkedRadioButtonId > 0}


        @SuppressLint("SetTextI18n")
        fun ImprimirResultados()
        {

            //Se comprueba que todos los datos hayan sido correctamente
            if (boolEntrada())
            {

                //Se capturan los valores de los datos numericos
                var saldo = saldoText.text.toString().toFloat()
                var monto = montoText.text.toString().toFloat()

                // Se imprimen los datos en pantalla
                resultadoText.text = """
                    Pago Inicial: 
                     ${calculadoraCashea.calcularInicial(calculadoraCashea.setNivel(), saldo, monto)}${'$'}
                     
                    Con ${calculadoraCashea.setNCuotas()} cuotas quincenales de : ${(calculadoraCashea.calcularCuotas(calculadoraCashea.setFinanciado(), calculadoraCashea.setNCuotas()) )}${'$'} 
                    
                """.trimIndent()
            }
            // En caso contrario, se limpia el TextView
            else resultadoText.text = " "

        }

        //Widgets
        val seekBar: SeekBar = findViewById(R.id.entradaSeekBar)

        var numero: Int = 0

        //Se captura el evento cuando el usuario mantiene pulsado el seekbar
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                // Se comprueba si el boton se deliza a la derecha o a la izquierda
                numero = p1

                montoText.setText(numero.toString())
                ImprimirResultados()

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //Toast.makeText(applicationContext, "Empieza a pulsar", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //Toast.makeText(applicationContext, "Pulsado", Toast.LENGTH_SHORT).show()
            }


        })

        // Se captura el evento cuando el usuario ingresa un valor en la entrada de texto
        montoText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(dig: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //Se verifica el valor de la entrada de texto para ubicar la posicion del boton
                val input = dig.toString().toIntOrNull()

                //Se comprueba si el digito es valido
                if (input != null)

                // Si el valor de la entrada esta en el rango, cambia la posicion del boton
                    if (input in 25..10000)
                        seekBar.progress = input

            }

            override fun afterTextChanged(p0: Editable?) {

                // Se posiciona el cursor siempre a la derecha al ingresar en la entrada de texto

            }


        })



        // Se captura el evento cuando el usuario pulsa un boton de nivel de cashea
        nivelCasheaGroup.setOnCheckedChangeListener{group, checkId ->

            // Se obtiene el boton pulsado
            val selectedButton = group.findViewById<RadioButton>(checkId)

            // Se ejecuta la funcion dependiendo del boton pulsado
            when(selectedButton?.id)
            {

                R.id.nivel1Check -> calculadoraCashea.getNivel(1)
                R.id.nivel2Check -> calculadoraCashea.getNivel(2)
                R.id.nivel3Check -> calculadoraCashea.getNivel(3)
                R.id.nivel4Check -> calculadoraCashea.getNivel(4)
                R.id.nivel5Check -> calculadoraCashea.getNivel(5)


            }

            ImprimirResultados()

        }

        //Se captura el evento cuando se pulsa un boton de las cuotas
        cuotasGroup.setOnCheckedChangeListener{group, checkId ->

            val selectedButton = group.findViewById<RadioButton>(checkId)

            // Se ejecuta la funcion dependiendo del boton pulsado
            when(selectedButton?.id)
            {

                R.id.cuota3Check -> calculadoraCashea.getNCuotas(3)
                R.id.cuota6Check -> calculadoraCashea.getNCuotas(6)
                R.id.cuota9Check -> calculadoraCashea.getNCuotas(9)
                R.id.cuota12Check -> calculadoraCashea.getNCuotas(12)
                R.id.cuota15Check -> calculadoraCashea.getNCuotas(15)

            }

            ImprimirResultados()

        }

        // Se capturan el evento cuando el usuario teclea en una entrada de texto
        fun keyListener(editText: EditText)
        {

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
                {

                    ImprimirResultados()

                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

        }

        keyListener(montoText)
        keyListener(saldoText)

    }

}