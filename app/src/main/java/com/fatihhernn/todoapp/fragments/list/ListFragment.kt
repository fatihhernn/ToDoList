package com.fatihhernn.todoapp.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fatihhernn.todoapp.R
import com.fatihhernn.todoapp.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentListBinding.inflate(inflater,container,false)

        listeners()

        return binding.root
    }

    private fun listeners(){
        binding.floatingActionButton.setOnClickListener{
            findNavController() .navigate(R.id.action_listFragment_to_addFragment)
        }
        binding.listLayout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}