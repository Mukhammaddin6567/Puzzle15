// Generated by view binder compiler. Do not edit!
package uz.gita.puzzle15MBF.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import uz.gita.puzzle15MBF.R;

public final class FragmentGameBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton button1;

  @NonNull
  public final AppCompatButton button10;

  @NonNull
  public final AppCompatButton button11;

  @NonNull
  public final AppCompatButton button12;

  @NonNull
  public final AppCompatButton button13;

  @NonNull
  public final AppCompatButton button14;

  @NonNull
  public final AppCompatButton button15;

  @NonNull
  public final AppCompatButton button16;

  @NonNull
  public final AppCompatButton button2;

  @NonNull
  public final AppCompatButton button3;

  @NonNull
  public final AppCompatButton button4;

  @NonNull
  public final AppCompatButton button5;

  @NonNull
  public final AppCompatButton button6;

  @NonNull
  public final AppCompatButton button7;

  @NonNull
  public final AppCompatButton button8;

  @NonNull
  public final AppCompatButton button9;

  @NonNull
  public final MaterialButton buttonPause;

  @NonNull
  public final MaterialButton buttonRestart;

  @NonNull
  public final AppCompatImageButton buttonResume;

  @NonNull
  public final Chronometer chronometer;

  @NonNull
  public final LinearLayoutCompat containerMoves;

  @NonNull
  public final LinearLayoutCompat containerTimer;

  @NonNull
  public final LinearLayoutCompat containerTimerMoves;

  @NonNull
  public final ConstraintLayout gameBoard;

  @NonNull
  public final AppCompatTextView moves;

  @NonNull
  public final AppCompatTextView tvPlay;

  private FragmentGameBinding(@NonNull ConstraintLayout rootView, @NonNull AppCompatButton button1,
      @NonNull AppCompatButton button10, @NonNull AppCompatButton button11,
      @NonNull AppCompatButton button12, @NonNull AppCompatButton button13,
      @NonNull AppCompatButton button14, @NonNull AppCompatButton button15,
      @NonNull AppCompatButton button16, @NonNull AppCompatButton button2,
      @NonNull AppCompatButton button3, @NonNull AppCompatButton button4,
      @NonNull AppCompatButton button5, @NonNull AppCompatButton button6,
      @NonNull AppCompatButton button7, @NonNull AppCompatButton button8,
      @NonNull AppCompatButton button9, @NonNull MaterialButton buttonPause,
      @NonNull MaterialButton buttonRestart, @NonNull AppCompatImageButton buttonResume,
      @NonNull Chronometer chronometer, @NonNull LinearLayoutCompat containerMoves,
      @NonNull LinearLayoutCompat containerTimer, @NonNull LinearLayoutCompat containerTimerMoves,
      @NonNull ConstraintLayout gameBoard, @NonNull AppCompatTextView moves,
      @NonNull AppCompatTextView tvPlay) {
    this.rootView = rootView;
    this.button1 = button1;
    this.button10 = button10;
    this.button11 = button11;
    this.button12 = button12;
    this.button13 = button13;
    this.button14 = button14;
    this.button15 = button15;
    this.button16 = button16;
    this.button2 = button2;
    this.button3 = button3;
    this.button4 = button4;
    this.button5 = button5;
    this.button6 = button6;
    this.button7 = button7;
    this.button8 = button8;
    this.button9 = button9;
    this.buttonPause = buttonPause;
    this.buttonRestart = buttonRestart;
    this.buttonResume = buttonResume;
    this.chronometer = chronometer;
    this.containerMoves = containerMoves;
    this.containerTimer = containerTimer;
    this.containerTimerMoves = containerTimerMoves;
    this.gameBoard = gameBoard;
    this.moves = moves;
    this.tvPlay = tvPlay;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentGameBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentGameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_game, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentGameBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button1;
      AppCompatButton button1 = ViewBindings.findChildViewById(rootView, id);
      if (button1 == null) {
        break missingId;
      }

      id = R.id.button10;
      AppCompatButton button10 = ViewBindings.findChildViewById(rootView, id);
      if (button10 == null) {
        break missingId;
      }

      id = R.id.button11;
      AppCompatButton button11 = ViewBindings.findChildViewById(rootView, id);
      if (button11 == null) {
        break missingId;
      }

      id = R.id.button12;
      AppCompatButton button12 = ViewBindings.findChildViewById(rootView, id);
      if (button12 == null) {
        break missingId;
      }

      id = R.id.button13;
      AppCompatButton button13 = ViewBindings.findChildViewById(rootView, id);
      if (button13 == null) {
        break missingId;
      }

      id = R.id.button14;
      AppCompatButton button14 = ViewBindings.findChildViewById(rootView, id);
      if (button14 == null) {
        break missingId;
      }

      id = R.id.button15;
      AppCompatButton button15 = ViewBindings.findChildViewById(rootView, id);
      if (button15 == null) {
        break missingId;
      }

      id = R.id.button16;
      AppCompatButton button16 = ViewBindings.findChildViewById(rootView, id);
      if (button16 == null) {
        break missingId;
      }

      id = R.id.button2;
      AppCompatButton button2 = ViewBindings.findChildViewById(rootView, id);
      if (button2 == null) {
        break missingId;
      }

      id = R.id.button3;
      AppCompatButton button3 = ViewBindings.findChildViewById(rootView, id);
      if (button3 == null) {
        break missingId;
      }

      id = R.id.button4;
      AppCompatButton button4 = ViewBindings.findChildViewById(rootView, id);
      if (button4 == null) {
        break missingId;
      }

      id = R.id.button5;
      AppCompatButton button5 = ViewBindings.findChildViewById(rootView, id);
      if (button5 == null) {
        break missingId;
      }

      id = R.id.button6;
      AppCompatButton button6 = ViewBindings.findChildViewById(rootView, id);
      if (button6 == null) {
        break missingId;
      }

      id = R.id.button7;
      AppCompatButton button7 = ViewBindings.findChildViewById(rootView, id);
      if (button7 == null) {
        break missingId;
      }

      id = R.id.button8;
      AppCompatButton button8 = ViewBindings.findChildViewById(rootView, id);
      if (button8 == null) {
        break missingId;
      }

      id = R.id.button9;
      AppCompatButton button9 = ViewBindings.findChildViewById(rootView, id);
      if (button9 == null) {
        break missingId;
      }

      id = R.id.button_pause;
      MaterialButton buttonPause = ViewBindings.findChildViewById(rootView, id);
      if (buttonPause == null) {
        break missingId;
      }

      id = R.id.button_restart;
      MaterialButton buttonRestart = ViewBindings.findChildViewById(rootView, id);
      if (buttonRestart == null) {
        break missingId;
      }

      id = R.id.button_resume;
      AppCompatImageButton buttonResume = ViewBindings.findChildViewById(rootView, id);
      if (buttonResume == null) {
        break missingId;
      }

      id = R.id.chronometer;
      Chronometer chronometer = ViewBindings.findChildViewById(rootView, id);
      if (chronometer == null) {
        break missingId;
      }

      id = R.id.container_moves;
      LinearLayoutCompat containerMoves = ViewBindings.findChildViewById(rootView, id);
      if (containerMoves == null) {
        break missingId;
      }

      id = R.id.container_timer;
      LinearLayoutCompat containerTimer = ViewBindings.findChildViewById(rootView, id);
      if (containerTimer == null) {
        break missingId;
      }

      id = R.id.container_timer_moves;
      LinearLayoutCompat containerTimerMoves = ViewBindings.findChildViewById(rootView, id);
      if (containerTimerMoves == null) {
        break missingId;
      }

      id = R.id.game_board;
      ConstraintLayout gameBoard = ViewBindings.findChildViewById(rootView, id);
      if (gameBoard == null) {
        break missingId;
      }

      id = R.id.moves;
      AppCompatTextView moves = ViewBindings.findChildViewById(rootView, id);
      if (moves == null) {
        break missingId;
      }

      id = R.id.tv_play;
      AppCompatTextView tvPlay = ViewBindings.findChildViewById(rootView, id);
      if (tvPlay == null) {
        break missingId;
      }

      return new FragmentGameBinding((ConstraintLayout) rootView, button1, button10, button11,
          button12, button13, button14, button15, button16, button2, button3, button4, button5,
          button6, button7, button8, button9, buttonPause, buttonRestart, buttonResume, chronometer,
          containerMoves, containerTimer, containerTimerMoves, gameBoard, moves, tvPlay);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
