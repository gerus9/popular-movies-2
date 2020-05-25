package com.gerus.android.popularmovies1.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gerus.android.popularmovies1.R;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CardInfo extends LinearLayout implements View.OnClickListener {

	private ConstraintLayout expandableView;
	private RecyclerView recyclerView;
	private CardView cardView;
	private Context mContext;
	private TextView textView;
	private Button arrowBtn;
	String txtValue = "";

	public CardInfo(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	public CardInfo(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		setArrayValues(attrs);
		init();
	}

	public CardInfo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		setArrayValues(attrs);
		init();
	}

	private void setArrayValues(AttributeSet attrs) {
		@SuppressLint("CustomViewStyleable")
		TypedArray voStyle = mContext.obtainStyledAttributes(attrs, R.styleable.CardView, 0, 0);
		try {
			txtValue = voStyle.getString(R.styleable.CardView_cv_text);
		} finally {
			voStyle.recycle();
		}
	}

	@SuppressLint("InflateParams")
	private void init() {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View voView = Objects.requireNonNull(inflater).inflate(R.layout.card_info, this, true);
		textView = voView.findViewById(R.id.name);
		textView.setText(txtValue);
		arrowBtn = voView.findViewById(R.id.arrowBtn);
		arrowBtn.setOnClickListener(this);
		expandableView = findViewById(R.id.expandableView);
		FrameLayout viewStub = findViewById(R.id.viewStub);
		recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.card_detail, null);
		viewStub.addView(recyclerView);
		cardView = findViewById(R.id.cardView);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.arrowBtn) {
			if (expandableView.getVisibility() == View.GONE) {
				TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
				expandableView.setVisibility(View.VISIBLE);
				arrowBtn.setBackgroundResource(R.drawable.vc_arrow_drop_up);
			} else {
				TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
				expandableView.setVisibility(View.GONE);
				arrowBtn.setBackgroundResource(R.drawable.vc_arrow_drop_down);
			}
		}
	}

	public RecyclerView getRecyclerView() {
		return recyclerView;
	}
}
