import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.Position
import com.sksamuel.scrimage.ScaleMethod
import com.sksamuel.scrimage.nio.PngWriter
import java.awt.Color
import java.lang.Exception
import java.nio.file.Path
import kotlin.io.path.forEachDirectoryEntry
import kotlin.io.path.isDirectory

fun main(args: Array<String>) {
    val inDir = Path.of(args.getOrElse(0) { "in" })
    val outDir = Path.of(args.getOrElse(1) { "out" })

    if (!inDir.isDirectory()) {
        throw IllegalStateException("Cannot find input directory: $inDir")
    }

    if (!outDir.isDirectory()) {
        throw IllegalStateException("Cannot find output directory: $outDir")
    }

    inDir.forEachDirectoryEntry {
        try {
            processImage(it, outDir)
        } catch (e: Exception) {
            println("Failed to process input: $it, ${e.message}")
            e.printStackTrace()
        }
    }
}

fun processImage(inFile: Path, outDir: Path) {
    val outPath = outDir.resolve("smol" + inFile.fileName)

    val image = ImmutableImage.loader().fromPath(inFile)

    val width = image.width

    if (width != image.height) {
        throw IllegalStateException("Input image is not a square.")
    }

    if (width % 4 != 0) {
        throw IllegalStateException("Input image size must be a multiple of 4.")
    }

    val scaledSize = 3 * (width / 4)

    image.scaleTo(scaledSize, scaledSize, ScaleMethod.Bicubic)
        .resizeTo(width, width, Position.BottomCenter, Color(0, 0, 0, 0))
        .output(PngWriter.MaxCompression, outPath)
}
