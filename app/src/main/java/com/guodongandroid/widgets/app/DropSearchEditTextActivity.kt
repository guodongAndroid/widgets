package com.guodongandroid.widgets.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.guodong.android.widget.edittext.DropSearchEditText
import com.guodongandroid.widgets.app.databinding.ActivityDropSearchEditTextBinding

/**
 * Created by guodongAndroid on 2021/8/2.
 */
class DropSearchEditTextActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDropSearchEditTextBinding

    private var sorted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDropSearchEditTextBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        val adapter = Adapter(generateModels())

        mBinding.dropSearch.setAdapter(adapter)

        mBinding.dropSearch.isExpandClickRange = false
        mBinding.dropSearch.clickOffset = 500

        mBinding.dropSearch.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                this,
                "position: $position, ${adapter.getItem(position).name}",
                Toast.LENGTH_SHORT
            ).show()
        }

        val model = Model("Add Model", 14, 18)
        val models = listOf(
            Model("Add Model 1", 15, 18),
            Model("Add Model 2", 16, 18),
            Model("Add Model 3", 17, 18)
        )

        mBinding.btnAdd.setOnClickListener {
            adapter.add(model)
        }

        mBinding.btnInsert.setOnClickListener {
            adapter.insert(0, model)
        }

        mBinding.btnAddAll.setOnClickListener {
            adapter.addAll(models)
        }

        mBinding.btnRemove.setOnClickListener {
            adapter.remove(model)
        }

        mBinding.btnClear.setOnClickListener {
            adapter.clear()
        }

        mBinding.btnSort.setOnClickListener {
            sorted = !sorted
            if (sorted) {
                adapter.sort { o1, o2 -> o2.id.compareTo(o1.id) }
            } else {
                adapter.sort { o1, o2 -> o1.id.compareTo(o2.id) }
            }
        }

        mBinding.btnReset.setOnClickListener {
            adapter.reset()
        }
    }

    private fun generateModels(): MutableList<Model> {
        return mutableListOf(
            Model("guodongAndroid", 1, 18),
            Model("guodongJava", 2, 18),
            Model("guodongC#", 3, 18),
            Model("guodongIOS", 4, 18),
            Model("guodongC++", 5, 18),
            Model("神经内科", 6, 50),
            Model("肾内科", 7, 39),
            Model("心累科", 8, 33),
            Model("皮肤科", 9, 35),
            Model("主任医生", 10, 45),
            Model("副主任医生", 11, 43),
            Model("教授", 12, 80),
            Model("副教授", 13, 78),
        )
    }

    private data class Model(val name: String, val id: Int, val age: Int) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Model

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }

    private class Adapter(objects: MutableList<Model>) :
        DropSearchEditText.Adapter<Model, Adapter.ViewHolder>(objects) {

        override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = getItem(position).name
        }

        override fun filter(item: Model, constraint: String): Boolean {
            return item.name.contains(constraint, true)
        }

        override fun convertResultToString(result: Model): CharSequence {
            return result.name
        }

        private class ViewHolder(itemView: View) : DropSearchEditText.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(android.R.id.text1)
        }
    }
}
