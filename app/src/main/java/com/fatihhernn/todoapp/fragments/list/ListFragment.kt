package com.fatihhernn.todoapp.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

        setMenu()

        return binding.root
    }

    private fun setMenu() {
        setHasOptionsMenu(true)
    }

    private fun listeners(){
        binding.floatingActionButton.setOnClickListener{
            findNavController() .navigate(R.id.action_listFragment_to_addFragment)
        }
        binding.listLayout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.list_fragment_menu,menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}