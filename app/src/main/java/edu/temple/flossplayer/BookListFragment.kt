package edu.temple.flossplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class BookListFragment : Fragment() {
    private lateinit var  bookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookViewModel = ViewModelProvider (requireActivity())[BookViewModel:: class.java]
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onClick: (Book) -> Unit = {
            book: Book -> //update viewModel
            bookViewModel.setSelectedBook(book) // to not have an event repeat
        }

        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(requireActivity())

            bookViewModel.getBookList().observe(requireActivity()) {
                adapter = BookListAdapter(it, onClick)
            }
        }



    }
}

class BookListAdapter(private val bookList: BookList, private val onClick: (Book) -> Unit) :
    RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {       inner class BookViewHolder (layout: View) : RecyclerView.ViewHolder (layout){
    val titleTextView : TextView = layout.findViewById(R.id.titleTextView)
    val authorTextView : TextView = layout.findViewById(R.id.authorTextView)

    init{
        layout.setOnClickListener{
            onClick(bookList[adapterPosition])
        }
    }

}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.booklist_items_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return bookList.size()
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.titleTextView.text = bookList[position].title
        holder.authorTextView.text = bookList[position].author
    }
}

