package eka.giros

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class SupportCreator : Fragment() {

    private lateinit var trakteerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_support_creator, container, false)

        trakteerButton = view.findViewById(R.id.trakteerButton)

        trakteerButton.setOnClickListener {
            val urlTrakteer = "https://trakteer.id/ekaadev/tip"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlTrakteer)
            startActivity(intent)
        }


        return view
    }

}