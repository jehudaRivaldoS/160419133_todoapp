package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*


class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        viewModel.fetch(uuid)

        btnAdd.setOnClickListener {
            val rad = view.findViewById<RadioButton>(rdoGroup.checkedRadioButtonId)
            viewModel.update(txtToDoList.text.toString(), txtNotes.text.toString(), rad.tag.toString().toInt(), uuid)
            Toast.makeText(it.context, "Todo Updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }

        txtJudulTodo.text = "Edit Todo"
        btnAdd.text = "Save Changes"
        observeViewModel()

    }
    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            txtToDoList.setText(it.title)
            txtNotes.setText(it.notes)

            when(it.priority)
            {
                3->radioHigh.isChecked = true
                2->radioMedium.isChecked = true
                1->radioLow.isChecked = true
            }
        })
    }


}