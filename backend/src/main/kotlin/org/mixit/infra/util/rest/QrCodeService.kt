package org.mixit.infra.util.rest

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.nio.charset.StandardCharsets
import java.util.EnumMap

object QrCodeService {
    fun generateSvg(content: String, height: Int = 200, width: Int = 200): String {
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = StandardCharsets.UTF_8.name()
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.M
        val bitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints)
        val svgXml = MatrixToSvgWriter.toSvg(bitMatrix)
        return svgXml
    }
}

object MatrixToSvgWriter {
    fun toSvg(matrix: com.google.zxing.common.BitMatrix): String {
        val width = matrix.width
        val height = matrix.height
        val sb = StringBuilder()
        sb.append("<svg xmlns='http://www.w3.org/2000/svg' width='$width' height='$height' shape-rendering='crispEdges'>")
        sb.append("<rect width='100%' height='100%' fill='white'/>")
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (matrix[x, y]) {
                    sb.append("<rect x='$x' y='$y' width='1' height='1' fill='black'/>")
                }
            }
        }
        sb.append("</svg>")
        return sb.toString()
    }
}
