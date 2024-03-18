package com.imp.clinica.util;

import com.imp.clinica.entities.CitaAgendada;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import java.awt.*;
import java.io.IOException;


public class CitaAgendadaPDF {
    private CitaAgendada citaAgendada;
    private Font fuente;
    @Autowired
    public CitaAgendadaPDF(CitaAgendada citaAgendada) {
        this.citaAgendada = citaAgendada;
    }

    public void construirCabezera(Document document)throws IOException {
        fuente =  FontFactory.getFont(FontFactory.HELVETICA_BOLD,25);
        PdfPTable table = new PdfPTable(1);
        PdfPCell cabecera = new PdfPCell();
        table.setWidthPercentage(130);

        cabecera.setBackgroundColor(Color.CYAN);
        cabecera.setPadding(10f);
        cabecera.setFixedHeight(120);
        Paragraph titulo = new Paragraph("Clinic Web\nHoja de Datos sobre cita agendada",fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        cabecera.addElement(titulo);
        table.addCell(cabecera);

        document.add(table);
    }
    public void construirDatosDocumento(Document document) {
        fuente = FontFactory.getFont(FontFactory.TIMES_ROMAN,16);

        Paragraph contenidoPaciente = new Paragraph("\nInformacion del Paciente: \nNombre Completo: "+citaAgendada.getCliente().getNombre()+
                citaAgendada.getCliente().getApellido()+"\nEdad del Paciente: "+citaAgendada.getCliente().getEdad()+" años"+
                "\nCorreo Electronico: "+citaAgendada.getCliente().getCorreo(),fuente);
        Paragraph contenidoCita = new Paragraph("\n\nInformacion de la Cita: "+
                "\nTratamiento Establecido: "+citaAgendada.getCita_id().getTratamiento()+"\nFecha de la Cita: "+citaAgendada.getCita_id().
                getFechaCita().getYear()+"/"+ citaAgendada.getCita_id().getFechaCita().getMonthValue()+"/"+citaAgendada.getCita_id().getFechaCita().
                getDayOfMonth()+"\nHora: "+citaAgendada.getCita_id().getFechaCita().getHour()+":"+citaAgendada.getCita_id().getFechaCita().getMinute()+
                "\n\nDoctor a cargo: "+citaAgendada.getCita_id().getMedico_id().getNombre(),fuente);

        Paragraph footerText = new Paragraph("\n\n\nPresenta este documento para validar tu cita en la clinica");
        footerText.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(contenidoPaciente);
        document.add(contenidoCita);
        document.add(footerText);
    }
    public void exportar(HttpServletResponse response)throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();

        try {
            construirCabezera(document);
            construirDatosDocumento(document);
            document.close();
        }catch (IOException e) {
            throw new IOException("No se pudo imprimir los valores en el PDF "+e.getMessage());
        }
    }
}