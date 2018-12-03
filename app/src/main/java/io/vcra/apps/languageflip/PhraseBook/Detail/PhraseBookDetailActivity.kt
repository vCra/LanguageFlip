package io.vcra.apps.languageflip.PhraseBook.Detail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.view.View
import android.widget.Toast
import io.vcra.apps.languageflip.PhraseBook.List.PhraseBookCreateActivity
import io.vcra.apps.languageflip.R
import io.vcra.apps.languageflip.data.phrase.Phrase
import io.vcra.apps.languageflip.data.phrase.PhraseListAdapter
import io.vcra.apps.languageflip.data.phrase.PhraseRepository
import io.vcra.apps.languageflip.data.phrase.PhraseViewModel
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook

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

        val recyclerView = findViewById<RecyclerView>(R.id.detail_recycler_view)
        val adapter = PhraseListAdapter(this)
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        // Add snapping, so cards fill the screen always
        val sh = LinearSnapHelper()
        sh.attachToRecyclerView(recyclerView)

        val fab = this.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@PhraseBookDetailActivity, PhraseCreateActivity::class.java)
            startActivityForResult(intent, NEW_PHRASE_ACTIVITY_REQUEST_CODE)
        }

        mViewModel!!.phrases.observe(this, Observer { phrases -> adapter.setPhrases(phrases) })

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

    companion object {

        val NEW_PHRASE_ACTIVITY_REQUEST_CODE = 2
    }

}
