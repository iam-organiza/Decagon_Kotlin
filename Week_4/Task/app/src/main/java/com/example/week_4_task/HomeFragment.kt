package com.example.week_4_task

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

class HomeFragment : Fragment() {
    private lateinit var sliderItemList: ArrayList<SliderItem>
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var sliderHandle: Handler
    private lateinit var sliderRunnable: Runnable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sliderItems()
        itemSliderView()
        val events = mutableListOf(
            UpcomingEvent(R.drawable.upcoming_img1, "Arya Stark", "20.10.2020", "30 days left", R.drawable.border_left),
            UpcomingEvent(R.drawable.upcoming_img2, "Samuel Garfield", "16.04.2021", "120 days left", R.drawable.border_left_red),
            UpcomingEvent(R.drawable.upcoming_img3, "Kome Holmes", "25.12.2020", "80 days left", R.drawable.border_left_purple),
            UpcomingEvent(R.drawable.upcoming_img4, "Omolade Rogers", "01.01.2020", "2 days left", R.drawable.border_left_blue)
        )

        val upcomingEventsAdapter = UpcomingEventsAdapter(events)
        val rvUpcomingEvents = requireView().findViewById<RecyclerView>(R.id.rvUpcomingEvents)
        rvUpcomingEvents.adapter = upcomingEventsAdapter
        rvUpcomingEvents.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun sliderItems() {
        val viewPager2 = requireView().findViewById<ViewPager2>(R.id.viewPagerSlider2)
        sliderItemList = ArrayList()
        sliderAdapter = SliderAdapter(viewPager2, sliderItemList)
        viewPager2.adapter = sliderAdapter
//        viewPager2.clipToPadding = false
//        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
//        viewPager2.currentItem = 3
        viewPager2.setPageTransformer(getTransformation())
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

//        var compositePageTransformer = CompositePageTransformer()
//        compositePageTransformer.addTransformer(MarginPageTransformer(10))
////        compositePageTransformer.addTransformer(ZoomOutPageTransformer)
//
//        viewPager2.setPageTransformer(compositePageTransformer)
//        compositePageTransformer.addTransformer { page, position ->
//            var r: Float = 1 - abs(position)
//            page.scaleY = 0.85f + r * 0.15f
//        }
//        lifecycleScope.launch{
//            delay(1000)
//
//            repeat()
//        }

        sliderHandle = Handler()
        sliderRunnable = Runnable {
            viewPager2.currentItem += 1
        }
        viewPager2.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandle.removeCallbacks(sliderRunnable)
                    sliderHandle.postDelayed(sliderRunnable, 10000)
                }

            }
        )
    }

    private fun getTransformation(): CompositePageTransformer {
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(30))
        transform.addTransformer { page, position ->
            page.scaleY = 0.85f + (1 - kotlin.math.abs(position)) * 0.15f
        }
        return transform
    }

    override fun onPause() {
        super.onPause()
        sliderHandle.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandle.postDelayed(sliderRunnable, 10000)
    }

    private fun itemSliderView() {
        sliderItemList.add(SliderItem(R.drawable.vector, "It's Victors Birthday"))
        sliderItemList.add(SliderItem(R.drawable.image1, "Happy Holidays"))
        sliderItemList.add(SliderItem(R.drawable.image2, "It's A Sunny Day"))
        sliderItemList.add(SliderItem(R.drawable.image3, "Happy Monday"))
        sliderItemList.add(SliderItem(R.drawable.image4, "It Works"))
    }
}