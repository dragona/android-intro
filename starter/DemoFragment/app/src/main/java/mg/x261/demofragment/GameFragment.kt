package mg.x261.demofragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import mg.x261.demofragment.databinding.FragmentGameBinding
import kotlin.properties.Delegates


class GameFragment : Fragment() {
    private var CORRECT: Int? = null
    var result = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )
        CORRECT = binding.firstAnswerRadioButton.id // we will set this here as the correct answer

        // Handle the changes to the radio buttons
        binding.questionRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            //val radio: RadioButton = findViewById(checkedId)
            binding.submitButton.visibility = View.VISIBLE
            // Check which radio button was clicked

            result = when (checkedId) {
                CORRECT -> true
                else -> false
            }
        }
        binding.submitButton.setOnClickListener { view: View ->
            if (result) {
                Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_wonFragment)
            } else {
                Navigation.findNavController(view)
                    .navigate(R.id.action_gameFragment_to_lostFragment)
            }
        }

        return binding.root

    }

}