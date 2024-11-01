package com.example.myandroidapplication

import kotlin.math.roundToInt

public class CalculadoraCashea()
{

    private var nivel:Int = 0
    private var saldo:Float = 0.0F
    private var monto:Float = 0.0F
    private var nCuotas:Int = 0
    private var inicial:Float = 0.0F
    private var financiado:Float = 0.0F
    private var cuota:Float = 0.0F

    fun getNivel(nivel:Int) { this.nivel = nivel }
    fun getsaldo(saldo: Float) { this.saldo = saldo }
    fun getMonto(monto: Float) { this.monto = monto }
    fun getNCuotas(nCuotas: Int) { this.nCuotas = nCuotas }
    fun getInicial(inicial:Float){ this.inicial = inicial}
    fun getFinanciado(financiado:Float){ this.financiado = financiado}

    fun setinicial():Float {return this.inicial}
    fun setNCuotas():Int {return this.nCuotas}
    fun setFinanciado():Float {return this.financiado}
    fun setNivel(): Int {return this.nivel}

    //Funcion que devuelve el valor de la cuota quincenal
    fun calcularCuotas(Financiado: Float, nCuotas: Int):Float
    {

        return (((this.setFinanciado() / this.setNCuotas().toFloat()) * 100).roundToInt().toFloat() / 100)

    }

    //Funcion que retorna el monto inicial
    fun calcularInicial(nivel: Int, saldo: Float, monto: Float):Float
    {

        var inicial:Float = 0.0F
        var porcentaje:Float = 0.0F

        // Se calcula la inicial dependiendo del nivel
        when(nivel)
        {

            1 -> porcentaje = 0.6F
            2 -> porcentaje = 0.5F
            in 3..5 -> porcentaje = 0.4F

            else -> print("Dato invalido")

        }

        inicial = monto * porcentaje

        // Si el saldo es menor a la inicial, este sera el monto financiado
        if(saldo < inicial)
        {

            getFinanciado(saldo)
            inicial = monto - saldo

        } else getFinanciado(monto - inicial)

        return ((inicial * 100).roundToInt().toFloat() / 100)

    }

    //Funcion que muestra en pantalla la inicial y las cuotas de la orden de cashea
    fun imprimirResultados()
    {

        //Se muestra la inicial
        println("Inicial: " + this.setinicial() + "\n")

        //Se muestran las coutas quincenales
        println("Coutas: ")

        for (i in 1 .. this.setNCuotas())
            println("Cuota $i: ${(this.setFinanciado() / this.setNCuotas())}")

    }

    constructor(nivel:Int, saldo:Float, monto:Float, nCuotas:Int):this()
    {

        getNivel(nivel)
        getsaldo(saldo)
        getMonto(monto)
        getNCuotas(nCuotas)

        getInicial( calcularInicial(this.nivel, this.saldo, this.monto) )

    }

}
