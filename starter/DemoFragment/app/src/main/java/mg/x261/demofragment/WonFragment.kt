package mg.x261.demofragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import mg.x261.demofragment.databinding.FragmentGameWonBinding


class WonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_game_won, container, false)
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_won,
            container,
            false
        )

        binding.nextMatchButton.setOnClickListener {
            View ->
            view?.findNavController()?.navigate(R.id.action_wonFragment_to_titleFragment)
        }


        return binding.root


    }


}