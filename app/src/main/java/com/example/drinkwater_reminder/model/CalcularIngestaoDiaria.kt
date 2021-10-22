package com.example.drinkwater_reminder.model

class CalcularIngestaoDiaria {

    private val Ml_JOVEM = 40.0
    private val Ml_ADULTO = 35.0
    private val Ml_IDOSO = 30.0
    private val Ml_MAIS_DE_66_ANOS = 25

    private var resultdo_ML = 0.0
    private var resultdo_total_ML = 0.0

    fun CalcularTotalML(peso: Double, idade: Int) {
        if (idade <= 17){
            resultdo_ML = peso * Ml_JOVEM
            resultdo_total_ML = resultdo_ML
        }else if(idade <= 55){
            resultdo_ML = peso * Ml_ADULTO
            resultdo_total_ML = resultdo_ML
        }else if(idade <= 65){
            resultdo_ML = peso * Ml_IDOSO
            resultdo_total_ML = resultdo_ML
        }else{
            resultdo_ML = peso * Ml_MAIS_DE_66_ANOS
            resultdo_total_ML = resultdo_ML
        }
    }

    fun ResultadoML():Double{
        return  resultdo_total_ML
    }

}