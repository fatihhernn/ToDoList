package com.fatihhernn.todoapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fatihhernn.todoapp.R
import com.fatihhernn.todoapp.data.models.Priority
import com.fatihhernn.todoapp.data.models.ToDoData
import com.fatihhernn.todoapp.data.viewModel.SharedViewModel
import com.fatihhernn.todoapp.data.viewModel.ToDoViewModel
import com.fatihhernn.todoapp.databinding.FragmentListBinding
import com.fatihhernn.todoapp.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel:SharedViewModel by viewModels()
    private val mToDoViewModel:ToDoViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentUpdateBinding.inflate(inflater,container,false)

        setMenu()

        setCurrentItem()

        return binding.root
    }

    private fun setCurrentItem() {
        binding.currentSpinnerPriority.onItemSelectedListener = mSharedViewModel.listener
        binding.currentTitleEt.setText(args.currentItem.title)
        binding.currentDescriptionEditText.setText(args.currentItem.description)
        binding.currentSpinnerPriority.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority),true)
    }

    private fun setMenu() {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu_fragment,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_update ->updateItem()
            R.id.menu_delete -> confirmItemDeleteDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemDeleteDialog() {
        val builder=AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(),"Successfully removed ${args.currentItem.title}",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_->

        }
        builder.setTitle("Delete : ${args.currentItem.title}?")
        builder.setMessage("Are you sure you want to remove? ${args.currentItem.title}")
        builder.create().show()
    }

    private fun updateItem() {
        val title=binding.currentTitleEt.text.toString()
        val description=binding.currentDescriptionEditText.text.toString()
        val getPriority=binding.currentSpinnerPriority.selectedItem.toString()

        val validation=mSharedViewModel.verifyDataFromUser(title,description)
        if (validation){
            val updatedData=ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )
            mToDoViewModel.updateData(updatedData)
            Toast.makeText(context,"Successfully updated!",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(context,"Please fill out all fields",Toast.LENGTH_LONG).show()
        }
    }


}