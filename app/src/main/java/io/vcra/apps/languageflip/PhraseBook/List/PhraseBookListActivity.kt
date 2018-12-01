package io.vcra.apps.languageflip.PhraseBook.List


import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import io.vcra.apps.languageflip.PhraseBook.Detail.PhraseBookDetailActivity
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook
import io.vcra.apps.languageflip.data.phrasebook.PhraseBookListAdapter
import io.vcra.apps.languageflip.data.phrasebook.PhraseBookViewModel
import io.vcra.apps.languageflip.R
import io.vcra.apps.languageflip.tools.RecyclerItemClickListener


class PhraseBookListActivity : AppCompatActivity() {

    private var mPhraseBookViewModel: PhraseBookViewModel? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phrase_book_list_activity)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Get a new or existing ViewModel from the ViewModelProvider.
        mPhraseBookViewModel = ViewModelProviders.of(this).get(PhraseBookViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PhraseBookListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(this@PhraseBookListActivity, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val pb = adapter.getFromPosition(position)
                        val intent = Intent(baseContext, PhraseBookDetailActivity::class.java)
                        intent.putExtra("PhraseBook", pb.id)
                        startActivity(intent)
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        val popup = PopupMenu(this@PhraseBookListActivity, view)
                        popup.menuInflater.inflate(R.menu.phrase_book_list_item_menu, popup.menu)
                        popup.setOnMenuItemClickListener { item ->
                            //TODO: Do this by not using the titles but instead the ID's
                            if (item.title == "Remove") {
                                Log.i("lf.pb.l", "Remove button pressed for " + adapter.getFromPosition(position).id.toString())

                                val alertDialog = AlertDialog.Builder(this@PhraseBookListActivity).create()
                                alertDialog.setTitle(getString(R.string.areyousure))
                                alertDialog.setMessage(getString(R.string.removephrasebooktext))
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.sure)
                                ) { _, which ->
                                    if (which == AlertDialog.BUTTON_POSITIVE) {
                                        mPhraseBookViewModel!!.remove(adapter.getFromPosition(position))
                                    }
                                }
                                alertDialog.show()
                            } else if (item.title == getString(R.string.rename)) {
                                Log.i("lf.pb.l", "Rename button pressed for " + adapter.getFromPosition(position).id.toString())
                                val alertDialog = AlertDialog.Builder(this@PhraseBookListActivity)
                                alertDialog.setView(layoutInflater.inflate(R.layout.phrase_book_dialog_rename, null))
                                alertDialog.setTitle(R.string.rename_phrasebook)
                                alertDialog.setMessage(R.string.rename_msg)
                                val alertDialog1 = alertDialog.create()
                                alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.rename)
                                ) { _, which ->
                                    if (which == AlertDialog.BUTTON_POSITIVE) {
                                        val et = alertDialog1.findViewById<EditText>(R.id.edit_text_input)
                                        val newName = et!!.text.toString()
                                        mPhraseBookViewModel!!.rename(
                                                adapter.getFromPosition(position),
                                                newName)
                                    }
                                }
                                alertDialog1.show()
                            } else {
                                Log.e("lf", "Context menu item selected which is not implemented for")
                            }
                            true
                        }
                        popup.show()
                    }
                })
        )


        // Add an observer on the LiveData returned by getPhraseBooks.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mPhraseBookViewModel!!.allWords.observe(this, Observer { phraseBooks ->
            // Update the cached copy of the words in the adapter.
            adapter.setPhraseBooks(phraseBooks)
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@PhraseBookListActivity, PhraseBookCreateActivity::class.java)
            startActivityForResult(intent, NEW_PHRASE_ACTIVITY_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val phraseBook = PhraseBook(data!!.getStringExtra(PhraseBookCreateActivity.EXTRA_REPLY))
            mPhraseBookViewModel!!.insert(phraseBook)
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        val NEW_PHRASE_ACTIVITY_REQUEST_CODE = 1
    }
}