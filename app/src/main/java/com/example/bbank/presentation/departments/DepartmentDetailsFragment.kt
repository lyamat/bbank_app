package com.example.bbank.presentation.departments

import android.animation.ValueAnimator
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentDepartmentDetailsBinding
import com.example.bbank.domain.models.Department
import com.example.bbank.presentation.adapters.CurrencyRatesAdapter
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
        setMapResizeOnClickListener()
    }

    private fun setupCurrencyRatesRecyclerView() =
        binding.rvCurrencyBuySale.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CurrencyRatesAdapter(
                department = args.department
            )
        }

    private fun setupFragmentViews() =
        binding.apply {
            val department = args.department
            val address = getDepartmentAddress(department)
            tvDepartmentAddress.text = address
            tvDepartmentName.text = getString(R.string.oao_belarusbank, department.filialsText)
            tvUpdateTime.text = SimpleDateFormat("HH:mm", Locale.UK).format(Date())
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
            val coordinates = geocoder.getAddressCoordinates(getDepartmentAddress(args.department))
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
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
}