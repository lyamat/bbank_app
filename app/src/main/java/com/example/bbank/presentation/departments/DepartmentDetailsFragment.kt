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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentDepartmentDetailsBinding
import com.example.bbank.presentation.activity.MainActivity
import com.example.core.domain.department.Department
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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.roundToInt

@AndroidEntryPoint
internal class DepartmentDetailsFragment :
    BaseFragment<FragmentDepartmentDetailsBinding>(FragmentDepartmentDetailsBinding::inflate) {

    private val args by navArgs<DepartmentDetailsFragmentArgs>()
    private val departmentsViewModel by activityViewModels<DepartmentsViewModel>()

    override fun setupView() {
        getDepartmentByIdFromArgs()
        observeDepartmentsState()
    }

    private fun getDepartmentByIdFromArgs() {
        departmentsViewModel.getDepartmentById(args.departmentId)
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
            setupFragmentViews(it)
            setupMapDisplay(it)
            makeMapVerticalScrollable()
            setMapResizeOnClickListener()
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

    private fun setupFragmentViews(department: Department) =
        binding.apply {
            val address = getDepartmentAddress(department)
            tvDepartmentAddress.text =
                address // TODO: it fast, use department.getFullAddress(), create few func (for fr'details and main'fr)
            tvDepartmentName.text = getString(R.string.oao_belarusbank, department.filialsText)
            tvUpdateTime.text = SimpleDateFormat("HH:mm", Locale.UK).format(Date())
            (activity as MainActivity).supportActionBar?.title = department.filialsText
        }

    private fun setupMapDisplay(department: Department) =
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            setupMap(department)
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

    private suspend fun setupMap(department: Department) {
        val mapView = binding.mapView
        try {
            MapKitFactory.initialize(requireContext())
            val geocoder = Geocoder(requireContext())
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
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun handleError(error: String) =
    // TODO: was currencyRates now departmentCurrencyRates, refactor remaining (room at all)
        // TODO: error appears when fast closing map (fragment) 
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()

    override fun onClickButtonCancel() = Unit
}