package com.josephmarchand.joenglish;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private String sourceLanguage = "";
    private String targetLanguage = "";
    private String currentLevel = "";
    private String learningReason = "";

    private int xp = 0;
    private int streak = 0;
    private int dailyGoal = 20;

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences(
                "JoEnglishData",
                Context.MODE_PRIVATE
        );

        loadData();

        if (prefs.getBoolean("onboarding_done", false)) {
            showHome();
        } else {
            showWelcome();
        }
    }

    private void loadData() {

        sourceLanguage = prefs.getString(
                "source_language",
                ""
        );

        targetLanguage = prefs.getString(
                "target_language",
                ""
        );

        currentLevel = prefs.getString(
                "current_level",
                ""
        );

        learningReason = prefs.getString(
                "learning_reason",
                ""
        );

        xp = prefs.getInt("xp", 0);

        streak = prefs.getInt("streak", 0);

        dailyGoal = prefs.getInt(
                "daily_goal",
                20
        );
    }

    private void saveData() {

        prefs.edit()
                .putString(
                        "source_language",
                        sourceLanguage
                )
                .putString(
                        "target_language",
                        targetLanguage
                )
                .putString(
                        "current_level",
                        currentLevel
                )
                .putString(
                        "learning_reason",
                        learningReason
                )
                .putInt("xp", xp)
                .putInt("streak", streak)
                .putInt("daily_goal", dailyGoal)
                .apply();
    }

    private void showWelcome() {

        root = createRoot();

        addSpace(40);

        TextView logo = createTitle("JoEnglish");

        logo.setTextSize(38);

        root.addView(logo);

        TextView slogan = createSubtitle(
                "Apprends une langue.\n" +
                "Construis ton avenir."
        );

        slogan.setTextSize(23);

        root.addView(slogan);

        addSpace(25);

        root.addView(createSubtitle(
                "Cours complets • Révisions • Défis • " +
                "Conversations • Progression continue"
        ));

        addSpace(30);

        Button start = createButton(
                "🚀 Commencer"
        );

        start.setOnClickListener(
                v -> showLanguageSelection()
        );

        root.addView(start);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showLanguageSelection() {

        root = createRoot();

        addBackButton(
                () -> showWelcome()
        );

        root.addView(
                createTitle("🌍 Tes langues")
        );

        root.addView(
                createSubtitle(
                        "Quelle langue connais-tu actuellement ?"
                )
        );

        String[] languages = {
                "Français",
                "English",
                "Español",
                "Português",
                "Deutsch"
        };

        for (String language : languages) {

            Button button = createButton(language);

            button.setOnClickListener(v -> {

                sourceLanguage = language;

                showTargetLanguageSelection();
            });

            root.addView(button);
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showTargetLanguageSelection() {

        root = createRoot();

        addBackButton(
                () -> showLanguageSelection()
        );

        root.addView(
                createTitle("🎯 Langue cible")
        );

        root.addView(
                createSubtitle(
                        "Quelle langue veux-tu apprendre ?"
                )
        );

        String[] languages = {
                "English",
                "Français",
                "Español",
                "Português",
                "Deutsch"
        };

        for (String language : languages) {

            if (language.equals(sourceLanguage)) {
                continue;
            }

            Button button = createButton(language);

            button.setOnClickListener(v -> {

                targetLanguage = language;

                showLevelSelection();
            });

            root.addView(button);
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showLevelSelection() {

        root = createRoot();

        addBackButton(
                () -> showTargetLanguageSelection()
        );

        root.addView(
                createTitle("📊 Ton niveau")
        );

        root.addView(
                createSubtitle(
                        "Choisis la description qui te correspond."
                )
        );

        Button beginner = createButton(
                "🌱 Débutant total\nJe commence de zéro"
        );

        beginner.setOnClickListener(v -> {

            currentLevel = "A1";

            showReasonSelection();
        });

        root.addView(beginner);

        Button basic = createButton(
                "📖 Quelques connaissances\nJe connais déjà des mots"
        );

        basic.setOnClickListener(v -> {

            currentLevel = "A2";

            showReasonSelection();
        });

        root.addView(basic);

        Button intermediate = createButton(
                "🚀 Intermédiaire\nJe peux déjà communiquer"
        );

        intermediate.setOnClickListener(v -> {

            currentLevel = "B1";

            showReasonSelection();
        });

        root.addView(intermediate);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showReasonSelection() {

        root = createRoot();

        addBackButton(
                () -> showLevelSelection()
        );

        root.addView(
                createTitle("🎯 Ton objectif")
        );

        root.addView(
                createSubtitle(
                        "Pourquoi veux-tu apprendre cette langue ?"
                )
        );

        String[] reasons = {
                "✈️ Voyage",
                "💼 Travail",
                "🎓 Études",
                "🗣️ Conversation",
                "🌍 Culture",
                "🏆 Devenir très fort"
        };

        for (String reason : reasons) {

            Button button = createButton(reason);

            button.setOnClickListener(v -> {

                learningReason = reason;

                saveData();

                showDailyGoalSelection();
            });

            root.addView(button);
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showDailyGoalSelection() {

        root = createRoot();

        addBackButton(
                () -> showReasonSelection()
        );

        root.addView(
                createTitle("⏱️ Objectif quotidien")
        );

        root.addView(
                createSubtitle(
                        "Combien veux-tu pratiquer chaque jour ?"
                )
        );

        String[] goals = {
                "🌱 10 XP — 5 minutes",
                "⭐ 20 XP — 10 minutes",
                "🔥 30 XP — 15 minutes",
                "🚀 50 XP — 20 minutes",
                "🏆 100 XP — 30 minutes"
        };

        int[] values = {
                10,
                20,
                30,
                50,
                100
        };

        for (int i = 0; i < goals.length; i++) {

            final int goal = values[i];

            Button button = createButton(
                    goals[i]
            );

            button.setOnClickListener(v -> {

                dailyGoal = goal;

                saveData();

                showPlacementIntro();
            });

            root.addView(button);
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showPlacementIntro() {

        root = createRoot();

        root.addView(
                createTitle("🧠 Test de placement")
        );

        root.addView(
                createSubtitle(
                        "Avant de commencer ton parcours, " +
                        "JoEnglish va vérifier ton niveau."
                )
        );

        addSpace(20);

        root.addView(
                createSubtitle(
                        "📝 5 questions\n\n" +
                        "📈 Difficulté progressive\n\n" +
                        "✅ Correction pédagogique\n\n" +
                        "📊 Niveau proposé à la fin"
                )
        );

        addSpace(25);

        Button start = createButton(
                "🚀 Commencer le test"
        );

        start.setOnClickListener(
                v -> showPlacementQuestion(0, 0)
        );

        root.addView(start);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showPlacementQuestion(
            int question,
            int score
    ) {

        root = createRoot();

        root.addView(
                createTitle(
                        "Question " +
                        (question + 1) +
                        " / 5"
                )
        );

        String questionText;

        String[] answers;

        int correct;

        if (question == 0) {

            questionText =
                    "What is your name?";

            answers = new String[]{
                    "Comment tu t'appelles ?",
                    "Où habites-tu ?",
                    "Quel âge as-tu ?",
                    "Bonne nuit"
            };

            correct = 0;

        } else if (question == 1) {

            questionText =
                    "I ___ from Congo.";

            answers = new String[]{
                    "am",
                    "is",
                    "are",
                    "be"
            };

            correct = 0;

        } else if (question == 2) {

            questionText =
                    "She ___ to school every day.";

            answers = new String[]{
                    "go",
                    "goes",
                    "going",
                    "gone"
            };

            correct = 1;

        } else if (question == 3) {

            questionText =
                    "What did you do yesterday?";

            answers = new String[]{
                    "I go to school.",
                    "I am going to school.",
                    "I went to school.",
                    "I goes to school."
            };

            correct = 2;

        } else {

            questionText =
                    "If I had more time, I ___ " +
                    "English every day.";

            answers = new String[]{
                    "study",
                    "will study",
                    "would study",
                    "studied"
            };

            correct = 2;
        }

        TextView questionView =
                createSubtitle(questionText);

        questionView.setTextSize(22);

        questionView.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        root.addView(questionView);

        addSpace(15);

        final int correctAnswer = correct;

        final int oldScore = score;

        for (int i = 0;
             i < answers.length;
             i++) {

            final int selected = i;

            Button answer =
                    createButton(answers[i]);

            answer.setOnClickListener(v -> {

                int newScore = oldScore;

                if (selected == correctAnswer) {
                    newScore++;
                }

                if (question < 4) {

                    showPlacementQuestion(
                            question + 1,
                            newScore
                    );

                } else {

                    finishPlacementTest(
                            newScore
                    );
                }
            });

            root.addView(answer);
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void finishPlacementTest(
            int score
    ) {

        if (score <= 1) {

            currentLevel = "A1";

        } else if (score == 2) {

            currentLevel = "A2";

        } else if (score == 3) {

            currentLevel = "B1";

        } else if (score == 4) {

            currentLevel = "B2";

        } else {

            currentLevel = "C1";
        }

        prefs.edit()
                .putBoolean(
                        "onboarding_done",
                        true
                )
                .putString(
                        "current_level",
                        currentLevel
                )
                .apply();

        loadData();

        showPlacementResult(score);
    }
        private void showPlacementResult(int score) {

        root = createRoot();

        root.addView(
                createTitle("🎉 Test terminé")
        );

        TextView result = createSubtitle(
                "Score : " + score + " / 5\n\n" +
                "Niveau de départ : " + currentLevel
        );

        result.setTextSize(21);

        root.addView(result);

        addSpace(20);

        root.addView(
                createSubtitle(
                        "Ton parcours sera adapté à ton niveau. " +
                        "Tu pourras progresser progressivement vers " +
                        "la maîtrise."
                )
        );

        Button start = createButton(
                "🚀 Commencer mon parcours"
        );

        start.setOnClickListener(
                v -> showHome()
        );

        root.addView(start);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showHome() {

        root = createRoot();

        root.addView(
                createTitle("JoEnglish")
        );

        root.addView(
                createSubtitle(
                        "Bonjour 👋\n" +
                        "Prêt pour ta session d'aujourd'hui ?"
                )
        );

        addSpace(10);

        LinearLayout stats =
                new LinearLayout(this);

        stats.setOrientation(
                LinearLayout.HORIZONTAL
        );

        stats.setGravity(
                Gravity.CENTER
        );

        stats.addView(
                createStat(
                        "⭐\n" + xp + " XP"
                )
        );

        stats.addView(
                createStat(
                        "🔥\n" + streak + " jours"
                )
        );

        stats.addView(
                createStat(
                        "🎯\n" + dailyGoal + " XP"
                )
        );

        root.addView(stats);

        addSpace(15);

        root.addView(
                createSectionTitle(
                        "📚 Continuer"
                )
        );

        Button continueButton =
                createButton(
                        "▶️ Continuer la leçon"
                );

        continueButton.setOnClickListener(
                v -> showLesson()
        );

        root.addView(continueButton);

        root.addView(
                createSectionTitle(
                        "🗺️ Apprentissage"
                )
        );

        Button path =
                createButton(
                        "🗺️ Parcours complet"
                );

        path.setOnClickListener(
                v -> showCoursePath()
        );

        root.addView(path);

        Button revision =
                createButton(
                        "🔄 Révision intelligente"
                );

        revision.setOnClickListener(
                v -> showRevision()
        );

        root.addView(revision);

        Button expressions =
                createButton(
                        "💬 Expressions utiles"
                );

        expressions.setOnClickListener(
                v -> showExpressions()
        );

        root.addView(expressions);

        root.addView(
                createSectionTitle(
                        "🏆 Activités quotidiennes"
                )
        );

        Button challenges =
                createButton(
                        "🏆 Défis du jour"
                );

        challenges.setOnClickListener(
                v -> showChallenges()
        );

        root.addView(challenges);

        Button daily =
                createButton(
                        "📅 Ma routine"
                );

        daily.setOnClickListener(
                v -> showDailyRoutine()
        );

        root.addView(daily);

        root.addView(
                createSectionTitle(
                        "👤 Compte"
                )
        );

        Button profile =
                createButton(
                        "👤 Mon profil"
                );

        profile.setOnClickListener(
                v -> showProfile()
        );

        root.addView(profile);

        Button settings =
                createButton(
                        "⚙️ Paramètres"
                );

        settings.setOnClickListener(
                v -> showSettings()
        );

        root.addView(settings);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showCoursePath() {

        root = createRoot();

        addBackButton(
                () -> showHome()
        );

        root.addView(
                createTitle(
                        "🗺️ Parcours JoEnglish"
                )
        );

        root.addView(
                createSubtitle(
                        "Un parcours très long, organisé " +
                        "du débutant jusqu'à la maîtrise."
                )
        );

        String[] levels = {
                "A1 — Débutant",
                "A2 — Élémentaire",
                "B1 — Intermédiaire",
                "B2 — Intermédiaire supérieur",
                "C1 — Avancé",
                "C2 — Maîtrise",
                "🏆 Maîtrise avancée",
                "♾️ Mode Expert"
        };

        for (String level : levels) {

            Button button =
                    createButton(level);

            button.setOnClickListener(
                    v -> showUnits(level)
            );

            root.addView(button);
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showUnits(String level) {

        root = createRoot();

        addBackButton(
                () -> showCoursePath()
        );

        root.addView(
                createTitle(level)
        );

        root.addView(
                createSubtitle(
                        "Choisis une unité."
                )
        );

        for (int i = 1; i <= 30; i++) {

            final int unit = i;

            Button button =
                    createButton(
                            "Unité " + unit +
                            "  •  30 leçons"
                    );

            button.setOnClickListener(
                    v -> showLessons(
                            level,
                            unit
                    )
            );

            root.addView(button);
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showLessons(
            String level,
            int unit
    ) {

        root = createRoot();

        addBackButton(
                () -> showUnits(level)
        );

        root.addView(
                createTitle(
                        "Unité " + unit
                )
        );

        root.addView(
                createSubtitle(
                        level +
                        "\n30 positions d'apprentissage"
                )
        );

        for (int i = 1; i <= 30; i++) {

            final int lesson = i;

            Button button =
                    createButton(
                            "Leçon " +
                            lesson +
                            "  •  +10 XP"
                    );

            button.setOnClickListener(
                    v -> showLesson()
            );

            root.addView(button);
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showLesson() {

        root = createRoot();

        addBackButton(
                () -> showHome()
        );

        root.addView(
                createTitle(
                        "📖 Leçon du jour"
                )
        );

        root.addView(
                createSubtitle(
                        "Choisis la bonne traduction."
                )
        );

        TextView word =
                createSubtitle("HELLO");

        word.setTextSize(34);

        word.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        root.addView(word);

        String[] answers = {
                "Bonjour",
                "Merci",
                "Au revoir",
                "Bonne nuit"
        };

        for (String answer : answers) {

            Button button =
                    createButton(answer);

            button.setOnClickListener(v -> {

                if (answer.equals(
                        "Bonjour"
                )) {

                    xp += 10;

                    saveData();

                    showCorrectAnswer();

                } else {

                    showWrongAnswer();
                }
            });

            root.addView(button);
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showCorrectAnswer() {

        root = createRoot();

        root.addView(
                createTitle(
                        "🎉 Excellent !"
                )
        );

        root.addView(
                createSubtitle(
                        "Bonne réponse !\n\n" +
                        "HELLO = BONJOUR\n\n" +
                        "⭐ +10 XP\n\n" +
                        "Continue pour progresser."
                )
        );

        Button next =
                createButton(
                        "➡️ Continuer"
                );

        next.setOnClickListener(
                v -> showHome()
        );

        root.addView(next);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showWrongAnswer() {

        root = createRoot();

        root.addView(
                createTitle(
                        "💡 Apprenons de l'erreur"
                )
        );

        root.addView(
                createSubtitle(
                        "La bonne réponse est :\n\n" +
                        "Bonjour\n\n" +
                        "HELLO signifie « Bonjour ».\n\n" +
                        "Lis la correction puis réessaie."
                )
        );

        Button retry =
                createButton(
                        "🔄 Réessayer"
                );

        retry.setOnClickListener(
                v -> showLesson()
        );

        root.addView(retry);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showRevision() {

        root = createRoot();

        addBackButton(
                () -> showHome()
        );

        root.addView(
                createTitle(
                        "🔄 Révision intelligente"
                )
        );

        root.addView(
                createSubtitle(
                        "JoEnglish pourra utiliser tes erreurs " +
                        "et les mots difficiles pour construire " +
                        "tes futures révisions."
                )
        );

        addSpace(20);

        Button start =
                createButton(
                        "▶️ Commencer une révision"
                );

        start.setOnClickListener(
                v -> showLesson()
        );

        root.addView(start);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showChallenges() {

        root = createRoot();

        addBackButton(
                () -> showHome()
        );

        root.addView(
                createTitle(
                        "🏆 Défis du jour"
                )
        );

        root.addView(
                createSubtitle(
                        "De nouvelles missions peuvent " +
                        "être proposées régulièrement."
                )
        );

        String[] missions = {
                "⭐ Gagner 50 XP",
                "📚 Faire 3 leçons",
                "🔄 Réviser 20 mots",
                "💬 Apprendre 10 expressions",
                "🎯 Atteindre ton objectif quotidien",
                "🔥 Maintenir ta série",
                "🧠 Réussir 10 réponses",
                "🏆 Faire une session complète"
        };

        for (String mission : missions) {

            root.addView(
                    createButton(mission)
            );
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showDailyRoutine() {

        root = createRoot();

        addBackButton(
                () -> showHome()
        );

        root.addView(
                createTitle(
                        "📅 Ma routine"
                )
        );

        root.addView(
                createSubtitle(
                        "Une petite pratique chaque jour " +
                        "permet de construire une habitude."
                )
        );

        String[] activities = {
                "🌅 Session du matin",
                "📚 Leçon principale",
                "🔄 Révision",
                "💬 Expressions",
                "🎧 Compréhension",
                "🏆 Défi du jour"
        };

        for (String activity : activities) {

            root.addView(
                    createButton(activity)
            );
        }

        setContentView(
                wrapScroll(root)
        );
    }

    private void showExpressions() {

        root = createRoot();

        addBackButton(
                () -> showHome()
        );

        root.addView(
                createTitle(
                        "💬 Expressions utiles"
                )
        );

        String[] expressions = {
                "How are you? — Comment vas-tu ?",
                "Nice to meet you. — Ravi de te rencontrer.",
                "What do you mean? — Qu'est-ce que tu veux dire ?",
                "See you later. — À plus tard.",
                "I don't understand. — Je ne comprends pas.",
                "Could you repeat that? — Peux-tu répéter ?",
                "How much is it? — Combien ça coûte ?",
                "Where are you from? — D'où viens-tu ?",
                "I'm looking for... — Je cherche...",
                "Can you help me? — Peux-tu m'aider ?"
        };

        for (String expression :
                expressions) {

            root.addView(
                    createButton(expression)
            );
        }

        setContentView(
                wrapScroll(root)
        );
            }
       private void showProfile() {

        root = createRoot();

        addBackButton(
                () -> showHome()
        );

        root.addView(
                createTitle("👤 Mon profil")
        );

        root.addView(
                createSubtitle(
                        "Ton espace personnel"
                )
        );

        addSpace(15);

        root.addView(
                createInfoCard(
                        "🌍 Langue",
                        sourceLanguage +
                        " → " +
                        targetLanguage
                )
        );

        root.addView(
                createInfoCard(
                        "📊 Niveau",
                        currentLevel
                )
        );

        root.addView(
                createInfoCard(
                        "🎯 Objectif",
                        learningReason
                )
        );

        root.addView(
                createInfoCard(
                        "⭐ XP total",
                        String.valueOf(xp)
                )
        );

        root.addView(
                createInfoCard(
                        "🔥 Série",
                        streak + " jours"
                )
        );

        root.addView(
                createInfoCard(
                        "🎯 Objectif quotidien",
                        dailyGoal + " XP"
                )
        );

        addSpace(20);

        root.addView(
                createSectionTitle(
                        "📈 Progression"
                )
        );

        root.addView(
                createProgressLine(
                        "Vocabulaire",
                        20
                )
        );

        root.addView(
                createProgressLine(
                        "Grammaire",
                        15
                )
        );

        root.addView(
                createProgressLine(
                        "Compréhension",
                        10
                )
        );

        root.addView(
                createProgressLine(
                        "Expressions",
                        12
                )
        );

        setContentView(
                wrapScroll(root)
        );
    }

    private TextView createInfoCard(
            String title,
            String value
    ) {

        TextView card =
                new TextView(this);

        card.setText(
                title + "\n" + value
        );

        card.setTextSize(17);

        card.setTextColor(
                Color.DKGRAY
        );

        card.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.setPadding(
                20,
                20,
                20,
                20
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(
                0,
                6,
                0,
                6
        );

        card.setLayoutParams(params);

        return card;
    }

    private TextView createProgressLine(
            String name,
            int percent
    ) {

        TextView progress =
                new TextView(this);

        progress.setText(
                name +
                "\n" +
                createProgressBar(percent) +
                " " +
                percent +
                "%"
        );

        progress.setTextSize(16);

        progress.setTextColor(
                Color.DKGRAY
        );

        progress.setPadding(
                10,
                10,
                10,
                10
        );

        return progress;
    }

    private String createProgressBar(
            int percent
    ) {

        int total = 20;

        int filled =
                (percent * total) / 100;

        StringBuilder bar =
                new StringBuilder();

        for (int i = 0; i < total; i++) {

            if (i < filled) {
                bar.append("■");
            } else {
                bar.append("□");
            }
        }

        return bar.toString();
    }

    private void showSettings() {

        root = createRoot();

        addBackButton(
                () -> showHome()
        );

        root.addView(
                createTitle(
                        "⚙️ Paramètres"
                )
        );

        root.addView(
                createSubtitle(
                        "Personnalise ton expérience JoEnglish."
                )
        );

        Button sound =
                createButton(
                        "🔊 Son et audio"
                );

        sound.setOnClickListener(v ->
                showSoundSettings()
        );

        root.addView(sound);

        Button notifications =
                createButton(
                        "🔔 Notifications"
                );

        notifications.setOnClickListener(v ->
                showNotificationSettings()
        );

        root.addView(notifications);

        Button appearance =
                createButton(
                        "🎨 Apparence"
                );

        appearance.setOnClickListener(v ->
                showAppearanceSettings()
        );

        root.addView(appearance);

        Button reset =
                createButton(
                        "🔄 Réinitialiser la progression"
                );

        reset.setOnClickListener(v ->
                confirmReset()
        );

        root.addView(reset);

        addSpace(25);

        root.addView(
                createSubtitle(
                        "JoEnglish\n\n" +
                        "Créé par Joseph Marchand"
                )
        );

        setContentView(
                wrapScroll(root)
        );
    }

    private void showSoundSettings() {

        root = createRoot();

        addBackButton(
                () -> showSettings()
        );

        root.addView(
                createTitle(
                        "🔊 Son et audio"
                )
        );

        root.addView(
                createSubtitle(
                        "Les commandes audio seront utilisées " +
                        "dans les leçons et les exercices " +
                        "de compréhension."
                )
        );

        Button audio =
                createButton(
                        "▶️ Tester le son"
                );

        audio.setOnClickListener(v -> {

            android.widget.Toast.makeText(
                    this,
                    "Le moteur audio sera connecté aux cours.",
                    android.widget.Toast.LENGTH_SHORT
            ).show();

        });

        root.addView(audio);

        Button volume =
                createButton(
                        "🔊 Volume"
                );

        volume.setOnClickListener(v -> {

            android.widget.Toast.makeText(
                    this,
                    "Le volume utilise les commandes audio Android.",
                    android.widget.Toast.LENGTH_SHORT
            ).show();

        });

        root.addView(volume);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showNotificationSettings() {

        root = createRoot();

        addBackButton(
                () -> showSettings()
        );

        root.addView(
                createTitle(
                        "🔔 Notifications"
                )
        );

        root.addView(
                createSubtitle(
                        "Les notifications pourront rappeler " +
                        "à l'utilisateur sa session quotidienne, " +
                        "ses révisions et ses défis."
                )
        );

        Button activate =
                createButton(
                        "🔔 Activer les rappels"
                );

        activate.setOnClickListener(v -> {

            android.widget.Toast.makeText(
                    this,
                    "Les notifications seront activées " +
                    "avec le système de rappels.",
                    android.widget.Toast.LENGTH_SHORT
            ).show();

        });

        root.addView(activate);

        setContentView(
                wrapScroll(root)
        );
    }

    private void showAppearanceSettings() {

        root = createRoot();

        addBackButton(
                () -> showSettings()
        );

        root.addView(
                createTitle(
                        "🎨 Apparence"
                )
        );

        root.addView(
                createSubtitle(
                        "Les options d'apparence seront ajoutées " +
                        "avec le thème complet de JoEnglish."
                )
        );

        root.addView(
                createButton(
                        "☀️ Mode clair"
                )
        );

        root.addView(
                createButton(
                        "🌙 Mode sombre"
                )
        );

        setContentView(
                wrapScroll(root)
        );
    }

    private void confirmReset() {

        root = createRoot();

        root.addView(
                createTitle(
                        "⚠️ Réinitialisation"
                )
        );

        root.addView(
                createSubtitle(
                        "Cette action remettra ta progression " +
                        "à zéro."
                )
        );

        Button cancel =
                createButton(
                        "← Annuler"
                );

        cancel.setOnClickListener(
                v -> showSettings()
        );

        root.addView(cancel);

        Button reset =
                createButton(
                        "🗑️ Réinitialiser"
                );

        reset.setOnClickListener(v -> {

            xp = 0;
            streak = 0;

            saveData();

            showHome();
        });

        root.addView(reset);

        setContentView(
                wrapScroll(root)
        );
    }

    private LinearLayout createRoot() {

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                30,
                35,
                30,
                35
        );

        layout.setBackgroundColor(
                Color.WHITE
        );

        return layout;
    }

    private ScrollView wrapScroll(
            View view
    ) {

        ScrollView scroll =
                new ScrollView(this);

        scroll.setFillViewport(true);

        scroll.addView(view);

        return scroll;
    }

    private TextView createTitle(
            String text
    ) {

        TextView title =
                new TextView(this);

        title.setText(text);

        title.setTextSize(30);

        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        title.setTextColor(
                Color.rgb(
                        79,
                        70,
                        229
                )
        );

        title.setGravity(
                Gravity.CENTER
        );

        title.setPadding(
                0,
                10,
                0,
                20
        );

        return title;
    }

    private TextView createSubtitle(
            String text
    ) {

        TextView subtitle =
                new TextView(this);

        subtitle.setText(text);

        subtitle.setTextSize(17);

        subtitle.setTextColor(
                Color.DKGRAY
        );

        subtitle.setGravity(
                Gravity.CENTER
        );

        subtitle.setPadding(
                5,
                5,
                5,
                15
        );

        return subtitle;
    }

    private TextView createSectionTitle(
            String text
    ) {

        TextView section =
                new TextView(this);

        section.setText(text);

        section.setTextSize(23);

        section.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        section.setTextColor(
                Color.DKGRAY
        );

        section.setPadding(
                0,
                18,
                0,
                12
        );

        return section;
    }

    private TextView createStat(
            String text
    ) {

        TextView stat =
                new TextView(this);

        stat.setText(text);

        stat.setTextSize(16);

        stat.setTextColor(
                Color.DKGRAY
        );

        stat.setGravity(
                Gravity.CENTER
        );

        stat.setPadding(
                8,
                15,
                8,
                15
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );

        stat.setLayoutParams(params);

        return stat;
    }

    private Button createButton(
            String text
    ) {

        Button button =
                new Button(this);

        button.setText(text);

        button.setTextSize(16);

        button.setAllCaps(false);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(
                0,
                8,
                0,
                8
        );

        button.setLayoutParams(params);

        return button;
    }

    private void addBackButton(
            Runnable action
    ) {

        Button back =
                createButton("← Retour");

        back.setOnClickListener(
                v -> action.run()
        );

        root.addView(back);
    }

    private void addSpace(
            int height
    ) {

        View space =
                new View(this);

        space.setLayoutParams(
                new LinearLayout.LayoutParams(
                        1,
                        height
                )
        );

        root.addView(space);
    }
                    } 
