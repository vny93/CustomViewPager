package com.example.customviewpager

import android.animation.*
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.customviewpager.databinding.ActivityMainBinding
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPager2: ViewPager2
    private lateinit var imageAdapter: ViewPagerAdapter1
    private lateinit var imageAdapter2: ViewPagerAdapter2
    lateinit var imageList: List<Int>
    lateinit var imageList2: List<Int>
    lateinit var list: List<Photo>
    private var currentIndex = 0
    private var isImv1Visible = true
    private var currentPage = 0
    private var currentPage2 = 0
    lateinit var listOld1: List<Int>
    lateinit var listOld2: List<Int>
    var isFirst = true
    var isFirst2 = true
    val handler = Handler(Looper.getMainLooper())
    val handler2 = Handler(Looper.getMainLooper())
    val handlerVP1 = Handler(Looper.getMainLooper())
    val handlerVP2 = Handler(Looper.getMainLooper())
    val handlerDelayVP = Handler(Looper.getMainLooper())
    var pagePxWidth1: Float = 0f
    var pagePxWidth2: Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        viewPager2 = binding.viewPager2

        imageList = listOf(
            R.drawable.img_banner_1_1,
            R.drawable.bogia_banner_1_2,
            R.drawable.cualaivobau_banner_1_2,
        )

        imageList2 = listOf(
            R.drawable.img_banner_1_2,
            R.drawable.img_banner_1_2,
            R.drawable.img_banner_1_2,
        )

        list = listOf(
            Photo(R.drawable.nhabanu_banner_1_1),
            Photo(R.drawable.bogia_banner_1_1),
            Photo(R.drawable.cualaivobau_banner)
        )

        listOld1 = imageList
        listOld2 = imageList2

        //background
        binding.imv1?.setImageResource(list[currentIndex].resourceId)
        binding.imv2?.setImageResource(list[currentIndex + 1].resourceId)
        currentIndex = (currentIndex + 1) % list.size

        handler2.postDelayed({
            updateImage()
        }, 2500L)



        //viewpager 1
        imageAdapter = ViewPagerAdapter1(imageList as MutableList<Int>)
        viewPager.adapter = imageAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val item = viewPager.getChildAt(0)
                pagePxWidth1 = (viewPager.width).toFloat()
                if (position == 0 && isFirst) {
                    //run first when building the app
                    handlerDelayVP.postDelayed({
                        cusTomAnimatorViewPager()
                    }, 1200L)

                }
                if (!isFirst) {
                    val animatorOut2 =
                        ObjectAnimator.ofFloat(item, "translationX", pagePxWidth1, -120f)
                    animatorOut2.duration = 600L

                    val animatorIn2 = ObjectAnimator.ofFloat(item, "translationX", -120f, 0f)
                    animatorIn2.duration = 200L

                    val animatorSet = AnimatorSet()
                    animatorSet.playSequentially(animatorOut2, animatorIn2)
                    animatorSet.interpolator = AccelerateDecelerateInterpolator()
                    animatorSet.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator) {}
                        override fun onAnimationEnd(p0: Animator) {
                            cusTomAnimatorViewPager()
                        }

                        override fun onAnimationCancel(p0: Animator) {}
                        override fun onAnimationRepeat(p0: Animator) {}
                    })
                    animatorSet.start()
                }
            }
        })

        binding.indicator.apply {
            setSliderColor(
                Color.parseColor("#26FFFFFF"),
                ContextCompat.getColor(binding.root.context, R.color.orange)
            )
            setSliderWidth(
                resources.getDimension(com.intuit.sdp.R.dimen._5sdp),
                resources.getDimension(com.intuit.sdp.R.dimen._15sdp)
            )
            setSliderHeight(resources.getDimension(com.intuit.sdp.R.dimen._5sdp))
            setSlideMode(IndicatorSlideMode.NORMAL)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setupWithViewPager(viewPager)
            notifyDataChanged()
        }


        //viewpager 2
        imageAdapter2 = ViewPagerAdapter2(imageList2 as MutableList<Int>)
        viewPager2.adapter = imageAdapter2

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val item = viewPager2.getChildAt(0)
                pagePxWidth2 = viewPager2.width.toFloat()
                if (position == 0 && isFirst2) {
                    //run first when building the app
                    cusTomAnimatorViewPager2()
                }
                if (!isFirst2) {
                    val animatorOut2 =
                        ObjectAnimator.ofFloat(item, "translationX", pagePxWidth2, -120f)
                    animatorOut2.duration = 600L

                    val animatorIn2 = ObjectAnimator.ofFloat(item, "translationX", -120f, 0f)
                    animatorIn2.duration = 200L

                    val animatorSet = AnimatorSet()
                    animatorSet.playSequentially(animatorOut2, animatorIn2)
                    animatorSet.interpolator = AccelerateDecelerateInterpolator()
                    animatorSet.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator) {}
                        override fun onAnimationEnd(p0: Animator) {
                            cusTomAnimatorViewPager2()
                        }

                        override fun onAnimationCancel(p0: Animator) {}
                        override fun onAnimationRepeat(p0: Animator) {}
                    })
                    animatorSet.start()
                }
            }

        })


    }

    private fun updateImage() {
        handler.postDelayed({
            if (isImv1Visible) {
                binding.imv1?.animate()?.alpha(0f)?.setDuration(1200)?.withEndAction {
                    currentIndex = (currentIndex + 1) % list.size
                    binding.imv1?.setImageResource(list[currentIndex].resourceId)
                }?.start()
                binding.imv2?.animate()?.alpha(1f)?.setDuration(1200)?.withEndAction {
                    updateImage()
                }?.start()
            } else {
                binding.imv2?.animate()?.alpha(0f)?.setDuration(1200)?.withEndAction {
                    currentIndex = (currentIndex + 1) % list.size
                    binding.imv2?.setImageResource(list[currentIndex].resourceId)
                }?.start()
                binding.imv1?.animate()?.alpha(1f)?.setDuration(1200)?.withEndAction {
                    updateImage()
                }?.start()
            }

            isImv1Visible = !isImv1Visible
        }, 3300L)

    }

    private fun cusTomAnimatorViewPager() {
        handlerVP1.postDelayed({
            val item = viewPager.getChildAt(0)
            pagePxWidth1 = (viewPager.width).toFloat()
            val animatorRight = ObjectAnimator.ofFloat(item, "translationX", 0f, 60f)
            animatorRight.duration = 200L
            val animatorInLeft = ObjectAnimator.ofFloat(item, "translationX", 60f, -pagePxWidth1)
            animatorInLeft.duration = 500L
            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(animatorRight, animatorInLeft)
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {}
                override fun onAnimationEnd(p0: Animator) {
                    if (currentPage == imageAdapter.itemCount - 1) {
                        currentPage = 0
                    } else {
                        currentPage++
                    }
                    isFirst = false
                    binding.viewPager.setCurrentItem(currentPage, false)
                }

                override fun onAnimationCancel(p0: Animator) {}
                override fun onAnimationRepeat(p0: Animator) {}
            })
            animatorSet.start()
        }, 3000L)

    }

    private fun cusTomAnimatorViewPager2() {
        handlerVP2.postDelayed({
            val item = viewPager2.getChildAt(0)
            pagePxWidth2 = viewPager2.width.toFloat()
            val animatorRight = ObjectAnimator.ofFloat(item, "translationX", 0f, 60f)
            animatorRight.duration = 200L
            val animatorInLeft = ObjectAnimator.ofFloat(item, "translationX", 60f, -pagePxWidth2)
            animatorInLeft.duration = 500L
            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(animatorRight, animatorInLeft)
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {}
                override fun onAnimationEnd(p0: Animator) {
                    if (currentPage2 == imageAdapter2.itemCount - 1) {
                        currentPage2 = 0
                    } else {
                        currentPage2++
                    }
                    isFirst2 = false
                    binding.viewPager2.setCurrentItem(currentPage2, false)
                }

                override fun onAnimationCancel(p0: Animator) {}
                override fun onAnimationRepeat(p0: Animator) {}
            })
            animatorSet.start()
        }, 3000L)
    }


}
