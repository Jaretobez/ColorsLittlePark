/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import javax.swing.JOptionPane;

public class CorteDeCaja {
    ConexionBD con = new ConexionBD();
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private String fechaActual = "";
    private String nombreArchivoPDFVenta = "";

    public void generarReporte() {
        try {
            // Determinar el turno basado en la hora actual
            LocalTime ahora = LocalTime.now();
            String turno = ahora.isAfter(LocalTime.of(15, 0)) ? "Vespertino" : "Matutino";

            // Cargar la fecha actual
            Date date = new Date();
            fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);
            // Cambiar el formato de la fecha de / a _
            String fechaNueva = fechaActual.replace("/", "_");

            nombreArchivoPDFVenta = "Reporte_Ventas_" + turno + "_" + fechaNueva + ".pdf";

            FileOutputStream archivo;
            File file = new File("src/PDF/" + nombreArchivoPDFVenta);
            archivo = new FileOutputStream(file);

            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Image img = Image.getInstance("src/img/activos.png");
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE); // Agregar nueva línea
            fecha.add("Reporte de Ventas - Turno: " + turno + "\nFecha: " + fechaActual + "\n\n");

            PdfPTable encabezado = new PdfPTable(4);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0); // Quitar el borde de la tabla
            // Tamaño de las celdas
            float[] columnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            encabezado.setWidths(columnaEncabezado);
            encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            // Agregar celdas
            encabezado.addCell(img);

            String ruc = "0987654321001";
            String nombre = "Colors Little Park";
            String telefono = "0987654321";
            String direccion = "Cd del Carmen";
            String razon = "sistema de control colors littlepark version pro VIP master";

            encabezado.addCell(""); // Celda vacía
            encabezado.addCell("RUC: " + ruc + "\nNOMBRE: " + nombre + "\nTELEFONO: " + telefono + "\nDIRECCION: " + direccion + "\nRAZON SOCIAL: " + razon);
            encabezado.addCell(fecha);
            doc.add(encabezado);

            // Cuerpo
            Paragraph ventasTitulo = new Paragraph();
            ventasTitulo.add(Chunk.NEWLINE); // Nueva línea
            ventasTitulo.add("Ventas del turno " + turno + ": " + "\n\n");
            doc.add(ventasTitulo);

            // Tabla de ventas
            PdfPTable tablaVentas = new PdfPTable(8);
            tablaVentas.setWidthPercentage(100);
            tablaVentas.getDefaultCell().setBorder(0); // Quitar bordes
            // Tamaño de las celdas
            float[] columnaVentas = new float[]{10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};
            tablaVentas.setWidths(columnaVentas);
            tablaVentas.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell venta1 = new PdfPCell(new Phrase("Folio", negrita));
            PdfPCell venta2 = new PdfPCell(new Phrase("Tiempo", negrita));
            PdfPCell venta3 = new PdfPCell(new Phrase("Tipo Pago", negrita));
            PdfPCell venta4 = new PdfPCell(new Phrase("Monto Total", negrita));
            PdfPCell venta5 = new PdfPCell(new Phrase("Fecha", negrita));
            PdfPCell venta6 = new PdfPCell(new Phrase("Infante", negrita));
            PdfPCell venta7 = new PdfPCell(new Phrase("Hora Entrada", negrita));
            PdfPCell venta8 = new PdfPCell(new Phrase("Hora Salida", negrita));
            // Quitar bordes
            venta1.setBorder(0);
            venta2.setBorder(0);
            venta3.setBorder(0);
            venta4.setBorder(0);
            venta5.setBorder(0);
            venta6.setBorder(0);
            venta7.setBorder(0);
            venta8.setBorder(0);
            // Agregar color al encabezado de ventas
            venta1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            venta2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            venta3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            venta4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            venta5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            venta6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            venta7.setBackgroundColor(BaseColor.LIGHT_GRAY);
            venta8.setBackgroundColor(BaseColor.LIGHT_GRAY);
            // Agregar celdas a la tabla
            tablaVentas.addCell(venta1);
            tablaVentas.addCell(venta2);
            tablaVentas.addCell(venta3);
            tablaVentas.addCell(venta4);
            tablaVentas.addCell(venta5);
            tablaVentas.addCell(venta6);
            tablaVentas.addCell(venta7);
            tablaVentas.addCell(venta8);

            // Obtener los datos de ventas del turno especificado
            String sql = obtenerConsultaSQL(turno);
            try {
                connection = con.conector();
                pst = connection.prepareStatement(sql);
                rs = pst.executeQuery();

                while (rs.next()) {
                    String folio = rs.getString("folio");
                    String tiempo = rs.getString("tiempo");
                    String tipoPago = rs.getString("tipo_pago");
                    String monto_total = rs.getString("monto_total");
                    String fecha2 = rs.getString("fecha");
                    String fkInfante = rs.getString("fk_infante");
                    String horaEntrada = rs.getString("hora_entrada");
                    String horaSalida = rs.getString("hora_salida");

                    tablaVentas.addCell(folio);
                    tablaVentas.addCell(tiempo);
                    tablaVentas.addCell(tipoPago);
                    tablaVentas.addCell(monto_total);
                    tablaVentas.addCell(fecha2);
                    tablaVentas.addCell(fkInfante);
                    tablaVentas.addCell(horaEntrada);
                    tablaVentas.addCell(horaSalida);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al conectar: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pst != null) pst.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
                }
            }
            doc.add(tablaVentas);

            // Firma
            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelación y firma\n\n");
            firma.add("_______________________");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            // Mensaje
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("¡Gracias por su compra!");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            // Cerrar el documento y el archivo
            doc.close();
            archivo.close();

            // Abrir el documento al ser generado automáticamente
            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException e) {
            System.out.println("Error en: " + e);
        }
    }

    private String obtenerConsultaSQL(String turno) {
        if (turno.equals("Matutino")) {
            return "SELECT * FROM venta WHERE hora_entrada BETWEEN '08:00:00' AND '14:59:59'";
        } else {
            return "SELECT * FROM venta WHERE hora_entrada BETWEEN '15:00:00' AND '20:00:00'";
        }
    }

    public static void main(String[] args) {
        CorteDeCaja reporte = new CorteDeCaja();
        reporte.generarReporte();
    }
}


