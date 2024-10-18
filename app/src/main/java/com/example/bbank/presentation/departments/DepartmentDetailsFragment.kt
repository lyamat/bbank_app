package com.example.bbank.presentation.departments

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentDepartmentDetailsBinding
import com.example.bbank.presentation.activity.MainActivity
import com.example.bbank.presentation.adapters.CurrencyRatesAdapter
import com.example.core.domain.department.Department
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.roundToInt

@AndroidEntryPoint
internal class DepartmentDetailsFragment : Fragment() {
    private lateinit var binding: FragmentDepartmentDetailsBinding
    private val args by navArgs<DepartmentDetailsFragmentArgs>()
    private lateinit var mapView: MapView
    private val department by lazy { args.departmentParcelable.toDepartment() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDepartmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCurrencyRatesRecyclerView()
        setupFragmentViews()
        setupMapDisplay()
        makeMapVerticalScrollable()
        setMapResizeOnClickListener()
    }

    private fun setupCurrencyRatesRecyclerView() =
        binding.rvCurrencyBuySale.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CurrencyRatesAdapter(
                department = department
            )
        }

    private fun setupFragmentViews() =
        binding.apply {
            val address = getDepartmentAddress(department)
            tvDepartmentAddress.text = address
            tvDepartmentName.text = getString(R.string.oao_belarusbank, department.filialsText)
            tvUpdateTime.text = SimpleDateFormat("HH:mm", Locale.UK).format(Date())
            (activity as MainActivity).supportActionBar?.title = department.filialsText
        }

    private fun setupMapDisplay() =
        CoroutineScope(Dispatchers.Main).launch {
            setupMap()
        }

    private fun setMapResizeOnClickListener() =
        binding.apply {
            ivIncreaseMapView.setOnClickListener {
                animateHeightChange(binding.mapLayout, dpToPx(500))
                ivDecreaseMapView.visibility = View.VISIBLE
                ivIncreaseMapView.visibility = View.GONE
            }
            ivDecreaseMapView.setOnClickListener {
                animateHeightChange(binding.mapLayout, dpToPx(150))
                ivDecreaseMapView.visibility = View.GONE
                ivIncreaseMapView.visibility = View.VISIBLE
            }
        }

    private fun dpToPx(dp: Int): Int =
        (dp * resources.displayMetrics.density).roundToInt()

    private fun animateHeightChange(view: View, toHeight: Int) =
        with(ValueAnimator.ofInt(view.measuredHeight, toHeight)) {
            addUpdateListener { valueAnimator ->
                val layoutParams = view.layoutParams
                layoutParams.height = valueAnimator.animatedValue as Int
                view.layoutParams = layoutParams
            }
            duration = 150
            start()
        }

    private fun getDepartmentAddress(department: Department) = getString(
        R.string.department_address,
        department.nameType,
        department.name,
        department.streetType,
        department.street,
        department.homeNumber
    )

    private suspend fun setupMap() {
        MapKitFactory.initialize(requireContext())
        mapView = binding.mapView
        val geocoder = Geocoder(requireContext())
        try {
            val coordinates = geocoder.getAddressCoordinates(getDepartmentAddress(department))
            if (coordinates != null) {
                mapView.mapWindow.map.move(
                    CameraPosition(
                        Point(coordinates.first, coordinates.second),
                        18.0f,
                        0.0f,
                        0.0f
                    )
                )
            } else mapView.visibility = View.GONE
        } catch (e: Exception) {
            handleError(e.message.toString())
        }
    }

    private suspend fun Geocoder.getAddressCoordinates(
        address: String
    ): Pair<Double, Double>? = withContext(Dispatchers.IO) {
        try {
            val location = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCoroutine { cont: Continuation<Address?> ->
                    getFromLocationName(address, 1) {
                        cont.resume(it.firstOrNull())
                    }
                }
            } else {
                suspendCoroutine { cont: Continuation<Address?> ->
                    @Suppress("DEPRECATION")
                    val coordinates = getFromLocationName(address, 1)?.firstOrNull()
                    cont.resume(coordinates)
                }
            }
            location?.let {
                Pair(it.latitude, it.longitude)
            }
        } catch (e: Exception) {
            handleError(e.message.toString())
            null
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun makeMapVerticalScrollable() {
        val mainScrollView = binding.scrollView
        val transparentImageView = binding.transparentImage

        transparentImageView.setOnTouchListener { _, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    mainScrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }

                MotionEvent.ACTION_UP -> {
                    mainScrollView.requestDisallowInterceptTouchEvent(false)
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    mainScrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }

                else -> true
            }
        }
    }

    private fun handleError(error: String) =
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
}