package com.stonic.stonic_scanner_movil_fe

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint

class BarcodeScannerFragment : Fragment() {
    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var textViewResult: TextView
    private var isFlashlightOn = false
    private var isFlashOn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_barcode_scanner, container, false)

        // Permitir rotación horizontal solo para este fragmento
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        barcodeScannerView = view.findViewById(R.id.barcode_scanner)
        // Configurar los formatos que queremos escanear
        val formats = listOf(
            BarcodeFormat.CODE_128,
            BarcodeFormat.CODE_39,
            BarcodeFormat.CODE_93,
            BarcodeFormat.EAN_8,
            BarcodeFormat.EAN_13,
            BarcodeFormat.UPC_A,
            BarcodeFormat.UPC_E,
            BarcodeFormat.ITF,
            BarcodeFormat.CODABAR
        )
        barcodeScannerView.barcodeView.decoderFactory = DefaultDecoderFactory(formats)

        barcodeScannerView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    Log.d("BarcodeScannerFragment", "Barcode result: ${it.text}")
                    // Crear Intent para abrir ScanResultActivity y pasar el resultado
                    val barcodeString = it.text
                    val intent = Intent(activity, ResultActivity::class.java)
                    intent.putExtra("result_string", barcodeString)
                    startActivity(intent)

                } ?: Log.e("BarcodeScannerFragment", "Barcode result is null")
            }
            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                // Do something with possible result points
            }
        })
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_flashlight -> {
                isFlashlightOn = !isFlashlightOn
                if (isFlashlightOn) {
                    barcodeScannerView.setTorchOn()
                    item.setIcon(R.drawable.ic_flashlight)
                } else {
                    barcodeScannerView.setTorchOff()
                    item.setIcon(R.drawable.ic_flashlight)
                }
                true
            }
            R.id.action_flash -> {
                isFlashOn = !isFlashOn
                if (isFlashOn) {
                    item.setIcon(R.drawable.ic_flash)
                } else {
                    item.setIcon(R.drawable.ic_flash)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeScannerView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeScannerView.pause()
    }
}