package com.fatihhernn.todoapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatihhernn.todoapp.R
import com.fatihhernn.todoapp.data.viewModel.ToDoViewModel
import com.fatihhernn.todoapp.data.models.Priority
import com.fatihhernn.todoapp.data.models.ToDoData
import com.fatihhernn.todoapp.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAddBinding.inflate(inflater,container,false)

        setMenu()

        return binding.root
    }

    private fun setMenu() {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_add){
           insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle=binding.titleEt.text.toString()
        val mPriority=binding.spinnerPriority.selectedItem.toString()
        val mDescription=binding.descriptionEditText.text.toString()

        val validation=verifyDataFromUser(mTitle,mDescription)
        if (validation){
            val newData=ToDoData(
                0,
                mTitle,
                parsePriority(mPriority),
                mDescription
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(),"Successfully added",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        else {
            Toast.makeText(requireContext(),"Please fill out all fields",Toast.LENGTH_LONG).show()
        }
    }

    private fun verifyDataFromUser(title: String,description:String):Boolean{
        return if (TextUtils.isEmpty(title)||TextUtils.isEmpty(description)){
            false
        } else !(title.isEmpty()||description.isEmpty())
    }

    private fun parsePriority(priority: String):Priority{
        return when(priority){
            "High Priority" -> {Priority.HIGH}
            "Medium Priority" -> {Priority.MEDIUM}
            "Low Priority" -> {Priority.LOW}
            else -> Priority.LOW
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}