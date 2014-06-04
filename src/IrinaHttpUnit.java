import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * This is my working example of the cookbook found on 
 * http://httpunit.sourceforge.net/doc/cookbook.html
 * 
 * @author Irina Likhtina iralight@gmail.com
 * Do not distribute without permission.
 *
 */
public class IrinaHttpUnit
{

	public static void main(String[] args)
	{
		WebConversation wc   = new WebConversation();
		try
		{
			String myUrl					   = "http://httpunit.sourceforge.net/doc/cookbook.html";
			
			WebResponse   resp = wc.getResponse( myUrl );
			
//			displayThePage(resp);
//			examineLinks(wc, resp);
//			examineTables(wc, resp);
//			examineForms(wc, resp);
//			examineCookies(wc, myUrl);
//			ourUrl(wc);
		} 
		catch (IOException | SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void examineCookies(WebConversation wc, String myUrl) throws IOException, SAXException
	{
		wc.addCookie("irina", "123");
		WebResponse resp = wc.getResponse(myUrl);
		junit.framework.Assert.assertEquals("123", wc.getCookieValue("irina"));
		
		String[] allCookies = wc.getCookieNames();
		for (int i = 0; i < allCookies.length; i++)
		{
			System.out.println(allCookies[i]);
		}
	}

	private static void ourUrl(WebConversation wc) throws IOException, SAXException
	{
		String myUrl = "https://secure.hain-celestial.com/celestial-seasonings/2014_01_wellness/";
		WebResponse resp = wc.getResponse(myUrl);
		
	}

	private static void examineForms(WebConversation wc, WebResponse resp) throws SAXException, IOException
	{
		WebForm form = resp.getForms()[0];      // select the first form in the page
		junit.framework.Assert.assertEquals( "La Cerentolla", form.getParameterValue( "Name" ) );
		junit.framework.Assert.assertEquals( "Chinese",       form.getParameterValue( "Food" ) );
		junit.framework.Assert.assertEquals( "Manayunk",      form.getParameterValue( "Location" ) );
		junit.framework.Assert.assertEquals( "on",            form.getParameterValue( "CreditCard" ) );	
		
		form.setParameter( "Food", "Italian" );      // select one of the permitted values for food
		form.removeParameter( "CreditCard" );         // clear the check box
		form.submit();                                // submit the form

		WebResponse formSubmissionResponse = wc.getCurrentPage();	
		System.out.println(formSubmissionResponse.getTitle());
	}

	private static void examineTables(WebConversation wc, WebResponse resp) throws SAXException
	{
	    WebTable table = resp.getTables()[0];
	    junit.framework.Assert.assertEquals( "rows", 3, table.getRowCount() );
	    junit.framework.Assert.assertEquals( "columns", 2, table.getColumnCount() );
	    String[][] colors = resp.getTables()[0].asText();
	    
	    junit.framework.Assert.assertEquals( "Name",  colors[0][0] );
	    junit.framework.Assert.assertEquals( "Color", colors[0][1] );
	    junit.framework.Assert.assertEquals( "gules", colors[1][0] );
	    junit.framework.Assert.assertEquals( "red",   colors[1][1] );
	    junit.framework.Assert.assertEquals( "sable", colors[2][0] );
	    junit.framework.Assert.assertEquals( "black", colors[2][1] );	

	}
	
	private static void examineLinks(WebConversation wc, WebResponse resp) 
			throws SAXException, IOException
	{
		WebLink       link = resp.getLinkWith( "response" );             
		link.click();
		WebResponse jdoc = wc.getCurrentPage();
		System.out.println("Page of the link title: " +jdoc.getTitle());
		System.out.println(jdoc.getText());
	}

	private static void displayThePage(WebResponse resp) throws SAXException,
			IOException
	{
		System.out.println("Title: " +resp.getTitle());
		System.out.println("Text: " + resp.getText());
	}

}
