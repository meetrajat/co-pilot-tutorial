package com.example.a29th_mar_android_project.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.a29th_mar_android_project.R
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import com.example.a29th_mar_android_project.continent_detail.ContinentDetailActivity


class HomeActivity : AppCompatActivity() {

    lateinit var asiaCardView:CardView;
    lateinit var europeCardView: CardView;
    lateinit var northAmericaCardView: CardView;
    lateinit var southAmerticaCardView: CardView;
    lateinit var africaCardView: CardView;
    lateinit var australiaCardView: CardView;

    private val ASIA_BUTTON_IDENTIFIER = 1001;
    private val EUROPE_BUTTON_IDENTIFIER = ASIA_BUTTON_IDENTIFIER + 1;
    private val NORTH_AMERICA_BUTTON_IDENTIFIER = EUROPE_BUTTON_IDENTIFIER + 1;
    private val SOUTH_AMERICA_BUTTON_IDENTIFIER = NORTH_AMERICA_BUTTON_IDENTIFIER + 1;
    private val AUSTRALIA_BUTTON_IDENTIFIER = SOUTH_AMERICA_BUTTON_IDENTIFIER + 1;
    private val AFRICA_BUTTON_IDENTIFIER = AUSTRALIA_BUTTON_IDENTIFIER + 1;

    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupOnclick();
        setupOnClickListeners();
        setupViewModel();
    }

    private fun setupOnclick(){
        asiaCardView = findViewById(R.id.continent_asia_cardview);
        europeCardView = findViewById(R.id.continent_europe_cardview);
        northAmericaCardView = findViewById(R.id.continent_north_america_cardview);
        southAmerticaCardView = findViewById(R.id.continent_south_america_cardview);
        australiaCardView = findViewById(R.id.continent_australia_txtview);
        africaCardView = findViewById(R.id.continent_africa_cardview);
    }

    private fun setupViewModel(){
        homeViewModel.buttonClick.observe(
            this, Observer {
                when(it){
                    ASIA_BUTTON_IDENTIFIER -> redirectToContinentDetail(it);
                    EUROPE_BUTTON_IDENTIFIER -> redirectToContinentDetail(it);
                    NORTH_AMERICA_BUTTON_IDENTIFIER -> redirectToContinentDetail(it);
                    SOUTH_AMERICA_BUTTON_IDENTIFIER -> redirectToContinentDetail(it);
                    AUSTRALIA_BUTTON_IDENTIFIER -> redirectToContinentDetail(it);
                    AFRICA_BUTTON_IDENTIFIER -> redirectToContinentDetail(it);
                }
            }
        )

        homeViewModel.movementToContinentDetail.observe(
            this, Observer {
                val intent = Intent(this, ContinentDetailActivity::class.java);
                intent.putExtra("continentID", it);
                startActivity(intent);
            }
            )
    }

    private fun setupOnClickListeners(){
        asiaCardView.setOnClickListener {
            homeViewModel.validateClickAction(ASIA_BUTTON_IDENTIFIER)
        }
        europeCardView.setOnClickListener {
            homeViewModel.validateClickAction(EUROPE_BUTTON_IDENTIFIER)
        }
        northAmericaCardView.setOnClickListener {
            homeViewModel.validateClickAction(NORTH_AMERICA_BUTTON_IDENTIFIER)
        }
        southAmerticaCardView.setOnClickListener {
            homeViewModel.validateClickAction(SOUTH_AMERICA_BUTTON_IDENTIFIER)
        }
        australiaCardView.setOnClickListener {
            homeViewModel.validateClickAction(AUSTRALIA_BUTTON_IDENTIFIER)
        }
        africaCardView.setOnClickListener {
            homeViewModel.validateClickAction(AFRICA_BUTTON_IDENTIFIER)
        }
    }

    //TODO : i am doing redirection from activity or fragment. But where should i handle this redirection should
    // i do this in someother class but then android specific code will move away from activity and fragment
    //TODO : 2- Do i need to go to viewmodel or can i directly call some utility method to launch the activity
    private fun redirectToContinentDetail(continentID:Int){
        homeViewModel.validateMovementToContinentDetail(continentID);
    }


}