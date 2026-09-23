package com.josephmarchand.joenglish;

import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int xp = 0;
    private int streak = 1;
    private int dailyGoal = 50;

    private TextView xpText;
    private TextView streakText;
    private TextView goalText;

    private final String[] levels = {
            "A1 — Débutant",
            "A2 — Élémentaire",
            "B1 — Intermédiaire",
            "B2 — Intermédiaire supérieur",
            "C1 — Avancé",
            "C2 — Maîtrise"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showHome();
    }

    private void showHome() {

        LinearLayout root = createRoot();

        TextView title = createTitle("JoEnglish");
        root.addView(title);

        TextView subtitle = createSubtitle(
                "Apprends l'anglais efficacement, du niveau A1 au C2."
        );
        root.addView(subtitle);

        LinearLayout stats = new LinearLayout(this);
        stats.setOrientation(LinearLayout.HORIZONTAL);
        stats.setGravity(Gravity.CENTER);
        stats.setPadding(0, 25, 0, 25);

        xpText = createStat("⭐\n" + xp + " XP");
        streakText = createStat("🔥\n" + streak + " jour");
        goalText = createStat("🎯\n" + dailyGoal + " XP");

        stats.addView(xpText, weightParams());
        stats.addView(streakText, weightParams());
        stats.addView(goalText, weightParams());

        root.addView(stats);

        TextView courseTitle = createSectionTitle("Ton parcours");

        root.addView(courseTitle);

        for (int i = 0; i < levels.length; i++) {
            final int level = i;

            Button levelButton = createButton(levels[i]);

            levelButton.setOnClickListener(v -> {
                showLevel(level);
            });

            root.addView(levelButton);
        }

        Button lessonButton = createButton("📚 Continuer la leçon");

        lessonButton.setOnClickListener(v -> {
            showLesson();
        });

        root.addView(lessonButton);

        Button revisionButton = createButton("🔄 Révision");

        revisionButton.setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "La section Révision arrive dans le moteur de cours.",
                    Toast.LENGTH_SHORT
            ).show();
        });

        root.addView(revisionButton);

        Button settingsButton = createButton("⚙️ Paramètres");

        settingsButton.setOnClickListener(v -> {
            showSettings();
        });

        root.addView(settingsButton);

        setContentView(wrapScroll(root));
    }

    private void showLevel(int level) {

        LinearLayout root = createRoot();

        Button back = createButton("← Retour");
        back.setOnClickListener(v -> showHome());
        root.addView(back);

        TextView title = createTitle(levels[level]);
        root.addView(title);

        TextView description = createSubtitle(
                "600 positions de leçons prévues pour ce niveau."
        );
        root.addView(description);

        for (int i = 1; i <= 10; i++) {

            final int lessonNumber = i;

            Button lesson = createButton(
                    "Leçon " + lessonNumber + "  •  +10 XP"
            );

            lesson.setOnClickListener(v -> {
                showLesson();
            });

            root.addView(lesson);
        }

        TextView more = createSubtitle(
                "Les autres positions seront chargées avec les packs de contenu."
        );

        root.addView(more);

        setContentView(wrapScroll(root));
    }

    private void showLesson() {

        LinearLayout root = createRoot();

        Button back = createButton("← Retour");
        back.setOnClickListener(v -> showHome());
        root.addView(back);

        TextView title = createTitle("Leçon A1 — Salutations");
        root.addView(title);

        TextView question = createSubtitle(
                "Choisis la bonne traduction :\n\nHello"
        );

        question.setTextSize(22);
        question.setTextColor(Color.DKGRAY);
        question.setPadding(0, 35, 0, 35);

        root.addView(question);

        String[] answers = {
                "Bonjour",
                "Merci",
                "Au revoir",
                "Bonne nuit"
        };

        for (String answer : answers) {

            Button answerButton = createButton(answer);

            answerButton.setOnClickListener(v -> {

                String selected = ((Button) v).getText().toString();

                if (selected.equals("Bonjour")) {

                    xp += 10;

                    Toast.makeText(
                            this,
                            "✅ Bonne réponse ! +10 XP",
                            Toast.LENGTH_SHORT
                    ).show();

                    showHome();

                } else {

                    Toast.makeText(
                            this,
                            "❌ Pas encore. « Hello » signifie « Bonjour ».",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

            root.addView(answerButton);
        }

        setContentView(wrapScroll(root));
    }

    private void showSettings() {

        LinearLayout root = createRoot();

        Button back = createButton("← Retour");
        back.setOnClickListener(v -> showHome());
        root.addView(back);

        TextView title = createTitle("Paramètres");
        root.addView(title);

        TextView author = createSubtitle(
                "JoEnglish\n\nCréé par Joseph Marchand"
        );

        root.addView(author);

        Button reset = createButton("🔄 Réinitialiser la progression");

        reset.setOnClickListener(v -> {

            xp = 0;
            streak = 1;

            Toast.makeText(
                    this,
                    "Progression réinitialisée.",
                    Toast.LENGTH_SHORT
            ).show();

            showHome();
        });

        root.addView(reset);

        Button language = createButton(
                "🌍 Langue source → langue cible"
        );

        language.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "La sélection des langues sera ajoutée dans l'onboarding.",
                    Toast.LENGTH_LONG
            ).show();

        });

        root.addView(language);

        setContentView(wrapScroll(root));
    }

    private LinearLayout createRoot() {

        LinearLayout root = new LinearLayout(this);

        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(30, 35, 30, 35);

        root.setBackgroundColor(Color.WHITE);

        return root;
    }

    private ScrollView wrapScroll(View view) {

        ScrollView scroll = new ScrollView(this);

        scroll.setFillViewport(true);
        scroll.addView(view);

        return scroll;
    }

    private TextView createTitle(String text) {

        TextView title = new TextView(this);

        title.setText(text);
        title.setTextSize(30);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setTextColor(Color.rgb(79, 70, 229));
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 10, 0, 15);

        return title;
    }

    private TextView createSubtitle(String text) {

        TextView subtitle = new TextView(this);

        subtitle.setText(text);
        subtitle.setTextSize(17);
        subtitle.setTextColor(Color.DKGRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(5, 5, 5, 15);

        return subtitle;
    }

    private TextView createSectionTitle(String text) {

        TextView section = new TextView(this);

        section.setText(text);
        section.setTextSize(23);
        section.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        section.setTextColor(Color.DKGRAY);
        section.setPadding(0, 20, 0, 15);

        return section;
    }

    private TextView createStat(String text) {

        TextView stat = new TextView(this);

        stat.setText(text);
        stat.setTextSize(16);
        stat.setTextColor(Color.DKGRAY);
        stat.setGravity(Gravity.CENTER);
        stat.setPadding(8, 15, 8, 15);

        return stat;
    }

    private Button createButton(String text) {

        Button button = new Button(this);

        button.setText(text);
        button.setTextSize(16);
        button.setAllCaps(false);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, 8, 0, 8);

        button.setLayoutParams(params);

        return button;
    }

    private LinearLayout.LayoutParams weightParams() {

        return new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
    }
          }
