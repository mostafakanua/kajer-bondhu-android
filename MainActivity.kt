package in.kajerbondhu.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {
    private val green = Color.rgb(23,105,65)
    private val orange = Color.rgb(239,128,39)
    private val ink = Color.rgb(35,52,42)
    private val pale = Color.rgb(242,247,242)
    private lateinit var content: LinearLayout
    private var drawer = false

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); home() }

    private fun txt(s:String, size:Float, color:Int=ink, bold:Boolean=false)=TextView(this).apply {
        text=s; textSize=size; setTextColor(color); setPadding(12,10,12,10)
        if(bold) setTypeface(typeface, android.graphics.Typeface.BOLD)
    }
    private fun btn(s:String, color:Int=green, action:()->Unit)=Button(this).apply {
        text=s; textSize=14f; isAllCaps=false; setTextColor(Color.WHITE)
        backgroundTintList=android.content.res.ColorStateList.valueOf(color); setOnClickListener { action() }
    }
    private fun shell(title:String="") {
        val root=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setBackgroundColor(pale) }
        val head=LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL; gravity=Gravity.CENTER_VERTICAL; setPadding(6,6,6,6); setBackgroundColor(Color.WHITE) }
        head.addView(btn(if(drawer) "✕" else "☰") { drawer=!drawer; if(drawer) menu() else home() }, LinearLayout.LayoutParams(58,54))
        head.addView(txt("কাজের বন্ধু\nআপনার এলাকার কাজ ও সেবা",17f,green,true), LinearLayout.LayoutParams(0,-2,1f))
        head.addView(btn("প্রোফাইল",orange) { page("আমার অ্যাকাউন্ট") })
        root.addView(head)
        if(title.isNotBlank()) root.addView(txt(title,21f,green,true))
        val scroll=ScrollView(this)
        content=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(12,4,12,14) }
        scroll.addView(content); root.addView(scroll,LinearLayout.LayoutParams(-1,0,1f))
        val nav=LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL; setBackgroundColor(Color.WHITE); gravity=Gravity.CENTER }
        listOf("হোম","খুঁজুন","+ পোস্ট","চ্যাট","অ্যাকাউন্ট").forEachIndexed { i,s ->
            nav.addView(btn(s,if(i==2) orange else green) { when(i){0->home();1->page("খুঁজুন");2->page("নতুন পোস্ট");3->page("চ্যাট");else->page("আমার অ্যাকাউন্ট")} },LinearLayout.LayoutParams(0,54,1f))
        }
        root.addView(nav); setContentView(root)
    }
    private fun card(title:String, desc:String, icon:String, destination:String) {
        val box=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setBackgroundColor(Color.WHITE); setPadding(8,5,8,5) }
        box.addView(txt("$icon  $title",17f,green,true)); box.addView(txt(desc,13f,Color.DKGRAY))
        box.addView(btn("বিস্তারিত দেখুন  →",orange) { page(destination) })
        content.addView(box,LinearLayout.LayoutParams(-1,-2).apply { setMargins(0,5,0,9) })
    }
    private fun home() {
        drawer=false; shell()
        content.addView(txt("আপনার এলাকার কাজ ও পরিষেবা",23f,green,true))
        content.addView(txt("কাজ খুঁজুন • কর্মী খুঁজুন • কেনাবেচা • বুকিং",14f))
        content.addView(btn("＋  নতুন পোস্ট করুন",orange) { page("নতুন পোস্ট") })
        content.addView(txt("সার্ভিস বিভাগ",19f,ink,true))
        card("কাজ খুঁজুন","চাকরি, অস্থায়ী ও দৈনিক কাজ","💼","কাজ খুঁজুন")
        card("কর্মী খুঁজুন","দক্ষ কর্মী ও সাধারণ শ্রমিক","👥","কর্মী খুঁজুন")
        card("পুরোনো জিনিস কেনাবেচা","ব্যবহৃত জিনিসের পোস্ট দেখুন","🛍️","কেনাবেচা")
        card("ক্যাটারিং ও প্যান্ডেল","অনুষ্ঠানের পরিষেবা বুকিং","🎉","ক্যাটারিং ও প্যান্ডেল")
        card("রুট শেয়ার","খালি গাড়ি ও আসন খোঁজার বিভাগ","🛣️","রুট শেয়ার")
        card("গাড়ি বুকিং","বিয়ে ও অনুষ্ঠানের গাড়ি","🚘","গাড়ি বুকিং")
        content.addView(txt("সাম্প্রতিক পোস্ট",19f,ink,true))
        card("দোকানে সহকারী প্রয়োজন","কলকাতা • ফুল টাইম • নমুনা পোস্ট","📌","দোকানে সহকারী প্রয়োজন")
    }
    private fun menu() {
        drawer=true; shell("সব বিভাগ")
        listOf("হোম","কাজ খুঁজুন","কর্মী খুঁজুন","কেনাবেচা","ক্যাটারিং ও প্যান্ডেল","রুট শেয়ার","গাড়ি বুকিং","নতুন পোস্ট","আমার পোস্ট","চ্যাট","আমার অ্যাকাউন্ট","সেটিংস").forEach {
            content.addView(btn("$it   ›",Color.WHITE) { page(it) }.apply { setTextColor(green); gravity=Gravity.CENTER_VERTICAL })
        }
    }
    private fun page(p:String) {
        drawer=false; shell(p)
        when(p) {
            "নতুন পোস্ট" -> { content.addView(txt("কোন ধরনের পোস্ট করবেন?",18f,ink,true)); listOf("কাজের বিজ্ঞাপন","কর্মী খুঁজছি","পুরোনো জিনিস বিক্রি","ক্যাটারিং / প্যান্ডেল","গাড়ি / রুট শেয়ার").forEach { card(it,"পরবর্তী ধাপে পোস্ট ফর্ম যুক্ত হবে","＋",it) } }
            "খুঁজুন" -> listOf("কাজ খুঁজুন","কর্মী খুঁজুন","কেনাবেচা","ক্যাটারিং ও প্যান্ডেল","গাড়ি বুকিং").forEach { card(it,"বিভাগ খুলতে চাপ দিন","🔎",it) }
            "আমার অ্যাকাউন্ট" -> { content.addView(txt("👤  আপনার প্রোফাইল",22f,green,true)); content.addView(txt("ডেমো অ্যাকাউন্ট — লগইন এখনো সংযুক্ত নয়",14f)); card("আমার পোস্ট","আপনার পোস্ট এখানে দেখা যাবে","📄","আমার পোস্ট") }
            "চ্যাট" -> content.addView(txt("চ্যাট চালু করতে পরবর্তী ধাপে Firebase বা অন্য ব্যাকএন্ড যুক্ত করতে হবে।",16f))
            else -> { content.addView(txt("$p বিভাগ",20f,green,true)); content.addView(txt("এটি প্রাথমিক Native Android ডেমো। অনলাইন পোস্ট, লগইন, চ্যাট ও বুকিং পরবর্তী ধাপে যুক্ত হবে।",15f)); content.addView(btn("＋ পোস্ট তৈরি",orange) { page("নতুন পোস্ট") }); content.addView(btn("হোমে ফিরুন") { home() }) }
        }
    }
}
