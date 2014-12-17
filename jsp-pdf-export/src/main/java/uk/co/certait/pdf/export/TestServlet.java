package uk.co.certait.pdf.export;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import com.lowagie.text.DocumentException;

public class TestServlet extends HttpServlet {

	private static final String PDF_RESPONSE_KEY = "pdf";
	private static final String PDF_URL_STRING = "pdfExportUrl";
	private static final String USER_COUNT_KEY = "userCount";
	private static final String USERS_ATTRIBUTE_KEY = "users";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Person> people = generateModel(Integer.parseInt(request.getParameter(USER_COUNT_KEY)));
		request.setAttribute(USERS_ATTRIBUTE_KEY, people);

		if (isPdfRequested(request)) {
			try {
				streamPDf(request, response);
			} catch (Exception e) {
				throw new ServletException(e);
			}

		} else {
			request.setAttribute(PDF_URL_STRING, generatePdfExportUrl(request));
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/pages/output.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected boolean isPdfRequested(HttpServletRequest request) {
		return StringUtils.equals(request.getParameter(PDF_RESPONSE_KEY), "true");
	}

	protected String generatePdfExportUrl(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(PDF_RESPONSE_KEY).append("=true");

		for (String key : request.getParameterMap().keySet()) {
			builder.append("&").append(key).append("=").append(request.getParameterMap().get(key)[0]);
		}

		return builder.toString();
	}

	protected void streamPDf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
			SAXException, ParserConfigurationException, DocumentException {

		// custom response write: writes the processed JSP to an HTML String
		CharArrayWriterResponse customResponse = new CharArrayWriterResponse(response);
		request.getRequestDispatcher("/pages/output.jsp").forward(request, customResponse);
		String html = customResponse.getOutput();
		byte[] data = html.getBytes();

		// send the generated HTML to XhtmlRenderer
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(data));
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(doc, null);
		renderer.layout();

		// stream the generated PDF to the client
		OutputStream out = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=export.pdf");
		response.setContentLength(data.length);

		renderer.createPDF(out);
		out.flush();
		out.close();
	}

	private List<Person> generateModel(int count) {
		List<Person> people = new ArrayList<Person>();

		for (int i = 0; i < count; ++i) {
			people.add(new Person(i, "Forename " + i, "Surname " + i));
		}

		return people;
	}
}
