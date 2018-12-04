package io.vcra.apps.languageflip.PhraseBook.Detail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import io.vcra.apps.languageflip.R
import io.vcra.apps.languageflip.data.phrase.Phrase
import io.vcra.apps.languageflip.data.phrase.PhraseListAdapter
import io.vcra.apps.languageflip.data.phrase.PhraseViewModel
import io.vcra.apps.languageflip.games.QuizActivity
import io.vcra.apps.languageflip.tools.RecyclerItemClickListener

class PhraseBookDetailActivity : AppCompatActivity() {

    private var mViewModel: PhraseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phrase_book_detail_activity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mViewModel = ViewModelProviders.of(this).get(PhraseViewModel::class.java)
        val id = intent.getIntExtra("PhraseBook", -1)
        mViewModel!!.setPhraseBookID(id)

        val recyclerView: RecyclerView = findViewById(R.id.detail_recycler_view)
        val adapter = PhraseListAdapter(this)
        recyclerView.adapter = adapter
        val llm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.layoutManager = llm

        recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(this@PhraseBookDetailActivity, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        val popup = PopupMenu(this@PhraseBookDetailActivity, view)
                        popup.menuInflater.inflate(R.menu.phrase_book_detail_item_menu, popup.menu)
                        popup.setOnMenuItemClickListener { item ->
                            //TODO: Do this by not using the titles but instead the ID's
                            if (item.title == getString(R.string.rem_phrase)) {
                                // onRemoveItem(adapter, position)
                            } else if (item.title == getString(R.string.ren_phrase)) {
                                onUpdateItem(adapter, position)
                            } else {
                                Log.e("lf", "Context menu item selected which is not implemented for")
                            }
                            true
                        }
                        popup.show()
                    }
                })
        )

        // Add snapping, so cards fill the screen always
        val sh = LinearSnapHelper()
        sh.attachToRecyclerView(recyclerView)

        val fab = this.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@PhraseBookDetailActivity, PhraseCreateActivity::class.java)
            startActivityForResult(intent, NEW_PHRASE_ACTIVITY_REQUEST_CODE)
        }
        val fabgame = this.findViewById<FloatingActionButton>(R.id.fabgame)
        fabgame.setOnClickListener {
            val intent = Intent(baseContext, QuizActivity::class.java)
            intent.putExtra("PhraseBook", id)
            startActivity(intent)
        }

        mViewModel!!.phrases.observe(this, Observer { phrases -> adapter.setPhrases(phrases) })

        Handler().postDelayed({
            llm.scrollToPositionWithOffset(0, -100)
            Handler().postDelayed({
                recyclerView.smoothScrollToPosition(0)
            }, 500)
        }, 1000)
        //animation to show that the application is scrollable
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val phrase = Phrase(
                    data!!.getStringExtra(PhraseCreateActivity.LANG1REPLY),
                    data.getStringExtra(PhraseCreateActivity.LANG2REPLY),
                    this.intent.getIntExtra("PhraseBook", -1)
            )
            mViewModel!!.insert(phrase)

        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }

    private fun onUpdateItem(adapter: PhraseListAdapter, position: Int) {
        Log.i("lf.pb.l", "Update button pressed for " + adapter.getFromPosition(position).id.toString())
        val alertDialog = AlertDialog.Builder(this@PhraseBookDetailActivity)
        alertDialog.setView(layoutInflater.inflate(R.layout.phrase_dialog_update, null))
        alertDialog.setTitle("Update Phrase")
        val alertDialog1 = alertDialog.create()

        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.rename)) { _, which ->
            if (which == AlertDialog.BUTTON_POSITIVE) {
                val lang1: EditText? = alertDialog1.findViewById(R.id.edit_phrase_lang1)
                val lang2: EditText? = alertDialog1.findViewById(R.id.edit_phrase_lang2)
                val phrase: Phrase = adapter.getFromPosition(position)
                phrase.primaryWord = lang1?.text.toString()
                phrase.secondaryWord = lang2?.text.toString()
                mViewModel!!.update(phrase)
            }
        }
        alertDialog1.show()
    }

    companion object {

        val NEW_PHRASE_ACTIVITY_REQUEST_CODE = 2
    }

}
