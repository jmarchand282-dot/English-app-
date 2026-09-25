package com.josephmarchand.joenglish;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    LinearLayout root, content; SharedPreferences p; TextToSpeech tts;
    int xp, streak, lesson=0, q=0, lessonXp=0;
    String source="Français", target="English", level="Débutant total", reason="Voyage";
    final String[] titles={"Salutations","Se présenter","Le verbe BE","La famille","Les nombres","Les objets","Les couleurs","Actions","Routine","Questions simples","Politesse","Mini conversation"};
    final String[][] data={
      {"Comment dit-on « Bonjour » ?","Hello","Goodbye","Thanks","Hello signifie bonjour."},
      {"Complète : My name ___ Joseph.","is","are","am","Avec My name, on utilise is."},
      {"Complète : I ___ a student.","am","is","are","Avec I, on utilise am."},
      {"Comment dit-on « mère » ?","mother","father","brother","Mother signifie mère."},
      {"Quel nombre signifie « five » ?","5","3","10","Five signifie cinq."},
      {"Quel mot signifie « livre » ?","book","house","chair","Book signifie livre."},
      {"Quelle couleur signifie « rouge » ?","red","blue","green","Red signifie rouge."},
      {"Que signifie « eat » ?","manger","dormir","courir","Eat signifie manger."},
      {"« I wake up » signifie :","Je me réveille","Je mange","Je travaille","Wake up signifie se réveiller."},
      {"Comment dit-on « Où habites-tu ? » ?","Where do you live?","What are you?","How old is?","Cette phrase demande où une personne habite."},
      {"« Thank you » signifie :","Merci","Bonjour","Pardon","Thank you signifie merci."},
      {"A: How are you? B: ___","I'm fine.","Good night.","My name Joseph.","I'm fine signifie je vais bien."}
    };
    @Override public void onCreate(Bundle b){super.onCreate(b); p=getSharedPreferences("joenglish",0); load(); tts=new TextToSpeech(this,s->{if(s==0)tts.setLanguage(Locale.US);}); if(p.getBoolean("onboarding",false)) home(); else welcome();}
    void load(){xp=p.getInt("xp",0);streak=p.getInt("streak",0);lesson=p.getInt("lesson",0);source=p.getString("source","Français");target=p.getString("target","English");level=p.getString("level","Débutant total");reason=p.getString("reason","Voyage");}
    void save(){p.edit().putInt("xp",xp).putInt("streak",streak).putInt("lesson",lesson).putString("source",source).putString("target",target).putString("level",level).putString("reason",reason).apply();}
    void page(String title,String sub){root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(22,28,22,18);root.setBackgroundColor(Color.rgb(248,249,252)); root.addView(txt(title,28,true));if(sub!=null)root.addView(txt(sub,15,false));ScrollView s=new ScrollView(this);content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);s.addView(content);root.addView(s,new LinearLayout.LayoutParams(-1,0,1));setContentView(root);}
    TextView txt(String s,float z,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(z);t.setTextColor(Color.rgb(30,30,40));t.setTypeface(null,bold?1:0);t.setPadding(0,9,0,9);return t;}
    Button btn(String s,View.OnClickListener l){Button b=new Button(this);b.setText(s);b.setTextSize(15);b.setAllCaps(false);b.setOnClickListener(l);content.addView(b);return b;}
    void welcome(){page("🇬🇧 JoEnglish","Un parcours personnalisé pour apprendre chaque jour.");content.addView(txt("Bienvenue 👋",24,true));content.addView(txt("Configure ton parcours avant de commencer les leçons.",17,false));btn("Commencer",v->chooseSource());}
    void chooseSource(){page("Ta langue","Quelle langue connais-tu déjà ?");for(String x:new String[]{"Français","English","Español","Português"})btn(x,v->{source=((Button)v).getText().toString();chooseTarget();});}
    void chooseTarget(){page("Langue à apprendre","Choisis ta langue cible.");for(String x:new String[]{"English","Français","Español","Deutsch"})if(!x.equals(source))btn(x,v->{target=((Button)v).getText().toString();chooseLevel();});}
    void chooseLevel(){page("Ton niveau","Choisis ton niveau actuel.");for(String x:new String[]{"Débutant total","Je connais quelques mots","Intermédiaire"})btn(x,v->{level=((Button)v).getText().toString();chooseReason();});}
    void chooseReason(){page("Pourquoi apprends-tu ?","Cela personnalise ton parcours.");for(String x:new String[]{"Voyage","Travail","Études","Conversation","Culture"})btn(x,v->{reason=((Button)v).getText().toString();testIntro();});}
    void testIntro(){page("Test de niveau","5 questions rapides.");content.addView(txt("Pas de stress : ce test sert seulement à choisir le point de départ.",17,false));btn("Commencer",v->test(0));}
    void test(int n){page("Test "+(n+1)+"/5","Choisis la réponse correcte.");String[][] a={{"Comment dit-on « bonjour » ?","Hello","Goodbye","Thanks"},{"I ___ Joseph.","am","is","are"},{"Thank you signifie :","Merci","Bonjour","Au revoir"},{"« maison » en anglais ?","house","book","school"},{"She ___ a student.","is","are","am"}};content.addView(txt(a[n][0],21,true));for(int i=1;i<4;i++){final int k=i;btn(a[n][i],v->{if(k==1)Toast.makeText(this,"✓ Bonne réponse",0).show();else Toast.makeText(this,"Correction : "+a[n][1],1).show();if(n<4)test(n+1);else{p.edit().putBoolean("onboarding",true).apply();save();home();}});}}
    void home(){page("JoEnglish",""+source+" → "+target);content.addView(txt("⭐ XP : "+xp+"\n🔥 Série : "+streak+" jour(s)\n📈 Niveau : "+level,18,true));btn("📚 Continuer le cours",v->course());btn("🔁 Révision",v->revision());btn("💬 Expressions",v->expressions());btn("🏆 Défis",v->challenges());btn("👤 Profil",v->profile());btn("⚙️ Paramètres",v->settings());}
    void course(){page("Parcours","A1 → A2 → B1 → B2 → C1 → C2 → Maîtrise → Expert");for(String x:new String[]{"A1","A2","B1","B2","C1","C2","Maîtrise","Expert"})btn(x+"  •  30 unités",v->lessons(x));}
    void lessons(String l){page(l,"Leçons disponibles");for(int i=0;i<30;i++){final int n=i;btn((i+1)+". "+(i<titles.length?titles[i]:"Entraînement "+(i+1)),v->{if(n<titles.length)start(n);else Toast.makeText(this,"Nouveau contenu à venir.",1).show();});}}
    void start(int n){lesson=n;q=0;lessonXp=0;lessonIntro();}
    void lessonIntro(){page(titles[lesson],"Leçon "+(lesson+1));content.addView(txt("Apprends cette notion puis réponds aux questions.\n\nObjectif : comprendre et utiliser la langue dans une situation réelle.",17,false));btn("🔊 Écouter",v->speak(data[lesson][0]));btn("Commencer",v->question());}
    void question(){page("Question "+(q+1)+"/3",titles[lesson]);String[] d=data[lesson];String correct=d[1];String a=q==0?correct:(q==1?d[2]:d[3]);String b=q==0?d[2]:(q==1?d[3]:correct);String c=q==0?d[3]:(q==1?correct:d[2]);content.addView(txt(d[0],21,true));btn("🔊 Écouter",v->speak(d[0]));for(String x:new String[]{a,b,c})btn(x,v->answer(x,correct,d[4]));}
    void answer(String x,String correct,String exp){if(x.equalsIgnoreCase(correct)){lessonXp+=10;Toast.makeText(this,"✓ Correct ! +10 XP",0).show();}else Toast.makeText(this,"✗ "+exp,1).show();q++;if(q<3)question();else finishLesson();}
    void finishLesson(){xp+=lessonXp;streak=Math.max(1,streak);if(lesson<29)lesson++;save();page("🎉 Leçon terminée !","Bravo !");content.addView(txt("+"+lessonXp+" XP",30,true));content.addView(txt("La correction t'aide à mémoriser. Reviens chaque jour pour renforcer tes acquis.",17,false));btn("Leçon suivante",v->lessons("A1"));btn("Accueil",v->home());}
    void revision(){page("🔁 Révision","Révise les leçons déjà vues.");for(int i=0;i<Math.min(lesson+1,titles.length);i++){final int n=i;btn("Réviser : "+titles[i],v->start(n));}btn("Accueil",v->home());}
    void expressions(){page("💬 Expressions utiles","À réutiliser dans la vraie vie.");String[][] e={{"How are you?","Comment vas-tu ?"},{"Nice to meet you.","Ravi de te rencontrer."},{"I don't understand.","Je ne comprends pas."},{"Could you help me?","Pourrais-tu m'aider ?"},{"How much is it?","Combien ça coûte ?"},{"See you later.","À plus tard."},{"Have a nice day!","Bonne journée !"}};for(String[] x:e){content.addView(txt(x[0]+"\n"+x[1],17,true));btn("🔊 Écouter",v->speak(x[0]));}}
    void challenges(){page("🏆 Défis","Objectifs supplémentaires.");content.addView(txt("Défi du jour\nGagne 50 XP aujourd'hui.\n\nDéfi de la semaine\nTermine 5 leçons.\n\nDéfi vocabulaire\nRévise 20 expressions.",18,false));btn("Accueil",v->home());}
    void profile(){page("👤 Profil","Ta progression.");content.addView(txt("⭐ XP total : "+xp+"\n🔥 Série : "+streak+" jour(s)\n🌍 "+source+" → "+target+"\n🎯 Motivation : "+reason,18,false));btn("Accueil",v->home());}
    void settings(){page("⚙️ Paramètres","Réglages de JoEnglish.");content.addView(txt("Créé par Joseph Marchand\nLangue : "+source+" → "+target,17,false));btn("🔊 Tester le son",v->speak("Hello, welcome to JoEnglish!"));btn("♻️ Réinitialiser la progression",v->{xp=0;streak=0;lesson=0;save();Toast.makeText(this,"Progression réinitialisée.",0).show();home();});btn("Accueil",v->home());}
    void speak(String s){if(tts!=null)tts.speak(s,TextToSpeech.QUEUE_FLUSH,null,"jo");}
    @Override protected void onDestroy(){if(tts!=null){tts.stop();tts.shutdown();}super.onDestroy();}
        }
                                                                                           
