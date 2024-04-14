package ru.mirea.andreevapk.mireaproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.mirea.andreevapk.mireaproject.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private val REQUEST_CODE_PERMISSION = 100
    private lateinit var mapView: MapView
    private lateinit var locationNewOverlay: MyLocationNewOverlay
    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance()
            .load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            funcAll()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), REQUEST_CODE_PERMISSION)
        }
    }

    override fun onResume() {
        super.onResume()
        Configuration.getInstance()
            .load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        if (::mapView.isInitialized) {
            mapView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        Configuration.getInstance()
            .save(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        if (::mapView.isInitialized) {
            mapView.onPause()
        }
    }

    private fun setMarker(nameMarker: String, point: GeoPoint) {
        val marker = Marker(mapView)
        marker.position = point
        marker.setOnMarkerClickListener { marker, mapView ->
            Toast.makeText(requireContext(), "Описание: $nameMarker", Toast.LENGTH_SHORT).show()
            true
        }
        mapView.overlays.add(marker)
        marker.icon = ContextCompat.getDrawable(
            requireContext(),
            org.osmdroid.library.R.drawable.osm_ic_follow_me_on
        )
        marker.title = nameMarker
    }

    private fun funcAll() {
        mapView = binding.mapView
        mapView.setZoomRounding(true)
        mapView.setMultiTouchControls(true)
        val mapController: IMapController = mapView.controller
        mapController.setZoom(15.0)
        val centerPoint = GeoPoint(55.760733, 37.636789)
        mapController.setCenter(centerPoint)

        locationNewOverlay = MyLocationNewOverlay(
            GpsMyLocationProvider(requireContext()),
            mapView
        )
        locationNewOverlay.enableMyLocation()
        mapView.overlays.add(locationNewOverlay)

        val compassOverlay = CompassOverlay(
            requireContext(),
            InternalCompassOrientationProvider(requireContext()),
            mapView
        )
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)

        val context: Context = requireContext()
        val dm = context.resources.displayMetrics
        val scaleBarOverlay = ScaleBarOverlay(mapView)
        scaleBarOverlay.setCentred(true)
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10)
        mapView.overlays.add(scaleBarOverlay)

        setMarker("МГУ\n" +
                "Адрес: ул. Колмогорова, 1, Москва, 119991", GeoPoint(55.702760, 37.531086))
        setMarker("МФТИ\n" +
                "Адрес: Институтский пер., 9, Долгопрудный, Московская обл., 141701", GeoPoint(55.929566, 37.519218))
        setMarker("Бауманка\n" +
                "Адрес: 2-я Бауманская ул., д.5, стр.1, Москва, 105005", GeoPoint(55.765670, 37.685522))
        setMarker("МИФИ\n" +
                "Адрес: Каширское ш., 31, Москва, 115409", GeoPoint(55.649802, 37.664688))
        setMarker("МАИ\n" +
                "Адрес: Волоколамское ш., 4, Москва, 125080 ", GeoPoint(55.811651, 37.502525))
        setMarker("Психоневрологический диспансер №15\n" +
                "Адрес: Армянский пер., 3-5с4, Москва, 101000", GeoPoint(55.760733, 37.636789))

        val startPointRoute = GeoPoint(55.765670, 37.685522)
        val endPointRoute = GeoPoint(55.760733, 37.636789)
        addRouteWithoutAPI(startPointRoute, endPointRoute)
    }

    private fun addRouteWithoutAPI(startPoint: GeoPoint, endPoint: GeoPoint) {
        mapView?.let { mapView ->
            val roadOverlay = Polyline(mapView)
            roadOverlay.title = "Route"
            roadOverlay.addPoint(startPoint)
            roadOverlay.addPoint(endPoint)
            roadOverlay.color = Color.BLUE
            roadOverlay.width = 5f

            mapView.overlays.add(roadOverlay)
            mapView.invalidate()
        }
    }
}
