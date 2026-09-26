package com.josephmarchand.joenglish;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private LinearLayout root, content; private SharedPreferences p; private TextToSpeech tts;
    private int xp, streak, lesson, daily; private String source,target,level,reason;
    private final int BLUE=Color.rgb(79,70,229), DARK=Color.rgb(28,31,43), MUTED=Color.rgb(100,105,120), BG=Color.rgb(247,248,252), GREEN=Color.rgb(22,163,74);
    private final ArrayList<CourseData.Lesson> lessons=new ArrayList<>(); private int qIndex=0, earned=0;

    @Override public void onCreate(Bundle b){super.onCreate(b); p=getSharedPreferences("joenglish",0); load();
        tts=new TextToSpeech(this,s->{if(s==TextToSpeech.SUCCESS)tts.setLanguage(Locale.US);});
        lessons.addAll(CourseData.build()); if(p.getBoolean("onboarding",false))home(); else welcome();}
    private void load(){xp=p.getInt("xp",0);streak=p.getInt("streak",0);lesson=p.getInt("lesson",0);daily=p.getInt("daily",15);source=p.getString("source","Français");target=p.getString("target","English");level=p.getString("level","Débutant total");reason=p.getString("reason","Voyage");}
    private void save(){p.edit().putInt("xp",xp).putInt("streak",streak).putInt("lesson",lesson).putInt("daily",daily).putString("source",source).putString("target",target).putString("level",level).putString("reason",reason).apply();}
    private TextView tv(String s,float z,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(z);t.setTextColor(DARK);t.setTypeface(Typeface.DEFAULT,bold?1:0);t.setPadding(4,7,4,7);return t;}
    private Button btn(String s,View.OnClickListener l){Button b=new Button(this);b.setText(s);b.setTextSize(15);b.setAllCaps(false);b.setOnClickListener(l);LinearLayout.LayoutParams x=new LinearLayout.LayoutParams(-1,56);x.setMargins(0,5,0,5);b.setLayoutParams(x);return b;}
    private void page(String title,String sub){root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(20,22,20,16);root.setBackgroundColor(BG);root.addView(tv(title,27,true));if(sub!=null)root.addView(tv(sub,15,false));ScrollView sv=new ScrollView(this);content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);sv.addView(content);root.addView(sv,new LinearLayout.LayoutParams(-1,0,1));setContentView(root);}
    private TextView card(String s){TextView t=tv(s,17,true);t.setGravity(Gravity.CENTER_VERTICAL);t.setPadding(18,15,18,15);t.setBackgroundColor(Color.WHITE);return t;}

    private void welcome(){page("🇬🇧 JoEnglish","Apprends aujourd'hui, un meilleur demain !");TextView w=tv("Bienvenue 👋\n\nUne application complète pour apprendre, pratiquer et réviser.",21,true);w.setGravity(17);content.addView(w);content.addView(btn("Commencer",v->source()));}
    private void source(){page("1. Ta langue","Langue que tu connais déjà");for(String s:new String[]{"Français","English","Español","Português"})content.addView(btn(s,v->{source=((Button)v).getText().toString();target();}));}
    private void target(){page("2. Langue à apprendre","Choisis ta langue cible");for(String s:new String[]{"English","Français","Español","Deutsch"})if(!s.equals(source))content.addView(btn(s,v->{target=((Button)v).getText().toString();levels();}));}
    private void levels(){page("3. Ton niveau","Où en es-tu ?");for(String s:new String[]{"Débutant total","Je connais quelques mots","Intermédiaire"})content.addView(btn(s,v->{level=((Button)v).getText().toString();reason();}));}
    private void reason(){page("4. Ton objectif","Pourquoi apprends-tu ?");for(String s:new String[]{"Voyage","Travail","Études","Conversation","Culture"})content.addView(btn(s,v->{reason=((Button)v).getText().toString();goal();}));}
    private void goal(){page("5. Objectif quotidien","Temps souhaité chaque jour");for(int n:new int[]{5,10,15,20,30})content.addView(btn(n+" minutes",v->{daily=Integer.parseInt(((Button)v).getText().toString().split(" ")[0]);test(0,0);}));}
    private void test(int n,int score){String[][] q={{"Bonjour = ?","Hello","Goodbye","Thanks"},{"I ___ Joseph.","am","is","are"},{"Thank you = ?","Merci","Bonjour","Pardon"},{"house = ?","maison","livre","école"},{"She ___ a student.","is","am","are"}};page("Test de niveau","Question "+(n+1)+"/5");content.addView(tv(q[n][0],22,true));for(int i=1;i<4;i++){final int a=i;content.addView(btn(q[n][a],v->{int sc=score+(a==1?1:0);if(n<4)test(n+1,sc);else{p.edit().putBoolean("onboarding",true).apply();save();home();}}));}}

    private void home(){page("JoEnglish","Bonjour 👋  •  "+source+" → "+target);content.addView(tv("Prêt pour ta prochaine session ?",20,true));LinearLayout row=new LinearLayout(this);row.setOrientation(LinearLayout.HORIZONTAL);TextView a=card("⭐ XP\n"+xp),b=card("🔥 Série\n"+streak+" jour(s)");row.addView(a,new LinearLayout.LayoutParams(0,92,1));row.addView(b,new LinearLayout.LayoutParams(0,92,1));content.addView(row);content.addView(card("🎯 Objectif\n"+daily+" minutes par jour"));content.addView(tv("\n📈 Progression",19,true));int pct=Math.min(100,(xp%50)*2);content.addView(tv(pct+"%   •   "+(xp%50)+" / 50 XP",15,false));content.addView(btn("▶ Continuer le cours",v->course()));content.addView(btn("🔁 Révision",v->revision()));content.addView(btn("💬 Expressions",v->expressions()));content.addView(btn("🏆 Défis",v->challenges()));content.addView(btn("📊 Statistiques",v->stats()));content.addView(btn("👤 Profil",v->profile()));content.addView(btn("⚙️ Paramètres",v->settings()));content.addView(tv("\nCréé par Joseph Marchand",13,false));}
    private void course(){page("📚 Parcours","A1 → A2 → B1 → B2 → C1 → C2 → Maîtrise → Expert");String[] lv={"A1","A2","B1","B2","C1","C2","Maîtrise","Expert"};for(String s:lv)content.addView(btn(s+"  •  30 unités",v->units(s)));content.addView(btn("← Accueil",v->home()));}
    private void units(String lv){page(lv,"30 unités • progression libre");for(int i=0;i<30;i++){final int n=i;String state=i<lesson?"✓":i==lesson?"▶":"○";content.addView(btn(state+"  Unité "+(i+1)+"  •  "+CourseData.topic(i),v->{if(n<lessons.size())lesson(n);else Toast.makeText(this,"Nouveau contenu à venir dans cette unité.",Toast.LENGTH_SHORT).show();}));}content.addView(btn("← Parcours",v->course()));}
    private void lesson(int n){if(n>=lessons.size())return;CourseData.Lesson l=lessons.get(n);qIndex=0;earned=0;intro(l);}
    private void intro(CourseData.Lesson l){page(l.title,"Leçon • explication et pratique");content.addView(tv(l.explanation,17,false));content.addView(btn("🔊 Écouter",v->speak(l.q.get(0).question)));content.addView(btn("Commencer",v->question(l)));}
    private void question(CourseData.Lesson l){CourseData.Q q=l.q.get(qIndex);page("Question "+(qIndex+1)+"/"+l.q.size(),l.title);content.addView(tv(q.question,22,true));content.addView(btn("🔊 Écouter",v->speak(q.question)));for(String a:q.answers)content.addView(btn(a,v->answer(l,q,a)));}
    private void answer(CourseData.Lesson l,CourseData.Q q,String a){if(a.equals(q.correct)){earned+=10;Toast.makeText(this,"✓ Correct ! +10 XP",Toast.LENGTH_SHORT).show();}else Toast.makeText(this,"✗ "+q.explanation,Toast.LENGTH_LONG).show();qIndex++;if(qIndex<l.q.size())question(l);else finishLesson(l);}
    private void finishLesson(CourseData.Lesson l){xp+=earned;streak=Math.max(1,streak);lesson=Math.min(lesson+1,29);save();page("🎉 Bravo !",l.title+" terminée");content.addView(tv("+"+earned+" XP",32,true));content.addView(tv("Ta progression a été enregistrée.",17,false));content.addView(btn("Leçon suivante",v->course()));content.addView(btn("Accueil",v->home()));}
    private void revision(){page("🔁 Révision intelligente","Revois les leçons disponibles");for(int i=0;i<Math.min(lessons.size(),Math.max(1,lesson));i++){final int n=i;content.addView(btn("Réviser • "+lessons.get(i).title,v->lesson(n)));}content.addView(btn("Accueil",v->home()));}
    private void expressions(){page("💬 Expressions utiles","Écoute et mémorise");String[][] e={{"How are you?","Comment vas-tu ?"},{"Nice to meet you.","Ravi de te rencontrer."},{"I don't understand.","Je ne comprends pas."},{"Could you help me?","Pourrais-tu m'aider ?"},{"Where is the bathroom?","Où sont les toilettes ?"},{"How much is it?","Combien ça coûte ?"},{"See you later.","À plus tard."},{"Have a nice day!","Bonne journée !"},{"I need help.","J'ai besoin d'aide."},{"What does this mean?","Qu'est-ce que cela signifie ?"}};for(String[] x:e){content.addView(tv(x[0]+"\n"+x[1],17,true));content.addView(btn("🔊 Écouter",v->speak(x[0])));}content.addView(btn("Accueil",v->home()));}
    private void challenges(){page("🏆 Défis","Objectifs permanents");content.addView(card("🔥 Quotidien\nGagne 50 XP"));content.addView(card("📅 Hebdomadaire\nTermine 5 leçons"));content.addView(card("📚 Vocabulaire\nRévise 20 éléments"));content.addView(card("💬 Conversation\nÉcoute 10 expressions"));content.addView(btn("Accueil",v->home()));}
    private void stats(){page("📊 Statistiques","Ta progression");content.addView(card("⭐ XP total\n"+xp));content.addView(card("🔥 Série actuelle\n"+streak+" jour(s)"));content.addView(card("📚 Leçons parcourues\n"+lesson));content.addView(card("🎯 Objectif quotidien\n"+daily+" minutes"));content.addView(card("🏅 Rang\n"+rank()));content.addView(btn("Accueil",v->home()));}
    private String rank(){if(xp<100)return"Débutant";if(xp<500)return"Apprenant";if(xp<1500)return"Passionné";if(xp<5000)return"Expert";return"Maître";}
    private void profile(){page("👤 Profil","Ton identité JoEnglish");content.addView(card("🌍 "+source+" → "+target));content.addView(card("📊 "+level));content.addView(card("🎯 "+reason));content.addView(card("⭐ "+xp+" XP"));content.addView(btn("Accueil",v->home()));}
    private void settings(){page("⚙️ Paramètres","Réglages");content.addView(btn("🔊 Tester la voix",v->speak("Hello, welcome to JoEnglish!")));content.addView(btn("♻️ Réinitialiser la progression",v->{xp=0;streak=0;lesson=0;save();home();}));content.addView(tv("Créé par Joseph Marchand",15,false));content.addView(btn("Accueil",v->home()));}
    private void speak(String s){if(tts!=null)tts.speak(s,TextToSpeech.QUEUE_FLUSH,null,"joenglish");}
    @Override protected void onDestroy(){if(tts!=null){tts.stop();tts.shutdown();}super.onDestroy();}
}
