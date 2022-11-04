package com.edon.vigenerecipher

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.TransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.edon.vigenerecipher.classes.CryptEngine
import com.edon.vigenerecipher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bnd: ActivityMainBinding
    private var operation: Int = 0
    private var keyType:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        //onclick listener for type of key (number or alphabet)
        bnd.rgKeyType.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radNumber -> {
                    bnd.etKey.inputType = InputType.TYPE_CLASS_NUMBER
                    keyType = 0
                }
                R.id.radText -> {
                    bnd.etKey.inputType = InputType.TYPE_CLASS_TEXT
                    keyType = 1
                }
            }
        }

        //onclick listener for type of operation
        bnd.rgOpType.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radEncode -> {
                    bnd.btnConvert.text = getString(R.string.encode)
                    operation = 0
                }
                R.id.radDecode -> {
                    bnd.btnConvert.text = getString(R.string.decode)
                    operation = 1
                }
            }
        }

        //onclick for button
        bnd.btnConvert.setOnClickListener { it->
            it.hideKeyboard()
            doConversion(it)
        }
    }

    private fun doConversion(it: View?) {
        if(bnd.tieMessage.text.toString().isEmpty() ||bnd.etKey.text.toString().isEmpty()){
            if(bnd.tieMessage.text.toString().isEmpty())
                bnd.tilMessage.error = "Please enter Message"
             if(bnd.etKey.text.toString().isEmpty())
                 bnd.etKey.error = "Please enter Shift"
        } else {
            if (keyType == 0 && operation == 0){
                numEncode(it)
            } else if(keyType == 0 && operation == 1){
                numDecode(it)
            } else if(keyType == 1 && operation == 0){
                textEncode(it)
            } else{
                textDecode(it)
            }
        }
    }

    private fun textDecode(view: View?) {
        //send message and shift to CryptEngine for encryption
        //TODO CHECK LENGTH OF SHIFT THAT IT'S NOT LONGER THAN MESSAGE
        bnd.tieCipherText.setText(CryptEngine.vigenereCypher(
            bnd.tieMessage.text.toString(),
            bnd.etKey.text.toString(),
        1))
    }

    private fun textEncode(view: View?) {
        //send message and shift to CryptEngine for encryption
        //TODO CHECK LENGTH OF SHIFT THAT IT'S NOT LONGER THAN MESSAGE
        bnd.tieCipherText.setText(CryptEngine.vigenereCypher(
            bnd.tieMessage.text.toString(),
            bnd.etKey.text.toString(),
        0))
    }

    //shift by a number
    private fun numDecode(view: View?) {
        //send message and shift to CryptEngine for encryption
        bnd.tieCipherText.setText(CryptEngine.decrypt(
            bnd.tieMessage.text.toString(),
            bnd.etKey.text.toString().toInt()))
    }

    //shift by a number
    fun numEncode(view: View?){
        //send message and shift to CryptEngine for encryption
        bnd.tieCipherText.setText(CryptEngine.encrypt(
            bnd.tieMessage.text.toString(),
            bnd.etKey.text.toString().toInt()))
    }

    //hide keyboard
    fun View.hideKeyboard(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}