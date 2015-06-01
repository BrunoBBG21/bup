package br.com.bup.controller;


import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.TransacaoBancariaDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.TransacaoBancaria;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.jasperreports.Report;
import br.com.caelum.vraptor.jasperreports.ReportBuilder;
import br.com.caelum.vraptor.jasperreports.download.ReportDownload;
import br.com.caelum.vraptor.jasperreports.formats.Pdf;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.validator.Validator;
@Controller
@Named("relatorio")
public class RelatorioController extends BaseWeb{
private final static Logger LOGGER = LoggerFactory.getLogger(AgenciaController.class);
	
	private final UsuarioDAO usuarioDAO;
	private final  TransacaoBancariaDAO transacaoBancariaDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected RelatorioController() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public RelatorioController(Result result, Validator validator, TransacaoBancariaDAO transacaoBancariaDAO,
			UsuarioSession usuarioSession, UsuarioDAO usuarioDAO, ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.usuarioDAO = usuarioDAO;
		this.transacaoBancariaDAO = transacaoBancariaDAO;
		
	}
	@OpenTransaction
	public Download teste() {
		List<TransacaoBancaria> dataList = transacaoBancariaDAO.buscarTodos();
		  Report report = new ReportBuilder().withTemplate("report1.jasper").withData(dataList).build();
		        return new ReportDownload(report, new Pdf());
		 }
}
//   @OpenTransaction
//   public  void relatorio() {
//	   
//      String sourceFileName =
//         "c://tools/jasperreports-5.0.1/test/jasper_report_template.jasper";
//      List<TransacaoBancaria> dataList = transacaoBancariaDAO.buscarTodos();
//
//      JRBeanCollectionDataSource beanColDataSource =
//      new JRBeanCollectionDataSource(dataList);
//
//      Map parameters = new HashMap();
//      try {
//         JasperPrint fillReport = JasperFillManager.fillReport(
//         sourceFileName,
//         parameters,
//         beanColDataSource);
//         
////         JasperPrintManager.printReport(fillReport., withPrintDialog), false) j = new JasperPrintFactory();
//         ReportDownload download = new ReportDownload(report, ExportFormats.pdf(), false);
////         http://www.guj.com.br/16024-plugin-vraptor-jasperreport---exibir-pdf-no-navegador-ao-inves-de-realizar-o-download
//      } catch (JRException e) {
//         e.printStackTrace();
//      }
//   }
