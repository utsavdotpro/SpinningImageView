package com.isolpro.library.spinningimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class SpinningImageView extends AppCompatImageView {
  private int duration = 800;
  private boolean alwaysSpinning = false;

  public SpinningImageView(Context context) {
    this(context, null);
  }

  public SpinningImageView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SpinningImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    if (alwaysSpinning) spin();
  }

  private void init(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SpinningImageView, 0, 0);

    try {
      setDuration(typedArray.getInt(R.styleable.SpinningImageView_siv_duration, duration));
      setAlwaysSpinning(typedArray.getBoolean(R.styleable.SpinningImageView_siv_alwaysSpinning, alwaysSpinning));
    } finally {
      typedArray.recycle();
    }
  }

  public void stop() {
    clearAnimation();
  }

  public void spin() {
    stop();

    RotateAnimation rotate = new RotateAnimation(
      0, 360,
      Animation.RELATIVE_TO_SELF, 0.5f,
      Animation.RELATIVE_TO_SELF, 0.5f
    );

    rotate.setDuration(duration);
    rotate.setRepeatCount(Animation.INFINITE);

    startAnimation(rotate);
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public boolean isAlwaysSpinning() {
    return alwaysSpinning;
  }

  public void setAlwaysSpinning(boolean alwaysSpinning) {
    this.alwaysSpinning = alwaysSpinning;
  }

  @Override
  public void setVisibility(int visibility) {
    super.setVisibility(visibility);

    // + stop spinning if not visible
    if (visibility != View.VISIBLE) stop();
    else if (alwaysSpinning) spin();
  }
}
