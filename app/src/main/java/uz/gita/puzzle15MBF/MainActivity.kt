package uz.gita.puzzle15MBF

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import uz.gita.puzzle15MBF.database.MySharedPreferences
import uz.gita.puzzle15MBF.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var pressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            (supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment).findNavController()

        if (MySharedPreferences.getBoolean(
                resources.getString(R.string.shp_fast_start),
                false
            )
        ) navController.navigate(R.id.action_fragmentMain_to_fragmentGame)
    }
}