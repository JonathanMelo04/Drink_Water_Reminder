package com.example.drinkwater_reminder

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.drinkwater_reminder.databinding.ActivityMainBinding
import com.example.drinkwater_reminder.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private var resultadoML = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual = 0
    var minutosAtuais = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        //IniciarComponentes()
        calcularIngestaoDiaria = CalcularIngestaoDiaria()

        binding.btCalcular.setOnClickListener {

            if (binding.editPeso.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            } else if (binding.editIdade.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            } else {
                val peso = binding.editPeso.text.toString().toDouble()
                val idade = binding.editIdade.text.toString().toInt()
                calcularIngestaoDiaria.CalcularTotalML(peso,idade)
                resultadoML = calcularIngestaoDiaria.ResultadoML()
                val formatar = NumberFormat.getNumberInstance(Locale("pt","BR"))
                formatar.isGroupingUsed = false
                binding.txtResultadoMl.text = formatar.format(resultadoML) + " " + "ml"
            }
        }

        binding.icRedefinir.setOnClickListener{
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_titulo)
                .setMessage(R.string.dialog_desc)
                .setPositiveButton("OK", {dialogInterface, i ->
                    binding.editPeso.setText("")
                    binding.editIdade.setText("")
                    binding.txtResultadoMl.text = ""
                })
            alertDialog.setNegativeButton("Cancelar", {dialogInterface, i ->

            })
            val dialog = alertDialog.create()
            dialog.show()
        }

        binding.btDefinirLembrete.setOnClickListener {
            calendario = Calendar.getInstance()
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutosAtuais = calendario.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this, { timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                    binding.txtHora.text = String.format("%02d",hourOfDay)
                    binding.txtMinuto.text = String.format("%02d",minutes)
                },horaAtual,minutosAtuais, true)
            timePickerDialog.show()
        }

        val txt_hora = binding.txtHora
        val txt_minutos = binding.txtMinuto

        binding.btDefinirAlarme.setOnClickListener{
            if(!txt_hora.text.toString().isEmpty() && !txt_minutos.text.toString().isEmpty()){
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, txt_hora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, txt_minutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_mensagem))
                startActivity(intent)

                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
            }
        }
    }
}