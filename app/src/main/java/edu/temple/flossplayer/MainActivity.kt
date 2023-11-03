package edu.temple.flossplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val isSingleContainer: Boolean by lazy {
        findViewById<View>(R.id.container2) == null
    }

    private val bookViewModel: BookViewModel by lazy {
        ViewModelProvider(this)[BookViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            if (isSingleContainer) {
                val selectedBook = bookViewModel.getSelectedBook()?.value
                if (selectedBook != null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container1, BookPlayerFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container1, BookListFragment())
                        .setReorderingAllowed(true)
                        .commit()
                }
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container1, BookListFragment())
                    .replace(R.id.container2, BookPlayerFragment())
                    .commit()
            }
        }

        bookViewModel.getSelectedBook()?.observe(this) {
            if (!bookViewModel.hasViewedSelectedBook()) {
                if (isSingleContainer) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container1, BookPlayerFragment())
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit()
                    bookViewModel.markSelectedBookViewed()
                }
            }
        }
    }

    override fun onBackPressed() {
        val clearSelectedBook = bookViewModel.clearSelectedBook()
        super.onBackPressed()
    }
}
