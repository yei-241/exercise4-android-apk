package com.yei241.exercise4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final int[] counts = new int[3];
    private final String[] keys = {"ripe", "medium", "unripe"};
    private final int[] labels = {R.string.ripe, R.string.medium, R.string.unripe};
    private final Button[] buttons = new Button[3];
    private LinearLayout content;
    private TextView total;
    private TextView distribution;
    private SharedPreferences preferences;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getPreferences(MODE_PRIVATE);
        for (int i = 0; i < counts.length; i++) {
            counts[i] = preferences.getInt(keys[i], 0);
        }

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(Color.rgb(245, 248, 250));
        scroll.setOnApplyWindowInsetsListener((view, insets) -> {
            view.setPadding(dp(20) + insets.getSystemWindowInsetLeft(),
                    dp(20) + insets.getSystemWindowInsetTop(),
                    dp(20) + insets.getSystemWindowInsetRight(),
                    dp(20) + insets.getSystemWindowInsetBottom());
            return insets;
        });
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        scroll.addView(content);

        TextView title = text(28);
        title.setText(R.string.app_name);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        add(title, 8);
        TextView subtitle = text(15);
        subtitle.setText(R.string.subtitle);
        add(subtitle, 8);
        total = text(23);
        add(total, 24);

        int[] colors = {Color.rgb(0, 105, 80), Color.rgb(146, 74, 0), Color.rgb(45, 80, 120)};
        for (int i = 0; i < counts.length; i++) {
            final int category = i;
            Button button = new Button(this);
            button.setAllCaps(false);
            button.setTextSize(18);
            button.setTextColor(Color.WHITE);
            button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colors[i]));
            button.setMinHeight(dp(66));
            button.setOnClickListener(view -> {
                counts[category]++;
                saveAndRender();
            });
            buttons[i] = button;
            add(button, 10);
        }
        distribution = text(14);
        add(distribution, 16);
        TextView saved = text(14);
        saved.setText(R.string.saved);
        add(saved, 10);
        Button reset = new Button(this);
        reset.setAllCaps(false);
        reset.setText(R.string.reset);
        reset.setOnClickListener(view -> new AlertDialog.Builder(this)
                .setTitle(R.string.reset_title)
                .setMessage(R.string.reset_message)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(R.string.reset, (dialog, which) -> {
                    for (int i = 0; i < counts.length; i++) counts[i] = 0;
                    saveAndRender();
                }).show());
        add(reset, 16);
        setContentView(scroll);
        scroll.requestApplyInsets();
        render();
    }

    private TextView text(int size) {
        TextView view = new TextView(this);
        view.setTextSize(size);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.rgb(20, 35, 50));
        return view;
    }

    private void add(View view, int topMargin) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = dp(topMargin);
        content.addView(view, params);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }

    private void saveAndRender() {
        SharedPreferences.Editor editor = preferences.edit();
        for (int i = 0; i < counts.length; i++) editor.putInt(keys[i], counts[i]);
        editor.apply();
        render();
    }

    private void render() {
        int sum = counts[0] + counts[1] + counts[2];
        total.setText(getString(R.string.total, sum));
        for (int i = 0; i < counts.length; i++) {
            buttons[i].setText(getString(labels[i], counts[i]));
        }
        double scale = sum == 0 ? 0.0 : 100.0 / sum;
        distribution.setText(getString(R.string.distribution,
                counts[0] * scale, counts[1] * scale, counts[2] * scale));
    }
}
