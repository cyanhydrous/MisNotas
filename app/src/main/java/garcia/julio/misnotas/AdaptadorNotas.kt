package garcia.julio.misnotas

import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.nota_layout.view.*
import java.io.File
import java.lang.Exception

class AdaptadorNotas:BaseAdapter {
    var context: Context
    var notas = ArrayList<Nota>()

    constructor(context: Context, notas: ArrayList<Nota>){
        this.context = context
        this.notas = notas
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota_layout, null)
        var nota = notas[position]
        vista.tv_titulo_det.text = nota.titulo
        vista.tv_contenido_det.text = nota.contenido

        vista.btn_borrar.setOnClickListener{
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }

    override fun getItem(position: Int): Any {
        return notas[0]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return notas.size
    }

    fun eliminar(titulo:String){
        if (titulo == ""){
            Toast.makeText(context, "Error: Titulo vacío", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val archivo = File(ubicacion(), titulo+".txt")
                archivo.delete()
                Toast.makeText(context, "Se eliminó la nota!",Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun ubicacion():String{
        val album = File(Environment.getExternalStorageDirectory(), "notas")
        if(!album.exists()){
            album.mkdir()
        }
        return album.absolutePath
    }
}