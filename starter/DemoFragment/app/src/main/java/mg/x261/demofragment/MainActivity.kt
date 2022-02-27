package mg.x261.demofragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Adding support to up button in the action bar, using navigation controller
        val navController = this.findNavController(R.id.myNavHostFragment)
        //Link the navController to the actionbar
        NavigationUI.setupActionBarWithNavController(this, navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        var navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }
}