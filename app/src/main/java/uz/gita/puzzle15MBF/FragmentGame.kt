package uz.gita.puzzle15MBF

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import uz.gita.puzzle15MBF.database.MySharedPreferences
import uz.gita.puzzle15MBF.databinding.DialogWinnerBinding
import uz.gita.puzzle15MBF.databinding.FragmentGameBinding
import uz.gita.puzzle15MBF.extensions.invisible
import uz.gita.puzzle15MBF.extensions.onClick
import uz.gita.puzzle15MBF.extensions.snackMessage
import uz.gita.puzzle15MBF.extensions.visible
import kotlin.math.abs

class FragmentGame : Fragment(R.layout.fragment_game) {

    private val isGameRestarted: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isGamePaused: MutableLiveData<Boolean> = MutableLiveData(false)

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val cell = Array(4) { arrayOfNulls<AppCompatButton>(4) }
    private var numbers: ArrayList<Int> = ArrayList(16)
    private var x = 0
    private var y = 0
    private var counter = 0

    private var soundPool: SoundPool? = null
    private var mediaPlayer: MediaPlayer? = null
    private var soundPlayer: Int = 0
    private var winPlayer: Int = 0
    private var onBackPressedTime: Long = 0
    private var timeWhenPaused: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateNumbers()
        createSoundPool()
        createMediaPlayer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(layoutInflater, container, false)
        startGame()
//        startFakeGame()
        onBackPressed()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeViewBinding()
    }

    private fun subscribeViewBinding() = with(binding) {
        chronometer.start()
        buttonRestart.onClick {
            restartGame()
        }
        tvPlay.onClick {
            isGameRestarted.value = true
        }
        buttonPause.onClick {
            pauseGame()
        }
        buttonResume.onClick {
            isGamePaused.value = false
        }
    }

    private fun startGame() {
        initSolvableNumbers()
        loadViews()
        loadDataToViews()
    }

    private fun loadViews() {
        for (i in 0 until binding.gameBoard.childCount) {
            binding.gameBoard.getChildAt(i).background =
                AppCompatResources.getDrawable(requireContext(), R.drawable.shape_button)

            cell[i / 4][i % 4] = binding.gameBoard.getChildAt(i) as AppCompatButton?
            cell[i / 4][i % 4]?.tag = i

            cell[i / 4][i % 4]?.onClick { view ->
                val tag: Int = view.tag as Int
                canMove(tag / 4, tag % 4)
            }
        }
    }

    private fun loadDataToViews() {
        for (i in 0..15) {
            if (numbers[i] == 16) {
                x = i / 4
                y = i % 4
                cell[x][y]?.visibility = invisible()
                cell[x][y]?.text = resources.getText(R.string.empty_button_text)
                cell[x][y]?.isClickable = false
            } else cell[i / 4][i % 4]!!.text = numbers[i].toString()
        }
    }

    private fun canMove(i: Int, j: Int): Boolean {
        return when {
            ((abs(x - i) == 0 && abs(y - j) == 1) || (abs(x - i) == 1 && abs(y - j) == 0)) -> {
                // for current movements
                counter++
                // button move animation
                makeMoveButtonAnimation(x, y)
                // for sound effect
                playSound()
                // swap empty button with current button
                swapEmptyButtonWithCurrentButton(x, y, i, j)
                // changing value of moves
                binding.moves.text = counter.toString()

                // checking game for win
                when {
                    checkForWin() -> {
                        if (MySharedPreferences.getBoolean(
                                resources.getString(R.string.shp_fast_passage),
                                false
                            )
                        ) {
                            saveRecord()
                            restartGame()
                        } else showGameOverAnimationAndDialog()
                    }
                }

                return true
            }
            else -> false
        }
    }

    private fun swapEmptyButtonWithCurrentButton(x: Int, y: Int, i: Int, j: Int) {
        // for x and y (current swiped button)
        cell[x][y]?.visibility = visible()
        cell[x][y]?.text = cell[i][j]?.text.toString()
        cell[x][y]?.isClickable = true

        // for i and j (empty button)
        cell[i][j]?.visibility = invisible()
        cell[i][j]?.text = ""
        cell[i][j]?.isClickable = false

        // changing values of x and y after swap
        this.x = i
        this.y = j
    }

    private fun checkForWin(): Boolean {
        //The first column of the each row must have a value
        if (cell[0][0]?.text.toString().isNotEmpty() &&
            cell[1][0]?.text.toString().isNotEmpty() &&
            cell[2][0]?.text.toString().isNotEmpty() &&
            cell[3][0]?.text.toString().isNotEmpty()
        ) {
            //Checking the each row's first column values and the last cell should be checked for the emptiness
            if (cell[0][0]?.text.toString().toInt() == 1
                && cell[1][0]?.text.toString().toInt() == 5
                && cell[2][0]?.text.toString().toInt() == 9
                && cell[3][0]?.text.toString().toInt() == 13
                && cell[3][3]?.text?.isEmpty()!!
            ) {
                /*If the first column of the each row is in the accurate position and if the last is empty,
                     then start checking the entire cell */
                var counter = 1
                for (i in 0 until binding.gameBoard.childCount - 1) {
                    val button = binding.gameBoard.getChildAt(i) as AppCompatButton
                    if (button.text.toString().isEmpty()) break
                    if (counter == (button.text.toString().toInt()))
                        counter++
                }
                //If the counter is 16 then the values are in their accurate positions
                return counter == 16
            }
        }
        return false
    }

    private fun showGameOverAnimationAndDialog() {
        // save record
        saveRecord()
        // stop all actions such as clicking buttons and timer
        stopAllActions()
        // win sound
        playWinSound()
        var counterCells = 0
        Thread {
            while (counterCells < binding.gameBoard.childCount - 1) {
                try {
                    Thread.sleep(160)
                    val view = binding.gameBoard.getChildAt(counterCells) as AppCompatButton
                    view.background = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.shape_button_win
                    )
                    view.setTextColor(Color.WHITE)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                counterCells++
            }
            activity?.runOnUiThread {
                popUpGameOverDialog()
            }
        }.start()
    }

    private fun saveRecord() {
        val oldRecord =
            MySharedPreferences.getString(resources.getString(R.string.shp_records), null)
        if (oldRecord == null) {
            MySharedPreferences.putString(
                resources.getString(R.string.shp_records),
                counter.toString()
            )
        } else {
            val newResult = "$oldRecord#$counter"
            MySharedPreferences.putString(
                resources.getString(R.string.shp_records),
                newResult
            )
        }
    }

    private fun playWinSound() {
        if (MySharedPreferences.getBoolean(
                resources.getString(R.string.shp_sound),
                false
            )
        ) soundPool?.play(winPlayer, 1f, 1f, 1, 0, 1f)
    }

    private fun popUpGameOverDialog() {
        val binding = DialogWinnerBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        binding.tvMovesAndTimer.text =
            resources.getString(R.string.tv_win_dialog, counter.toString())
        binding.start.onClick {
            dialog.dismiss()
            resumeAllActions()
        }
        binding.home.onClick {
            dialog.dismiss()
            findNavController().popBackStack()
        }
    }

    private fun stopAllActions() {
        if (MySharedPreferences.getBoolean(
                resources.getString(R.string.shp_music),
                false
            )
        ) {
            mediaPlayer?.pause()
        }
        with(binding) {
            chronometer.stop()
            buttonRestart.isClickable = false
            buttonPause.isClickable = false
            for (i in 0 until binding.gameBoard.childCount) {
                binding.gameBoard.getChildAt(i).isClickable = false
            }
        }
    }

    private fun resumeAllActions() {
        if (MySharedPreferences.getBoolean(
                resources.getString(R.string.shp_music),
                false
            )
        ) {
            mediaPlayer?.seekTo(0)
            mediaPlayer?.start()
        }
        counter = 0
        with(binding) {
            moves.text = counter.toString()
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            buttonRestart.isClickable = true
            buttonPause.isClickable = true
            for (i in 0 until binding.gameBoard.childCount) {
                binding.gameBoard.getChildAt(i).isClickable = true
                binding.gameBoard.getChildAt(i).visibility = visible()
            }
        }
        startGame()
    }

    private fun restartGame() {
        isGameRestarted.value = false
        isGameRestarted.observe(requireActivity()) { isGameRestartedObserver(it) }
    }

    private fun isGameRestartedObserver(isActive: Boolean) {
        if (isActive) {
            counter = 0
            with(binding) {
                tvPlay.visibility = invisible()
                tvPlay.clearAnimation()
                buttonRestart.visibility = visible()
                buttonPause.visibility = visible()
                containerTimerMoves.visibility = visible()
                binding.moves.text = counter.toString()
                binding.chronometer.base = SystemClock.elapsedRealtime()
                binding.chronometer.start()
                gameBoard.background = AppCompatResources.getDrawable(
                    requireContext(), R.drawable.shape_game_board_parent
                )
                for (i in 0..binding.gameBoard.childCount) {
                    val currentButton = binding.gameBoard.getChildAt(i) as AppCompatButton?
                    currentButton?.visibility = visible()
                }
            }
            startGame()
        } else {
            with(binding) {
                tvPlay.visibility = visible()
                cell[x][y]?.visibility = visible()
                tvPlay.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_scale))
                buttonRestart.visibility = invisible()
                buttonPause.visibility = invisible()
                containerTimerMoves.visibility = invisible()
                gameBoard.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.shape_game_board_disable
                )
            }
            for (i in 0..binding.gameBoard.childCount) {
                val currentButton = binding.gameBoard.getChildAt(i) as AppCompatButton?
                currentButton?.background =
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.shape_button_disable
                    )
                currentButton?.text = ""
            }

        }
    }

    private fun pauseGame() {
        isGamePaused.value = true
        isGamePaused.observe(requireActivity()) { isGamePausedObserver(it) }
    }

    private fun isGamePausedObserver(isPaused: Boolean) {
        if (isPaused) {
            with(binding) {
                buttonRestart.isClickable = false
                timeWhenPaused = chronometer.base - SystemClock.elapsedRealtime()
                chronometer.stop()
                buttonPause.text = resources.getText(R.string.play)
                buttonResume.visibility = visible()
                buttonResume.startAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.anim_scale
                    )
                )
                buttonPause.onClick {
                    buttonPause.text = resources.getText(R.string.play)
                    isGamePaused.value = false
                }
            }
        } else {
            with(binding) {
                buttonRestart.isClickable = true
                chronometer.base = timeWhenPaused + SystemClock.elapsedRealtime()
                chronometer.start()
                buttonPause.text = resources.getText(R.string.pause)
                buttonResume.visibility = invisible()
                buttonResume.clearAnimation()
                buttonPause.onClick {
                    buttonPause.text = resources.getText(R.string.pause)
                    isGamePaused.value = true
                }
            }
        }
    }

    private fun generateNumbers() {
        numbers.clear()
        for (i in 0..15) numbers.add(i + 1)
    }

    private fun initSolvableNumbers() {
        do {
            shuffle()
        } while (!isSolvable(numbers))
    }

    private fun shuffle() = numbers.shuffle()

    private fun isSolvable(list: List<Int>): Boolean {
        var counter = 0
        for (i in list.indices) {
            if (list[i] == 16) {
                counter += i / 4 + 1
                continue
            }
            for (j in i + 1 until list.size) {
                if (list[i] > list[j]) {
                    counter++
                }
            }
        }
        return counter % 2 == 0
    }

    private fun makeMoveButtonAnimation(x: Int, y: Int) {
        YoYo
            .with(Techniques.BounceIn)
            .duration(400)
            .playOn(cell[x][y])
    }

    private fun createSoundPool() {
        when {
            MySharedPreferences.getBoolean(
                resources.getString(R.string.shp_sound),
                false
            ) -> {
                val audioAttributes =
                    AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
                soundPool =
                    SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioAttributes).build()
                soundPlayer = soundPool!!.load(requireContext(), R.raw.sound, 1)
                winPlayer = soundPool!!.load(requireContext(), R.raw.win, 1)
            }
        }
    }

    private fun playSound() {
        if (MySharedPreferences.getBoolean(
                resources.getString(R.string.shp_sound),
                false
            )
        ) soundPool?.play(soundPlayer, 1f, 1f, 1, 0, 1f)
    }

    private fun createMediaPlayer() {
        when {
            MySharedPreferences.getBoolean(
                resources.getString(R.string.shp_music),
                true
            ) -> {
                mediaPlayer = MediaPlayer.create(requireContext(), R.raw.music)
                mediaPlayer?.isLooping = true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timeWhenPaused = binding.chronometer.base - SystemClock.elapsedRealtime()
        binding.chronometer.stop()
        when {
            MySharedPreferences.getBoolean(
                resources.getString(R.string.shp_music),
                false
            ) -> mediaPlayer?.pause()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.chronometer.base = SystemClock.elapsedRealtime() + timeWhenPaused
        binding.chronometer.start()
        if (MySharedPreferences.getBoolean(
                resources.getString(R.string.shp_music),
                false
            )
        ) mediaPlayer?.start()
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (onBackPressedTime + 2000 > System.currentTimeMillis()) {
//                        saveGame()
                        findNavController().popBackStack()
                        return
                    } else snackMessage(getString(R.string.snackbar_exit_text))
                    onBackPressedTime = System.currentTimeMillis()
                }
            })
    }

    override fun onDestroyView() {
//        saveGame()
        super.onDestroyView()
        _binding = null
        soundPool = null
        mediaPlayer = null
    }

    /*private fun startFakeGame() {
       binding.chronometer.start()
       numbers.clear()
       for (i in 1..14) {
           numbers.add(i)
       }
       numbers.add(16)
       numbers.add(15)
       loadViews()
       loadDataToViews()
   }*/

    /*private fun saveGame() {
        val memory: MutableSet<String> = mutableSetOf()
        memory.add(counter.toString())
        memory.add((binding.chronometer.base - SystemClock.elapsedRealtime()).toString())
        for (i in 0 until binding.gameBoard.childCount) {
            val view = binding.gameBoard.getChildAt(i) as AppCompatButton
            memory.add(view.text.toString())
        }
        MySharedPreferences.putString(getString(R.string.shp_continue_game), memory.toString())
    }*/

    /*private fun loadMemoryValues() {
        var memory =
            MySharedPreferences.getString(resources.getString(R.string.shp_continue_game), null)
                .toString()
        memory = memory.substring(1)
        memory = memory.substring(0, memory.length - 1)
//        Toast.makeText(requireContext(), memory.toString(), Toast.LENGTH_SHORT).show()
        memoryValues = memory.split(", ")
    }*/

    /*private fun startMemoryGame() {
        binding.moves.text = memoryValues[0]
        binding.chronometer.base =
            SystemClock.elapsedRealtime() + memoryValues[1].toString().toLong()
        for (i in 0..16) {
            val view = binding.gameBoard.getChildAt(i)
            if (i != 16) {

            }
        }
    }*/

    /*private fun startGame() {
        when {
            MySharedPreferences.getString(
                resources.getString(R.string.shp_continue_game),
                null
            ) != null -> {
                loadViews()
                loadMemoryValues()
            }
            else -> {
                binding.chronometer.start()
                initSolvableNumbers()
                loadViews()
                loadDataToViews()
            }
        }
    }*/

    /*private fun loadMemoryDataToViews() {
        for (i in 2..memoryValues.size) {
            if (memoryValues[i] == "16") {
                x = i / 4
                y = i % 4
                cell[x][y]?.visibility = invisible()
                cell[x][y]?.text = resources.getText(R.string.empty_button_text)
                cell[x][y]?.isClickable = false
            } else cell[i / 4][i % 4]!!.text = numbers[i].toString()
        }
    }*/
}