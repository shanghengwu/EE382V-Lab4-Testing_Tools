import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.*;
import com.mockobjects.servlet.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestingLabConverterServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String farTemp = request.getParameter("farenheitTemperature");
        
        if (farTemp == null) {
            out.println("<html><head><title>No Temperature</title>"
                    + "</head><body><h2>Need to enter a temperature!"
                    + "</h2></body></html>");
            out.close();
            return;
        }
        farTemp = farTemp.trim();
        
        Double farTempDouble = null;
        try {
          farTempDouble = Double.parseDouble(farTemp);
        } catch (NumberFormatException e) {
          out.println("<html><head><title>Bad Temperature</title>"
                    + "</head><body><h2>Need to enter a valid temperature!"
                    + "Got a NumberFormatException on " 
                    + farTemp 
                    + "</h2></body></html>");
          out.close();
          return;
        }
        
        Double celTempDouble = 100.0*(farTempDouble - 32.0)/180.0;
        DecimalFormat df = new DecimalFormat("#.0");
        String celTemp = df.format(celTempDouble);
        /*
        out.println("<html><head><title>Temperature Converter Result</title>"
                    + "</head><body><h2>" + farTemp + " Farenheit = " + celTemp + " Celsius "
                    + "</h2>");
        */
        out.print(  "Fahrenheit: "+ farTemp 
                    + ", Celsius: " + celTemp
                    );

        String austinTemperature = CityTemperatureServiceProvider.lookup("Austin");
        /*
        out.println("<p><h3>The temperature in Austin is " + austinTemperature + " degrees Farenheit</h3>");
        out.println("</body></html>");
        */
        out.print(", Austin Temperature in Farenheit: " + austinTemperature);
        out.close();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Test
    public void test_Austin() throws Exception {
        TestingLabConverterServlet s = new TestingLabConverterServlet();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
    
        request.setupAddParameter("farenheitTemperature", "212");
        response.setExpectedContentType("text/html");
        s.doGet(request,response);
        response.verify();
        //System.out.println("response.getOutputStreamContents()= "+response.getOutputStreamContents().trim());
        assertEquals("Fahrenheit: 212, Celsius: 100.0, Austin Temperature in Farenheit: 451", response.getOutputStreamContents().trim());
    }
}