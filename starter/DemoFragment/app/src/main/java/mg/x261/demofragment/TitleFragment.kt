package mg.x261.demofragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import mg.x261.demofragment.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    //Inflating and Returning the View with DataBindingUtil
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )

        // method 1:
//        binding.startButton.setOnClickListener {
//            // The navigation fragment is the host of our fragments.
//            // Thus we need to navigate it to access the other fragments
//
//             View -> Navigation.findNavController(View)
//             .navigate(R.id.action_titleFragment_to_gameFragment)
//        }
        // method 2:
        // use navigation to create the on click listener
        binding.startButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
        )


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // inflate the in my fragment
        inflater.inflate(R.menu.oveflow_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Navigate to the about fragment
        return NavigationUI.onNavDestinationSelected(
            item, requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }
}