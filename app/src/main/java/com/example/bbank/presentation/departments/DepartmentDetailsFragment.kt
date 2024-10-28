package com.example.bbank.presentation.departments

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentDepartmentDetailsBinding
import com.example.bbank.presentation.activity.MainActivity
import com.example.core.domain.department.Department
import com.example.core.domain.util.extentions.getAddress
import com.example.core.domain.util.extentions.getName
import com.example.core.presentation.ui.base.BaseFragment
import com.example.core.presentation.ui.util.department.filterForAvailable
import com.example.core.presentation.ui.util.department.getDepartmentCurrencies
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.Continuation
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.roundToInt

@AndroidEntryPoint
internal class DepartmentDetailsFragment :
    BaseFragment<FragmentDepartmentDetailsBinding>(FragmentDepartmentDetailsBinding::inflate) {

    private val departmentsViewModel by activityViewModels<DepartmentsViewModel>()

    override fun setupView() {
        getDepartmentIdFromArgs()
        observeDepartmentsState()
    }

    private fun getDepartmentIdFromArgs() {
        arguments?.getString("departmentId")?.let {
            getDepartmentByLink(it)
        }
    }

    private fun getDepartmentByLink(departmentId: String) {
        departmentsViewModel.getDepartmentById(departmentId)
    }

    private fun observeDepartmentsState() =
        viewLifecycleOwner.lifecycleScope.launch {
            departmentsViewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    handleDepartmentState(it)
                }
        }

    private fun handleDepartmentState(state: DepartmentsState) {
        state.chosenDepartment?.let {
            setupDepartmentCurrencyRecyclerView(it)
            setupViews(it)
            setupMapDisplay(it)
            makeMapVerticalScrollable()
            setMapResizeClickListener()
        }
    }

    private fun setupDepartmentCurrencyRecyclerView(department: Department) =
        binding.rvDepartmentCurrency.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DepartmentCurrencyAdapter(
                departmentCurrencies = department.getDepartmentCurrencies(requireContext())
                    .filterForAvailable()
            )
        }

    private fun setupViews(department: Department) =
        binding.apply {
            tvDepartmentAddress.text = department.getAddress()
            tvDepartmentName.text = department.getName()
            tvUpdateTime.text = SimpleDateFormat("HH:mm", Locale.UK).format(Date())
            (activity as MainActivity).supportActionBar?.title = department.filialsText
        }

    private fun setupMapDisplay(department: Department) =
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            setupMap(department.getAddress())
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

    private fun setMapResizeClickListener() =
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

    private suspend fun setupMap(departmentAddress: String) {
        val mapView = binding.mapView
        try {
            MapKitFactory.initialize(requireContext())
            val geocoder = Geocoder(requireContext())
            val coordinates = geocoder.getAddressCoordinates(departmentAddress)
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
            if (e !is CancellationException)
                handleError(e.message.toString())
        }
    }

    private suspend fun Geocoder.getAddressCoordinates(
        address: String
    ): Pair<Double, Double>? = withContext(Dispatchers.IO) {
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

    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun handleError(error: String) =
        // TODO: was currencyRates now departmentCurrencyRates, refactor remaining (room at all)
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()

    override fun onClickButtonCancel() = Unit
}