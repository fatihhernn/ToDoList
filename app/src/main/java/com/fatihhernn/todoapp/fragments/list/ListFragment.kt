package com.fatihhernn.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.GridLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.fatihhernn.todoapp.R
import com.fatihhernn.todoapp.data.models.ToDoData
import com.fatihhernn.todoapp.data.viewModel.SharedViewModel
import com.fatihhernn.todoapp.data.viewModel.ToDoViewModel
import com.fatihhernn.todoapp.databinding.FragmentListBinding
import com.fatihhernn.todoapp.fragments.list.adapter.ListAdapter
import com.fatihhernn.todoapp.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(),SearchView.OnQueryTextListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }

    private val mToDoViewModel:ToDoViewModel by viewModels()
    private val mSharedViewModel:SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentListBinding.inflate(inflater,container,false)

        setUpRecyclerView()

        getAllData()

        showEmptyView()

        listeners()

        setMenu()

        hideKeyboard(requireActivity())

        return binding.root
    }

    private fun showEmptyView() {
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })
    }

    private fun getAllData() {
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter=adapter
        //binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        //binding.recyclerView.layoutManager=GridLayoutManager(requireContext(),2)
        binding.recyclerView.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        binding.recyclerView.itemAnimator=SlideInUpAnimator().apply {
            addDuration=300
        }

        swipeToDelete(binding.recyclerView)
    }

    private fun showEmptyDatabaseViews(emptyDatabase:Boolean) {
       if (emptyDatabase){
           binding.noDataImageView.visibility=View.VISIBLE
           binding.noDataTextView.visibility=View.VISIBLE
       }else{
           binding.noDataImageView.visibility=View.INVISIBLE
           binding.noDataTextView.visibility=View.INVISIBLE
       }
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallBack=object :SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem=adapter.dataList[viewHolder.adapterPosition]

                //Delete Item
                mToDoViewModel.deleteItem(deletedItem)
                adapter.notifyItemChanged(viewHolder.adapterPosition)

                //Restore Deleted Item
                restoredDeletedData(viewHolder.itemView,deletedItem,viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper=ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    private fun restoredDeletedData(view:View,deletedItem:ToDoData,position:Int){
        val snackBar=Snackbar.make(
            view,
            "Deleted ${deletedItem.title}",
            Snackbar.LENGTH_LONG)

        snackBar.setAction("Undo"){
            mToDoViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
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

            //search
        val search=menu.findItem(R.id.menu_search)
        val searchView= search.actionView as SearchView
        searchView.isSubmitButtonEnabled=true
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
                R.id.menu_delete_all -> confirmRemoval()
                R.id.menu_priority_high -> mToDoViewModel.sortByHighPriority.observe(this, Observer {
                    adapter.setData(it)
                })
                R.id.menu_priority_low -> mToDoViewModel.sortByLowPriority.observe(this, Observer {
                    adapter.setData(it)
                })
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null){
            searchThroughDatabase(query)
        }
        return true
    }
    override fun onQueryTextChange(query: String?): Boolean {
        if(query!=null){
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery=query
        searchQuery="%$searchQuery%"

        mToDoViewModel.searchDatabase(searchQuery).observe(this, Observer { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }



    //Show AlertDialog to confirm Removal of All items from Database Table
    private fun confirmRemoval() {
        val builder= AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(),"Successfully removed everything", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No"){_,_->

        }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }




}